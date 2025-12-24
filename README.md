# NewsMoA

> NewsMoA는 어려운 뉴스 기사를 AI를 활용해 쉬운 표현으로 재해석하고, 요약과 용어 설명을 제공하는 뉴스 해석 플랫폼입니다.  
> 이 리포지토리는 NewsMoA 서비스의 **백엔드 서버**를 담당합니다.

## 기술 스택

### Frontend

- **React (CRA 기반)**
    
- **Axios**
    
    - 백엔드 REST API 통신
        

### Backend

- **Spring Boot**
    
- **Spring Web MVC**
    
    - REST API 서버 구현
        
- **Spring Data JPA**
    
    - MySQL 기반 데이터 접근
        
- **Spring Security**
    
    - 인증·인가 및 API 접근 제어
        
- **Spring Test**
    
    - spring-boot-starter-data-jpa-test 포함
        
- **SpringDoc OpenAPI**
    
    - API 문서 자동화
        
- **Gson**
    
    - Gemini API 응답(JSON) 파싱
        
- **Jsoup**
    
    - 네이버 뉴스 기사 크롤링
        
- **OpenCSV**
    
    - 국회의원 명단 CSV 데이터 처리
        

### Database

- **MySQL**
    

### Infra / Deployment

- **Nginx**
    
    - React 빌드 파일 정적 서빙
        
    - `/api` 요청에 대한 리버스 프록시
        

### External / Data Source

- **Naver News**
    
    - 뉴스 기사 크롤링 대상
        
- **Google Gemini API**
    
    - 기사 요약, 재해석 및 단어 설명 기능
        
- **대한민국 국회 공개 데이터**
    
    - 국회의원 명단 CSV 파일을 프로젝트 리소스로 포함하여 사용
        

## 아키텍처

React 기반 Frontend는 Nginx를 통해 정적 파일로 서빙되며,  
`/api` 요청은 Nginx 리버스 프록시를 통해 Backend로 전달됩니다.

Backend(Spring Boot)는 네이버 뉴스와 통신하여 기사를 크롤링하고,  
Gemini API와 연동해 기사 요약 및 쉬운 표현으로의 재해석을 수행합니다.

가공된 데이터는 MySQL에 저장되며,  
Frontend는 Backend API를 통해 이를 조회합니다.

## 주요 기능

- **뉴스 기사 수집**
    
    - 네이버 뉴스에서 미리 정의된 5가지 카테고리의 기사를 주기적으로 크롤링
        
- **AI 기반 기사 요약 및 재해석**
    
    - Gemini API를 활용해 기사 내용을 **요약문**과 **재해석문**으로 각각 변환
        
    - 요약문은 핵심 정보 위주로 압축된 결과 제공
        
    - 재해석문은 전문적이거나 어려운 표현을 쉬운 단어와 문장으로 변환한 결과 제공
        
- **단어 선택 기반 설명 기능**
    
    - 기사 내 특정 단어를 선택하면 문맥에 맞는 설명을 AI에게 요청
        
- **정치 관련 통계 제공**
    
    - 당일 수집된 뉴스 데이터를 기반으로 국회의원 언급 수 랭킹 제공
        
    - 정당별 언급 횟수 통계 제공
        

## API 문서

본 프로젝트의 API 문서는 **SpringDoc OpenAPI(Swagger UI)** 를 통해 제공됩니다.

- 로컬 실행 시: `http://localhost:8080/swagger-ui.html`
    
- 배포 환경: `https://뉴스모아.온라인.한국/swagger-ui.html`
    

각 API의 요청/응답 형식과 파라미터는 Swagger UI에서 확인할 수 있습니다.

## 실행 방법

### 실행 환경

- Java 17 이상
    
- MySQL 8.x
    

### 환경 설정

아래 항목들은 `application.yml` 또는 환경 변수로 설정되어야 합니다.

- MySQL 접속 정보
    
- Gemini API Key
    

### 서버 실행

```
./gradlew bootRun
```

서버 실행 후 Swagger UI를 통해 API를 확인할 수 있습니다.

## 향후 계획

- **사용자 유형별 개인화**
    
    - 현재는 학습 목적의 초등학생을 기준으로 기사 해석 수준을 제공하고 있으나, 향후에는 전문 용어 이해가 어려운 성인, 사회 경험이 적은 고등학생 등 다양한 사용자 유형에 맞춘 개인화된 요약 및 해석 기능을 제공할 계획입니다.
        
- **비용 최적화**
    
    - AI 응답 결과를 캐싱하여 중복 요청을 줄이고, AI API 사용 비용을 효율적으로 관리할 수 있는 구조로 개선할 계획입니다.
        
- **기능 고도화**
    
    - 현재 제공 중인 추천 기사 시스템을 개선하고, 검색 엔진을 고도화하여 뉴스 탐색 정확도를 향상시킬 계획입니다.