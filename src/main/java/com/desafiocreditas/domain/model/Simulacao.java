package com.desafiocreditas.domain.model;

import lombok.*;

import java.math.BigDecimal;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Simulacao {

    private BigDecimal valorEmprestimo;
    private BigDecimal valorTotal;
    private BigDecimal valorParcelas;
    private BigDecimal totalJuros;
    private int prazoMeses;
    private int idade;

}
