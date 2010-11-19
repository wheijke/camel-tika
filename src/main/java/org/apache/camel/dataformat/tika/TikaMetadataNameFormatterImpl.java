package org.apache.camel.dataformat.tika;

/**
 * Apache Tika Metadata name formatter.
 * Capitalizes a Metadata name and removes punctuation characters
 * 
 * @author Wouter Heijke
 */
public class TikaMetadataNameFormatterImpl implements
        TikaMetadataNameFormatter {

    private static String PREFIX = "Tika";

    public String convert(String key) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(PREFIX);

        if (key != null) {
            String[] words = key.split("\\p{Punct}");
            for (String s : words) {
                sb.append(capitalize(s));
            }
        }
        return sb.toString();
    }

    private String capitalize(String s) {
        if (s.length() == 0)
            return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
