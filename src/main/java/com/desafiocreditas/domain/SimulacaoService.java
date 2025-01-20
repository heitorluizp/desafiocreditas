package com.desafiocreditas.domain;

import com.desafiocreditas.domain.model.Simulacao;

import java.math.BigDecimal;

public interface SimulacaoService {
    Simulacao calcularSimulacao(BigDecimal valorEmprestimo, int prazoMeses, String dataNascimento);
}
