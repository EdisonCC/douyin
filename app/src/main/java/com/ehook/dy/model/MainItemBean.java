package com.ehook.dy.model;

import java.io.Serializable;

public class MainItemBean implements Serializable {
    public String title;
    public boolean isOpen;

    public MainItemBean(String title, boolean b) {
        this.title = title;
        this.isOpen = b;
    }
}
