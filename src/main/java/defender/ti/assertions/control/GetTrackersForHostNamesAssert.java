package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.TrackersForHostNames;
import net.thucydides.core.annotations.Step;

import java.util.List;


public class GetTrackersForHostNamesAssert extends GenericAssertions {

    InheritableThreadLocal<TrackersForHostNames> trackersForHostNames = new InheritableThreadLocal<>();

    private TrackersForHostNames trackersForHostNames() {
        return trackersForHostNames.get();
    }


    public GetTrackersForHostNamesAssert assertThat(TrackersForHostNames component) {
        this.trackersForHostNames.set(component);
        return this;
    }

    @Step("Validate component for hostnames mandatory fields are not empty")
    public GetTrackersForHostNamesAssert mandatoryFieldsAreNotEmpty() {
        List<TrackersForHostNames.TrackerBindHost> trackers = trackersForHostNames().getValue();

        for (TrackersForHostNames.TrackerBindHost tracker: trackers
             ) {
            assertions().assertThat(tracker.getHost().getId())
                    .describedAs("bind Host Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(tracker.getValue())
                    .describedAs("Value is not empty")
                    .isNotEmpty();
        }
        return this;
    }
    @Step("Validate that the response is the Last Page")
    public GetTrackersForHostNamesAssert assertIsLastPage(Integer top) {
        List<TrackersForHostNames.TrackerBindHost> trackers = trackersForHostNames().getValue();
        assertions().assertThat(trackersForHostNames().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(trackers.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }

    @Step("Validate trackers date fields are in the right format")
    public GetTrackersForHostNamesAssert areDatesFormattedCorrectly() {
        List<TrackersForHostNames.TrackerBindHost> trackers = trackersForHostNames().getValue();
        for (TrackersForHostNames.TrackerBindHost tracker: trackers
             ) {
            this.assertBodyFieldValueMatchWith(tracker.getFirstSeenDateTime(), RFC3339);
            this.assertBodyFieldValueMatchWith(tracker.getLastSeenDateTime(), RFC3339);
        }
        return this;
    }


}
