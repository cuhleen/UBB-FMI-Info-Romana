from Domains.client import *
from Domains.validation import validatorClient

import unittest

from Domains.client import *
from Domains.validation import validatorClient

class TestClient(unittest.TestCase):
    def setUp(self) -> None:
        pass

    def testClient(self):
        c = Client(1, "Nume Prenume 1", 1111111111111)
        self.assertEqual(c.getId(), 1)
        self.assertEqual(c.getNume(), "Nume Prenume 1")
        self.assertEqual(c.getCNP(), 1111111111111)

        c.setNume("Nume Prenume 1 Editat")
        self.assertEqual(c.getNume(), "Nume Prenume 1 Editat")
        c.setCNP("Descriere 1 Editata")
        self.assertEqual(c.getDescriere(), "Descriere 1 Editata")

    def testEgalClient(self):
        c1 = Client(1, "Nume Prenume 1", 1111111111111)
        c2 = Client(1, "Nume Prenume 2", 2222222222222)
        self.assertEqual(c1, c2)

        c3 = Client(3, "Nume Prenume 1", 1111111111111)
        self.assertNotEqual(c1, c3)

    def testValidClient(self):
        validator = validatorClient()
        c1 = Client(1, "Nume Prenume 1", 1111111111111)
        rez = validator.validate(c1)
        self.assertIsNone(rez)

        c2 = Client(-2, "Nume Prenume 2", 1111111111111)
        self.assertRaises(ValueError, validator.validate, c2)

        c3 = Client(3, "", 1111111111111)
        self.assertRaises(ValueError, validator.validate, c3)

        c4 = Client(4, "Nume Prenume 4", 111)
        self.assertRaises(ValueError, validator.validate, c4)

    def tearDown(self) -> None:
        pass

if __name__ == "__main__":
    unittest.main()

"""
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

"""