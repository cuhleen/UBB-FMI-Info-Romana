#include "Interface.h"
#include <iostream>
using std::cin;
using std::cout;
using std::getline;

void Interface::Run() {
    while (true) {
        int command = 0;
        cout<<"\n\n        Cinema\n";
        cout << "1. Add film\n";
        cout << "2. Show all films\n";
        cout << "3. Search film\n";
        cout << "4. Delete film\n";
        cout << "5. Update film\n";
        cout << "0. Exit\n";
        cout << "Insert command: ";
        cin >> command;
        cin.ignore();
        if (command == 0) {
            break;
        }
        if (command == 1) {
            string name, genre, actor;
            int year;
            cout << "Title: ";
            getline(cin, name);
            cout << "Genre: ";
            getline(cin, genre);
            cout << "Actor: ";
            getline(cin, actor);
            cout << "Year: ";
            cin >> year;
            try {
                service.addFilm(name, genre, actor, year);
            }
            catch (RepoException &e) {
                cout << e.getMessage() << "\n";
            }
            catch (ValidationException &e) {
                cout << e.getMessage() << "\n";
            }
        }
        else if (command == 2) {
            auto films = service.getAll();
            for (const auto& film : films) {
                cout << film.getTitle() << " - " << film.getGenre() << " - " << film.getActor() << " - " << film.getYear() << "\n";
            }
        }
        else if (command == 3) {
            string name;
            cout << "Title: ";
            getline(cin, name);
            int position = service.searchFilm(name);
            if (position != -1) {
                auto films = service.getAll();
                cout<< films[position].getTitle() << " - " << films[position].getGenre() << " - " << films[position].getActor() << " - " << films[position].getYear() << " se află la pozitia " << position << "\n";
            }
            else {
                cout << "The film was not found\n";
            }
        }
        else if (command == 4) {
            string name;
            cout << "Title: ";
            getline(cin, name);
            try {
                service.removeFilm(name);
                cout << "The film has been deleted\n";
            }
            catch (RepoException &e) {
                cout << e.getMessage() << "\n";
            }
        }
        else if (command == 5) {
            string name;
            cout << "Title: ";
            getline(cin, name);
            string newName, newType, newManufacturer;
            int newPrice;
            cout << "New title: ";
            getline(cin, newName);
            cout << "New genre: ";
            getline(cin, newType);
            cout << "New actor: ";
            getline(cin, newManufacturer);
            cout << "New year: ";
            cin >> newPrice;
            try {
                Film newProduct(newName, newType, newManufacturer, newPrice);
                service.updateFilm(name, newProduct);
                cout << "The film has been updated\n";
            }
            catch (RepoException &e) {
                cout << e.getMessage() << "\n";
            }
        }
    }
}