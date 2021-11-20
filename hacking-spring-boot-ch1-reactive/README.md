## Spring Boot Web Application

- Run

`$ ./mvnw clean spring-boot:run`
- Service Call    

```
$ curl -N -v localhost:8080/server  

data:{"description":"Sweet & sour beef","delivered":true}
data:{"description":"Lo mein noodles, plain","delivered":true}
data:{"description":"Sweet & sour beef","delivered":true}
  
$ curl -N -v localhost:8080/served-dishes     
  
data:{"description":"Sweet & sour beef","delivered":true}
data:{"description":"Lo mein noodles, plain","delivered":true}
data:{"description":"Sweet & sour beef","delivered":true}

```
