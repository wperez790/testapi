package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.TrackersAssert;
import defender.ti.model.ResponseBodyImpl.TrackersWrapper;
import defender.ti.tests.BaseTest;
import defender.ti.utils.DataGenerator;
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

import static defender.ti.api.BaseApiCall.ApiCallHelper.INVALID_CREDENTIALS;
import static defender.ti.api.BaseApiCall.ApiCallHelper.VALID;
import static defender.ti.assertions.GenericAssertions.RFC5322;

@RunWith(SerenityRunner.class)
@Concurrent
public class TrackersTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    TrackersAssert trackersAssert;

    @Test
    @Title("GET Trackers for a Hostname - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Trackers"), @WithTag("happy-path")})
    public void getTrackersByHostNameHappyPath() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = trackersSteps.getTrackersForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        TrackersWrapper trackersWrapperResponse = response.as(TrackersWrapper.class);
        trackersAssert.assertThat(trackersWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
        .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET trackers by hostName - Invalid Credentials")
    @WithTags(value = {@WithTag("Trackers")})
    public void getTrackersByHostInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = trackersSteps.getTrackersForAHost(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                //.assertHeaderContainsAttribute("x-ms-request-id")
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }


    @Test
    @Title("GET Trackers by IP Address - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Trackers"), @WithTag("happy-path")})
    public void getTrackersByIPAddress() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = trackersSteps.getTrackersForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        TrackersWrapper trackersWrapperResponse = response.as(TrackersWrapper.class);
        trackersAssert.assertThat(trackersWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET trackers by IP Address - Invalid Credentials")
    @WithTags(value = {@WithTag("Trackers")})
    public void getTrackersByIPAddressInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = trackersSteps.getTrackersForAHost(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }



}
