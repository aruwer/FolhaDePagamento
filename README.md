# 💰 Sistema de Folha de Pagamento

Aplicação em Java com Spring Boot e PostgreSQL para gerenciamento, cálculo de salários e emissão de folhas de pagamento individuais e consolidadas para diferentes categorias de colaboradores.

---

## 📌 Sumário
- [Visão Geral e Requisitos](#-visão-geral-e-requisitos)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Ambiente Passo a Passo](#-configuração-do-ambiente-passo-a-passo)
  - [1. Estruturação do Projeto](#1-estruturação-do-projeto)
  - [2. Configuração do Banco de Dados (Docker)](#2-configuração-do-banco-de-dados-docker)
  - [3. Configuração do Spring Boot (`application.properties`)](#3-configuração-do-spring-boot-applicationproperties)
  - [4. Execução do Projeto](#4-execução-do-projeto)
- [Estrutura do Projeto (Arquitetura O.O.)](#-estrutura-do-projeto-arquitetura-oo)
- [Regras de Negócio](#-regras-de-negócio)
- [Comandos Úteis](#-comandos-úteis)

---

## 📄 Visão Geral e Requisitos

O sistema foi concebido para atender às diferentes modalidades de contratação de uma empresa, calculando automaticamente as remunerações e apresentando o custo total da folha.

### Categorias de Colaboradores Suportadas:
1. **Colaborador Padrão:** Recebe apenas o salário base.
2. **Colaborador Comissionado:**
   $$\text{Comissão} = \text{Valor das Vendas} \times \text{Percentual de Comissão}$$
   $$\text{Salário Final} = \text{Salário Base} + \text{Comissão}$$
3. **Colaborador por Produção:**
   $$\text{Produtividade} = \text{Quantidade Produzida} \times \text{Valor por Unidade}$$
   $$\text{Salário Final} = \text{Salário Base} + \text{Produtividade}$$

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem:** Java 17 (ou superior)
- **Framework:** Spring Boot (Spring Web, Spring Data JPA, Validation)
- **Gerenciador de Dependências:** Gradle (via Gradle Wrapper)
- **Banco de Dados:** PostgreSQL 15+
- **Containerização:** Docker / Docker Compose

---

## 📋 Pré-requisitos

Antes de iniciar a execução do projeto, certifique-se de que sua máquina possui:

1. **JDK (Java Development Kit) 17 ou 21** instalado e configurado nas variáveis de ambiente.
2. **Docker** e **Docker Compose** instalados e em execução.
3. **Git** para clonar o repositório.

> ℹ️ **Aviso sobre o Gradle:** Não é necessário instalar o Gradle manualmente no sistema operacional. O repositório já conta com o **Gradle Wrapper** (`gradlew` / `gradlew.bat`), garantindo que todos os desenvolvedores utilizem exatamente a mesma versão da ferramenta.

---

## 🚀 Configuração do Ambiente Passo a Passo

### 1. Estruturação do Projeto
Obtenha o código fonte do projeto via Git ou descompacte o arquivo fonte no diretório de sua preferência (ex: `Documentos/folha-pagamento`):

```bash
git clone <url-do-repositorio>
cd folha-pagamento
```

### 2. Configuração do Banco de Dados (Docker)
O banco de dados PostgreSQL é executado isoladamente dentro de um container Docker.

Na raiz do projeto, garanta a presença do arquivo `docker-compose.yml` com o conteúdo:

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15-alpine
    container_name: folha_db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: rootpassword
      POSTGRES_DB: folha_pagamento_db
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

Abra o terminal na raiz do projeto e inicie o container:

```bash
docker compose up -d
```

*(Para confirmar que o container está ativo, execute `docker ps`)*

### 3. Configuração do Spring Boot (`application.properties`)
Verifique se o arquivo `src/main/resources/application.properties` contém as variáveis de conexão corretas para o banco containerizado:

```properties
# Conexão com o PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/folha_pagamento_db
spring.datasource.username=postgres
spring.datasource.password=rootpassword
spring.datasource.driver-class-name=org.postgresql.Driver

# Configurações do JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 4. Execução do Projeto
Com o banco de dados rodando, utilize o executável do Gradle Wrapper para compilar as dependências e iniciar a aplicação:

- **No Windows (PowerShell / Prompt de Comando):**
  ```cmd
  .\gradlew.bat bootRun
  ```

- **No Linux / macOS (Terminal):**
  ```bash
  ./gradlew bootRun
  ```

A aplicação iniciará e estará disponível em `http://localhost:8080`.

---

## 📐 Estrutura do Projeto (Arquitetura O.O.)

O projeto adota os princípios de Orientação a Objetos para permitir fácil inclusão de novos tipos de remuneração no futuro (RNF004) sem necessidade de reescrever a lógica existente.

```text
src/main/java/com/sistema/folhapagamento/
├── domain/            # Modelos de domínio (Colaborador, Heranças e Enums)
│   ├── Colaborador.java (Classe base abstrata)
│   ├── ColaboradorPadrao.java
│   ├── ColaboradorComissionado.java
│   └── ColaboradorProducao.java
├── repository/        # Interfaces JPA para comunicação com PostgreSQL
├── service/           # Regras de cálculo, validação e emissão da folha
├── controller/        # Endpoints REST para cadastro, consultas e relatórios
└── FolhaPagamentoApplication.java
```

---

## ⚖️ Regras de Negócio Validation Checklist

- **RN001:** Toda matrícula deve ser única no sistema.
- **RN002:** Nome do colaborador é obrigatório.
- **RN003 a RN007:** Salário base, valor de vendas, percentual de comissão, quantidade produzida e valor por unidade **não podem ser negativos**.
- **RN008:** Salário final calculado automaticamente pela regra específica da categoria.

---

## 🧰 Comandos Úteis

- **Subir o banco de dados:** `docker compose up -d`
- **Parar o banco de dados:** `docker compose down`
- **Limpar o build da aplicação:** `.\gradlew clean` ou `./gradlew clean`
- **Executar testes unitários:** `.\gradlew test` ou `./gradlew test`
- **Compilar e gerar o `.jar` final:** `.\gradlew build` ou `./gradlew build`