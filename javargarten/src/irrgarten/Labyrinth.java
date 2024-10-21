package irrgarten;

import java.util.ArrayList;

import irrgarten.enums.Directions;
import irrgarten.enums.Orientation;



public class Labyrinth {

    private int nRows, nCols;
    private int exitRow, exitCol;

    //Tableros de monstruos, jugadores y laberinto
    private Monster[][] monsters;
    private Player[][] players;

    private char[][] labyrinth;
    private static final char //caracteres para representar el tablero labyrinth
        BLOCK_CHAR='X',
        EMPTY_CHAR='-',
        MONSTER_CHAR='M',
        COMBAT_CHAR='C',
        EXIT_CHAR='E';
    private static final int ROW=0, COL=1;



    /**************************************************************************/

    /*Constructor por defecto: 
    Crea un laberinto con todos los valores por defecto (0)*/
    public Labyrinth(){this(0,0,0,0);}
    /*Constructor con especificación de número de cols y filas, y número de exitRow y exitCol*/
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.nRows=nRows;   this.nCols=nCols;
        this.exitCol=exitCol;   this.exitRow=exitRow;
        monsters = new Monster[nRows][nCols];
        players = new Player[nRows][nCols];
        labyrinth = new char[nRows][nCols]; //por defecto todas las casillas están vacías
        for (int i=0; i<nRows; i++) //rellena con empty_chars
            for (int j=0; j<nCols; j++){
                labyrinth[i][j]=EMPTY_CHAR;
                players[i][j] = null; monsters[i][j] = null;
            }
        labyrinth[exitRow][exitCol]=EXIT_CHAR; //indica la salida
    }
    /**************************************************************************/

    //Getters
    public int getRows(){return nRows;}
    public int getCols(){return nCols;}

    /* Reparte a los jugadores (pasados como argumento) en posiciones aleatorias del laberinto*/
    public void spreadPlayers(ArrayList<Player> players){
        for (Player p : players){
            int[] pos = randomEmptyPos();
            putPlayer2D(-1,-1,pos[0], pos[1], p);
        }
    }

    /* Muéve al jugador en la dirección indicada y devuelve al monstruo que hay en la nueva
    posición en caso de que exista uno.
    pre: el jugador se puede mover en dicha dirección (validMoves(row, col) contiene direction)
    */
    public Monster putPlayer(Directions direction, Player player){
        int oldRow=player.getRow(), oldCol=player.getCol(); //la posición actual del jugador
        int[] newPos=dir2Pos(oldRow, oldCol, direction); //calcula la nueva posición
        return putPlayer2D(oldRow, oldCol, newPos[0], newPos[1], player); //coloca al jugador en la nueva posición
    }

    /* Añade un bloque en el laberinto en la posición indicada por startRow y startCol,
    con la longitud indicada y en la orientación indicada mientras sea posible */
    public void addBlock(Orientation orientation, int startRow,int startCol, int length){
        int incRow=0, incCol=0; // Calcula la dirección en la que se  van a incrementar las casillas
        if (orientation==Orientation.VERTICAL)  incRow=1;
        else incCol=1;

        int row=startRow, col=startCol; //añade los bloques si es posible
        while (posOK(row, col) && emptyPos(row, col) && length>0){
            this.labyrinth[row][col]=BLOCK_CHAR; //añade el bloque 
            length--; row+=incRow; col+=incCol;  //itera
        }
    }

    /* Si la posición suministrada está dentro del tablero y está vacía, 
    introduce al monstruo en dicha posición y le actualiza la posicion, devolviendo true
    si no está vacía devuelve false*/
    public boolean/*void*/ addMonster(int row, int col, Monster monster){
        assert row>=0 && row<nRows && col>=0 && col<nCols;
        if (monsters[row][col]==null){
            labyrinth[row][col]=MONSTER_CHAR;//anota la presencia de un monstruo
            monsters[row][col]=monster; //guarda la referencia del monstruo
            monster.setPos(row, col); //indica al monstruo su posición
            return true;
        }return false;
    }


    /*Devuelve true si hay un jugador en la casilla de salida */
    boolean haveAWinner(){return players[exitRow][exitCol] != null ;}//por defecto todas las casillas apuntan a null

    /* Devuelve un array con las direcciones en las que se puede mover el jugador*/
    public ArrayList<Directions> validMoves(int row,int col){
        ArrayList<Directions> output = new ArrayList<Directions>();
        if (canStepOn(row+1, col))    output.add(Directions.DOWN);
        if (canStepOn(row-1, col))    output.add(Directions.UP);
        if (canStepOn(row, col+1))    output.add(Directions.RIGHT);
        if (canStepOn(row, col-1))    output.add(Directions.LEFT);
        return output;
    }


    /* Genera una representación del estado completo del laberinto en forma de String,
    usando el tablero labyrinth.
    */
    @Override
    public String toString(){
        String output= new String("");
        for (int i=0; i<nRows; i++){
            for (int j=0; j<nCols; j++){
                output+=labyrinth[i][j];
            }
            output+="\n";
        }
        return output;
    }




    /* Devuelve true si la posición proporcionada está dentro del laberinto */
    private boolean posOK(int row, int col){ return row>=0 && row<nRows && col>=0 && col<nCols; }
    /* Devuelve true si la posición suministrada está vacía */
    private boolean emptyPos(int row, int col){ return posOK(row, col) && labyrinth[row][col]==EMPTY_CHAR; }
    /*Devuelve true si la posición suministrada está ocupada por un monstruo */
    private boolean monsterPos(int row, int col){ return posOK(row, col) && labyrinth[row][col]==MONSTER_CHAR; }
    /*Devuelve true si la posición suministrada es la posición de salida */
    private boolean exitPos(int row, int col){ return posOK(row, col) && labyrinth[row][col]==EXIT_CHAR; }
    /*Devuelve true si la posición suministrada es de combate */
    private boolean combatPos(int row, int col){ return posOK(row, col) && labyrinth[row][col]==COMBAT_CHAR; }
    /*Indica si la posición suministrada está dentro del laberinto y se
    corresponde con una de estas tres opciones: 
        casilla vacía, casilla donde habita un monstruo o salida */
    private boolean canStepOn(int row, int col){
        if (!posOK(row, col)) return false;
        return exitPos(row, col)||monsterPos(row, col)||emptyPos(row, col);
    }

    /*Calcula la posición del laberinto ([{row},{col}]) a la que se llegaría si 
    desde la posición suministrada se avanza en la dirección pasada como parámetro.
    Puede dar posiciones en las que no se puede mover el jugador o directamente no existen*/
    private int[] dir2Pos(int row, int col, Directions direction){
        switch (direction){ //calcula el incremento en la fila y columna según la dirección
            case LEFT: row--; break; //retrocede en la fila
            case RIGHT: row++; break; //avanza en la fila
            case UP: col--; break;   //retrocede en la columna
            case DOWN: col++; break;  //avanza en la columna
        }
        int[] pos = {row, col};
        return pos;
    }


    /*Devuelve una posición aleatoria ([{row},{col}]) vacía del laberinto */
    private int[] randomEmptyPos(){
        int[] pos = {Dice.randomPos(nRows), Dice.randomPos(nCols)}; //lectura adelantada
        while (!emptyPos(pos[ROW], pos[COL])) //mientras no esté vacía
            pos[0]=Dice.randomPos(nRows); pos[1]=Dice.randomPos(nCols); //genera otra
        return pos;
    }

    /*Si en esa posición el laberinto estaba indicando el estado de combate, 
    el estado de esa casilla del laberinto pasa a indicar que simplemente hay un monstruo. 
    En otro caso, el estado de esa casilla del laberinto pasa a indicar que está vacía.
    Este método es llamado cuando un jugador abandona una casilla
    */
    private void updateOldPos(int row, int col){
        assert  posOK(row, col);
        if (combatPos(row, col)) labyrinth[row][col]=MONSTER_CHAR;
        else labyrinth[row][col]=EMPTY_CHAR;
    }


    /* Coloca al jugador en la posición nueva y devuelve el monstruo con el que 
    se va a combatir si la casilla nueva está ocupada por un monstruo.*/
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output=null;

        if (canStepOn(row, col)){ 

            if (posOK(oldRow, oldCol)){ //observa si el jugador estaba en el laberinto
                Player p = players[oldRow][oldCol]; 
                if (player == p){
                    updateOldPos(oldRow,oldCol);  //actualiza la posición anterior de labyrinth
                    players[oldRow][oldCol]=null; //actualiza la posición anterior de players
                }
            }

            if (monsterPos(row,col)){  //observa si hay un monstruo en la casilla nueva
                labyrinth[row][col]=COMBAT_CHAR; //indica que hay combate (player vs monstruo)
                output = monsters[row][col]; //devuelve el monstruo

            }else{ //indica la presencia del jugador en labyrinth
                labyrinth[row][col] = player.getNumber();
            }

            players[row][col] = player; //actualiza la posición de players
            player.setPos(row, col); //actualiza la posición del jugador
        }

        return output;
    }

}