package com.myprojecticaro.poc_reflections_api.domain.model;

import java.util.List;

/**
 * A generic holder class used to demonstrate the use of {@code ParameterizedType}
 * in Java Reflection.
 */
public class GenericHolder {

    /**
     * A list of strings used for generic type inspection.
     */
    private List<String> list;
    
    /**
     * A list of person used for generic type inspection.
     */
    private List<Person> persons;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
    
    
}
