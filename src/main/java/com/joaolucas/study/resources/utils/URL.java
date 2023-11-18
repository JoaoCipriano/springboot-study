package com.joaolucas.study.resources.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class URL {

    private URL() {
    }

    public static String decodeParam(String s) {
        return URLDecoder.decode(s, StandardCharsets.UTF_8);
    }

    public static List<Integer> decodeIntList(String s) {
        return Arrays.stream(s.split(",")).map(Integer::parseInt).toList();
    }
}
