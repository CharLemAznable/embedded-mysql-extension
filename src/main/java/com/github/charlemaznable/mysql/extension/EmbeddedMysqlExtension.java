package com.github.charlemaznable.mysql.extension;

import com.github.charlemaznable.core.lang.Listt;
import com.github.charlemaznable.core.lang.Mapp;
import com.google.common.collect.Maps;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import lombok.val;
import org.junit.jupiter.api.extension.Extension;

import java.util.Map;
import java.util.ServiceLoader;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;

public final class EmbeddedMysqlExtension implements Extension {

    public static final String DEFAULT_NAME = "embedded";
    private static final Map<String, EmbeddedMysql> embeddedMysqlMap = Maps.newHashMap();

    static {
        val configLoaders = ServiceLoader.load(EmbeddedMysqlConfigLoader.class);
        for (val configLoader : configLoaders) {
            val configMap = Mapp.newHashMap(configLoader.loadEmbeddedMysqlConfigs());
            for (val entry : configMap.entrySet()) {
                embeddedMysqlMap.put(entry.getKey(), embeddedMysql(entry.getValue()));
            }
        }
    }

    public static EmbeddedMysql getMysql(String name) {
        return embeddedMysqlMap.get(name);
    }

    private static EmbeddedMysql embeddedMysql(EmbeddedMysqlConfig config) {
        val mysqldConfig = mysqldConfig(config);
        val embeddedMysql = anEmbeddedMysql(mysqldConfig);
        val schemas = Listt.newArrayList(config.schemas());
        for (val schema : schemas) {
            embeddedMysql.addSchema(schema.getKey(),
                    Listt.newArrayList(schema.getValue()));
        }
        return embeddedMysql.start();
    }

    private static MysqldConfig mysqldConfig(EmbeddedMysqlConfig config) {
        val mysqldConfig = aMysqldConfig(config.version())
                .withPort(config.port())
                .withCharset(config.charset())
                .withUser(config.user().getLeft(), config.user().getRight())
                .withTimeZone(config.timeZone());
        val serverVariables = Listt.newArrayList(config.serverVariables());
        for (val variable : serverVariables) {
            mysqldConfig.withServerVariable(variable.getKey(), variable.getValue());
        }
        return mysqldConfig.build();
    }

    private EmbeddedMysqlExtension() {}
}
