# SPRING WEB MVC SETTING 12 - HTTP 메시지 컨버터 JSON

* 스프링 부트는 jackson2Present를 기본적으로 가지고있다.
* 따라서 자동으로 해당 컨버터를 가져온다.



**예제**

**Controller**

```java
@RestController
public class SampleController {
    
	...
        
    @GetMapping("/jsonMessage")
    public Person jsonMessage(@RequestBody Person person) {
        return person;
    }
}
```

<br>

<br>

**Test**

```java
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void jsonMessage() throws Exception {
        Person person = new Person();
        person.setId(2019l);
        person.setName("haunm");

        String jsonString = objectMapper.writeValueAsString(person);

        /*
            accept : 요청에 대한 응답은 어떤 것을 원하는지를 알려준다.
        * */
        this.mockMvc.perform(get("/jsonMessage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(jsonString))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
```



**결과**

* 요청

  ```
  MockHttpServletRequest:
        HTTP Method = GET
        Request URI = /jsonMessage
         Parameters = {}
            Headers = [Content-Type:"application/json;charset=UTF-8", Accept:"application/json", Content-Length:"26"]
               Body = {"id":2019,"name":"haunm"}
      Session Attrs = {}
  ```

* 응답

  ```
  MockHttpServletResponse:
             Status = 200
      Error message = null
            Headers = [Content-Type:"application/json"]
       Content type = application/json
               Body = {"id":2019,"name":"haunm"}
      Forwarded URL = null
     Redirected URL = null
            Cookies = []
  ```

  