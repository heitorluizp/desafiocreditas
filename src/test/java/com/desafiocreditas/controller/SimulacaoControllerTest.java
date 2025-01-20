package com.desafiocreditas.controller;

import com.desafiocreditas.domain.SimulacaoService;
import com.desafiocreditas.domain.dto.SimulacaoRequestDTO;
import com.desafiocreditas.domain.dto.SimulacaoResponseDTO;
import com.desafiocreditas.domain.model.Simulacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SimulacaoControllerTest {

    @Mock
    private SimulacaoService simulacaoService;

    @InjectMocks
    private SimulacaoController simulacaoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSimularEmprestimo() {
        SimulacaoRequestDTO requestDTO = new SimulacaoRequestDTO();
        requestDTO.setValorEmprestimo(new BigDecimal("1500.00"));
        requestDTO.setPrazoMeses(24);
        requestDTO.setDataNascimento("1990-03-01");

        Simulacao simulacao = new Simulacao();
        simulacao.setValorTotal(new BigDecimal("1620.00"));
        simulacao.setValorParcelas(new BigDecimal("67.50"));
        simulacao.setTotalJuros(new BigDecimal("120.00"));

        when(simulacaoService.calcularSimulacao(any(BigDecimal.class), any(Integer.class), any(String.class)))
                .thenReturn(simulacao);

        ResponseEntity<SimulacaoResponseDTO> response = simulacaoController.simularEmprestimo(requestDTO);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new BigDecimal("1620.00"), response.getBody().getValorTotal());
        assertEquals(new BigDecimal("67.50"), response.getBody().getValorParcelas());
        assertEquals(new BigDecimal("120.00"), response.getBody().getTotalJuros());
    }
}