import csv
import sys
from tabulate import tabulate
noTerminales = list()
terminales = list()
producciones = list()
conjuntosFinales = list()
producciones_con_puntos = list()
aSeparar = list()
kernels = list()
shift = list()
primeros = {}
irA = list()

def p(simb):
    res = list()
    if simb in terminales:
        res.append(simb)
    elif simb in noTerminales:
        noTerminalesAEvaluar = list()
        for i in range(0,len(producciones)):
            if producciones[i][0] == simb:
                if producciones[i][1]  != simb:
                    if producciones[i][1] in noTerminales:
                        noTerminalesAEvaluar.append(producciones[i][1])
                    else:
                        res.append(producciones[i][1])
                
        for n in noTerminalesAEvaluar:
            res.extend(p(n))
    return res 


#---------------------LEER ARCHIVO, CARGAR DATOS-------------
with open('LALR.csv') as csv_file:
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            
            for r in row:
                noTerminales.append(r.lstrip())
            line_count += 1
        elif line_count == 1:
            for r in row:
                terminales.append(r.lstrip())
            line_count += 1
        else:
            auxproduccion = list()
            for r in row:
                auxproduccion.append(r.lstrip())
            producciones.append(auxproduccion)

def extenderPrimeros(listaPrimeros, donde):
    extendidos = list()
    for r in listaPrimeros:
        auxEx = donde
        auxEx.append(r)
        extendidos.append(auxEx)
    return extendidos

def cerradura(conjuntoAHacerCerradura):
    global producciones_con_puntos
    global noTerminales
    faltantes = []
    faltantes.append(conjuntoAHacerCerradura)
    resultado = list()
    resultado.append(conjuntoAHacerCerradura)
    cont = 0
    bandera = True
    
    while(bandera):
        
        actual = list(faltantes.pop(0))
        indicePunto = 0
        indicePunto = actual.index('.')
        if indicePunto != len(actual)-1: 
            if actual[indicePunto+1].lstrip() in noTerminales: #Verificar si hay un no terminal despues del punto
                resultadoActual=list()                #Donde se guardara los conjuntos que faltan por agregar
                buscando = actual[indicePunto+1]
                for i in range(0, len(producciones_con_puntos)):    #Para todas las producciones con puntos 
                    primerosCerradura = list()
                    if (producciones_con_puntos[i][0] == buscando):
                        primerosCerradura = p(actual[indicePunto+2])
                        for pC in primerosCerradura:
                            aux = list(producciones_con_puntos[i])
                            aux.append(pC)
                            if(aux not in resultado):
                                faltantes.append(aux)
                                resultadoActual.append(aux)
                    
                resultado.extend(resultadoActual)
        if len(faltantes) == 0:
            bandera = False
        cont = cont +1
    return resultado

def mover (aMover, letra):
    resultadoDeMover = list()
    buscadoEnMover = "'.', '"+str(letra)+"'"
    for enAMover in aMover:
        if buscadoEnMover in str(enAMover):
            aux = list(enAMover)
            indice = aux.index('.')
            del(aux[indice])
            aux.insert(indice+1,".")
            resultadoDeMover.append(aux)
    
    return resultadoDeMover
  
#---------------------PASO 1 Extender Gramática--------------
aux = ['Z']
aux.append(producciones[0][0])
producciones.insert(0,aux)
noTerminales.append("Z")
terminales.append("$")

#---------------------PASO 2 ---------------------------------
#   +++++ Cerradura 0 +++++
for pr in producciones:
    auxParaPuntos = list(pr)
    auxParaPuntos.insert(1,'.')
    producciones_con_puntos.append(auxParaPuntos)

porCalcular = []
aux = producciones_con_puntos[0]
aux.append("$")
auxParaPrimeraCerradura = cerradura(aux)
print("Primera cerradura ", auxParaPrimeraCerradura)
conjuntosFinales.append(auxParaPrimeraCerradura)
porCalcular.append(auxParaPrimeraCerradura)
kernels.append(auxParaPrimeraCerradura)
banderaFinalizacion = True

while(banderaFinalizacion):
    evaluandoEnAlgoritmo = porCalcular.pop(0)
    letrasAMover = list()

    for ev in evaluandoEnAlgoritmo:
        indicePunto = ev.index('.')
        if indicePunto != len(ev)-2:
            if ev[indicePunto+1] not in letrasAMover:
                letrasAMover.append(ev[indicePunto+1]) 

    for l in letrasAMover:
        kernelCalculado = list(mover(evaluandoEnAlgoritmo, l))
        if kernelCalculado not in kernels:
            trancision = list()
            kernels.append(kernelCalculado)
            print("Nuevo kernel ", kernelCalculado)
            conjuntoNuevoApartirDeKernel = list()
            for k in kernelCalculado: #Para cada elemento del kernel calculado 
                conjuntoNuevoApartirDeKernel.extend(cerradura(k))
                if k.index(".") == len(k)-2:
                    print("Kernel con punto al final")
                    aux= list(k)
                    charFinal = k[-1]
                    indiceDeProduccion = producciones.index(aux[:-2])
                    trancision = list()
                    trancision.append(-1*(kernels.index(kernelCalculado)))
                    trancision.append(charFinal)
                    trancision.append(indiceDeProduccion)
                    aSeparar.append(trancision)
            porCalcular.append(conjuntoNuevoApartirDeKernel)
            conjuntosFinales.append(conjuntoNuevoApartirDeKernel)
            trancision = list()
            trancision.append(conjuntosFinales.index(evaluandoEnAlgoritmo))
            trancision.append(l)
            trancision.append(kernels.index(kernelCalculado))
            aSeparar.append(trancision)
        else:
            trancision = list()
            trancision.append(conjuntosFinales.index(evaluandoEnAlgoritmo))
            trancision.append(l)
            trancision.append(kernels.index(kernelCalculado))
            aSeparar.append(trancision)
    if(len(porCalcular)== 0):
        banderaFinalizacion = False



print("")
tabla = list()
reduceAAgregar = list()
terminales.extend(noTerminales)

print("")

for t in terminales:
    llave = "p("+str(t)+")"
    primeros[llave] = p(t)

# ---------------------Creacion de la tabla, con shift e irA
for c in range(0, len(conjuntosFinales)):
    reduceAAgregar.clear()
    row = list = ["-" for i in range(len(terminales))]

    for sh in aSeparar:
        if sh[0] == c:
            if sh[1] in noTerminales:
                row[terminales.index(sh[1])] = str(sh[2])
            else:
                row[terminales.index(sh[1])] = "s"+str(sh[2])
        if sh[0] < 0:
            if abs(sh[0]) == c:
                if sh[2] == 0:
                    row[terminales.index(sh[1])] = "ACC"
                else:
                    row[terminales.index(sh[1])] = "r"+str(sh[2])
    tabla.append(row)

#print("\n aSeparar \n",aSeparar)

# ---------------------PASO 4 ver kernels similares
nuevosEstados = []
for i in range(1, len(kernels)-1):
    estadoAgrupado = []
    kernelActual = []
    kernelActual.extend(kernels[i])
    for j in range(i+1, len(kernels)):
        kernelComparando = []
        kernelComparando.extend(kernels[j])
        control = False
        for k1 in kernelActual:
            for k2 in kernelComparando:
                if k1[:-1] == k2[:-1]:
                    control = True
                else:
                    control = False
        if control:
            nuevoEstado = []
            nuevoEstado.append(i)
            nuevoEstado.append(j)
            print("Nuevo estado ", nuevoEstado)
            nuevosEstados.append(nuevoEstado)

# ---------------------PASO 5 Compactar tabla

for numeros in nuevosEstados:
    fila1 = tabla[numeros[0]]
    fila2 = tabla[numeros[1]]
    for x in range(0, len(fila1)):
        if (fila1[x] =="-" and fila2[x] != "-") :
            fila1[x] = fila2[x]
        elif(fila2[x] != "-" and fila1[x] != "-"):
            if (fila2[x][0] != "r") and (fila2[x][0] != "s"):
                fila1[x] = fila1[x] + "," + fila2[x]
            else: 
                fila1[x] = fila1[x] + "," + fila2[x][1:]
for t in tabla:
    print(t)
estadosAQuitar = []
for numeros in nuevosEstados:    
    estadosAQuitar.append(numeros[1])
estadosAQuitar.sort(reverse=True)
for x in estadosAQuitar:
    del tabla[x]
print(estadosAQuitar)

for i in range(0, len(tabla)):
    for j in range(0, len(tabla[i])):
        if len(tabla[i][j]) == 2:
            ev = int(tabla[i][j][1])
            for numeros in nuevosEstados:
                if ev in numeros:
                    tabla[i][j] = tabla[i][j][-2]
                    tabla[i][j] = tabla[i][j] + str(numeros)
        if len(tabla[i][j]) == 1:
            if tabla[i][j] != '-':
                ev = int(tabla[i][j])
                for numeros in nuevosEstados:
                    if ev in numeros:
                        tabla[i][j] = tabla[i][j][-1]
                        tabla[i][j] = tabla[i][j] + str(numeros)


print(tabulate(tabla, terminales, tablefmt="grid"))