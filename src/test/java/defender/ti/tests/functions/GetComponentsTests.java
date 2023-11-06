package defender.ti.tests.functions;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.ComponentsAssert;

import defender.ti.assertions.control.GetComponentsForHostNamesAssert;
import defender.ti.model.ResponseBodyImpl;
import defender.ti.model.ResponseBodyImpl.ComponentsForHostNames;
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
public class GetComponentsTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    GetComponentsForHostNamesAssert componentsAssert;

    @Test
    @Title("GET Components for Hostnames - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Components"), @WithTag("happy-path")})
    public void getComponentsForHostNamesHappyPath() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = componentsSteps.getComponentsForHostNames(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        ComponentsForHostNames componentsForHostNamesResponse = response.as(ComponentsForHostNames.class);
        componentsAssert.assertThat(componentsForHostNamesResponse)
                .mandatoryFieldsAreNotEmpty()
        .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET components for hostNames - Invalid Credentials")
    @WithTags(value = { @WithTag("Components")})
    public void getComponentsByHostInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = componentsSteps.getComponentsForHostNames(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                //.assertHeaderContainsAttribute("x-ms-request-id")
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }

    @Test
    @Title("GET Components for Hostnames - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Components"), @WithTag("Pagination")})
    public void getComponentsByHostnamesCheckPagination() throws Exception {
        String host = DataGenerator.hostNameGenerator("riskiq");
        Response response = componentsSteps.getComponentsForHostNames(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        ResponseBodyImpl.ComponentsForHostNames componentsForHostNamesResponse = response.as(ResponseBodyImpl.ComponentsForHostNames.class);
        assertLastPage(host, componentsForHostNamesResponse);
        assertions.assertAll();
    }

    @Test
    @Title("GET Components for IP Addresses - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Components"), @WithTag("happy-path")})
    public void getComponentsByIPAddress() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = componentsSteps.getComponentsForIpAddresses(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        ComponentsForHostNames componentWrapperResponse = response.as(ComponentsForHostNames.class);
        componentsAssert.assertThat(componentWrapperResponse)
                .mandatoryFieldsAreNotEmpty()
                .areDatesFormattedCorrectly();

        assertions.assertAll();
    }


    @Test
    @Title("GET Components for IP Addresses - Invalid Credentials")
    @WithTags(value = { @WithTag("Components")})
    public void getComponentsByIPAddressInvalidCredentials() {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = componentsSteps.getComponentsForIpAddresses(host, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);

        assertions.assertAll();
    }



    @Test
    @Title("GET Components for IP Addresses - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Components"), @WithTag("Pagination")})
    public void getComponentsByIPAddressCheckPagination() throws Exception {
        String host = DataGenerator.hostNameGenerator("8.8.8.8");
        Response response = componentsSteps.getComponentsForIpAddresses(host, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        ResponseBodyImpl.ComponentsForHostNames componentsForHostNamesResponse = response.as(ResponseBodyImpl.ComponentsForHostNames.class);
        assertLastPage(host, componentsForHostNamesResponse);
        assertions.assertAll();
    }

    private void assertLastPage(String host, ComponentsForHostNames componentsForHostNamesResponse) throws Exception {
        Integer count = componentsForHostNamesResponse.getCount();
        if(count == 0 ){
            return;
        }
        String nextLink = componentsForHostNamesResponse.getNextLink();
        if(nextLink != null && !componentsForHostNamesResponse.getValue().isEmpty()){
            // GET LAST PAGE RESPONSE
            Integer top = Integer.parseInt(Utils.getParamValueFromUrl(nextLink, "$top"));
            Integer pages = (int) Math.floor(count / top);
            Integer skip = count % top == 0 ? count - top : pages * top;
            Response responseLastPage = Utils.isIp(host) ? componentsSteps.getComponentsForIpAddresses(host, LAST_PAGE, top, skip) : componentsSteps.getComponentsForHostNames(host, LAST_PAGE, top, skip);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            ComponentsForHostNames componentsForHostNamesResponseLastPage = responseLastPage.as(ComponentsForHostNames.class);
            componentsAssert.assertThat(componentsForHostNamesResponseLastPage)
                    .assertIsLastPage(top);
        }
    }


}
