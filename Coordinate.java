package zaidimas.Data;

public class Coordinate {
    String column;
    int columnIndex;
    int row;

    public Coordinate(String column, int columnIndex, int row) {
        this.column = column;
        this.columnIndex = columnIndex;
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
    @Override
    public String toString(){
        return column + row;
    }
}
