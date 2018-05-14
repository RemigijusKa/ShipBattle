package zaidimas;

import zaidimas.Data.Game;
import zaidimas.Data.Ship;
import zaidimas.Data.User;
import zaidimas.Services.GameService;
import zaidimas.Services.ShipService;
import zaidimas.Services.UserService;

import java.util.List;

public class App {

    public static void main( String[] args ) {
        UserService userService = new UserService();
        GameService gameService = new GameService();
        ShipService shipService = new ShipService();
        User user = userService.createUser("Remigijus", "Remigijus.kaniava@gmail.com");
        Game game = gameService.joinGame (user.getId(),GameService.DIFFICULTY_SIMPLE);
        gameService.checkIfCanSetupGame(game);
        List<Ship> shipList = shipService.createShipList();
        String [][] shipTable =  shipService.createGameTable(shipList);
        String [][] opponentGameTable = shipService.createOpponentGameTable();
        String shipDataForServer = shipService.createShipDataFromList(shipList);
        gameService.setupGame(shipDataForServer, game, user);
        gameService.checkIfCanStartGame(game);

        gameService.gameTurns(game, user, shipTable,opponentGameTable);
    }

}
