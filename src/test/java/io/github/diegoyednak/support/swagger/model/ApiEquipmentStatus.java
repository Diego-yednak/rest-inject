package io.github.diegoyednak.support.swagger.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * ApiEquipmentStatus
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-11-27T19:38:39.742413300-03:00[America/Sao_Paulo]", comments = "Generator version: 7.8.0")
public class ApiEquipmentStatus {

  private String routerId;

  private Long tpCode;

  private Long statusCode;

  private String state;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime creationDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledStartDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime scheduledEndDate;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime actualEndDate;

  private Long lastCloseDate;

  public ApiEquipmentStatus routerId(String routerId) {
    this.routerId = routerId;
    return this;
  }

  /**
   * Get routerId
   * @return routerId
   */
  
  @Schema(name = "routerId", example = "SR-SDR1A", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getRouterId() {
    return routerId;
  }

  public void setRouterId(String routerId) {
    this.routerId = routerId;
  }

  public ApiEquipmentStatus tpCode(Long tpCode) {
    this.tpCode = tpCode;
    return this;
  }

  /**
   * Get tpCode
   * @return tpCode
   */
  
  @Schema(name = "tpCode", example = "13653791", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getTpCode() {
    return tpCode;
  }

  public void setTpCode(Long tpCode) {
    this.tpCode = tpCode;
  }

  public ApiEquipmentStatus statusCode(Long statusCode) {
    this.statusCode = statusCode;
    return this;
  }

  /**
   * Get statusCode
   * @return statusCode
   */
  
  @Schema(name = "statusCode", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Long statusCode) {
    this.statusCode = statusCode;
  }

  public ApiEquipmentStatus state(String state) {
    this.state = state;
    return this;
  }

  /**
   * Get state
   * @return state
   */
  
  @Schema(name = "state", example = "Pendente Permissão", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public ApiEquipmentStatus creationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  /**
   * Get creationDate
   * @return creationDate
   */
  @Valid 
  @Schema(name = "creationDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public OffsetDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(OffsetDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public ApiEquipmentStatus scheduledStartDate(OffsetDateTime scheduledStartDate) {
    this.scheduledStartDate = scheduledStartDate;
    return this;
  }

  /**
   * Get scheduledStartDate
   * @return scheduledStartDate
   */
  @Valid 
  @Schema(name = "scheduledStartDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public OffsetDateTime getScheduledStartDate() {
    return scheduledStartDate;
  }

  public void setScheduledStartDate(OffsetDateTime scheduledStartDate) {
    this.scheduledStartDate = scheduledStartDate;
  }

  public ApiEquipmentStatus scheduledEndDate(OffsetDateTime scheduledEndDate) {
    this.scheduledEndDate = scheduledEndDate;
    return this;
  }

  /**
   * Get scheduledEndDate
   * @return scheduledEndDate
   */
  @Valid 
  @Schema(name = "scheduledEndDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public OffsetDateTime getScheduledEndDate() {
    return scheduledEndDate;
  }

  public void setScheduledEndDate(OffsetDateTime scheduledEndDate) {
    this.scheduledEndDate = scheduledEndDate;
  }

  public ApiEquipmentStatus actualEndDate(OffsetDateTime actualEndDate) {
    this.actualEndDate = actualEndDate;
    return this;
  }

  /**
   * Get actualEndDate
   * @return actualEndDate
   */
  @Valid 
  @Schema(name = "actualEndDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public OffsetDateTime getActualEndDate() {
    return actualEndDate;
  }

  public void setActualEndDate(OffsetDateTime actualEndDate) {
    this.actualEndDate = actualEndDate;
  }

  public ApiEquipmentStatus lastCloseDate(Long lastCloseDate) {
    this.lastCloseDate = lastCloseDate;
    return this;
  }

  /**
   * Get lastCloseDate
   * @return lastCloseDate
   */
  
  @Schema(name = "lastCloseDate", example = "1033", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  public Long getLastCloseDate() {
    return lastCloseDate;
  }

  public void setLastCloseDate(Long lastCloseDate) {
    this.lastCloseDate = lastCloseDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApiEquipmentStatus equipmentStatus = (ApiEquipmentStatus) o;
    return Objects.equals(this.routerId, equipmentStatus.routerId) &&
        Objects.equals(this.tpCode, equipmentStatus.tpCode) &&
        Objects.equals(this.statusCode, equipmentStatus.statusCode) &&
        Objects.equals(this.state, equipmentStatus.state) &&
        Objects.equals(this.creationDate, equipmentStatus.creationDate) &&
        Objects.equals(this.scheduledStartDate, equipmentStatus.scheduledStartDate) &&
        Objects.equals(this.scheduledEndDate, equipmentStatus.scheduledEndDate) &&
        Objects.equals(this.actualEndDate, equipmentStatus.actualEndDate) &&
        Objects.equals(this.lastCloseDate, equipmentStatus.lastCloseDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(routerId, tpCode, statusCode, state, creationDate, scheduledStartDate, scheduledEndDate, actualEndDate, lastCloseDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApiEquipmentStatus {\n");
    sb.append("    routerId: ").append(toIndentedString(routerId)).append("\n");
    sb.append("    tpCode: ").append(toIndentedString(tpCode)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    state: ").append(toIndentedString(state)).append("\n");
    sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
    sb.append("    scheduledStartDate: ").append(toIndentedString(scheduledStartDate)).append("\n");
    sb.append("    scheduledEndDate: ").append(toIndentedString(scheduledEndDate)).append("\n");
    sb.append("    actualEndDate: ").append(toIndentedString(actualEndDate)).append("\n");
    sb.append("    lastCloseDate: ").append(toIndentedString(lastCloseDate)).append("\n");
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

