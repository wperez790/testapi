package defender.ti.steps;


import defender.ti.api.BaseApiCall;
import defender.ti.api.CliftonApiCall;
import defender.ti.utils.Constants;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class PassiveDnsSteps {


    @Step("Get Passive DNS")
    public Response getPassiveDns(String name, Boolean count, Integer top, Integer skip, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Passive DNS Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getPassiveDns(name ,count,top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getPassiveDns(name, count, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Passive DNS Record with Order")
    public Response getPassiveDns(String name, Boolean count, Integer top, Integer skip, String fieldToOrder, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Passive DNS Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getPassiveDns(name, count,top, skip, DataGenerator.generateNewToken());
            case ORDER_BY_ASC:
                return CliftonApiCall.getPassiveDns(name, count, top, skip, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.asc, System.getProperty("access-token"));
            case ORDER_BY_DESC:
                return CliftonApiCall.getPassiveDns(name, count, top, skip, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.desc, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getPassiveDns(name, true, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Passive DNS Record with Filter")
    public Response getPassiveDnsRecordFilter(String name, Boolean count, Integer top, Integer skip, String filter,  CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Passive DNS Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getPassiveDns(name, true,top, skip, DataGenerator.generateNewToken());
            case FILTER:
                return CliftonApiCall.getPassiveDns(name, true, null, null, null, filter, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getPassiveDns(name, true, top, skip, System.getProperty("access-token"));
        }
    }

    // REVERSE DNS

    @Step("Get Passive DNS Reverse")
    public Response getPassiveDnsReverse(String name, Boolean count, Integer top, Integer skip, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Passive DNS Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getPassiveDnsReverse(name ,count,top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getPassiveDnsReverse(name, count, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Passive DNS Reverse Record with Order")
    public Response getPassiveDnsReverse(String name, Boolean count, Integer top, Integer skip, String fieldToOrder, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Passive DNS Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getPassiveDnsReverse(name, count,top, skip, DataGenerator.generateNewToken());
            case ORDER_BY_ASC:
                return CliftonApiCall.getPassiveDnsReverse(name, count, top, skip, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.asc, System.getProperty("access-token"));
            case ORDER_BY_DESC:
                return CliftonApiCall.getPassiveDnsReverse(name, count, top, skip, fieldToOrder+"%20"+Constants.ORDER_CLASSIFICATIONS.desc, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getPassiveDnsReverse(name, true, top, skip, System.getProperty("access-token"));
        }
    }

    @Step("Get Passive DNS Reverse Record with Filter")
    public Response getPassiveDnsReverseRecordFilter(String name, Boolean count, Integer top, Integer skip, String filter,  CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Passive DNS Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getPassiveDnsReverse(name, true,top, skip, DataGenerator.generateNewToken());
            case FILTER:
                return CliftonApiCall.getPassiveDnsReverse(name, true, null, null, null, filter, top, skip, System.getProperty("access-token"));
            default:
                return CliftonApiCall.getPassiveDnsReverse(name, true, top, skip, System.getProperty("access-token"));
        }
    }

    public Response getPassiveDnsRecords(String uuid, boolean count, Integer top, Integer skip, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Passive DNS Records");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getPassiveDnsRecords(uuid ,count,top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getPassiveDnsRecords(uuid, count, top, skip, System.getProperty("access-token"));
        }
    }
}
