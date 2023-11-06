package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.ArticlesAssert;
import defender.ti.assertions.control.ComponentsAssert;
import defender.ti.model.ResponseBodyImpl.ArticleWrapper;
import defender.ti.model.ResponseBodyImpl.ArticleIndicatorsWrapper;
import defender.ti.model.ResponseBodyImpl.ArticleIndicatorsWrapper.ArticleIndicators;
import defender.ti.model.ResponseBodyImpl.ComponentWrapper;
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
public class ArticlesTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    ComponentsAssert componentsAssert;
    @Steps
    ArticlesAssert articlesAssert;

    @Test
    @Title("GET Articles - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticlesHappyPath() {
        Response response = articleSteps.getArticles( true, null, null, null, null,  VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }


    @Test
    @Title("GET Articles - Invalid Credentials")
    @WithTags(value = {@WithTag("Articles"), @WithTag("InvalidCredentials")})
    public void getArticlesInvalidCredentials() {
        Response response = articleSteps.getArticles(true, null, null, null, null, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }
    

    @Test
    @Title("GET Articles - Mandatory fields are not empty")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticlesMandatoryFieldsAreNotEmpty() {
        Response response = articleSteps.getArticles(true, null, null, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        ArticleWrapper wrapper = response.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapper).mandatoryFieldsAreNotEmpty();
        assertions.assertAll();
    }

    @Test
    @Title("GET Articles - Top test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticlesTop() {
        int top = 5;
        Response response = articleSteps.getArticles(true, null, null, top, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        ArticleWrapper wrapperList = response.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapperList).topIsWorkingProperly(top, wrapperList);
    }

    @Test
    @Title("GET Articles  - Skip test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticlesSkip() {
        int skip = 4;
        int top = 5;
        Response response = articleSteps.getArticles(true, null, null, top, skip, VALID);
        ArticleWrapper wrapperList = response.as(ArticleWrapper.class);
        Response responseListWithoutSkipping = articleSteps.getArticles(true, null,null, (top + skip), 0 , VALID);
        ArticleWrapper wrapperListWithoutSkipping = responseListWithoutSkipping.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapperList).skipIsWorkingProperly(wrapperListWithoutSkipping, wrapperList, skip);
    }

    @Test
    @Title("GET Articles - Select test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticlesSelect() {
        int skip = 0;
        int top = 5;
        String select = "createdDateTime";
        Response response = articleSteps.getArticles(true, null, null, top, skip, VALID);
        ArticleWrapper wrapperList = response.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapperList).selectIsWorkingProperly(select);
    }

    @Test
    @Title("GET Articles - Search test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticlesSearch() {
        int skip = 0;
        int top = 5;
        String search = "microsoft";
        Response response = articleSteps.getArticles(true, null, search, top, skip, VALID);
        ArticleWrapper wrapperList = response.as(ArticleWrapper.class);
        articlesAssert.assertThat(wrapperList).searchIsWorkingProperly(search);
    }

    @Test
    @Title("GET Article By Id - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleByIdHappyPath() {
        Response response = articleSteps.getArticleById( Constants.ARTICLE_ID, false, null, null, null, null,  VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }

    @Test
    @Title("GET Article By Id - Invalid Token")
    @WithTags(value = {@WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleByIdInvalidToken() {
        Response response = articleSteps.getArticleById( Constants.ARTICLE_ID, true, null, null, null, null,  INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }

    @Test
    @Title("GET Article Indicators - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleIndicatorsHappyPath() {
        Response response = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID, true, null, null, null, null,  VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }

    @Test
    @Title("GET Article Indicators - Invalid Credentials")
    @WithTags(value = {@WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleIndicatorsInvalidToken() {
        Response response = articleSteps.getArticlesIndicators( Constants.ARTICLE_ID, true, null, null, null, null,  INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }

    @Test
    @Title("GET Articles - Mandatory fields are not empty")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleIndicatorsMandatoryFieldsAreNotEmpty() {
        Response response = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID,true, null, null, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        ArticleIndicatorsWrapper wrapper = response.as(ArticleIndicatorsWrapper.class);
        articlesAssert.assertThat(wrapper).mandatoryFieldsAreNotEmptyFromIndicators();
        assertions.assertAll();
    }


    @Test
    @Title("GET Articles - Top test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleIndicatorsTop() {
        int top = 5;
        Response response = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID,false, null, null, top, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        ArticleIndicatorsWrapper wrapperList = response.as(ArticleIndicatorsWrapper.class);
        articlesAssert.assertThat(wrapperList).topIsWorkingProperly(top);
    }

    @Test
    @Title("GET Articles  - Skip test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleIndicatorsSkip() {
        int skip = 4;
        int top = 5;
        Response response = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID, true, null, null, top, skip, VALID);
        ArticleIndicatorsWrapper wrapperList = response.as(ArticleIndicatorsWrapper.class);
        Response responseListWithoutSkipping = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID,true, null,null, (top + skip), 0 , VALID);
        ArticleIndicatorsWrapper wrapperListWithoutSkipping = responseListWithoutSkipping.as(ArticleIndicatorsWrapper.class);
        articlesAssert.assertThat(wrapperList).skipIsWorkingProperly(wrapperListWithoutSkipping, wrapperList, skip);
    }


    @Test
    @Title("GET Articles - Select test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleIndicatorsSelect() {
        int skip = 0;
        int top = 5;
        String select = "id";
        Response response = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID,true, null, null, top, skip, VALID);
        ArticleIndicatorsWrapper wrapperList = response.as(ArticleIndicatorsWrapper.class);
        articlesAssert.assertThat(wrapperList).selectIsWorkingProperlyInIndicators(select);
    }

    @Test
    @Title("GET Articles - Search test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("happy-path")})
    public void getArticleIndicatorsSearch() {
        int skip = 0;
        int top = 5;
        String search = "microsoft";
        Response response = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID,true, null, search, top, skip, VALID);
        ArticleIndicatorsWrapper wrapperList = response.as(ArticleIndicatorsWrapper.class);
        articlesAssert.assertThat(wrapperList).searchIsWorkingProperlyInIndicators(search);
    }

    @Test
    @Title("GET Articles  - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("Pagination")})
    public void getArticlesCheckPagination() throws Exception {
        Response response = articleSteps.getArticles(true, null, null, null, null, VALID);
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
            Response responseLastPage = articleSteps.getArticles(true, null, null, top, skip, LAST_PAGE);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            ArticleWrapper wrapperLastPage = responseLastPage.as(ArticleWrapper.class);
            articlesAssert.assertThat(wrapperLastPage)
                    .assertIsLastPage(top);
        }
    }


    // ARTICLE INDICATORS PAGINATION

    @Test
    @Title("GET Article Indicators - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("Articles"), @WithTag("Pagination")})
    public void getArticlesIndicatorsCheckPagination() throws Exception {
        Response response = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID, true, null, null, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        ArticleIndicatorsWrapper wrapper = response.as(ArticleIndicatorsWrapper.class);
        assertLastPage(wrapper);
        assertions.assertAll();
    }

    private void assertLastPage(ArticleIndicatorsWrapper wrapper) throws Exception {
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
            Response responseLastPage = articleSteps.getArticlesIndicators(Constants.ARTICLE_ID, true, null, null, top, skip, LAST_PAGE);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            ArticleIndicatorsWrapper wrapperLastPage = responseLastPage.as(ArticleIndicatorsWrapper.class);
            articlesAssert.assertThat(wrapperLastPage)
                    .assertIsLastPage(top);
        }
    }

}
