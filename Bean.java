package com.example.administrator.yuekaob;

/**
 * date:2017/5/28 0028
 * authom:贾雪茹
 * function:
 */

public class Bean {
    private String name;
    private boolean cb;

    public boolean isCb() {
        return cb;
    }

    public void setCb(boolean cb) {
        this.cb = cb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", cb=" + cb +
                '}';
    }
}
