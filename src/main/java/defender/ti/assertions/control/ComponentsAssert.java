package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.ComponentWrapper;
import net.thucydides.core.annotations.Step;

import java.util.List;


public class ComponentsAssert extends GenericAssertions {

    InheritableThreadLocal<ComponentWrapper> componentWrapper = new InheritableThreadLocal<>();

    private ComponentWrapper componentWrapper() {
        return componentWrapper.get();
    }


    public ComponentsAssert assertThat(ComponentWrapper component) {
        this.componentWrapper.set(component);
        return this;
    }

    @Step("Validate component mandatory fields are not empty")
    public ComponentsAssert mandatoryFieldsAreNotEmpty() {
        List<ComponentWrapper.Component> components = componentWrapper().getValue();

        for (ComponentWrapper.Component component: components
             ) {
            assertions().assertThat(component.getFirstSeenDateTime())
                    .describedAs("id is not empty")
                    .isNotEmpty();
            assertions().assertThat(component.getFirstSeenDateTime())
                    .describedAs("First Seen Date is not empty")
                    .isNotEmpty();
            assertions().assertThat(component.getLastSeenDateTime())
                    .describedAs("Last Seen Date is not empty")
                    .isNotEmpty();
            assertions().assertThat(component.getName())
                    .describedAs("Name is not empty")
                    .isNotEmpty();
        }
        return this;
    }

    @Step("Validate component mandatory fields are not empty")
    public ComponentsAssert areDatesFormattedCorrectly() {
        List<ComponentWrapper.Component> components = componentWrapper().getValue();
        for (ComponentWrapper.Component component: components
             ) {
            this.assertBodyFieldValueMatchWith(component.getFirstSeenDateTime(), RFC3339);
            this.assertBodyFieldValueMatchWith(component.getLastSeenDateTime(), RFC3339);
        }
        return this;
    }


    public void skipIsWorkingProperly(ComponentWrapper wrapperListWithoutSkipping, ComponentWrapper wrapperList, int skip) {
        List<ComponentWrapper.Component> arrWithoutSkipping = wrapperListWithoutSkipping.getValue();
        List<ComponentWrapper.Component>  sublist = arrWithoutSkipping.subList(skip, arrWithoutSkipping.size());
        assertions().assertThat(sublist).describedAs("Components Skip works properly").isEqualTo(wrapperList.getValue());
    }

    public void topIsWorkingProperly(int top, ComponentWrapper wrapper) {
        assertions().assertThat(wrapper.getValue().size()).describedAs("Top is working properly").isEqualTo(top);

    }

    @Step("Validate that the response is the Last Page")
    public ComponentsAssert assertIsLastPage(Integer top) {
        List<ComponentWrapper.Component> components = componentWrapper().getValue();
        assertions().assertThat(componentWrapper().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(components.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }

}
