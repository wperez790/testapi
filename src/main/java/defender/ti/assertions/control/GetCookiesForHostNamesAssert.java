package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.CookiesForHostNames;
import net.thucydides.core.annotations.Step;

import java.util.List;


public class GetCookiesForHostNamesAssert extends GenericAssertions {

    InheritableThreadLocal<CookiesForHostNames> cookiesForHostNames = new InheritableThreadLocal<>();

    private CookiesForHostNames cookiesForHostNames() {
        return cookiesForHostNames.get();
    }


    public GetCookiesForHostNamesAssert assertThat(CookiesForHostNames cookie) {
        this.cookiesForHostNames.set(cookie);
        return this;
    }

    @Step("Validate cookie for hostnames mandatory fields are not empty")
    public GetCookiesForHostNamesAssert mandatoryFieldsAreNotEmpty() {
        List<CookiesForHostNames.CookiesBindHost> cookies = cookiesForHostNames().getValue();
        for (CookiesForHostNames.CookiesBindHost cookie: cookies
             ) {
            assertions().assertThat(cookie.getHost().getId())
                    .describedAs("bind Host Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(cookie.getName())
                    .describedAs("Name is not empty")
                    .isNotEmpty();
        }
        return this;
    }

    @Step("Validate cookie date fields are in the right format")
    public GetCookiesForHostNamesAssert areDatesFormattedCorrectly() {
        List<CookiesForHostNames.CookiesBindHost> cookies = cookiesForHostNames().getValue();
        for (CookiesForHostNames.CookiesBindHost cookie: cookies
             ) {
            this.assertBodyFieldValueMatchWith(cookie.getFirstSeenDateTime(), RFC3339);
            this.assertBodyFieldValueMatchWith(cookie.getLastSeenDateTime(), RFC3339);
        }
        return this;
    }

    @Step("Validate that the response is the Last Page")
    public GetCookiesForHostNamesAssert assertIsLastPage(Integer top) {
        List<CookiesForHostNames.CookiesBindHost> cookies = cookiesForHostNames().getValue();
        assertions().assertThat(cookiesForHostNames().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(cookies.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }


}
