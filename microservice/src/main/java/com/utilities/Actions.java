package com.utilities;

public enum Actions {
    GETLIST("products.getAll"),
    ADD("products.add");
    private String value;

    public String getValue() {
        return value;
    }
    Actions(String value) {
        this.value = value;
    }

    public static Actions getFromValue(String value) {
        for(Actions action : Actions.values()) {
            if(action.getValue().equalsIgnoreCase(value)) {
                return action;
            }
        }
        return null;
    }
}
