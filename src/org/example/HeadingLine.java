package org.example;

public class HeadingLine {
    private final String name;
    private final int level;

    HeadingLine(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel(){
        return level;
    }
}
