package zaidimas.Services;

import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zaidimas.Data.Coordinate;
import zaidimas.Data.Game;
import zaidimas.Data.Ship;

public class ShipService {
    public static final String SHIP_HIT = "X";
    public static final String EMPTY_HIT = "*";
    public static final String MY_SHIP = "O";
    public static final String TABLE_SPACE = ".";


    public List<Ship> createShipList() {

        List<Ship> shipList = new ArrayList<Ship>();

        shipList.add(parseShip("M3", "M6"));
        shipList.add(parseShip("I6", "I8"));
        shipList.add(parseShip("R1", "S1"));
        shipList.add(parseShip("I3", "L3"));
        shipList.add(parseShip("T4", "R4"));
        shipList.add(parseShip("T8", "T9"));
        shipList.add(parseShip("O1", "O1"));
        shipList.add(parseShip("A6", "A6"));
        shipList.add(parseShip("O9", "O9"));
        shipList.add(parseShip("S9", "S9"));

        return shipList;
    }

    public Ship parseShip(String coordinateStart, String coordinateEnd) {

        return new Ship(convertToCoordinate(coordinateStart), convertToCoordinate(coordinateEnd));
    }

    public Coordinate convertToCoordinate(String coordinate) {

        String column = coordinate.substring(0, 1);
        String kilometras = Game.COLUMN_NAMES;
        int columnIndex = kilometras.indexOf(column);
        int row = Integer.parseInt(coordinate.substring(1));
        return new Coordinate(column, columnIndex, row);
    }

    public String [][]  createGameTable(List<Ship> shipList) {
        String[][] shipTable = new String[10][10];

        for (int row = 0; row < shipTable.length; row++) {
            for (int column = 0; column < shipTable[row].length; column++) {
                shipTable[row][column] = TABLE_SPACE;
            }
        }
        return drawShipsInGameTable(shipList, shipTable);
    }

    public String [][] drawShipsInGameTable(List<Ship> shipList, String[][] shipTable) {
        for (Ship ship : shipList) {
            for (int row = ship.getStart().getRow(); row <= ship.getEnd().getRow(); row++) {
                for (int column = ship.getStart().getColumnIndex(); column <= ship.getEnd().getColumnIndex(); column++) {
                    shipTable[row][column] = MY_SHIP;
                }
            }
        }
        printGameTableWithShips(shipTable);
        return shipTable;
    }

    public void  printGameTableWithShips(String[][] shipTable) {
        System.out.print(" ");
        for (int i = 0; i < Game.COLUMN_NAMES.length(); i++) {
            System.out.print(" " + Game.COLUMN_NAMES.charAt(i));
        }
        System.out.println();
        for (int row = 0; row < shipTable.length; row++) {
            System.out.print(row + " ");
            for (int column = 0; column < shipTable[row].length; column++) {
                System.out.print(shipTable[row][column] + " ");
            }
            System.out.println();
        }
    }

    public String createShipDataFromList(List<Ship> shipList) {

        StringBuilder stringBuilder = new StringBuilder();
        for (Ship ship : shipList) {
            stringBuilder.append(ship).append("!");
        }
        return stringBuilder.toString();

    }
    public String [][]  createOpponentGameTable() {
        String[][] shipTable = new String[10][10];

        for (int row = 0; row < shipTable.length; row++) {
            for (int column = 0; column < shipTable[row].length; column++) {
                shipTable[row][column] = TABLE_SPACE;
            }
        }
        return shipTable;
    }
}
