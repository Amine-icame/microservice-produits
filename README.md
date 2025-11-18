# Microservice Produits

Ce microservice est responsable de la gestion des opérations CRUD sur les produits. Il inclut un endpoint de simulation de délai pour tester la résilience côté client (microservice-commandes).

## 🚀 Technologies Utilisées

-   **Spring Boot 3+**
-   **Spring Web**
-   **Spring Data JPA**
-   **H2 Database**
-   **Lombok**
-   **Spring Cloud Config Client**
-   **Spring Cloud Eureka Client**
-   **Spring Boot Actuator**
-   **Springdoc-openapi / Swagger UI**
-   **Maven**
-   **Java 17+**

## ⚙️ Comment le Lancer ?

1.  **Prérequis :**
    -   `spring-cloud-config-server` doit être lancé (sur `http://localhost:8888`).
    -   `spring-cloud-eureka-server` doit être lancé (sur `http://localhost:8761`).

2.  **Lancement :**
    -   Pour la première instance :
        ```bash
        mvn spring-boot:run
        ```
        (Port 8082, configuré via le Config Server)
    -   Pour une deuxième instance (pour tester le load balancing) :
        ```bash
        mvn spring-boot:run -Dspring-boot.run.profiles=instance2
        ```
        (Assurez-vous d'avoir un `application-instance2.properties` local avec `server.port=8083`)
## H2 Database
<img width="1126" height="472" alt="image" src="https://github.com/user-attachments/assets/33c88ac7-4434-4175-84cb-a195d89f585a" />


## 💡 Configuration

Ce microservice récupère sa configuration depuis le `spring-cloud-config-server` via le fichier `microservice-produits.properties` situé dans le dépôt `microservices-config-repo`.

## 🧪 Endpoints API (via la Gateway ou directement)

Le préfixe pour toutes les routes est `/produits`.
**Adresse directe :** `http://localhost:8082/produits/...` (ou 8083)
**Adresse via Gateway :** `http://localhost:8080/produits/...`

| Méthode | Endpoint                                 | Description                                                                 | Corps de requête (JSON)                                       |
| :------ | :--------------------------------------- | :-------------------------------------------------------------------------- | :------------------------------------------------------------ |
| `GET`   | `/produits`                              | Récupère tous les produits.                                                 | `N/A`                                                         |
| `GET`   | `/produits/{id}`                         | Récupère un produit par son ID.                                           | `N/A`                                                         |
| `POST`  | `/produits`                              | Crée un nouveau produit.                                                 | `{ "nom": "...", "description": "...", "prix": ... }` |
| `PUT`   | `/produits/{id}`                         | Met à jour un produit existant.                                          | `{ "nom": "...", "description": "...", "prix": ... }` |
| `DELETE`| `/produits/{id}`                         | Supprime un produit par son ID.                                           | `N/A`                                                         |
| `GET`   | `/produits/delay/{seconds}`              | Simule un délai de réponse pour tester la résilience côté client.           | `N/A`                                                         |

## 📊 Monitoring et Documentation

-   **Swagger UI :** `http://localhost:8082/swagger-ui.html`
   <img width="1821" height="1016" alt="image" src="https://github.com/user-attachments/assets/0e28c65f-7792-4620-bb79-3456152bec70" />
-  **Swagger UI (instance 2) :** `http://localhost:8083/swagger-ui.html`
   <img width="1827" height="1016" alt="image" src="https://github.com/user-attachments/assets/af0b7fc1-f758-4551-8449-0f919b408340" />
-   **Actuator Endpoints :** `http://localhost:8082/actuator` (inclut `health`, `info`)

---

*Développé par Amine içame/ Salma BenOmar pour le module JEE.*
