package io.github.diegoyednak.mock;

import io.github.diegoyednak.support.swagger.model.ApiNetworkLayer;

public class EntitiesMock {

    public static String buildEquipmentStatusListJson() {
        return """
                [
                    {
                        "routerId":"SR-SDR1A",
                        "tpCode":13653791,
                        "statusCode":10,
                        "state":"Pendente Permissão",
                        "creationDate":"2025-04-14T18:41:27.739Z",
                        "scheduledStartDate":"2025-04-14T18:41:27.739Z",
                        "scheduledEndDate":"2025-04-14T18:41:27.739Z",
                        "actualEndDate":"2025-04-14T18:41:27.739Z",
                        "lastCloseDate":1033
                    }
                ]""".trim();
    }

    public static String buildErrorResponseListJson() {
        return "[" + buildErrorResponseJson() + "]";
    }

    public static String buildErrorResponseJson() {
        return """
                {
                    "code": 1234,
                    "detail": "Nada de mais",
                    "message": "Nada de mais2"
                }
                """.trim();
    }

    public static String buildApiNetworkLayerListJson() {
        return "[" + buildApiNetworkLayerJson() + "]";
    }

    public static String buildApiNetworkLayerJson() {
        return """
                {
                    "tpCode": 123,
                    "statusCode": 456,
                    "state": "ACTIVE",
                    "scheduledStartDate": "2025-04-21T10:00:00Z",
                    "scheduledEndDate": "2025-04-21T12:00:00Z"
                }
                """.trim();
    }

    public static String buildApiNetworkLayerWithoutIdAndDateJson() {
        return """
                {
                    "tpCode": null,
                    "statusCode": 456,
                    "state": "ACTIVE",
                    "scheduledStartDate": null,
                    "scheduledEndDate": null
                }
                """.trim();
    }

    public static ApiNetworkLayer buildApiNetworkLayerWithoutIdAndDate() {
        ApiNetworkLayer apiNetworkLayer = new ApiNetworkLayer();
        apiNetworkLayer.setStatusCode(456L);
        apiNetworkLayer.setState("ACTIVE");
        return apiNetworkLayer;
    }

    public static String buildApiNetworkLayerStatusListJson() {
        return """
                [
                    {
                        "networkLayer": "L2",
                        "tpCode": 12345,
                        "routerId": "SR-SDR1A",
                        "state": "PLANNED",
                        "impactType": "PARTIAL",
                        "scheduledStartDate": "2025-04-21T10:00:00-03:00",
                        "scheduledEndDate": "2025-04-21T14:00:00-03:00"
                    }
                ]
                """;
    }
}
