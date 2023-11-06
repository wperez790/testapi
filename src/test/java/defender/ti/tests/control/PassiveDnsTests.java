package defender.ti.tests.control;

import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.assertions.control.PassiveDnsAssert;
import defender.ti.model.ResponseBodyImpl;
import defender.ti.model.ResponseBodyImpl.PassiveDnsRecordWrapper;
import defender.ti.model.ResponseBodyImpl.PassiveDnsRecordWrapper.PassiveDnsRecord;
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

import java.text.ParseException;

import static defender.ti.api.BaseApiCall.ApiCallHelper.*;
import static defender.ti.assertions.GenericAssertions.RFC5322;

@RunWith(SerenityRunner.class)
@Concurrent
public class PassiveDnsTests extends BaseTest {

    @Steps
    ErrorHandleAssert assertions;

    @Steps
    PassiveDnsAssert passiveDnsAssert;

    @Test
    @Title("GET PassiveDns - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsHappyPath() {
        Response response = passiveDnsSteps.getPassiveDns("microsoft.com",true,10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).passiveDnsRecordIsNotEmpty();
        assertions.assertAll();
    }


    @Test
    @Title("GET PassiveDns - Invalid Credentials")
    @WithTags(value = { @WithTag("PassiveDns")})
    public void getPassiveDnsInvalidCredentials() {
        Response response = passiveDnsSteps.getPassiveDns("microsoft.com", true, 10, 0, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }
    

    @Test
    @Title("GET PassiveDns - Mandatory fields are not empty")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsMandatoryFieldsAreNotEmpty() {
        Response response = passiveDnsSteps.getPassiveDns("microsoft.com",true,10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).mandatoryFieldsFromListAreNotEmpty();
        assertions.assertAll();
    }

    @Test
    @Title("GET PassiveDns - Filter works properly")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns")})
    public void getPassiveDnsFilterWorksProperly() {
        Response response = passiveDnsSteps.getPassiveDnsRecordFilter("microsoft.com",true, 10, 0, Constants.RECORD_TYPE_EQ_A, FILTER);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).filterIsWorkingProperly(Constants.RECORD_TYPE_EQ_A);
        assertions.assertAll();
    }

    @Test
    @Title("GET PassiveDns - Top test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsTop() {
        int top = 5;
        Response responseList = passiveDnsSteps.getPassiveDns("microsoft.com", true, top, null, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        PassiveDnsRecordWrapper wrapperList = responseList.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapperList).topIsWorkingProperly(top);
    }

    @Test
    @Title("GET PassiveDns - Skip test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsSkip() {
        int skip = 4;
        int top = 5;
        Response responseList = passiveDnsSteps.getPassiveDns("microsoft.com", true, top, skip, VALID);
        PassiveDnsRecordWrapper wrapperList = responseList.as(PassiveDnsRecordWrapper.class);
        Response responseListWithoutSkipping = passiveDnsSteps.getPassiveDns("microsoft.com", true, (top + skip), 0, VALID);
        PassiveDnsRecordWrapper wrapperListWithoutSkipping = responseListWithoutSkipping.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapperList).skipIsWorkingProperly(wrapperListWithoutSkipping, wrapperList, skip);
    }


    @Test
    @Title("GET PassiveDns - Order by FirstSeen ASC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsOrderAsc() throws ParseException {
            Response response = passiveDnsSteps.getPassiveDns("microsoft.com", true, 10, 0, "firstSeenDateTime", ORDER_BY_ASC);
            assertions.assertStatusCode(response, HttpStatus.OK_200)
                    .assertHeaderMatchWith("Date", RFC5322);
            PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
            passiveDnsAssert.assertThat(wrapper).orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS.asc);
            assertions.assertAll();
    }

    @Test
    @Title("GET PassiveDns - Order by FirstSeen DESC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsOrderDesc() throws ParseException {
        Response response = passiveDnsSteps.getPassiveDns("microsoft.com", true, 10, 0, "firstSeenDateTime", ORDER_BY_DESC);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS.desc);
        assertions.assertAll();
    }


    // REVERSE DNS



    @Test
    @Title("GET PassiveDns - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsReverseHappyPath() {
        Response response = passiveDnsSteps.getPassiveDnsReverse("microsoft.com",true,10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).passiveDnsRecordIsNotEmpty();
        assertions.assertAll();
    }


    @Test
    @Title("GET PassiveDns - Invalid Credentials")
    @WithTags(value = { @WithTag("PassiveDns")})
    public void getPassiveDnsReverseInvalidCredentials() {
        Response response = passiveDnsSteps.getPassiveDnsReverse("microsoft.com", true, 10, 0, INVALID_CREDENTIALS);
        assertions.assertStatusCode(response, HttpStatus.UNAUTHORIZED_401)
                .assertHeaderMatchWith("Date", RFC5322);
        assertions.assertAll();
    }


    @Test
    @Title("GET PassiveDns - Mandatory fields are not empty")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsReverseMandatoryFieldsAreNotEmpty() {
        Response response = passiveDnsSteps.getPassiveDnsReverse("microsoft.com",true,10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).mandatoryFieldsFromListAreNotEmpty();
        assertions.assertAll();
    }

    @Test
    @Title("GET PassiveDns - Filter works properly")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns")})
    public void getPassiveDnsReverseFilterWorksProperly() {
        Response response = passiveDnsSteps.getPassiveDnsReverseRecordFilter("microsoft.com",true, 10, 0, Constants.RECORD_TYPE_EQ_CNAME, FILTER);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).filterIsWorkingProperly(Constants.RECORD_TYPE_EQ_CNAME);
        assertions.assertAll();
    }

    @Test
    @Title("GET PassiveDns - Top test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsReverseTop() {
        int top = 5;
        Response responseList = passiveDnsSteps.getPassiveDnsReverse("microsoft.com", true, top, null, VALID);
        assertions.assertStatusCode(responseList, HttpStatus.OK_200);
        PassiveDnsRecordWrapper wrapperList = responseList.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapperList).topIsWorkingProperly(top);
    }

    @Test
    @Title("GET PassiveDns - Skip test")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsReverseSkip() {
        int skip = 4;
        int top = 5;
        Response responseList = passiveDnsSteps.getPassiveDnsReverse("microsoft.com", true, top, skip, VALID);
        PassiveDnsRecordWrapper wrapperList = responseList.as(PassiveDnsRecordWrapper.class);
        Response responseListWithoutSkipping = passiveDnsSteps.getPassiveDns("microsoft.com", true, (top + skip), 0, VALID);
        PassiveDnsRecordWrapper wrapperListWithoutSkipping = responseList.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapperList).skipIsWorkingProperly(wrapperListWithoutSkipping, wrapperList, skip);
    }


    @Test
    @Title("GET PassiveDns - Order by FirstSeen ASC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsReverseOrderAsc() throws ParseException {
        Response response = passiveDnsSteps.getPassiveDnsReverse("microsoft.com", true, 10, 0, "firstSeenDateTime", ORDER_BY_ASC);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS.asc);
        assertions.assertAll();
    }

    @Test
    @Title("GET PassiveDns - Order by FirstSeen DESC is working")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("happy-path")})
    public void getPassiveDnsReverseOrderDesc() throws ParseException {
        Response response = passiveDnsSteps.getPassiveDnsReverse("microsoft.com", true, 10, 0, "firstSeenDateTime", ORDER_BY_DESC);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        passiveDnsAssert.assertThat(wrapper).orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS.desc);
        assertions.assertAll();
    }

    // PASSIVE DNS RECORDS

    @Test
    @Title("GET PassiveDnsRecords - Happy Path")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDns"), @WithTag("PassiveDnsRecords"), @WithTag("happy-path")})
    public void getPassiveDnsRecordsHappyPath() {
        // Get a valid Id
        Response response = passiveDnsSteps.getPassiveDns("microsoft.com",true,10, 0, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200)
                .assertHeaderMatchWith("Date", RFC5322);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        // Get passivedns records
        if(wrapper.getValue().size() > 0) {
            Response responsePassiveDnsRecords = passiveDnsSteps.getPassiveDnsRecords(wrapper.getValue().get(0).getId(),false,null, null, VALID);
            PassiveDnsRecord passiveDns = responsePassiveDnsRecords.as(PassiveDnsRecord.class);
            passiveDnsAssert.assertThat(passiveDns).mandatoryFieldsAreNotEmpty();
            assertions.assertAll();
        }
    }


    @Test
    @Title("GET Passive Dns - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDnss"), @WithTag("Pagination")})
    public void getPassiveDnsCheckPagination() throws Exception {
        Response response = passiveDnsSteps.getPassiveDns("microsoft.com",true, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        assertLastPage(wrapper, false);
        assertions.assertAll();
    }


    private void assertLastPage(PassiveDnsRecordWrapper wrapper, Boolean reverse) throws Exception {
        Integer count = wrapper.getCount();
        if(count == 0) {
            return;
        }
        String nextLink = wrapper.getNextLink();

        if (!((nextLink != null && nextLink.isEmpty()) & wrapper.getValue().isEmpty())) {
            // GET LAST PAGE RESPONSE
            Integer top = Integer.parseInt(Utils.getParamValueFromUrl(nextLink, "$top"));
            Integer pages = (int) Math.floor(count / top);
            Integer skip = (count % top == 0) ? count - top : pages * top;
            Response responseLastPage = reverse
                    ? passiveDnsSteps.getPassiveDnsReverse("microsoft.com", true, top, skip, LAST_PAGE)
                    : passiveDnsSteps.getPassiveDns("microsoft.com", true, top, skip, LAST_PAGE);
            assertions.assertStatusCode(responseLastPage, HttpStatus.OK_200);
            PassiveDnsRecordWrapper wrapperLastPage = responseLastPage.as(PassiveDnsRecordWrapper.class);
            passiveDnsAssert.assertThat(wrapperLastPage)
                    .assertIsLastPage(top);
        }
    }



    // REVERSE DNS

    @Test
    @Title("GET Passive Reverse Dns - Check Pagination")
    @WithTags(value = {@WithTag("Regression"), @WithTag("PassiveDnss"), @WithTag("Pagination")})
    public void getPassiveReverseDnsCheckPagination() throws Exception {
        Response response = passiveDnsSteps.getPassiveDnsReverse("microsoft.com",true, null, null, VALID);
        assertions.assertStatusCode(response, HttpStatus.OK_200);
        PassiveDnsRecordWrapper wrapper = response.as(PassiveDnsRecordWrapper.class);
        assertLastPage(wrapper, true);
        assertions.assertAll();
    }



}
