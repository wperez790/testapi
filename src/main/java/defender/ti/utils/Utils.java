package defender.ti.utils;

import org.fluentlenium.core.filter.matcher.NotContainsMatcher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getParamValueFromUrl(String url, String param) throws Exception {
        String regex = String.format("(\\%s=\\d+)", param);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()){
            String match = matcher.group(0);
            return match.replace(param+"=", "");
        }
        else {
            throw new Exception("Does not contains param: " + param);
        }
    }

    public static boolean isIp(String ipOrDomain) {
        Pattern pattern = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(ipOrDomain);
        return matcher.find();
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat format;
        if (dateString.contains(".")) {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
        } else {
            format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        }
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw e;
        }
    }
}
