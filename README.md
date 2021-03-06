# AutorApi

O projeto 'Cadastro de Autor' é composto por duas partes:
- A interface (AutorWeb) construída em [Angular (V13.1.4)](https://angular.io/cli)
  - Repositório: https://github.com/willfragoso/autor-web.git

- O serviço (AutorApi) construído em [Spring Boot (2.6.3)](https://spring.io/projects/spring-boot)
  - Repositório: https://github.com/willfragoso/autor-api.git

### Configuração Inicial - Backend

Para rodar o backend é necessário seguir os seguintes passos:

1) clonar o repositório "autor-api.git"
2) Executar a instalação das dependências (usando o Maven)

```shell
git clone https://github.com/willfragoso/autor-api.git
cd autor-api/
mvn clean package
```

3) Criar o container docker, contendo uma imagem banco de dados (MySQL - última versão) através da execução do comando abaixo:

```shell
docker-compose -f <DIRETORIO_PROJETO>\autor-api\database\docker-compose.yml up -d
```

### Subindo o projeto

É possível subir o projeto executando o seguinte comando:

```shell
mvn spring-boot:run
```

O mesmo está configurado para subir na porta padrão (8080)

- Link para consumo dos serviços: http://localhost:8080/autor-api/autor

OBSERVAÇÃO: existe uma classe que sobe junto com a aplicação que popula as tabelas de autor e livro, caso elas estejam vazias.
