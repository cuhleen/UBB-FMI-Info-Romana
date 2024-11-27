from Domains.client import *
from Domains.validation import validatorClient



def testClient():
    client1 = Client(1, "Nume Prenume 1", 1111111111111)
    assert(client1.getId() == 1)
    assert(client1.getNume() == "Nume Prenume 1")
    assert(client1.getCnp() == 1111111111111)

    client1.setId(2)
    assert(client1.getId() == 2)

    client1.setNume("Nume Prenume 2")
    assert(client1.getNume() == "Nume Prenume 2")

    client1.setCnp(2222222222222)
    assert(client1.getCnp() == 2222222222222)



def testValidareClient():
    validator = validatorClient()

    # ID-ul trebuie să fie un număr natural pozitiv
    client1 = Client(-1, "Nume Prenume 1", 1111111111111)
    try:
        validator.validate(client1)
        assert False
    except ValueError:
        assert True

    # Numele trebuie să aibă cel puțin un caracter de spațiu în nume
    client2 = Client(2, "Nume", 2222222222222)
    try:
        validator.validate(client2)
        assert False
    except ValueError:
        assert True

    # CNP-ul trebuie să fie un număr format din 13 caractere
    client3 = Client(3, "Nume Prenume", 333)
    try:
        validator.validate(client3)
        assert False
    except ValueError:
        assert True

    # Client invalid din mai multe puncte de vedere
    client5 = Client(5, "NumePrenume5", 555)
    try:
        validator.validate(client5)
        assert False
    except ValueError:
        assert True



def testEgalClient():
    client1 = Client(12, "Nume Prenume 12", 1212121212121)
    client2 = Client(12, "Nume Prenume 12", 1212121212121)

    assert (client1 == client2)

    client3 = Client(3, "Nume Prenume 3", 3333333333333)

    assert (client1 != client3)