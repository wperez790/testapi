package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.Reputation;
import net.thucydides.core.annotations.Step;


public class ReputationAssert extends GenericAssertions {

    InheritableThreadLocal<Reputation> reputation = new InheritableThreadLocal<>();

    private Reputation reputation() {
        return reputation.get();
    }


    public ReputationAssert assertThat(Reputation reputation) {
        this.reputation.set(reputation);
        return this;
    }

    @Step("Validate reputation classification is not empty")
    public ReputationAssert classificationIsNotEmpty() {
        assertions().assertThat(reputation().getClassification())
                .describedAs("classification is not empty")
                .isNotEmpty();
        return this;
    }
    @Step("Validate reputation classification is in List")
    public ReputationAssert classificationShouldBeInList(String[] array) {
        assertions().assertThat(reputation().getClassification())
                .describedAs("classification is in list")
                .isIn(array);
        return this;
    }
    @Step("Validate reputation classification is Equal to")
    public ReputationAssert classificationIsEqualTo(String classification) {
        assertions().assertThat(reputation().getClassification())
                .describedAs("classification is equal to")
                .isEqualTo(classification);
        return this;
    }
    @Step("Validate reputation rules are Equal to")
    public ReputationAssert rulesAreEqualTo(Reputation.Rule[] rules) {
        Reputation.Rule[] responseRules = reputation().getRules();
        for(int i = 0 ; i < responseRules.length; i++){
            assertions().assertThat(responseRules[i].name)
                    .describedAs("name of rule equal to")
                    .isEqualTo(rules[i].name);
            assertions().assertThat(responseRules[i].description)
                    .describedAs("description of rule equal to")
                    .isEqualTo(rules[i].description);
            assertions().assertThat(responseRules[i].severity)
                    .describedAs("severity of rule equal to")
                    .isEqualTo(rules[i].severity);
            assertions().assertThat(responseRules[i].link)
                    .describedAs("link of rule equal to")
                    .isEqualTo(rules[i].link);
        }
        return this;
    }

}
