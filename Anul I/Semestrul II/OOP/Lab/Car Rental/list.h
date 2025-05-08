#pragma once
#include "masina.h"

typedef void* ElemType;

typedef void(*DestroyFunction)(ElemType);
typedef ElemType(*CopyFunction)(ElemType);

typedef struct
{
	ElemType* cars;
	int number_of_cars;
	int capacity;
	DestroyFunction destroyFct;
} List;

List* createList(DestroyFunction destroyFct);
void destroyList(List* car_list);
void resizeList(List* car_list);
List* copyList(List* car_list, CopyFunction copyFct);
void repoAddCar(List* car_list, ElemType car);
void repoModifyCar(List* car_list, ElemType car);
int repoRemoveCar(List* car_list, char* license_plate);
ElemType removeLastCar(List* car_list);