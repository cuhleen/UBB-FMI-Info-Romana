cmake_minimum_required(VERSION 3.29)
project(TAD_Colectie)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(TAD_Colectie
    App.cpp
    Colectie.cpp
    Colectie.h
    IteratorColectie.cpp
    IteratorColectie.h
    TestExtins.cpp
    TestExtins.h
    TestScurt.cpp
    TestScurt.h)
