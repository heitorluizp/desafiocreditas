package com.desafiocreditas.controller;


import com.desafiocreditas.domain.SimulacaoService;
import com.desafiocreditas.domain.dto.SimulacaoRequestDTO;
import com.desafiocreditas.domain.dto.SimulacaoResponseDTO;
import com.desafiocreditas.domain.model.Simulacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;

@RestController
@Tag(name = "Simulação", description = "APIs para operações de simulação")
@RequestMapping("/api/simulacao")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;


    public SimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @Operation(summary = "Simular empréstimo", description = "Calcula o valor total, parcelas e juros de um empréstimo com base no valor solicitado e prazo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SimulacaoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos parâmetros",
                    content = @Content)
    })    @PostMapping("/simulacao")
    public ResponseEntity<SimulacaoResponseDTO> simularEmprestimo(
            @RequestBody SimulacaoRequestDTO simulacaoRequestDTO) {
        Simulacao simulacao = simulacaoService.calcularSimulacao(
                simulacaoRequestDTO.getValorEmprestimo(),
                simulacaoRequestDTO.getPrazoMeses(),
                simulacaoRequestDTO.getDataNascimento()
        );

        SimulacaoResponseDTO responseDTO = new SimulacaoResponseDTO(
                simulacao.getValorTotal(),
                simulacao.getValorParcelas(),
                simulacao.getTotalJuros()
        );

        return ResponseEntity.ok(responseDTO);


    }


}
