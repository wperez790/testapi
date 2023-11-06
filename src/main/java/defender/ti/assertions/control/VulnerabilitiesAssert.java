package defender.ti.assertions.control;

import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.Vulnerability;
import net.thucydides.core.annotations.Step;


public class VulnerabilitiesAssert extends GenericAssertions {

    InheritableThreadLocal<Vulnerability> vulnerability = new InheritableThreadLocal<>();
    //InheritableThreadLocal<VulnerabilityComponents> vulnerabilityComponents = new InheritableThreadLocal<>();

    private Vulnerability vulnerability() {
        return vulnerability.get();
    }

    public VulnerabilitiesAssert assertThat(Vulnerability vulnerabilityWrapper) {
        this.vulnerability.set(vulnerabilityWrapper);
        return this;
    }

    @Step("Validate Vulnerability is not empty")
    public VulnerabilitiesAssert vulnerabilityIsNotEmpty() {
        assertions().assertThat(vulnerability())
                .describedAs("Value of passive DNS record is not empty")
                .isNotNull();
        return this;
    }

    @Step("Validate List of Passive DNS Records mandatory fields are not empty")
    public VulnerabilitiesAssert mandatoryFieldsFromListAreNotEmpty() {
        Vulnerability vulnerability = vulnerability();

            assertions().assertThat(vulnerability.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(vulnerability.getCreatedDateTime())
                    .describedAs("Created Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(vulnerability.getLastModifiedDateTime())
                    .describedAs("Last Modified Date is not empty")
                    .isNotEmpty();
            assertions().assertThat(vulnerability.getPublishedDateTime())
                    .describedAs("Published Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(vulnerability.getDescription())
                    .describedAs("Description is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getPriorityScore())
                    .describedAs("Priority Score is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getSeverity())
                    .describedAs("Severity is not empty")
                    .isNotEmpty();
            assertions().assertThat(vulnerability.getCommonWeaknessEnumerationIds())
                    .describedAs("CommonWeaknessEnumerationIds is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getCvss2Summary())
                    .describedAs("Cvss2Summary is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getCvss3Summary())
                    .describedAs("Cvss3Summary is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getHasChatter())
                    .describedAs("hasChatter is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getExploitsAvailable())
                    .describedAs("exploitsAvailable is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getExploitsAvailable())
                    .describedAs("ExploitsAvailable is not null")
                    .isNotNull();
            assertions().assertThat(vulnerability.getActiveExploitsObserved())
                    .describedAs("ActiveExploitsObserved is not null")
                    .isNotNull();
        return this;
    }
}