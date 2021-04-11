package org.example;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MarkdownContents {
    public static final int MAX_LEVEL = 6; //6 is maximum number of # in beginning of heading

    public static String getContentHeaderLine(int level, int levelCount, String name) {
        return "\t".repeat(level - 1) + levelCount + ". [" + name + "](#" + name.replaceAll(" ", "-").toLowerCase() + ")";
    }

    public static List<HeadingLine> getHeadings(List<String> lines) {
        Pattern regex = Pattern.compile("#.*");
        return lines.stream().
                filter(s -> regex.matcher(s).matches()).
                map(s -> new HeadingLine(s.chars().mapToObj(c -> (char) c).dropWhile(c -> c == '#').
                        collect(Collector.of(
                                StringBuilder::new,
                                StringBuilder::append,
                                StringBuilder::append,
                                StringBuilder::toString)).trim(),
                        (int) s.chars().takeWhile(c -> c == '#').count())).
                collect(Collectors.toList());
    }

    public static void writeContents(List<HeadingLine> headings) {
        long lastLevel = -1;
        ArrayList<Integer> levelCount = new ArrayList<>(Collections.nCopies(MAX_LEVEL, 1));
        for (HeadingLine line : headings) {
            int level = line.getLevel();
            String name = line.getName();
            if (level > lastLevel) {
                levelCount.set(level, 1);
            } else {
                levelCount.set(level, 1 + levelCount.get(level));
            }
            lastLevel = level;
            System.out.println(getContentHeaderLine(level, levelCount.get(level), name));
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("You need to pass filename as an argument");
            return;
        }
        List<String> lines;
        try {
            lines = Files.lines(Paths.get(args[0])).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the file");
            return;
        }
        List<HeadingLine> headings = getHeadings(lines);
        writeContents(headings);
        System.out.println();
        lines.forEach(System.out::println);

    }
}
