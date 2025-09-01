# Book's Trade – Sistema de Troca de Livros

## Descrição Geral

O **Book's Trade** é uma aplicação web que visa criar uma comunidade de leitores, permitindo que usuários cadastrem livros, proponham trocas e gerenciem suas transações de forma organizada. A plataforma promove o acesso à leitura e o reaproveitamento de exemplares.

## Funcionalidades e Regras de Negócio

O sistema suporta os seguintes perfis de usuário e funcionalidades:

### Usuário Comum
- **Gestão de Livros:** Cadastro, listagem e busca de livros.
- **Trocas:** Envio, gerenciamento e histórico de propostas de troca.
- **Perfil:** Edição de informações pessoais.

### Administrador
- **Moderação:** Gerenciamento de usuários e livros.
- **Monitoramento:** Acesso a histórico e estatísticas da plataforma.

### Regras Principais
- Apenas usuários autenticados podem interagir com o sistema.
- Livros cadastrados devem ter informações obrigatórias (título, autor, gênero).
- Propostas de troca podem ser aceitas ou recusadas.
- Apenas administradores podem moderar o conteúdo.

---

## Tecnologias Utilizadas

Este projeto foi desenvolvido com a seguinte stack de tecnologias:

- **Linguagem de Programação:** Java 17
- **Framework:** Spring Boot 3.x
- **Template Engine:** Thymeleaf
- **Banco de Dados:** H2 Database (em memória, para desenvolvimento)
- **Gerenciador de Dependências:** Maven
- **Ferramentas:** Git e GitHub para controle de versão.

---

## Estrutura do Projeto

O projeto segue a arquitetura em camadas, com pacotes organizados para separar as responsabilidades:
-> controller           // Lógica de requisições web (views e APIs)
-> dto                  // Objetos de Transferência de Dados para validação
-> entities             // Entidades de domínio (modelos do banco de dados)
-> repository           // Interfaces para acesso ao banco de dados (JPA)
-> service              // Lógica de negócio da aplicação

---

## Como Executar o Projeto

Para rodar a aplicação localmente, siga os passos abaixo:

1.  **Pré-requisitos:** Certifique-se de ter o **Java 17** e o **Maven** instalados em sua máquina.
2.  **Clone o Repositório:** Abra o terminal e execute o comando:
    `git clone https://github.com/Gabrielxcb/ChangeBooks.git`
3.  **Navegue até a Pasta do Projeto:**
    `cd books_trade`
4.  **Execute a Aplicação:** Utilize o Maven para rodar o projeto:
    `./mvnw spring-boot:run`
5.  **Acesse a Aplicação:** Após o startup, abra seu navegador e acesse a URL:
    `http://localhost:8080`
