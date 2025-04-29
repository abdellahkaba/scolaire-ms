Projet: Microservice paramètrage scolaire
Prérequis pour démarrer le Projet

    Java 17
    Docker Desktop
    Maven 3+
    
Faites clone

    git clone https://github.com/abdellahkaba/scolaire-ms.git

    # Executer la commande docker-compose dans la grande racine du projet
  
      docker compose up -d

    Keycloak import realm

    alez-y dans dossier keycloak/realm/scolaire-ms puis creer un nouveau realm et importer le realm scolaire-ms
      
    # Démarrer le projet pour les tests (backend)

        cd scolaire
    
        mvn clean package
    
    # créer l'image de backend (build)

        docker build -t scolaire-api .
        
    # créer l'image de front (builde)
    
        cd scolaire-front
        
        docker build -t scolaire-front .
Technologies utilisées

    spring boot 3.4.2
    Angular 19+
    OpenAPI Documentation # pour générer automatiquement une documentation interactive avec Swagger UI
    Docker
    Pipeline CI/CD avec GitLab CI/CD au coté gitlab 
    Keycloak 24+
    PostgreSQL et MySQL
    ngx-toastr # pour les notifications visuelles
    Bootstrap et CSS3

Diagramme de classe

![Diagramme_de_classe drawio](https://github.com/user-attachments/assets/c790220c-cf7f-4f49-aaed-fca3eb4994ce)


Quelques interface de Keycloak


![image1](https://github.com/user-attachments/assets/162ee535-2bbb-4bf5-b0b5-3d29df7d9c51)


![image2](https://github.com/user-attachments/assets/5b8582db-7a54-4e04-817a-2a23a3e2509f)


![image3](https://github.com/user-attachments/assets/9de6bdf5-32d1-4b10-9c67-86ffd95e6b99)


![image4](https://github.com/user-attachments/assets/a3308c6b-97ff-4943-9bda-edcab3ab1d0b)







