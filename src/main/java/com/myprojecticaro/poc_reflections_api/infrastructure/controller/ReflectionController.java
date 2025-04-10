package com.myprojecticaro.poc_reflections_api.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myprojecticaro.poc_reflections_api.domain.model.Person;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;

/**
 * REST controller for demonstrating and studying Java Reflection API features.
 * 
 * <p>This controller provides endpoints to explore various capabilities of the
 * {@code java.lang.reflect} package, such as accessing class metadata, fields,
 * methods, annotations, dynamic proxies, and generic type introspection.</p>
 * 
 * <p>All operations use the {@code Person} and related helper classes.</p>
 * 
 * @author You
 */
@RestController
@RequestMapping("/reflection")
public class ReflectionController {

	 /**
     * Lists all declared fields of the {@code Person} class, including private ones.
     * 
     * @return A string containing each field's name and type.
     */
    @GetMapping("/fields")
    public String listFields() {
    	
        StringBuilder sb = new StringBuilder();
        Field[] fields = Person.class.getDeclaredFields();
        for (Field field : fields) {
            sb.append(field.getName()).append(" (").append(field.getType().getSimpleName()).append(")\n");
        }
        return sb.toString();
    }

    /**
     * Invokes the private {@code greet} method of a {@code Person} instance via reflection.
     * 
     * @return The result of the invoked method.
     * @throws Exception If the method cannot be accessed or invoked.
     */
    @GetMapping("/invokePrivateMethod")
    public String invokePrivateMethod() throws Exception {
        Person person = new Person("Bruno", 30);

        Method method = Person.class.getDeclaredMethod("greet");
        method.setAccessible(true);

        return (String) method.invoke(person);
    }

    /**
     * Accesses and modifies a private field ({@code name}) of the {@code Person} class.
     * 
     * @return The updated name after reflection modification.
     * @throws Exception If the field cannot be accessed or modified.
     */
    @GetMapping("/modifyField")
    public String modifyPrivateField() throws Exception {
        Person person = new Person("Jessica", 25);

        Field field = Person.class.getDeclaredField("name");
        field.setAccessible(true);
        field.set(person, "Icaro");

        return "Updated name: " + person.getName();
    }
    
    /**
     * Instantiates a {@code Person} object using its constructor via reflection.
     * 
     * @return A string describing the newly created person.
     * @throws Exception If the constructor cannot be found or used.
     */
    @GetMapping("/instantiate")
    public String instantiateObject() throws Exception {
        Constructor<?> constructor = Person.class.getConstructor(String.class, int.class);
        
        Person person = (Person) constructor.newInstance("Denis", 28);
        return "Created: " + person.getName() + ", age " + person.getAge();
    }
    
    /**
     * Lists the parameters of the {@code setName} method in {@code Person}.
     * 
     * @return A string representation of the method parameters.
     * @throws Exception If the method is not found.
     */
    @GetMapping("/methodParams")
    public String methodParameters() throws Exception {
        Method method = Person.class.getMethod("setName", String.class);
        
        Parameter[] parameters = method.getParameters();
        return Arrays.toString(parameters);
    }

    /**
     * Displays the access modifiers of the {@code Person} class and its {@code getName} method.
     * 
     * @return A string containing the class and method modifiers.
     * @throws Exception If the method is not found.
     */
    @GetMapping("/modifiers")
    public String listModifiers() throws Exception {
    	
        int classModifiers = Person.class.getModifiers();
        
        Method method = Person.class.getMethod("getName");
        int methodModifiers = method.getModifiers();
        
        return "Class modifiers: " + Modifier.toString(classModifiers) +
               "\nMethod modifiers: " + Modifier.toString(methodModifiers);
    }
}