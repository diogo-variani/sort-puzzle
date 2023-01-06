package com.vcompany.games.sortpuzzle;

import java.util.Arrays;
import java.util.Stack;
import java.util.stream.Collectors;

public class Container implements Cloneable {

    public static final int MAX_SIZE = 4;

    private String id;
    private Stack<BallCollor> stack;

    public Container(String id) {
        this.id = id;
        this.stack = new Stack<>();
    }

    public Container(String id, BallCollor[] balls) {
        if( balls.length > MAX_SIZE ){
            throw new IllegalArgumentException(String.format("The stack cannot be larger than %d", MAX_SIZE));
        }

        this.id = id;

        stack = new Stack<>();
        stack.addAll(Arrays.asList(balls));
    }

    public Container(String id, Stack<BallCollor> stack) {
        if( stack.size() > MAX_SIZE ){
            throw new IllegalArgumentException(String.format("The stack cannot be larger than %d", MAX_SIZE));
        }

        this.id = id;
        this.stack = stack;
    }

    public BallCollor remove() {
        if (stack.isEmpty()) {
            throw new IllegalStateException("Nothing to remove");
        }

        return stack.pop();
    }

    public BallCollor getCurrentBall() {
        if (stack.isEmpty()) {
            return null;
        }

        return stack.peek();
    }

    public void add(BallCollor ballCollor) {
        if (!canAdd(ballCollor)) {
            throw new IllegalStateException(String.format("The container is either full or the ball %s cannot be added over ", ballCollor, getCurrentBall()));
        }

        stack.push(ballCollor);
    }

    public boolean canMove(Container source) {
        BallCollor currentBall = source.getCurrentBall();
        return canAdd(currentBall);
    }


    public boolean canAdd(BallCollor ballCollor) {
        return isThereSpace() ?
                isEmpty() ? true : this.getCurrentBall() == ballCollor
                : false;
    }

    public BallCollor getBallCollorAt(int index) {
        if (index < 0 || index >= this.stack.size()) {
            return null;
        }

        return stack.get(index);
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public boolean isThereSpace() {
        return this.stack.size() != MAX_SIZE;
    }

    public boolean isCompleted() {
        if(stack.isEmpty()){
            return true;
        }else if(stack.size() < MAX_SIZE) {
            return false;
        }

        return areTheSame();
    }

    public String getId() {
        return this.id;
    }

    public int size(){
        return stack.size();
    }

    public String createCanonicalString(){
        return this.stack.stream().map( b -> b.getId()).collect(Collectors.joining("."));
    }

    public boolean areTheSame(){
        if (isEmpty()) {
            return true;
        }

        BallCollor currentBall = getCurrentBall();
        return stack.stream().allMatch(b -> b.equals(currentBall));
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        String id = this.id;

        Stack<BallCollor> balls = new Stack<>();
        balls.addAll(this.stack);

        return new Container(id, balls);
    }
}