"""
    Cadenas probadas
    
    Aceptadas
        aaa
        abaaaba
        ababaaababa
        ababababaaabababa
    No aceptadas
        aba
        abbba
        aca
        a
"""
entrada = "aaaa"
i = 0

def consumir(x):
    global i
    if (entrada[i] == x):
        i += 1
    else:
        print(entrada, " NO PERTENECE A L(G)")
        exit(1)

def A():
    consumir("a")
    B()
    consumir("a")

def B():
    global i
    if (entrada[i] == "b"):
        consumir("b")
        A()
        consumir("b")
    elif (entrada[i] == "a"):
        consumir("a")
    else:
        print(entrada, " NO pertenece a L(G)")
        exit(1)

A()
print(entrada, "Si pertenece a L(G)")
        