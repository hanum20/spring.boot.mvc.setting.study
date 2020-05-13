# SPRING WEB MVC SETTING 09 - 헨들러 인터셉터 구현



#### HandlerInterceptor

**GreatingInterceptor**

```java
public class GreatingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle 1");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle 1");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 1");
    }
}
```



**AnotherInterceptor**

```java
public class AnotherInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle 2");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle 2");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 2");
    }
}
```





#### Handler 등록

**기본 등록**

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreatingInterceptor());
        registry.addInterceptor(new AnotherInterceptor());
    }
}
```

* 추가된 순서대로 order가 된다.

**Test 결과**

```
preHandle 1
preHandle 2
postHandle 2
postHandle 1
afterCompletion 2
afterCompletion 1
```

<br><br>



**order 속성 설정**

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreatingInterceptor()).order(0);
        registry.addInterceptor(new AnotherInterceptor()).order(-1);
    }
}
```



**결과**

```
preHandle 2
preHandle 1
postHandle 1
postHandle 2
afterCompletion 1
afterCompletion 2
```

<br><br>



**패턴 추가**

```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GreatingInterceptor()).order(0);
        registry.addInterceptor(new AnotherInterceptor())
                .addPathPatterns("/hi")
                .order(-1);
    }
}
```



**결과**

```
preHandle 1
postHandle 1
afterCompletion 1
```

* `/hello ` 요청에 동작하는 Interceptor가 GreatingInterceptor 하나라는 것을 확인할 수 있다.

