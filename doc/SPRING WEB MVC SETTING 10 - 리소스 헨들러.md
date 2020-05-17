# SPRING WEB MVC SETTING 10 - 리소스 헨들러

* **Default Servlet**
  * 정적 리소스를 다루기 위해서는 Default Servlet에 대해 알아야 함.
  * 각각의 톰켓에는 Default Servlet이 존재한다.
  * 정적의 리소스를 처리하는데 사용된다.
  * 디렉토리 목록을 보여주는데 사용된다.
  * `$CATALINA_BASE/conf/web.xml`에 전역적으로 등록되어있다.
  * 스프링은 이렇게 등록되어있는 Default Servlet에 위임해서 정적인 리소스를 처리한다.
  * 스프링 부트는 정적 리소스 헨들러 기능을 기본으로 지원을 한다.
    * 해당 디렉토리 중 하나가 `resource/static`이다.



**예제**

* 정적 리소스

  * `resource/static/index.html`

    ```html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
    <h1>hello index</h1>
    </body>
    </html>
    ```

    

  * `resource/mobile/index.html`

    ```html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
    <h1>hello mobile</h1>
    </body>
    </html>
    ```

    

* **WebConfig**

  ```java
  @Configuration
  public class WebConfig implements WebMvcConfigurer {
  
    ...
  
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
          registry.addResourceHandler("/mobile/**")
                  .addResourceLocations("classpath:/mobile/")
                  .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
      }
  }
  ```

  * 기본적으로는 `resource/static` 하위의 정적 리소스를 이용하지만, 위와 같이 추가적으로 헨들러를 등록할 수 있다.
  * `classpath`는 `resource`와 `java`(?)부터 시작한다.

* **Test**

  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest
  @AutoConfigureMockMvc
  public class SampleControllerTest {
  
      @Autowired
      MockMvc mockMvc;
  
      @Autowired
      PersonRepository personRepository;
  
     ....
  
      @Test
      public void helloStatic() throws Exception {
          this.mockMvc.perform(get("/mobile/index.html"))
                  .andDo(print())
                  .andExpect(status().isOk())
                  .andExpect(content().string(Matchers.containsString("hello mobile")))
                  .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
      }
  }
  ```