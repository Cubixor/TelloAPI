package me.cubixor.telloapi.logs;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogRecord implements Serializable {
    private final String name;
    private final int id;
    private final List<LogField> fields;
    private int size = 0;

    public LogRecord(String name, int id) {
        this.name = name;
        this.id = id;
        this.fields = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void calculateSize() {
        for (LogField logField : getFields()) {
            size += LogDataType.matchByName(logField.getDataType()).getSize();
        }
        System.out.println(name + " size: " + size);
    }

    public List<LogField> getFields() {
        return fields;
    }

}
