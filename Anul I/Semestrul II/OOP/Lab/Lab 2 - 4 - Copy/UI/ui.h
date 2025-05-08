#pragma once

#include "../Domain/medicament.h"
#include "../Repo/lista.h"
#include "../Service/service.h"

void ui_adaugaMedicament(Farmacie* farmacie);

void ui_modificaMedicament(Farmacie* farmacie);

void ui_stergeMedicament(Farmacie* farmacie);

void ui_afiseazaFarmacia(Lista* lista);

void ui_filtreaza(Farmacie* farmacie);

void ui_sorteaza(Farmacie* farmacie);

void run();