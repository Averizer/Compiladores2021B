
/* tipos de tokens */
#define UNDEF 0
#define TIPO_INT 1
#define TIPO_REAL 2
#define TIPO_CHAR 3
#define TIPO_ARREGLO 4
#define TIPO_APUNTADOR 5
#define TIPO_FUNCION 6

/* tipos de operador */
#define NONE 0		// asignaciones, parametros
#define OP_ARIT 1 // ADDOP, MULOP, DIVOP (+, -, *, /)
#define OP_INCR 2   // INCR (++, --)
#define OP_BOOL 3   // OROP, ANDOP (||, &&)
#define OP_NOT  4    // NOTOP (!)
#define OP_REL  5    // RELOP (>, <, >=, <=)
#define OP_EQUI 6    // EQUOP (==, !=)

// Declaracion de funciones
int get_tipo_resultado (int tipo1, int tipo2, int tipo_op); /* checa el tipo y resultado */
void tipo_error (int tipo1, int tipo2, int tipo_op);      /* imprimir el error */