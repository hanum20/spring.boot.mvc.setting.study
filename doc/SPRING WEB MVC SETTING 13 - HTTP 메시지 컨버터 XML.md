# SPRING WEB MVC SETTING 13 - HTTP 메시지 컨버터 XML

* 의존성만 추가하면, 자동으로 컨버터가 주가된다.

  ```XML
  <dependency>
      <groupId>javax.xml.bind</groupId>
      <artifactId>jaxb-api</artifactId>
  </dependency>
  <dependency>
      <groupId>org.glassfish.jaxb</groupId>
      <artifactId>jaxb-runtime</artifactId>
  </dependency>
  <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-oxm</artifactId>
      <version>${spring-framework.version}</version>
  </dependency>
  ```

  * javax.xml.bind
    * jaxb 인터페이스
  * org.glassfish.jaxb
    * glassfish가 구현
    * 인터페이스의 구현체
  * spring-oxm
    * XML을 객체로, 객체를 XML로 변환해주는...
  * Marshalling, unmashalling 용어

<br>

<br>

**빈 등록**

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
        // @XmlRootElement를 찾는다.
        jaxb2Marshaller.setPackagesToScan(Person.class.getPackage().getName());
        return jaxb2Marshaller;
    }
}
```

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

    @Autowired
    Marshaller marshaller;

    @Test
    public void xmlMessage() throws Exception {
        Person person = new Person();
        person.setId(2019l);
        person.setName("hanum");

        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        marshaller.marshal(person, result);
        String xmlString = stringWriter.toString();

        this.mockMvc.perform(get("/jsonMessage")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(xmlString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("person/name").string("hanum"))
                .andExpect(xpath("person/id").string("2019"));
    }
    
    ...
}
```

