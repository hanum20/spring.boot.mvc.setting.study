# SPRING WEB MVC SETTING  06 - WebMvcConfigurer 1부 formatter



#### Controller

```java
@RestController
public class SampleController {

    @GetMapping("hello/{name}")
    public String hello(@PathVariable("name") Person person){
        return "hello " + person.getName();
    }
}
```

* name을 person으로 변환시키도록 formatter가 필요하다.



#### formatter

```java
public class PersonFormatter implements Formatter<Person> {
    @Override
    public Person parse(String s, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(s);
        return person;
    }

    @Override
    public String print(Person person, Locale locale) {
        return person.toString();
    }
}
```



#### formatter를 등록하는 방법들

##### Config로 등록하기

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PersonFormatter());
    }
}
```

##### Anotation으로 등록하기(Spring Boot)

```java
// @Component로 bean으로 등록하면 자동으로 주입된다.
@Component
public class PersonFormatter implements Formatter<Person> {
    @Override
    public Person parse(String s, Locale locale) throws ParseException {
        Person person = new Person();
        person.setName(s);
        return person;
    }

    @Override
    public String print(Person person, Locale locale) {
        return person.toString();
    }
}
```

