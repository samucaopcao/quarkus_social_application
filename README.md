<<<<<<< HEAD
# quarkus_social Project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus_social-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
=======

# Sejam bem vindos(as) ao meu repositório : quarkus_social_application


- :mortar_board: Este repositório foi construido a partir do Curso Aprenda Quarkus e Desenvolva API'S RESTful Poderosas em Java ministrado pelo professor Dougllas Sousa na plataforma Udemy.  
- :construction: Foi utilizado para construção as seguintes ferramentas: JDK 11, Eclipse, Maven 3.8.3.
- :hammer: Tecnologias do projeto : Java.  
- :memo: Será realizada uma pequena rede social onde contaremos com usuários que poderão escrever pequenos textos (Crud de USERS), teremos postagens em formato de texto com data e hora, e também seguidores. 

<h2>Algumas informações</h2>

<p align="center">
<img align="center" width="305" height="150" src="https://design.jboss.org/quarkus/logo/final/PNG/quarkus_logo_vertical_rgb_1280px_default.png">
</p>
<br>

Quarkus é um  framework Java nativo em Kubernetes e de stack completo que foi  criado em 20 de Março de 2019 para máquinas virtuais Java (JVMs) e compilação nativa. Ele otimiza essa linguagem especificamente para containers, fazendo com que essa tecnologia seja uma plataforma eficaz para ambientes serverless, de nuvem e Kubernetes.
>>>>>>> 66602bb1b92653fa229cc6ceb54b69477f6528d3
