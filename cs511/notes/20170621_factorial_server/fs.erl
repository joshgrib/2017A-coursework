%%factorial server - in class exercise 20170621
-module(fs).
-import(fact, [fact/1]).
-compile(export_all).

fserver() ->
    receive
        {From, Ref, N} ->
            From ! {self(), Ref, fact(N)},
            fserver();
        stop -> true
    end.

wait_for_reply(P, Ref1, Ref2) ->
    receive
        {P, Ref1, Res} ->
            io:format("The factorial of 5 is ~p~n", [Res]);
        {P, Ref2, Res} ->
            io:format("The factorial of 7 is ~p~n", [Res])
    end.

start() ->
    P = spawn(fun fserver/0),
    Ref1 = make_ref(),
    Ref2 = make_ref(),
    P ! {self(), Ref1, 5},
    P ! {self(), Ref2, 7},
    wait_for_reply(P, Ref1, Ref2),
    wait_for_reply(P, Ref1, Ref2),
    P ! stop.

% 1> c(fs).
% fs.erl:4: Warning: export_all flag enabled - all functions will be exported
% {ok,fs}
% 2> c(fact).
% fact.erl:2: Warning: export_all flag enabled - all functions will be exported
% {ok,fact}
% 3> fs:start().
% The factorial of 5 is 120
% The factorial of 7 is 5040
% stop
