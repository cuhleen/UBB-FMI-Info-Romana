cmake_minimum_required(VERSION 3.28)
project(Lab_6_7_PetStore)

set(CMAKE_CXX_STANDARD 14)
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")

include_directories(.)

add_executable(Lab_6_7_PetStore
    main.cpp
    Pet.cpp
    Pet.h
    VectDinNewDelete.cpp
    VectDinNewDelete.h
    VectDinSmartPointer.cpp
    VectDinSmartPointer.h
    VectorDinamicTemplate.h)
