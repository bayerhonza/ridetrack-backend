# RIDETRACK-BACKEND

Ridetrack-backend est composé de tous les modules nécéssaires a l'appliction pour fonctionner

## Installation 
### Prérequis 
Pour que l'application puisse être installé, sur la machine hôte, il faut le prérequis suivants : 
- Java 11
- Maven
- PostgreSQL est présent 
- MongoDB en localhost:27017

### Démarrage de l'application 
- mvn clean install
- ridetrack.jar -Dapp.home=/path/to/app/fs/conf.properties ou path est le chemin vers le fichier de conf 

*exemple.conf:*  
db.url=jdbc:postgresql://localhost:5432/ridetrack  
db.username=username  
db.password=password  
 
