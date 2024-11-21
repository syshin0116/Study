cmake_minimum_required(VERSION 3.29)
project(P01_HelloWorld C)

set(CMAKE_C_STANDARD 11)

add_executable(P01_HelloWorld main.c
        01hello.c)
