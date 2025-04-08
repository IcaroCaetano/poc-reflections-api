# poc reflections

Reflections from the java.lang package is a powerful API for accessing and manipulating objects, classes, methods, fields, constructors, and arrays at runtime.

## Classes and Interfaces of the java.lang.reflect package

### 1. Class<T> (in java.lang)
- Although it is in java.lang, it is the basis of Reflection.

- It allows you to obtain type information at runtime.


## Field manipulation

### 2. Field

- Represents an attribute of a class or interface.  
- Allows:  
  	- Get the name, type and modifiers (public, private, etc.).  
  	- Read and modify values, even if private (via `setAccessible(true)`).

	

## Summary of main classes/interfaces

| Element           | Description                                                                   |
|-------------------|-------------------------------------------------------------------------------|
| `Class<T>`        | Represents the structure of a class at runtime                                |
| `Field`           | Represents fields (attributes)                                                |
| `Method`          | Represents methods                                                            |
| `Constructor<T>`  | Represents constructors                                                       |
| `Parameter`       | Represents parameters of methods/constructors                                 |
| `Type`            | Base interface for generic types                                              |
| `ParameterizedType` | Type with generics (e.g., `List<String>`)                                  |
| `TypeVariable`    | Type variables (e.g., `<T>`)                                                  |
| `WildcardType`    | Wildcard types (`?`, `? extends`, etc.)                                       |
| `GenericArrayType`| Array of a generic type (e.g., `T[]`)                                         |
| `AnnotatedElement`| Interface for elements that can have annotations                              |
| `Modifier`        | Utility for analyzing access modifiers (`public`, `private`, `static`, etc.) |
| `Proxy`           | Enables creation of dynamic objects based on interfaces                       |
| `InvocationHandler` | Interface to intercept method calls on proxy instances                     |
