package defender.ti.steps;


import defender.ti.api.CliftonApiCall;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class ComponentsSteps {


    @Step("Get components for a host")
    public Response getComponentsForAHost(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Components")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getComponentsForAHost(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getComponentsForAHost(hostName, System.getProperty("access-token"));
        }
    }

    @Step("Get components for hostnames")
    public Response getComponentsForHostNames(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("GET Components")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getComponentsForHostNames(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getComponentsForHostNames(hostName, System.getProperty("access-token"));
        }
    }
    @Step("Get components for hostnames")
    public Response getComponentsForHostNames(String hostName, CliftonApiCall.ApiCallHelper helper, Integer top, Integer skip) {
        Serenity.recordReportData().withTitle("GET Components")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getComponentsForHostNames(hostName, DataGenerator.generateNewToken());
            case LAST_PAGE:
                return CliftonApiCall.getComponentsForHostNames(hostName, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getComponentsForHostNames(hostName, System.getProperty("access-token"));
        }
    }
    @Step("Get components for ip addresses")
    public Response getComponentsForIpAddresses(String ipAddress, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("GET Components")
                .andContents(String.format("IP address: %s", ipAddress ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getComponentsForIpAddresses(ipAddress, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getComponentsForIpAddresses(ipAddress, System.getProperty("access-token"));
        }
    }
    @Step("Get components for ip addresses")
    public Response getComponentsForIpAddresses(String ipAddress, CliftonApiCall.ApiCallHelper helper, Integer top, Integer skip) {
        Serenity.recordReportData().withTitle("GET Components")
                .andContents(String.format("IP address: %s", ipAddress ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getComponentsForIpAddresses(ipAddress, DataGenerator.generateNewToken());
            case LAST_PAGE:
                return CliftonApiCall.getComponentsForIpAddresses(ipAddress, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getComponentsForIpAddresses(ipAddress, System.getProperty("access-token"));
        }
    }


}
