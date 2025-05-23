package com.myprojecticaro.poc_reflections_api.domain.model;

import java.util.Objects;

import com.myprojecticaro.poc_reflections_api.infrastructure.annotation.Info;

@Info(author = "Bruno", date = "2025-04-10")
public class Person {
	
    private String name;
    
    private int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private String greet() {
        return "Hi, my name is " + name + " and I'm " + age + " years old.";
    }

    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    
    public void setAge(int age) { this.age = age; }

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return age == other.age && Objects.equals(name, other.name);
	}
}
