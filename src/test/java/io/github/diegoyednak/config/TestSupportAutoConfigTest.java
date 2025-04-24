package io.github.diegoyednak.config;

import io.github.diegoyednak.support.annotation.InjectMockServer;
import io.github.diegoyednak.support.annotation.SpringBootTestJupiter;
import io.github.diegoyednak.annotation.InjectRestClient;
import io.github.diegoyednak.error.HttpErrorResponseException;
import io.github.diegoyednak.mock.EntitiesMock;
import io.github.diegoyednak.support.swagger.RoutersApiTst;
import io.github.diegoyednak.support.swagger.model.ApiEquipmentStatus;
import io.github.diegoyednak.support.swagger.model.ApiErrorMessage;
import io.github.diegoyednak.support.swagger.model.ApiNetworkLayer;
import io.github.diegoyednak.support.swagger.model.ApiNetworkLayerStatus;
import io.github.diegoyednak.support.swagger.model.Error;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTestJupiter
@DisplayName("Requisições para a API de roteadores")
class TestSupportAutoConfigTest {

    @InjectRestClient
    RoutersApiTst routerApi;

    @InjectMockServer
    MockRestServiceServer server;

    final String routersApiName = "Custom:RoutersApiTst";

    @Test
    @DisplayName("Deve comportar-se corretamente nos métodos herdados de Object")
    void shouldBehaveAsExpectedOnObjectMethods() {
        assertEquals(routersApiName, routerApi.toString());
        assertEquals(routerApi.hashCode(), routerApi.hashCode());

        Optional<NativeWebRequest> request = routerApi.getRequest();
        assertTrue(request.isEmpty());
    }

    @Test
    @DisplayName("Deve montar a requisição corretamente para obter o status dos equipamentos")
    void shouldBuildRequestToGetEquipmentStatus() {
        // Arrange
        server.expect(ExpectedCount.once(), requestTo("/routers/status"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(EntitiesMock.buildEquipmentStatusListJson()));
        // Act
        ResponseEntity<List<ApiEquipmentStatus>> equipmentStatus = routerApi.getEquipmentStatus();
        List<ApiEquipmentStatus> body = equipmentStatus.getBody();
        // Assert
        assertEquals(1, body.size());
        assertEquals("SR-SDR1A", body.get(0).getRouterId());
        // Verify
        server.verify();
    }

    @Test
    @DisplayName("Deve retornar resposta sem body ao consultar status dos equipamentos")
    void shouldReturnResponseWithoutBodyWhenNoContent() {
        // Arrange
        server.expect(ExpectedCount.once(), requestTo("/routers/status"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess().contentType(MediaType.APPLICATION_JSON));
        // Act
        ResponseEntity<List<ApiEquipmentStatus>> equipmentStatus = routerApi.getEquipmentStatus();
        // Assert
        assertFalse(equipmentStatus.hasBody());
        // Verify
        server.verify();
    }

    @Test
    @DisplayName("Deve montar a requisição corretamente para obter as camadas de rede pelos códigos TP")
    void shouldBuildRequestToGetNetworkLayersWithTpCodes() {
        // Arrange
        server.expect(ExpectedCount.once(), requestTo("/routers/network-layers/?tpCode=55&tpCode=56"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(EntitiesMock.buildApiNetworkLayerListJson()));
        // Act
        ResponseEntity<List<ApiNetworkLayer>> networkLayers = routerApi.getNetworkLayers(Arrays.asList(55L, 56L));
        List<ApiNetworkLayer> body = networkLayers.getBody();
        // Assert
        assertEquals(1, body.size());
        assertEquals("ACTIVE", body.get(0).getState());
        assertEquals(123, body.get(0).getTpCode());
        assertEquals("2025-04-21T10:00Z", body.get(0).getScheduledStartDate().toString());
        // Verify
        server.verify();
    }

    @Test
    @DisplayName("Deve montar a requisição corretamente para obter o status das camadas de rede por routerId")
    void shouldBuildRequestToGetNetworkLayerStatusWithRouterIds() {
        // Arrange
        server.expect(ExpectedCount.once(), requestTo("/routers/status/upcoming-days?routerIds=ROUTER-ID-01&routerIds=ROUTER-ID-02"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(EntitiesMock.buildApiNetworkLayerStatusListJson()));
        // Act
        ResponseEntity<List<ApiNetworkLayerStatus>> networkLayersStatus = routerApi.getNetworkLayersStatus(Arrays.asList("ROUTER-ID-01", "ROUTER-ID-02"));
        List<ApiNetworkLayerStatus> body = networkLayersStatus.getBody();
        // Assert
        assertEquals(1, body.size());
        assertEquals("PLANNED", body.get(0).getState());
        assertEquals(12345, body.get(0).getTpCode());
        assertEquals("2025-04-21T13:00Z", body.get(0).getScheduledStartDate().toString());
        // Verify
        server.verify();
    }

    @Test
    @DisplayName("Deve lançar exceção com body ao receber erro da API de status")
    void shouldBuildRequestAndThrowExceptionOnErrorResponse() {
        // Arrange
        server.expect(requestTo("/routers/status/upcoming-days?routerIds=ANY&routerIds=ANY2"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(EntitiesMock.buildErrorResponseJson()));
        // Act
        HttpErrorResponseException exception = Assertions.assertThrows(HttpErrorResponseException.class,
                () -> routerApi.getNetworkLayersStatus(Arrays.asList("ANY", "ANY2"))
        );
        ApiErrorMessage body = exception.getBodyContent();
        HttpHeaders headers = exception.getHeaders();
        // Assert
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals(1234, body.getCode());
        assertEquals("Nada de mais", body.getDetail());
        assertEquals("Nada de mais2", body.getMessage());
        // Verify
        server.verify();
    }

    @Test
    @DisplayName("Deve retornar o JSON bruto do corpo da resposta em caso de erro")
    void shouldReturnRawBodyOnError() {
        // Arrange
        String bodyError = "->forçaErro";
        server.expect(requestTo("/routers/status/upcoming-days?routerIds=ANY&routerIds=ANY2"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError().body("->forçaErro"));
        // Act
        HttpErrorResponseException exception = Assertions.assertThrows(HttpErrorResponseException.class,
                () -> routerApi.getNetworkLayersStatus(Arrays.asList("ANY", "ANY2"))
        );
        HttpHeaders headers = exception.getHeaders();
        // Assert
        assertEquals(0, headers.size());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals(bodyError, exception.getBodyContent());
        // Verify
        server.verify();
    }

    @Test
    @DisplayName("Deve montar PATCH corretamente para atualizar camada de rede")
    void shouldBuildPatchRequestToUpdateNetworkLayer() {
        // Arrange
        server.expect(requestTo("/routers/network-layers/MY-ID"))
                .andExpect(method(HttpMethod.PATCH))
                .andExpect(content().json(EntitiesMock.buildApiNetworkLayerWithoutIdAndDateJson()))
                .andRespond(withSuccess()
                        .body(EntitiesMock.buildApiNetworkLayerJson())
                        .contentType(MediaType.APPLICATION_JSON));
        // Assert
        ApiNetworkLayer apiNetworkLayer = EntitiesMock.buildApiNetworkLayerWithoutIdAndDate();
        // Act
        ResponseEntity<ApiNetworkLayer> result = routerApi.patchNetworkLayers("MY-ID", apiNetworkLayer);
        ApiNetworkLayer body = result.getBody();
        // Assert
        assertEquals("ACTIVE", body.getState());
        assertEquals(123, body.getTpCode());
        assertEquals("2025-04-21T10:00Z", body.getScheduledStartDate().toString());
        assertEquals("2025-04-21T12:00Z", body.getScheduledEndDate().toString());
        // Verify
        server.verify();
    }

    @Test
    @DisplayName("Deve lançar exceção com body ao falhar no PATCH da camada de rede")
    void shouldThrowExceptionWithBodyOnPatchNetworkLayerError() {
        // Arrange
        server.expect(requestTo("/routers/network-layers/MY-ID"))
                .andExpect(method(HttpMethod.PATCH))
                .andExpect(content().json(EntitiesMock.buildApiNetworkLayerWithoutIdAndDateJson()))
                .andRespond(withServerError()
                        .body(EntitiesMock.buildErrorResponseJson())
                        .contentType(MediaType.APPLICATION_JSON));
        // Act
        HttpErrorResponseException exception = Assertions.assertThrows(HttpErrorResponseException.class,
                () -> routerApi.patchNetworkLayers("MY-ID", EntitiesMock.buildApiNetworkLayerWithoutIdAndDate())
        );
        HttpHeaders headers = exception.getHeaders();
        Error body = exception.getBodyContent();
        // Assert
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals(1234, body.getCode());
        assertEquals("Nada de mais2", body.getMessage());
        // Verify
        server.verify();
    }


    @Test
    @DisplayName("Deve lançar exceção com body ao falhar no DELETE da camada de rede")
    void algo() {
        // Arrange
        server.expect(requestTo("/routers/network-layers/MY-ID"))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withServerError()
                        .body(EntitiesMock.buildErrorResponseListJson())
                        .contentType(MediaType.APPLICATION_JSON));
        // Act
        HttpErrorResponseException exception = Assertions.assertThrows(HttpErrorResponseException.class,
                () -> routerApi.deleteNetworkLayers("MY-ID")
        );
        HttpHeaders headers = exception.getHeaders();
        List<ApiErrorMessage> body = exception.getBodyContent();
        // Assert
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
        assertEquals(1, body.size());
        assertEquals(1234, body.get(0).getCode());
        assertEquals("Nada de mais2", body.get(0).getMessage());
        // Verify
        server.verify();
    }

    /* TODO Implementar testes de POST / PUT / DELETE */

}
