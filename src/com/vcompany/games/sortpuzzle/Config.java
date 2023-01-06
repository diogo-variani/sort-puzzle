package com.vcompany.games.sortpuzzle;

public class Config {

    private boolean interactive = false;
    private boolean debug = false;
    private boolean showSummary = true;

    public boolean isInteractive() {
        return interactive;
    }

    public Config setInteractive(boolean interactive) {
        this.interactive = interactive;
        return this;
    }

    public boolean isDebug() {
        return debug;
    }

    public Config setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public boolean isShowSummary() {
        return showSummary;
    }

    public Config setShowSummary(boolean showSummary) {
        this.showSummary = showSummary;
        return this;
    }
}