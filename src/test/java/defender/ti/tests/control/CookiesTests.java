package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.CookiesAssert;
import defender.ti.model.ResponseBodyImpl.CookiesWrapper;
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
public class CookiesTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    CookiesAssert cookiesAssert;

    @Test
    @Title("GET Cookies for a Hostname - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Cookies"), @WithTag("happy-path")})
    public void getCookiesByHostNameHappyPath() {
        String host = DataGenerator.hostNameGenerator("facebook");
        Response response = cookiesSteps.getCookiesForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        CookiesWrapper cookieWrapperResponse = response.as(CookiesWrapper.class);
        cookiesAssert.assertThat(cookieWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET Cookies by hostName - Invalid Credentials")
    @WithTags(value = { @WithTag("Cookies")})
    public void getCookiesByHostInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = cookiesSteps.getCookiesForAHost(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }


    @Test
    @Title("GET Cookies by IP Address - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Cookies"), @WithTag("happy-path")})
    public void getCookiesByIPAddress() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = cookiesSteps.getCookiesForAHost(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        CookiesWrapper cookieWrapperResponse = response.as(CookiesWrapper.class);
        cookiesAssert.assertThat(cookieWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET Cookies by IP Address - Invalid Credentials")
    @WithTags(value = {@WithTag("Cookies")})
    public void getCookiesByIPAddressInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = cookiesSteps.getCookiesForAHost(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }


}
