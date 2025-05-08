cmake_minimum_required(VERSION 3.28)
project(Car_Rental C)

set(CMAKE_C_STANDARD 11)

include_directories(.)

add_executable(Car_Rental
    list.c
    list.h
    main.c
    masina.c
    masina.h
    repo_masini.c
    repo_masini.h
    service_masini.c
    service_masini.h
    teste.c
    teste.h
    ui.c
    ui.h
    validare.c
    validare.h)
