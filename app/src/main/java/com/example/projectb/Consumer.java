package com.example.projectb;

import java.io.Serializable;

public class Consumer implements Serializable {
    int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}
