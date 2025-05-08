#include <string.h>
#include <stdlib.h>

#include "masina.h"


Car* createCar(char* license_plate, char* model, char* category, int is_rented)
{
	/*
	This function creates a new car
	params: license_plate - char*
			model - char*
			category - char*
			is_rented - int
	return: car - Car
	*/
	Car* car = malloc(sizeof(Car));
	size_t number_of_characters;
	number_of_characters = strlen(license_plate) + 1;
	car->license_plate = malloc(number_of_characters * sizeof(char));
	strcpy(car->license_plate, license_plate);
	number_of_characters = strlen(model) + 1;
	car->model = malloc(number_of_characters * sizeof(char));
	strcpy(car->model, model);
	number_of_characters = strlen(category) + 1;
	car->category = malloc(number_of_characters * sizeof(char));
	strcpy(car->category, category);
	car->is_rented = is_rented;
	return car;
}

int areEqual(Car* car1, Car* car2)
{
	/*
	This function verifies when 2 cars are equal
	params: car1 - Car
			car2 - Car
	return: 1, if the cars have the same license plate
			0, otherwise
	*/
	if (strcmp(car1->license_plate, car2->license_plate) == 0)
		return 1;
	return 0;
}

void destroyCar(Car* car)
{
	/*
	This function destroys a car (deallocates the memory)
	params: car - Car*
	*/
	free(car->license_plate);
	free(car->model);
	free(car->category);
	free(car);
}

Car* copyCar(Car* car) {
	return createCar(car->license_plate, car->model, car->category, car->is_rented);
}