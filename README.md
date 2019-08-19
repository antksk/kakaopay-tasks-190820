# 개발 프레임워크
- SpringBoot 2.x
- KOMORAN 3.3.4 : keyword 생성
- guava 28.0
- commons-lang3 3.9
- jackson-dataformat-csv 2.9.9
- db : h2 

# 문제해결 전략
### Task2
- 서비스 지역 정보는 "통계분류 포털" 사이트에서 행정구역분류 정보를 참고함
- 프로그램 소개의 키워드 검색과 프로그램 상세 정보의 키워드 검색을 다르게 구성함
  - 프로그램 상세 정보는 비교할 택스트가 많을 것으로 생각되어, KOMORAN을 사용하여 
  키워드를 미리 뽑아서 keywordJson 필드에 저장 후 검색하는 방식을 사용하음
  - 키워드 검색 대상은 명사, 동사 형태를 사용함
- CSV 파일 로드는 jackson-dataformat-csv를 사용하였으며, task2.integration.csv 패키지에서 확인 가능함
- CSV 파일이 생성되고 나면, 생성된 객체를 기반으로 ToEntity 인터페이스를 통해, Entity 클래스를 생성하여 DB에 저장함
- DB에 저장될때, 키워드 생성(keywordJson) 및 서비스 지역 정보(FormalServiceRegion)가 설정됨 

# 빌드 및 실행 방법
### 테스트
- 테스트 케이스는 integration 테스트와 unit 테스트로 구성되어 있음
- task2.integration 패키지에 있는 테스트는 db와 연결되어 있거나 서비스등의 테스트가 필요할때 사용됨
- task2.unit은 순수하게 자바 코드 테스트용으로 사용함
- 

### 빌드 및 실행
- 특별히 빌드 환경을 구성할 필요 없이 Java8 이상에서 동작할수 있도록 구성하였음
  - 만약 intellij를 사용하는 경우, lomback를 사용하고 있기 때문에 다음의 경로의 설정이 필요함
  - cmd + , > build > compiler > Annotaion Process > Enable anntation processing(활성화 필요)
- 실행 환경은 profile 별로 구분하여, local 환경으로 구성함
- local 환경은 h2 mem으로 구성하여 휘발성으로 비즈니스 로직만 테스트 진행함
