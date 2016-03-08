package org.apache.camel.component.tika;

import java.io.InputStream;
import java.util.Map;

import org.apache.camel.RuntimeCamelException;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.test.junit4.TestSupport;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TikaDataFormatTest extends TestSupport {

    private static final Logger LOG = LoggerFactory.getLogger(TikaDataFormatTest.class);

    @Test(expected = RuntimeCamelException.class)
    public void marshalNotSupported() throws Exception {
        TikaDataFormat format = new TikaDataFormat();
        format.marshal(null, null, null);
    }

    @Test
    public void canLoadSimpleXml() throws Exception {
        DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
        InputStream stream = TikaDataFormatTest.class.getClassLoader().getResourceAsStream("test.xml");

        Assert.assertNotNull("stream is null", stream);

        TikaDataFormat format = new TikaDataFormat();
        String text = (String)format.unmarshal(exchange, stream);

        Assert.assertNotNull(text);
        Assert.assertNotNull(exchange.getOut());
        Assert.assertNotNull(exchange.getOut().getHeaders());

        Map<String, Object> metadata = exchange.getOut().getHeaders();

        LOG.debug(text);
        for (Map.Entry<String, Object> current : metadata.entrySet()) {
            LOG.debug(current.getKey() + " / " + current.getValue().toString());
        }

        Assert.assertTrue(metadata.size() > 0);
        Assert.assertTrue(text.contains("XML Simple"));
    }

    @Test
    public void canLoadSimpleDocx() throws Exception {
        DefaultExchange exchange = new DefaultExchange(new DefaultCamelContext());
        InputStream stream = TikaDataFormatTest.class.getClassLoader().getResourceAsStream("test.docx");

        Assert.assertNotNull("stream is null", stream);

        TikaDataFormat format = new TikaDataFormat();
        String text = (String)format.unmarshal(exchange, stream);

        Assert.assertNotNull(text);
        Assert.assertNotNull(exchange.getOut());
        Assert.assertNotNull(exchange.getOut().getHeaders());

        Map<String, Object> metadata = exchange.getOut().getHeaders();

        LOG.debug(text);
        for (Map.Entry<String, Object> current : metadata.entrySet()) {
            LOG.debug(current.getKey() + " / " + current.getValue().toString());
        }

        Assert.assertTrue(metadata.size() > 0);
        Assert.assertTrue(text, text.contains("Word.docx Test"));
    }
}
