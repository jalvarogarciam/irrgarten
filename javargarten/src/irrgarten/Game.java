package irrgarten;

import java.util.ArrayList;

import irrgarten.enums.Directions;
import irrgarten.enums.GameCharacter;
import irrgarten.enums.Orientation;

public class Game {
    private String log; //registro de eventos

    private ArrayList<Player> players;   //jugadores
    private Player currentPlayer; //jugador actual
    private int currentPlayerIndex; //índice del jugador actual
    private ArrayList<Monster> monsters; //monstruos
    private Labyrinth labyrinth; //laberinto

    private static int MAX_ROUNDS=10; //número máximo de rondas

    /**************************************************************************/

    /*Constructor por defecto: crea un juego sin jugadores*/
    public Game(){this(0);}
    /*Constructor con especificación del número de jugadores*/
    public Game(int nPlayers){

        //crea el array de jugadores con el número de jugadores especificado
        this.players = new ArrayList<>(nPlayers);
        for (int i=0; i<nPlayers; i++)//crea jugadores con inteligencia y fuerza aleatorias
            players.add(
                new Player((char)(48+i), Dice.randomIntelligence(), Dice.randomStrength())
            );

        //crea tantos monstruos como jugadores
        this.monsters = new ArrayList<>(nPlayers);
        for (int i=0; i<nPlayers; i++)//crea monstruos con inteligencia y fuerza aleatorias
            monsters.add(
                new Monster("Monster#"+i, Dice.randomIntelligence(), Dice.randomStrength())
            );


        //crea un laberinto con el mismo número de filas y columnas que jugadores
        this.labyrinth = new Labyrinth(20, 20, 19, 19);

        configureLabyrinth(); //configura el laberinto

        this.currentPlayerIndex = Dice.randomPos(nPlayers); //elige un jugador aleatorio
        this.currentPlayer = players.get(currentPlayerIndex); //asigna el jugador actual
        this.log = "";
    }
    /**************************************************************************/

    //Getters
    public ArrayList<Player> getPlayers(){return new ArrayList<>(players);}
    public Player getCurrentPlayer(){return currentPlayer;}
    public ArrayList<Monster> getMonsters(){return new ArrayList<>(monsters);}
    public Labyrinth getLabyrinth(){return labyrinth;}
    public String getLog(){return log;}

    /* Genera una instancia de GameState integrando toda la información del estado del juego */
    public GameState getGameState(){
        return new GameState(
            labyrinth.toString(),
            players.toString(),
            monsters.toString(),
            currentPlayerIndex,
            finished(),
            log
        );
    }
    /* Configura el laberinto añadiendo bloques de obstáculos y monstruos. */
    private void configureLabyrinth(){
        LabDesign.makeEmptyLab(labyrinth); //crea un laberinto vacío
    }

    public boolean finished(){ return labyrinth.haveAWinner(); }

    /*Pasa al siguiente jugador */
    private void nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex+1) % players.size();
        currentPlayer = players.get(currentPlayerIndex);
    }

    /* Realiza un paso en el juego.
        Si el jugador actual está muerto, gestiona su resurrección.
        Si está vivo, intenta moverlo en la dirección preferida y realiza un 
        combate si hay un monstruo en la nueva casilla.
    Devuelve true si el juego ha terminado, false en caso contrario.
     */
    private boolean nextStep(Directions preferredDirection){
        log = ""; //limpia el registro de eventos
        if (!currentPlayer.dead()){ //si el jugador está muerto
            Directions direction = actualDirection(preferredDirection);
            if (direction != preferredDirection) // si no se puede mover en la dirección preferida
                logPlayerNoOrders();  //registra el evento

            Monster monster = labyrinth.putPlayer(direction, currentPlayer); //mueve al jugador

            if (monster == null) logNoMonster(); //si en la nueva casilla no hay monstruo, registra el evento
            else{ //si hay monstruo
                GameCharacter winner = combat(monster); //combate
                manageReward(winner); //gestiona la recompensa, registrando el evento
            }
        }else manageResurrection(); //si el jugador está muerto, gestiona la resurrección

        if (!finished()) {
            nextPlayer(); //pasa al siguiente jugador
            return false; //no ha terminado el juego
        }
        return true; //ha terminado el juego
    }

    /* Realiza un paso en el juego, moviendo al jugador actual en su dirección preferida si es posible.*/
    private Directions actualDirection(Directions preferredDirection){
        int currentRow = currentPlayer.getRow(), currentCol = currentPlayer.getCol();
        ArrayList<Directions> vMoves = labyrinth.validMoves(currentRow, currentCol); //movimientos válidos
        return currentPlayer.move(preferredDirection, vMoves); //devuelve la dirección en la que se moverá
    }

    /* Realiza un combate entre el jugador actual y el monstruo pasado como argumento.*/
    private GameCharacter combat(Monster monster){
        int rounds = 0; //rondas de combate
        GameCharacter winner = GameCharacter.PLAYER; //ganador por defecto

        boolean lose = monster.defend(currentPlayer.attack()); //el monstruo defiende (lectura adelantada)

        while (!lose && rounds < MAX_ROUNDS){ //mientras nadie pierda y no se alcance el máximo de rondas
            rounds++;
            winner = GameCharacter.MONSTER; //el monstruo gana por defecto

            lose = currentPlayer.defend(monster.attack()); //el jugador defiende
            if (!lose){ //si el jugador no pierde, ataca
                winner = GameCharacter.PLAYER; //el jugador gana por defecto
                lose = monster.defend(currentPlayer.attack()); //el monstruo defiende
            }
        }
        logRounds(rounds, MAX_ROUNDS); //registra las rondas
        return winner; //devuelve el ganador
    }

    /* Actualiza el estado del juego tras el combate y otorga la recompensa al ganador.*/
    private void manageReward(GameCharacter winner){
        if (winner == GameCharacter.PLAYER){ //si el jugador gana
            currentPlayer.receiveReward(); //añade la recompensa
            logPlayerWon(); //registra el evento
        }else{ logMonsterWon(); } //si el monstruo gana, registra el evento
    }

    /* Da la posibilidad al jugador actual de resucitar si el dado lo permite y lo registra.*/
    private void manageResurrection(){
        if (Dice.resurrectPlayer()){    //El dado decide si se resucita
            currentPlayer.resurrect();
            logResurrected();
        }else{ logPlayerSkipTurn(); }
    }

    /* Añade al final del atributo log el mensaje indicando que el jugador ha 
    ganado el combate y el indicador de nueva línea.*/
    private void logPlayerWon(){
        this.log += "Player won the combat.\n";
    }

    /*Añade al final del atributo log el mensaje indicando que el monstruo ha 
    ganado el combate y el indicador de nueva línea.*/
    private void logMonsterWon(){
        this.log += "Monster won the combat.\n";
    }

    /*Añade al final del atributo log el mensaje indicando que el jugador ha resucitado
    y el indicador de nueva línea.*/
    private void logResurrected(){
        this.log += "Player resurrected.\n";
    }

    /*Añade al final del atributo log el mensaje indicando que el jugador ha perdido el turno
    por estar muerto y el indicador de nueva línea.*/
    private void logPlayerSkipTurn(){
        this.log += "Player skipped turn.\n";
    }

    /*Añade al final del atributo log el mensaje indicando que el jugador no ha 
    seguido las instrucciones del jugador humano (no fue posible),
    y el indicador de nueva línea.*/
    private void logPlayerNoOrders(){
        this.log += "Player did not follow orders.\n";
    }

    /*Añade al final del atributo log el mensaje indicando que el jugador se ha 
    movido a una celda vacía o no le ha sido posible moverse, y el indicador de nueva linea */
    private void logNoMonster(){
        this.log += "No monster.\n";
    }

    /*Añade al final del atributo log el mensaje que se han producido rounds de 
    max rondas de combate, y el indicador de nueva línea.*/
    private void logRounds(int rounds,int max){
        this.log += "Rounds: "+rounds+"/"+max+".\n";
    }

}
