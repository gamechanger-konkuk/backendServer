# 백엔드 서버

## 사전 준비

백엔드 서버를 위한 포트 localhost:8080이 사용 중이 아니어야 합니다.

ai 서버가 http://localhost:8000으로 열려 있는 상태여야 합니다.

AWS S3 버킷이 있어야 합니다. 해당 버킷의 정보는 src/main/resources/application-aws.yml에 작성되어야 합니다.

![image](https://github.com/user-attachments/assets/ac52984b-f9ac-44c4-b1e7-5e3899296d2b)

mysql을 설치하고, "tindy"라는 database를 생성해야 합니다.

AWS S3의 접근 권한이 있는 키를 발급해야 합니다. access key와 secret key가 필요합니다.

Liveblocks API에 프로젝트를 생성하고, 해당 프로젝트의 secret key를 발급해야 합니다.

mysql의 ID/PW, S3의 키, Liveblocks의 키는 src/main/resources/application-cridentials.yml에 작성되어야 합니다.

![image](https://github.com/user-attachments/assets/001a369b-977e-4986-8e2d-c9ccf329b6a3)

## 실행 방법

```
git clone http://github.com/gamechanger-konkuk/backendServer.git
cd backendServer
```

### Windows

```
gradlew build
gradlew bootRun
```

### Mac

```
./gradlew build
./gradlew bootRun
```

## api 목록

### Clothes

- POST /clothes/create : 티셔츠 생성
- GET /clothes/view : 모든 티셔츠 조회
- GET /clothes/name/{clothesName} : 특정 티셔츠 조회
- PUT /clothes/name/{clothesName} : 해당 티셔츠 저장
- DELETE /clothes/name/{clothesName} : 해당 티셔츠 삭제

### Image

- POST /clothes/image/generate : 프롬프트를 이용한 이미지 생성
- POST /clothes/image/remove-background : 이미지 배경 제거


