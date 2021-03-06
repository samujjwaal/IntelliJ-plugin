package com.samujjwaal.hw1ProjectFiles;

import java.io.IOException;

abstract class AbstractDesignPatternGenerator {

    protected  String[] designPatterns = {"Singleton","Abstract Factory","Builder","Factory Method","Prototype",
            "Adapter","Bridge","Composite","Decorator","Facade","Flyweight","Proxy",
            "Chain of Responsibility","Command","Interpreter","Iterator","Mediator","Memento","Observer","State","Strategy","Visitor","Template Method"};

    abstract void displayDesignPatterns();

    abstract void generateDesignPattern() throws IOException;

    abstract void designPatternFactory() throws IOException;
}
