package com.github.charlemaznable.mysql.extension;

import com.github.charlemaznable.core.lang.Mapp;

import java.util.Map;

import static com.github.charlemaznable.mysql.extension.EmbeddedMysqlExtension.DEFAULT_NAME;

public interface EmbeddedMysqlConfigLoader {

    default Map<String, EmbeddedMysqlConfig> loadEmbeddedMysqlConfigs() {
        return Mapp.of(DEFAULT_NAME, new EmbeddedMysqlConfig() {});
    }
}
