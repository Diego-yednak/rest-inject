package io.github.swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Informações sobre o status de uma camada de rede e previsão de tarefas.
 */

@Schema(name = "NetworkLayerStatus", description = "Informações sobre o status de uma camada de rede e previsão de tarefas.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-11-27T19:38:39.742413300-03:00[America/Sao_Paulo]", comments = "Generator version: 7.8.0")
public class ApiNetworkLayerStatus {

  private String networkLayer;

  private Long tpCode;

  private String routerId;

  private String state;

  private String impactType;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledStartDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledEndDate;

  public ApiNetworkLayerStatus networkLayer(String networkLayer) {
    this.networkLayer = networkLayer;
    return this;
  }

  /**
   * Camada da rede do equipamento.
   * @return networkLayer
   */
  
  @Schema(name = "networkLayer", example = "SR1", description = "Camada da rede do equipamento.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getNetworkLayer() {
    return networkLayer;
  }

  public void setNetworkLayer(String networkLayer) {
    this.networkLayer = networkLayer;
  }

  public ApiNetworkLayerStatus tpCode(Long tpCode) {
    this.tpCode = tpCode;
    return this;
  }

  /**
   * Código da tarefa.
   * @return tpCode
   */
  
  @Schema(name = "tpCode", example = "13653791", description = "Código da tarefa.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getTpCode() {
    return tpCode;
  }

  public void setTpCode(Long tpCode) {
    this.tpCode = tpCode;
  }

  public ApiNetworkLayerStatus routerId(String routerId) {
    this.routerId = routerId;
    return this;
  }

  /**
   * Nome do equipamento.
   * @return routerId
   */
  
  @Schema(name = "routerId", example = "SR-SDR1A", description = "Nome do equipamento.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getRouterId() {
    return routerId;
  }

  public void setRouterId(String routerId) {
    this.routerId = routerId;
  }

  public ApiNetworkLayerStatus state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Status da tarefa.
   * @return state
   */
  
  @Schema(name = "state", example = "Pendente Permissão", description = "Status da tarefa.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public ApiNetworkLayerStatus impactType(String impactType) {
    this.impactType = impactType;
    return this;
  }

  /**
   * Tipo de afetação.
   * @return impactType
   */
  
  @Schema(name = "impactType", example = "Não Afeta Elemento nem Serviços", description = "Tipo de afetação.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public String getImpactType() {
    return impactType;
  }

  public void setImpactType(String impactType) {
    this.impactType = impactType;
  }

  public ApiNetworkLayerStatus scheduledStartDate(OffsetDateTime scheduledStartDate) {
    this.scheduledStartDate = scheduledStartDate;
    return this;
  }

  /**
   * Data e hora de início prevista.
   * @return scheduledStartDate
   */
  @Valid 
  @Schema(name = "scheduledStartDate", description = "Data e hora de início prevista.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public OffsetDateTime getScheduledStartDate() {
    return scheduledStartDate;
  }

  public void setScheduledStartDate(OffsetDateTime scheduledStartDate) {
    this.scheduledStartDate = scheduledStartDate;
  }

  public ApiNetworkLayerStatus scheduledEndDate(OffsetDateTime scheduledEndDate) {
    this.scheduledEndDate = scheduledEndDate;
    return this;
  }

  /**
   * Data e hora de término prevista.
   * @return scheduledEndDate
   */
  @Valid 
  @Schema(name = "scheduledEndDate", description = "Data e hora de término prevista.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public OffsetDateTime getScheduledEndDate() {
    return scheduledEndDate;
  }

  public void setScheduledEndDate(OffsetDateTime scheduledEndDate) {
    this.scheduledEndDate = scheduledEndDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiNetworkLayerStatus networkLayerStatus = (ApiNetworkLayerStatus) o;
    return Objects.equals(this.networkLayer, networkLayerStatus.networkLayer) &&
        Objects.equals(this.tpCode, networkLayerStatus.tpCode) &&
        Objects.equals(this.routerId, networkLayerStatus.routerId) &&
        Objects.equals(this.state, networkLayerStatus.state) &&
        Objects.equals(this.impactType, networkLayerStatus.impactType) &&
        Objects.equals(this.scheduledStartDate, networkLayerStatus.scheduledStartDate) &&
        Objects.equals(this.scheduledEndDate, networkLayerStatus.scheduledEndDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(networkLayer, tpCode, routerId, state, impactType, scheduledStartDate, scheduledEndDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiNetworkLayerStatus {\n");
    sb.append("    networkLayer: ").append(toIndentedString(networkLayer)).append("\n");
    sb.append("    tpCode: ").append(toIndentedString(tpCode)).append("\n");
    sb.append("    routerId: ").append(toIndentedString(routerId)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    impactType: ").append(toIndentedString(impactType)).append("\n");
    sb.append("    scheduledStartDate: ").append(toIndentedString(scheduledStartDate)).append("\n");
    sb.append("    scheduledEndDate: ").append(toIndentedString(scheduledEndDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

