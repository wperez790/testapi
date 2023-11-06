package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.IntelProfileAssert;
import defender.ti.model.ResponseBodyImpl;
import defender.ti.model.ResponseBodyImpl.IntelProfileWrapper;
import defender.ti.model.ResponseBodyImpl.IntelProfileWrapper.IntelProfile;
import defender.ti.model.ResponseBodyImpl.IntelProfileIndicatorsWrapper;
import defender.ti.model.ResponseBodyImpl.IntelProfileIndicatorsWrapper.IntelProfileIndicators;
import defender.ti.tests.BaseTest;
import defender.ti.utils.Constants;
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

import java.text.ParseException;
import java.util.Random;

import static defender.ti.api.BaseApiCall.ApiCallHelper.*;
import static defender.ti.assertions.GenericAssertions.RFC5322;

@RunWith(SerenityRunner.class)
@Concurrent
public class IntelProfileTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    IntelProfileAssert intelProfileAssert;

    // Intel Profiles

    @Test
    @Title("GET IntelProfiles - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelProfilesHappyPath() {
        Response response = intelProfileSteps.getIntelProfiles(true, 10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        IntelProfileWrapper wrapper = response.as(IntelProfileWrapper.class);
        intelProfileAssert.assertThat(wrapper).intelProfileIsNotEmpty();
        assertions.assertAll();
    }

    @Test
    @Title("GET IntelProfiles - Invalid Credentials")
    @WithTags(value = { @WithTag("IntelProfiles")})
    public void getIntelProfilesInvalidCredentials() {
        Response response = intelProfileSteps.getIntelProfiles(true, 10, 0, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }

    @Test
    @Title("GET IntelProfiles - Order by title ASC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles")})
    public void getIntelProfilesOrderByTitleAsc() {
        Response response = intelProfileSteps.getIntelProfiles(true, 10, 0, "title", null, null, ORDER_BY_ASC);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        IntelProfileWrapper wrapper = response.as(IntelProfileWrapper.class);
        intelProfileAssert.assertThat(wrapper).orderByTitleIsWorking(Constants.ORDER_CLASSIFICATIONS.asc);
        assertions.assertAll();
    }

    @Test
    @Title("GET IntelProfiles - Order by title DESC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles")})
    public void getIntelProfilesOrderByTitleDesc() {
        Response response = intelProfileSteps.getIntelProfiles(true, 10, 0, "title", null, null, ORDER_BY_DESC);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        IntelProfileWrapper wrapper = response.as(IntelProfileWrapper.class);
        intelProfileAssert.assertThat(wrapper).orderByTitleIsWorking(Constants.ORDER_CLASSIFICATIONS.desc);
        assertions.assertAll();
    }

    @Test
    @Title("GET IntelProfiles - Mandatory fields are not empty")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelProfilesMandatoryFieldsAreNotEmpty() {
        Response response = intelProfileSteps.getIntelProfiles(true, 10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        IntelProfileWrapper wrapper = response.as(IntelProfileWrapper.class);
        intelProfileAssert.assertThat(wrapper).mandatoryFieldsFromListAreNotEmpty();
        assertions.assertAll();
    }

    @Test
    @Title("GET IntelProfiles - Filter works properly")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles")})
    public void getIntelProfilesFilterWorksProperly() {
        Response response = intelProfileSteps.getIntelProfilesFilter(true, 10, 0, Constants.KIND_EQ_ACTOR, FILTER);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        IntelProfileWrapper wrapper = response.as(IntelProfileWrapper.class);
        intelProfileAssert.assertThat(wrapper).filterIsWorkingProperly(Constants.KIND_EQ_ACTOR);
        assertions.assertAll();
    }

    @Test
    @Title("GET IntelProfiles - By Id")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelProfilesById() {
        // Get a valid Id
        Response responseList = intelProfileSteps.getIntelProfiles(true, 10, 0, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        IntelProfileWrapper wrapperList = responseList.as(IntelProfileWrapper.class);
        if(wrapperList.getValue().size() > 0) {
            String id = wrapperList.getValue().get(0).getId();
            // Get the intel profile accordingly to the Id
            Response response = intelProfileSteps.getIntelProfilesById(id, true, null, null, null, VALID);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            IntelProfile intelProfileResponse = response.as(IntelProfile.class);
            intelProfileAssert.assertThat(intelProfileResponse).mandatoryFieldsAreNotEmpty();
            assertions.assertAll();
        }
    }

    @Test
    @Title("GET IntelProfiles - By Id Select filter test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelProfilesSelect() {
        int skip = 0;
        int top = 5;
        String select = "id";
        // Get a valid Id
        Response responseList = intelProfileSteps.getIntelProfiles(true, 10, 0, null, select, null, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        IntelProfileWrapper wrapperList = responseList.as(IntelProfileWrapper.class);
        if(wrapperList.getValue().size() > 0) {
            Integer random = new Random().nextInt(wrapperList.getValue().size() - 1);
            String id = wrapperList.getValue().get(random).getId();
            // Get the intel profile accordingly to the Id
            Response response = intelProfileSteps.getIntelProfilesById(id, true, null, null, select,  VALID);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            IntelProfile intelProfileResponse = response.as(IntelProfile.class);
            intelProfileAssert.assertThat(intelProfileResponse).selectIdIsWorkingProperlyOnIntelProfile();
            assertions.assertAll();
        }
    }

     @Test
    @Title("GET IntelProfiles - Search test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles2"), @WithTag("happy-path")})
    public void getIntelProfilesSearch() {
        int skip = 0;
        int top = 5;
        String search = "Microsoft";
        // Get a valid Id
        Response responseList = intelProfileSteps.getIntelProfiles(false, null, null, null, null, search, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        IntelProfileWrapper wrapperList = responseList.as(IntelProfileWrapper.class);
        intelProfileAssert.assertThat(wrapperList).searchIsWorkingProperly(search);
        assertions.assertAll();
    }


    // Intel Profile Indicators

    @Test
    @Title("GET IntelProfiles - By name Indicators Mandatory fields are not empty")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelProfilesByIdIndicators() {
        // Get a valid Id
        Response responseList = intelProfileSteps.getIntelProfiles(true, 10, 0, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        IntelProfileWrapper wrapperList = responseList.as(IntelProfileWrapper.class);
        if(wrapperList.getValue().size() > 0) {
            Integer random = new Random().nextInt(wrapperList.getValue().size() - 1);
            String id = wrapperList.getValue().get(random).getId();
            // Get the intel profile accordingly to the Id
            Response response = intelProfileSteps.getIntelProfilesByNameIndicators(id, true, 10, 0, VALID);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            IntelProfileIndicatorsWrapper wrapper = response.as(IntelProfileIndicatorsWrapper.class);
            intelProfileAssert.assertThat(wrapper).mandatoryFieldsFromListIndicatorsAreNotEmpty();
            assertions.assertAll();
        }
    }

    @Test
    @Title("GET IntelProfiles - By name Indicators Order by FirstSeen ASC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiless"), @WithTag("happy-path")})
    public void getIntelProfilesByIdIndicatorsOrderAsc() throws ParseException {
        // Get a valid Id
        Response responseList = intelProfileSteps.getIntelProfiles(false, 10, 0, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        IntelProfileWrapper wrapperList = responseList.as(IntelProfileWrapper.class);
        if(wrapperList.getValue().size() > 0) {
            Integer random = new Random().nextInt(wrapperList.getValue().size() - 1);
            String id = wrapperList.getValue().get(random).getId();
            // Get the intel profile accordingly to the Id
            Response response = intelProfileSteps.getIntelProfilesByNameIndicators(id, true, 10, 0, "firstSeenDateTime", null, ORDER_BY_ASC);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            IntelProfileIndicatorsWrapper wrapper = response.as(IntelProfileIndicatorsWrapper.class);
            intelProfileAssert.assertThat(wrapper).orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS.asc);
            assertions.assertAll();
        }
    }

    @Test
    @Title("GET IntelProfiles - By name Indicators Order by FirstSeen DESC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelProfilesByIdIndicatorsOrderDesc() throws ParseException {
        // Get a valid Id
        Response responseList = intelProfileSteps.getIntelProfiles(true, 10, 0, VALID);
        IntelProfileWrapper wrapperList = responseList.as(IntelProfileWrapper.class);
        if(wrapperList.getValue().size() > 0) {
            Integer random = new Random().nextInt(wrapperList.getValue().size() - 1);
            String id = wrapperList.getValue().get(random).getId();
            // Get the intel profile accordingly to the Id
            Response response = intelProfileSteps.getIntelProfilesByNameIndicators(id, false, 10, 0, "firstSeenDateTime", null, ORDER_BY_DESC);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            IntelProfileIndicatorsWrapper wrapper = response.as(IntelProfileIndicatorsWrapper.class);
            intelProfileAssert.assertThat(wrapper).orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS.desc);
            assertions.assertAll();
        }
    }

    @Test
    @Title("GET IntelProfiles - By id Indicators Select is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelProfilesIndicatorsByIdSelectTest() {
        // Get a valid Id
        Response responseList = intelProfileSteps.getIntelProfiles(true, 10, 0, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        IntelProfileWrapper wrapperList = responseList.as(IntelProfileWrapper.class);
        if(wrapperList.getValue().size() > 0) {
            Integer random = new Random().nextInt(wrapperList.getValue().size() - 1);
            String id = wrapperList.getValue().get(random).getId();
            // Select filter
            String select = "id";
            // Get the intel profile accordingly to the Id
            Response response = intelProfileSteps.getIntelProfilesByNameIndicators(id, false, 5, 0, null, select, VALID);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            IntelProfileIndicatorsWrapper wrapper = response.as(IntelProfileIndicatorsWrapper.class);
            intelProfileAssert.assertThat(wrapper).selectIdIsWorkingProperlyOnIndicators();
            assertions.assertAll();
        }
    }

    @Test
    @Title("GET intelligenceProfileIndicators('ID')")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("happy-path")})
    public void getIntelligenceProfileIndicators() {
            // Get the intel profile accordingly to the Id
            Response response = intelProfileSteps.getIntelProfilesIndicators("f8d6367e-af59-4fe2-a4bf-d7a7497026a5", false, null, null, VALID);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            IntelProfileIndicators wrapper = response.as(IntelProfileIndicators.class);
            intelProfileAssert.assertThat(wrapper).mandatoryFieldsFromIndicatorsAreNotEmpty();
            assertions.assertAll();
        }

    @Test
    @Title("GET Intel Profiles - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiles"), @WithTag("Pagination")})
    public void getIntelProfilesCheckPagination() throws Exception {
        Response response = intelProfileSteps.getIntelProfiles(true, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        IntelProfileWrapper wrapper = response.as(IntelProfileWrapper.class);
        assertLastPage(wrapper);
        assertions.assertAll();
    }

    private void assertLastPage(IntelProfileWrapper wrapper) throws Exception {
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
            Response responseLastPage = intelProfileSteps.getIntelProfiles( true, top, skip, LAST_PAGE);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            IntelProfileWrapper intelProfilesResponseLastPage = responseLastPage.as(IntelProfileWrapper.class);
            intelProfileAssert.assertThat(intelProfilesResponseLastPage)
                    .assertIsLastPage(top);
        }
    }



    @Test
    @Title("GET Intel Profiles - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("IntelProfiless"), @WithTag("Pagination")})
    public void getIntelProfilesIndicatorsCheckPagination() throws Exception {
        Response response = intelProfileSteps.getIntelProfilesByNameIndicators("9b01de37bf66d1760954a16dc2b52fed2a7bd4e093dfc8a4905e108e4843da80",true, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        IntelProfileIndicatorsWrapper wrapper = response.as(IntelProfileIndicatorsWrapper.class);
        assertLastPage(wrapper);
        assertions.assertAll();
    }

    private void assertLastPage(IntelProfileIndicatorsWrapper wrapper) throws Exception {
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
            Response responseLastPage = intelProfileSteps.getIntelProfilesByNameIndicators("9b01de37bf66d1760954a16dc2b52fed2a7bd4e093dfc8a4905e108e4843da80", true, top, skip, LAST_PAGE);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            IntelProfileIndicatorsWrapper intelProfilesResponseLastPage = responseLastPage.as(IntelProfileIndicatorsWrapper.class);
            intelProfileAssert.assertThat(intelProfilesResponseLastPage)
                    .assertIsLastPageOnIndicators(top);
        }
    }
}
