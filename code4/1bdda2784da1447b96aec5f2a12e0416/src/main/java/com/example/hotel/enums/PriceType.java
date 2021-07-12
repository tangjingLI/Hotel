package com.example.hotel.enums;

public enum PriceType {
    one("100-300"),
    tow("300-500"),
    there("500以上");
    private String value;

    PriceType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
