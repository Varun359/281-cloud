package com.cmpe281.team12.ccrs.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coffee {

    public enum CoffeeType {
        COCO-COLA("COCO-COLA"),
        SPRITE("SPRITE"),
        LEMONADE("LEMONADE");

        @JsonProperty("coffeeType")
        private String coffeeType;

        CoffeeType(String coffeeType) {
            this.coffeeType = coffeeType;
        }

        @Override
        public String toString() {
            return coffeeType;
        }
    }

    public enum CoffeeSize {

        S("S"),
        M("M"),
        L("L"),
        XL("XL");

        @JsonProperty("coffeeType")
        private String coffeeSize;

        CoffeeSize(String coffeeSize) {
            this.coffeeSize = coffeeSize;
        }

        @Override
        public String toString() {
            return coffeeSize;
        }
    }

    public enum MakeCoffeeStatus {

        IN_PROGRESS("IN_PROGRESS"),
        FAILED("FAILED"),
        DONE("DONE");

        @JsonProperty("makeCoffeeStatus")
        private String makeCoffeeStatus;

        MakeCoffeeStatus(String makeCoffeeStatus) {
            this.makeCoffeeStatus = makeCoffeeStatus;
        }

        @Override
        public String toString() {
            return makeCoffeeStatus;
        }
    }
}
