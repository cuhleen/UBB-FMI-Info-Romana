/*
#include <string.h>

#include "masina.h"
#include "list.h"


void repoAddCar(List* car_list, Car car)
{
	
	This function adds a new car to the list
	params: car_list - List*
			car - Car
	
	if (car_list->number_of_cars == car_list->capacity)
		resizeList(car_list);
	car_list->cars[car_list->number_of_cars] = car;
	car_list->number_of_cars++;
}

void repoModifyCar(List* car_list, Car car)
{
	
	This function modifies the model and the category for a car (a car is identified by its license plate)
	params:	car_list - List*
			car - Car
	
	int found = 0;
	for (int i = 0; i < car_list->number_of_cars; i++)
	{
		if (areEqual(car_list->cars[i], car))
		{
			int is_rented = car_list->cars[i].is_rented;
			destroyCar(car_list->cars[i]);
			car_list->cars[i] = car;
			car_list->cars[i].is_rented = is_rented;
			found = 1;
			break;
		}
	}
	if (!found)
		destroyCar(car);
}
*/