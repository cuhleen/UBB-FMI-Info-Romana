import datetime

errors = []

def validarePachet(dataInceput, dataSfarsit, destinatia, pret):
    global errors
    errors = []
    validareDataInceputFinal(dataInceput, dataSfarsit)
    validareDestinatie(destinatia)
    validarePret(pret)

    if len(errors) > 0:
        error_message = '\n'.join(errors)
        raise ValueError(error_message)

def validarePret(pret):
    if not pret.isdigit() or int(pret) <= 0:
        errors.append("Prețul trebuie să fie un număr întreg pozitiv.")
    
def validareDestinatie(destinatie):
    if not destinatie:
        errors.append("Destinația nu poate fi goală.")

def validareDataInceputFinal(dataInceput, dataSfarsit):
    try:
        dataInceput = datetime.datetime.strptime(dataInceput, '%Y/%m/%d')
    except:
        errors.append("Data de început nu este validă.")
    try:
        dataSfarsit = datetime.datetime.strptime(dataSfarsit, '%Y/%m/%d')
    except:
        errors.append("Data de sfârșit nu este validă.")
    if type(dataInceput) is not str:
        if type(dataSfarsit) is not str:
            if dataInceput >= dataSfarsit:
                errors.append("Data de început trebuie să fie înaintea datei de sfârșit.")