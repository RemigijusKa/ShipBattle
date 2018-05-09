package zaidimas.Services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import zaidimas.Data.Coordinate;
import zaidimas.Data.Event;
import zaidimas.Data.Game;
import zaidimas.Data.User;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GameService extends WebServiceClient {
    public static final String METHOD_JOIN = "join_";
    public static final String DIFFICULTY_SIMPLE = "simple?";
    public static final String DIFFICULTY_SMART = "smart?";
    public static final String METHOD_SETUP = "setup?";
    public static final String METHOD_STATUS = "status?";
    public static final String METHOD_TURN = "turn?";

    static Scanner scanner = new Scanner(System.in);
    ShipService shipService = new ShipService();

    public Game joinGame(String userId, String difficulty) {
        String url = WebServiceClient.SERVER + METHOD_JOIN + difficulty + "user_id=" + userId;
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            String body = convertStreamToString(response.getEntity().getContent());
            if (response.getStatusLine().getStatusCode() == 200) {
                return convertResponseToGame(body);
            }
            System.out.println("Klaida is serverio:" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Game convertResponseToGame(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(str);
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            String gameId = (String) jsonObject.get("id");
            String gameStatus = (String) jsonObject.get("status");
            return new Game(gameId, gameStatus);
        }
        return null;
    }


    public String setupGame(String shipData, Game game, User user) {


        String url = WebServiceClient.SERVER + METHOD_SETUP + "game_id=" + game.getGameId() + "&user_id=" + user.getId() + "&data=" + shipData;
        String body = requestToServer(url);
        return parseGameStatus(body, game);
    }

    public String requestToServer(String url) {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        try {
            HttpResponse response = client.execute(request);
            String body = convertStreamToString(response.getEntity().getContent());
            if (response.getStatusLine().getStatusCode() == 200) {

                return body;
            }
            System.out.println("Klaida is serverio:" + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parseGameStatus(String str, Game game) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(str);
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                String status = (String) jsonObject.get("status");
                String userTurnId = (String) jsonObject.get("nextTurnForUserId");
                game.setStatus(status);
                game.setNextTurnId(userTurnId);
                return status;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getGameStatus(Game game) {
        String url = WebServiceClient.SERVER + METHOD_STATUS + "game_id=" + game.getGameId();
        String body = requestToServer(url);
        return parseGameStatus(body, game);
    }

    public void checkIfCanSetupGame(Game game) {

        while (true) {
            String gameStatus = getGameStatus(game);
            if (gameStatus.equals(Game.READY_FOR_SHIPS)) {
                System.out.println(Game.READY_FOR_SHIPS);
                break;
            } else {
                try {
                    System.out.println(gameStatus);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void checkIfCanStartGame(Game game) {

        while (true) {
            String gameStatus = getGameStatus(game);
            if (gameStatus.equals(Game.READY_TO_PLAY)) {
                System.out.println(gameStatus);
                break;
            } else {
                try {
                    System.out.println(gameStatus);
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void checkWhoseTurnIsNow(Game game, User user) {
        while (true) {
            getGameStatus(game);
            String nextTurn = game.getNextTurnId();
            if (nextTurn.equals(user.getId())) {
                System.out.println("Kitas ėjimas: " + user.getName());
                break;
            } else {
                try {
                    System.out.println("Kitas ėjimas: " + game.getNextTurnId());
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void gameTurns(Game game, User user, String[][] shiptable, String[][] opponentGameTable) {
        while (game.getStatus() != Game.FINISHED) {

            checkWhoseTurnIsNow(game, user);
            System.out.println("Įveskite savo šūvį: ");
            String turnCoordinate = scanner.next();
            turnCoordinate = turnCoordinate.toUpperCase();
            String url = WebServiceClient.SERVER + METHOD_TURN + "game_id=" + game.getGameId() + "&user_id=" + user.getId() + "&data=" + turnCoordinate;
            String body = requestToServer(url);
            List<Event> events = parseTurnResponse(body, game);
            updateTurnsOnTables(events, shiptable, opponentGameTable, user);
            shipService.printGameTableWithShips(shiptable);
            shipService.printGameTableWithShips(opponentGameTable);
        }
    }

    public List<Event> parseTurnResponse(String str, Game game) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(str);
            List<Event> events = new ArrayList<Event>();
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                String status = (String) jsonObject.get("status");
                String nexTurnId = (String) jsonObject.get("nextTurnForUserId");
                String winnerUserId = (String) jsonObject.get("winnerUserId");
                game.setStatus(status);
                game.setNextTurnId(nexTurnId);
                game.setWinnerId(winnerUserId);
                JSONArray eventsJson = (JSONArray) jsonObject.get("events");
                for (Object eventObj : eventsJson) {
                    JSONObject eventJsonObject = (JSONObject) eventObj;
                    Date createDate = new Date((Long) eventJsonObject.get("date"));

                    JSONObject coordJsonObj = (JSONObject) eventJsonObject.get("coordinate");
                    String coordColumn = (String) coordJsonObj.get("column");
                    Long coordRow = (Long) coordJsonObj.get("row");
                    int columnIndex = Game.COLUMN_NAMES.indexOf(coordColumn);

                    Coordinate coordinate = new Coordinate(coordColumn, columnIndex, coordRow.intValue());
                    String userId = (String) eventJsonObject.get("userId");
                    Boolean hit = (Boolean) eventJsonObject.get("hit");

                    Event event = new Event(createDate, coordinate, hit, userId);
                    events.add(event);
                }
                return events;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateTurnsOnTables(List<Event> events, String[][] myShipTable, String[][] opponentGameTable, User user) {
        for (Event event : events) {
            if (event.getUserId().equals(user.getId())) {
                if (event.isHit()) {
                    opponentGameTable[event.getCoordinate().getRow()][event.getCoordinate().getColumnIndex()] = ShipService.SHIP_HIT;
                } else {
                    opponentGameTable[event.getCoordinate().getRow()][event.getCoordinate().getColumnIndex()] = ShipService.EMPTY_HIT;
                }
            }
            if (!event.getUserId().equals(user.getId())) {
                if (event.isHit()) {
                    myShipTable[event.getCoordinate().getRow()][event.getCoordinate().getColumnIndex()] = ShipService.SHIP_HIT;
                } else {
                    myShipTable[event.getCoordinate().getRow()][event.getCoordinate().getColumnIndex()] = ShipService.EMPTY_HIT;
                }
            }
        }
    }


}
