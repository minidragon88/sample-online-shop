package com.phu.onlineshop;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.io.Resources;

import java.nio.charset.Charset;

public final class TestHelpers
{
    private TestHelpers()
    {}

    public static ObjectNode getData(final String fileName)
    {
        try {
            return (ObjectNode) Utils.MAPPER.readTree(Resources.toString(Resources.getResource(fileName), Charset.defaultCharset()));
        }
        catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
