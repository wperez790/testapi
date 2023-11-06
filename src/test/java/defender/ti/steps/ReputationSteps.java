package defender.ti.steps;


import defender.ti.api.CliftonApiCall;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class ReputationSteps {


    @Step("Get reputation for a host")
    public Response getReputationForAHost(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Reputation")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getReputationForAHost(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getReputationForAHost(hostName, System.getProperty("access-token"));
        }
    }


}
