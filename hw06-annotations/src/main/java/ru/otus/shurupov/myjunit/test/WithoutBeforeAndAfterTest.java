package ru.otus.shurupov.myjunit.test;

import ru.otus.shurupov.myjunit.annotations.Test;

public class WithoutBeforeAndAfterTest {
    @Test
    public void test1() {
        System.out.println("This is a simple test");
    }

    @Test
    public void test2() {
        System.out.println("This is a simple test with RuntimeException");
        throw new RuntimeException("This is RuntimeException");
    }

    @Test
    public void test3() {
        System.out.println("This is a simple test with NullPointerException");
        String nothing = null;
        nothing.length();
    }
}
