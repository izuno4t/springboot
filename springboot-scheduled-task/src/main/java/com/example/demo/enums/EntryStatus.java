package com.example.demo.enums;

import org.seasar.doma.Domain;

@Domain(valueType = String.class, factoryMethod = "of")
public enum EntryStatus {

    /**
     * 待機中
     */
    QUEUED("0"),
    /**
     * 実行中
     */
    EXECUTING("1"),
    /**
     * 成功
     */
    SUCCESSES("2"),
    /**
     * 成功（警告有り）
     */
    SUCCESSES_WITH_WARNING("3"),
    /**
     * 中止
     */
    STOPPED("4"),
    /**
     * 失敗
     */
    FAILED("9");

    private final String value;

    EntryStatus(String value) {
        this.value = value;
    }

    public static EntryStatus of(String value) {
        for (var status : EntryStatus.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException(value);
    }

    public String getValue() {
        return value;
    }
}
