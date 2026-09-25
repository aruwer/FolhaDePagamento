package com.sistema.folha_pagamento.repository;

import com.sistema.folha_pagamento.model.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColaboradorRepository 
    extends JpaRepository<Colaborador, Long> {
        Optional<Colaborador> findById(String id);
}