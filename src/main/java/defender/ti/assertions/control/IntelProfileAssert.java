package defender.ti.assertions.control;

import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl;
import defender.ti.model.ResponseBodyImpl.IntelProfileWrapper;
import defender.ti.model.ResponseBodyImpl.IntelProfileWrapper.IntelProfile;
import defender.ti.model.ResponseBodyImpl.IntelProfileIndicatorsWrapper;
import defender.ti.model.ResponseBodyImpl.IntelProfileIndicatorsWrapper.IntelProfileIndicators;
import defender.ti.model.ResponseBodyImpl.IntelligenceProfileSponsorState;
import defender.ti.utils.Constants;
import defender.ti.utils.Utils;
import net.thucydides.core.annotations.Step;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntelProfileAssert extends GenericAssertions {

    InheritableThreadLocal<IntelProfileWrapper> intelProfileWrapper = new InheritableThreadLocal<>();
    InheritableThreadLocal<IntelProfile> intelProfile = new InheritableThreadLocal<>();
    InheritableThreadLocal<IntelProfileIndicatorsWrapper> intelProfileIndicatorsWrapper = new InheritableThreadLocal<>();
    InheritableThreadLocal<IntelProfileIndicators> intelProfileIndicators = new InheritableThreadLocal<>();

    private IntelProfileWrapper intelProfileWrapper() {
        return intelProfileWrapper.get();
    }
    private IntelProfile intelProfile() {
        return intelProfile.get();
    }
    private IntelProfileIndicatorsWrapper intelProfileIndicatorsWrapper() {
        return intelProfileIndicatorsWrapper.get();
    }
    private IntelProfileIndicators intelProfileIndicators() {
        return intelProfileIndicators.get();
    }

    public IntelProfileAssert assertThat(IntelProfileWrapper intelProfile) {
        this.intelProfileWrapper.set(intelProfile);
        return this;
    }
    public IntelProfileAssert assertThat(IntelProfile intelProfile) {
        this.intelProfile.set(intelProfile);
        return this;
    }
    public IntelProfileAssert assertThat(IntelProfileIndicatorsWrapper intelProfileIndicatorsWrapper) {
        this.intelProfileIndicatorsWrapper.set(intelProfileIndicatorsWrapper);
        return this;
    }
    public IntelProfileAssert assertThat(IntelProfileIndicators intelProfile) {
        this.intelProfileIndicators.set(intelProfile);
        return this;
    }

    @Step("Validate IntelProfile is not empty")
    public IntelProfileAssert intelProfileIsNotEmpty() {
        assertions().assertThat(intelProfileWrapper().getValue())
                .describedAs("Value of intel profile is not empty")
                .isNotEmpty();
        return this;
    }

    @Step("Validate IntelProfile order by title is working properly")
    public IntelProfileAssert orderByTitleIsWorking(Constants.ORDER_CLASSIFICATIONS order) {
        List<IntelProfile> intelProfiles = intelProfileWrapper().getValue();
        String firstTitle = intelProfiles.get(0).getTitle();
        if(order.equals(Constants.ORDER_CLASSIFICATIONS.asc)){
            for(int i = 1; i < intelProfiles.size(); i++){
                String title = intelProfiles.get(i).getTitle();
                assertions().assertThat(title)
                        .describedAs("Value of intel profile is not empty")
                        .isGreaterThan(firstTitle);
                firstTitle = title;
            }
        } else {
            for(int i = 1; i < intelProfiles.size(); i++){
                String title = intelProfiles.get(i).getTitle();
                assertions().assertThat(title)
                        .describedAs("Value of intel profile is not empty")
                        .isLessThan(firstTitle);
                firstTitle = title;
            }
        }
        return this;
    }

    @Step("Validate List of Intelligence Profile mandatory fields are not empty")
    public IntelProfileAssert mandatoryFieldsFromListAreNotEmpty() {
        List<IntelProfile> intelProfiles = intelProfileWrapper().getValue();

        for (IntelProfile intelligenceProfile: intelProfiles
        ) {
            assertions().assertThat(intelligenceProfile.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getKind())
                    .describedAs("Kind is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getTitle())
                    .describedAs("Title is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getFirstActiveDateTime())
                    .describedAs("First Active Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getSummary().getContent())
                    .describedAs("Summary content is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getSummary().getFormat())
                    .describedAs("Summary format is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getDescription().getContent())
                    .describedAs("Description content is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getDescription().getFormat())
                    .describedAs("Description format is not empty")
                    .isNotEmpty();
            IntelligenceProfileSponsorState[] sponsorStates = intelligenceProfile.getSponsorStates();
            for(IntelligenceProfileSponsorState sponsorState : sponsorStates){
                assertions().assertThat(sponsorState.getCode()).describedAs("Sponsor state Code is not empty");
                assertions().assertThat(sponsorState.getLabel()).describedAs("Sponsor state Label is not empty");
            }
        }
        return this;
    }

    @Step("Validate List of Intelligence Profile mandatory fields are not empty")
    public IntelProfileAssert mandatoryFieldsFromListIndicatorsAreNotEmpty() {
        List<IntelProfileIndicators> intelProfiles = intelProfileIndicatorsWrapper().getValue();

        for (IntelProfileIndicators intelligenceProfile: intelProfiles
        ) {
            assertions().assertThat(intelligenceProfile.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getSource())
                    .describedAs("Source is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelligenceProfile.getArtifact())
                    .describedAs("Artifact is not empty")
                    .isNotNull();
        }
        return this;
    }

    @Step("Validate Intelligence Profile mandatory fields are not empty")
    public IntelProfileAssert mandatoryFieldsAreNotEmpty() {
        IntelProfile intelProfile = intelProfile();
            assertions().assertThat(intelProfile.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getKind())
                    .describedAs("Kind is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getTitle())
                    .describedAs("Title is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getFirstActiveDateTime())
                    .describedAs("First Active Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getSummary().getContent())
                    .describedAs("Summary content is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getSummary().getFormat())
                    .describedAs("Summary format is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getDescription().getContent())
                    .describedAs("Description content is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getDescription().getFormat())
                    .describedAs("Description format is not empty")
                    .isNotEmpty();
            IntelligenceProfileSponsorState[] sponsorStates = intelProfile.getSponsorStates();
            for(IntelligenceProfileSponsorState sponsorState : sponsorStates){
                assertions().assertThat(sponsorState.getCode()).describedAs("Sponsor state Code is not empty");
                assertions().assertThat(sponsorState.getLabel()).describedAs("Sponsor state Label is not empty");
            }
        return this;
    }

    @Step("Validate IntelProfile order by title is working properly")
    public IntelProfileAssert filterIsWorkingProperly(String filter) {
        List<IntelProfile> intelProfiles = intelProfileWrapper().getValue();
        for(int i = 0; i < intelProfiles.size(); i++){
            String kind = intelProfiles.get(i).getKind();
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(filter);

            if (matcher.find()) {
                String filterKind = matcher.group(1);
            assertions().assertThat(kind)
                    .describedAs("Kind is equal to the one is filtered")
                    .isEqualTo(filterKind);
            }
        }
        return this;
    }

    @Step("Validate IntelProfile order by firstSeenDateTime is working properly")
    public IntelProfileAssert orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS order) throws ParseException {
        List<IntelProfileIndicators> intelProfiles = intelProfileIndicatorsWrapper().getValue();
        if(intelProfiles.size() > 0) {
            Date firstActiveDateTime = Utils.stringToDate(intelProfiles.get(0).getFirstSeenDateTime());
            if (order.equals(Constants.ORDER_CLASSIFICATIONS.asc)) {
                for (int i = 1; i < intelProfiles.size(); i++) {
                    Date date = Utils.stringToDate(intelProfiles.get(i).getFirstSeenDateTime());
                    assertions().assertThat(date)
                            .describedAs("First seen date time is order as ascending")
                            .isAfterOrEqualTo(firstActiveDateTime);
                    firstActiveDateTime = date;
                }
            } else {
                for (int i = 1; i < intelProfiles.size(); i++) {
                    Date date = Utils.stringToDate(intelProfiles.get(i).getFirstSeenDateTime());
                    assertions().assertThat(date)
                            .describedAs("Value of intel profile is not empty")
                            .isBeforeOrEqualTo(firstActiveDateTime);
                    firstActiveDateTime = date;
                }
            }
        }
        return this;
    }
    @Step("Validate List of Intelligence Profile mandatory fields are not empty")
    public IntelProfileAssert mandatoryFieldsFromIndicatorsAreNotEmpty() {
        IntelProfileIndicators intelProfile = intelProfileIndicators();

            assertions().assertThat(intelProfile.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(intelProfile.getSource())
                    .describedAs("Source is not empty")
                    .isNotEmpty();
        return this;
    }


    public void selectIdIsWorkingProperly() {
        List<IntelProfile> intelProfiles  = intelProfileWrapper().getValue();
            for (ResponseBodyImpl.IntelProfileWrapper.IntelProfile intelProfile : intelProfiles){
                // Checking select is bringing the fields that you select
                assertions().assertThat(intelProfile.getId()).isNotNull();
                // Checking select is NOT bringing the other fields
                assertions().assertThat(intelProfile.getKind()).isNull();
                assertions().assertThat(intelProfile.getTitle()).isNull();
                assertions().assertThat(intelProfile.getFirstActiveDateTime()).isNull();
                assertions().assertThat(intelProfile.getAliases()).isNull();
                assertions().assertThat(intelProfile.getTargets()).isNull();
                assertions().assertThat(intelProfile.getSponsorStates()).isNull();
                assertions().assertThat(intelProfile.getSummary()).isNull();
                assertions().assertThat(intelProfile.getDescription()).isNull();
                assertions().assertThat(intelProfile.getTradecraft()).isNull();
            }
    }
    public IntelProfileAssert selectIdIsWorkingProperlyOnIntelProfile() {
        ResponseBodyImpl.IntelProfileWrapper.IntelProfile intelProfile  = intelProfile();
                // Checking select is bringing the fields that you select
                assertions().assertThat(intelProfile.getId()).isNotNull();
                // Checking select is NOT bringing the other fields
                assertions().assertThat(intelProfile.getKind()).isNull();
                assertions().assertThat(intelProfile.getTitle()).isNull();
                assertions().assertThat(intelProfile.getFirstActiveDateTime()).isNull();
                assertions().assertThat(intelProfile.getAliases()).isNull();
                assertions().assertThat(intelProfile.getTargets()).isNull();
                assertions().assertThat(intelProfile.getSponsorStates()).isNull();
                assertions().assertThat(intelProfile.getSummary()).isNull();
                assertions().assertThat(intelProfile.getDescription()).isNull();
                assertions().assertThat(intelProfile.getTradecraft()).isNull();
            return this;
    }

    public IntelProfileAssert selectIdIsWorkingProperlyOnIndicators() {
        List<IntelProfileIndicators> intelProfiles  = intelProfileIndicatorsWrapper().getValue();
        for (IntelProfileIndicatorsWrapper.IntelProfileIndicators intelProfile : intelProfiles){
            // Checking select is bringing the fields that you select
            assertions().assertThat(intelProfile.getId()).isNotNull();
            // Checking select is NOT bringing the other fields
            assertions().assertThat(intelProfile.getLastSeenDateTime()).isNull();
            assertions().assertThat(intelProfile.getArtifact()).isNull();
            assertions().assertThat(intelProfile.getSource()).isNull();
            assertions().assertThat(intelProfile.getFirstSeenDateTime()).isNull();
            assertions().assertThat(intelProfile.getContext()).isNull();
        }
        return this;
    }

    public IntelProfileAssert searchIsWorkingProperly(String search) {
        String actualContent = intelProfileWrapper().getValue().get(0).getSummary().getContent()
                + intelProfileWrapper().getValue().get(0).getDescription().getContent()
                + intelProfileWrapper().getValue().get(0).getTradecraft().getContent();
            assertions().assertThat(actualContent).as("The summary, description or tradecraft content don't contain the word '%s'", search).containsIgnoringCase(search);

            return this;
    }

    @Step("Validate that the response is the Last Page")
    public IntelProfileAssert assertIsLastPage(Integer top) {
        List<IntelProfile> intelProfiles = intelProfileWrapper().getValue();
        assertions().assertThat(intelProfileWrapper().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(intelProfiles.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }

    @Step("Validate that the response is the Last Page")
    public IntelProfileAssert assertIsLastPageOnIndicators(Integer top) {
        List<IntelProfileIndicators> intelProfiles = intelProfileIndicatorsWrapper().getValue();
        assertions().assertThat(intelProfileIndicatorsWrapper().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(intelProfiles.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }


}