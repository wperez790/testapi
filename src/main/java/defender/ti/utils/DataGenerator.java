package defender.ti.utils;

import com.google.gson.Gson;
import defender.ti.model.ResponseBodyImpl;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.IntStream;



public class DataGenerator {

    public static String hostNameGenerator(String domainOrIp) {
        if(Utils.isIp(domainOrIp)){
            return domainOrIp;
        }
        return String.format("%s.com", domainOrIp);
    }
    public static ResponseBodyImpl.Reputation.Rule[] generateRules() {
        String json = "[\n" +
                "        {\n" +
                "            \"name\": \"SSL certificate issuer\",\n" +
                "            \"description\": \"Certificates by this issuer are frequently associated with suspicious behavior\",\n" +
                "            \"severity\": \"unknown\",\n" +
                "            \"link\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"ASN\",\n" +
                "            \"description\": \"Infrastructure hosted by this ASN frequently exhibits suspicious behavior\",\n" +
                "            \"severity\": \"unknown\",\n" +
                "            \"link\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"SSL certificate self-signed\",\n" +
                "            \"description\": \"Self-signed certificates lack verified ownership and may not be trustworthy\",\n" +
                "            \"severity\": \"low\",\n" +
                "            \"link\": null\n" +
                "        }\n" +
                "    ]";
        ResponseBodyImpl.Reputation.Rule[] data = new Gson().fromJson(json, ResponseBodyImpl.Reputation.Rule[].class);
        return data;
    }

    public static String generateNewToken() {
        final SecureRandom secureRandom = new SecureRandom();
        final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    public static Map<String, String> tagsGenerator() {
        Map<String, String> tags = new HashMap<String, String>();
        IntStream.range(1, new SplittableRandom().nextInt(2, 10))
                .forEach(i -> tags.put("Tag " + i, "This tag was created by an automation with testing purpose"));
        return tags;
    }
}
