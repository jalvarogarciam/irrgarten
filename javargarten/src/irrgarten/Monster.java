package irrgarten;

public class Monster {

    private String name;
    private float intelligence, strength, health;
    private int row, col;

    private static final int INITIAL_HEALTH=5;

    /**************************************************************************/

    /*Constructor por defecto: 
    crea un monstruo con nombre por defecto (x) y con cualidades nulas*/
    public Monster(){ this("x", 0, 0); }
    /*Constructor con especificación de nombre, inteligencia y fuerza */
    public Monster(String name, float intellingence, float strenght){
        this.name = name;
        this.intelligence = intelligence;
        this.strength = strength;
        this.health = INITIAL_HEALTH;
        this.row = this.col= -1;    //luego se resasignará en otra clase
    }

    //Getters
    public int getRow(){return this.row;}
    public int getCol(){return this.col;}
    public String getName(){return this.name;}
    public float getHealth(){return this.health;}
    public float getStrength(){return this.strength;}
    public float getIntelligence(){return this.intelligence;}

    //setter de row y col
    public void setPos(int row, int col){ 
        if (row>=0 && col>=0){ this.row = row; this.col=col; }
    }


    //Devuelve true si el monstruo está muerto.
    public boolean dead(){return this.health == 0;}


    /*Devuelve la fuerza del ataque del monstruo (aleatoria entre 0 y su fuerza)*/
    public float attack(){return Dice.intensity(this.strength);}

    /*Recibe un ataque y devuelve true si el monstruo muere en el proceso*/
    public boolean defend(float receivedAttack){
        boolean isDead = dead(); //si ya está muerto, no puede defenderse
        if (!isDead){ //si no está muerto, se defiende
            float defensiveEnergy = Dice.intensity(this.intelligence); //energía defensiva
            if (receivedAttack > defensiveEnergy){ //si el ataque es mayor que la defensa
                gotWounded(); //el monstruo recibe un daño
                isDead = dead(); //se actualiza el estado de vida del monstruo
            }
        }
        return isDead;
    }

    /* Genera un representación del estado completo del monstruo en forma de String:
     M[{Nombre}, {intelligence}, {strenght}, {health}, ({row},{col})]
    */
    @Override
    public String toString(){
        return "M[" + name+", " + intelligence+", " + strength+", " + 
                health+", " + '('+row +", "+col+')'+ "]";
    }

    /*Decrementa en una unidad el atributo que representa la salud del monstruo.*/
    private void gotWounded(){this.health--;}


}
