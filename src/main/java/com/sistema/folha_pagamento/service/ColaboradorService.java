package com.sistema.folha_pagamento.service;

import com.sistema.folha_pagamento.model.Colaborador;
import com.sistema.folha_pagamento.repository.ColaboradorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ColaboradorService {
    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorService(ColaboradorRepository repository) {
        this.colaboradorRepository = repository;
    }

    public List<Colaborador> getAllColaboradores() {
        return colaboradorRepository.findAll();
    }

    public Colaborador getColaboradorById(String id) {
        return repository.findById(id)
        .orElseThrow(() -> 
            new IllegalArgumentException("Colaborador não encontrado com o ID: " + id));
    }
}
