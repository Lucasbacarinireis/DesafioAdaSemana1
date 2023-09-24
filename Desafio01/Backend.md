# Nossa Aplicação

## Nossas Classes

# FeedbackService

A classe `FeedbackService` é responsável por fornecer serviços relacionados à manipulação e gerenciamento de feedbacks de clientes em um contexto de aplicação. Ela encapsula a lógica de negócios para interagir com o MongoDB para criar, atualizar, recuperar e listar feedbacks de clientes.

Aqui está uma descrição detalhada das principais funções e métodos da classe `FeedbackService`:

1. **Injeção de Dependência do MongoTemplate**: O `MongoTemplate` é injetado no construtor da classe, permitindo que ela interaja com o MongoDB. Isso é feito através da anotação `@Autowired`.

2. **Adicionar Feedback**: O método `adicionarFeedback` permite adicionar um novo feedback de cliente ao MongoDB. Ele simplesmente chama `mongoTemplate.save(feedback)` para salvar o feedback no banco de dados.

3. **Atualizar Status do Feedback**: O método `atualizarStatusFeedback` permite atualizar o status de um feedback existente com base em seu ID. Ele primeiro verifica se um feedback com o ID fornecido existe no banco de dados. Se existir, ele atualiza o status desse feedback para o novo status especificado. Se não existir, lança uma exceção `FeedbackNotFoundException`.

4. **Listar Todos os Feedbacks**: O método `listarTodosFeedbacks` retorna uma lista de todos os feedbacks de clientes presentes no MongoDB. Isso é feito chamando `mongoTemplate.findAll(CustomerFeedback.class)`.

5. **Listar Feedbacks por Tipo**: O método `listarFeedbacksPorTipo` permite listar feedbacks com base em seu tipo. Ele cria uma consulta usando o `Criteria` do MongoDB para encontrar feedbacks com um tipo específico e retorna a lista de feedbacks correspondentes.

Essencialmente, a classe `FeedbackService` atua como uma camada intermediária entre a lógica de negócios da aplicação e o MongoDB, fornecendo métodos convenientes para realizar operações comuns relacionadas a feedbacks de clientes. Isso ajuda a manter a separação de preocupações e facilita a manutenção da aplicação.
