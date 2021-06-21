prueba = ['Z', '.', 'S', '$']

print(len(prueba)-1)
print(prueba.index('.'))
prueba.remove(len(prueba)-1)
print(len(prueba))


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
