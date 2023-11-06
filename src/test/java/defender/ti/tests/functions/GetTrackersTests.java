package defender.ti.tests.functions;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.GetTrackersForHostNamesAssert;
import defender.ti.model.ResponseBodyImpl.TrackersForHostNames;
import defender.ti.tests.BaseTest;
import defender.ti.utils.DataGenerator;
import defender.ti.utils.Utils;
import io.restassured.response.Response;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import net.thucydides.core.annotations.WithTag;
import net.thucydides.core.annotations.WithTags;
import net.thucydides.junit.annotations.Concurrent;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import static defender.ti.api.BaseApiCall.ApiCallHelper.*;
import static defender.ti.assertions.GenericAssertions.RFC5322;

@RunWith(SerenityRunner.class)
@Concurrent
public class GetTrackersTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    GetTrackersForHostNamesAssert trackersAssert;

    @Test
    @Title("GET Trackers for Hostnames - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Trackers"), @WithTag("happy-path")})
    public void getTrackersForHostNamesHappyPath() {
        String host = "riskiq";
        Response response = trackersSteps.getTrackersForHostNames(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        TrackersForHostNames trackersForHostNamesResponse = response.as(TrackersForHostNames.class);
        trackersAssert.assertThat(trackersForHostNamesResponse)
                .mandatoryFieldsAreNotEmpty()
        .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET trackers for hostNames - Invalid Credentials")
    @WithTags(value = { @WithTag("Trackers")})
    public void getTrackersForHostNamesInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = trackersSteps.getTrackersForHostNames(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                //.assertHeaderContainsAttribute("x-ms-request-id")
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }

    @Test
    @Title("GET Trackers for HostNames - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Trackers"), @WithTag("Pagination")})
    public void getTrackersForHostNamesCheckPagination() throws Exception {
        String host = "riskiq";
        Response response = trackersSteps.getTrackersForHostNames(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        TrackersForHostNames trackerForHostnamesResponse = response.as(TrackersForHostNames.class);
        assertLastPage(host, trackerForHostnamesResponse);
        assertions.assertAll();
    }


    @Test
    @Title("GET Trackers for IP Addresses - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Trackers"), @WithTag("happy-path")})
    public void getTrackersForIpAddresses() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = trackersSteps.getTrackersForIpAddresses(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        TrackersForHostNames trackerForHostnamesResponse = response.as(TrackersForHostNames.class);
        trackersAssert.assertThat(trackerForHostnamesResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET Trackers for IP Addresses - Invalid Credentials")
    @WithTags(value = {@WithTag("Trackers")})
    public void getTrackersByIPAddressInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = trackersSteps.getTrackersForIpAddresses(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }
    
    @Test
    @Title("GET Trackers for IP Addresses - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Trackers"), @WithTag("Pagination")})
    public void getTrackersByIPAddressCheckPagination() throws Exception {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = trackersSteps.getTrackersForIpAddresses(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        // Extraer funcion con el trackerForHostnamesResponse
        TrackersForHostNames trackerForHostnamesResponse = response.as(TrackersForHostNames.class);
        assertLastPage(host, trackerForHostnamesResponse);
        assertions.assertAll();
    }

    private void assertLastPage(String host, TrackersForHostNames trackerForHostnamesResponse) throws Exception {
        Integer count = trackerForHostnamesResponse.getCount();
        if(count == 0 ){
            return;
        }
        String nextLink = trackerForHostnamesResponse.getNextLink();

        if(nextLink != null && !trackerForHostnamesResponse.getValue().isEmpty()) {
            // GET RESPONSE OF THE LAST PAGE
            Integer top = Integer.parseInt(Utils.getParamValueFromUrl(nextLink, "$top"));
            Integer pages = (int) Math.floor(count / top);
            Integer skip = count % top == 0 ?  count - top : pages * top;
            Response responseLastPage = Utils.isIp(host) ? trackersSteps.getTrackersForIpAddresses(host, LAST_PAGE, top, skip) : trackersSteps.getTrackersForHostNames(host, LAST_PAGE, top, skip);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            TrackersForHostNames trackerForHostnamesResponseLastPage = responseLastPage.as(TrackersForHostNames.class);
            trackersAssert.assertThat(trackerForHostnamesResponseLastPage)
                    .assertIsLastPage(top);
        }
    }

}
