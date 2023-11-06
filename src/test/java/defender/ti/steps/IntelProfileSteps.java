package defender.ti.steps;


import defender.ti.api.CliftonApiCall;
import defender.ti.utils.Constants;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class IntelProfileSteps {


    @Step("Get Intel Profiles")
    public Response getIntelProfiles(Boolean count, Integer top, Integer skip,  CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Intel Profiles");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getIntelProfiles(count,top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getIntelProfiles(count, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Intel Profiles with Order")
    public Response getIntelProfiles(Boolean count, Integer top, Integer skip, String fieldToOrder, String select, String search, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Intel Profiles");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getIntelProfiles(count,top, skip, DataGenerator.generateNewToken());
            case ORDER_BY_ASC:
                return CliftonApiCall.getIntelProfiles(count, select, search, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.asc, top, skip, System.getProperty("access-token"));
            case ORDER_BY_DESC:
                return CliftonApiCall.getIntelProfiles(count, select, search, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.desc, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getIntelProfiles(count, select, search, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Intel Profiles with Filter")
    public Response getIntelProfilesFilter(Boolean count, Integer top, Integer skip, String filter,  CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Intel Profiles");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getIntelProfiles(count,top, skip, DataGenerator.generateNewToken());
            case FILTER:
                return CliftonApiCall.getIntelProfiles(count, top, skip, null, null, null, filter, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getIntelProfiles(count, top, skip, System.getProperty("access-token"));
        }
    }
    @Step("Get Intel Profiles By Id")
    public Response getIntelProfilesById(String name, Boolean count, Integer top, Integer skip, String select, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Intel Profiles");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getIntelProfiles(count,top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getIntelProfilesById(name, false, select, null, null, null, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Intel Profiles By Name Indicators")
    public Response getIntelProfilesByNameIndicators(String name, Boolean count, Integer top, Integer skip,  CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Intel Profiles");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getIntelProfiles(count,top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getIntelProfilesByNameIndicators(name, count, null, null, null, null, top, skip, System.getProperty("access-token"));
        }
    }
    @Step("Get Intel Profiles Indicators")
    public Response getIntelProfilesIndicators(String name, Boolean count, Integer top, Integer skip,  CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Intel Profiles By UUID");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getIntelProfiles(count,top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getIntelProfilesIndicators(name, count, null, null, null, null, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Intel Profiles By Name Indicators")
    public Response getIntelProfilesByNameIndicators(String name, Boolean count, Integer top, Integer skip, String fieldToOrder, String select, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Intel Profiles");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getIntelProfiles(count,top, skip, DataGenerator.generateNewToken());
            case ORDER_BY_ASC:
                return CliftonApiCall.getIntelProfilesByNameIndicators(name, count, select, null, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.asc, null, top, skip, System.getProperty("access-token"));
            case ORDER_BY_DESC:
                return CliftonApiCall.getIntelProfilesByNameIndicators(name, count, select, null, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.desc, null, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getIntelProfilesByNameIndicators(name, count, select, null, null, null, top, skip, System.getProperty("access-token"));

        }
    }



}
