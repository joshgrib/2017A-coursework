%%% Simple calculator module
-module(cl).
-compile(export_all).

% BNF notation
% <E> := N | <E> + <E> | <E> * <E> | (<E>)
%(4+5)*7
anE() ->
    {times, {p, {plus, {num, 4}, {num, 5}}}, {num, 7}}.

-spec eval(tuple()) -> number(). %type spec helps typecheckers find problems
% you can also make your own types to be more specific,
% or use any() to match all types
eval({num, N}) ->
    N;
eval({p, E}) ->
    eval(E);
eval({plus, E1, E2}) ->
    eval(E1)+eval(E2);
eval({times, E1, E2}) ->
    eval(E1)*eval(E2).

eval2(E) ->
    case E of
        {num, N} ->
            N;
        {p, E1} ->
            eval2(E1);
        {plus, E1, E2} ->
            eval2(E1)+eval2(E2);
        {times, E1, E2} ->
            eval2(E1)*eval2(E2);
        _ -> throw(incorrect_expression)
    end.


% Testing
%
% 1> c(cl).
% cl.erl:3: Warning: export_all flag enabled - all functions will be exported
% {ok,cl}
% 2> cl:eval(cl:anE()).
% 63
%
% Works! 63 = (4+5)*7
