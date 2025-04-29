Projet: Microservice paramètrage scolaire
Prérequis pour démarrer le Projet

    Java 17
    Docker Desktop
    Maven 3+
    
Faites clone

    git clone https://github.com/abdellahkaba/scolaire-ms.git

    # Executer la commande docker-compose dans la grande racine du projet
  
      docker compose up -d
      
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


