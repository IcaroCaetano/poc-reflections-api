package com.myprojecticaro.poc_reflections_api.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myprojecticaro.poc_reflections_api.domain.model.Person;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@RestController
@RequestMapping("/reflection")
public class ReflectionController {

	/** Listing class fields */
    @GetMapping("/fields")
    public String listFields() {
    	
        StringBuilder sb = new StringBuilder();
        Field[] fields = Person.class.getDeclaredFields();
        for (Field field : fields) {
            sb.append(field.getName()).append(" (").append(field.getType().getSimpleName()).append(")\n");
        }
        return sb.toString();
    }

    /** Invoking private methods */
    @GetMapping("/invokePrivateMethod")
    public String invokePrivateMethod() throws Exception {
        Person person = new Person("Bruno", 30);

        Method method = Person.class.getDeclaredMethod("greet");
        method.setAccessible(true);

        return (String) method.invoke(person);
    }

    /** Accessing and modifying private class fields */
    @GetMapping("/modifyField")
    public String modifyPrivateField() throws Exception {
        Person person = new Person("Jessica", 25);

        Field field = Person.class.getDeclaredField("name");
        field.setAccessible(true);
        field.set(person, "Icaro");

        return "Updated name: " + person.getName();
    }
}