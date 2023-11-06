package defender.ti.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class ResponseBodyImpl {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Error {
        public @Getter Body error;

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {
            public @Getter String code, message, target, innererror;
            public @Getter List<Details> details;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Details {
            public @Getter String code, message, target, details;
        }
    }


        @ToString
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Reputation {
            @Getter
            String classification;
            @Getter @Setter
            Integer score;
            @Getter
            Rule[] rules;

            @Data
            public static class Rule {
                @Getter
                public String name, description, severity, link;
            }

        }

        @ToString
        @SuperBuilder
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Host {
            @Getter
            String id, firstSeenDateTime, lastSeenDateTime, reputation, registrar, registrant;


            @Data
            public static class IpAddress extends Host {
                @Getter
                public String countryOrRegion, netblock, hostingProvider;
                @Getter
                public AutonomousSystem autonomousSystem;
                @Data
                public static class AutonomousSystem{
                    @Getter
                    public String name, organization, value;
                    @Getter
                    public Integer number;
                }
            }
        }


    @ToString
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ComponentWrapper {
        @Getter
        @JsonProperty("@odata.context")
        private String context;
        @Getter
        List<Component> value;
        @Getter
        @JsonProperty("@odata.count")
        Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;

        @Data
        public static class Component {
            @Getter
            String id, firstSeenDateTime, lastSeenDateTime, name, version, category;
            @Getter
            Host host;

        }
    }

    @ToString
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ComponentsForHostNames {
        @Getter
        List<ComponentsForHostNames.ComponentBindHost> value;
        @Getter
        @JsonProperty("@odata.count")
        Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;

        @Data
        public static class ComponentBindHost extends ComponentWrapper.Component {
            @Getter
            Host host;

        }
    }

    // Trackers
    @ToString
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrackersWrapper {
        @Getter
        Tracker[] value;

        @Data
        public static class Tracker {
            @Getter
            String id, firstSeenDateTime, lastSeenDateTime, kind, value;
            @Getter
            Host host;

        }
    }

    @ToString
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TrackersForHostNames {
        @Getter
        List<TrackersForHostNames.TrackerBindHost> value;
        @Getter
        @JsonProperty("@odata.count")
        Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;

        @Data
        public static class TrackerBindHost extends TrackersWrapper.Tracker {
            @Getter
            Host host;
        }
    }

        @ToString
        public static class Workspaces {

            @Getter
            List<Reputation> value;
        }

    @ToString
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CookiesWrapper {
        @Getter
        Cookies[] value;

        @Data
        public static class Cookies {
            @Getter
            String id, firstSeenDateTime, lastSeenDateTime, domain, name;
            @Getter
            Host host;

        }
    }

    @ToString
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CookiesForHostNames {
        @Getter
        List<CookiesForHostNames.CookiesBindHost> value;
        @Getter
        @JsonProperty("@odata.count")
        Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;

        @Data
        public static class CookiesBindHost extends CookiesWrapper.Cookies {
            @Getter
            Host host;

        }
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IntelProfileWrapper {
        List<IntelProfile> value;
        @JsonProperty("@odata.nextLink")
        private String nextLink;
        @JsonProperty("@odata.count")
        private Integer count;
        @Data
        public static class IntelProfile {
            @Getter
            @JsonProperty("@odata.context")
            private String context;
            @Getter
            private String id, kind , title, firstActiveDateTime, classification, score;
            @Getter
            private ContentFormat
                    description, tradecraft,
                    summary;
            @Getter
            private String[] aliases, targets;
            @Getter
            private IntelligenceProfileSponsorState[] sponsorStates;
            @Getter
            private List<CountryOrRegion> countriesOrRegionsOfOrigin ;
            @Getter
            @Data
            @SuperBuilder
            @AllArgsConstructor
            @NoArgsConstructor
            private static class CountryOrRegion {
                @Getter
                String label;
                @Getter
                String code;
            }
        }
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IntelProfileIndicatorsWrapper {
        List<IntelProfileIndicators> value;
        @Getter
        @JsonProperty("@odata.count")
        private Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;

        @Data
        public static class IntelProfileIndicators {
            @Getter
            @JsonProperty("@odata.context")
            private String context;
            @Getter
            private String id, source , firstSeenDateTime, lastSeenDateTime;
            @Getter
            private Artifact artifact;
            @Getter
            @JsonProperty("@odata.nextLink")
            private String nextLink;
        }
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PassiveDnsRecordWrapper {
        List<PassiveDnsRecord> value;
        @Getter
        @JsonProperty("@odata.count")
        private Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;

        @Data
        public static class PassiveDnsRecord {
            @Getter
            @JsonProperty("@odata.context")
            private String context;
            @Getter
            private String id, collectedDateTime , firstSeenDateTime, lastSeenDateTime, recordType;
            @Getter
            private Host parentHost;
            @Getter
            private Artifact artifact;
        }
    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentFormat {
        @Getter
        String content, format;
    }
    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Artifact {
        @Getter
        @JsonProperty("@odata.type")
        private String type;
        @Getter
        String id, kind, value;
    }


    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class IntelligenceProfileSponsorState {
        @Getter
        private String label;
        @Getter
        private String code;

    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Vulnerability {
        @Getter
        @JsonProperty("@odata.context")
        private String type;
        @Getter
        String id, createdDateTime, lastModifiedDateTime, publishedDateTime;
        @Getter
        ContentFormat description, remediation;
        @Getter
        Integer priorityScore;
        @Getter
        String severity;
        @Getter
        String [] commonWeaknessEnumerationIds;
        @Getter
        CvssSummary cvss2Summary, cvss3Summary;
        @Getter
        Boolean hasChatter, exploitsAvailable, activeExploitsObserved;
        @Getter
        Hyperlink[] exploits, references;

    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CvssSummary {
        @Getter
        private Integer score;
        @Getter
        private String severity;
        @Getter
        private String vectorString;

    }

    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Hyperlink {
        @Getter
        private String name, url;
    }


    @ToString
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArticleWrapper {
        @Getter
        @JsonProperty("@odata.context")
        private String context;
        @Getter
        @JsonProperty("@odata.count")
        private Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;

        @Getter
        List<Article> value;

        @Data
        public static class Article {
            @Getter
            @JsonProperty("@odata.id")
            String odataid;
            @Getter
            String id, createdDateTime, lastUpdatedDateTime, title, imageUrl;
            @Getter
            String [] tags;
            @Getter
            ContentFormat summary, body;
            @Getter
            Boolean isFeatured;

        }
    }



    @Data
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ArticleIndicatorsWrapper {
        @Getter
        @JsonProperty("@odata.context")
        private String context;
        @Getter
        @JsonProperty("@odata.count")
        Integer count;
        @Getter
        @JsonProperty("@odata.nextLink")
        String nextLink;
        List<ArticleIndicators> value;

        @Data
        public static class ArticleIndicators {
            @Getter
            private String id, source;
            @Getter
            private Artifact artifact;
        }
    }

}