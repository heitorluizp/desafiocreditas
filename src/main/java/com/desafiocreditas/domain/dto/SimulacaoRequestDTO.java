package com.desafiocreditas.domain.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoRequestDTO {
    private BigDecimal valorEmprestimo;
    private int prazoMeses;
    private String dataNascimento;
}
