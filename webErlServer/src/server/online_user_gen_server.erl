-module(online_user_gen_server).
-author("armando").
-behavior(gen_server).

-export([start_server/0]).
-export([add_user/1, remove_user/1, get_online_user/0, reset/0, stop/0]).
-export([init/1, handle_call/3, handle_cast/2, terminate/2]).

start_server() ->
    gen_server:start({local, onlineuser_gen_server}, ?MODULE, [], []).

add_user(Username) ->
    gen_server:call(onlineuser_gen_server, {add, Username}).

remove_user(Username) ->
    gen_server:call(onlineuser_gen_server, {remove, Username}).

get_online_user() ->
    gen_server:call(onlineuser_gen_server, get).

reset() ->
    gen_server:cast(onlineuser_gen_server, reset).

stop() ->
    gen_server:cast(onlineuser_gen_server, stop).


% gen_server CALLBACK FUNCTIONS

init(_Args) ->
    {ok, {[]}}.   % general format: {ok, InitialState}

handle_call({add, Username}, _From, {H}) ->
    UserOnlineList = H ++ [Username],
    {reply, UserOnlineList, {UserOnlineList}};
  % general format: {reply, ReplyPayload, NewState}


handle_call({remove, Username}, _From, {H}) ->
    UserOnlineList = lists:delete(Username, H),
    {reply, UserOnlineList, {UserOnlineList}};
  % general format: {reply, ReplyPayload, NewState}

handle_call(get, _From, {H}) ->
    {reply, H, {H}}.

handle_cast(reset, _State) ->
    {noreply, {[]}};
  % general format: {noreply, NewState}

 handle_cast(stop, _State) ->
   {stop, normal, _State}.

 terminate(normal, _State) ->
   ok.
