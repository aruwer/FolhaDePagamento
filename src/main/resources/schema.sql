-- ============================================================
-- TABELA PRINCIPAL DE COLABORADORES
-- ============================================================
-- Armazena os dados comuns a todos os tipos de colaboradores:
-- matrícula, nome, salário base e tipo de colaborador.
-- A matrícula é única e os dados obrigatórios não podem ser nulos.
-- O salário base possui uma validação para impedir valores negativos.

CREATE TABLE IF NOT EXISTS colaborador (
    id BIGSERIAL PRIMARY KEY,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    nome VARCHAR(150) NOT NULL,
    salario_base NUMERIC(12, 2) NOT NULL
        CHECK (salario_base >= 0),
    tipo_colaborador VARCHAR(30) NOT NULL
);


-- ============================================================
-- TABELA DE COLABORADORES COMISSIONADOS
-- ============================================================
-- Armazena as informações específicas dos colaboradores
-- que recebem comissão sobre suas vendas.
-- O ID é relacionado ao colaborador da tabela principal.
-- Os valores de vendas e percentual de comissão não podem
-- ser negativos.

CREATE TABLE IF NOT EXISTS colaborador_comissionado (
    id BIGINT PRIMARY KEY
        REFERENCES colaborador(id)
        ON DELETE CASCADE,

    valor_vendas NUMERIC(12, 2) NOT NULL
        CHECK (valor_vendas >= 0),

    percentual_comissao NUMERIC(5, 4) NOT NULL
        CHECK (percentual_comissao >= 0)
);


-- ============================================================
-- TABELA DE COLABORADORES POR PRODUÇÃO
-- ============================================================
-- Armazena as informações específicas dos colaboradores
-- remunerados de acordo com sua produção.
-- O ID é relacionado ao colaborador da tabela principal.
-- A quantidade produzida e o valor por unidade não podem
-- ser negativos.

CREATE TABLE IF NOT EXISTS colaborador_producao (
    id BIGINT PRIMARY KEY
        REFERENCES colaborador(id)
        ON DELETE CASCADE,

    quantidade_produzida INTEGER NOT NULL
        CHECK (quantidade_produzida >= 0),

    valor_por_unidade NUMERIC(12, 2) NOT NULL
        CHECK (valor_por_unidade >= 0)
);