#include <string.h>

#include "service_masini.h"
#include "masina.h"
#include "list.h"
#include "validare.h"

CarRental createCarRental() {
	CarRental carRental;
	carRental.allCars = createList(destroyCar);
	carRental.undoList = createList(destroyList);
	return carRental;
}

void destroyCarRental(CarRental* car_rental) {
	destroyList(car_rental->allCars);
	destroyList(car_rental->undoList);
}

void serviceAddCar(CarRental* car_rental, char* license_plate, char* model, char* category)
{
	/*
	This function adds a new car to the list
	params: car_rental - CarRental*
			license_plate - char*
			model - char*
			category - char*
	*/
	Car* car = createCar(license_plate, model, category, 0);
	List* undo_list = copyList(car_rental->allCars, copyCar);
	if (validateCar(car)) {
		repoAddCar(car_rental->allCars, car);
		repoAddCar(car_rental->undoList, undo_list);
	}
	else {
		destroyCar(car);
		destroyList(undo_list);
	}
}

void serviceModifyCar(CarRental* car_rental, char* license_plate, char* new_model, char* new_category)
{
	/*
	This function modifies the model and the category for a car (a car is identified by its license plate)
	params: car_rental - CarRental*
			license_plate - char*
			new_model - char*
			new_category - char*
	*/
	for (int i = 0; i < car_rental->allCars->number_of_cars; i++) {
		if (strcmp(((Car*)(car_rental->allCars->cars[i]))->license_plate, license_plate) == 0) {
			Car* car = createCar(license_plate, new_model, new_category, 0);
			List* copy = copyList(car_rental->allCars, copyCar);
			repoAddCar(car_rental->undoList, copy);
			repoModifyCar(car_rental->allCars, car);
			return;
		}
	}
}

int serviceRemoveCar(CarRental* car_rental, char* license_plate) {
	/*
	This function removes a car from the list
	params: car_rental - CarRental*
			license_plate - char*
	*/
	List* copy = copyList(car_rental->allCars, copyCar);
	repoAddCar(car_rental->undoList, copy);
	int existentCar = repoRemoveCar(car_rental->allCars, license_plate);
	return existentCar;
}

void serviceRentCar(CarRental* car_rental, char* license_plate)
{
	/*
	This function rents a car if it is not rented
	params: car_rental - CarRental*
			license_plate - char*
	*/
	for (int i = 0; i < car_rental->allCars->number_of_cars; i++)
		if (strcmp(((Car*)(car_rental->allCars->cars[i]))->license_plate, license_plate) == 0 && ((Car*)(car_rental->allCars->cars[i]))->is_rented == 0) {
			List* copy = copyList(car_rental->allCars, copyCar);
			repoAddCar(car_rental->undoList, copy);
			((Car*)(car_rental->allCars->cars[i]))->is_rented = 1; // car rented
		}
}

void serviceUnrentCar(CarRental* car_rental, char* license_plate)
{
	/*
	This function unrents a car
	params: car_rental - CarRental*
			license_plate - char*
	*/
	for (int i = 0; i < car_rental->allCars->number_of_cars; i++)
		if (strcmp(((Car*)(car_rental->allCars->cars[i]))->license_plate, license_plate) == 0 && ((Car*)(car_rental->allCars->cars[i]))->is_rented == 1) {
			List* copy = copyList(car_rental->allCars, copyCar);
			repoAddCar(car_rental->undoList, copy);
			((Car*)(car_rental->allCars->cars[i]))->is_rented = 0; // car unrented
		}
}

List* serviceGetCarsByModel(CarRental* car_rental, char* model)
{
	/*
	This function returns all cars that have the model *model*
	params: car_rental - CarRental*
			model - char*
	return: all_cars_model - List*
	*/
	List* all_cars_model = createList(destroyCar);
	for (int i = 0; i < car_rental->allCars->number_of_cars; i++)
	{
		if (strcmp(((Car*)(car_rental->allCars->cars[i]))->model, model) == 0)
		{
			Car* car_model = createCar(((Car*)(car_rental->allCars->cars[i]))->license_plate, ((Car*)(car_rental->allCars->cars[i]))->model, ((Car*)(car_rental->allCars->cars[i]))->category, ((Car*)(car_rental->allCars->cars[i]))->is_rented);
			repoAddCar(all_cars_model, car_model);
		}
	}
	return all_cars_model;
}

List* serviceGetCarsByCategory(CarRental* car_rental, char* category)
{
	/*
	This function returns all cars that have the category *category*
	params: car_rental - CarRental*
			category - char*
	return: all_cars_category - List*
	*/
	List* all_cars_category = createList(destroyCar);
	for (int i = 0; i < car_rental->allCars->number_of_cars; i++)
	{
		if (strcmp(((Car*)(car_rental->allCars->cars[i]))->category, category) == 0)
		{
			Car* car_category = createCar(((Car*)(car_rental->allCars->cars[i]))->license_plate, ((Car*)(car_rental->allCars->cars[i]))->model, ((Car*)(car_rental->allCars->cars[i]))->category, ((Car*)(car_rental->allCars->cars[i]))->is_rented);
			repoAddCar(all_cars_category, car_category);
		}
	}
	return all_cars_category;
}

List* serviceGetCarsByRentOrUnrent(CarRental* car_rental, int rentOrUnrent) {
	/*
	This function returns all cars that are rent or unrent *rentOrUnrent*
	params: car_rental - CarRental*
			rentOrUnrent - int
	return: all_cars_rentOrUnrent - List*
	*/
	List* rentOrUnrentList = createList(destroyCar);
	for (int i = 0; i < car_rental->allCars->number_of_cars; i++)
	{
		if (((Car*)(car_rental->allCars->cars[i]))->is_rented == rentOrUnrent)
		{
			Car* car_rentOrUnrent = createCar(((Car*)(car_rental->allCars->cars[i]))->license_plate, ((Car*)(car_rental->allCars->cars[i]))->model, ((Car*)(car_rental->allCars->cars[i]))->category, ((Car*)(car_rental->allCars->cars[i]))->is_rented);
			repoAddCar(rentOrUnrentList, car_rentOrUnrent);
		}
	}
	return rentOrUnrentList;
}

int compareCarsByModelIncreasing(Car* car1, Car* car2)
{
	/*
	This function checks that car1 and car2 are sorted by model in increasing order
	params: car1 - Car*
			car2 - Car*
	return: 0 - if cars are already sorted by model in increasing order
			1 - otherwise
	*/
	if (strcmp(car1->model, car2->model) > 0)
		return 1;
	return 0;
}

int compareCarsByModelDecreasing(Car* car1, Car* car2)
{
	/*
	This function checks that car1 and car2 are sorted by model in decreasing order
	params: car1 - Car*
			car2 - Car*
	return: 0 - if cars are already sorted by model in decreasing order
			1 - otherwise
	*/
	if (strcmp(car1->model, car2->model) < 0)
		return 1;
	return 0;
}

int compareCarsByCategoryIncreasing(Car* car1, Car* car2)
{
	/*
	This function checks that car1 and car2 are sorted by category in increasing order
	params: car1 - Car*
			car2 - Car*
	return: 0 - if cars are already sorted by category in increasing order
			1 - otherwise
	*/
	if (strcmp(car1->category, car2->category) > 0)
		return 1;
	return 0;
}

int compareCarsByCategoryDecreasing(Car* car1, Car* car2)
{
	/*
	This function checks that car1 and car2 are sorted by category in decreasing order
	params: car1 - Car*
			car2 - Car*
	return: 0 - if cars are already sorted by category in decreasing order
			1 - otherwise
	*/
	if (strcmp(car1->category, car2->category) < 0)
		return 1;
	return 0;
}

List* serviceSortCars(CarRental* car_rental, int (*compareFunction)(Car*, Car*))
{
	/*
	This function returns a list of all the cars
	params: car_rental - CarRental*
			compareFunction - function pointer to sorting criteria
	return: sorted_car_list - List*
	*/
	List* sorted_car_list = createList(destroyCar);
	for (int i = 0; i < car_rental->allCars->number_of_cars; i++)
	{
		Car* car_copy = createCar(((Car*)(car_rental->allCars->cars[i]))->license_plate, ((Car*)(car_rental->allCars->cars[i]))->model, ((Car*)(car_rental->allCars->cars[i]))->category, ((Car*)(car_rental->allCars->cars[i]))->is_rented);
		repoAddCar(sorted_car_list, car_copy);
	}
	int sortat = 0;
	while (!sortat)
	{
		sortat = 1;
		for (int i = 0; i < sorted_car_list->number_of_cars - 1; i++)
			if (compareFunction(sorted_car_list->cars[i], sorted_car_list->cars[i + 1]))
			{
				Car* car_copy_i = createCar(((Car*)(sorted_car_list->cars[i]))->license_plate, ((Car*)(sorted_car_list->cars[i]))->model, ((Car*)(sorted_car_list->cars[i]))->category, ((Car*)(sorted_car_list->cars[i]))->is_rented);
				Car* car_copy_iplus1 = createCar(((Car*)(sorted_car_list->cars[i + 1]))->license_plate, ((Car*)(sorted_car_list->cars[i + 1]))->model, ((Car*)(sorted_car_list->cars[i + 1]))->category, ((Car*)(sorted_car_list->cars[i + 1]))->is_rented);
				destroyCar(sorted_car_list->cars[i]);
				destroyCar(sorted_car_list->cars[i + 1]);
				sorted_car_list->cars[i] = car_copy_iplus1;
				sorted_car_list->cars[i + 1] = car_copy_i;
				sortat = 0;
			}
	}
	return sorted_car_list;
}

int undo(CarRental* car_rental) {
	/*
	This function undoes the last operation made
	params: car_rental - CarRental*
	return: 0 if undo was successful
			1 if there are no mode undoes to be made
	*/
	if (car_rental->undoList->number_of_cars == 0)
		return 1;
	List* car_list = removeLastCar(car_rental->undoList);
	destroyList(car_rental->allCars);
	car_rental->allCars = car_list;
	return 0;
}