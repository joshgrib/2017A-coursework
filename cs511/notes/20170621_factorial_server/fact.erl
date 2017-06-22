-module(fact).
-compile(export_all).

fact(0) -> 1;
fact(N) when N > 0 -> N*fact(N-1).
