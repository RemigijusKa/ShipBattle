package zaidimas.Data;

public class Ship {
    Coordinate start;
    Coordinate end;


    public Ship(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public Coordinate getStart() {
        return start;
    }

    public void setStart(Coordinate start) {
        this.start = start;
    }

    public Coordinate getEnd() {
        return end;
    }

    public void setEnd(Coordinate end) {
        this.end = end;
    }
    @Override
    public String toString(){
        return start + "-"+ end;
    }
}

