<!-- # Springboot-MyBatis-Recruitment-Project -->
<h1> 👩‍👩‍👦‍👦HIGHRE란?👩‍👩‍👦‍👦 <br></h1>

![image](https://user-images.githubusercontent.com/122351733/223630188-a70350c4-3496-4705-bfe3-c031f68d27f3.png)


>'고용하다'라는 의미의 Hire와 '더 높은'이라는 의미의 Higher를
><br>합쳐 고객들에게 더 좋은 일자리를 제공한다는 의미를 담았습니다

<br> 

# 기술스택💡
- JDK 11
- Springboot 2.7.8
- Bootstrap
- MyBatis
- 테스트 H2 DB
- 프로덕션 MySQL DB
- JSP
- redis

<br>

# 아키텍쳐(MVC) 💫

![image](https://user-images.githubusercontent.com/122351733/223648466-ee6ce325-64b3-4f82-888b-5d627e5e55fe.png)

## 패키지 구조
> Controller👩‍💻 Model📚 Service✨ View🎨

- Client의 책임 : View로 부터 응답을 받음 , Model을 거쳐 데이터를 받아 View로 부터 응답

- Service의 책임 : Controller의 책임을 Transaction 을 사용하여 분리 하는 역할 

- Model의 책임 :  DataBase나 File이나 다른 서버로 부터의 통신

- View의 책임 : Client에 그림을 그려줌


<br>

# 기능정리📝
<br>

## <strong>Backend💕
<br>

### 1단계💛

- REST API 구현
- MyBatis ORM 구현

<br>

### 2단계💚

- AOP 
- JWT
- OAuth
- Filter
