cmake_minimum_required(VERSION 3.25)
project(Lab6)

set(CMAKE_CXX_STANDARD 26)
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")

add_executable(Lab6 main.cpp
        Film.cpp
        Film.h
        FilmRepo.cpp
        FilmRepo.h
        Service.cpp
        Service.h
        Interface.cpp
        Interface.h
        Validation.cpp
        Validation.h
        Tests.cpp
        Tests.h
        TemplateDynamicVector.cpp
        TemplateDynamicVector.h)
