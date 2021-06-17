import csv
noTerminales = list()
terminales = list()
producciones = list()
conjuntosFinales = list()
producciones_con_puntos = list()
kernels = list()
trancicionesAAgregar = list()

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
            trancicionesAAgregar.append(trancision)
        else:
            trancision = list()
            trancision.append(conjuntosFinales.index(evaluandoEnAlgoritmo))
            trancision.append(l)
            trancision.append(kernels.index(kernelCalculado)+1)
            trancicionesAAgregar.append(trancision)
    if(len(porCalcular)== 0):
        banderaFinalizacion = False

print("SHIFTS")
for i in trancicionesAAgregar:
    print(i)
    