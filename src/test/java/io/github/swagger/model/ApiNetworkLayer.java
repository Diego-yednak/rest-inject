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

@Schema(name = "NetworkLayer", description = "Informações sobre o status de uma camada de rede e previsão de tarefas.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-11-27T19:38:39.742413300-03:00[America/Sao_Paulo]", comments = "Generator version: 7.8.0")
public class ApiNetworkLayer {

  private Long tpCode;

  private Long statusCode;

  private String state;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledStartDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledEndDate;

  public ApiNetworkLayer tpCode(Long tpCode) {
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

  public ApiNetworkLayer statusCode(Long statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Status da tarefa.
   * @return statusCode
   */
  
  @Schema(name = "statusCode", example = "10", description = "Status da tarefa.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    public Long getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Long statusCode) {
    this.statusCode = statusCode;
  }

  public ApiNetworkLayer state(String state) {
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

  public ApiNetworkLayer scheduledStartDate(OffsetDateTime scheduledStartDate) {
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

  public ApiNetworkLayer scheduledEndDate(OffsetDateTime scheduledEndDate) {
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
    ApiNetworkLayer networkLayer = (ApiNetworkLayer) o;
    return Objects.equals(this.tpCode, networkLayer.tpCode) &&
        Objects.equals(this.statusCode, networkLayer.statusCode) &&
        Objects.equals(this.state, networkLayer.state) &&
        Objects.equals(this.scheduledStartDate, networkLayer.scheduledStartDate) &&
        Objects.equals(this.scheduledEndDate, networkLayer.scheduledEndDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tpCode, statusCode, state, scheduledStartDate, scheduledEndDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiNetworkLayer {\n");
    sb.append("    tpCode: ").append(toIndentedString(tpCode)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
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

