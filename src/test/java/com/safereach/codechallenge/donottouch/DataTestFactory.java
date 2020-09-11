package com.safereach.codechallenge.donottouch;

public class DataTestFactory {

    public static Data dataEntity() {
        return dataEntity(1L, "content");
    }

    public static Data dataEntity(Long id, String content) {
        Data data = new Data();
        data.setId(id);
        data.setContent(content);
        return data;
    }

}
