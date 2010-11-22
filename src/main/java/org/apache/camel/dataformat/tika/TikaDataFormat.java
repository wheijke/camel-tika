/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.dataformat.tika;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.spi.DataFormat;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;



/**
 * Apache Tika Data format.
 * Transforms message body content recognized by Tika to ASCII
 * 
 * @author Wouter Heijke
 */
public class TikaDataFormat implements DataFormat {

    private Tika tika = new Tika();

    private Metadata metadata = new Metadata();

    private TikaMetadataNameFormatter nameFormatter = new TikaMetadataNameFormatterImpl();

    public void marshal(Exchange exchange, Object graph, OutputStream stream)
            throws Exception {
        throw new RuntimeCamelException("Marshalling not supported");
    }

    public Object unmarshal(Exchange exchange, InputStream stream)
            throws Exception {

        String text = tika.parseToString(stream, metadata);

        Message out = exchange.getOut();

        String[] keys = metadata.names();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            String outKey = nameFormatter.convert(key);
            if (outKey != null) {
                if (metadata.isMultiValued(key)) {
                    String[] values = metadata.getValues(key);
                    for (int v = 0; v < values.length; v++) {
                        String val = values[i];
                        out.setHeader(outKey, val);
                    }
                } else {
                    out.setHeader(outKey, metadata.get(key));
                }
            }
        }

        return text;
    }

    public void setTikaMetadataNameFormatter(
            TikaMetadataNameFormatter tikaMetadataNameFormatter) {
        this.nameFormatter = tikaMetadataNameFormatter;
    }

}
