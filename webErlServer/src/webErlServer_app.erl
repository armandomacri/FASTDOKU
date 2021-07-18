%%%-------------------------------------------------------------------
%% @doc webErlServer public API
%% @end
%%%-------------------------------------------------------------------

-module(webErlServer_app).
-import('start', [init_main_server/0]).
-behaviour(application).

-export([start/2, stop/1]).

start(_Type, _Args) ->
    start:init_main_server(),
    Dispatch = cowboy_router:compile([
        {'_', [{"/ws", web_server, {} }]} %% {} initial state is an empty tuple
    ]),
    {ok, _} = cowboy:start_clear(http_listener,
        [{port, 8090}],
        #{env => #{dispatch => Dispatch}}
    ),
    webErlServer_sup:start_link().

stop(_State) ->
    ok = cowboy:stop_listener(http_listener). %% stop the listener when the application stops

%% internal functions

