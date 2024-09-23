package web;

public class JspTools {
    public static String escapeHtml(String s) {
        return org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(s)
                .replaceAll("\\r\\n", "\n")
                .replaceAll("\\r", "\n")
                .replaceAll("\\n", "<br />");
    }
}
