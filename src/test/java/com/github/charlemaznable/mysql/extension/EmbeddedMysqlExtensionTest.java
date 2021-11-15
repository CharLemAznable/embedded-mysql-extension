package com.github.charlemaznable.mysql.extension;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.n3r.diamond.client.impl.MockDiamondServer;
import org.n3r.eql.diamond.Dql;

import static com.github.charlemaznable.mysql.extension.EmbeddedMysqlExtension.DEFAULT_NAME;
import static com.github.charlemaznable.mysql.extension.InitedEmbeddedMysqlConfigLoader.INITED_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWithEmbeddedMysql
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedMysqlExtensionTest {

    @BeforeAll
    public void beforeAll() {
        MockDiamondServer.setUpMockServer();
        MockDiamondServer.setConfigInfo("EqlConfig", DEFAULT_NAME, "" +
                "url=jdbc:mysql://127.0.0.1:3310/embedded?useUnicode=true&&characterEncoding=UTF-8&connectTimeout=60000&socketTimeout=60000&autoReconnect=true\n" +
                "username=auser\n" +
                "password=sa\n" +
                "initialSize=1\n" +
                "minIdle=1\n" +
                "maxActive=50\n" +
                "maxWait=60000\n" +
                "timeBetweenEvictionRunsMillis=60000\n" +
                "minEvictableIdleTimeMillis=300000\n" +
                "validationQuery=SELECT 'x'\n" +
                "connection.impl=org.n3r.eql.trans.EqlDruidConnection\n");
        MockDiamondServer.setConfigInfo("EqlConfig", INITED_NAME, "" +
                "url=jdbc:mysql://127.0.0.1:3311/inited?useUnicode=true&&characterEncoding=UTF-8&connectTimeout=60000&socketTimeout=60000&autoReconnect=true\n" +
                "username=auser\n" +
                "password=sa\n" +
                "initialSize=1\n" +
                "minIdle=1\n" +
                "maxActive=50\n" +
                "maxWait=60000\n" +
                "timeBetweenEvictionRunsMillis=60000\n" +
                "minEvictableIdleTimeMillis=300000\n" +
                "validationQuery=SELECT 'x'\n" +
                "connection.impl=org.n3r.eql.trans.EqlDruidConnection\n");
    }

    @AfterAll
    public void afterAll() {
        MockDiamondServer.tearDownMockServer();
    }

    @Test
    public void testDefaultEmbeddedMysql() {
        assertNotNull(EmbeddedMysqlExtension.getMysql(DEFAULT_NAME));

        val result = new Dql(DEFAULT_NAME).limit(1).execute("select 'x'");
        assertEquals("x", result);
    }

    @Test
    public void testInitedEmbeddedMysql() {
        assertNotNull(EmbeddedMysqlExtension.getMysql(INITED_NAME));

        val result1 = new Dql(INITED_NAME).limit(1).execute("select b from inited_test where a=1");
        assertEquals("111", result1);

        val result2 = new Dql(INITED_NAME).limit(1).execute("select b from inited_test where a=2");
        assertEquals("222", result2);
    }
}
