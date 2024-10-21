package irrgarten;

public class Shield {
/*Esta clase representa los escudos que utiliza el jugador cuando se defiende de 
un ataque de un monstruo.*/

    private float protection;
    private int uses;



    /**************************************************************************/

    /*Constructor por defecto: crea un escudo sin protección ni usos*/
    public Shield(){this(Dice.shieldPower(), Dice.MAX_USES());}
    /*Constructor con especificación de cada parámetro */
    public Shield(float protection, int uses){
        this.protection = protection;
        this.uses = uses;
    }
    /**************************************************************************/


    /*Devuelve  la intensidad de la defensa del jugador y resta un uso al escudo 
    si éste puede proteger (uses>0). */
    public float protect(){
        if (uses > 0){
            uses--;
            return protection;
        }
        return 0;
    }

    /*Decide si un escudo debe ser descartado con ayuda del Dado. */
    public boolean discard(){ return Dice.discardElement(uses); }

    //Devuelve una String del formato 'S[{power}, {uses}]'
    @Override
    public String toString(){ return "S[" + protection + ", " + uses + "]"; }
}
