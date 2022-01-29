package ru.otus.shurupov.myjunit.test;

import ru.otus.shurupov.myjunit.annotations.Test;

public class WithExceptionInConstructorTest {

    public WithExceptionInConstructorTest() {
        int i =  5 / 0;
    }

    @Test
    public void test1() {
        System.out.println("This is a simple test");
    }
}
