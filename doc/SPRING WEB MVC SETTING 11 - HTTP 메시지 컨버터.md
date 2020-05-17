# SPRING WEB MVC SETTING 11 - HTTP 메시지 컨버터

**HTTP 메시지 컨버터**

* 아래와 같은 어노테이션에서 사용된다.
  * ex) `@RequestBody`, `@ResponseBody`
* 요청 본문에 있는 문자열을 객체로 변환하거나
* 응답을 응답 본문에 담아 보내거나...

<br><br>

**기본 HTTP 메시지 컨버터**
● 바이트 배열 컨버터
● 문자열 컨버터
● Resource 컨버터
● Form 컨버터 (폼 데이터 to/from MultiValueMap<String, String>)
● (JAXB2 컨버터)
● (Jackson2 컨버터)
● (Jackson 컨버터)
● (Gson 컨버터)
● (Atom 컨버터)
● (RSS 컨버터)

<br><br>

**추가적으로 컨버터를 등록하는 방법**

* 기본으로 등록해주는 컨버터를 모두 무시하고 새 컨버터를 등록하기
  * configureMessageConverters
* 기본으로 등록해주는 컨버터에 새로운 컨버터 추가하기
  * extendMessageConverters
* 의존성 추가로 컨버터 등록하기 (추천)
  * 메이븐 또는 그래들 설정에 의존성을 추가하면 그에 따른 컨버터가 자동으로 등록 된다. 
  * WebMvcConfigurationSupport
  * (이 기능 자체는 스프링 프레임워크의 기능임, 스프링 부트 아님.)