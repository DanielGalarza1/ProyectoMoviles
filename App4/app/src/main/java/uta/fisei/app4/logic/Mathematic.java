package uta.fisei.app4.logic;

public class Mathematic {

    public long pow(int base, int exponent){
        long result = 1;

        for (int i = 1; i <= exponent; i++){
            result *= base;
        }

        return result;
    }

    public static long factorial(int number){
        long result = 1;

        for (int i = 1; i <= number; i++){
            result *= i;
        }

        return result;
    }

    public static int AddForClass(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }
}
