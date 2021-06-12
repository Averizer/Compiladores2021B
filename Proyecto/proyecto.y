%{
	#include "operadores.c"
	//#include "symtab.c"
	#include <stdio.h>
	#include <stdlib.h>
	#include <string.h>
	extern FILE *yyin; //Archivo entrada
	extern FILE *yyout;//Archivo salidad
	extern int linea;  //Cuenta de linea desde Flex
	extern int yylex();//Analizador lexico
	void yyerror();	   //Mensaje de error

%}
/* para saber que tipo de datos estoy utilizando */
 %union
{
    char char_val;
	int int_val;
	double double_val;
    char* str_val;
	//list_t* symtab_item;
}
/* Definicion de los tokens */
%token<int_val>  CHAR INT FLOAT DOUBLE IF ELSE WHILE FOR CONTINUE BREAK VOID RETURN
%token<int_val> ADICION MULTIPLICACION DIVISION INCREMENTO OR AND NOT EQUIVALENTE RELACION
%token<int_val> PARENI PAREND CORCHD CORCHI LLAVEI LLAVED PCOMA PUNTO COMA ASIGNACION REF
%token<symtab_item> ID 
%token <int_val> CONSTANTEINT 
%token <double_val> CONSTANTEFLOAT 
%token <char_val> CONSTANTECHAR 
%token <str_val> STRING

/* Precedencias y aociaciones */
%left 	PARENI PAREND CORCHI CORCHD
%right 	NOT INCREMENTO REF
%left 	MULTIPLICACION DIVISION
%left 	ADICION
%left 	RELACION
%left 	EQUIVALENTE
%left 	OR
%left 	AND
%right 	ASIGNACION
%left 	COMA

%start programa
 
/* expresion priorities and rules */
 
%%
 
programa: 
	declaraciones estructuras RETURN PCOMA funciones_opcional
;
/* declaraciones */
declaraciones: 
	declaraciones declaracion 
	| declaracion
;
 
declaracion: 
	tipo nombres PCOMA 
;
 
tipo: 
	INT 
	| CHAR 
	| FLOAT 
	| DOUBLE 
	| VOID
;
 
nombres: 
	nombres COMA variable
	| nombres COMA inicializacion
	| variable
	| inicializacion
;
 
variable: 
	ID		
	|apuntador ID 
	|ID arreglo
;
 
apuntador: 
	apuntador MULTIPLICACION 
	| MULTIPLICACION 
;
 
arreglo: 
	arreglo CORCHI expresion CORCHD 
	| CORCHI expresion CORCHD 
;

inicializacion:
	inicializar_variable 
	| inicializacion_arreglo
;

inicializar_variable:
	ID ASIGNACION constante
;

inicializacion_valor:
	constante
	| inicializacion_arreglo
;

inicializacion_arreglo:
	ID arreglo ASIGNACION CORCHI valores CORCHD
;

valores:
	valores COMA constante
	| constante
;
/* estructuras */
estructuras: 
	estructuras estructura 
	| estructura
;
 
estructura:
	if_estructura 
	| for_estructura 
	| while_estructura 
	| asign PCOMA
	| CONTINUE PCOMA 
	| BREAK PCOMA 
	| RETURN PCOMA
	| llamada_a_funcion PCOMA
	| ID INCREMENTO PCOMA
	| INCREMENTO ID PCOMA
;
 
if_estructura: 
	IF PARENI expresion PAREND final else_if else_opcional
	| IF PARENI expresion PAREND final else_opcional
;
 
else_if: 
	else_if ELSE IF PARENI expresion PAREND final 
	|ELSE IF PARENI expresion PAREND final  
	
; 

else_opcional:
	ELSE final
	| /*NADA*/
;
 
for_estructura: 
	FOR PARENI asign PCOMA expresion PCOMA expresion PAREND final 
;
 
while_estructura: 
	WHILE PARENI expresion PAREND final
;
 
final: 
	LLAVEI estructuras LLAVED 
;
 
expresion:
    expresion ADICION expresion 
	| expresion MULTIPLICACION expresion 
	| expresion DIVISION expresion 
	| expresion INCREMENTO 
	| INCREMENTO expresion 
	| expresion OR expresion 
	| expresion AND expresion 
	| NOT expresion 
	| expresion EQUIVALENTE expresion 
	| expresion RELACION expresion 
	| PARENI expresion PAREND 
	| referencia 
	| signo constante
	| llamada_a_funcion
;
 
signo: 
	ADICION { printf("%d\n", $1); } 
	| /* NADA */ ; 
 
constante: 
    CONSTANTEINT      	{ printf("%d\n", yylval.int_val); } 
    | CONSTANTEFLOAT    { printf("%.2f\n", yylval.double_val); } 
    | CONSTANTECHAR 
;
 
asign: 
	referencia ASIGNACION expresion 
; 
 
referencia: 
	variable 
	| REF variable 
;

llamada_a_funcion:
	ID PARENI llamada_a_parametros PAREND 
;

llamada_a_parametros:
	llamda_a_parametro
	| STRING 
	| /* NADA */
;

llamda_a_parametro:
	llamda_a_parametro COMA expresion
	| expresion
;
/* ----------------------Funciones ---------------------------------*/
funciones_opcional:
	funciones 
	| /* NADA */ 
;

funciones:
	funciones funcion
	| funcion 
;

funcion:
	funcion_inicio funcion_final 
;

funcion_inicio:
	tiporetorno ID PARENI parametro_opcional PAREND
;

tiporetorno: 
	tipo 
	| tipo apuntador
;

parametro_opcional:
	parametros
	| /* NADA */
;

parametros: 
	parametros COMA parametro
	| parametro
;

parametro:
	 tipo variable 
;

funcion_final:
	LLAVEI declaraciones_opcional estructuras_opcional return_opcional  LLAVED
;

declaraciones_opcional:
	declaraciones
	|
;

estructuras_opcional:
	estructuras
	|
;

return_opcional:
	RETURN expresion PCOMA
	|
;
	
%%
 
void yyerror ()
{
  fprintf(stderr, "Error de sintaxis línea: %d\n", linea);
  exit(1);
}
 
int main (int argc, char *argv[]){
 
	// initialize symbol table
	//init_hash_table();

	// initialize revisit queue
	//queue = NULL;
	
	// parsing
	int flag;
	yyin = fopen(argv[1], "r");
	yyparse();
	fclose(yyin);
	printf("Parsing finished!\n");
	
	//if(queue != NULL){
	//	printf("ADVERTENCIA: Algo no se ha verificado en la cola de revision!\n");
	//}

	// symbol table dump
	//yyout = fopen("symtab_dump.out", "w");
	//symtab_dump(yyout);
	//fclose(yyout);	
	
	// revisit queue dump
	//yyout = fopen("revisit_dump.out", "w");
	//revisit_dump(yyout);
	//fclose(yyout);

	return 0;
}