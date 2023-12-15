### Mysql DB SETUP
- docker 환경이 구축 되어 있다는 가정 하에 작성 했습니다.
 ```bash
# 현재 경로가 프로젝트 루트 경로라는 가정
docker compose up -d
# compose down 및 볼륨 삭제 : docker compose down -v
```
- docker compose up -d : 도커 컨테이너를 해당 레포지토리의 docker-compose.yaml 파일 기준으로 생성, 도커 이미지가 없을 시 자동 pulling 됩니다.
- docker compose down (-v) : 도커 컨테이너를 내립니다. -v 옵션을 추가할 경우 데이터베이스 볼륨도 함께 삭제 됩니다.

### 사용 기술 스텍
- 언어 : JAVA (JDK 17 Zulu)
- 프레임워크 : Spring Boot (3.1.6)
- 데이터베이스 : Mysql (8.0.19)
- ORM : Spring Data JPA (latest)
- 빌드 도구 : Gradle
- API문서화 : Stringdoc-OpenAPI