package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.ComponentsAssert;
import defender.ti.model.ResponseBodyImpl.ComponentWrapper;
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
public class ComponentsTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    ComponentsAssert componentsAssert;

    @Test
    @Title("GET Components for a Hostname - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Components"), @WithTag("happy-path")})
    public void getComponentsByHostNameHappyPath() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = componentsSteps.getComponentsForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        ComponentWrapper componentWrapperResponse = response.as(ComponentWrapper.class);
        componentsAssert.assertThat(componentWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
        .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET components by hostName - Invalid Credentials")
    @WithTags(value = {@WithTag("Components")})
    public void getComponentsByHostInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = componentsSteps.getComponentsForAHost(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                //.assertHeaderContainsAttribute("x-ms-request-id")
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }


    @Test
    @Title("GET Component by IP Address - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Components"), @WithTag("happy-path")})
    public void getComponentsByIPAddress() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = componentsSteps.getComponentsForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        ComponentWrapper componentWrapperResponse = response.as(ComponentWrapper.class);
        componentsAssert.assertThat(componentWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET host by IP Address - Invalid Credentials")
    @WithTags(value = {@WithTag("Components")})
    public void getComponentsByIPAddressInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = componentsSteps.getComponentsForAHost(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }



}
