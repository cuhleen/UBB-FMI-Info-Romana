#include <stdlib.h>
#include <string.h>

#include "list.h"
#include "masina.h"


List* createList(DestroyFunction destroyFct)
{
	/*
	This function creates a new list of cars
	return: car_list - List*
	*/
	List* car_list = malloc(sizeof(List));
	car_list->cars = malloc(2 * sizeof(Car));
	car_list->number_of_cars = 0;
	car_list->capacity = 2;	
	car_list->destroyFct = destroyFct;
	return car_list;
}

void resizeList(List* car_list)
{
	/*
	This function allocates additional memory for the list of cars
	params: car_list - List*
	*/
	if (car_list->number_of_cars < car_list->capacity)
		return;
	int new_capacity = 2 * car_list->capacity;
	ElemType* new_cars = malloc(new_capacity * sizeof(ElemType));
	for (int i = 0; i < car_list->number_of_cars; i++)
		new_cars[i] = car_list->cars[i];
	free(car_list->cars); // deallocates old space
	car_list->cars = new_cars;
	car_list->capacity = new_capacity;
}

void destroyList(List* car_list)
{
	/*
	This function destroys a list of cars (deallocates the memory)
	params: car_list - List*
	*/
	for (int i = 0; i < car_list->number_of_cars; i++)
		car_list->destroyFct(car_list->cars[i]);
	free(car_list->cars);
	free(car_list);
}

ElemType getCar(List* car_list, int pos) {
	return car_list->cars[pos];
}

ElemType setCar(List* car_list, int pos, ElemType car) {
	ElemType new_car = car_list->cars[pos];
	car_list->cars[pos] = car;
	return new_car;
}

List* copyList(List* car_list, CopyFunction copyFct) {
	List* carList = createList(car_list->destroyFct);
	for (int i = 0; i < car_list->number_of_cars; i++) {
		ElemType car = getCar(car_list, i);
		repoAddCar(carList, copyFct(car));
	}
	return carList;
}

void repoAddCar(List* car_list, ElemType car)
{
	/*
	This function adds a new car to the list
	params: car_list - List*
			car - Car
	*/
	if (car_list->number_of_cars == car_list->capacity)
		resizeList(car_list);
	car_list->cars[car_list->number_of_cars] = car;
	car_list->number_of_cars++;
}

void repoModifyCar(List* car_list, ElemType new_car)
{
	/*
	This function modifies the model and the category for a car (a car is identified by its license plate)
	params:	car_list - List*
			car - Car
	*/
	for (int i = 0; i < car_list->number_of_cars; i++) {
		Car* car = getCar(car_list, i);
		if (strcmp(car->license_plate, ((Car*)new_car)->license_plate) == 0) {
			destroyCar(setCar(car_list, i, new_car));
			return;
		}
	}
}

int repoRemoveCar(List* car_list, char* license_plate) {
	for (int i = 0; i < car_list->number_of_cars; i++) {
		Car* car = getCar(car_list, i);
		if (strcmp(car->license_plate, license_plate) == 0) {
			destroyCar(car);
			for (int j = i; j < car_list->number_of_cars - 1; j++) {
				car_list->cars[j] = car_list->cars[j + 1];
			}
			car_list->number_of_cars -= 1;
			return 0;
		}
	}
	return 1;
}

ElemType removeLastCar(List* car_list) {
	ElemType carForRemoval = car_list->cars[car_list->number_of_cars - 1];
	car_list->number_of_cars -= 1;
	return carForRemoval;
}