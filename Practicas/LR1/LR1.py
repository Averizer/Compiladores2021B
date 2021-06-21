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
with open('LR1.csv') as csv_file:
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
#---------------------CAlCULO DE PRIMEROS--------------------
"""for nt in noTerminales:
    llave = "p("+str(nt)+")"
    primeros[llave] = p(nt)

for t in terminales:
    print(t)
    llave = "p("+str(t)+")"
    primeros[llave] = p(t)
"""
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
                #if ev[indicePunto+1] in noTerminales:
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
            porCalcular.append(conjuntoNuevoApartirDeKernel)
            conjuntosFinales.append(conjuntoNuevoApartirDeKernel)
            trancision = list()
            trancision.append(conjuntosFinales.index(evaluandoEnAlgoritmo))
            trancision.append(l)
            trancision.append(kernels.index(kernelCalculado)+1)
            aSeparar.append(trancision)
        else:
            trancision = list()
            trancision.append(conjuntosFinales.index(evaluandoEnAlgoritmo))
            trancision.append(l)
            trancision.append(kernels.index(kernelCalculado)+1)
            aSeparar.append(trancision)
    if(len(porCalcular)== 0):
        banderaFinalizacion = False


print("Terminales ", terminales)
print("No terminales ", noTerminales)
print("Producciones ", producciones)
print("Primeros ", primeros)

print("\nKernels \n",kernels)
print("\nConjuntos Finales \n",conjuntosFinales)
print("")
tabla = list()
reduceAAgregar = list()
terminales.extend(noTerminales)

print("")
print("Conjuntos finales",len(conjuntosFinales))
print("Kernels ", len(kernels))
for t in terminales:
    llave = "p("+str(t)+")"
    primeros[llave] = p(t)

# ---------------------Creacion de la tabla, con shift e irA
for c in range(0, len(conjuntosFinales)):
    reduceAAgregar.clear()
    row = list = ["-" for i in range(len(terminales))]
    for k in kernels[c]:
        puntoEnKernel = k.index('.')
        if puntoEnKernel == (len(k)-2):
            print("")
            reduceAAgregar.extend(primeros["p("+str(k[-1])+")"])
            for reduceA in reduceAAgregar:
                if c == 1:
                    row[terminales.index(reduceA)] = "ACC"
                else:
                    row[terminales.index(reduceA)] = "r"+str(k[-1])

    for sh in aSeparar:
        if sh[0] == c:
            if sh[1] in noTerminales:
                row[terminales.index(sh[1])] = str(sh[2])
            else:
                row[terminales.index(sh[1])] = "s"+str(sh[2])
    tabla.append(row)

print("\n aSeparar \n",aSeparar)
print(tabulate(tabla, terminales, tablefmt="grid"))