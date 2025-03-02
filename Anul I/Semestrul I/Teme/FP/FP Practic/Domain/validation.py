from Domain.melodie import Melodie

class validation():
    def validate(self, melodie:Melodie):
        errors = []

        if len(melodie.getTitlu()) < 1:
            errors.append('Titlul trebuie să aibă minim un caracter. ')
        if len(melodie.getArtist()) < 1:
            errors.append('Artistul trebuie să aibă minim un caracter. ')
        if melodie.getGen() != "Rock" and melodie.getGen() != "Pop" and melodie.getGen() != "Jazz":
            errors.append('Genul trebuie să fie "Rock" sau "Pop" sau "Jazz". ')

        nrZile = [0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
        zi, luna, an = melodie.getData().split(".")
        zi = int(zi)
        luna = int(luna)
        an = int(an)
        if luna > 12:
            errors.append("Data este incorectă. ")
        elif zi > nrZile[luna]:
            errors.append("Data este incorectă. ")

        if len(errors) > 0:
            return errors
        return -1