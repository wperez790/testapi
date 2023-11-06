package defender.ti.assertions.basic;

import org.assertj.core.api.SoftAssertions;

public class StringAssertions {

    public static void stringEqTo(SoftAssertions assertions, String actual, String value) {
        assertions.assertThat(actual).describedAs(String.format("Assert %s should be equals to %s", value, actual)).isEqualTo(value);
    }

    public static void stringMatchWith(SoftAssertions assertions, String actual, String regex) {
        assertions.assertThat(actual).describedAs(String.format("Assert %s should match with %s", actual, regex)).matches(regex);
    }

}
