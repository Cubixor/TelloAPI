package me.cubixor.telloapi.logs;

import java.io.Serializable;

public class LogField implements Serializable {

    private final String name;
    private final String dataType;

    public LogField(String name, String dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public String getDataType() {
        return dataType;
    }

}
