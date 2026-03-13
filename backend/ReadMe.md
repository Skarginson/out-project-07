# Backend – Spring Boot (H2 Database)

Ce projet constitue la partie backend de l’application, développé avec **Spring Boot** et utilisant une **base de données H2 en mémoire** pour le développement et les tests.

---
## 🚀 Prérequis

Avant de lancer le projet, assure-toi d’avoir installé :

- **Java 21+**
- **Maven 3.8+**
- (Optionnel) Un IDE comme IntelliJ IDEA, Eclipse ou VS Code

Vérification rapide :

```bash
java -version
mvn -version
```

## 🚀 Démarrer le serveur de développement

- Pour lancer l’application localement, exécute :

```bash
mvn spring-boot:run
```

- Une fois le serveur démarré, l’API sera accessible à l’adresse :

```bash
http://localhost:8080/
```

- La console web est activée et accessible ici :
```bash
http://localhost:8080/h2-console
```
Paramètres de connexion par défaut :

    - JDBC URL : jdbc:h2:mem:outprojectdb

    - Username : sa

    - Password : (vide)

##  🧪 Tests & Couverture – JaCoCo
- ▶️ Lancer les tests
    ```bash
        mvn test
    ```
- 📊 Générer le rapport JaCoCo

  Le rapport HTML est généré automatiquement après les tests :
  target/site/jacoco/index.html


## 📚 Ressources utiles
- Spring Boot : https://spring.io/projects/spring-boot

- JPA / Hibernate : https://spring.io/guides/gs/accessing-data-jpa

- H2 Database : https://www.h2database.com