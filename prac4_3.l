%{
#include <stdio.h>

int chars = 0;
int words = 0;
int lines = 0;
%}

%%
\n          { lines++; chars++; }
[ \t]       { chars++; }
[^ \t\n]+   { words++; chars += yyleng; }
%%

int yywrap()
{
    return 1;
}

int main()
{
    printf("Enter text (Ctrl+D to end input):\n");
    yylex();

    printf("\nTotal Characters = %d", chars);
    printf("\nTotal Words      = %d", words);
    printf("\nTotal Lines      = %d\n", lines);

    return 0;
}
