public class Palette extends Bild
{
    public Kiste kiste;

    public Palette(int x, int y)
    {
        super(x, y, "images/palette.png");
        this.kiste = null;
    }

    public void nimmKiste(Kiste k)
    {
        this.kiste = k;
    }

    public void gibKisteAn(Stapler s)
    {
        s.nimmKiste(this.kiste);
        this.kiste = null;
    }
}
