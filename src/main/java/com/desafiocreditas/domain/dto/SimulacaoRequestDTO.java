package com.desafiocreditas.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para requisição de simulação")
public class SimulacaoRequestDTO {
    @Schema(description = "Valor do Empréstimo", example = "1500.00", required = true)
    private BigDecimal valorEmprestimo;
    @Schema(description = "Prazo em Meses", example = "24", required = true)
    private int prazoMeses;
    @Schema(description = "Data de Nascimento", example = "1990-01-01", required = true)
    private String dataNascimento;
}
