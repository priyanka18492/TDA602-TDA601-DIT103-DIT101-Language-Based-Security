#!/bin/bash
echo "please select a program to run [0,1,2,3] and Press ENTER"
echo "0: part0"
echo "1: part1"
echo "2: part2"

read number

java -jar "part$number.jar"
