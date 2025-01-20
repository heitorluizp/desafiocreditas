package com.desafiocreditas.controller;


import com.desafiocreditas.domain.SimulacaoService;
import com.desafiocreditas.domain.dto.SimulacaoRequestDTO;
import com.desafiocreditas.domain.dto.SimulacaoResponseDTO;
import com.desafiocreditas.domain.model.Simulacao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Period;

@RestController
@RequestMapping("/api/simulacao")
public class SimulacaoController {

    private final SimulacaoService simulacaoService;


    public SimulacaoController(SimulacaoService simulacaoService) {
        this.simulacaoService = simulacaoService;
    }

    @PostMapping
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
