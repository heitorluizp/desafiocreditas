package com.desafiocreditas.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para resposta de simulação")
public class SimulacaoResponseDTO {
    @Schema(description = "Valor Total", example = "1579.3776")
    private BigDecimal valorTotal;
    @Schema(description = "Valor das Parcelas", example = "65.8074")
    private BigDecimal valorParcelas;
    @Schema(description = "Total de Juros", example = "79.3776")
    private BigDecimal totalJuros;
}
