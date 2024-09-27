public class Shield {
/*Esta clase representa los escudos que utiliza el jugador cuando se defiende de 
un ataque de un monstruo.*/

    private float protection;
    private int uses;

    public Shield(float protection, int uses){
        this.protection = protection;
        this.uses = uses;
    }

    /*Devuelve  la intensidad de la defensa del jugador. 
    Si el escudo aún tiene usos disponibles (uses > 0) se decrementa ese valor y 
    se devuelve el valor de protection. 
    En otro caso el método devuelve 0.*/
    public float attack(){
        if (uses > 0){
            uses--;
            return protection;
        }
        return 0;
    }

    /*Decide si un escudo debe ser descartado con ayuda del Dado. */
    public boolean discard(){
        return Dice.discardElement(uses);
    }

    //Devuelve una String del formato 'S[{power}, {uses}]' 
    public String toString(){
        return "S[" + protection + ", " + uses + "]";
    }
}
