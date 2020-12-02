package com.imooc.mall.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;


public class HlgGsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(HlgGsonUtil.class);

    private HlgGsonUtil() {
    }

    public static String toJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static String toDecoderJson(Object o) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(o);
    }

    public static String toJsonWithNull(Object o) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(o);
    }

    public static String toJsonWithoutSlash(Object o) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        String ret = gson.toJson(o).replace("\\", "");
        logger.info("toJsonWithoutSlash {}", ret);
        return ret;
    }

    public static String toJsonWithoutSlashCms(Object o) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();
        String ret = gson.toJson(o).replace("\\", "");
        logger.info("toJsonWithoutSlash {}", ret);
        return ret;
    }

    public static void main(String[] args) {
        String URL =
                "https://web.hanyuhl.com/h5/pages/Detail/Detail?userinfo={\"Ssecurity\":\"xAdwD4peQStiQDLJ2bhePA"
                        + "==\"//,\"userId\":\"10000004693\",\"hlg"
                        + ".api_st\":\"CgpobGcuYXBpLnN0EmCuRxCir12sfnO7oPbv7r3ql7Va01AnhcVu6e8BuZYe9kK0jCkptGjb7"
                        +
                        "-jBtPsCBqmCUXWf4DrXP2Er1_KJSfNo8JPI9l_XegEGn0l6OfiZsKDg0MS3Zz7tIyH5Hnb3vM4aErfv7SvYRiV5CVVFUKUR5NbCLCIgFMYa43V30D7m01YGD0EcYT7C0kLM6iJtnn-orJvnZl0oBTAB\"}&itemId=7119545";
        System.out.println(URL.replaceAll("//", ""));
    }

}
