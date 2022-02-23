# Bytecode

## Task
You need to add custom @Log annotation to some method in your class.

Your code should create proxy of your class object on the fly and log parameters of the annotated method during its execution. 

## How to see results

After you built execute the following from the current folder

`java -jar build/libs/bytecode-0.1.jar $(pwd)/build/classes/java/main/ru/shurupov/otus/java/bytecode/process/impl/TestLoggingImpl.class`
