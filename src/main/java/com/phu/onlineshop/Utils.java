package com.phu.onlineshop;

import com.google.common.io.Resources;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.IOException;

public final class Utils
{
    private Utils()
    {}

    public static final Yaml YAML = new Yaml();
    public static final Configuration RUNTIME_CONFIGURATION = loadConfig();

    private static Configuration loadConfig()
    {
        try (FileReader reader = new FileReader(Resources.getResource("online_shop.yml").getPath())) {
            return YAML.loadAs(new FileReader(Resources.getResource("online_shop.yml").getPath()), Configuration.class);
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
