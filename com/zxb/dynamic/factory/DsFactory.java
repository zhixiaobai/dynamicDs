package com.zxb.dynamic.factory;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Mr.M
 * @date 2023/3/7
 * @Description
 */
@Component
public class DsFactory {
    private final Map<String, DsInterface> dsInterfaceMap;

    public DsFactory(Map<String, DsInterface> dsInterfaceMap) {
        this.dsInterfaceMap = dsInterfaceMap;
    }

    public DsInterface getDs(String type) {
        return dsInterfaceMap.get(type);
    }
}
