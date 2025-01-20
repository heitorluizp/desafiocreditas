package com.desafiocreditas.domain.service;

import com.desafiocreditas.application.configuration.AppConfig;
import com.desafiocreditas.domain.model.Simulacao;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class SimulacaoServiceImplTest {

    @Mock
    private TaxaFinder taxaFinder;

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private SimulacaoServiceImpl simulacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @SneakyThrows
    void testCalcularSimulacao() {
        BigDecimal valorEmprestimo = new BigDecimal("1500.00");
        int prazoMeses = 24;
        String dataNascimento = "1990-01-01";
        int idade = 35;
        BigDecimal taxaJuros = new BigDecimal("0.05");

        when(taxaFinder.findTaxa(eq("idade"), anyInt())).thenReturn(taxaJuros);

        Simulacao simulacao = simulacaoService.calcularSimulacao(valorEmprestimo, prazoMeses, dataNascimento);

        assertEquals(valorEmprestimo, simulacao.getValorEmprestimo());
        assertEquals(prazoMeses, simulacao.getPrazoMeses());
        assertEquals(idade, simulacao.getIdade());
        assertEquals(new BigDecimal("65.8074"), simulacao.getValorParcelas());
        assertEquals(new BigDecimal("1579.3776"), simulacao.getValorTotal());
        assertEquals(new BigDecimal("79.3776"), simulacao.getTotalJuros());
    }

    @Test
    @SneakyThrows
    void testCalcularSimulacaoWithDefaultTaxaJuros() {
        BigDecimal valorEmprestimo = new BigDecimal("1500.00");
        int prazoMeses = 24;
        String dataNascimento = "1990-01-01";
        BigDecimal defaultTaxaJuros = new BigDecimal("0.03");

        when(taxaFinder.findTaxa(eq("idade"), anyInt())).thenThrow(new RuntimeException("Erro ao buscar taxa de juros"));
        when(appConfig.getTaxaPadraoJuros()).thenReturn(defaultTaxaJuros);

        Simulacao simulacao = simulacaoService.calcularSimulacao(valorEmprestimo, prazoMeses, dataNascimento);

        assertEquals(new BigDecimal("64.4718"), simulacao.getValorParcelas());
        assertEquals(new BigDecimal("1547.3232"), simulacao.getValorTotal());
        assertEquals(new BigDecimal("47.3232"), simulacao.getTotalJuros());
    }

    @Test
    void testCalcularSimulacaoWithInvalidDataNascimento() {
        BigDecimal valorEmprestimo = new BigDecimal("1500.00");
        int prazoMeses = 24;
        String invalidDataNascimento = "invalid-date";

        assertThrows(DateTimeParseException.class, () -> {
            simulacaoService.calcularSimulacao(valorEmprestimo, prazoMeses, invalidDataNascimento);
        });
    }

    @Test
    void testCalcularPMT() {
        BigDecimal valorPresente = new BigDecimal("1500.00");
        BigDecimal taxaJuros = new BigDecimal("0.05").divide(BigDecimal.valueOf(12), 6, BigDecimal.ROUND_HALF_UP);
        int numeroParcelas = 24;

        BigDecimal pmt = simulacaoService.calcularPMT(valorPresente, taxaJuros, numeroParcelas);

        assertEquals(new BigDecimal("65.8074"), pmt);
    }

    @Test
    void testCalcularIdade() {
        String dataNascimento = "1990-01-01";

        int idade = simulacaoService.calcularIdade(dataNascimento);

        assertEquals(35, idade);
    }
}