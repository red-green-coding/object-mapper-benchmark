
# Why you should reuse Jackson's ObjectMapper

As freelancers, we encounter a wide array of projects, each with its own unique characteristics and challenges. 
Among the common issues we come across is the less-than-ideal utilization of Jackson's ObjectMapper, often resembling scenarios like this:

```java
public String toJson(SomeDto someObject){
    var mapper = new ObjectMapper();
    return mapper.writeValueAsString(someObject);
}
```

As you can see, every time we need to serialize an instance of SomeDto into a JSON string, we create a new instance of ObjectMapper.
The most extreme instance I've come across featuring this code was nestled within a Hibernate column mapper.

This practice is less than ideal the Jackson documentation is not really clear with recommendations here. 
It is only mentioned in some source code samples, e.g. in [JavaDoc of the ObjectMapper](https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html)

> `final ObjectMapper mapper = new ObjectMapper(); // can use static singleton, inject: just make sure to reuse!`

This why we created this writup, which will allow us to referre to whenever we encounter this usage patterns.

Not reusing an ObjectMapper is less than ideal for two distinct reasons:
- Creating the ObjectMapper itself is an expensive operation. TODO: why? maybe look at a  heap dump?
<img src="object-mapper-memory.png" alt="object mapper inernal state" width="400"/>

- The ObjectMapper employs reflection to seamlessly handle the serialization and deserialization of custom classes to and from JSON. 
For instance, when it encounters a JSON object that needs to be mapped to a custom class, it must determine whether to utilize a constructor with parameters, a no-argument constructor with setter methods, 
or direct property access. 
Remarkably versatile as Jackson is, a combination of these approaches is perfectly valid as well.
The key lies in its ability to construct a well-thought-out strategy for executing the actual mapping process. 
This entails a two-step process: first, it utilizes reflection to gather all properties, methods, and annotations. 
Subsequently, it assembles a mapping strategy.
This strategy is then cached within the ObjectMapper instance.
Whenever the ObjectMapper needs to map a JSON object to the same class again, the strategy is already prepped and ready for deployment, resulting in optimized performance.


Both of these are performance concerns.
If you use the ObjectMapper only sporadically, there's no need to be overly concerned (take a look at the absolute numbers in the benchmarks).
However, if you're dealing with high throughput, it's worth delving further into this topic.
Avoiding these issues is straightforward and can lead to a significant boost in serialization speed (in our benchmarks, we observed a whopping 40-fold increase).
So, read on if you're keen on not wasting CPU cycles and response delays.

## Benchmarks
Source code of the benchmark: https://github.com/red-green-coding/object-mapper-tests

The benchmarks are designed to give you a basic understanding of the performance disparities between reusing the ObjectMapper and generating a new instance for each call.
They employ a relatively modest payload and do not take full advantage of the extensive Jackson features available for customizing the serialization process.
In real-world situations, we anticipate the gap in performance to be even more pronounced.

Payload used to serialize and deserialize
//TODO: keep me up to date
```json
{
  "some": "some",
  "dtoEnum": "B",
  "innerDto": {
    "num": 123,
    "strings": [ "1", "2" ]
  }
}
```

[JMH report](https://jmh.morethan.io/?gist=1d98e83fa1fcab88beaf40caa0ea35be)

TODO: Discuss

## How to avoid creating new ObjectMapper instances

> Mapper instances are fully thread-safe provided that ALL configuration of the instance occurs before ANY read or write calls.

[JavaDoc](https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html)

Meaning as long as we do not change its configuration, after we started to use it, we can share it between multiple threads.
In most cases we recommend to just use a static field like

```java
public final static ObjectMapper mapper = new ObjectMapper().registerModule(new ParameterNamesModule());
```
Of course if you use it inside a singleton like a Spring component, having it as an instance property is fine as well.

If you cannot for some reason be sure that the configuration will not change during runtime, the recommendation is to
> Construct and use ObjectReader for reading, ObjectWriter for writing. Both types are fully immutable ...

[JavaDoc](https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html)

This is a bit less convenient, and we solely see reasons to use it.


## Passing the mapper as a dependency vs using a global reference

```java
public class ServiceClient{
    @Injct ObjectMapper mapper;
    @Inject HttpClient httpClient;
    
    Dto fetchData(){
        var response = httpClient.get("ServiceXYZ/some/url");
        // ... error handling etc
        return mapper.readValueFromString(response.body());
    }
}

/*
        VS
 */

class Json{
    public static ObjectMapper mapper = new ObjectMapper(); 
}

public class ServiceClient{
    @Inject HttpClient httpClient;

    Dto fetchData(){
        var response = httpClient.get("ServiceXYZ/some/url");
        // ... error handling etc
        return Json.mapper.readValueFromString(response.body());
    }
}
```

ServiceClient here is meant to communicate with ServiceXYZ and is supposed to understand exactly their Json dialect.
When we write an integration tests for the first variant, is important to use an ObjectMapper that has the exact same configuration, as the ObjectMapper that will be used during production runtime.
In the second case, we do not have to worry about this.
This is why I prefer the second approach.

An exception might be when you are inside a Spring application and want to use the same mapper that lives inside the spring context and is used by different spring components that are not under your direct controll.

Never ever mock ObjectMapper! 😱


## When to share an ObjectMapper between different components?
When ObjectMappers share the same configuration, there's typically no need to employ separate instances.
However, you might wonder when it becomes necessary to utilize distinct configurations.
In many scenarios, JSON serves as the go-to format for external system communication.
These systems, though, might use JSON in slightly varying manners.
For instance, consider a peculiar legacy system that formats timestamps in an unconventional manner, while other systems adhere to the ISO-8601-compatible string format.
In such situations, opting for ObjectMappers with distinct configurations becomes entirely justifiable.


### Do not use findAndRegisterModules
Avoid using [`mapper.findAndRegisterModules()`](https://fasterxml.github.io/jackson-databind/javadoc/2.7/com/fasterxml/jackson/databind/ObjectMapper.html#findAndRegisterModules()), 
as it will dynamically load any Jackson modules it discovers on the classpath. 
This practice can introduce reliability issues into your tests, especially when they share the same classpath with additional dependencies, which is often the case for unit and integration tests.

# Conclusion
Just make the mapper a static field and reuse it.


## TODOs


Probably out of scope:
## Runtime reflection vs compiletime reflection
Nowadays, there are libraries that can map Json without runtime reflection like [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization).
They typically use a compiler plugin to generate custom mapper at compile time.
This can avoid some of the problems mentioned here and can have even better performance characteristics than Jackson (which is already pretty good).
They are usually a bit less convenient to use and offer far fewer options to configure the mapping, when compared to Jackson.
