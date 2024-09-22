package common;

public class RegexTool {
    public static String toPartialRegex(String regex) {
        if (regex.isEmpty()) return ".*";
        if (!regex.startsWith("^")) regex = ".*" + regex;
        if (!regex.endsWith(("$"))) regex = regex + ".*";
        return regex;
    }
}
