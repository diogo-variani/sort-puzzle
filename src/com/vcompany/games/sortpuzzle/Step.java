package com.vcompany.games.sortpuzzle;

public class Step {

    private String source;
    private String target;
    private BallCollor ballCollor;

    public String getSource() {
        return source;
    }

    public Step setSource(String source) {
        this.source = source;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public Step setTarget(String target) {
        this.target = target;
        return this;
    }

    public BallCollor getBallCollor() {
        return ballCollor;
    }

    public Step setBallCollor(BallCollor ballCollor) {
        this.ballCollor = ballCollor;
        return this;
    }
}
