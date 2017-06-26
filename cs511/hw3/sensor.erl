-author("Josh Gribbon").
-module(sensor).
-compile(export_all).

measure() ->
    Measurement = rand:uniform(11),
    if Measurement =:= 11 ->
