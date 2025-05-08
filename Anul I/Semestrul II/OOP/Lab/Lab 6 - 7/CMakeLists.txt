cmake_minimum_required(VERSION 3.28)
project(Lab_6___7)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_CLANG_TIDY "/usr/bin/clang-tidy")
set (CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")

add_executable(Lab_6___7 main.cpp
        Repo/FilmRepo.h
        Service/FilmService.h
        Repo/FilmRepo.cpp
        Domain/Film.cpp
        Service/FilmService.cpp
        UI/console.h
        UI/console.cpp
        Domain/Validator.h
        Domain/Validator.cpp
        Tests/tests.h
        Tests/tests.cpp
        TemplateDynamicVector.h
        TemplateDynamicVector.cpp)
