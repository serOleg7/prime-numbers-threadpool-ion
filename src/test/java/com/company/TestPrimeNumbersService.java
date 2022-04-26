package test.java.com.company;

import main.java.com.company.PrimeNumbersService;
import main.java.com.company.PrimeNumbersServiceImpl;
import org.junit.Test;
import org.testng.Assert;

import java.lang.reflect.Field;
import java.util.InputMismatchException;
import java.util.concurrent.atomic.AtomicInteger;


public class TestPrimeNumbersService {
    PrimeNumbersService primeNumbersService;


    @Test
    public void testPrimeNumbersService_CustomConstructor() throws Exception {
        primeNumbersService = new PrimeNumbersServiceImpl(10, 1);
        primeNumbersService.printPrimeNumbers(19, 8);
        Assert.assertEquals(getCounter(), 4);
        System.out.println("--------");

    }


    @Test
    public void testPrimeNumbersService_DefaultConstructor() throws Exception {
        primeNumbersService = new PrimeNumbersServiceImpl();
        primeNumbersService.printPrimeNumbers(1_000_000, 8);
        Assert.assertEquals(getCounter(), 78498);
        System.out.println("--------");
    }


    @Test(expected = InputMismatchException.class)
    public void testPrimeNumbersService_InputMismatchExceptionExpected() {
        PrimeNumbersService primeNumbersService = new PrimeNumbersServiceImpl();
        primeNumbersService.printPrimeNumbers(1_000_000, 0);

    }


    private int getCounter() throws Exception {
        Field counterField = PrimeNumbersServiceImpl.class.getDeclaredField("counter");
        counterField.setAccessible(true);
        AtomicInteger value = (AtomicInteger) counterField.get(primeNumbersService);
        return value.get();
    }

}
