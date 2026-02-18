%{
#include <stdio.h>
%}

%%
[0-9]+        { printf("NUMBER: %s\n", yytext); }
[ \t\n]+      { ; }        /* ignore whitespace */
.             { ; }
%%

int main() {
    yylex();
    return 0;
}

int yywrap() {
    return 1;
}
