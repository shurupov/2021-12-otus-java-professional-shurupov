package ru.otus.shurupov.myjunit.test;

import ru.otus.shurupov.myjunit.annotations.Before;
import ru.otus.shurupov.myjunit.annotations.Test;

public class WithBeforeTest {

    @Before
    public void init() {
        System.out.println("This is an init method");
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
