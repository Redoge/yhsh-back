# How to build:
```shell
./gradlew clean build
```
# How to run:
### Profiles:
- ### Dev
```shell
java -jar yhsh-back-{version}.jar --spring.profiles.active=dev
```
- ### Prod
```shell
java -jar yhsh-back-{version}.jar --spring.profiles.active=prod
```