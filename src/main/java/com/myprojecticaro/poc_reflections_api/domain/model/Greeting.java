package com.myprojecticaro.poc_reflections_api.domain.model;
/**
 * A simple interface used to demonstrate dynamic proxies with Reflection.
 */
public interface Greeting {

    /**
     * A method that should return a greeting message.
     *
     * @return a greeting message.
     */
    String sayHello();
}