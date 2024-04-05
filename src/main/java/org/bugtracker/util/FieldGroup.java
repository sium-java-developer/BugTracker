package org.bugtracker.util;

public enum FieldGroup {
    LOW,
    MEDIUM,
    HIGH;

    public static FieldGroup getField(String field){
        return FieldGroup.valueOf(field.toUpperCase());
    }
}