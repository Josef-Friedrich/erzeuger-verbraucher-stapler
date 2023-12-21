import java.util.Random;

public class Stapler implements Runnable
{
    private char seite;

    private Bild stapler;

    private Kiste kiste;

    private Random zufall;

    private Palette palette;

    private static final int speed = 10;

    @Override
    public void run()
    {
        for (int i = 0; i < 25; i++)
        {
            try
            {
                erzeugeUndVerbraucheNichtSynchronisiert();
                // erzeugeUndVerbraucheAktivesWarten();
                // erzeugeUndVerbrauchePassivesWarten();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void erzeugeUndVerbraucheNichtSynchronisiert()
    {
        fahreHin();
        if (seite == 'l')
        {
            ladeAb();
        }
        else
        {
            ladeAuf();
        }
        fahreWeg();
    }

    public void erzeugeUndVerbraucheAktivesWarten()
    {
        int counter = 0;
        fahreHin();
        while (seite == 'l' && this.kiste != null
                || seite == 'r' && this.kiste == null)
        {
            counter++;
            System.out.println("Aktives Warten " + counter + " Nano-Sekunde " + System.nanoTime());
            synchronized (palette)
            {
                if (seite == 'l')
                {
                    if (palette.kiste == null)
                    {
                        ladeAb();
                    }
                }
                else
                {
                    if (palette.kiste != null)
                    {
                        ladeAuf();
                    }
                }
            }
        }
        fahreWeg();
    }

    public void erzeugeUndVerbrauchePassivesWarten() throws Exception
    {
        fahreHin();
        synchronized (palette)
        {
            if (seite == 'l')
            {
                if (palette.kiste != null)
                {
                    palette.wait();
                }
                ladeAb();
            }
            else
            {
                if (palette.kiste == null)
                {
                    palette.wait();
                }
                ladeAuf();
            }
            palette.notify();
        }
        fahreWeg();
    }

    public Stapler(int x, int y, char seite, Palette p)
    {
        this.seite = seite;
        if (seite == 'r')
        {
            this.stapler = new Bild(x, y, "images/stapler_R.gif");
        }
        else if (seite == 'l')
        {
            this.stapler = new Bild(x, y, "images/stapler_L.gif");
        }
        this.kiste = null;
        this.palette = p;
        this.zufall = new Random();
    }

    public void nimmKiste(Kiste k)
    {
        this.kiste = k;
    }

    public void gibKisteAn(Palette p)
    {
        p.nimmKiste(this.kiste);
        this.kiste = null;
    }

    public void neueKiste()
    {
        this.kiste = new Kiste(this.stapler.nenneMx() + 106,
                this.stapler.nenneMy() - 21);
    }

    public void fahreHin()
    {
        if (this.seite == 'l')
        {
            while (this.stapler.nenneMx() < 250)
            {
                this.stapler.verschiebenUm(speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            while (this.stapler.nenneMx() > 750)
            {
                this.stapler.verschiebenUm(-speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(-speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void fahreWeg()
    {
        int n = this.zufall.nextInt(1000);
        if (this.seite == 'l')
        {
            while (this.stapler.nenneMx() > -200 - n)
            {
                this.stapler.verschiebenUm(-speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(-speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            while (this.stapler.nenneMx() < 1200 + n)
            {
                this.stapler.verschiebenUm(speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        if (seite == 'l')
        { // Code fuer Mehrfachverwendung eingefuegt
            this.neueKiste();
        }
        if (seite == 'r')
        {
            kiste = null;
        }
    }

    public void ladeAb()
    {
        if (this.seite == 'l')
        {
            while (this.stapler.nenneMx() < 394)
            {
                this.stapler.verschiebenUm(speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            this.gibKisteAn(this.palette);
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            while (this.stapler.nenneMx() > 250)
            {
                this.stapler.verschiebenUm(-speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(-speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            while (this.stapler.nenneMx() > 606)
            {
                this.stapler.verschiebenUm(-speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(-speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            this.gibKisteAn(this.palette);
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            while (this.stapler.nenneMx() < 750)
            {
                this.stapler.verschiebenUm(speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ladeAuf()
    {
        if (this.seite == 'l')
        {
            while (this.stapler.nenneMx() < 394)
            {
                this.stapler.verschiebenUm(speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            this.palette.gibKisteAn(this);
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            while (this.stapler.nenneMx() > 250)
            {
                this.stapler.verschiebenUm(-speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(-speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            while (this.stapler.nenneMx() > 606)
            {
                this.stapler.verschiebenUm(-speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(-speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            this.palette.gibKisteAn(this);
            try
            {
                Thread.sleep(300);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            while (this.stapler.nenneMx() < 750)
            {
                this.stapler.verschiebenUm(speed, 0);
                if (this.kiste != null)
                {
                    this.kiste.verschiebenUm(speed, 0);
                }
                try
                {
                    Thread.sleep(10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}