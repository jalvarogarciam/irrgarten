package irrgarten;

import irrgarten.enums.Orientation;

public class LabDesign {
    public static void makeEmptyLab(Labyrinth lab){
        // Marco exterior del laberinto
        lab.addBlock(Orientation.HORIZONTAL, 0, 2, 20);  // Borde superior
        lab.addBlock(Orientation.HORIZONTAL, 9, 0, 18); // Borde inferior
        lab.addBlock(Orientation.VERTICAL, 1, 0, 10);    // Borde izquierdo
        lab.addBlock(Orientation.VERTICAL, 1, 19, 10);   // Borde derecho

        lab.addBlock(Orientation.HORIZONTAL, 2, 2, 7); 
        lab.addBlock(Orientation.HORIZONTAL, 2, 10, 7);
        lab.addBlock(Orientation.HORIZONTAL, 3, 2, 1);
        lab.addBlock(Orientation.VERTICAL, 3, 8, 3);
        lab.addBlock(Orientation.HORIZONTAL, 3, 6, 2);
        lab.addBlock(Orientation.VERTICAL, 2, 17, 6);
        lab.addBlock(Orientation.HORIZONTAL, 4, 9, 7);
        lab.addBlock(Orientation.HORIZONTAL, 6, 10, 2);
        lab.addBlock(Orientation.HORIZONTAL, 7, 10, 1);
        lab.addBlock(Orientation.HORIZONTAL, 6, 13, 4);
        lab.addBlock(Orientation.HORIZONTAL, 7, 14, 3);
        lab.addBlock(Orientation.HORIZONTAL, 8, 12, 1);
        lab.addBlock(Orientation.HORIZONTAL, 5, 2, 3);
        lab.addBlock(Orientation.HORIZONTAL, 4, 4, 1);
        lab.addBlock(Orientation.HORIZONTAL, 7, 2, 3);
        lab.addBlock(Orientation.HORIZONTAL, 5, 9, 3);
        lab.addBlock(Orientation.HORIZONTAL, 7, 6, 3);
        lab.addBlock(Orientation.HORIZONTAL, 8, 6, 3);
        lab.addBlock(Orientation.HORIZONTAL, 6, 3, 2);
        lab.addBlock(Orientation.VERTICAL, 5, 6, 2);

    }
}
