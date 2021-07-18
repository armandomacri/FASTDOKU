%%%-------------------------------------------------------------------
%%% @author arman
%%% @copyright (C) 2021, <COMPANY>
%%% @doc
%%%     ispired by https://www.youtube.com/watch?v=0oWlmMj4bGw
%%% @end
%%% Created : 09. feb 2021 14:08
%%%-------------------------------------------------------------------
-module(web_server).

-author("Armando Macrì, Rossella De Domenicis").

%% API
-export([init/2, websocket_init/1, websocket_handle/2, websocket_info/2, terminate/3]).

%% called when the request is received
init(Req, State) ->
    io:format("Inside the init/2 callback.\n", []),
    {cowboy_websocket, Req, State, #{idle_timeout => infinity}}.

websocket_init(State) ->
    io:format("Inside the websocket_init callback.\n", []),
    io:format("PID of Websocket server is ~p. ~n", [self()]),
    {[{text, <<"Web socket server connection success">>}], State}.

websocket_handle({text, Frame}, State) ->
    io:format("Frame receivd: ~p\n", [Frame]),
    Json = jsx:decode(Frame, [return_maps]),
    Sender = binary_to_atom(maps:get(<<"sender">>, Json)),
    Receiver = binary_to_atom(maps:get(<<"receiver">>, Json)),
    Type = binary_to_atom(maps:get(<<"type">>, Json)),
    case Type of
        %% register_me is called when a new user logged in.
        %% The websocket correlated to the user is registered
        %% with the same name of the user (uniqueness).
        %% The online users list is sent as answer.
        %% If the user is already register the websocket
        %% return an error.
        register_me ->
            Check = whereis(Sender),
            if
                Check =/= undefined -> %% user already logged in
                    Response = jsx:encode(#{<<"type">> => <<"error">>,
                                            <<"sender">> => <<"WebSocket">>,
                                            <<"text">> => <<"User already logged!">>}),
                    NewState = State;
                true ->
                    register(Sender, self()),
                    fastdoku_server ! {self(), get},
                    Response = jsx:encode(#{<<"type">> => <<"info">>,
                                            <<"sender">> => <<"WebSocket">>,
                                            <<"text">> => <<"Now you are online!">>}),
                    NewState = State
            end;

        %% add_online is in charged to add the user in the gen_server that handle
        %% all the users online and ready to play.
        %% if the process is not already registered the operation is denied
        add_online ->
            Check = process_info(self(), registered_name),
            if
                Check == [] -> %% this means that the process is not registered, so the user is not logged in
                    Response = jsx:encode(#{<<"type">> => <<"error">>,
                                            <<"sender">> => <<"WebSocket">>,
                                            <<"text">> => <<"User not logged!">>}),

                    NewState = State;
                true ->
                    Name = element(2, Check),
                    fastdoku_server ! {add, Name},
                    Response = jsx:encode(#{<<"type">> => <<"info">>,
                                            <<"sender">> => <<"WebSocket">>,
                                            <<"text">> => <<"Now you are online!">>}),
                    NewState = State
            end;
        send_request ->
            NewState = State,
            whereis(Receiver) ! Frame,
            Response = jsx:encode(#{<<"type">> => <<"info">>,
                                    <<"sender">> => <<"WebSocket">>,
                                    <<"text">> => <<"Request correctly sent!">>});

        refuse_request ->
            NewState = State,
            whereis(Receiver) ! Frame,
            Response = jsx:encode(#{<<"type">> => <<"info">>,
                <<"sender">> => <<"WebSocket">>,
                <<"text">> => <<"Request correctly refused!">>});

        %% when a user accepts a challenge a message is sent to the opponent
        play_start ->
            whereis(Receiver) ! Frame,
            NewState = {busy},
            Response = jsx:encode(#{<<"type">> => <<"play_start">>,
                                    <<"sender">> => Receiver,
                                    <<"text">> => maps:get(<<"text">>, Json)});
        %% Save the username of my opponent in the state of the process.
        register_opponent ->
            NewState = {opponent, binary_to_atom(maps:get(<<"text">>, Json))},
            Response = jsx:encode(#{<<"type">> => <<"info">>,
                                    <<"sender">> => <<"WebSocket">>,
                                    <<"text">> => <<"">>});
        win ->
            Check = whereis(Receiver),
            if
                Check =/= undefined ->
                    Check ! jsx:encode(#{<<"type">> => <<"lose">>,
                        <<"sender">> => <<"WebSocket">>,
                        <<"text">> => <<"The opponent has won the game!">>}),
                    NewState = {busy},
                    Response = jsx:encode(#{<<"type">> => <<"info">>,
                        <<"sender">> => <<"WebSocket">>});
                true ->
                    NewState = State,
                    Response = jsx:encode(#{<<"type">> => <<"opponent_disconnected">>,
                        <<"sender">> => <<"WebSocket">>,
                        <<"text">> => <<"The opponent has left the game!">>})
            end;
        lose ->
            NewState = State,
            Response = Frame;

        %% the user has decided to surrender
        surrender ->
            Check = whereis(Receiver),
            if
                Check =/= undefined ->
                    Check ! Frame,
                    NewState = {surrender},
                    Response = jsx:encode(#{<<"type">> => <<"info">>,
                                            <<"sender">> => <<"WebSocket">>,
                                            <<"text">> => <<"Message sent!">>});
                true ->
                    NewState = State,
                    Response = jsx:encode(#{<<"type">> => <<"error">>,
                                        <<"sender">> => <<"WebSocket">>,
                                        <<"text">> => <<"Receiver unavailable!">>})
            end;
        _ ->
            NewState = State,
            Response = jsx:encode(#{<<"type">> => <<"nothing">>,
                                    <<"sender">> => <<"WebSocket">>,
                                    <<"text">> => <<"">>})
    end,
    {[{text, Response}], NewState};

websocket_handle (_, State) -> {ok, State}.

%% The websocket_info/2 callback is called when we use the ! operator
%% So Cowboy will call websocket_info/2 whenever an Erlang message arrives.
websocket_info (stop, State) ->
    {stop, State}; %% Say to the server to terminate the connection

websocket_info(close, State) ->
    {[{close, 1000, <<"some-reason">>}], State};

%% This function can be used to send a message Info to this process by another process
websocket_info(Info, State) ->
    {[{text, Info}], State}. %% Returns this message to the client

%% terminate/3 is for handling the termination of the connection
%% The first argument is the reason for the closing
%% The most common reasons are stop and remote
%% stop -> The server close the connection
%% remote -> The client close the connection
terminate (TerminateReason, _Req, {opponent, Username}) ->
    io:format("Disconnected: ~p\n", [self()]),
    io:format("Terminate reason: ~p\n", [TerminateReason]),
    OpponentPID = whereis(Username),
    if
        OpponentPID =/= undefined -> %% The opponent is not already disconnected
            OpponentPID ! jsx:encode(#{<<"type">> => <<"opponent_disconnected">>,
                                        <<"sender">> => <<"WebSocket">>,
                                        <<"text">> => <<"The opponent has left the game!">>});
        true ->
            ok
    end;

terminate (TerminateReason, _Req, {error, Msg}) ->
    io:format("Terminate reason: ~p\n", [TerminateReason]),
    io:format("*** Error: ~p\n", [Msg]);

terminate (TerminateReason, _Req, {surrender}) ->
    io:format("Surrender: ~p\n", [self()]),
    io:format("Terminate reason: ~p\n", [TerminateReason]);

terminate (TerminateReason, _Req, {busy}) ->
    Username = element(2, erlang:process_info(self(), registered_name)),
    fastdoku_server ! {remove, Username},
    io:format("Terminate reason: ~p\n", [TerminateReason]);

terminate (TerminateReason, _Req, {}) ->
    Username = element(2, erlang:process_info(self(), registered_name)),
    fastdoku_server ! {remove, Username},
    io:format("Terminate reason: ~p\n", [TerminateReason]),
    io:format("Terminate with empty state ~n").


