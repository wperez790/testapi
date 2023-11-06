package defender.ti.api.paths;

public class Paths {
    public static String REPUTATION_FOR_HOST = "/hosts('{hostName}')/reputation";
    public static String HOST_BY_HOSTNAME = "/hosts('{hostName}')";
    public static String COMPONENTS_FOR_HOST = "/hosts('{hostName}')/components";
    public static String TRACKERS_FOR_HOST = "/hosts('{hostName}')/trackers";
    public static String COOKIES_FOR_HOST = "/hosts('{hostName}')/cookies";
    // URL OF Functions
    public static String COMPONENTS_FOR_HOSTNAMES = "/hostComponents/microsoft.graph.security.getComponentsForHostnames(name='{hostName}',version=null,category=null)";
    public static String COMPONENTS_FOR_IP_ADDRESSES = "/hostComponents/microsoft.graph.security.getComponentsForIPAddresses(name='{ipAddress}',version=null,category=null)";
    public static String TRACKERS_FOR_HOSTNAMES = "/hostTrackers/microsoft.graph.security.getTrackersForHostnames(value='{hostName}',kind=null)";
    public static String TRACKERS_FOR_IP_ADDRESSES = "/hostTrackers/microsoft.graph.security.getTrackersForIPAddresses(value='{hostName}',kind=null)";
    public static String COOKIES_FOR_HOSTNAMES = "/hostCookies/microsoft.graph.security.getCookiesForHostnames(name='{hostName}',domain=null)";
    public static String COOKIES_FOR_IP_ADDRESSES = "/hostCookies/microsoft.graph.security.getCookiesForIPAddresses(name='{ipAddress}',domain=null)";
    public static String INTEL_PROFILES = "/intelProfiles";

    public static String INTEL_PROFILES_NAME = "/intelProfiles('{name}')";
    public static String INTEL_PROFILES_NAME_INDICATORS = "/intelProfiles('{name}')/indicators";
    public static String INTEL_PROFILE_INDICATORS_UUID = "/intelligenceProfileIndicators('{uuid}')";

    // Passive DNS
    public static String PASSIVE_DNS_FOR_HOST = "/hosts('{hostName}')/passiveDns";
    public static String PASSIVE_DNS_REVERSE_FOR_HOST = "/hosts('{hostName}')/passiveDnsReverse";
    public static String PASSIVE_DNS_RECORDS = "/passiveDnsRecords('{uuid}')";


    // Vulnerabilities
    public static String VULNERABILITIES = "/vulnerabilities/{name}";
    public static String VULNERABILITIES_ARTICLES = "/vulnerabilities/{name}/articles";
    public static String VULNERABILITIES_COMPONENTS = "/vulnerabilities/{name}/components";

    // Articles
    public static String ARTICLES = "/articles";
    public static String ARTICLES_ID = "/articles('{id}')";
    public static String ARTICLES_INDICATORS = "/articles('{id}')/indicators";
    public static String ARTICLES_INDICATORS_ID = "/articleIndicators('{name}')";

}
