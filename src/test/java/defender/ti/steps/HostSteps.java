package defender.ti.steps;

import defender.ti.api.CliftonApiCall;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class HostSteps {

    @Step("Get host by hostName")
    public Response getHostByHostName(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Host")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getHostByHostname(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getHostByHostname(hostName, System.getProperty("access-token"));
        }
    }
}
