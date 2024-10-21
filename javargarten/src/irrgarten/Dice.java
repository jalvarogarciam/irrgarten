package irrgarten;

import java.util.Random;

public class Dice {
/*
Esta clase tiene la responsabilidad de tomar todas las decisiones que dependen 
del azar en el juego.

Es como una especie de dado, pero algo más sofisticado, ya que no proporciona 
simplemente un número del 1 al 6, sino decisiones concretas en base a una serie 
de probabilidades establecidas.

Dado que se considera que no tiene sentido que existan distintas instancias 
de esta clase, con estados distintos, toda la funcionalidad requerida la 
proporciona la propia clase.*/

    //número máximo de usos de armas y escudos
    static private final int MAX_USES = 5 ;

    //valor máximo para la inteligencia de jugadores y monstruos
    static private final float MAX_INTELLIGENCE = 10.0f; 

    //valor máximo para la fuerza de jugadores y monstruos
    static private final float MAX_STRENGTH = 10.0f; 

    //probabilidad de que un jugador sea resucitado en cada turno
    static private final float RESURRECT_PROB = 0.3f;

    //numero máximo de armas recibidas al ganar un combate
    static private final int WEAPONS_REWARD = 2;

    //numero máximo de escudos recibidos al ganar un combate
    static private final int SHIELDS_REWARD = 3;

    //numero máximo de unidades de salud recibidas al ganar un combate
    static private final int HEALTH_REWARD = 5;

    //máxima potencia de las armas
    static private final int MAX_ATTACK = 3;

    //máxima potencia de los escudos
    static private final int MAX_SHIELD = 2;

    private static Random generator = new Random();


    //Getters de las constantes 
    public static int MAX_USES(){return MAX_USES;}
    public static float MAX_INTELLIGENCE(){return MAX_INTELLIGENCE;}
    public static float MAX_STRENGTH(){return MAX_STRENGTH;}
    public static float RESURRECT_PROB(){return RESURRECT_PROB;}
    public static int WEAPONS_REWARD(){return WEAPONS_REWARD;}
    public static int SHIELDS_REWARD(){return SHIELDS_REWARD;}
    public static int HEALTH_REWARD(){return HEALTH_REWARD;}
    public static int MAX_ATTACK(){return MAX_ATTACK;}
    public static int MAX_SHIELD(){return MAX_SHIELD;}

    /*Devuelve un número de fila o columna aleatoria siendo el valor del 
    parámetro el número de filas o columnas del tablero. 
    La fila y la columna de menor valor tienen como índice el número cero. */
    public static int randomPos(int max){
        return generator.nextInt(max); //devuelve un número entre 0 y max-1
    }

    /*Devuelve el índice del jugador que comenzará la partida. 
    El parámetro representa el número de jugadores en la partida. Los jugadores 
    se numeran comenzando con el número 0.*/
    public static int whoStarts(int nplayers){
        return generator.nextInt(nplayers); //devuelve un número entre 0 y npalyers-1
    }

    /*Devuelve un valor aleatorio de inteligencia del intervalo [0, MAX_INTELLIGENCE]*/
    public static float randomIntelligence(){
        return generator.nextFloat() * MAX_INTELLIGENCE;
        // nextFloat no acepta argumentos, devuelve un número entre 0.0 y 1.0
    }
    /*Devuelve una valor aleatorio de fuerza del intervalo [0, ]*/
    public static float randomStrength(){
        return generator.nextFloat() * MAX_STRENGTH;
    }
    /*Indica si un jugador muerto debe ser resucitado o no.*/
    public static boolean resurrectPlayer(){
        return generator.nextFloat() <= RESURRECT_PROB;
    }
    /*Indica la cantidad de armas que recibirá el jugador por ganar el combate.
    Será un número aleatorio comprendido en [0,WEAPONS_REWARD].*/
    public static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD + 1);
    }
    /*Indica la cantidad de escudos que recibirá el jugador por ganar el combate.
    Será un número aleatorio comprendido en [0,SHIELDS_REWARD].*/
    public static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD + 1);
    }
    /*Indica la cantidad de unidades de salud que recibirá el jugador por ganar el combate.
    Será un número aleatorio comprendido en [0,HEALTH_REWARD].*/
    public static int healthReward(){
        return generator.nextInt(HEALTH_REWARD + 1);
    }
    /*Devuelve un valor aleatorio en el intervalo [0, MAX_ATTACK]*/
    public static float weaponPower(){
        return generator.nextFloat() * MAX_ATTACK;
    }
    /*Devuelve un valor aleatorio en el intervalo [0, MAX_SHIELD]*/
    public static float shieldPower(){
        return generator.nextFloat() * MAX_SHIELD;
    }
    /*Devuelve el número de usos (aleatorio) que se asignará a un arma o escudo,
    comprendido en el intervalo [0, MAX_USES].*/
    public static int usesLeft(){
        return generator.nextInt(MAX_USES + 1);
    }
    /*Devuelve la cantidad (aleatoria) de competencia aplicada,
    comprendida en el intervalo [0, competence]*/
    public static float intensity(float competence){
        return generator.nextFloat() * competence;
    }
    /*Devuelve true con una probabilidad inversamente proporcional a lo cercano 
    que esté el parámetro del número máximo de usos que puede tener un arma o escudo. 
    Como casos extremos, si el número de usos es el máximo devolverá false y 
    si es 0 devolverá true. 
    Es decir, las armas o escudos con más usos posibles es menos probable que 
    sean descartados.*/
    public static boolean discardElement(int usesLeft){
        return generator.nextFloat() >= (float)usesLeft/(float)(MAX_USES);
    }

}
