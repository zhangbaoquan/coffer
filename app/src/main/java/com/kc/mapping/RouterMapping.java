package com.kc.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 * author       : coffer
 * date         : 2022/3/20
 * description  :
 */

public class RouterMapping {
    public static Map<String, String> get() {

        Map<String, String> map = new HashMap<>();

        // 下面的RouterMapping_1、RouterMapping_2 是自定义的
//        map.putAll(RouterMapping_1.get());
//
//        map.putAll(RouterMapping_2.get());

        // ...

        return map;

    }

}
