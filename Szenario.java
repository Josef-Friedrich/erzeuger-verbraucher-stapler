import ea.edu.FensterE;

import java.util.Random;

public class Szenario
{
    private FensterE fenster;

    private Bild hintergrund;

    private Palette palette;

    private Stapler erzeuger;

    private Stapler verbraucher;

    public Szenario()
    {
        this.fenster = FensterE.getFenster(1000, 500);
        this.hintergrund = new Bild(500, 250, "images/hintergrund.gif");
        Random zufall = new Random();
        this.palette = new Palette(500, 333);
        int xL = zufall.nextInt(300);
        int xR = zufall.nextInt(300);
        this.erzeuger = new Stapler(-200 - xL, 293, 'l', this.palette); // -200
        this.verbraucher = new Stapler(3000 + xR, 293, 'r', this.palette); // 1200
        this.erzeuger.neueKiste();
        Thread t1 = new Thread(erzeuger);
        Thread t2 = new Thread(verbraucher);
        t1.start();
        t2.start();
    }

    public static void main(String[] args)
    {
        new Szenario();
    }
}
