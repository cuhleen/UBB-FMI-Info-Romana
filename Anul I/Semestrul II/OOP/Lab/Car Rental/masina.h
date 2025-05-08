#pragma once

typedef struct {
	char* license_plate;
	char* model;
	char* category;
	int is_rented; // is_rented = 1 if the car is rented, 0 otherwise
} Car;

Car* createCar(char* license_plate, char* model, char* category, int is_rented);
int areEqual(Car* car1, Car* car2);
void destroyCar(Car* car);
Car* copyCar(Car* car);