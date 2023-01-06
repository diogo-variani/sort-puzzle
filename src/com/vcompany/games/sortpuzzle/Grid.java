package com.vcompany.games.sortpuzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Grid {

    private List<Container> containers;

    public Grid(Grid grid){
        containers = new ArrayList<>(grid.containers.size());

        for( Container container : grid.containers ){
            try {
                containers.add( (Container) container.clone() );
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Grid(List<Container> containers){
        this.containers = containers;
    }

    public String createCanonicalString(){
        return containers.stream().map(c -> c.createCanonicalString()).collect(Collectors.joining("-"));
    }

    public boolean isComplete() {
        return this.containers.stream().allMatch(c -> c.isCompleted());
    }

    public boolean isComplete(int i) {
        return this.containers.get(i).isCompleted();
    }

    public boolean allMatches(int i){
        return this.containers.get(i).areTheSame();
    }

    public boolean isEmpty(int i){
        return this.containers.get(i).isEmpty();
    }

    public List<Container> getContainers() {
        return containers;
    }

    public String getId(int i){
        return containers.get(i).getId();
    }

    public int size(){
        return containers.size();
    }

    public Container get(int i){
        return containers.get(i);
    }
}
