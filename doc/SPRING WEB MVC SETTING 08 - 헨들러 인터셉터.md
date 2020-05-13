# SPRING WEB MVC SETTING 08 - 헨들러 인터셉터

* 핸들러 매핑에 설정할 수 있는 인터셉터

* 핸들러

  ```
  // preHandle 1
  // perHandle 2
  // 요청처리
  // postHandler 2
  // postHandler 1
  // 뷰 렌더링
  // afterCompletion 2
  // afterCompletion 1
  ```

  * `preHandle`
    * Handler에 대한 정보를 가지고있다.
      * 어떤 클래스의 어떤 메소드인지...
    * 리턴 값(ture, false)로 헨들러로 요청을 전달할지 여부를 결정한다.
      * false = 요청 전달 안함
      * true = 요청 전달
  * postHandler, afterCompletion은 역순으로 호출
  * 비동기 호출에서는 postHandler와 afterCompletion이 호출되지 않는다.
    * async에 관련된 것들이 호출된다.
  * 순서는 order라는 속성으로 설정할 수 있다.

* Handler에 따라서 로직이 변경되어야 한다면, 헨들러 인터셉터를 이용해 인터셉터를 구현하는 것이 맞을 것이다.

* 그러나, 스프링 mvc와 상관없이 일반적인 용도로 인터셉터를 구현해야한다면, Servlet 필터를 이용하는 것이 맞을 것이다.