package com.amstr.runscore.HomeAdapter;

public class FeaturedHelperClass {
    int icon;
    String value,desc;

    public FeaturedHelperClass(int icon, String value, String desc) {
        this.icon = icon;
        this.value = value;
        this.desc = desc;
    }

    public int getIcon() {
        return icon;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
