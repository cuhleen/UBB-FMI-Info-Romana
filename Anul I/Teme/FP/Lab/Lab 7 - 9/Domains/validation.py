from Domains.carte import Carte
from Domains.client import Client
from Domains.inchiriere import Inchiriere



class validatorCarte:
    def validate(self, carte:Carte):
        """
         Validează o carte dată
         :param carte: cartea de validat
         :return: -
         :raises: ValueError cu mesajele de eroare dacă cartea nu este validă
         """
        errors = []

        if carte.getId() != int(carte.getId()) or int(carte.getId()) < 0:
            errors.append("ID-ul cărții trebuie să fie un număr natural pozitiv")

        if len(carte.getTitlu()) < 1:
            errors.append("Titlul cărții trebuie să aiba cel puțin un caracter")
        if len(carte.getDescriere()) < 1:
            errors.append("Descrierea cărții trebuie să aiba cel puțin un caracter")
        if len(carte.getAutor()) < 1:
            errors.append("Autorul cărții trebuie să aiba cel puțin un caracter")

        if len(errors) > 0:
            error_message = '\n'.join(errors)
            raise ValueError(error_message)



class validatorClient:
    def validate(self, client:Client):
        """
         Validează un client dat
         :param client: clientul de validat
         :return: -
         :raises: ValueError cu mesajele de eroare dacă clientul nu este valid
         """
        errors = []

        if client.getId() != int(client.getId()) or int(client.getId()) < 0:
            errors.append("ID-ul clientului trebuie să fie un număr natural pozitiv")

        nume = client.getNume()
        if len(nume) < 3 or ' ' not in nume:
            errors.append("Clientul trebuie să aiba cel puțin un prenume și un nume")
        cnp = client.getCnp()
        if len(str(cnp)) != 13 or isinstance(cnp, int) is False:
            errors.append("CNP-ul clientului trebuie să fie un număr format din 13 cifre")

        if len(errors) > 0:
            errorMessage = '\n'.join(errors)
            raise ValueError(errorMessage)