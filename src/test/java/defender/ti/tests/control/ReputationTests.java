package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.ReputationAssert;
import defender.ti.model.ResponseBodyImpl.Reputation;
import defender.ti.tests.BaseTest;
import defender.ti.utils.Constants;
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
public class ReputationTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    ReputationAssert reputationAssert;

    @Test
    @Title("GET reputation by host - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Reputation"), @WithTag("happy-path")})
    public void getReputationByHostHappyPath() {
        String host = DataGenerator.hostNameGenerator("google");
        Response response = reputationSteps.getReputationForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        Reputation reputation = response.as(Reputation.class);
        reputationAssert.assertThat(reputation)
                .classificationIsNotEmpty()
                        .classificationShouldBeInList(Constants.REPUTATION_CLASSIFICATIONS_LIST);

        assertions.assertAll();
    }


    @Test
    @Title("GET reputation by host - Invalid Credentials")
    @WithTags(value = {@WithTag("Reputation"), @WithTag("happy-path")})
    public void getReputationByHostInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("google");
        Response response = reputationSteps.getReputationForAHost(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                //.assertHeaderContainsAttribute("x-ms-request-id")
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }

    @Test
    @Title("GET reputation by an Ip Address - Checking Classification Value")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Reputation"), @WithTag("happy-path")})
    public void getReputationByHostClassificationSuspicious() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = reputationSteps.getReputationForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                //.assertHeaderContainsAttribute("x-ms-request-id")
                .assertHeaderMatchWith("Date", RFC5322);
        Reputation reputation = response.as(Reputation.class);
        reputationAssert.assertThat(reputation)
                .classificationIsNotEmpty()
                .classificationIsEqualTo(Constants.REPUTATION_CLASSIFICATIONS.UNKNOWN.getClassification());

        assertions.assertAll();
    }

    @Test
    @Title("GET Host by a hostname")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Host"), @WithTag("happy-path")})
    public void getHostByHostname() {
        String host = DataGenerator.hostNameGenerator("riskiq.net");
        Response response = reputationSteps.getReputationForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                //.assertHeaderContainsAttribute("x-ms-request-id")
                .assertHeaderMatchWith("Date", RFC5322);
        Reputation reputation = response.as(Reputation.class);
        reputationAssert.assertThat(reputation)
                .classificationIsNotEmpty()
                .classificationIsEqualTo(Constants.REPUTATION_CLASSIFICATIONS.UNKNOWN.getClassification());

        assertions.assertAll();
    }


}
