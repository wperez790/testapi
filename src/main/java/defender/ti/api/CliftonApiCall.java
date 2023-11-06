package defender.ti.api;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;

import static defender.ti.api.paths.Paths.*;

public class CliftonApiCall extends BaseApiCall {

    private static RequestSpecification requestSpec;

    public CliftonApiCall(String endpoint, String token) {
        super(endpoint, token);
        requestSpec = setGenericRequestSpec();
    }


    // Reputation for a host

    public static Response getReputationForAHost(String hostName) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .basePath(REPUTATION_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getReputationForAHost(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(REPUTATION_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }




    // Host by hostname

    public static Response getHostByHostname(String hostName) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .basePath(HOST_BY_HOSTNAME)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getHostByHostname(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(HOST_BY_HOSTNAME)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }


    // Components for a host

    public static Response getComponentsForAHost(String hostName) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .basePath(COMPONENTS_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getComponentsForAHost(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COMPONENTS_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getComponentsForHostNames(String hostName) {
        return SerenityRest.given()
                .spec(requestSpec)
                .basePath(COMPONENTS_FOR_HOSTNAMES)
                .queryParam("$count", true)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getComponentsForHostNames(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COMPONENTS_FOR_HOSTNAMES)
                .pathParam("hostName", hostName)
                .queryParam("$count", true)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getComponentsForHostNames(String hostName, Integer top, Integer skip, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COMPONENTS_FOR_HOSTNAMES)
                .pathParam("hostName", hostName)
                .queryParam("$count", true)
                .queryParam("$top", top)
                .queryParam("$skip", skip)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getComponentsForIpAddresses(String ipAddress, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COMPONENTS_FOR_IP_ADDRESSES)
                .queryParam("$count", true)
                .pathParam("ipAddress", ipAddress)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getComponentsForIpAddresses(String ipAddress, Integer top, Integer skip, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COMPONENTS_FOR_IP_ADDRESSES)
                .queryParam("$count", true)
                .queryParam("$top", top)
                .queryParam("$skip", skip)
                .pathParam("ipAddress", ipAddress)
                .when()
                .get()
                .then()
                .extract().response();
    }


    // Trackers for a host

    public static Response getTrackersForAHost(String hostName) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .basePath(TRACKERS_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getTrackersForAHost(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(TRACKERS_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }


    // Trackers for hostnames

    public static Response getTrackersForHostNames(String hostName) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .basePath(TRACKERS_FOR_HOSTNAMES)
                .queryParam("$count", true)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getTrackersForHostNames(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(TRACKERS_FOR_HOSTNAMES)
                .queryParam("$count", true)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getTrackersForHostNames(String hostName, Integer top, Integer skip, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(TRACKERS_FOR_HOSTNAMES)
                .queryParam("$count", true)
                .queryParam("$top", top)
                .queryParam("$skip", skip)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }
    // Trackers for ip addresses

    public static Response getTrackersForIpAddresses(String hostName) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .basePath(TRACKERS_FOR_IP_ADDRESSES)
                .queryParam("$count", true)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getTrackersForIpAddresses(String hostName, Integer top, Integer skip, String accessToken) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(TRACKERS_FOR_IP_ADDRESSES)
                .queryParam("$count", true)
                .queryParam("$top", top)
                .queryParam("$skip", skip)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getTrackersForIpAddresses(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(TRACKERS_FOR_IP_ADDRESSES)
                .queryParam("$count", true)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }

// Components for a host

    public static Response getCookiesForAHost(String hostName) {
        Response response =  SerenityRest.given()
                .spec(requestSpec)
                .basePath(COOKIES_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
        return response;
    }

    public static Response getCookiesForAHost(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COOKIES_FOR_HOST)
                .pathParam("hostName", hostName)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getCookiesForHostNames(String hostName, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COOKIES_FOR_HOSTNAMES)
                .pathParam("hostName", hostName)
                .queryParam("$count", true)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getCookiesForHostNames(String hostName, Integer top, Integer skip, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COOKIES_FOR_HOSTNAMES)
                .pathParam("hostName", hostName)
                .queryParam("$count", true)
                .queryParam("$top", top)
                .queryParam("$skip", skip)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getCookiesForIpAddresses(String ipAddress, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COOKIES_FOR_IP_ADDRESSES)
                .queryParam("$count", true)
                .pathParam("ipAddress", ipAddress)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getCookiesForIpAddresses(String ipAddress, Integer top, Integer skip, String accessToken) {
        return SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .basePath(COOKIES_FOR_IP_ADDRESSES)
                .queryParam("$count", true)
                .queryParam("$top", top)
                .queryParam("$skip", skip)
                .pathParam("ipAddress", ipAddress)
                .when()
                .get()
                .then()
                .extract().response();
    }

    // Intel Profiles
    public static Response getIntelProfiles(Boolean count, Integer top, Integer skip, String select, String search, String orderBy, String filter, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken);

        spec = getSpecification(count, select, search, orderBy, filter, top, skip, spec);

        return spec.basePath(INTEL_PROFILES)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getIntelProfiles(Boolean count, Integer top, Integer skip, String accessToken) {
        return getIntelProfiles(count, top, skip, null, null, null, null, accessToken);
    }

    public static Response getIntelProfiles(Boolean count, Integer top, Integer skip, String select, String accessToken) {
        return getIntelProfiles(count, top, skip, select, null, null, null,  accessToken);
    }

    public static Response getIntelProfiles(Boolean count, String select, String search, Integer top, Integer skip, String accessToken) {
        return getIntelProfiles(count, top, skip, select, search, null, null, accessToken);
    }

    public static Response getIntelProfiles(Boolean count, String select, String search, String orderBy, Integer top, Integer skip, String accessToken) {
        return getIntelProfiles(count, top, skip, select, search, orderBy, null, accessToken);
    }
    public static Response getIntelProfilesById(String name, Boolean count, String select, String search, String orderBy, String filter, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("name", name);

        spec = getSpecification(count, select, search, orderBy, filter, top, skip, spec);

        return spec.basePath(INTEL_PROFILES_NAME)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getIntelProfilesByNameIndicators(String name, Boolean count, String select, String search, String orderBy, String filter, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("name", name);

        spec = getSpecification(count, select, search, orderBy, filter, top, skip, spec);

        return spec.basePath(INTEL_PROFILES_NAME_INDICATORS)
                .when()
                .get()
                .then()
                .extract().response();
    }

    private static RequestSpecification getSpecification(Boolean count, String select, String search, String orderBy, String filter, Integer top, Integer skip, RequestSpecification spec) {
        if(count){
            spec = spec.queryParam("$count", count);
        }
        if(top != null){
            spec = spec.queryParam("$top", top);
        }
        if(skip != null){
            spec = spec.queryParam("$skip", skip);
        }
        if (select != null) {
            spec = spec.queryParam("$select", select);
        }

        if (search != null) {
            spec = spec.queryParam("$search", search);
        }

        if (orderBy != null) {
            spec = spec.queryParam("$orderby", orderBy);
        }

        if (filter != null) {
            spec = spec.queryParam("$filter", filter);
        }
        return spec;
    }

    public static Response getIntelProfilesIndicators(String uuid, Boolean count, String select, String search, String orderBy, String filter, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("uuid", uuid);

        spec = getSpecification(count, select, search, orderBy, filter, top, skip, spec);

        return spec.basePath(INTEL_PROFILE_INDICATORS_UUID)
                .when()
                .get()
                .then()
                .extract().response();
    }

    // Passive DNS
    public static Response getPassiveDns(String name, Boolean count, String select, String search, String orderBy, String filter, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("hostName", name);

        spec = getSpecification(count, select, search, orderBy, filter, top, skip, spec);

        return spec.basePath(PASSIVE_DNS_FOR_HOST)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getPassiveDns(String name, Boolean count, Integer top, Integer skip, String accessToken) {
        return getPassiveDns(name, count, null, null, null, null, top, skip, accessToken);
    }
    public static Response getPassiveDns(String name, Boolean count, Integer top, Integer skip, String order, String accessToken) {
        return getPassiveDns(name, count, null, null, order, null, top, skip, accessToken);
    }


    // Reverse DNS
    public static Response getPassiveDnsReverse(String name, Boolean count, String select, String search, String orderBy, String filter, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("hostName", name);

        spec = getSpecification(count, select, search, orderBy, filter, top, skip, spec);

        return spec.basePath(PASSIVE_DNS_REVERSE_FOR_HOST)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getPassiveDnsReverse(String name, Boolean count, Integer top, Integer skip, String accessToken) {
        return getPassiveDnsReverse(name, count, null, null, null, null, top, skip, accessToken);
    }
    public static Response getPassiveDnsReverse(String name, Boolean count, Integer top, Integer skip, String order, String accessToken) {
        return getPassiveDns(name, count, null, null, order, null, top, skip, accessToken);
    }
    public static Response getPassiveDnsRecords(String uuid, Boolean count, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("uuid", uuid);
        spec = getSpecification(count, null, null, null, null, top, skip, spec);

        return spec.basePath(PASSIVE_DNS_RECORDS)
                .when()
                .get()
                .then()
                .extract().response();
    }


    // Vulnerabilities

    public static Response getVulnerability(String name, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("name", name);

        return spec.basePath(VULNERABILITIES)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getVulnerabilityArticles(String name, Boolean count, Integer top, Integer skip, String select, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("name", name);
        spec = getSpecification(count, select, null, null, null, top, skip, spec);

        return spec.basePath(VULNERABILITIES_ARTICLES)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getVulnerabilityComponents(String name, Boolean count, String select, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("name", name);
        spec = getSpecification(count, select, null, null, null, top, skip, spec);

        return spec.basePath(VULNERABILITIES_COMPONENTS)
                .when()
                .get()
                .then()
                .extract().response();
    }

    // Articles
    public static Response getArticles(Boolean count, String select, String search, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken);

        spec = getSpecification(count, select, search, null, null, top, skip, spec);

        return spec.basePath(ARTICLES)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getArticleById(String id, Boolean count, String select, String search, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("id", id);

        spec = getSpecification(count, select, search, null, null, top, skip, spec);

        return spec.basePath(ARTICLES_ID)
                .when()
                .get()
                .then()
                .extract().response();
    }
    public static Response getArticleIndicators(String id, Boolean count, String select, String search, Integer top, Integer skip, String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("id", id);
        spec = getSpecification(count, select, search, null, null, top, skip, spec);

        return spec.basePath(ARTICLES_INDICATORS)
                .when()
                .get()
                .then()
                .extract().response();
    }

    public static Response getArticlesIndicatorsById(String id,  String accessToken) {
        RequestSpecification spec = SerenityRest.given()
                .spec(requestSpec)
                .auth().oauth2(accessToken)
                .pathParam("id", id);
        spec = getSpecification(null, null, null, null, null, null, null, spec);

        return spec.basePath(ARTICLES_INDICATORS_ID)
                .when()
                .get()
                .then()
                .extract().response();
    }


}
