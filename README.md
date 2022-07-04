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

Este projeto usa Quarkus, o Supersonic Subatomic Java Framework.

Se você quiser saber mais sobre o Quarkus, visite o site: https://quarkus.io/ .

## Executando o aplicativo no modo dev

Você pode executar seu aplicativo no modo dev que permite a codificação ao vivo usando:
<br>
```shell script
./mvnw compile quarkus:dev
```
<br>

> <strong>**NOTA:</strong>** Quarkus agora vem com uma Dev UI, que está disponível no modo dev apenas em http://localhost:8080/q/dev/.

## Inserindo dependências via linha de comando

mvnw quarkus:add-extensions -Dextensions="jdbc-h2,hibernate-orm,hibernate-orm-panache,"resteasy-jsonb"

## Empacotando e executando o aplicativo

O aplicativo pode ser empacotado usando:
<br>
```shell script
./mvnw package
```
<br>

Ele produz o arquivo `quarkus-run.jar` no diretório `target/quarkus-app/`.
<br>
Esteja ciente de que não é um _über-jar_ pois as dependências são copiadas para o diretório `target/quarkus-app/lib/`.
<br>
O aplicativo agora pode ser executado usando `java -jar target/quarkus-app/quarkus-run.jar`.

Se você deseja construir um _über-jar_, execute o seguinte comando:
<br>
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```
<br>

O aplicativo, empacotado como um _über-jar_, agora pode ser executado usando 
<br>`java -jar target/*-runner.jar`.<br>

## Criando um executável nativo

Você pode criar um executável nativo usando:
<br>
```shell script
./mvnw package -Pnative
```
<br>

Ou, se você não tiver o GraalVM instalado, poderá executar a compilação executável nativa em um contêiner usando:
<br>
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

Você pode então executar seu executável nativo com: `./target/quarkus_social-1.0.0-SNAPSHOT-runner`

Se você quiser saber mais sobre como criar executáveis ​​nativos, consulte https://quarkus.io/guides/maven-tooling.

