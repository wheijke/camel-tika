package org.apache.camel.dataformat.tika;

/**
 * Apache Tika Metadata name formatter.
 * Formats a Metadata name into a format suitable for a Camel Message header
 * 
 * @author Wouter Heijke
 */
public interface TikaMetadataNameFormatter {

    public String convert(String key) throws Exception;

}
