package uta.fisei.repasoparaprueba.Logic;

public class Mathemathics {
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

    public static int Add(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }

    public static int Substract(int firstNumber, int secondNumber) {
        return firstNumber - secondNumber;
    }
}
