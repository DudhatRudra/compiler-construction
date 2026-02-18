%option noyywrap
%option case-insensitive
%{
#include <stdio.h>
%}

%%
charusat    { printf("university"); }
.|\n        { printf("%s", yytext); }
%%

int main() {
    yylex();
    return 0;
}
