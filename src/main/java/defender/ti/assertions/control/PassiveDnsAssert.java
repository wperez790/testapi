package defender.ti.assertions.control;

import defender.ti.assertions.GenericAssertions;
import defender.ti.model.ResponseBodyImpl.PassiveDnsRecordWrapper;
import defender.ti.model.ResponseBodyImpl.PassiveDnsRecordWrapper.PassiveDnsRecord;
import defender.ti.utils.Constants;
import defender.ti.utils.Utils;
import net.thucydides.core.annotations.Step;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PassiveDnsAssert extends GenericAssertions {

    InheritableThreadLocal<PassiveDnsRecordWrapper> passiveDnsRecordsWrapper = new InheritableThreadLocal<>();
    InheritableThreadLocal<PassiveDnsRecord> passiveDnsRecord = new InheritableThreadLocal<>();

    private PassiveDnsRecordWrapper passiveDnsRecordsWrapper() {
        return passiveDnsRecordsWrapper.get();
    }
    private PassiveDnsRecord passiveDnsRecord() {
        return passiveDnsRecord.get();
    }

    public PassiveDnsAssert assertThat(PassiveDnsRecordWrapper passiveDnsRecordWrapper) {
        this.passiveDnsRecordsWrapper.set(passiveDnsRecordWrapper);
        return this;
    }
    public PassiveDnsAssert assertThat(PassiveDnsRecord passiveDnsRecord) {
        this.passiveDnsRecord.set(passiveDnsRecord);
        return this;
    }

    @Step("Validate PassiveDnsRecord is not empty")
    public PassiveDnsAssert passiveDnsRecordIsNotEmpty() {
        assertions().assertThat(passiveDnsRecordsWrapper().getValue())
                .describedAs("Value of passive DNS record is not empty")
                .isNotEmpty();
        return this;
    }

    @Step("Validate List of Passive DNS Records mandatory fields are not empty")
    public PassiveDnsAssert mandatoryFieldsFromListAreNotEmpty() {
        List<PassiveDnsRecord> PassiveDnsRecords = passiveDnsRecordsWrapper().getValue();

        for (PassiveDnsRecord passiveDnsRecord: PassiveDnsRecords
        ) {
            assertions().assertThat(passiveDnsRecord.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getParentHost())
                    .describedAs("Parent Host is not null")
                    .isNotNull();
            assertions().assertThat(passiveDnsRecord.getRecordType())
                    .describedAs("Record Type is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getFirstSeenDateTime())
                    .describedAs("First Seen Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getLastSeenDateTime())
                    .describedAs("Last Seen Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getCollectedDateTime())
                    .describedAs("Collected Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getArtifact())
                    .describedAs("Artifact content is not empty")
                    .isNotNull();
        }
        return this;
    }

    @Step("Validate Passive DNS Records mandatory fields are not empty")
    public PassiveDnsAssert mandatoryFieldsAreNotEmpty() {
        PassiveDnsRecord passiveDnsRecord = passiveDnsRecord();
            assertions().assertThat(passiveDnsRecord.getId())
                    .describedAs("Id is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getParentHost())
                    .describedAs("Parent Host is not null")
                    .isNotNull();
            assertions().assertThat(passiveDnsRecord.getRecordType())
                    .describedAs("Record Type is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getFirstSeenDateTime())
                    .describedAs("First Seen Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getLastSeenDateTime())
                    .describedAs("Last Seen Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getCollectedDateTime())
                    .describedAs("Collected Date Time is not empty")
                    .isNotEmpty();
            assertions().assertThat(passiveDnsRecord.getArtifact())
                    .describedAs("Artifact content is not empty")
                    .isNotNull();
        return this;
    }


    @Step("Validate PassiveDnsRecord order by firstSeenDateTime is working properly")
    public PassiveDnsAssert orderByFirstSeenDateIsWorking(Constants.ORDER_CLASSIFICATIONS order) throws ParseException {
        List<PassiveDnsRecord> PassiveDnsRecords = passiveDnsRecordsWrapper().getValue();
        Date firstActiveDateTime = Utils.stringToDate(PassiveDnsRecords.get(0).getFirstSeenDateTime());
        if(order.equals(Constants.ORDER_CLASSIFICATIONS.asc)){
            for(int i = 1; i < PassiveDnsRecords.size(); i++){
                Date date = Utils.stringToDate(PassiveDnsRecords.get(i).getFirstSeenDateTime());
                assertions().assertThat(date)
                        .describedAs("First seen date time is order as ascending")
                        .isAfterOrEqualTo(firstActiveDateTime);
                firstActiveDateTime = date;
            }
        } else {
            for(int i = 1; i < PassiveDnsRecords.size(); i++){
                Date date = Utils.stringToDate(PassiveDnsRecords.get(i).getFirstSeenDateTime());
                assertions().assertThat(date)
                        .describedAs("Value of intel profile is not empty")
                        .isBeforeOrEqualTo(firstActiveDateTime);
                firstActiveDateTime = date;
            }
        }
        return this;
    }


    @Step("Validate Passive Dns filter is working properly")
    public PassiveDnsAssert filterIsWorkingProperly(String filter) {
        List<PassiveDnsRecord> passiveDnsRecord = passiveDnsRecordsWrapper().getValue();
        for(int i = 0; i < passiveDnsRecord.size(); i++){
            String kind = passiveDnsRecord.get(i).getRecordType();
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(filter);

            if (matcher.find()) {
                String filterKind = matcher.group(1);
                assertions().assertThat(kind)
                        .describedAs("Record Type is equal to the one is filtered")
                        .isEqualTo(filterKind);
            }
        }
        return this;
    }


    public void topIsWorkingProperly(int top) {
        assertions().assertThat(passiveDnsRecordsWrapper().getValue().size()).isEqualTo(top);
    }

    public void skipIsWorkingProperly(PassiveDnsRecordWrapper wrapperListWithoutSkipping, PassiveDnsRecordWrapper wrapperList, int skip) {
        List<PassiveDnsRecord> listWithoutSkipping = wrapperListWithoutSkipping.getValue();
        List<PassiveDnsRecord> list = listWithoutSkipping.subList(skip, listWithoutSkipping.size());
            assertions().assertThat(list).describedAs("Skip works properly").isEqualTo(wrapperList.getValue());
    }

    @Step("Validate that the response is the Last Page")
    public PassiveDnsAssert assertIsLastPage(Integer top) {
        List<PassiveDnsRecordWrapper.PassiveDnsRecord> intelProfiles = passiveDnsRecordsWrapper().getValue();
        assertions().assertThat(passiveDnsRecordsWrapper().getNextLink())
                .describedAs("Next Link is empty")
                .isNull();
        assertions().assertThat(intelProfiles.size())
                .describedAs("The values of the response are less than the pagination top")
                .isLessThanOrEqualTo(top);

        return this;
    }
}