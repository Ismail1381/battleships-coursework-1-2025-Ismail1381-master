package model;

import java.util.List;

public class Ship{
    private int size;
    private List<String> position;

    public Ship(int length, List <String> position){
        this.size = length;
        this.position = position;
    }
    public List<String> getPositions(){
        return position;
    }


    public boolean contains(int x, int y){
        return position.contains(x+" "+y);
    }
    }

