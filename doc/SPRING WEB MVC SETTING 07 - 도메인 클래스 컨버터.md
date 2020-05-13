# SPRING WEB MVC SETTING 07 - 도메인 클래스 컨버터

> 도메인 클래스 컨버터를 이용하면, 도메인 클래스의 id값을 자동으로 Person으로 컨버팅해줄 수 있다.
>
> 이 컨버터는 Spring data jpa가 제공한다.



#### 적용 전

```JAVA
public class Person {
    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```



```JAVA
@RestController
public class SampleController {

    @GetMapping("hello")
    public String hello(@RequestParam("id") Person person){
        return "hello " + person.getName();
    }
}
```

* SPRING DATA JPA의 도움을 받아서 ID값을 PERSON으로 컨버팅할 수 있다.

* 따라서 SPRING DATA JPA 라이브러리를 가져와야 한다.

<br>



#### 적용 후

* PersonFormatter 삭제

* Person을 Domain class로 변경

  ```java
  @Entity
  public class Person {
  
      @Id @GeneratedValue
      private Long id;
  
      private String name;
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public Long getId() {
          return id;
      }
  
      public void setId(Long id) {
          this.id = id;
      }
  }
  ```

* PersonRepository 생성

  ```java
  public interface PersonRepository extends JpaRepository<Person, Long> {
  }
  ```

* Controller 

  ```java
  @RestController
  public class SampleController {
  
      @GetMapping("hello")
      public String hello(@RequestParam("id") Person person){
          return "hello " + person.getName();
      }
  }
  ```

  * 도메인 클래스 컨버터는 인자로 받은 id값을 자동으로 Person 객체로 변환시켜줄 것이다.

* Test

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest
  @AutoConfigureMockMvc
  public class SampleControllerTest {
  
      @Autowired
      MockMvc mockMvc;
  
      @Autowired
      PersonRepository personRepository;
  
      @Test
      public void hello() throws Exception {
          Person person = new Person();
          person.setName("hanum");
  
          Person savedPerson = personRepository.save(person);
  
          this.mockMvc.perform(get("/hello")
                      .param("id", savedPerson.getId().toString()))
                  .andDo(print())
                  .andExpect(content().string("hello hanum"));
  
      }
  }
  ```

  * 테스트 결과 정상적으로 Converting이 되어 원하는 값을 받아온 것을 확인할 수 있었다.

  * **Test 결과**

    ```
    MockHttpServletResponse:
               Status = 200
        Error message = null
              Headers = [Content-Type:"text/plain;charset=UTF-8", Content-Length:"11"]
         Content type = text/plain;charset=UTF-8
                 Body = hello hanum
        Forwarded URL = null
       Redirected URL = null
              Cookies = []
    ```

    