package defender.ti.assertions;

import defender.ti.assertions.basic.StringAssertions;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.Rule;

import static defender.ti.assertions.basic.StringAssertions.stringMatchWith;

public class GenericAssertions {

    public static final String RFC3339 = "((?:(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?))(Z|[\\+-]\\d{2}:\\d{2})?)";
    public static final String RFC3339_b = "(\\d{4})-(\\d{2})-(\\d{2})T(\\d{2})\\:(\\d{2})\\:(\\d{2}).(\\d{3})Z";
    public static final String RFC5322 = "(((Mon|Tue|Wed|Thu|Fri|Sat|Sun))[,]?\\s[0-9]{1,2})\\s(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s([0-9]{4})\\s([0-9]{2}):([0-9]{2})(:([0-9]{2}))?\\s(GMT)\\s?";

    InheritableThreadLocal<Response> r = new InheritableThreadLocal<>();
    @Rule
    public static InheritableThreadLocal<SoftAssertions> assertions = new InheritableThreadLocal<>();

    private Response response() {
        return r.get();
    }

    public SoftAssertions assertions() {
        return assertions.get();
    }

    @Step("Validate status code is equal to {1}")
    public GenericAssertions assertStatusCode(Response response, int status) {
        r.set(response);
        assertions.set(new SoftAssertions());
        response().then().assertThat().statusCode(status);
        return this;
    }

    @Step("Validate header {0} is equal to {1}")
    public GenericAssertions assertHeaderEqualsTo(String headerName, String value) {
        StringAssertions.stringEqTo(assertions(), response().getHeader(headerName), value);
        return this;
    }

    @Step("Validate header {0} match with {1}")
    public GenericAssertions assertHeaderMatchWith(String headerName, String regex) {
        stringMatchWith(assertions(), response().getHeader(headerName), regex);
        return this;
    }
    @Step("Validate header {0} match with {1}")
    public GenericAssertions assertBodyFieldValueMatchWith(String value, String regex) {
        stringMatchWith(assertions(), value, regex);
        return this;
    }


    @Step("Validate response contains header {0}")
    public GenericAssertions assertHeaderContainsAttribute(String headerName) {
        assertions().assertThat(response().getHeader(headerName)!=null).as(String.format("Expect response headers contain %s ", headerName)).isEqualTo(true);
        return this;
    }

    @Step("Validate response body is empty")
    public GenericAssertions assertResponseIsEmpty() {
        assertions().assertThat(response().then().extract().body().asPrettyString().isEmpty()).as("Expect response body is empty ").isEqualTo(true);
        return this;
    }


    public void assertAll() {
        assertions().assertAll();
    }

}
