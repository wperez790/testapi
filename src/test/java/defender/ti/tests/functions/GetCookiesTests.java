package defender.ti.tests.functions;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.GetCookiesForHostNamesAssert;
import defender.ti.model.ResponseBodyImpl.CookiesForHostNames;
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
public class GetCookiesTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    GetCookiesForHostNamesAssert cookiesAssert;

    @Test
    @Title("GET Cookies for Hostnames - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Cookies"), @WithTag("happy-path")})
    public void getCookiesForHostNamesHappyPath() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = cookiesSteps.getCookiesForHostNames(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        CookiesForHostNames cookiesForHostNamesResponse = response.as(CookiesForHostNames.class);
        cookiesAssert.assertThat(cookiesForHostNamesResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET components for hostNames - Invalid Credentials")
    @WithTags(value = { @WithTag("Cookies")})
    public void getCookiesByHostInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = cookiesSteps.getCookiesForHostNames(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }

    @Test
    @Title("GET Cookies for Hostnames - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Cookies1"), @WithTag("Pagination")})
    public void getCookiesByHostnamesCheckPagination() throws Exception {
        String host = DataGenerator.hostNameGenerator("facebook");
        Response response = cookiesSteps.getCookiesForHostNames(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        CookiesForHostNames cookiesForHostNamesResponse = response.as(CookiesForHostNames.class);
        assertLastPage(host, cookiesForHostNamesResponse);
        assertions.assertAll();
    }

    @Test
    @Title("GET Cookies for IP Addresses - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Cookies"), @WithTag("happy-path")})
    public void getCookiesByIPAddress() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = cookiesSteps.getCookiesForIpAddresses(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        CookiesForHostNames cookiesWrapperResponse = response.as(CookiesForHostNames.class);
        cookiesAssert.assertThat(cookiesWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET Cookies for IP Addresses - Invalid Credentials")
    @WithTags(value = { @WithTag("Cookies")})
    public void getCookiesByIPAddressInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = cookiesSteps.getCookiesForIpAddresses(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }


    @Test
    @Title("GET Cookies for IP Addresses - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Cookies1"), @WithTag("Pagination")})
    public void getCookiesByIPAddressCheckPagination() throws Exception {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = cookiesSteps.getCookiesForIpAddresses(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        CookiesForHostNames cookiesForHostNamesResponse = response.as(CookiesForHostNames.class);
        assertLastPage(host, cookiesForHostNamesResponse);
        assertions.assertAll();
    }

    private void assertLastPage(String host, CookiesForHostNames cookiesForHostNamesResponse) throws Exception {
        Integer count = cookiesForHostNamesResponse.getCount();
        if(count == 0 ){
            return;
        }
        String nextLink = cookiesForHostNamesResponse.getNextLink();

        if (nextLink != null && !cookiesForHostNamesResponse.getValue().isEmpty()) {
            // GET LAST PAGE RESPONSE
            Integer top = Integer.parseInt(Utils.getParamValueFromUrl(nextLink, "$top"));
            Integer pages = (int) Math.floor(count / top);
            Integer skip = (count % top == 0) ? count - top : pages * top;
            Response responseLastPage = Utils.isIp(host) ? cookiesSteps.getCookiesForIpAddresses(host, LAST_PAGE, top, skip) : cookiesSteps.getCookiesForHostNames(host, LAST_PAGE, top, skip);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            CookiesForHostNames cookiesForHostNamesResponseLastPage = responseLastPage.as(CookiesForHostNames.class);
            cookiesAssert.assertThat(cookiesForHostNamesResponseLastPage)
                    .assertIsLastPage(top);
        }
    }


}
