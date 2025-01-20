

# Desafio Backend Creditas

## Visão Geral
Este projeto é um serviço de simulação de empréstimos construído com Java e Spring Boot. Ele calcula os detalhes do empréstimo com base no valor do empréstimo, prazo em meses e a idade do mutuário.
## SWAGGER 
A documentação da API pode ser acessada em http://localhost:8080/swagger-ui.html
## Estrutura JSON de Taxas
Foi utilizado para a definição da taxa, um json personalizavel, idealmente ele estaria em uma base de dados não relacional e seria consultado no momento da simulação.
Tal escolha foi feita, para possibilitar que a taxa da simulação seja facilmente modificada sem a necessidade de alterar o código fonte. 
A estrutura JSON usada para determinar a taxa de juros (`taxa`) é hierárquica e inclui o parâmetro `idade`. Aqui está um exemplo:

```json
[
  {
    "atributo": "idade",
    "valor": "25",
    "operador": "<=",
    "taxa": "0.05"  
  },
  {
    "atributo": "idade",
    "valor": "26",
    "operador": ">",
    "taxa": "0.03"
  }
]
```

## Executando o Projeto
Esse projeto foi feito utilizando Spring, Java 17 e Maven
Para executar o projeto, siga estes passos:

1. **Clone o repositório:**
   ```sh
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Construa o projeto usando Maven:**
   ```sh
   mvn clean install
   ```

3. **Execute a aplicação:**
   ```sh
   mvn spring-boot:run
   ```

## Arquitetura
O projeto segue uma arquitetura em camadas:

- **Camada de Controladores:** Lida com requisições e respostas HTTP.
- **Camada de Serviços:** Contém a lógica de negócios.
- **Camada de Repositórios:** Gerencia a persistência de dados.
- **Camada de Configuração:** Contém arquivos de configuração e definições.

### Componentes Principais
- **`SimulacaoServiceImpl`:** Implementa a lógica de simulação de empréstimos.
- **`TaxaFinder`:** Encontra a taxa de juros apropriada com base nas regras JSON.
- **`SimulacaoRequestDTO`:** Objeto de Transferência de Dados para requisições de simulação.
- **`Simulacao`:** Modelo que representa o resultado da simulação de empréstimo.

## Exemplo de Payload
Aqui está um exemplo de payload para uma requisição de simulação de empréstimo:

```json
{
  "valorEmprestimo": 1500.00,
  "prazoMeses": 24,
  "dataNascimento": "1990-01-01"
}
```
Este payload será processado para calcular os detalhes do empréstimo, incluindo o pagamento mensal, valor total a pagar e juros totais.

