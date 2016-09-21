Esse é um sistema para efetuar leitura em um arquivo CSV e apresentá-lo em uma página Web.

A plataforma é Java e utiliza Spring Boot para o backend e Angular JS como frontend.

Para utilizá-lo é necessário ter o Java 8 e Maven 3.

Basta fazer o clone do projeto e no diretório do projeto digitar: mvn clean package

Isso vai construir o jar de execucao.

Ao terminar, digite: cd target

Depois, execute a aplicação digitando: java -jar stock-reader-1.0.0.jar

Espera a linha no prompt avisar que o servidor web iniciou, entre no browser e digite o seguinte endereço: http://localhost:8080/home

Uma outra opção após efetuar o clone do projeto é executar a aplicação direto digitando: mvn spring-boot:run
Ao terminar, é só digitar o mesmo endereço no browser que foi dito antes.
 