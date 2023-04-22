# Spring Boot のサンドボックス

SpringBootのあれやこれやを試すプロジェクト

- 他のディレクトリは個別の内容を検証するためのものとする。
- なので、ここはどこに入れたらわからないとかプロジェクト作るまでもないものを主な対象にする

## 環境

- Java17
- SpringBoot2.7

## Refs

- [spring initializer](https://start.spring.io/)
- [Spring Boot Maven プラグインのドキュメント](https://spring.pleiades.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/)
  - [6. OCI イメージのパッケージ化](https://spring.pleiades.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle/#build-image)

## Spring Boot Plugin で OCI イメージを作成する

```sh
./mvnw clean spring-boot:build-image
```
