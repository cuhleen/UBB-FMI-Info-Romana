cmake_minimum_required(VERSION 3.28.3)
project(5_3_13 C)

set(CMAKE_CXX_FLAGS "-Wall -Wextra")
set(CMAKE_C_FLAGS "--coverage -g ${CMAKE_CXX_FLAGS}")
set(CMAKE_C_STANDARD 11)

add_executable(5_3_13
    main.c
        Domain/medicament.h
        Domain/medicament.c
        Repo/lista.h
        Repo/lista.c
        Service/service.h
        Service/service.c
        UI/ui.h
        UI/ui.c
        Tests/teste.h
        Tests/teste.c)
