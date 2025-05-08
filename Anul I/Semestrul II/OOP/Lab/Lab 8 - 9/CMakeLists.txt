cmake_minimum_required(VERSION 3.28)
project(Lab_8___9)

set(CMAKE_CXX_STANDARD 14)
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")

include_directories(.)

add_executable(Lab_8___9
    consola.cpp
    consola.h
    Film.cpp
    Film.h
    main.cpp
    repo.cpp
    repo.h
    service.cpp
    service.h
    teste.cpp
    teste.h
    validator.cpp
    validator.h
        ShoppingCart.h
        ShoppingCart.cpp)
