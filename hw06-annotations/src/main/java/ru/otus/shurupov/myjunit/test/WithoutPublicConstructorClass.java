package ru.otus.shurupov.myjunit.test;

import ru.otus.shurupov.myjunit.annotations.Test;

public class WithoutPublicConstructorClass {

    private WithoutPublicConstructorClass() {
    }

    @Test
    public void test1() {
        System.out.println("This is a simple test");
    }
}
