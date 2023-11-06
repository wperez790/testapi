package defender.ti.assertions;

import defender.ti.assertions.basic.StringAssertions;
import defender.ti.model.ResponseBodyImpl;
import net.thucydides.core.annotations.Step;

public class ErrorHandleAssert extends GenericAssertions{

    InheritableThreadLocal<ResponseBodyImpl.Error> e = new InheritableThreadLocal<>();

    private ResponseBodyImpl.Error.Body error() {
        return e.get().getError();
    }

    public ErrorHandleAssert assertThat(ResponseBodyImpl.Error error) {
        e.set(error);
        return this;
    }

    @Step("Validate error code is equal to {0}")
    public ErrorHandleAssert assertErrorCode(String code) {
        StringAssertions.stringEqTo(assertions(), error().getCode(), code);
        return this;
    }

    @Step("Validate error is null")
    public ErrorHandleAssert assertError() {
        assertions().assertThat(error()).isNull();
        return this;
    }

    @Step("Validate message is equal to {0}")
    public ErrorHandleAssert assertErrorMessage(String message) {
        StringAssertions.stringEqTo(assertions(), error().getMessage(), message);
        return this;
    }

    @Step("Validate target is equal to {0}")
    public ErrorHandleAssert assertErrorTarget(String target) {
        StringAssertions.stringEqTo(assertions(), error().getTarget(), target);
        return this;
    }

    @Step("Validate details error code is equal to {0}")
    public ErrorHandleAssert assertDetailsErrorCode(String code) {
        StringAssertions.stringEqTo(assertions(), error().getDetails().get(1).getCode(), code);
        return this;
    }
    @Step("Validate details message is equal to {0}")
    public ErrorHandleAssert assertDetailsErrorMessage(String message) {
        StringAssertions.stringEqTo(assertions(), error().getDetails().get(1).getMessage(), message);
        return this;
    }

    @Step("Validate message is equal to {0}")
    public ErrorHandleAssert assertErrorMessageMatch(String regex) {
        StringAssertions.stringMatchWith(assertions(), error().getMessage(), regex);
        return this;
    }
}
