package com.vcompany.games.sortpuzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.vcompany.games.sortpuzzle.BallCollor.*;

public class SortPuzzle {

    private static final String NEXT_COMMAND = "N";
    private static final String PRINT_COMMAND = "P";

    private Config config;

    public SortPuzzle(){
        this(new Config());
    }

    public SortPuzzle(Config config){
        this.config = config;
    }

    public List<Step> solve(Grid grid) {
        long start = System.currentTimeMillis();

        Printer.print(grid);

        List<Step> steps = new ArrayList<>();
        doSolve(0, grid, new ArrayList<>(), steps);

        long end = System.currentTimeMillis();

        Collections.reverse(steps);

        if( config.isShowSummary()){
            Printer.print(start, end, steps);
        }

        return steps;
    }

    private boolean doSolve(int recursiveIndex, Grid grid, List<String> stepControl, List<Step> steps) {
        visit(grid, stepControl);

        for (int i = 0; i < grid.size(); i++) {

            for (int j = 0; j < grid.size(); j++) {

                if (!canMove(recursiveIndex, grid, i, j)) {
                    continue;
                }

                Grid newGrid = new Grid(grid);
                BallCollor ball = move(newGrid, i, j);

                if (newGrid.isComplete()) {
                    log(recursiveIndex,"Finally is completed!!!!");
                    steps.add(new Step().setSource(grid.getId(i)).setTarget(grid.getId(j)).setBallCollor(ball));
                    return true;
                }

                if (isAlreadyVisited(newGrid, stepControl)) {
                    log(recursiveIndex,"Moving %s from %s to %s was already visited...", ball, newGrid.getId(i), newGrid.getId(j));
                    continue;
                }

                checkCommand(recursiveIndex, newGrid, ball, i, j);

                if (doSolve(recursiveIndex + 1, newGrid, stepControl, steps)) {

                    steps.add(new Step().setSource(grid.getId(i)).setTarget(grid.getId(j)).setBallCollor(ball));

                    log(recursiveIndex,"Finally solved!!!!");
                    return true;
                }
            }
        }

        log(recursiveIndex,"No solution has been found...");

        return false;
    }

    private boolean canMove(int recursiveIndex, Grid grid, int sourceIndex, int targetIndex) {
        if (sourceIndex == targetIndex) {
            return false;
        }

        Container source = grid.get(sourceIndex);
        Container target = grid.get(targetIndex);

        if (source.isEmpty()) {
            log(recursiveIndex,"Cannot move from %s to %s: source is empty...", source.getId(), target.getId());
            return false;
        }

        if (source.size() == 1 && target.isEmpty()) {
            log(recursiveIndex,"Cannot move from %s to %s: source has just one element and target is empty...", source.getId(), target.getId());
            return false;
        }

        if (source.isCompleted()) {
            log(recursiveIndex,"Cannot move from %s to %s: source is completed...", source.getId(), target.getId());
            return false;
        }

        if (source.size() > 1 && source.areTheSame()) {
            log(recursiveIndex,"Cannot move from %s to %s: all the balls from source matches...", source.getId(), target.getId());
            return false;
        }

        return target.canMove(source);
    }

    private BallCollor move(Grid grid, int sourceIndex, int targetIndex) {
        Container source = grid.get(sourceIndex);
        Container target = grid.get(targetIndex);
        BallCollor ballCollor = source.remove();
        target.add(ballCollor);

        return ballCollor;
    }

    private void checkCommand(int recursiveIndex, Grid grid, BallCollor ballCollor, int sourceIndex, int targetIndex) {
        Container source = grid.get(sourceIndex);
        Container target = grid.get(targetIndex);

        log(recursiveIndex, "Moving ball color %s from %s to %s...\n", ballCollor.toString(), source.getId(), target.getId());

        if( config.isInteractive() ) {
            if (checkNextCommand().equalsIgnoreCase(PRINT_COMMAND)) {
                Printer.print(grid);
            }
        }
    }

    private void visit(Grid grid, List<String> stepControl) {
        String canonicalString = grid.createCanonicalString();
        stepControl.add(canonicalString);
    }

    private boolean isAlreadyVisited(Grid grid, List<String> stepControl) {
        String canonicalString = grid.createCanonicalString();
        return stepControl.contains(canonicalString);
    }

    private String checkNextCommand() {
        try {
            while (true) {
                System.out.print("> ");
                String command = readLine();

                if (command.equalsIgnoreCase(NEXT_COMMAND) || command.equalsIgnoreCase(PRINT_COMMAND)) {
                    return command.toUpperCase();
                }
                if (command.trim().equals("")) {
                    return PRINT_COMMAND;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readLine() throws IOException {
        if (System.console() != null) {
            return System.console().readLine();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                System.in));
        return reader.readLine();
    }

    private void log(int recursiveIndex, String message, Object... parameters) {
        log(recursiveIndex, String.format(message, parameters));
    }

    private void log(int recursiveIndex, String message) {
        if( config.isDebug() ) {
            Stream.generate(() -> "\t").limit(recursiveIndex).forEach(System.out::print);
            System.out.println(message);
        }
    }

    public static void main(String[] args) {
        List<Container> containers = new ArrayList<>();
        containers.add(new Container("CNT 1", new BallCollor[]{PURPLE, PINK, GRAY, PURPLE}));
        containers.add(new Container("CNT 2", new BallCollor[]{GREEN, ORANGE, LIGHT_GREEN, GRAY}));
        containers.add(new Container("CNT 3", new BallCollor[]{GREEN, LIGHT_GREEN, DARK_BLUE, PINK}));
        containers.add(new Container("CNT 4", new BallCollor[]{LIGHT_GREEN, RED, PINK, BLUE}));
        containers.add(new Container("CNT 5", new BallCollor[]{BLUE, DARK_BLUE, GRAY, RED}));
        containers.add(new Container("CNT 6", new BallCollor[]{PURPLE, RED, DARK_BLUE, BLUE}));
        containers.add(new Container("CNT 7", new BallCollor[]{GRAY, PINK, RED, ORANGE}));
        containers.add(new Container("CNT 8", new BallCollor[]{LIGHT_GREEN, ORANGE, BLUE, GREEN}));
        containers.add(new Container("CNT 9", new BallCollor[]{PURPLE, ORANGE, DARK_BLUE, GREEN}));
        containers.add(new Container("CNT 10"));
        containers.add(new Container("CNT 11"));



        /*containers.add(new Container("CNT 1", new BallCollor[]{GREEN, BLUE, BLUE, BLUE}));
        containers.add(new Container("CNT 2", new BallCollor[]{YELLOW, BLUE, RED, YELLOW}));
        containers.add(new Container("CNT 3", new BallCollor[]{YELLOW, GREEN, GREEN, YELLOW}));
        containers.add(new Container("CNT 4", new BallCollor[]{RED, RED, RED, GREEN}));
        containers.add(new Container("CNT 5"));
        containers.add(new Container("CNT 6"));*/

        Grid grid = new Grid(containers);

        List<Step> steps = new SortPuzzle().solve(grid);
        steps.stream().forEach(s -> System.out.println(String.format("Move %s from %s to %s...", s.getBallCollor(), s.getSource(), s.getTarget())));
    }
}