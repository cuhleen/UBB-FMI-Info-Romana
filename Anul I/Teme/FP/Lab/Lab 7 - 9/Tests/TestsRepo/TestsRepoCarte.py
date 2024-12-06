import unittest

from Domains.carte import Carte
from Repository.repository import RepositoryCarti
from Repository.repository import RepositoryCartiFile

class TestRepoCarte(unittest.TestCase):
    def setUp(self)->None:
        print("setUp called")
        self.test_repo = RepositoryCarti()

    def testStore(self):
        print("testStore")
        self.assertEqual(len(self.test_repo.getAll()), 0)
        c = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.test_repo.store(c)
        self.assertEqual(len(self.test_repo.getAll()), 1)

    def testFind(self):
        self.assertEqual(len(self.test_repo.getAll()), 0)
        c = self.test_repo.find(1)
        self.assertEqual(c, None)
        c = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.test_repo.store(c)
        self.assertEqual(len(self.test_repo.getAll()), 1)
        cGasita = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.assertEqual(self.test_repo.find(1), cGasita)

    def testGetAll(self):
        print("testGetAll")
        self.assertEqual(len(self.test_repo.getAll()), 0)
        c1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.test_repo.store(c1)
        self.assertEqual(len(self.test_repo.getAll()), 1)
        c2 = Carte(2, "Titlu 2", "Descriere 2", "Autor 2")
        self.test_repo.store(c2)
        self.assertEqual(len(self.test_repo.getAll()), 2)

    def tearDown(self):
        print("tearDown called")



class testRepoCarteFile(unittest.TestCase):
    def setUp(self):
        self.testRepo = RepositoryCartiFile("CartiDefaultTest.txt")

    def testStore(self):
        self.assertEqual(len(self.testRepo.getAll()), 5)
        c = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.testRepo.store(c)
        self.assertEqual(len(self.testRepo.getAll()), 6)

    def tearDown(self):
        with open("CartiDefaultTest.txt", "w") as f:
            f.write('111,"Dracula","An epistolary novel, the narrative is related through letters, diary entries, and newspaper articles. It has no single protagonist and opens with solicitor Jonathan Harker taking a business trip to stay at the castle of a Transylvanian nobleman, Count Dracula.","Bram Stoker"\n112,"Schoolgirl","It illuminates the prevalent social structures of a lost time, as well as the struggle of the individual against thema theme that occupied Dazais life both personally and professionally.","Osamu Dazai"\n113,"White Nights","A short story told in the first person by a nameless narrator. The narrator is a young man living in Saint Petersburg who suffers from loneliness. He gets to know and falls in love with a young woman, but the love remains unrequited as the woman misses her lover, with whom she is finally reunited.","Fyodor Dostoevsky"\n114,"Mr. Cogito","The collection ultimately consisted of 40 poems, which build up the drama around its single main character, Mr. Cogito. Mr. Cogito, who is involved in all the monologues and dialogues in the book, invites a range of various interpretations, including cultural and historical ones.","Herbert Zbigniew"\n115,"Symposium","It depicts a friendly contest of extemporaneous speeches given by a group of notable Athenian men attending a banquet. The panegyrics are to be given in praise of Eros, the god of love.","Plato"')



class TestRepoCarteBlackBox(unittest.TestCase):
    def setUp(self) -> None:
        self.test_repo = RepositoryCarti()

    def testStoreAndFind(self):
        c = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.test_repo.store(c)

        found_book = self.test_repo.find(1)
        self.assertIsNotNone(found_book)
        self.assertEqual(found_book.getId(), c.getId())
        self.assertEqual(found_book.getTitlu(), c.getTitlu())

    def testFindNonExistentBook(self):
        self.assertIsNone(self.test_repo.find(999))

    def testGetAll(self):
        c1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        c2 = Carte(2, "Titlu 2", "Descriere 2", "Autor 2")

        self.test_repo.store(c1)
        self.test_repo.store(c2)

        all_books = self.test_repo.getAll()
        self.assertEqual(len(all_books), 2)
        self.assertIn(c1, all_books)
        self.assertIn(c2, all_books)

    def tearDown(self):
        pass



"""
def testStoreRepoCarte():
    testRepo = RepositoryCarti()
    assert(testRepo.getSize() == 0)

    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    testRepo.store(carte1)
    assert(testRepo.getSize() == 1)

    carte2 = Carte(1, "Titlu 2", "Descriere 2", "Autor 2")

    try:
        testRepo.store(carte2)
        assert False
    except ValueError:
        assert True

    assert (testRepo.getSize() == 1)

    testRepo.store(Carte(2, "Titlu 3", "Descriere 3", "Autor 3"))
    assert(testRepo.getSize() == 2)



def testUpdateRepoCarte():
    testRepo = RepositoryCarti()
    assert(testRepo.getSize() == 0)

    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    try:
        testRepo.update(carte1)
        assert False
    except ValueError:
        assert True

    testRepo.store(carte1)
    assert(testRepo.getSize() == 1)

    carte2 = Carte(1, "Titlu 2", "Descriere 2", "Autor 2")

    testRepo.update(carte2)

    assert(testRepo.getSize() == 1)
    carteModif = testRepo.find(1)
    assert(carteModif.getTitlu() == "Titlu 2")
    assert(carteModif.getDescriere() == "Descriere 2")
    assert(carteModif.getAutor() == "Autor 2")

    carteNoua = Carte(4, "Titlu 4", "Descriere 4", "Autor 4")
    try:
        testRepo.update(carteNoua)
        assert False
    except ValueError:
        assert True
    assert(testRepo.getSize() == 1)



def testFindRepoCarte():
    testRepo = RepositoryCarti()
    assert(testRepo.getSize() == 0)
    carteGasita = testRepo.find(1)
    assert(carteGasita is None)

    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    carte2 = Carte(2, "Titlu 2", "Descriere 2", "Autor 2")
    carte3 = Carte(3, "Titlu 3", "Descriere 3", "Autor 3")

    testRepo.store(carte1)
    testRepo.store(carte2)
    testRepo.store(carte3)

    assert(testRepo.getSize() == 3)

    carte1Gasita = testRepo.find(1)
    assert(carte1Gasita.getTitlu() == "Titlu 1")
    assert (carte1Gasita.getDescriere() == "Descriere 1")
    assert (carte1Gasita.getAutor() == "Autor 1")

    carte2Gasita = testRepo.find(2)
    assert(carte2Gasita.getTitlu() == "Titlu 2")
    assert (carte2Gasita.getDescriere() == "Descriere 2")
    assert (carte2Gasita.getAutor() == "Autor 2")

    carte3Gasita = testRepo.find(3)
    assert(carte3Gasita.getTitlu() == "Titlu 3")
    assert (carte3Gasita.getDescriere() == "Descriere 3")
    assert (carte3Gasita.getAutor() == "Autor 3")

    carte4Gasita = testRepo.find(4)
    assert(carte4Gasita is None)
"""