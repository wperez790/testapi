package defender.ti.utils;

import lombok.Getter;

public class Constants {
    public static final String CVE_VALUE = "CVE-2021-44228";
    public static final String ARTICLE_ID = "988834b9";
    public static String[] REPUTATION_CLASSIFICATIONS_LIST =  {
            REPUTATION_CLASSIFICATIONS.UNKNOWN.classification,
            REPUTATION_CLASSIFICATIONS.NEUTRAL.classification,
            REPUTATION_CLASSIFICATIONS.SUSPICIOUS.classification,
            REPUTATION_CLASSIFICATIONS.MALICIOUS.classification
    };

    @Getter
    public enum REPUTATION_CLASSIFICATIONS {
        UNKNOWN("unknown"),
        NEUTRAL("neutral"),
        SUSPICIOUS("suspicious"),
        MALICIOUS("malicious");

    private final @Getter String classification;

    REPUTATION_CLASSIFICATIONS(String classification) {
        this.classification = classification;
    }
    }

    @Getter
    public enum ORDER_CLASSIFICATIONS {
        asc("asc"),
        desc("desc");
        private final @Getter String order;

        ORDER_CLASSIFICATIONS(String order) {
            this.order = order;
        }
    }

    public static String KIND_EQ_ACTOR= "kind%20eq%20microsoft.graph.security.intelligenceProfileKind'actor'";
    public static String KIND_EQ_TOOL= "kind%20eq%20microsoft.graph.security.intelligenceProfileKind'tool'";
    public static String KIND_EQ_BACKDOOR= "kind%20eq%20microsoft.graph.security.intelligenceProfileKind'backdoor'";
    public static String RECORD_TYPE_EQ_A= "recordType%20eq%20'A'";
    public static String RECORD_TYPE_EQ_CNAME= "recordType%20eq%20'CNAME'";
}
