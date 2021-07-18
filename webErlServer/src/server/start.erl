-module(start).
-author("armando").
%% API
-export([init_main_server/0]).

init_main_server() ->
    %start gen server for online user
    online_user_gen_server:start_server(),
    io:fwrite("===> Start gen server \n"),
    %start main server
    Serv_pid = spawn(fun() -> main_server_loop() end),
    io:fwrite("===> Start main server ~w\n", [Serv_pid]),
    %for process registration
    register(fastdoku_server, Serv_pid),
    io:fwrite("===> Main server registered! \n").

main_server_loop() ->
    receive
        {add, Username} ->
            UserList = online_user_gen_server:add_user(Username),
            inform_all(UserList, {add, Username}),
            io:format("{Online User} -> ~p\n", [UserList]);
        {remove, Username} ->
            UserList = online_user_gen_server:remove_user(Username),
            inform_all(UserList, {remove, Username}),
            io:format("{Online User} -> ~p\n", [UserList]);
        {From, get} ->
            UserList = online_user_gen_server:get_online_user(),
            From ! jsx:encode(#{<<"type">> => <<"user_list">>,
                                <<"text">> => UserList,
                                <<"sender">> => <<"WebSocket">>}),
            io:format("{Online User} -> ~p\n", [UserList]);
        _ -> ok
    end,
    main_server_loop().

inform_all([], _) -> ok;
inform_all([First|Others], {add, UpUser}) ->
    if
        First =/= UpUser ->
            whereis(First) ! jsx:encode(#{<<"type">> => <<"new_user">>,
                                        <<"text">> => UpUser,
                                        <<"sender">> => <<"WebSocket">>});
        true -> ok
    end,
    inform_all(Others, {add, UpUser});

inform_all([First|Others], {remove, UpUser}) ->
    User = whereis(First),
    if User =/= undefined ->
        User ! jsx:encode(#{<<"type">> => <<"remove_user">>,
                                    <<"text">> => UpUser,
                                    <<"sender">> => <<"WebSocket">>});
        true -> ok
    end,
    inform_all(Others, {remove, UpUser}).

%erl -sname fastdoku@localhost -setcookie 'fastdokuNode'
