

%{
    #include<stdlib.h>
    #include "calculadora.tab.h"
%}

%%
[0-9]+ { yylval = atoi(yytext); return NUMERO; }
[+]    { return '+';  }
[*]    { return '*';  }
\n     { return '\n'; }
.
%%