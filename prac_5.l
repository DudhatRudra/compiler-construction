%{
#include<stdio.h>
#include<string.h>
#include<stdlib.h>

int line = 1;
int symcount = 0;
char symtab[100][100];

int isSymbolPresent(char *str) {
    for(int i=0;i<symcount;i++) {
        if(strcmp(symtab[i], str)==0)
            return 1;
    }
    return 0;
}

void addSymbol(char *str) {
    if(!isSymbolPresent(str)) {
        strcpy(symtab[symcount++], str);
    }
}
%}

KEYWORD     int|char|float|long|return|void|struct
ID          [a-zA-Z_][a-zA-Z0-9_]*
NUMBER      [0-9]+
HEX         [0-9]+[A-Za-z]+
STRING      \"([^\"\n]*)\"
CHAR        \'([^\'\n])\'
OPERATOR    \+|\-|\*|\/|\=|\%|\.
PUNCT       [\(\)\{\}\,\;\&]

%%

{KEYWORD}    { printf("Keyword: %s\n", yytext); }

{ID}         { 
                printf("Identifier: %s\n", yytext);
                addSymbol(yytext);
             }

{NUMBER}     { printf("Constant: %s\n", yytext); }

{CHAR}       { printf("Constant: %s\n", yytext); }

{STRING}     { printf("String: %s\n", yytext); }

{OPERATOR}   { printf("Operator: %s\n", yytext); }

{PUNCT}      { printf("Punctuation: %s\n", yytext); }

{HEX}        { printf("LEXICAL ERROR: Line %d : %s invalid lexeme\n", line, yytext); }

"//".*       { }

"/*"([^*]|\n)*"*/"   { }

\n           { line++; }

[ \t\r]      { }

.            { printf("LEXICAL ERROR: Line %d : %s invalid lexeme\n", line, yytext); }

%%

int main() {
    printf("TOKENS\n");
    yylex();

    printf("\nSYMBOL TABLE ENTRIES\n");
    for(int i=0;i<symcount;i++)
        printf("%d) %s\n", i+1, symtab[i]);

    return 0;
}
