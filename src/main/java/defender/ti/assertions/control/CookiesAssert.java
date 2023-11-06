package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.CookiesWrapper;
import net.thucydides.core.annotations.Step;


public class CookiesAssert extends GenericAssertions {

    InheritableThreadLocal<CookiesWrapper> cookiesWrapper = new InheritableThreadLocal<>();

    private CookiesWrapper cookiesWrapper() {
        return cookiesWrapper.get();
    }


    public CookiesAssert assertThat(CookiesWrapper cookie) {
        this.cookiesWrapper.set(cookie);
        return this;
    }

    @Step("Validate component mandatory fields are not empty")
    public CookiesAssert mandatoryFieldsAreNotEmpty() {
        CookiesWrapper.Cookies[] cookies = cookiesWrapper().getValue();

        for (CookiesWrapper.Cookies cookie: cookies
             ) {
            assertions().assertThat(cookie.getFirstSeenDateTime())
                    .describedAs("id is not empty")
                    .isNotEmpty();
            assertions().assertThat(cookie.getFirstSeenDateTime())
                    .describedAs("First Seen Date is not empty")
                    .isNotEmpty();
            assertions().assertThat(cookie.getLastSeenDateTime())
                    .describedAs("Last Seen Date is not empty")
                    .isNotEmpty();
            assertions().assertThat(cookie.getDomain())
                    .describedAs("Domain is not empty")
                    .isNotEmpty();
            assertions().assertThat(cookie.getName())
                    .describedAs("Name is not empty")
                    .isNotEmpty();
        }
        return this;
    }

    @Step("Validate component mandatory fields are not empty")
    public CookiesAssert areDatesFormattedCorrectly() {
        CookiesWrapper.Cookies[] cookies = cookiesWrapper().getValue();
        for (CookiesWrapper.Cookies cookie: cookies
             ) {
            this.assertBodyFieldValueMatchWith(cookie.getFirstSeenDateTime(), RFC3339);
            this.assertBodyFieldValueMatchWith(cookie.getLastSeenDateTime(), RFC3339);
        }
        return this;
    }


}
