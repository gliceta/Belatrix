package com.prueba.belatrix.enums;

/**
 * @author German Liceta
 */
public enum LogTypeEnum {
    MESSAGE("MESSAGE", 1),
    WARNING("WARNING", 2),
    ERROR("ERROR", 3);

    private String descLogType = "";
    private int valueLogType = 0;

    /**
     *
     * @param descLogType
     * @param valueLogType
     */
    private LogTypeEnum(String descLogType, int valueLogType) {
        this.descLogType = descLogType;
        this.valueLogType = valueLogType;
    }

    /**
     *
     * @return
     */
    public String getDescLogType() {
        return descLogType;
    }

    /**
     *
     * @return
     */
    public int getValueLogType() {
        return valueLogType;
    }
}
