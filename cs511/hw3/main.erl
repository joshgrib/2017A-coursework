-author("Josh Gribbon").
-module(main).
-compile(export_all).

% We'll have a monitor that has to recieve info from temp sensors, and if a sensor goes down the monior will re-launch it. Reference the judge critic thing from class, but using a monitor instead of a link. A monitor is a one-way link



setup() ->
    {ok, [ N ]} = io:fread("enter number of sensors> ", "~d"),
    if N =< 1 ->
        io:fwrite("setup: range must be at least 2~n", []);
    true ->
        Num_watchers = 1 + (N div 10),
        setup_loop(N, Num_watchers)
end.
