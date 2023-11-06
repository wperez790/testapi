package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.Host;
import net.thucydides.core.annotations.Step;


public class HostAssert extends GenericAssertions {

    InheritableThreadLocal<Host> host = new InheritableThreadLocal<>();

    private Host host() {
        return host.get();
    }


    public HostAssert assertThat(Host host) {
        this.host.set(host);
        return this;
    }

    @Step("Validate host mandatory fields are not empty")
    public HostAssert mandatoryFieldsAreNotEmpty() {
        assertions().assertThat(host().getFirstSeenDateTime())
                .describedAs("id is not empty")
                .isNotEmpty();
        assertions().assertThat(host().getFirstSeenDateTime())
                .describedAs("First Seen Date is not empty")
                .isNotEmpty();
        assertions().assertThat(host().getLastSeenDateTime())
                .describedAs("Last Seen Date is not empty")
                .isNotEmpty();
        return this;
    }


}
