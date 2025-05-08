#pragma once
#include "list.h"
#include "service_masini.h"

/*
Functions used for presentation layer
*/
void runUI();
void printMenu();
void addCar(CarRental* car_rental);
void modifyCar(CarRental* car_rental);
void rentOrUnrentCar(CarRental* car_rental);
void showCarsByCriteria(CarRental* car_rental);
void sortCarsByCriteria(CarRental* car_rental);