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
siguientes = {}
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

def s(simb):
    res = list()
    if simb == producciones[0][0]:
        res.append("$")
    for i in range(0,len(producciones)):
        parteDerecha = producciones[i][1:len(producciones)]
        if simb in parteDerecha:
            indiceDerecha = parteDerecha.index(simb)
            if indiceDerecha == len(producciones[i])-2: #Esta al final]
                res.extend(s(producciones[i][0]))
            else:
                aEvaluarPrimero = parteDerecha[parteDerecha.index(simb)+1]
                res.extend((aEvaluarPrimero))
   
    res = list(dict.fromkeys(res))
    return res
    
def cerradura(conjuntoAHacerCerradura):
    global producciones_con_puntos
    global noTerminales
    faltantes = []
    faltantes.append(conjuntoAHacerCerradura)
    resultado = list()
    resultado.append(conjuntoAHacerCerradura)

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
                    if (producciones_con_puntos[i][0] == buscando):
                            if(producciones_con_puntos[i] not in resultado):
                                faltantes.append(producciones_con_puntos[i])
                                resultadoActual.append(producciones_con_puntos[i])
                    
                resultado.extend(resultadoActual)
        if len(faltantes) == 0:
            bandera = False
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
    
with open('LR0.csv') as csv_file:
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


#---------------------PASO 1 Extender Gramática--------------------
aux = ['Z']
aux.append(producciones[0][0])
producciones.insert(0,aux)

#---------------------PASO 2 --------------------------------------
#   +++++ Cerradura 0 +++++
for p in producciones:
    auxParaPuntos = list(p)
    auxParaPuntos.insert(1,'.')
    producciones_con_puntos.append(auxParaPuntos)

porCalcular = []
auxParaPrimeraCerradura = cerradura(producciones_con_puntos[0])
conjuntosFinales.append(auxParaPrimeraCerradura)
porCalcular.append(auxParaPrimeraCerradura)
banderaFinalizacion = True

while(banderaFinalizacion):
    evaluandoEnAlgoritmo = porCalcular.pop(0)
    letrasAMover = list()

    for ev in evaluandoEnAlgoritmo:
        indicePunto = ev.index('.')
        if indicePunto != len(ev)-1:
            if ev[indicePunto+1] not in letrasAMover:
                letrasAMover.append(ev[indicePunto+1]) 

    for l in letrasAMover:
        kernelCalculado = list(mover(evaluandoEnAlgoritmo, l))
        if kernelCalculado not in kernels:
            trancision = list()
            kernels.append(kernelCalculado)
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


for p in producciones:
    llave = "s("+str(p[0])+")"
    siguientes[llave] = s(p[0])


terminales.append("$")
terminales.extend(noTerminales)
tabla = list()
for p in producciones:
    p.append(".")
    
print("")
reduceAAgregar = list()
# ---------------------Creacion de la tabla, con shift e irA
for c in range(0, len(conjuntosFinales)):
    reduceAAgregar.clear()
    row = list = ["-" for i in range(len(terminales))]
    for p in producciones:
        if p in conjuntosFinales[c]:
            print("")
            reduceAAgregar.extend(siguientes["s("+p[0]+")"])
            for reduceA in reduceAAgregar:
                if producciones.index(p) == 0:
                    row[terminales.index(reduceA)] = "ACC"
                else:
                    row[terminales.index(reduceA)] = "r"+str(producciones.index(p))

    for sh in aSeparar:
        if sh[0] == c:
            if sh[1] in noTerminales:
                row[terminales.index(sh[1])] = str(sh[2])
            else:
                row[terminales.index(sh[1])] = "s"+str(sh[2])
    tabla.append(row)
print("")
print("Conjuntos finales",len(conjuntosFinales))
print("Kernels ", len(kernels))

print(tabulate(tabla, terminales, tablefmt="grid"))

