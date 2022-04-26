package main.java.com.company;

public class PrimeNumbers implements Runnable {
    private final int number;
    private boolean result;

    public PrimeNumbers(int number) {
        this.number = number;
    }

    @Override
    public void run() {
            result = isPrime(number);
    }

    public boolean isResult() {
        return result;

    }


    static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0)
                return false;
        }

        return true;
    }


}
