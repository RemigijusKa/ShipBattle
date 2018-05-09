# ShipBattle

This programs includes Data files, Service files and main App.

Data files - objects: Ship, Event, User, Game, Coordinate.
Service files: 
WebServiceClient - connects to game server.
UserService - creates User during the game.
ShipService - creates list of ships, creates game tables, and saves data during the game. 
GameService - operates main commands during the game - joins the game, setups ship information, makes turns and collects data from server. 
Game is controlled from command line.

This program is setup in a way that ships are already determined, their locations can be changed from ShipService class. User information also is determined and can be modified from UserService calss.

Program makes turns - send shooting coordinates to server. Receives JSnon object type information with game Id, user Id, player Id for next turn, and events - where shots were made and did they hit something. From received information program draws game tables - user and opponent tables. Every turn tables are modified with information about shots, misses and hits.

