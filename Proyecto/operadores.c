#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "operadores.h"



int get_tipo_resultado(int tipo1, int tipo2, int tipo_op){ /* Checa el tipo y el tipo de resultado*/
	switch(tipo_op){
		case NONE: /* tipo de compatibilidad, solo 1 tipo */
			// first type INT
			if(tipo1 == TIPO_INT){
				// El tipo int puede ser, entero o un caracter (cada caracter tiene un numero)
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return 1;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			// Tipo real
			else if(tipo1 == TIPO_REAL){
				// el tipo real, puede ser un int, char o float
				if(tipo2 == TIPO_INT || tipo2 == TIPO_REAL || tipo2 == TIPO_CHAR){
					return 1;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			// Tipo CHAR
			else if(tipo1 == TIPO_CHAR){
				// El char puede ser un caracter o un numero
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return 1;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			break;
		/* ---------------------------------------------------------- */
		case OP_ARIT: /* Compatibilidad de Operadores aritmeticos */
			// tipo INT
			if(tipo1 == TIPO_INT){
				// int puede operar con int, o char si el return es int
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return TIPO_INT;
				}
				//  tipo int puede operar con tipo real, si el return es real
				else if(tipo2 == TIPO_REAL){
					return TIPO_REAL;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			// tipo REAL
			else if(tipo1 == TIPO_REAL){
				// tipo real, opera con int, real y char, si el return es real
				if(tipo2 == TIPO_INT || tipo2 == TIPO_REAL || tipo2 == TIPO_CHAR){
					return TIPO_REAL;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			// tipo CHAR
			else if(tipo1 == TIPO_CHAR){
				// tipo char opera con int y char, si el return es char
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return TIPO_CHAR;
				}
				// tipo char opera con real, si el return es real
				else if(tipo2 == TIPO_REAL){
					return TIPO_REAL;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			else{
				tipo_error(tipo1, tipo2, tipo_op);
			}
			break;
		/* ---------------------------------------------------------- */
		case OP_INCR: /* Operaciones de incremento */
			// tipo in, regresa int
			if(tipo1 == TIPO_INT){
				return TIPO_INT;
			}
			// tipo REAL, regresa real
			else if(tipo1 == TIPO_REAL){
				return TIPO_REAL;
			}
			// tipo CHAR regresa char
			else if(tipo1 == TIPO_CHAR){
				return TIPO_CHAR;
			}
			else{
				tipo_error(tipo1, tipo2, tipo_op);
			}
			break;
		/* ---------------------------------------------------------- */
		case OP_BOOL: /* Operador binario */
			// tipo INT
			if(tipo1 == TIPO_INT){
				// tipo int, opera con int y char, si retorno es char
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return TIPO_INT;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			// tipo CHAR
			else if(tipo1 == TIPO_CHAR){
				// tipo char, opera con int y con char, si return es char
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return TIPO_CHAR;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			else{
				tipo_error(tipo1, tipo2, tipo_op);
			}
			break;
		/* ---------------------------------------------------------- */
		case OP_NOT: /* Operacion NOT */
			// tipo INT, retunr int
			if(tipo1 == TIPO_INT){
				return TIPO_INT;
			}
			// tipo char, retorna char
			else if(tipo1 == TIPO_CHAR){
				return TIPO_INT;
			}
			else{
				tipo_error(tipo1, tipo2, tipo_op);
			}
			break;
		/* ---------------------------------------------------------- */
		case OP_REL: /* Operadores relacionales */
			// tipo INT
			if(tipo1 == TIPO_INT){
				// opera con, int, real, char
				if(tipo2 == TIPO_INT || tipo2 == TIPO_REAL || tipo2 == TIPO_CHAR){
					return TIPO_INT;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			else if(tipo1 == TIPO_REAL){
				// real, opera con int, real, char, retorna un int
				if(tipo2 == TIPO_INT || tipo2 == TIPO_REAL || tipo2 == TIPO_CHAR){
					return TIPO_INT;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			// tipo CHAR
			else if(tipo1 == TIPO_CHAR){
				// opera con int, real, char, retorna int
				if(tipo2 == TIPO_INT || tipo2 == TIPO_REAL || tipo2 == TIPO_CHAR){
					return TIPO_INT;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			else{
				tipo_error(tipo1, tipo2, tipo_op);
			}
			break;
		/* ---------------------------------------------------------- */
		case OP_EQUI: /* Operadores de igualdad */
			// tipo INT
			if(tipo1 == TIPO_INT){
				// opera con int, char, retorna int
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return TIPO_INT;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			//tipo REAL
			else if(tipo1 == TIPO_REAL){
				// opera con tipo real, retorna un int
				if(tipo2 == TIPO_REAL){
					return TIPO_INT;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			// tipo CHAR
			else if(tipo1 == TIPO_CHAR){
				// opera con int, char, retorna int
				if(tipo2 == TIPO_INT || tipo2 == TIPO_CHAR){
					return TIPO_INT;
				}
				else{
					tipo_error(tipo1, tipo2, tipo_op);
				}
			}
			else{
				tipo_error(tipo1, tipo2, tipo_op);
			}
			break;
		/* ---------------------------------------------------------- */
		default: /* Eleccion de operador incorrecta */
			fprintf(stderr, "Error en los operadores!\n");
			exit(1);
	}
}

void tipo_error(int tipo1, int tipo2, int tipo_op){ 
	fprintf(stderr, "Conflicto en el tipo %d y %d usando el tipo de resultado %d\n", tipo1, tipo2, tipo_op);
	exit(1);
}