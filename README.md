# 백엔드 서버

## 사전 준비

백엔드 서버를 위한 포트 localhost:8080이 사용 중이 아니어야 합니다.

ai 서버가 http://127.0.0.1:8082로 열려 있는 상태여야 합니다.

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

## api 설명

### POST

프론트에서 백엔드로 프롬프트를 전송할 때 /start-image-generation으로 post 요청을 보냅니다.

ai 서버에 프롬프트를 전송하는 post 요청을 /submit-text로 보냅니다.

해당 데이터는 json 형식으로 전송하게 됩니다.
```
{
  "prompt": "example"
}
```
ai 서버에서는 해당 요청에 대한 작업 id를 백엔드 서버로 전송합니다.

해당 데이터는 문자열(ai 서버와 조율 가능)로 전송하게 됩니다.

작업 id를 프론트에게 바로 전송합니다.

### GET

ai 서버에서 이미지를 받을 때는 작업 id를 필요로 합니다. 

따라서 프론트에서는 post 요청 시에 받은 작업 id를 기억해야 합니다.

프론트는 백엔드 서버에 /get-image/{task_id}로 get 요청을 보냅니다.

백엔드 서버는 ai 서버에 이미지를 요청하는 get 요청을 /get-image/{task_id}로 보냅니다.

현재 설정은 ai 서버에서 png 파일을 백엔드 서버로 전송합니다.

백엔드 서버는 프론트에게 해당 png 파일을 전송합니다.
