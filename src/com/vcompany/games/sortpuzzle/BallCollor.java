package com.vcompany.games.sortpuzzle;

public enum BallCollor {

    RED("R"), BLUE("B"), DARK_BLUE("DB"), YELLOW("Y"), GRAY("G"),
    ORANGE("O"), BROWN("B"), PINK("P"), PURPLE("PP"), GREEN("GN"),
    DARK_GREEN("DG"), LIGHT_GREEN("LG");

    private String id;

    BallCollor(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}
