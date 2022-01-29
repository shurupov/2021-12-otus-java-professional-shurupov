    package ru.otus.shurupov.myjunit;

import ru.otus.shurupov.myjunit.execution.TestsExecutor;
import ru.otus.shurupov.myjunit.test.*;

public class TestApp {
    public static void main(String[] args) {
        TestsExecutor.start(
            WithoutBeforeAndAfterTest.class,
            WithExceptionInConstructorTest.class,
            WithoutPublicConstructorClass.class,
            WithBeforeTest.class,
            WithAfterTest.class,
            WithBeforeAndAfterTest.class,
            WithExceptionInBeforeTest.class,
            WithPrivateAfterTest.class,
            WithExceptionInAfterTest.class
        );
    }
}
