package com.vcompany.games.sortpuzzle;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Printer {

    private static final int PRINT_LINES = 2;
    private static final int PRINT_COLUMN_LENGTH = 15;

    private static final String PRINT_MASK = "%-" + PRINT_COLUMN_LENGTH + "s";
    private static final String PRINT_SEPARATOR = "------------";
    private static final String PRINT_NULL_VALUE = "-";

    public static void print(Grid grid) {
        List<Container> containers = grid.getContainers();
        int containersPerLine = containers.size() % PRINT_LINES > 0 ? (containers.size() / PRINT_LINES) + 1 : containers.size() / PRINT_LINES;

        Stream.generate(() -> "-").limit(PRINT_COLUMN_LENGTH * containersPerLine).forEach(System.out::print);
        System.out.println("");
        System.out.println(grid.createCanonicalString());
        Stream.generate(() -> "-").limit(PRINT_COLUMN_LENGTH * containersPerLine).forEach(System.out::print);
        System.out.println("");

        for (int currentLine = 0; currentLine < PRINT_LINES; currentLine++) {
            final int start = currentLine * containersPerLine;
            final int end = Math.min((currentLine * containersPerLine) + containersPerLine, containers.size());

            IntStream.range(start, end)
                    .forEach(index -> System.out.format(PRINT_MASK, containers.get(index).getId()));

            System.out.print(System.lineSeparator());

            IntStream.range(start, end)
                    .forEach(index -> System.out.format(PRINT_MASK, PRINT_SEPARATOR));

            System.out.print(System.lineSeparator());

            IntStream.range(0, Container.MAX_SIZE)
                    .boxed()
                    .sorted(Collections.reverseOrder()) //Reverse order
                    .forEach(slot -> {
                        IntStream.range(start, end)
                                .forEach(index -> System.out.format(PRINT_MASK, containers.get(index).getBallCollorAt(slot) == null ? PRINT_NULL_VALUE : containers.get(index).getBallCollorAt(slot)));
                        System.out.print(System.lineSeparator());
                    });

            System.out.print(System.lineSeparator());
        }
    }


    public static void print(long start, long end, List<Step> steps) {

        long timeSpent = end - start;
        int stepsSize = steps.size();

        Stream.generate(() -> "-").limit(50).forEach(System.out::print);
        System.out.println("");
        System.out.format("Time spent: %d milesecond(s)",timeSpent);
        System.out.println("");
        System.out.format("Steps taken: %d steps to solve it!", stepsSize);
        System.out.println("");
        Stream.generate(() -> "-").limit(50).forEach(System.out::print);
        System.out.println("");
        System.out.println("");
    }
}
