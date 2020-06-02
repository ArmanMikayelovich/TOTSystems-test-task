SET dbUsername=admin
SET dbPassword=admin
SET dbDataSource=jdbc:mysql://localhost:3306/moex?serverTimezone=UTC
SET port=9999

mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.username=%dbUsername%,--spring.datasource.password=%dbPassword%,--spring.datasource.url=%dbDataSource%,----server.port=%port%"
PAUSE