%{
    #include <stdio.h>
    void yyerror( char *mensaje ){

        printf("Error %s \n", mensaje);

    }
%}


%token NUMERO
%%
entrada: 
%empty
;

entrada: entrada linea
;

linea: '\n'
;
linea: expresion '\n' { printf("resultado =  %d \n ", $1); }
;

expresion: NUMERO { $$ = $1; }
;
expresion: expresion expresion '+' { $$ = $1 + $2; }
;
expresion: expresion expresion '*' { $$ = $1 * $2; }
;

%%

int main(){
    yyparse(); /* Inicia y dispara análisis sintáctico*/
    return 0;
}