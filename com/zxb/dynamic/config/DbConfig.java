package com.zxb.dynamic.config;

import com.zxb.dynamic.core.MultiDataSource;
import com.zxb.dynamic.factory.DsFactory;
import com.zxb.dynamic.factory.DsInterface;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mr.M
 * @date 2023/3/6
 * @Description
 */
@Configuration
public class DbConfig {

    private final Environment environment;
    private final DsFactory dsFactory;

    public DbConfig(Environment environment, DsFactory dsFactory) {
        this.environment = environment;
        this.dsFactory = dsFactory;
    }

    @Bean
    @Primary
    public DataSource buildMainDataSource() {
        MultiDataSource multiDataSource = new MultiDataSource();
        multiDataSource.setDefaultTargetDataSource(buildDataSource("main"));
        return multiDataSource;
    }

    public DataSource buildDataSource(String dsName) {
        Map<String, String> map = new HashMap<>(4);
        map.put("url", environment.getProperty("spring.datasource." + dsName + ".url"));
        map.put("username", environment.getProperty("spring.datasource." + dsName + ".username"));
        map.put("password", environment.getProperty("spring.datasource." + dsName + ".password"));
        map.put("driverClass", environment.getProperty("spring.datasource." + dsName + ".driver-class-name"));

        String type = environment.getProperty("ds.pool.type");
        DsInterface dsInterface = dsFactory.getDs(type);
        return dsInterface.buildDs(dsName, map);
    }
}
