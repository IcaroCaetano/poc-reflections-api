package com.myprojecticaro.poc_reflections_api.infrastructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myprojecticaro.poc_reflections_api.domain.model.Greeting;
import com.myprojecticaro.poc_reflections_api.domain.model.Person;
import com.myprojecticaro.poc_reflections_api.infrastructure.annotation.Info;
import com.myprojecticaro.poc_reflections_api.domain.model.GenericHolder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

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
     * Displays the parameter details of selected methods in the {@code Person} class
     * using Java Reflection. This example includes both {@code setName(String name)}
     * and {@code setAge(int age)} methods.
     *
     * <p>
     * It demonstrates how to:
     * <ul>
     *   <li>Retrieve method parameters at runtime</li>
     *   <li>Access parameter types and names (if available at compile-time)</li>
     * </ul>
     *
     * <p><strong>Note:</strong> To see actual parameter names (like {@code name} and {@code age}),
     * the code must be compiled with the {@code -parameters} flag.
     *
     * @return A string describing the name and type of each parameter from the inspected methods.
     * @throws Exception If the method is not found via reflection.
     */
    @GetMapping("/methodParams")
    public String methodParameters() throws Exception {
        StringBuilder sb = new StringBuilder();

        // Method: setName(String name)
        Method setNameMethod = Person.class.getMethod("setName", String.class);
        Parameter[] nameParams = setNameMethod.getParameters();
        sb.append("Method: ").append(setNameMethod.getName()).append("\n");
        for (Parameter param : nameParams) {
            sb.append("  → Parameter: ").append(param.getName())
              .append(" (Type: ").append(param.getType().getSimpleName()).append(")\n");
        }
        sb.append("\n");

        // Method: setAge(int age)
        Method setAgeMethod = Person.class.getMethod("setAge", int.class);
        Parameter[] ageParams = setAgeMethod.getParameters();
        sb.append("Method: ").append(setAgeMethod.getName()).append("\n");
        for (Parameter param : ageParams) {
            sb.append("  → Parameter: ").append(param.getName())
              .append(" (Type: ").append(param.getType().getSimpleName()).append(")\n");
        }

        return sb.toString();
    }

    /**
     * Displays the access modifiers of the {@code Person} class and its methods,
     * demonstrating both public and private modifiers using reflection.
     *
     * @return A string with detailed descriptions of class and method modifiers.
     * @throws Exception If a method is not found via reflection.
     */
    @GetMapping("/modifiers")
    public String listModifiers() throws Exception {
        StringBuilder sb = new StringBuilder();

        // Get modifiers
        int classModifiers = Person.class.getModifiers();
        sb.append("Class Modifiers:\n");
        sb.append("  → ").append(Modifier.toString(classModifiers)).append("\n\n");

        // public method
        Method publicMethod = Person.class.getMethod("getName");
        int publicModifiers = publicMethod.getModifiers();
        sb.append("Public Method (getName) Modifiers:\n");
        sb.append("  → ").append(Modifier.toString(publicModifiers)).append("\n\n");

        // private method
        Method privateMethod = Person.class.getDeclaredMethod("greet");
        int privateModifiers = privateMethod.getModifiers();
        sb.append("Private Method (greet) Modifiers:\n");
        sb.append("  → ").append(Modifier.toString(privateModifiers)).append("\n\n");

        sb.append("Note:\n")
          .append("  - Public methods can be accessed normally.\n")
          .append("  - Private methods require setAccessible(true) to be invoked via reflection.\n");

        return sb.toString();
    }

    
    /**
     * Lists all annotations present on the {@code Person} class with detailed attribute names.
     *
     * @return A string with each annotation and its field values (if available).
     */
    @GetMapping("/annotations")
    public String listAnnotations() {
        StringBuilder sb = new StringBuilder();
        Annotation[] annotations = Person.class.getAnnotations();
        for (Annotation annotation : annotations) {
            String annotationName = annotation.annotationType().getSimpleName();
            sb.append("Annotation: ").append(annotationName).append("\n");

            if (annotation instanceof Info info) {
                sb.append("  → Info.author: ").append(info.author()).append("\n");
                sb.append("  → Info.date: ").append(info.date()).append("\n");
            }
        }

        return sb.toString();
    }
    

    /**
     * Demonstrates creating a dynamic proxy using the {@code Proxy} class and
     * an {@code InvocationHandler}.
     *
     * @return A greeting message returned by the proxy.
     */
    @GetMapping("/dynamicProxy")
    public String createProxy() {
        Greeting proxy = (Greeting) Proxy.newProxyInstance(
                Greeting.class.getClassLoader(),
                new Class[]{Greeting.class},
                (proxyObj, method, args) -> "Hello from proxy! Method: " + method.getName()
        );

        return proxy.sayHello();
    }
    
    /**
     * Demonstrates how to retrieve the generic type information of a field using {@code ParameterizedType}.
     *
     * @return The name of the generic type argument, if any.
     * @throws Exception If the field is not found or inaccessible.
     */
    @GetMapping("/genericType")
    public String genericType() throws Exception {
        Field field = GenericHolder.class.getDeclaredField("persons");
        Type type = field.getGenericType();

        if (type instanceof ParameterizedType pt) {
            Type actualType = pt.getActualTypeArguments()[0];
            return "Generic type of 'list': " + actualType.getTypeName();
        }

        return "Not a parameterized type";
    }
}