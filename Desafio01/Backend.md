# Nossa Aplicação

## Como Executar

 Trata-se de uma aplicação Maven que tem como requisitos a instalação prévia de algumas ferramentas como:

| Software    | Versão  |
|-------------|---------|
| Maven       | 3.9.4   |
| Java JDK    | 17.0.8  |

Com essas ferramentas instaladas, pelo terminal, abra a pasta da aplicação backend (pasta Desafio01) e digite o seguinte código:

`mvn clean package`

Este comando é usado para realizar duas tarefas principais:

Clean (Limpeza): O comando "clean" remove todos os arquivos compilados e artefatos gerados anteriormente pelo Maven. Isso garante que você esteja começando com um ambiente limpo antes de construir seu projeto.

Package (Empacotamento): O comando "package" compila o código-fonte do projeto e empacota-o em um formato específico, geralmente em um arquivo JAR (Java Archive) ou outro tipo de pacote.

Em seguida entre com o comando `mvn exec:java` para executar nossa aplicação main. Lembrando que, antes de tudo, é necessário ter instalado o [MongoDB](https://github.com/Lucasbacarinireis/DesafioAdaSemana1/Mongodb.md) para aplicação funcionar sem erros.

# Nossas Classes

## Visão Geral

### Desafio01Application.java
Esta é a classe principal da aplicação Spring Boot. Ela inicia o contexto da aplicação e é o ponto de entrada para a execução da aplicação.

### AwsSNSConfig.java
Esta classe de configuração configura o cliente Amazon SNS (Simple Notification Service) que permite enviar mensagens para tópicos SNS.

### AwsSQSConfig.java
Esta classe configura o cliente Amazon SQS (Simple Queue Service) que permite interagir com filas SQS.

### MongoConfig.java
Esta classe  configura a conexão com o banco de dados MongoDB, permitindo que você armazene e recupere dados da sua aplicação.

### OpenAPIConfig.java
Esta classe configura a documentação da API com o OpenAPI (anteriormente conhecido como Swagger), permitindo a documentação interativa da API.

### Feedback.java
Esta classe representa uma entidade de Feedback que é armazenada no MongoDB.

### CustomerFeedback.java
Esta classe representa uma entidade de Feedback do cliente com algumas configurações predefinidas, como tipo, mensagem e status.

### FeedbackStatus.java
Esta enum define os possíveis estados de feedback, como "Recebido", "Em Processamento" e "Finalizado".

### FeedbackType.java
Esta enum define os tipos de feedback, como "Sugestão", "Elogio" e "Crítica".

### FeedbackControllerPost.java
Este controlador Spring gerencia as operações de envio de feedbacks. Ele lida com a criação e envio de feedbacks para filas SQS e armazenamento no MongoDB.

### FeedbackControllerGet.java
Este controlador Spring lida com operações relacionadas à recuperação de feedbacks. Ele fornece endpoints para consultar o tamanho das filas SQS e recuperar feedbacks por tipo.

### FeedbackQueueListener.java
Esta classe é um ouvinte que processa mensagens das filas SQS. Ele processa mensagens de sugestão, elogio e crítica, atualiza o status dos feedbacks e exclui as mensagens da fila.

### FeedbackRepository.java
Esta interface define o repositório MongoDB para a entidade Feedback.

### FeedbackService.java
Esta classe fornece serviços relacionados a feedbacks, como adicionar feedbacks, atualizar status e listar feedbacks.

### QueuePollingService.java
Este serviço realiza a verificação periódica das filas SQS para processar mensagens e atualizar o status dos feedbacks.

### SNSService.java
Este serviço permite o envio de mensagens para tópicos SNS.

## Métodos importantes

### FeedbackService

A classe `FeedbackService` é responsável por fornecer serviços relacionados à manipulação e gerenciamento de feedbacks de clientes em um contexto de aplicação. Ela encapsula a lógica de negócios para interagir com o MongoDB para criar, atualizar, recuperar e listar feedbacks de clientes.

Aqui está uma descrição detalhada das principais funções e métodos da classe `FeedbackService`:

1. **Injeção de Dependência do MongoTemplate**: O `MongoTemplate` é injetado no construtor da classe, permitindo que ela interaja com o MongoDB. Isso é feito através da anotação `@Autowired`.

2. **Adicionar Feedback**: O método `adicionarFeedback` permite adicionar um novo feedback de cliente ao MongoDB. Ele simplesmente chama `mongoTemplate.save(feedback)` para salvar o feedback no banco de dados.

3. **Atualizar Status do Feedback**: O método `atualizarStatusFeedback` permite atualizar o status de um feedback existente com base em seu ID. Ele primeiro verifica se um feedback com o ID fornecido existe no banco de dados. Se existir, ele atualiza o status desse feedback para o novo status especificado. Se não existir, lança uma exceção `FeedbackNotFoundException`.

4. **Listar Todos os Feedbacks**: O método `listarTodosFeedbacks` retorna uma lista de todos os feedbacks de clientes presentes no MongoDB. Isso é feito chamando `mongoTemplate.findAll(CustomerFeedback.class)`.

5. **Listar Feedbacks por Tipo**: O método `listarFeedbacksPorTipo` permite listar feedbacks com base em seu tipo. Ele cria uma consulta usando o `Criteria` do MongoDB para encontrar feedbacks com um tipo específico e retorna a lista de feedbacks correspondentes.

Essencialmente, a classe `FeedbackService` atua como uma camada intermediária entre a lógica de negócios da aplicação e o MongoDB, fornecendo métodos convenientes para realizar operações comuns relacionadas a feedbacks de clientes. Isso ajuda a manter a separação de preocupações e facilita a manutenção da aplicação.



### Arquivo application.properties

- `awsAccessKey` e `awsSecretKey`: Chaves de acesso à AWS (Amazon Web Services) para autenticação.
- `sns.suggestion.topic.arn`: ARN (Amazon Resource Name) do tópico SNS para sugestões.
- `sns.compliment.topic.arn`: ARN do tópico SNS para elogios.
- `sns.criticism.topic.arn`: ARN do tópico SNS para críticas.
- `sqs.suggestion.queue.url`: URL da fila SQS para sugestões.
- `sqs.compliment.queue.url`: URL da fila SQS para elogios.
- `sqs.criticism.queue.url`: URL da fila SQS para críticas.

### Agendamento de Tarefas

A aplicação usa a anotação `@Scheduled` para agendar a verificação periódica das filas SQS nas classes `QueuePollingService`. Os métodos `pollSuggestionQueue`, `pollComplimentQueue` e `pollCriticismQueue` são executados em intervalos regulares para processar mensagens nas filas.

### Observações
- A aplicação utiliza o MongoDB como banco de dados para armazenar os feedbacks.

- O Swagger (OpenAPI) é configurado para fornecer documentação interativa da API, que pode ser acessada em `/swagger-ui.html`.


Esta documentação forneceu uma visão geral das principais classes e configurações de nossa aplicação, mas se ainda houver dúvidas não deixe de entrar em contato com um de nossos colaboradores.