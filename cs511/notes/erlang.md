# 20170615 Thursday - Erlang

**A binary tree in erlang**
```Erlang
% Module for binary tree
-module(bt).
%-export([aBt/0, sumBT/1]).
-compile(export_all). %%this exports everything instead of each individually

aBt() ->
    {node, 1,
        {node, 2, {leaf, 3}, {leaf, 4}},
        {node, 5, {leaf, 6}, {leaf, 7}}}.

sumBt({leaf, N}) -> N;%%note semicolons between cases
sumBt({node, N, LT, RT}) -> N+sumBT(LT)+sumBT(RT).

incBT({leaf, N}) ->
    {leaf, N+1};
incBT({node, N, LT, RT}) ->
    {node, N+1, incBT(LT), incBT(RT)}.

mapBT(F, {leaf, N}) ->
    {leaf, F(N)};
mapBT(F, {node, N, LT, RT}) ->
    {node, F(N), mapBT(LT), mapBT(RT)}.

foldBT(F, _G, {leaf, N}) -> % The _G means you're not going to use G
    F(N);
foldBT(F, G, {node, N, LT, RT}) ->
    G(N, foldBT(F, G, LT) foldBT(F, G, RT)).
% 1> c(bt).
% {ok, bt}
% 2> bt:foldBT(fun(X) -> X end, fun(X,SL,SR) -> X+SL+SR end, bt:aBt()).
% 28

% union type example
-spec f('a' | 1) -> 'b' | 1.
f(1) -> 1;
f(a) -> b.
```
**Notes:**
* Tail recursion is recommended for recursive calls so the compiler can optimize better
* For the class we want to assign types to add functions
