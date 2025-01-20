package com.desafiocreditas.domain.service;

import com.desafiocreditas.application.configuration.AppConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class TaxaFinderTest {

    @Mock
    private AppConfig appConfig;

    @InjectMocks
    private TaxaFinder taxaFinder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindTaxaEqualOperator() throws IOException {
        // Arrange
        String jsonString = "[{\"atributo\": \"idade\", \"valor\": 30, \"operador\": \"=\", \"taxa\": \"0.05\"}]";
        when(appConfig.getRegrasTaxaIdade()).thenReturn(jsonString);

        // Act
        BigDecimal taxa = taxaFinder.findTaxa("idade", 30);

        // Assert
        assertEquals(new BigDecimal("0.05"), taxa);
    }

    @Test
    void testFindTaxaGreaterThanOperator() throws IOException {
        // Arrange
        String jsonString = "[{\"atributo\": \"idade\", \"valor\": 30, \"operador\": \">\", \"taxa\": \"0.06\"}]";
        when(appConfig.getRegrasTaxaIdade()).thenReturn(jsonString);

        // Act
        BigDecimal taxa = taxaFinder.findTaxa("idade", 31);

        // Assert
        assertEquals(new BigDecimal("0.06"), taxa);
    }

    @Test
    void testFindTaxaLessThanOperator() throws IOException {
        // Arrange
        String jsonString = "[{\"atributo\": \"idade\", \"valor\": 30, \"operador\": \"<\", \"taxa\": \"0.04\"}]";
        when(appConfig.getRegrasTaxaIdade()).thenReturn(jsonString);

        // Act
        BigDecimal taxa = taxaFinder.findTaxa("idade", 29);

        // Assert
        assertEquals(new BigDecimal("0.04"), taxa);
    }

    @Test
    void testFindTaxaGreaterThanOrEqualOperator() throws IOException {
        // Arrange
        String jsonString = "[{\"atributo\": \"idade\", \"valor\": 30, \"operador\": \">=\", \"taxa\": \"0.05\"}]";
        when(appConfig.getRegrasTaxaIdade()).thenReturn(jsonString);

        // Act
        BigDecimal taxa = taxaFinder.findTaxa("idade", 30);

        // Assert
        assertEquals(new BigDecimal("0.05"), taxa);
    }

    @Test
    void testFindTaxaLessThanOrEqualOperator() throws IOException {
        // Arrange
        String jsonString = "[{\"atributo\": \"idade\", \"valor\": 30, \"operador\": \"<=\", \"taxa\": \"0.04\"}]";
        when(appConfig.getRegrasTaxaIdade()).thenReturn(jsonString);

        // Act
        BigDecimal taxa = taxaFinder.findTaxa("idade", 30);

        // Assert
        assertEquals(new BigDecimal("0.04"), taxa);
    }

    @Test
    void testFindTaxaInvalidJson() {
        // Arrange
        String invalidJsonString = "invalid-json";
        when(appConfig.getRegrasTaxaIdade()).thenReturn(invalidJsonString);

        // Act & Assert
        assertThrows(IOException.class, () -> {
            taxaFinder.findTaxa("idade", 30);
        });
    }
}