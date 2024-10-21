import irrgarten.Dice;
import irrgarten.GameState;
import irrgarten.LabDesign;
import irrgarten.Shield;
import irrgarten.Weapon;
import irrgarten.Labyrinth;
import irrgarten.Monster;
import irrgarten.Player;
import irrgarten.Game;
import irrgarten.enums.Directions;
import irrgarten.enums.Orientation;
import irrgarten.enums.GameCharacter;


public class TestP3 {
    public static void main(String[] args) {
        Labyrinth lab = new Labyrinth(10, 20, 9, 19);
        LabDesign.makeEmptyLab(lab);
        System.out.println(lab);
    }


}
