package main.java.com.company;

import java.util.InputMismatchException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class PrimeNumbersServiceImpl implements PrimeNumbersService {
    private final AtomicInteger counter;
    private final int START;
    private final int GRANULARITY;

    public PrimeNumbersServiceImpl(int START, int GRANULARITY) {
        if (START < 1)
            throw new InputMismatchException("Illegal arguments");
        this.START = START;
        this.GRANULARITY = GRANULARITY;
        counter = new AtomicInteger();
    }

    public PrimeNumbersServiceImpl() {
        this(1, 1000);
    }


    @Override
    public void printPrimeNumbers(int maxNumber, int nThreads) {
        if (START < 1 || maxNumber < START || nThreads < 1)
            throw new InputMismatchException("Illegal arguments");
        //TODO check max or optimal size for nThreads, overwrite if needed
        // Number of threads = Number of Available Cores * (1 + Wait time / Service time)
        if (maxNumber - START < GRANULARITY) {
            for (int i = START; i <= maxNumber; i++)
                if (PrimeNumbers.isPrime(i)) {
                    System.out.println(i);
                    counter.incrementAndGet();
                }
        } else
            printOnlyPrime(getArrayNumbers(maxNumber, nThreads));
    }


    private Byte[] getArrayNumbers(int maxNumber, int nThreads) {
        Byte[] res = new Byte[maxNumber - START + 1];
        PrimeNumbers[] tasks = new PrimeNumbers[maxNumber - START + 1];
        for (int i = 0; i < tasks.length; i++)
            tasks[i] = new PrimeNumbers(i + START);
        ExecutorService executorService = Executors.newWorkStealingPool(nThreads);
        for (PrimeNumbers task : tasks)
            executorService.execute(task);

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tasks.length; i++)
            if (tasks[i].isResult()) {
                res[i] = 1;
                counter.incrementAndGet();
            }
        return res;
    }

    private void printOnlyPrime(Byte[] res) {
        for (int i = 0; i < res.length; i++)
            if (res[i] != null)
                System.out.println(i + START);
    }
}
