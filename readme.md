Création d'une BDD avec:

spring.datasource.url=jdbc:mysql://localhost:3306/springboot?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false


spring.datasource.username=springuser
spring.datasource.password=springuser

Pour éxécuter le back, commencer par:

mvn -B -DskipTests clean package

puis

mvn spring-boot:run

--

Dand le fichier branchements.txt, il y a les request mapping

//testing the webhook
