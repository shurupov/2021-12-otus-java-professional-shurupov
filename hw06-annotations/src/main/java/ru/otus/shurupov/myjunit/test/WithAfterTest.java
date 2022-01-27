package ru.otus.shurupov.myjunit.test;

import ru.otus.shurupov.myjunit.annotations.After;
import ru.otus.shurupov.myjunit.annotations.Test;

public class WithAfterTest {

    @After
    public void end() {
        System.out.println("This is an end method");
    }

    @Test
    public void test1() {
        System.out.println("This is a simple test");
    }

    @Test
    public void test2() throws Exception {
        System.out.println("This is a test with exception");
        throw new Exception("Just exception");
    }
}
