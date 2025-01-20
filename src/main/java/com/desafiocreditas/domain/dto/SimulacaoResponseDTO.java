package com.desafiocreditas.domain.dto;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoResponseDTO {
    private BigDecimal valorTotal;
    private BigDecimal valorParcelas;
    private BigDecimal totalJuros;
}
