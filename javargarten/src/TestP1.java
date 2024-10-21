import irrgarten.Dice;
import irrgarten.GameState;
import irrgarten.Shield;
import irrgarten.Weapon;
import irrgarten.enums.Directions;
import irrgarten.enums.Orientation;
import irrgarten.enums.GameCharacter;


public class TestP1 {
    public static void main(String[] args) {

        System.out.println(".............Probando Weapon y Shield.............");
        // Creación de instancias de Weapon y Shield
        Weapon weapon = new Weapon(2.0f, 5);
        Shield shield = new Shield(3.0f, 4);

        // Usar métodos y probar toString
        System.out.println(weapon);
        System.out.println("Weapon attack: " + weapon.attack());
        System.out.println(weapon);

        System.out.println(shield);
        System.out.println("Shield protect: " + shield.protect());
        System.out.println(shield);

        System.out.println("    ..prueba de discard()..");
        // Probar discard
        float[] probabilidad = new float[5+1];  // MAX_USES = 5
        for (int i = 0; i <= 5; i++){           // MAX_USES = 5
            int descarta = 0;
            float veces=10000000;
            for (int j = 0; j < veces; j++){
                Weapon arma = new Weapon(2,i);
                if (arma.discard()) descarta++;
            }
            probabilidad[i] = (float)descarta/veces;
        }
        for (int i = 0; i <= 5; i++){          // MAX_USES = 5
            System.out.println("    p(discard() == true) para uses="+i+" -> " + probabilidad[i]*100 + "%");
        }

        System.out.println();System.out.println();
        System.out.println(".............Probando Enumerados.............");
        // Probar enumerados
        Directions dir = Directions.UP;
        GameCharacter character = GameCharacter.PLAYER;
        Orientation orientation = Orientation.VERTICAL;

        System.out.println("Direction: " + dir);
        System.out.println("Character: " + character);
        System.out.println("Orientation: " + orientation);

        System.out.println();System.out.println();
        System.out.println(".............Probando Dice.............");
        int times = 10000000;
        float [] promedios = new float[11];
        for (int i = 0; i < times; i++) {
            promedios[0] += Dice.randomPos(10)/(float)times;
            promedios[1] += Dice.whoStarts(5)/(float)times;
            promedios[2] += Dice.randomIntelligence()/(float)times;
            promedios[3] += Dice.randomStrength()/(float)times;
            promedios[4] += (Dice.resurrectPlayer() ? 1 : 0)/(float)times;  
            promedios[5] += Dice.weaponsReward()/(float)times;
            promedios[6] += Dice.shieldsReward()/(float)times;
            promedios[7] += Dice.healthReward()/(float)times;
            promedios[8] += Dice.weaponPower()/(float)times;
            promedios[9] += Dice.shieldPower()/(float)times;
            promedios[10] += Dice.usesLeft()/(float)times;
        }
        System.out.println("Promedios:");   
        System.out.println("randomPos:            " + promedios[0] + " ≈ 5");
        System.out.println("whoStarts:            " + promedios[1] + " ≈ 2.5");
        System.out.println("randomIntelligence:   " + promedios[2] + " ≈ 5");
        System.out.println("randomStrength:       " + promedios[3] + " ≈ 5");
        System.out.println("resurrectPlayer:      " + promedios[4] + " ≈ 0.3");
        System.out.println("weaponsReward:        " + promedios[5] + " ≈ 1");
        System.out.println("shieldsReward:        " + promedios[6] + " ≈ 1.5");
        System.out.println("healthReward:         " + promedios[7] + " ≈ 2.5");
        System.out.println("weaponPower:          " + promedios[8] + " ≈ 1.5");
        System.out.println("shieldPower:          " + promedios[9] + " ≈ 1");
        System.out.println("usesLeft:             " + promedios[10] + " ≈ 2.5");

    }


}
