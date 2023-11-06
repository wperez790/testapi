package defender.ti.steps;


import defender.ti.api.BaseApiCall;
import defender.ti.api.CliftonApiCall;
import defender.ti.utils.DataGenerator;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class ArticleSteps {


    @Step("Get Article")
    public Response getArticles(Boolean count, String select, String search, Integer top, Integer skip, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Article Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getArticles(count, select, search, top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getArticles( count, select, search, top, skip, System.getProperty("access-token"));
        }
    }
    @Step("Get Article Id")
    public Response getArticleById(String id, Boolean count, String select, String search, Integer top, Integer skip, CliftonApiCall.ApiCallHelper helper) {
        Serenity.recordReportData().withTitle("Article Record");
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getArticleById(id, count, select, search, top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getArticleById(id, count, select, search, top, skip, System.getProperty("access-token"));
        }
    }

    public Response getArticlesIndicators(String id, Boolean count, String select, String search, Integer top, Integer skip, CliftonApiCall.ApiCallHelper helper) {
        switch (helper) {
            case INVALID_CREDENTIALS:
                return CliftonApiCall.getArticleIndicators(id, count, select, search, top, skip, DataGenerator.generateNewToken());
            default:
                return CliftonApiCall.getArticleIndicators(id, count, select, search, top, skip, System.getProperty("access-token"));
        }
    }


}
