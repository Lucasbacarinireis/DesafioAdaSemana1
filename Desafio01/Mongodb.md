# Por que o MongoDB?

### Escolhemos o MongoDB como banco de dados para servir nosso sistema por uma série de fatores que seriam levados em conta neste projeto, os principais são a Flexibilidade no esquema de dados, afinal, o MongoDB é um banco de dados não relacional e nos permitiria alterar e acrescentar outros tipos de dados sem uma estrutura fixa engessada. A Escalabilidade, o mongo é conhecido pela capacidade de escalabilidade horizontal, o que significa que podemos adicionar mais servidores ao cluster para lidar com cargas de trabalho crescentes. As possibilidades infinitas de consultas, o desempenho de leitura rápida, a possibilidade de uso gratuito dentre outros.

## Configuração do nosso banco

### Utilizado, inicialmente, apenas para testes, decidimos por não atribuir usuário e senha, bastando conectar o banco na porta `27017`, criar a pasta `data/db` e definir o nome do banco como `test`. 


```mongodb:
    image: mongo:latest
    ports:
      - "27017:27017"
    environment:
      - MONGODB_HOST=localhost
      - MONGODB_PORT=27017
      - MONGODB_DATABASE=test
    volumes:
      - ./data/db:/data/db
```
## Como instalar

### Existe muitas formas de se utilizar e configurar o MongoDB, as principais são através do serviço [Atlas](https://www.mongodb.com/atlas/database), ou através de aplicação [Mongosh](https://www.mongodb.com/products/tools/compass), ou então via Docker em que basta subir esta aplicação via docker compose digitando através do terminal, na raiz deste projeto, o comando `docker-compose up -d mongodb`.
