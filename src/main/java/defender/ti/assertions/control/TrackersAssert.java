package defender.ti.assertions.control;


import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.TrackersWrapper;
import net.thucydides.core.annotations.Step;


public class TrackersAssert extends GenericAssertions {

    InheritableThreadLocal<TrackersWrapper> trackers = new InheritableThreadLocal<>();

    private TrackersWrapper trackers() {
        return trackers.get();
    }


    public TrackersAssert assertThat(TrackersWrapper tracker) {
        this.trackers.set(tracker);
        return this;
    }

    @Step("Validate tracker mandatory fields are not empty")
    public TrackersAssert mandatoryFieldsAreNotEmpty() {
        TrackersWrapper.Tracker[] trackers = trackers().getValue();

        for (TrackersWrapper.Tracker tracker: trackers
             ) {
            assertions().assertThat(tracker.getFirstSeenDateTime())
                    .describedAs("id is not empty")
                    .isNotEmpty();
            assertions().assertThat(tracker.getFirstSeenDateTime())
                    .describedAs("First Seen Date is not empty")
                    .isNotEmpty();
            assertions().assertThat(tracker.getLastSeenDateTime())
                    .describedAs("Last Seen Date is not empty")
                    .isNotEmpty();
        }
        return this;
    }

    @Step("Validate tracker mandatory fields are not empty")
    public TrackersAssert areDatesFormattedCorrectly() {
        TrackersWrapper.Tracker[] trackers = trackers().getValue();
        for (TrackersWrapper.Tracker tracker: trackers
             ) {
            this.assertBodyFieldValueMatchWith(tracker.getFirstSeenDateTime(), RFC3339);
            this.assertBodyFieldValueMatchWith(tracker.getLastSeenDateTime(), RFC3339);
        }
        return this;
    }


}
