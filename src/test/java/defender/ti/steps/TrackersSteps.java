package defender.ti.steps;


import defender.ti.api.CliftonApiCall;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class TrackersSteps {


    @Step("Get trackers for a host")
    public Response getTrackersForAHost(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Trackers")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getTrackersForAHost(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getTrackersForAHost(hostName, System.getProperty("access-token"));
        }
    }

    @Step("Get trackers for HostNames")
    public Response getTrackersForHostNames(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Trackers")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getTrackersForHostNames(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getTrackersForHostNames(hostName, System.getProperty("access-token"));
        }
    }

    @Step("Get trackers for HostNames")
    public Response getTrackersForHostNames(String hostName, CliftonApiCall.ApiCallHelper helper, Integer top, Integer skip) {
        Serenity.recordReportData().withTitle("Trackers")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getTrackersForHostNames(hostName, DataGenerator.generateNewToken());
            case LAST_PAGE:
                return CliftonApiCall.getTrackersForHostNames(hostName, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getTrackersForHostNames(hostName, System.getProperty("access-token"));
        }
    }

    @Step("Get trackers for Ip Addresses")
    public Response getTrackersForIpAddresses(String hostName, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Trackers")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getTrackersForIpAddresses(hostName, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getTrackersForIpAddresses(hostName, System.getProperty("access-token"));
        }
    }

    @Step("Get trackers for Ip Addresses")
    public Response getTrackersForIpAddresses(String hostName, CliftonApiCall.ApiCallHelper helper, Integer top, Integer skip) {
        Serenity.recordReportData().withTitle("Trackers")
                .andContents(String.format("Host name: %s", hostName ));
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getTrackersForIpAddresses(hostName, DataGenerator.generateNewToken());
            case LAST_PAGE:
                return CliftonApiCall.getTrackersForIpAddresses(hostName, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getTrackersForIpAddresses(hostName, System.getProperty("access-token"));
        }
    }

}
