package defender.ti.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


public class BaseApiCall {

    private final String endpoint, token;

    BaseApiCall(String endpoint, String token) {
        this.endpoint = endpoint;
        this.token = String.format("Bearer %s", token);
    }

    protected RequestSpecification setGenericRequestSpec() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.addHeader("Authorization", token);
        requestSpecBuilder.setUrlEncodingEnabled(false);
        if (endpoint!=null) {
            requestSpecBuilder.setBaseUri(this.endpoint);
        }
        return requestSpecBuilder.build();
    }

    public enum ApiCallHelper {
        VALID,
        NO_BODY,
        EMPTY_BODY,
        INVALID_API,
        INVALID_NAME,
        INVALID_CREDENTIALS,
        LAST_PAGE,
        BODY_ATTRIBUTE_NULL,
        INVALID_SUBSCRIPTION,
        INVALID_WORKSPACE,
        WRONG_BODY_ATTRIBUTE,
        INVALID_RESOURCE_GROUP,
        INVALID_DISCOVERY_GROUP,
        INVALID_ID,
        ORDER_BY_ASC,
        ORDER_BY_DESC,
        FILTER;
    }

}
