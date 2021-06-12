%option noyywrap
 
%{
	#include <stdio.h>
	#include <stdlib.h>
	#include <string.h>
	//#include "symtab.h"
	#include "operadores.h"
	#include "proyecto.tab.h"
	extern FILE *yyin;
	extern FILE *yyout;
 
	int linea = 1; 
	void ret_print(char *token_type);
	void yyerror();
%}

%x ML_COMMENT
 
letra 			[a-zA-Z]
digito 			[0-9]
alfanum 		{letra}|{digito}
print			[ -~]
 
ID 				{letra}+{alfanum}*
CONSTANTEINT	"0"|[0-9]{digito}*
CONSTANTEFLOAT	"0"|{digito}*"."{digito}+
CONSTANTECHAR	(\'{print}\')|(\'\\[nftrbv]\')
STRING			\"{print}*\"
 
%%
 
"//".*					{ printf("/ / en %d\n", linea); } 
 
"/*"					{ printf("Comentario desde %d ", linea); BEGIN(ML_COMMENT); }
<ML_COMMENT>"*/" 		{ printf("Hasta %d\n", linea); BEGIN(INITIAL); }
<ML_COMMENT>[^*\n]+		
<ML_COMMENT>"*"			
<ML_COMMENT>"\n"		{ linea += 1; }
 
 
"char"|"CHAR"       	{ return CHAR; }
"int"|"INT"				{ return INT; }
"float"|"FLOAT"			{ return FLOAT; }
"double"|"DOUBLE"		{ return DOUBLE; }
"if"|"IF"				{ return IF; }
"else"|"ELSE"			{ return ELSE; }
"while"|"WHILE"			{ return WHILE; }
"for"|"FOR"				{ return FOR; }
"continue"|"CONTINUE"	{ return CONTINUE; }
"break"|"BREAK"			{ return BREAK; }
"void"|"VOID"			{ return VOID; }
"return"|"RETURN"		{ return RETURN; }
 
 
"+"|"-"					{ return ADICION; }
"*"						{ return MULTIPLICACION; }
"/"						{ return DIVISION; }
"++"|"--"				{ return INCREMENTO; }
"||"					{ return OR; }
"&&"					{ return AND; }
"!"						{ return NOT; }
"=="|"!="				{ return EQUIVALENTE; }
">"|"<"|">="|"<="		{ return RELACION; }
 
 
"("				{ return PARENI; }
")"				{ return PAREND; }
"["				{ return CORCHI; }
"]"				{ return CORCHD; }
"{"				{ return LLAVEI; }
"}"				{ return LLAVED; }
";"				{ return PCOMA; }
"."				{ return PUNTO; }
","				{ return COMA; }
"="				{ return ASIGNACION; }
"&"				{ return REF; }
 
 
{ID} 			{
					/* Ingresar ID en la tabla de simbolos
					insert(yytext, strlen(yytext), UNDEF, linea);
					yylval.symtab_item = lookup( yytext ); */
					return ID;
				}
{CONSTANTEINT}      { yylval.int_val    = atoi(yytext); return CONSTANTEINT; }
{CONSTANTEFLOAT} 	{ yylval.double_val = atof(yytext); return CONSTANTEFLOAT; }
{CONSTANTECHAR} 	{ yylval.char_val   = yytext[0];    return CONSTANTECHAR; }
{STRING} 			{ yylval.str_val = malloc(yyleng * sizeof(char));
                  	strcpy(yylval.str_val, yytext);  return STRING; }
 
"\n"			{ linea += 1; }
[ \t\r\f]+		
 
.				{ yyerror("CARACTER DESCONOCIDO"); }
 
%%