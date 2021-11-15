package com.github.charlemaznable.mysql.extension;

import com.github.charlemaznable.core.lang.Listt;
import com.wix.mysql.SqlScriptSource;
import com.wix.mysql.config.Charset;
import com.wix.mysql.distribution.Version;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

import static com.github.charlemaznable.mysql.extension.EmbeddedMysqlExtension.DEFAULT_NAME;
import static com.wix.mysql.config.Charset.UTF8;
import static com.wix.mysql.distribution.Version.v5_7_latest;

public interface EmbeddedMysqlConfig {

    default Version version() {
        return v5_7_latest;
    }

    default int port() {
        return 3310;
    }

    default Charset charset() {
        return UTF8;
    }

    default Pair<String/* username */, String/* password */> user() {
        return Pair.of("auser", "sa");
    }

    default String timeZone() {
        return "UTC";
    }

    default List<Pair<String/* name */, String/* value */>> serverVariables() {
        return Listt.newArrayList(Pair.of("sql_mode", ""));
    }

    default List<Pair<String/* schema name */, List<SqlScriptSource>/* SqlScript */>> schemas() {
        return Listt.newArrayList(Pair.of(DEFAULT_NAME, Listt.newArrayList()));
    }
}
