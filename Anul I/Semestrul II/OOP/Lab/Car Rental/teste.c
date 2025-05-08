#include <assert.h>
#include <string.h>
#include <stdio.h>

#include "list.h"
//#include "repo_masini.h"
#include "service_masini.h"


void repoAddCarTest()
{
	List* car_list = createList(destroyCar);
	Car* car = createCar("cj11abc", "logan", "sport", 0);
	assert(car_list->number_of_cars == 0);
	repoAddCar(car_list, car);
	assert(car_list->number_of_cars == 1);
	assert(strcmp(((Car*)(car_list->cars[0]))->license_plate, "cj11abc") == 0);
	assert(strcmp(((Car*)(car_list->cars[0]))->model, "logan") == 0);
	assert(strcmp(((Car*)(car_list->cars[0]))->category, "sport") == 0);
	destroyList(car_list);
}

void repoModifyCarTest()
{
	List* car_list = createList(destroyCar);
	Car* car = createCar("cj11abc", "logan", "sport", 0);
	repoAddCar(car_list, car);
	Car* car_new_model_and_category = createCar("cj11abc", "porsche", "suv", 0);
	repoModifyCar(car_list, car_new_model_and_category);
	assert(strcmp(((Car*)(car_list->cars[0]))->license_plate, "cj11abc") == 0);
	assert(strcmp(((Car*)(car_list->cars[0]))->model, "porsche") == 0);
	assert(strcmp(((Car*)(car_list->cars[0]))->category, "suv") == 0);
	Car* car2 = createCar("cj22xyz", "golf4", "sport", 0);
	repoModifyCar(car_list, car2);
	destroyCar(car2);
	destroyList(car_list);
}

void repoRemoveCarTest()
{
	List* car_list = createList(destroyCar);
	Car* car1 = createCar("cj11abc", "logan", "sport", 0);
	Car* car2 = createCar("cj22xyz", "porsche", "suv", 1);
	Car* car3 = createCar("bn99abc", "golf4", "sport", 1);
	Car* car4 = createCar("cj13azc", "golf4", "suv", 0);
	Car* car5 = createCar("cj21arc", "golf4", "sport", 1);
	repoAddCar(car_list, car1);
	repoAddCar(car_list, car2);
	repoAddCar(car_list, car3);
	repoAddCar(car_list, car4);
	repoAddCar(car_list, car5);
	assert(car_list->number_of_cars == 5);
	repoRemoveCar(car_list, "cj13azc");
	assert(car_list->number_of_cars == 4);
	repoRemoveCar(car_list, "bn99abc");
	assert(car_list->number_of_cars == 3);
	int existentCar = repoRemoveCar(car_list, "mm99abc");
	assert(existentCar == 1);
	destroyList(car_list);
}

void serviceAddCarTest()
{
	CarRental car_rental = createCarRental();
	assert(car_rental.allCars->number_of_cars == 0);
	serviceAddCar(&car_rental, "cj11abc", "logan", "sport");
	assert(car_rental.allCars->number_of_cars == 1);
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->license_plate, "cj11abc") == 0);
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->model, "logan") == 0);
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->category, "sport") == 0);
	serviceAddCar(&car_rental, "", "", "tractor");
	assert(car_rental.allCars->number_of_cars == 1);
	serviceAddCar(&car_rental, "cj99mnp", "lamborghini", "tractor");
	assert(car_rental.allCars->number_of_cars == 1);
	destroyCarRental(&car_rental);
}

void serviceModifyCarTest()
{
	CarRental car_rental = createCarRental();
	assert(car_rental.allCars->number_of_cars == 0);
	serviceAddCar(&car_rental, "cj11abc", "logan", "sport");
	assert(car_rental.allCars->number_of_cars == 1);
	serviceModifyCar(&car_rental, "cj11abc", "porsche", "suv");
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->license_plate, "cj11abc") == 0);
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->model, "porsche") == 0);
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->category, "suv") == 0);
	serviceModifyCar(&car_rental, "", "", "tractor");
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->license_plate, "cj11abc") == 0);
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->model, "porsche") == 0);
	assert(strcmp(((Car*)(car_rental.allCars->cars[0]))->category, "suv") == 0);
	destroyCarRental(&car_rental);
}

void serviceRemoveCarTest()
{
	CarRental car_rental = createCarRental();
	assert(car_rental.allCars->number_of_cars == 0);
	serviceAddCar(&car_rental, "cj11abc", "logan", "sport");
	assert(car_rental.allCars->number_of_cars == 1);
	serviceRemoveCar(&car_rental, "cj11abc");
	assert(car_rental.allCars->number_of_cars == 0);
	destroyCarRental(&car_rental);
}

void serviceRentUnrentCarTest()
{
	CarRental car_rental = createCarRental();
	assert(car_rental.allCars->number_of_cars == 0);
	serviceAddCar(&car_rental, "bn99abc", "golf4", "sport");
	serviceRentCar(&car_rental, "bn99abc");
	assert(((Car*)(car_rental.allCars->cars[0]))->is_rented == 1);
	serviceUnrentCar(&car_rental, "bn99abc");
	assert(((Car*)(car_rental.allCars->cars[0]))->is_rented == 0);
	destroyCarRental(&car_rental);
}

void serviceGetCarsByModelTest()
{
	CarRental car_rental = createCarRental();
	serviceAddCar(&car_rental, "cj11abc", "golf4", "sport");
	serviceAddCar(&car_rental, "cj22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bn99abc", "golf4", "sport");
	List* car_list_model = serviceGetCarsByModel(&car_rental, "golf4");
	assert(car_list_model->number_of_cars == 2);
	destroyList(car_list_model);
	destroyCarRental(&car_rental);
}

void serviceGetCarsByCategoryTest()
{
	CarRental car_rental = createCarRental();
	serviceAddCar(&car_rental, "cj11abc", "golf4", "sport");
	serviceAddCar(&car_rental, "cj22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bn99abc", "golf4", "sport");
	List* car_list_category = serviceGetCarsByCategory(&car_rental, "suv");
	assert(car_list_category->number_of_cars == 1);
	destroyList(car_list_category);
	destroyCarRental(&car_rental);
}

void serviceGetCarsByRentTest()
{
	CarRental car_rental = createCarRental();
	Car* car1 = createCar("cj11abc", "logan", "sport", 0);
	Car* car2 = createCar("cj22xyz", "porsche", "suv", 1);
	Car* car3 = createCar("bn99abc", "golf4", "sport", 1);
	Car* car4 = createCar("cj13azc", "golf4", "suv", 0);
	Car* car5 = createCar("cj21arc", "golf4", "sport", 1);
	repoAddCar(car_rental.allCars, car1);
	repoAddCar(car_rental.allCars, car2);
	repoAddCar(car_rental.allCars, car3);
	repoAddCar(car_rental.allCars, car4);
	repoAddCar(car_rental.allCars, car5);
	List* car_list_rent = serviceGetCarsByRentOrUnrent(&car_rental, 1);
	assert(car_list_rent->number_of_cars == 3);
	destroyList(car_list_rent);
	List* car_list_unrent = serviceGetCarsByRentOrUnrent(&car_rental, 0);
	assert(car_list_unrent->number_of_cars == 2);
	destroyList(car_list_unrent);
	destroyCarRental(&car_rental);
}

void serviceSortCarsByModelTest()
{
	CarRental car_rental = createCarRental();
	serviceAddCar(&car_rental, "cj11abc", "logan", "sport");
	serviceAddCar(&car_rental, "bn33mnp", "golf5", "sport");
	serviceAddCar(&car_rental, "cj22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bn99abc", "golf4", "sport");
	serviceAddCar(&car_rental, "ct77asd", "golf4", "sport");
	serviceAddCar(&car_rental, "mm88jkl", "polo", "mini");
	List* sorted_car_list_inc = serviceSortCars(&car_rental, compareCarsByModelIncreasing);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[0]))->license_plate, "bn99abc") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[0]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[0]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[1]))->license_plate, "ct77asd") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[1]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[1]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[2]))->license_plate, "bn33mnp") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[2]))->model, "golf5") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[2]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[3]))->license_plate, "cj11abc") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[3]))->model, "logan") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[3]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[4]))->license_plate, "mm88jkl") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[4]))->model, "polo") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[4]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[5]))->license_plate, "cj22xyz") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[5]))->model, "porsche") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[5]))->category, "suv") == 0);
	List* sorted_car_list_dec = serviceSortCars(&car_rental, compareCarsByModelDecreasing);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[5]))->license_plate, "ct77asd") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[5]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[5]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[4]))->license_plate, "bn99abc") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[4]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[4]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[3]))->license_plate, "bn33mnp") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[3]))->model, "golf5") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[3]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[2]))->license_plate, "cj11abc") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[2]))->model, "logan") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[2]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[1]))->license_plate, "mm88jkl") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[1]))->model, "polo") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[1]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[0]))->license_plate, "cj22xyz") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[0]))->model, "porsche") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[0]))->category, "suv") == 0);
	destroyList(sorted_car_list_inc);
	destroyList(sorted_car_list_dec);
	destroyCarRental(&car_rental);
}

void serviceSortCarsByCategoryTest()
{
	CarRental car_rental = createCarRental();
	serviceAddCar(&car_rental, "cj11abc", "logan", "mini");
	serviceAddCar(&car_rental, "bn33mnp", "golf5", "sport");
	serviceAddCar(&car_rental, "cj22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bn99abc", "golf4", "mini");
	serviceAddCar(&car_rental, "ct77asd", "golf4", "sport");
	serviceAddCar(&car_rental, "mm88jkl", "polo", "mini");
	List* sorted_car_list_inc = serviceSortCars(&car_rental, compareCarsByCategoryIncreasing);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[0]))->license_plate, "cj11abc") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[0]))->model, "logan") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[0]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[1]))->license_plate, "bn99abc") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[1]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[1]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[2]))->license_plate, "mm88jkl") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[2]))->model, "polo") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[2]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[3]))->license_plate, "bn33mnp") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[3]))->model, "golf5") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[3]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[4]))->license_plate, "ct77asd") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[4]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[4]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_inc->cars[5]))->license_plate, "cj22xyz") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[5]))->model, "porsche") == 0 && strcmp(((Car*)(sorted_car_list_inc->cars[5]))->category, "suv") == 0);
	List* sorted_car_list_dec = serviceSortCars(&car_rental, compareCarsByCategoryDecreasing);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[5]))->license_plate, "mm88jkl") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[5]))->model, "polo") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[5]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[4]))->license_plate, "bn99abc") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[4]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[4]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[3]))->license_plate, "cj11abc") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[3]))->model, "logan") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[3]))->category, "mini") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[2]))->license_plate, "ct77asd") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[2]))->model, "golf4") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[2]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[1]))->license_plate, "bn33mnp") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[1]))->model, "golf5") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[1]))->category, "sport") == 0);
	assert(strcmp(((Car*)(sorted_car_list_dec->cars[0]))->license_plate, "cj22xyz") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[0]))->model, "porsche") == 0 && strcmp(((Car*)(sorted_car_list_dec->cars[0]))->category, "suv") == 0);
	destroyList(sorted_car_list_inc);
	destroyList(sorted_car_list_dec);
	destroyCarRental(&car_rental);
}

void domainResizeListTest()
{
	CarRental car_rental = createCarRental();
	assert(car_rental.allCars->capacity == 2);
	serviceAddCar(&car_rental, "cj11abc", "logan", "sport");
	serviceAddCar(&car_rental, "cj22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bn99abc", "golf4", "sport");
	assert(car_rental.allCars->capacity == 4);
	serviceAddCar(&car_rental, "bt11abc", "logan", "sport");
	serviceAddCar(&car_rental, "bt22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bt99abc", "golf4", "sport");
	assert(car_rental.allCars->capacity == 8);
	assert(car_rental.allCars->number_of_cars == 6);
	resizeList(car_rental.allCars);
	ElemType carRemoved = removeLastCar(car_rental.allCars);
	assert(car_rental.allCars->capacity == 8);
	assert(car_rental.allCars->number_of_cars == 5);
	destroyCar(carRemoved);
	destroyCarRental(&car_rental);
}

void domainAreEqualTest() {
	Car* car1 = createCar("cj11abc", "logan", "sport", 0);
	Car* car2 = createCar("cj11abc", "porsche", "suv", 0);
	Car* car3 = createCar("bt11abc", "logan", "sport", 0);
	assert(areEqual(car1, car2) == 1);
	assert(areEqual(car1, car3) == 0);
	destroyCar(car3);
	destroyCar(car2);
	destroyCar(car1);
}

void undoTest() {
	CarRental car_rental = createCarRental();
	serviceAddCar(&car_rental, "cj11abc", "logan", "mini");
	serviceAddCar(&car_rental, "bn33mnp", "golf5", "sport");
	serviceAddCar(&car_rental, "cj22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bn99abc", "golf4", "mini");
	serviceAddCar(&car_rental, "ct77asd", "golf4", "sport");
	serviceAddCar(&car_rental, "mm88jkl", "polo", "mini");
	assert(car_rental.allCars->number_of_cars == 6);
	assert(car_rental.undoList->number_of_cars == 6);
	serviceModifyCar(&car_rental, "bn33mnp", "golf6", "suv");
	serviceRentCar(&car_rental, "bn99abc");
	serviceRemoveCar(&car_rental, "mm88jkl");
	serviceRentCar(&car_rental, "ct77asd");
	serviceUnrentCar(&car_rental, "bn99abc");
	assert(car_rental.allCars->number_of_cars == 5);
	assert(car_rental.undoList->number_of_cars == 11);
	
	undo(&car_rental);
	assert(car_rental.allCars->number_of_cars == 5);
	assert(car_rental.undoList->number_of_cars == 10);
	assert(((Car*)(car_rental.allCars->cars[3]))->is_rented == 1);
	
	undo(&car_rental);
	assert(car_rental.allCars->number_of_cars == 5);
	assert(car_rental.undoList->number_of_cars == 9);
	assert(((Car*)(car_rental.allCars->cars[4]))->is_rented == 0);

	undo(&car_rental);
	assert(car_rental.allCars->number_of_cars == 6);
	assert(car_rental.undoList->number_of_cars == 8);

	undo(&car_rental);
	assert(car_rental.allCars->number_of_cars == 6);
	assert(car_rental.undoList->number_of_cars == 7);
	assert(((Car*)(car_rental.allCars->cars[3]))->is_rented == 0);

	assert(strcmp(((Car*)(car_rental.allCars->cars[1]))->model, "golf6") == 0 && strcmp(((Car*)(car_rental.allCars->cars[1]))->category, "suv") == 0);
	undo(&car_rental);
	assert(car_rental.allCars->number_of_cars == 6);
	assert(car_rental.undoList->number_of_cars == 6);
	assert(strcmp(((Car*)(car_rental.allCars->cars[1]))->model, "golf5") == 0 && strcmp(((Car*)(car_rental.allCars->cars[1]))->category, "sport") == 0);

	undo(&car_rental);
	assert(car_rental.allCars->number_of_cars == 5);
	assert(car_rental.undoList->number_of_cars == 5);

	undo(&car_rental);
	undo(&car_rental);
	undo(&car_rental);
	undo(&car_rental);
	undo(&car_rental);
	assert(car_rental.allCars->number_of_cars == 0);
	assert(car_rental.undoList->number_of_cars == 0);

	int rezultatUndo = undo(&car_rental);
	assert(rezultatUndo == 1);
	
	destroyCarRental(&car_rental);
}

/*
	This function runs all the tests
*/
void runAllTests() {
	repoAddCarTest();
	repoModifyCarTest();
	repoRemoveCarTest();
	serviceAddCarTest();
	serviceModifyCarTest();
	serviceRemoveCarTest();
	serviceRentUnrentCarTest();
	serviceGetCarsByModelTest();
	serviceGetCarsByCategoryTest();
	serviceGetCarsByRentTest();
	serviceSortCarsByModelTest();
	serviceSortCarsByCategoryTest();
	domainResizeListTest();
	domainAreEqualTest();
	undoTest();
}
