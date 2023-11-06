package defender.ti.tests;

import defender.ti.api.CliftonApiCall;
import defender.ti.assertions.ErrorHandleAssert;
import defender.ti.steps.*;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.Before;

import static io.restassured.path.json.JsonPath.from;

@CommonsLog
public class BaseTest {
    private static RequestSpecification requestSpec;

    private static EnvironmentVariables environmentVariables;

    protected static String portal, cliftonBaseUrl, accessToken;

    @Getter
    public static String supportedLocations, location;



    @Steps
    protected ReputationSteps reputationSteps;
    @Steps
    protected HostSteps hostSteps;
    @Steps
    protected ComponentsSteps componentsSteps;
    @Steps
    protected TrackersSteps trackersSteps;
    @Steps
    protected ErrorHandleAssert assertions;
    @Steps
    protected CookiesSteps cookiesSteps;
    @Steps
    protected IntelProfileSteps intelProfileSteps;
    @Steps
    protected PassiveDnsSteps passiveDnsSteps;
    @Steps
    protected VulnerabilitySteps vulnerabilitySteps;
    @Steps
    protected ArticleSteps articleSteps;
    //endregion Steps

    @Before
    public void configureBaseApiCall() {
        String env = System.getProperty("environment");
        cliftonBaseUrl = environmentVariables.optionalProperty(String.format("azure.%s.baseUrl", env))
                .orElseThrow(() -> new RuntimeException("Control plane url is not defined"));
        new CliftonApiCall(cliftonBaseUrl, System.getProperty("access-token"));
    }


}
