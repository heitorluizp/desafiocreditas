package com.desafiocreditas.application.configuration;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
public class AppConfig {

    @Value("${taxa.padrao.juros}")
    private BigDecimal taxaPadraoJuros;

    @Value("${regras.taxa.idade}")
    private String regrasTaxaIdade;

}
