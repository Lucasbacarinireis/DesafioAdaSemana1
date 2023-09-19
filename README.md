# DesafioAdaSemana1
DesafioAdaSemana1

Desafio
Você foi designado para criar um serviço que gerencia filas de sugestões, elogios e críticas de
clientes para uma empresa, aproveitando os serviços da AWS, como SNS e SQS. O serviço deve
permitir que os clientes enviem suas sugestões, elogios e críticas, que serão enfileirados e
processados de acordo com a ordem em que foram recebidos. Além disso, deve ser possível
verificar o status da fila e obter informações sobre as mensagens recebidas.
Requisitos:
  1. Implemente uma classe CustomerFeedback que represente uma mensagem de
feedback com os seguintes atributos: id, type (tipo de feedback, como "Sugestão",
"Elogio" ou "Crítica"), message (mensagem do cliente) e status (status da mensagem,
como "Recebido", "Em Processamento" ou "Finalizado").
  2. Configure um tópico SNS para cada tipo de feedback (Sugestão, Elogio, Crítica) na
AWS. Quando um cliente envia um feedback, o sistema deve publicar a mensagem no
tópico SNS correspondente.
  3. Configure uma fila SQS para cada tipo de feedback (Sugestão, Elogio, Crítica) na AWS.
Configure as filas para seguir o princípio FIFO (First-In-First-Out).
  4. Implemente um consumidor de fila SQS para processar os feedbacks da fila. Este
consumidor deve ser executado em segundo plano e processar os feedbacks de acordo
com a ordem da fila. Quando um feedback for processado com sucesso, seu status
deve ser atualizado para "Finalizado".
  5. Crie um controlador REST em Java usando a biblioteca Spring Boot para expor
endpoints para as seguintes operações:
    a. Enviar um feedback (sugestão, elogio ou crítica) para a fila correspondente no
SQS.
    b. Obter o tamanho atual da fila de feedbacks para cada tipo (Sugestão, Elogio,
Crítica).
    c. Obter informações sobre todos os feedbacks na fila de cada tipo.
  6. Documente a API REST usando a especificação Swagger ou alguma outra ferramenta
de documentação de API.


# Ferramentas Utilizadas

- [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

- [Swagger](https://editor.swagger.io/)
