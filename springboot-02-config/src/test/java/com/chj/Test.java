package com.chj;

import com.chj.pojo.Cat;
import com.chj.pojo.Dog;
import com.chj.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {

    @Autowired
    private Dog dog;
    @Autowired
    private Person person;
    @Autowired
    private Cat cat;

    @org.junit.jupiter.api.Test
    void dogTest(){
        System.out.println(dog);
    }

    @org.junit.jupiter.api.Test
    void personTest(){
        System.out.println(person);
    }

    @org.junit.jupiter.api.Test
    void catTest(){
        System.out.println(cat);
    }
}
