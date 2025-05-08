#pragma once
#include "list.h"

typedef struct {
	List* allCars;
	List* undoList;
} CarRental;

/*
Functions used for business layer
*/
CarRental createCarRental();
void destroyCarRental(CarRental* car_rental);
void serviceAddCar(CarRental* car_list, char* license_plate, char* model, char* category);
void serviceModifyCar(CarRental* car_rental, char* license_plate, char* new_model, char* new_category);
int serviceRemoveCar(CarRental* car_rental, char* license_plate);

List* serviceGetCarsByModel(CarRental* car_rental, char* model);
List* serviceGetCarsByCategory(CarRental* car_rental, char* category);
List* serviceGetCarsByRentOrUnrent(CarRental* car_rental, int rentOrUnrent);
void serviceRentCar(CarRental* car_rental, char* license_plate);
void serviceUnrentCar(CarRental* car_rental, char* license_plate);
int compareCarsByModelIncreasing(Car* car1, Car* car2);
int compareCarsByModelDecreasing(Car* car1, Car* car2);
int compareCarsByCategoryIncreasing(Car* car1, Car* car2);
int compareCarsByCategoryDecreasing(Car* car1, Car* car2);
List* serviceSortCars(CarRental* car_rental, int (*compareFunction)(Car*, Car*));

int undo(CarRental* car_rental);

