#include <string.h>

#include "masina.h"


int validateLicensePlate(char* license_plate)
{
	/*
	This function validates the car license_plate
	params: license_plate - char[]
	return: 1, if license_plate is not empty string
			0, otherwise
	*/
	return strlen(license_plate) > 0;
}

int validateModel(char* model)
{
	/*
	This function validates the car model
	params: model - char[]
	return: 1, if model is not empty string
			0, otherwise
	*/
	return strlen(model) > 0;
}

int validateCategory(char* category)
{
	/*
	This function validates the car category
	params: category - char[]
	return: 1, if category is mini, sport or suv
			0, otherwise
	*/
	if (strcmp(category, "mini") == 0 || strcmp(category, "sport") == 0 || strcmp(category, "suv") == 0)
		return 1;
	return 0;
}

int validateCar(Car* car)
{
	/*
	This function validates a car
	params: car - Car
	return: 1, if car is valid (valid license plate, model and category)
			0, otherwise
	*/
	return validateLicensePlate(car->license_plate) && validateModel(car->model) && validateCategory(car->category);
}