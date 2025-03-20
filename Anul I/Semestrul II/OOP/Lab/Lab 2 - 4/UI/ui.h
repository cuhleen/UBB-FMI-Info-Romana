#pragma once

#include "../Domain/cheltuiala.h"
#include "../Repo/lista.h"
#include "../Service/service.h"

void ui_adaugaCheltuiala(BugetFamilie* buget);

void ui_modificaCheltuiala(BugetFamilie* buget);

void ui_stergeCheltuiala(BugetFamilie* buget);

void ui_afiseazaBugetulFamiliei(Lista* lista);

void ui_filtreaza(BugetFamilie* buget);

void ui_sorteaza(BugetFamilie* buget);

void run();