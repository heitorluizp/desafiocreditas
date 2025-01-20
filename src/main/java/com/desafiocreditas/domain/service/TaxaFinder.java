package com.desafiocreditas.domain.service;

import com.desafiocreditas.application.configuration.AppConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;


@Service
public class TaxaFinder {
    private final AppConfig appConfig;

    public TaxaFinder(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public BigDecimal findTaxa(String atributo, int valor) throws IOException {

        String jsonString = appConfig.getRegrasTaxaIdade();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(jsonString);
        String taxa = findTaxaRecursively(rootNode, atributo, valor);
        return new BigDecimal(taxa);
    }

    private  String findTaxaRecursively(JsonNode node, String atributo, int valor) {
        if (node.isArray()) {
            for (JsonNode jsonNode : node) {
                String taxa = findTaxaRecursively(jsonNode, atributo, valor);
                if (taxa != null) {
                    return taxa;
                }
            }
        } else if (node.isObject()) {
            if (node.has("atributo") && node.get("atributo").asText().equals(atributo)) {
                int jsonValue = node.get("valor").asInt();
                String operador = node.get("operador").asText();

                if (operador.equals("=") && valor == jsonValue) {
                    return node.get("taxa").asText();
                } else if (operador.equals(">") && valor > jsonValue) {
                    return node.get("taxa").asText();
                } else if (operador.equals("<") && valor < jsonValue) {
                    return node.get("taxa").asText();
                } else if (operador.equals(">=") && valor >= jsonValue) {
                    return node.get("taxa").asText();
                } else if (operador.equals("<=") && valor <= jsonValue) {
                    return node.get("taxa").asText();
                }
            }

            if (node.has("node")) {
                String taxa = findTaxaRecursively(node.get("node"), atributo, valor);
                if (taxa != null) {
                    return taxa;
                }
            }
        }

        return null;
    }
}
