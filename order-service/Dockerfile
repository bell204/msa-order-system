# 1. 베이스 이미지 설정 (Java 17 사용 가정)
FROM eclipse-temurin:17-jdk-alpine

# 2. 시간대 설정 (선택 사항, 로그 시간 맞추기용)
ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 3. 작업 디렉토리 설정
WORKDIR /app

# 4. 빌드된 jar 파일을 컨테이너로 복사
# Maven 빌드 결과물 위치: order-service/target/*.jar
# (만약 Gradle이라면 order-service/build/libs/*.jar 로 수정)
COPY order-service/target/*.jar app.jar

# 5. 컨테이너가 사용할 포트 명시 (문서화 용도)
EXPOSE 8080

# 6. 애플리케이션 실행 명령어
# JVM 옵션(메모리 설정 등)을 추가할 수 있습니다.
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]