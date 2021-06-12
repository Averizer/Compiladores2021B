/* A Bison parser, made by GNU Bison 3.4.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2019 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* Undocumented macros, especially those whose name start with YY_,
   are private implementation details.  Do not rely on them.  */

#ifndef YY_YY_PROYECTO_TAB_H_INCLUDED
# define YY_YY_PROYECTO_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token type.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    CHAR = 258,
    INT = 259,
    FLOAT = 260,
    DOUBLE = 261,
    IF = 262,
    ELSE = 263,
    WHILE = 264,
    FOR = 265,
    CONTINUE = 266,
    BREAK = 267,
    VOID = 268,
    RETURN = 269,
    ADICION = 270,
    MULTIPLICACION = 271,
    DIVISION = 272,
    INCREMENTO = 273,
    OR = 274,
    AND = 275,
    NOT = 276,
    EQUIVALENTE = 277,
    RELACION = 278,
    PARENI = 279,
    PAREND = 280,
    CORCHD = 281,
    CORCHI = 282,
    LLAVEI = 283,
    LLAVED = 284,
    PCOMA = 285,
    PUNTO = 286,
    COMA = 287,
    ASIGNACION = 288,
    REF = 289,
    ID = 290,
    CONSTANTEINT = 291,
    CONSTANTEFLOAT = 292,
    CONSTANTECHAR = 293,
    STRING = 294
  };
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
union YYSTYPE
{
#line 16 "proyecto.y"

    char char_val;
	int int_val;
	double double_val;
    char* str_val;
	//list_t* symtab_item;

#line 105 "proyecto.tab.h"

};
typedef union YYSTYPE YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;

int yyparse (void);

#endif /* !YY_YY_PROYECTO_TAB_H_INCLUDED  */
