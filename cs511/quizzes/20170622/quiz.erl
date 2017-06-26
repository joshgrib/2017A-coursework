%% Josh Gribbon CS511 20170622
-module(quiz).
-compile(export_all).

aT() ->
    {node, 3,
        {node, 1, {leaf, 2}, {leaf, 5}},
        {node, 6, {leaf, 8}, {leaf, 4}}}.

bfs({leaf, N}) ->
    N;
bfs({node, N, B1, B2}) ->
    [N, bfs(B1), bfs(B2)].

%%% OUTPUT
% 1> c(quiz).
% quiz.erl:3: Warning: export_all flag enabled - all functions will be exported
% {ok,quiz}
% 2> quiz:bfs(quiz:aT()).
% [3,2,4]


%%% PROBLEM - my solution messes up by nesting list results
% Class solution:
aT2() ->
    {node, 1, {node, 2, {leaf, 3}, {leaf, 4}}, {leaf, 5}}.

bfs2(T) ->
    bfs_aux([T], []).

bfs_aux([{leaf, I}|Q], L) ->
    bfs_aux(Q, [I|L]);
bfs_aux([{node, I, LT, RT}|Q], L) ->
    bfs_aux(lists:append(Q, [LT, RT]), [I|L]);
bfs_aux([], L) ->
    lists:reverse(L).


% Quiz option 2: general tree map
ex2() ->
    {node, 1, [{node, 2, []}, {node, 3, []}, {node, 4, []}]}.

mapGTree(F, {node, I, L}) ->
    {node, F(I), mapToChildren(F, L)}.

mapToChildren(_F, []) ->
    [];
mapToChildren(F, [T|TS]) ->
    [mapGTree(F, T)|mapToChildren(F, TS)].

%%% OUTPUT
% 6> c(quiz).
% quiz.erl:3: Warning: export_all flag enabled - all functions will be exported
% {ok,quiz}
% 7> quiz:ex2().
% {node,1,[{node,2,[]},{node,3,[]},{node,4,[]}]}
% 8> quiz:mapGTree(fun(I) -> I+1 end, quiz:ex2()).
% {node,2,[{node,3,[]},{node,4,[]},{node,5,[]}]}



% Quiz option 3: inorder tree traversal
iot({leaf, I}) ->
    [I];
iot({node, I, LT, RT}) ->
    iot(LT)++[I]++iot(RT).

%%% OUTPUT
% 11> c(quiz).
% quiz.erl:3: Warning: export_all flag enabled - all functions will be exported
% {ok,quiz}
% 12> quiz:iot(quiz:aT()).
% [2,1,5,3,8,6,4]
