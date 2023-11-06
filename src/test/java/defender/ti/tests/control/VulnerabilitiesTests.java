package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.ArticlesAssert;
import defender.ti.assertions.control.ComponentsAssert;
import defender.ti.assertions.control.VulnerabilitiesAssert;
import defender.ti.model.ResponseBodyImpl;
import defender.ti.model.ResponseBodyImpl.ComponentWrapper;
import defender.ti.model.ResponseBodyImpl.ArticleWrapper;
import defender.ti.model.ResponseBodyImpl.Vulnerability;
import defender.ti.tests.BaseTest;
import defender.ti.utils.Constants;
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
public class VulnerabilitiesTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    VulnerabilitiesAssert vulnerabilitiesAssert;
    @Steps
    ComponentsAssert componentsAssert;
    @Steps
    ArticlesAssert articlesAssert;

    @Test
    @Title("GET Vulnerabilities - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesHappyPath() {
        Response response = vulnerabilitySteps.getVulnerability(Constants.CVE_VALUE, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        Vulnerability wrapper = response.as(Vulnerability.class);
        vulnerabilitiesAssert.assertThat(wrapper).vulnerabilityIsNotEmpty();
        assertions.assertAll();
    }


    @Test
    @Title("GET Vulnerabilities - Invalid Credentials")
    @WithTags(value = { @WithTag("Vulnerabilities")})
    public void getVulnerabilitiesInvalidCredentials() {
        Response response = vulnerabilitySteps.getVulnerability(Constants.CVE_VALUE, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }
    

    @Test
    @Title("GET Vulnerability - Mandatory fields are not empty")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesMandatoryFieldsAreNotEmpty() {
        Response response = vulnerabilitySteps.getVulnerability(Constants.CVE_VALUE, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        Vulnerability wrapper = response.as(Vulnerability.class);
        vulnerabilitiesAssert.assertThat(wrapper).mandatoryFieldsFromListAreNotEmpty();
        assertions.assertAll();
    }


    @Test
    @Title("GET Vulnerability Articles - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesArticlesWorksProperly() {
        Response response = vulnerabilitySteps.getVulnerabilityArticles(Constants.CVE_VALUE,true, 10, 0, null,VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }

    @Test
    @Title("GET Vulnerabilities Articles - Top test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesArticlesTop() {
        int top = 5;
        Response responseList = vulnerabilitySteps.getVulnerabilityArticles(Constants.CVE_VALUE, true, top, null, null, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        ArticleWrapper wrapperList = responseList.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapperList).topIsWorkingProperly(top, wrapperList);
    }

    @Test
    @Title("GET Vulnerabilities Articles - Skip test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesArticlesSkip() {
        int skip = 4;
        int top = 5;
        Response responseList = vulnerabilitySteps.getVulnerabilityArticles(Constants.CVE_VALUE, true, top, skip, null, VALID);
        ArticleWrapper wrapperList = responseList.as(ArticleWrapper.class);
        Response responseListWithoutSkipping = vulnerabilitySteps.getVulnerabilityArticles(Constants.CVE_VALUE, true, (top + skip), 0, null, VALID);
        ArticleWrapper wrapperListWithoutSkipping = responseListWithoutSkipping.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapperList).skipIsWorkingProperly(wrapperListWithoutSkipping, wrapperList, skip);
    }

    @Test
    @Title("GET Vulnerabilities Articles - Select test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesArticlesSelect() {
        int skip = 0;
        int top = 5;
        String select = "createdDateTime";
        Response responseList = vulnerabilitySteps.getVulnerabilityArticles(Constants.CVE_VALUE, true, top, skip, select, VALID);
        ArticleWrapper wrapperList = responseList.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapperList).selectIsWorkingProperly(select);
    }

    @Test
    @Title("GET Vulnerability Components - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesFilterWorksProperly() {
        Response response = vulnerabilitySteps.getVulnerabilityComponents(Constants.CVE_VALUE,true, 10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }

    @Test
    @Title("GET Vulnerabilities Components - Top test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesTop() {
        int top = 5;
        Response responseList = vulnerabilitySteps.getVulnerabilityComponents(Constants.CVE_VALUE, true, top, null, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        ComponentWrapper wrapperList = responseList.as(ComponentWrapper.class);
        componentsAssert.assertThat(wrapperList).topIsWorkingProperly(top, wrapperList);
    }

    @Test
    @Title("GET Vulnerabilities Components - Skip test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("happy-path")})
    public void getVulnerabilitiesSkip() {
        int skip = 4;
        int top = 5;
        Response responseList = vulnerabilitySteps.getVulnerabilityComponents(Constants.CVE_VALUE, true, top, skip, VALID);
        ComponentWrapper wrapperList = responseList.as(ComponentWrapper.class);
        Response responseListWithoutSkipping = vulnerabilitySteps.getVulnerabilityComponents(Constants.CVE_VALUE, true, (top + skip), 0, VALID);
        ComponentWrapper wrapperListWithoutSkipping = responseListWithoutSkipping.as(ComponentWrapper.class);
        componentsAssert.assertThat(wrapperList).skipIsWorkingProperly(wrapperListWithoutSkipping, wrapperList, skip);
    }


    @Test
    @Title("GET Vulnerabilities Components - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("Pagination")})
    public void getVulnerabilitiesComponentsCheckPagination() throws Exception {
        Response response = vulnerabilitySteps.getVulnerabilityComponents(Constants.CVE_VALUE,true, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        ComponentWrapper wrapper = response.as(ComponentWrapper.class);
        assertLastPage(wrapper);
        assertions.assertAll();
    }

    private void assertLastPage(ComponentWrapper wrapper) throws Exception {
        Integer count = wrapper.getCount();
        if(count == 0) {
            return;
        }
        String nextLink = wrapper.getNextLink();

        if (nextLink != null && !wrapper.getValue().isEmpty()) {
            // GET LAST PAGE RESPONSE
            Integer top = Integer.parseInt(Utils.getParamValueFromUrl(nextLink, "$top"));
            Integer pages = (int) Math.floor(count / top);
            Integer skip = (count % top == 0) ? count - top : pages * top;
            Response responseLastPage = vulnerabilitySteps.getVulnerabilityComponents(Constants.CVE_VALUE, true, top, skip, LAST_PAGE);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            ComponentWrapper wrapperLastPage = responseLastPage.as(ComponentWrapper.class);
            componentsAssert.assertThat(wrapperLastPage)
                    .assertIsLastPage(top);
        }
    }

    // Vulnerability Articles

    @Test
    @Title("GET Vulnerabilities Articles - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Vulnerabilities"), @WithTag("Pagination")})
    public void getVulnerabilitiesArticlesCheckPagination() throws Exception {
        Response response = vulnerabilitySteps.getVulnerabilityArticles(Constants.CVE_VALUE,true, null, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        ArticleWrapper wrapper = response.as(ArticleWrapper.class);
        assertLastPage(wrapper);
        assertions.assertAll();
    }

    private void assertLastPage(ArticleWrapper wrapper) throws Exception {
        Integer count = wrapper.getCount();
        if(count == 0) {
            return;
        }
        String nextLink = wrapper.getNextLink();

        if (nextLink != null && !wrapper.getValue().isEmpty()) {
            // GET LAST PAGE RESPONSE
            Integer top = Integer.parseInt(Utils.getParamValueFromUrl(nextLink, "$top"));
            Integer pages = (int) Math.floor(count / top);
            Integer skip = (count % top == 0) ? count - top : pages * top;
            Response responseLastPage = vulnerabilitySteps.getVulnerabilityArticles(Constants.CVE_VALUE, true, top, skip, null, LAST_PAGE);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            ArticleWrapper wrapperLastPage = responseLastPage.as(ArticleWrapper.class);
            articlesAssert.assertThat(wrapperLastPage)
                    .assertIsLastPage(top);
        }
    }

}
