# Student Payment App — compatible JDK 23 / Maven 3.9.9 / Angular 17

Projet complet conforme à l’Activité 4 : backend Spring Boot, frontend Angular Material, gestion des étudiants, paiements, upload PDF, Swagger, service/DTO/mapper, JWT, guards Angular.

## Versions ciblées

Backend :

```text
Java: 23.0.1
Maven: 3.9.9
Spring Boot: 3.5.14
Lombok: 1.18.46
Maven Compiler Plugin: 3.14.1
springdoc-openapi-starter-webmvc-ui: 2.8.17
```

Frontend :

```text
Angular CLI: 17.3.x
Angular: 17.3.x
Node recommandé: 20.x
npm: version fournie avec Node 20
```

## Vérifier tes versions sur macOS

```bash
java -version
/usr/libexec/java_home -V
mvn -version
node -v
npm -v
ng version
```

Ton Maven actuel doit afficher quelque chose proche de :

```text
Apache Maven 3.9.9
Java version: 23.0.1
OS: mac os x
```

## Ouvrir le backend dans IntelliJ IDEA

Ouvre uniquement ce dossier :

```text
student-payment-app/payment-backend
```

Ne lance pas depuis le dossier racine `student-payment-app`.

Dans IntelliJ IDEA :

```text
File > Open > payment-backend/pom.xml > Open as Project
```

Réglages IntelliJ :

```text
File > Project Structure > Project
SDK: JDK 23
Language level: 23
```

```text
Settings > Build, Execution, Deployment > Build Tools > Maven > Importing
JDK for importer: JDK 23
```

```text
Settings > Build, Execution, Deployment > Build Tools > Maven > Runner
JRE: JDK 23
```

## Lancer le backend

Depuis le terminal IntelliJ :

```bash
cd payment-backend
mvn clean compile
mvn spring-boot:run
```

Si tu es déjà dans le dossier `payment-backend` :

```bash
mvn clean compile
mvn spring-boot:run
```

URLs :

```text
Backend:  http://localhost:8080
Swagger:  http://localhost:8080/swagger-ui/index.html
H2:       http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:payments-db
User:     sa
Password: vide
```

## Stockage PDF macOS

Les reçus PDF sont sauvegardés dans :

```java
System.getProperty("user.home") + "/inset_data/payments/"
```

Le dossier est créé automatiquement si absent.

## Lancer le frontend

Angular 17 fonctionne correctement avec Node 20.

```bash
nvm install 20
nvm use 20
npm install -g @angular/cli@17
```

Puis :

```bash
cd payment-frontend
npm install
ng serve -o
```

URL :

```text
http://localhost:4200
```

Comptes de test :

```text
admin / 1234    rôles ADMIN, USER
user1 / 1234    rôle USER
```

## Commande Angular demandée dans le cahier des charges

Le projet est déjà généré, mais la commande correcte est :

```bash
ng new payment-frontend --standalone false --routing --style css
ng add @angular/material
```

## Endpoints principaux

Auth :

```http
POST /auth/login
```

Payments :

```http
GET /api/payments
GET /api/payments/{id}
GET /api/payments/student/{studentCode}
GET /api/payments/program/{programId}
GET /api/payments/status/{status}
GET /api/payments/type/{type}
POST /api/payments multipart/form-data
PATCH /api/payments/{id}/status
GET /api/payments/{id}/file
```

Students :

```http
GET /api/students
GET /api/students/{code}
GET /api/students/program/{programId}
GET /api/students/search?keyword=...
```

## Si IntelliJ garde une ancienne configuration

Supprime le cache local du module :

```bash
cd payment-backend
rm -rf target .idea *.iml
```

Puis rouvre :

```text
File > Open > payment-backend/pom.xml > Open as Project
```

Recharge Maven :

```text
Maven panel > Reload All Maven Projects
Build > Rebuild Project
```
