package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl;
import defender.ti.model.ResponseBodyImpl.ComponentsForHostNames;
import net.thucydides.core.annotations.Step;

import java.util.List;


public class GetComponentsForHostNamesAssert extends GenericAssertions {

    InheritableThreadLocal<ResponseBodyImpl.ComponentsForHostNames> componentsForHostNames = new InheritableThreadLocal<>();

    private ComponentsForHostNames componentsForHostNames() {
        return componentsForHostNames.get();
    }


    public GetComponentsForHostNamesAssert assertThat(ComponentsForHostNames component) {
        this.componentsForHostNames.set(component);
        return this;
    }

    @Step("Validate component for hostnames mandatory fields are not empty")
    public GetComponentsForHostNamesAssert mandatoryFieldsAreNotEmpty() {
        List<ComponentsForHostNames.ComponentBindHost> components = componentsForHostNames().getValue();
        for (ComponentsForHostNames.ComponentBindHost component: components
             ) {
            assertions().assertThat(component.getHost().getId())
                    .describedAs("bind Host Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(component.getName())
                    .describedAs("Name is not empty")
                    .isNotEmpty();
        }
        return this;
    }

    @Step("Validate component date fields are in the right format")
    public GetComponentsForHostNamesAssert areDatesFormattedCorrectly() {
        List<ComponentsForHostNames.ComponentBindHost> components = componentsForHostNames().getValue();
        for (ComponentsForHostNames.ComponentBindHost component: components
             ) {
            this.assertBodyFieldValueMatchWith(component.getFirstSeenDateTime(), RFC3339);
            this.assertBodyFieldValueMatchWith(component.getLastSeenDateTime(), RFC3339);
        }
        return this;
    }

    @Step("Validate that the response is the Last Page")
    public GetComponentsForHostNamesAssert assertIsLastPage(Integer top) {
        List<ResponseBodyImpl.ComponentsForHostNames.ComponentBindHost> components = componentsForHostNames().getValue();
        assertions().assertThat(componentsForHostNames().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(components.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }


}
