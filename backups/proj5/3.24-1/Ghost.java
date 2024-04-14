import java.awt.image.BufferedImage;
import java.awt.Graphics;

public class Ghost extends Sprite{
    private static BufferedImage ghostImages[] = null;
    private final int MAX_IMAGES_GHOST = 6;

    public Ghost() {
        this(0,0,40,40);
    }

    public Ghost(int x, int y, int w, int h) {
        super(x,y,w,h);
        // ONLY LOADS ONE IMAGE NOW -- CHANGE
        if (ghostImages == null) {
            // Initializes array of player images
            ghostImages = new BufferedImage[MAX_IMAGES_GHOST];
            for (int i = 0; i < MAX_IMAGES_GHOST; i++) {
                ghostImages[i] = View.loadImage("ghost" + i + ".png");
            }
        }
    }

    public void draw(Graphics g, int throwaway) {
        g.drawImage(ghostImages[0], x,y,w,h, null);
    }
    public boolean update() {
        return isValid;
    }

    public void eatGhost() {
        isValid = false;
    }

    @Override
    public boolean isGhost() {
        return true;
    }

    // Marshals this object into a JSON DOM
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }
}
