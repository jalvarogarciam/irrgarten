package irrgarten;


public class Weapon {
/*Esta clase representa las armas que utiliza el jugador en los ataques 
durante los combates.*/
    private float power;
    private int uses;
    
    /**************************************************************************/

    /*Constructor por defecto: 
    crea sin daño ni usos*/
    public Weapon(){this(0,0);}
    /*Constructor con especificación de cada parámetro */
    public Weapon(float power, int uses){
        this.power = power;
        this.uses = uses;
    }
    /**************************************************************************/




    /*Devuelve  la intensidad del ataque del jugador y resta un uso al arma 
    si ésta puede atacar (uses>0). */
    public float attack(){
        if (uses > 0){
            uses--;
            return power;
        }
        return 0;
    }

    /*Decide si un arma debe ser descartado con ayuda del Dado. */
    public boolean discard(){ return Dice.discardElement(uses); }

    //Devuelve una String del formato 'W[{power}, {uses}]' 
    @Override
    public String toString(){ return "W[" + power + ", " + uses + "]"; }
}
