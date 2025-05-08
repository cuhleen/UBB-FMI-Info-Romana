#include <stdio.h>
#include <string.h>

#include "ui.h"
#include "service_masini.h"
#include "list.h"


void printMenu()
{
	/*
	This function prints the main menu of the application
	*/
	printf("\n1) Add a car.\n");
	printf("2) Modify an existing car.\n");
	printf("3) Remove an existing car.\n");
	printf("4) Rent or unrent car.\n");
	printf("5) Show cars by model, category or rent/unrent.\n");
	printf("6) Sort cars by model or category.\n");
	printf("7) Show all the cars.\n");
	printf("8) Undo.\n");
	printf("0) Exit.\n\n");
}

void addCar(CarRental* car_rental)
{
	/*
	This function runs option 1 - add car
	params: car_rental - CarRental*
	*/
	//printf("Option 1 - add car\n"); 
	char license_plate[32], model[64], category[32];
	printf("Enter license plate: ");
	scanf("%s", license_plate, 32);
	printf("Enter model: ");
	scanf("%s", model, 64);
	printf("Enter category: ");
	scanf("%s", category, 32);
	serviceAddCar(car_rental, license_plate, model, category);
}

void modifyCar(CarRental* car_rental)
{
	/*
	This function runs option 2 - modify car
	params: car_rental - CarRental*
	*/
	//printf("Option 2 - modify car\n");
	char license_plate[32], new_model[64], new_category[32];
	printf("Enter license plate: ");
	scanf("%s", license_plate, 32);
	printf("Enter new model: ");
	scanf("%s", new_model, 64);
	printf("Enter new category: ");
	scanf("%s", new_category, 32);
	serviceModifyCar(car_rental, license_plate, new_model, new_category);
}

void removeCar(CarRental* car_rental) {
	/*
	This function runs option 3 - remove car
	params: car_rental - CarRental*
	*/
	char license_plate[32];
	printf("Enter license plate for removal: ");
	scanf("%s", license_plate, 32);
	int successfulRemoval = serviceRemoveCar(car_rental, license_plate);
	if (successfulRemoval == 1)
		printf("Inexistent Car with the given license plate!\n");
	else
		printf("The car was successfully removed!\n");
}

void rentOrUnrentCar(CarRental* car_rental)
{
	/*
	This function runs option 4 - rent/unrent car
	params: car_rental - CarRental*
	*/
	//printf("Option 4 - rent/unrent car\n");
	char license_plate[32];
	printf("Enter license plate: ");
	scanf("%s", license_plate, 32);
	printf("Enter 1 for rent or 0 for unrent: ");
	int option3_choice;
	scanf("%d", &option3_choice);
	if(option3_choice == 1)
		serviceRentCar(car_rental, license_plate);
	else if (option3_choice == 0)
		serviceUnrentCar(car_rental, license_plate);
}

void showCars(List* car_list)
{
	/*
	This function prints all the cars from the list car_list
	params: car_rental - CarRental*
	*/
	for(int i = 0; i < car_list->number_of_cars; i++)
		printf("%s, %s, %s, %d\n", ((Car*)(car_list->cars[i]))->license_plate, ((Car*)(car_list->cars[i]))->model, ((Car*)(car_list->cars[i]))->category, ((Car*)(car_list->cars[i]))->is_rented);
}

void showCarsByCriteria(CarRental* car_rental)
{
	/*
	This function runs option 4 - show cars by criteria
	params: car_rental - CarRental*
	*/
	//printf("Option 4 - show cars by criteria\n");
	printf("Enter 1 for model or 2 for category or 3 for rent / unrent: ");
	int option4_choice;
	scanf("%d", &option4_choice);
	if (option4_choice == 1)
	{
		char model[64];
		printf("Enter model: ");
		scanf("%s", model, 64);
		List* cars_to_show_by_model = serviceGetCarsByModel(car_rental, model);
		showCars(cars_to_show_by_model);
		destroyList(cars_to_show_by_model);
	}
	else
	{
		if (option4_choice == 2)
		{
			char category[32];
			printf("Enter category: ");
			scanf("%s", category, 32);
			List* cars_to_show_by_category = serviceGetCarsByCategory(car_rental, category);
			showCars(cars_to_show_by_category);
			destroyList(cars_to_show_by_category);
		}
		else {
			if (option4_choice == 3) {
				int rentOrUnrent;
				printf("Enter 1 for rent or 0 for unrent: ");
				scanf("%d", &rentOrUnrent);
				List* cars_to_show_by_rentOrUnrent = serviceGetCarsByRentOrUnrent(car_rental, rentOrUnrent);
				showCars(cars_to_show_by_rentOrUnrent);
				destroyList(cars_to_show_by_rentOrUnrent);
			}
		}
	}
}

void sortCarsByCriteria(CarRental* car_rental)
{
	/*
	This function runs option 5 - sort cars by criteria
	params: car_rental - CarRental*
	*/
	//printf("Option 5 - sort cars by criteria\n");
	printf("Enter 1 for sorting in increasing order or 0 for decreasing order: ");
	int sorting_order;
	scanf("%d", &sorting_order);
	printf("Enter 1 for sorting by model or 2 for sorting by category: ");
	int option5_choice;
	scanf("%d", &option5_choice);
	int(*compareFunction)(Car*, Car*); // define function pointer
	if (option5_choice == 1)
	{
		if (sorting_order == 1) // increasing order
			compareFunction = compareCarsByModelIncreasing;
		else // decreasing order
			compareFunction = compareCarsByModelDecreasing;
	}	
	else
	{
		if (sorting_order == 1) // increasing order
			compareFunction = compareCarsByCategoryIncreasing;
		else // decreasing order
			compareFunction = compareCarsByCategoryDecreasing;
	}
	List* sorted_car_list = serviceSortCars(car_rental, compareFunction);
	showCars(sorted_car_list);
	destroyList(sorted_car_list);
}



void runUI()
{
	/*
	This function runs UI layer
	*/
	CarRental car_rental = createCarRental();
	/*
	serviceAddCar(&car_rental, "cj11abc", "golf4", "sport");
	serviceAddCar(&car_rental, "cj22xyz", "porsche", "suv");
	serviceAddCar(&car_rental, "bn99abc", "golf4", "mini");
	*/
	/*
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
	*/
	while (1)
	{
		printMenu();
		int main_menu_option;
		printf("Enter option: ");
		scanf("%d", &main_menu_option);
		if (main_menu_option == 0)
			break;
		switch (main_menu_option)
		{
			case 1:
				addCar(&car_rental);
				break;
			case 2:
				modifyCar(&car_rental);
				break;
			case 3:
				removeCar(&car_rental);
				break;
			case 4:
				rentOrUnrentCar(&car_rental);
				break;
			case 5:
				showCarsByCriteria(&car_rental);
				break;
			case 6:
				sortCarsByCriteria(&car_rental);
				break;
			case 7:
				showCars(car_rental.allCars);
				break;
			case 8:
				if (undo(&car_rental) == 1)
					printf("No more undo!\n");
				break;
			default:
				continue;
		}
	}
	destroyCarRental(&car_rental);
}