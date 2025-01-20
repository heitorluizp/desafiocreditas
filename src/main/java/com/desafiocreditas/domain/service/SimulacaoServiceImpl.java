package com.desafiocreditas.domain.service;

import com.desafiocreditas.application.configuration.AppConfig;
import com.desafiocreditas.domain.SimulacaoService;
import com.desafiocreditas.domain.model.Simulacao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

import static java.lang.Math.pow;


@Slf4j
@Service
public class SimulacaoServiceImpl implements SimulacaoService {

    private final TaxaFinder taxaFinder;

    private final AppConfig appConfig;

    public SimulacaoServiceImpl(TaxaFinder taxaFinder, AppConfig appConfig) {
        this.taxaFinder = taxaFinder;
        this.appConfig = appConfig;
    }

    @Override
    public Simulacao calcularSimulacao(BigDecimal valorEmprestimo, int prazoMeses, String dataNascimento) {
        int idade = calcularIdade(dataNascimento);
        BigDecimal taxaJuros = determinarTaxaJuros(idade);
        BigDecimal taxaMensal = taxaJuros.divide(BigDecimal.valueOf(12), 6, BigDecimal.ROUND_HALF_UP);

        BigDecimal pmt = calcularPMT(valorEmprestimo, taxaMensal, prazoMeses);

        BigDecimal valorTotal = pmt.multiply(BigDecimal.valueOf(prazoMeses)).setScale(4,RoundingMode.HALF_UP);
        BigDecimal totalJuros = valorTotal.subtract(valorEmprestimo).setScale(4,RoundingMode.HALF_UP);
        return Simulacao.builder()
                .valorEmprestimo(valorEmprestimo)
                .valorTotal(valorTotal)
                .valorParcelas(pmt)
                .totalJuros(totalJuros)
                .prazoMeses(prazoMeses)
                .idade(idade)
                .build();
    }

    protected BigDecimal calcularPMT(BigDecimal valorPresente, BigDecimal taxaJuros, int numeroParcelas) {
        // Calcula (1 + r)
        BigDecimal umMaisTaxa = BigDecimal.ONE.add(taxaJuros);
        // Calcula (1 + r)^n
        BigDecimal potenciaPositiva = umMaisTaxa.pow(numeroParcelas);
        // Calcula (1 + r)^-n = 1 / (1 + r)^n
        BigDecimal potenciaNegativa = BigDecimal.ONE.divide(potenciaPositiva, 10, RoundingMode.HALF_UP);
        // Calcula o denominador: 1 - (1 + r)^-n
        BigDecimal denominador = BigDecimal.ONE.subtract(potenciaNegativa);
        // Calcula o numerador: PV * r
        BigDecimal numerador = valorPresente.multiply(taxaJuros);
        // Calcula PMT: numerador / denominador
        return numerador.divide(denominador, 4,RoundingMode.HALF_UP);
    }

    protected BigDecimal determinarTaxaJuros(int idade) {
        try{
            // Resultado em um cache
            return taxaFinder.findTaxa("idade",idade);
        }catch(Exception e){
            log.error("Erro ao buscar taxa de juros", e);
            //Envio para o New Relic ou outro sistema de monitoramento
            return appConfig.getTaxaPadraoJuros();
        }
    }

    protected int calcularIdade(String dataNascimento) {
        return Period.between(LocalDate.parse(dataNascimento), LocalDate.now()).getYears();
    }


}
