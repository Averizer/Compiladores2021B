# Proyecto (Compilador C -> DEX)

Este proyecto toma como referencia principal, el proyecto de un usuario llamado drifter1, en la plataforma steemit, donde tiene una serie de tutoriales estilo curso, donde explica paso a paso los componentes de un compilador y la programación con flex, bison y C. A modo de repaso y ayuda en el desarrollo del proyecto, lo tomé como guía para mi propio proyecto.

> Versiones -> 
> BISON v3.4,
> FLEX v2.6.4


## Archivo make

Cree un archivo make, para facilitar la compilación del programa, a continuación, los tres comandos más utiles del archivo.

```bash
make all   //Compila todo el programa
make run   //Corre el analaizador léxico y sintáctico
make clean //Limpia todos los archivos resultantes de las compilaciones
```

## Ultima update del proyecto

Cosas realizadas:

> Declaraciones, con sus respectivos tipos, apuntadores, regerencias, estructura de for, if, else, else if, while y return. Archivo de operadores para revisar los tipos de datos con los que se está trabajando y los esperados a la salida.


Cosas por revisar:

> Funciones, crash al momento de hacer return.

# Para las prácticas 

## Construcciones de thompson

Ocupa una pila de compilación para saber el orden de las operaciones, se van creando subautoamatas que se van juntando cuando hay operaciones, dependiendo de la operación es como se van uniendo, conservando las reglas de las construcciones de thomson.<br />

La entrada es escrita en consola, y la salida se escribe sobre un archivo llamado AFND.csv (cambiar la ubicación del archivo al compilar), la salida de este programa, es la entrada para el siguiente algoritmo (algoritmo de los subconjuntos).<br />
Entrada <br />
![image](https://user-images.githubusercontent.com/36316073/122655655-aeaea200-d119-11eb-8cb9-db78dd40ebd6.png)
<br />Salida<br />
![image](https://user-images.githubusercontent.com/36316073/122655675-cede6100-d119-11eb-90b3-081c51361e19.png)

## Algoritmo de los subconjutos

Este algoritmo está probado con el primer ejercicio usando como entrada el automata creado con la expresión regular (a|bc) * a, el cual fué resuelto en clase, la salida coincide con toda la resolución de clase, la salida de este algoritmo es una archivo llamado AFD.csv (cambiar la ubicación del archivo al compilar). Este archivo de salida es el archivo de entrada para el algoritmo de minimización de automata.<br />

Entrada (Salida de Thompson)<br />
![image](https://user-images.githubusercontent.com/36316073/122655675-cede6100-d119-11eb-90b3-081c51361e19.png)
<br />Salida<br />
![image](https://user-images.githubusercontent.com/36316073/122655713-2e3c7100-d11a-11eb-8f50-d8af1b645f19.png)
<br />
## Algoritmo de minimización

Algoritmo de minimización, este algoritmo está probado con el ejercicio de clase, especificando en el archivo aMinimizar.csv, alfabeto, terminal, inicial, no terminales, y las trancisiones, el ejercicio muestra todos los pasos e intrucciones para redibujar el nuevo automata.
<br />Entrada<br />
![image](https://user-images.githubusercontent.com/36316073/122656140-993b7700-d11d-11eb-9907-fcb869ad3d2d.png)
<br />Salida<br />
![image](https://user-images.githubusercontent.com/36316073/122655917-e585b780-d11b-11eb-8321-8adc00a52145.png)

## ASDR

Este algoritmo es la implementación el problema planteado y resuelto en clase.
<br />Programa<br />
![image](https://user-images.githubusercontent.com/36316073/122655943-21b91800-d11c-11eb-9dcd-8321f2f6d982.png)


## LL1

El algoritmo carga los datos de un archivo llamado LL1.csv, el archivo contiene separados, los no terminales, los terminales, y las producciones, el programa muestra todas las produccioens que se van creando durante la ejecución del algoritmo, este programa se puede mejorar agregando a una tabla hash cada calculo con su resultado para no tener que calcular de nuevo, no está implementada esa parte, pero si la de gaurdar en la tabla hash, finalmente se muestra una tabla con el nuemero de producción y con que terminal es activada esa producción, tal cual fue realizado el ejercicio en clase. Algoritmo probado con el ejercicio en clase.
<br />Entrada<br />
![image](https://user-images.githubusercontent.com/36316073/122655955-41e8d700-d11c-11eb-98af-c00655d87674.png)
<br />Iteraciones<br />
![image](https://user-images.githubusercontent.com/36316073/122656027-d5220c80-d11c-11eb-9175-66591b145aff.png)
<br />Salida (Tabla LL1)<br />
![image](https://user-images.githubusercontent.com/36316073/122656043-ee2abd80-d11c-11eb-8c66-a322ad9809e0.png)




## LL0

El algoritmo lee los terminales, no terminales y producciones desde el archivo llamado LR0.csv, el programa muestra directamente la tabla LR0, las variables fueron separadas de tal manera que se puede mostrar las producciones, los conjuntos resultante de la ejecución del algoritmo y demás. Se ocupan los conceptos de primero, siguiente, cerradura y mover. 
Para mostrar la tabla hice uso de la librería Tabulate, obtenida de https://pypi.org/project/tabulate/ . <br />
Entrada <br />
![image](https://user-images.githubusercontent.com/36316073/122655623-58d9fa00-d119-11eb-9d25-dc491d46b4dc.png)
<br />
Salida <br />
![image](https://user-images.githubusercontent.com/36316073/122655408-ab1a1b80-d117-11eb-9fbe-0c87aee6b89c.png)
