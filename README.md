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

## Method manipulation

### 3. Method

- Represents a method of a class or interface.

- Enables:
	- Invoking methods dynamically.
	- Reading return type, parameters and annotations.
	- Accessing private methods with setAccessible(true).	
	
	
## Constructor manipulation

## 4. Constructor

- Represents a class constructor.

- Enables:
	- Creating instances even with private constructors.
	- Inspecting constructor parameters and annotations.
	
## Annotations

### 5. Annotation

- Using getAnnotations() or getDeclaredAnnotations(), you can read metadata at runtime.

- Useful for frameworks (like Spring) that act based on annotations.

## Method Parameters

### 6. Parameter

- Represents the parameters of a method or constructor.

- Allows:
	- Accessing parameter names (with -parameters flag during compilation).
	- Analyzing types and annotations per parameter.
	
## Modifiers

### 7. Modifier

- Utility class for analyzing access modifiers.

- Example: Modifier.isPrivate(method.getModifiers())

- Converts integer representation of modifiers to readable strings (public, private, final, etc.).

## Dynamic Proxy & InvocationHandler

### 8. Dynamic Proxy + InvocationHandler

- Allows you to create objects dynamically that implement interfaces.

- Used in AOP, interceptors, mocking frameworks.

- Example:

```
Greeting proxy = (Greeting) Proxy.newProxyInstance(
    Greeting.class.getClassLoader(),
    new Class[]{Greeting.class},
    (proxyObj, method, args) -> "Hello from proxy!"
);
```


## 9. ParameterizedType (Generics)

### Use getGenericType() to inspect generic types at runtime.

- Useful to discover that a field is, for example, List<String> rather than just List.


## 10. Advanced Type Inspection

### Type

- Super interface for all types in reflection.

- Used with generics, arrays, wildcards, etc.

### ParameterizedType

- Represents a generic type, like List<String>.

- Use getActualTypeArguments() to inspect contained types.

### TypeVariable

- Represents type variables like <T>.

- Can be used to understand type parameters of classes/methods.

### WildcardType

- Represents wildcard types like ? or ? extends Number.

- Use getUpperBounds() and getLowerBounds().

### GenericArrayType

- Represents arrays of generic types like T[].

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
