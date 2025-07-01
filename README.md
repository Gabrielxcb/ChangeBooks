# Book's Trade – Sistema de Troca de Livros

## Descrição Geral

O **Book's Trade** é uma aplicação web cujo objetivo é criar uma comunidade de leitores, permitindo que usuários cadastrem os livros que possuem e proponham trocas com outros usuários. A plataforma visa incentivar o compartilhamento de livros, ampliar o acesso à leitura e promover o reaproveitamento de exemplares.

Usuários podem visualizar os livros disponíveis, cadastrar seus próprios livros, fazer propostas de troca e aceitar ou recusar propostas recebidas. Além disso, haverá um perfil de **Administrador**, responsável por gerenciar o sistema e moderar os conteúdos.

---

## Objetivos do Sistema

- Incentivar a circulação de livros entre leitores.
- Facilitar a busca por livros disponíveis para troca.
- Permitir que usuários proponham e realizem trocas de forma segura e organizada.
- Oferecer ferramentas administrativas para moderação de conteúdo e gerenciamento de usuários.
- Manter um histórico de trocas realizadas.

---

## Funcionalidades Principais

- Cadastro de usuários.
- Login e autenticação de usuários.
- Cadastro de livros (com informações como título, autor, gênero, estado de conservação, etc.).
- Listagem e busca de livros disponíveis para troca.
- Envio e gerenciamento de propostas de troca entre usuários.
- Histórico de trocas realizadas.
- Moderação e gerenciamento por parte de administradores.

---

## Perfis de Usuário

### Usuário Comum

- **Cadastro e Autenticação:** Criar conta e fazer login seguro no sistema.
- **Gestão de Perfil:** Editar informações pessoais e preferências.
- **Cadastro de Livros:** Inserir novos livros, com título, autor, gênero, estado de conservação, entre outros.
- **Visualização de Catálogo:** Consultar a lista de livros disponíveis, com filtros como título, autor e gênero.
- **Envio de Propostas de Troca:** Escolher livros de interesse e enviar propostas de troca.
- **Gerenciamento de Propostas:** Visualizar, aceitar ou recusar propostas recebidas.
- **Histórico de Trocas:** Acompanhar todas as trocas realizadas.

### Administrador

- **Gerenciamento de Usuários:** Consultar, editar ou bloquear perfis de usuários.
- **Moderação de Livros:** Revisar cadastros de livros, podendo aprovar, editar ou excluir conteúdos.
- **Monitoramento de Atividades:** Acompanhar estatísticas como número de usuários, número de trocas e livros mais trocados.
- **Acesso ao Histórico Geral:** Visualizar o histórico completo de trocas realizadas na plataforma.

---

## Regras de Negócio

- Apenas usuários autenticados podem cadastrar livros e realizar trocas.
- Cada livro cadastrado deve conter informações obrigatórias: título, autor, gênero e estado de conservação.
- Propostas de troca só podem ser feitas por usuários logados.
- Usuários podem aceitar ou recusar propostas recebidas.
- O histórico de trocas será mantido permanentemente.
- Apenas administradores podem excluir livros de terceiros ou bloquear usuários.
- Todos os cadastros de livros estão sujeitos a moderação para garantir o cumprimento das regras da plataforma.

---

## Requisitos Não Funcionais

- A aplicação será desenvolvida como uma plataforma web responsiva, compatível com desktops, tablets e smartphones.
- O sistema contará com autenticação segura (com criptografia de senha).
- Interface será intuitiva e de fácil navegação, acessível para todos os tipos de usuários.
- O sistema manterá logs de atividades administrativas, garantindo rastreabilidade de ações como exclusões de conteúdo ou bloqueios de usuários.


