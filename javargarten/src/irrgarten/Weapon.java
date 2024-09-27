

public class Weapon {
    /*Esta clase representa las armas que utiliza el jugador en los ataques 
    durante los combates.*/
    private float power;
    private int uses;
    
    public Weapon(float power, int uses){
        this.power = power;
        this.uses = uses;
    }

    /*Devuelve  la intensidad del ataque del jugador. 
    Si el arma aún tiene usos disponibles (uses > 0) se decrementa ese valor y 
    se devuelve el valor de power. 
    En otro caso el método devuelve 0.*/
    public float attack(){
        if (uses > 0){
            uses--;
            return power;
        }
        return 0;
    }

    /*Decide si un arma debe ser descartado con ayuda del Dado. */
    public boolean discard(){
        return Dice.discardElement(uses);
    }

    //Devuelve una String del formato 'W[{power}, {uses}]' 
    public String toString(){
        return "W[" + power + ", " + uses + "]";
    }

    public static void main(String[] args){
        float[] probabilidad = new float[5+1];
        for (int i = 0; i <= 5; i++){
            int descarta = 0;
            float veces=10000000;
            for (int j = 0; j < veces; j++){
                Weapon arma = new Weapon(5,i);
                if (arma.discard()) descarta++;
            }
            probabilidad[i] = (float)descarta/veces;
        }
        for (int i = 0; i <= 5; i++){
            System.out.println("p(discard() == true) -> " + probabilidad[i]*100 + "%");
        }
    }
}
