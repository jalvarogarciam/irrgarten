public class GameState {
/*Esta clase permitirá, de forma muy sencilla, almacenar una representación del 
estado completo del juego: 
    El estado del laberinto
    El estado de los jugadores
    El estado de los monstruos
    El índice del jugador que tiene el turno
    Un indicador sobre si ya hay un ganador
    Un atributo adicional para guardar en una cadena de caracteres eventos 
    interesantes que hayan ocurrido desde el turno anterior 
*/
    private String labyrinth;   //Estado del laberinto
    private String players;     //Estado de los jugadores
    private String monsters;    //Estado de los monstruos
    private int currentPlayer;  //Indice del jugador actual
    private boolean winner;     //Indicador sobre si ya hay un ganador
    //Eventos interesantes que hayan ocurrido desde el turno anterior
    private String log;


    public GameState(String labyrinth, String players, String monsters, 
    int currentPlayer, boolean winner, String log){
        this.labyrinth = labyrinth;
        this.players = players;
        this.monsters = monsters;
        this.currentPlayer = currentPlayer;
        this.winner = winner;
        this.log = log;
    }

    public String getLabyrinth() { return labyrinth;}
    public String getPlayers() { return players; }
    public String getMonsters() { return monsters; }
    public int getCurrentPlayer() { return currentPlayer; }
    public boolean isWinner() { return winner; }
    public String getLog() { return log; }
}
