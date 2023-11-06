package defender.ti.steps;


import defender.ti.api.CliftonApiCall;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class CookiesSteps {


    @Step("Get cookies for a host")
    public Response getCookiesForAHost(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Cookies")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getCookiesForAHost(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getCookiesForAHost(hostName, System.getProperty("access-token"));
        }
    }

    @Step("Get cookies for hostnames")
    public Response getCookiesForHostNames(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("GET Cookies")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getCookiesForHostNames(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getCookiesForHostNames(hostName, System.getProperty("access-token"));
        }
    }
    @Step("Get cookies for hostnames")
    public Response getCookiesForHostNames(String hostName, CliftonApiCall.ApiCallHelper helper, Integer top, Integer skip) {
        Serenity.recordReportData().withTitle("GET Cookies")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getCookiesForHostNames(hostName, DataGenerator.generateNewToken());
            case LAST_PAGE:
                return CliftonApiCall.getCookiesForHostNames(hostName, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getCookiesForHostNames(hostName, System.getProperty("access-token"));
        }
    }
    @Step("Get cookies for ip addresses")
    public Response getCookiesForIpAddresses(String ipAddress, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("GET Cookies")
                .andContents(String.format("IP address: %s", ipAddress ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getCookiesForIpAddresses(ipAddress, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getCookiesForIpAddresses(ipAddress, System.getProperty("access-token"));
        }
    }
    @Step("Get cookies for ip addresses")
    public Response getCookiesForIpAddresses(String ipAddress, CliftonApiCall.ApiCallHelper helper, Integer top, Integer skip) {
        Serenity.recordReportData().withTitle("GET Cookies")
                .andContents(String.format("IP address: %s", ipAddress ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getCookiesForIpAddresses(ipAddress, DataGenerator.generateNewToken());
            case LAST_PAGE:
                return CliftonApiCall.getCookiesForIpAddresses(ipAddress, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getCookiesForIpAddresses(ipAddress, System.getProperty("access-token"));
        }
    }
}
