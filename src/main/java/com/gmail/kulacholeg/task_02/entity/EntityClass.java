package com.gmail.kulacholeg.task_02.entity;

import com.gmail.kulacholeg.task_02.annotation.FormatProperty;
import com.gmail.kulacholeg.task_02.annotation.NameProperty;

import java.time.Instant;

public class EntityClass {
    private String stringProperty;
    @NameProperty(name = "numberProperty")
    private int myNumber;
    @FormatProperty
    private Instant timeProperty;

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(int myNumber) {
        this.myNumber = myNumber;
    }

    public Instant getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Instant timeProperty) {
        this.timeProperty = timeProperty;
    }

    @Override
    public String toString() {
        return "String: " + stringProperty +
                "\nint: " + myNumber +
                "\ntime: " + timeProperty;
    }
}
