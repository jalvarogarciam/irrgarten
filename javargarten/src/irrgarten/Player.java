package irrgarten;

import java.util.ArrayList;

import irrgarten.enums.Directions;

public class Player {
    private String name;
    private char number;
    private float intelligence, strength, health;
    private int row, col;
    private int consecutiveHits=0;
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;

    private static final int MAX_WEAPONS=2, MAX_SHIELDS=3;
    private static final int INITIAL_HEALTH=10;
    private static final int HITS2LOSE=3;


    /**************************************************************************/

    /*Constructor por defecto: 
    crea un jugador con nombre por defecto (Player#x) y con cualidades nulas*/
    public Player(){this('x',0,0);}
    /*Constructor con especificación de número, inteligencia y fuerza */
    public Player(char number, float intelligence, float strength){
        this.number = number;
        this.name = "Player#"+this.number;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.row = this.col= -1;    //luego se resasignará en otra clase
        this.weapons = new ArrayList<Weapon>();
        this.shields = new ArrayList<Shield>();
    }
    /**************************************************************************/

    //Getters
    public String getName(){return this.name;}
    public char getNumber(){return this.number;}
    public float getHealth(){return this.health;}
    public float getStrength(){return this.strength;}
    public float getIntelligence(){return this.intelligence;}
    public int getConsecutiveHits(){return this.consecutiveHits;}
    public int getRow(){return this.row;}
    public int getCol(){return this.col;}
    public ArrayList<Weapon> getWeapons(){return new ArrayList<>(this.weapons);}
    public ArrayList<Shield> getShields(){return new ArrayList<>(this.shields);}

    //setter de row y col
    public void setPos(int row, int col){this.row=row; this.col=col;}

    public boolean dead(){return this.health == 0;}//Devuelve true si el jugador está muerto.


    /*Reinicia al jugador:
        Hace que las listas de armas y escudos sean listas vacías, 
        que el nivel de salud sea el determinado para el inicio del juego y 
        el número consecutivo de impactos cero.*/
    public void resurrect(){ 
        this.health= INITIAL_HEALTH;                //restablece la salud
        this.weapons.clear(); this.shields.clear(); //vacía los arrays de armas y escudos
    }





    /* Recibe una recompensa de armas, escudos y salud de acuerdo a los valores que arroje el dado.*/    public void receiveReward(){
        int wReward = Dice.weaponsReward(), sReward = Dice.shieldsReward();
        for (int i = 0; i < wReward; i++) { receiveWeapon(newWeapon()); } //recibe armas
        for (int i = 0; i < sReward; i++) { receiveShield(newShield()); } //recibe escudos
        this.health+= Dice.healthReward(); //recibe salud
    }


    /* Calcula la suma de la fuerza del jugador y la suma de lo aportado por sus armas */
    public float attack(){ return sumWeapons() + this.strength; }

    /* Delega su funcionalidad en el método manageHit.*/
    public boolean defend(float receivedAttack){ return manageHit(receivedAttack); }

    /* Devuelve la direccion pasada si es válida, si no, devuelve la primera 
    dirección válida de la lista.*/
    public Directions move(Directions direction, ArrayList<Directions> validMoves){ 
        if (validMoves.size()>0 && !validMoves.contains(direction))
            return validMoves.get(0);                                     // Supuestamente debería ser aleatoria...
        return direction;
    }

    /* Genera un representación del estado completo del jugador en forma de String:
     P[{Nombre}, {intelligence}, {strenght}, {health}, ({row},{col}), {consecutiveHits}]
    */
    @Override
    public String toString(){
        return "P[" + name+", " + intelligence+", " + strength+", " + health+", " + 
                '('+row +", "+col+')'+", "+ consecutiveHits+"]";
    }





    /* Genera una nueva instancia de arma. 
    Los parámetros necesarios para construir el arma se le solicitarán al dado.*/
    private Weapon newWeapon(){ return new Weapon(Dice.weaponPower(),Dice.MAX_USES()); }

    /* Genera una nueva instancia de escudo. 
    Los parámetros necesarios para construir el escudo se le solicitarán al dado.*/
    private Shield newShield(){ return new Shield(Dice.shieldPower(),Dice.MAX_USES()); }

    /* Calcula la suma de la inteligencia con el aporte de los escudos (sumShields)*/
    private float defensiveEnergy(){ return sumShields() + intelligence; }

    /*Devuelve la suma del ataque de todas sus armas (usándolas).*/
    private float sumWeapons(){
        float total = 0;
        for (Weapon weapon:this.weapons){ total += weapon.attack(); }
        return total;
    }

    /*Devuelve la suma de la defensa de todos sus escudos (usándolos)*/
    private float sumShields(){
        float total = 0;
        for (Shield shield:this.shields){ total += shield.protect(); }
        return total;
    }

    /* Incrementa en una unidad el contador de impactos consecutivos.*/
    private void incConsecutiveHits(){this.consecutiveHits++;}
    /* Fija el valor del contador de impactos consecutivos a cero.*/
    private void resetHits(){ this.consecutiveHits=0; }

    /*Recibe un arma si tiene espacio y escarta las armas gastadas*/
    private void receiveWeapon(Weapon w){
        for (int i = 0; i < weapons.size(); i++) // Descarta armas gastadas
            if (weapons.get(i).discard()){
                weapons.remove(i);
                i--; 
            }
        if (weapons.size() < MAX_WEAPONS){ weapons.add(w); } // la recoge si puede
    }

    /*Recibe un escudo si tiene espacio y escarta los escudos gastados*/
    private void receiveShield(Shield s){
        for (int i = 0; i < shields.size(); i++) // Descarta escudos gastados
            if (shields.get(i).discard()){
                shields.remove(i);
                i--; 
            }
        if (shields.size() < MAX_SHIELDS){ shields.add(s); } // lo recoge si puede
    }

    /* Decrementa en una unidad el atributo que representa la salud del jugador*/
    private void gotWounded(){ if (health != 0){this.health--;} }

    /* Recibe un ataque y devuelve true si el jugador muere en el proceso*/
    private boolean manageHit(float receivedAttack){
        boolean lose; // indica si el jugador ha perdido
        if (defensiveEnergy()<receivedAttack){  //si el ataque es mayor que la defensa
            gotWounded(); //el jugador recibe un daño
            incConsecutiveHits(); //incrementa el contador de impactos consecutivos
        }else {resetHits();} //si se defiende, el contador de impactos consecutivos se reinicia

        if (this.consecutiveHits==HITS2LOSE || dead()){ //si el jugador ha recibido 3 impactos consecutivos o está muerto
            resetHits(); //se reinicia el contador de impactos consecutivos
            lose = true; //el jugador ha perdido
        }else{lose = false;} //el jugador no ha perdido
        return lose;
    }
}
