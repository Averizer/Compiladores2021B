all: 
	bison -d proyecto.y
	flex proyecto.l
	gcc -c proyecto.tab.c 
	gcc -c lex.yy.c
	gcc lex.yy.o  proyecto.tab.o  -lfl

bison:	proyecto.y
	bison -d proyecto.y

flex: proyecto.l 
	flex proyecto.l

c: lex.yy.c proyecto.tab.c
	gcc -c proyecto.tab.c 
	gcc -c lex.yy.c

juntar: proyecto.tab.o lex.yy.o 
	gcc lex.yy.o  proyecto.tab.o  -lfl

clean:
	rm a.out proyecto.tab.c lex.yy.c proyecto.tab.h lex.yy.o proyecto.tab.o

tabla:
	gcc -o tabla.out tablaHash.c

runtabla:
	./tabla.out

run: a.out
	./a.out prueba.txt