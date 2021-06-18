all: 
	bison -d calculadora.y
	flex calculadora.l
	gcc -c calculadora.tab.c 
	gcc -c lex.yy.c
	gcc lex.yy.o  calculadora.tab.o  -lfl

bison:	calculadora.y
	bison -d calculadora.y

flex: calculadora.l 
	flex calculadora.l

c: lex.yy.c calculadora.tab.c
	gcc -c calculadora.tab.c 
	gcc -c lex.yy.c

juntar: calculadora.tab.o lex.yy.o 
	gcc lex.yy.o  calculadora.tab.o  -lfl

clean:
	rm a.out calculadora.tab.c lex.yy.c calculadora.tab.h lex.yy.o calculadora.tab.o

run: a.out
	./a.out