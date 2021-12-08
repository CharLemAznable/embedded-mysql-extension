package com.github.charlemaznable.mysql.extension;

import com.github.charlemaznable.core.lang.Listt;
import com.github.charlemaznable.core.lang.Mapp;
import com.google.auto.service.AutoService;
import com.wix.mysql.Sources;
import com.wix.mysql.SqlScriptSource;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

import static com.github.charlemaznable.core.lang.ClzPath.classResource;

@AutoService(EmbeddedMysqlConfigLoader.class)
public class InitedEmbeddedMysqlConfigLoader implements EmbeddedMysqlConfigLoader {

    public static final String INITED_NAME = "inited";

    @Override
    public Map<String, EmbeddedMysqlConfig> loadEmbeddedMysqlConfigs() {
        return Mapp.of(INITED_NAME, new EmbeddedMysqlConfig() {

            @Override
            public int port() {
                return 3311;
            }

            @Override
            public List<Pair<String, List<SqlScriptSource>>> schemas() {
                return Listt.newArrayList(Pair.of(INITED_NAME, Listt.newArrayList(
                        Sources.fromURL(classResource("sql/inited.sql"))
                )));
            }
        });
    }
}
