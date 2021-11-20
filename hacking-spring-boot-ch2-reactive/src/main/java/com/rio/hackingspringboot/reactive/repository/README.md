### Spring Data MongoDB Query Method Name

- Sample Entity
```java
public class Item {
    private @Id String id;
    private String name;
    private String description;
    private double price;
    private String distributorRegion;
    private Date releaseDate;
    private int availableUnits;
    private Point location;
    private boolean active;   
}
```


|Query Method| Description|
|---|---|
|findBy**Description**(...)|description 값이 일치하는 데이터 질의|
|findBy**NameAndDescription**(...)|Name, description 값이 모두 일치하는 데이터 질의|
|findBy**NameAndDistributorRegion**(...)|Name, DistributorRegion 값이 모두 일치하는 데이터 질의|
|find**Top10ByName**(...) <br> find**First10ByName**(...)|name 값이 일치하는 첫 10개 데이터 질의|
|findByName**IgnoreCase**(...)|name 값이 대소문자 구분 없이 일치하는 데이터 질의|
|findByNameAndDescription**AllIgnoreCase**(...)|name, description 값 모두 대소문자 구분없이 일치하는 데이터 질의|
|findByName**OrderByDescriptionAsc**(...)|name 값이 일치하는 데이터를 description 값 기준 오름차순으로 정렬한 데이터 질의|
|findByReleaseDate**Before**(Date date)|releaseDate 값이 date 보다 이전인 데이터 질의|
|findByReleaseDate**After**(Date date)|releaseDate 값이 date 보다 이후인 데이터 질의|
|findByAvailableUnits**GraterThan**(int units)|availableUnits 값이 units 보다 큰 데이터 질의|
|findByAvailableUnits**GraterThanEqual**(int units)|availableUnits 값이 units 보다 크거나 같은 데이터 질의|
|findByAvailableUnits**LessThan**(int units)|availableUnits 값이 units 보다 작은 데이터 질의|
|findByAvailableUnits**LessThanEqual**(int units)|availableUnits 값이 units 보다 작거나 같은 데이터 질의|
|findByAvailableUnits**Between**(int from, int to)|availableUnits 값이 from 과 to 사이에 있는 데이터 질의|
|findByAvailableUnits**In**(Collection unitss)|availableUnits 값이 unitss 컬렉션에 포함돼 있는 데이터 질의|
|findByAvailableUnits**NotIn**(Collection unitss)|availableUnits 값이 unitss 컬렉션에 포함돼 있지 않은 데이터 질의|
|findByName**NotNull**()|name 값이 null 이 아닌 데이터 질의|
|findByName**Null**()<br>findByName**IsNull**()|name 값이 null 인 데이터 질의|
|findByName**Like**(String f)|name 값이 문자열 f를 포함하는 데이터 질의|
|findByName**NotLike**(String f)<br>findByName**IsNotLike**(String f)|name 값이 문자열 f를 포함하지 않는 데이터 질의|
|findByName**StartingWith**(String f)|name 값이 문자열 f로 시작하는 데이터 질의|
|findByName**EndingWith**(String f)|name 값이 문자열 f로 끝나는 데이터 질의|
|findByName**NotContaining**(String f)|name 값이 문자열 f를 포함하지 않는 데이터 질의|
|findByName**Regex**(String pattern)|name 값이 pattern 으로 표현되는 정규 표현식에 해당하는 데이터 질의|
|findByLocation**Near**(Point p, Distance max)|location 값이 p 지점 기준 거리 max 이내에서 가장 가까운 순서로 정렬된 데이터 질의|
|findByLocation**Near**(Point p, Distance min, Distance max)|location 값이 p 지점 기준 거리 min 이상 max 이내에서 가장 가까운 순서로 정렬된 데이터 질의|
|findByLocation**WithIn**(Circle c)|location 값이 원 영역 c 안에 포함돼 있는 데이터 질의|
|findByLocation**WithIn**(Box b)|location 값이 직사각형 영역 b 안에 포함돼 있는 데이터 질의|
|findByActive**IsTrue**()|active 값이 true 인 데이터 질의|
|findByActive**IsFalse**()|active 값이 false 인 데이터 질의|
|findByLocation**Exists**(boolean e)|location 속성의 존재 여부 기준으로 데이터 질의|

- Reference  
[스프링데이터 JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords)  
[스프링데이터 아파치 카산드라](https://docs.spring.io/spring-data/cassandra/docs/current/reference/html/#repository-query-keywords)  
[스프링데이터 피보탈 젬파이어](https://docs.spring.io/spring-data/gemfire/docs/current/reference/html/#repository-query-keywords)
