# 1. 베이스 이미지 설정
FROM eclipse-temurin:17-jdk-alpine

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 빌드된 jar 파일을 컨테이너로 복사
# (Maven은 target/*.jar, Gradle은 build/libs/*.jar)
COPY worker-service/target/*.jar app.jar

# 4. 앱 실행
ENTRYPOINT ["java", "-jar", "app.jar"]