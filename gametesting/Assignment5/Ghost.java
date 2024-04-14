// Name: 					Evan Meyers
// Date: 					3/9/2024
// Assignment Description: 	Implement polymorphism, enemies, and objective points

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.Random;

public class Ghost extends Sprite{
    private static BufferedImage ghostImages[][] = null;
    private static BufferedImage deathImages[] = null;
    private static BufferedImage skeleImages[] = null;
    private final int MAX_IMAGES_PER_DIRECTION = 2, MAX_DEATH_IMAGES = 6, MAX_SKELE_IMAGES = 13;
    private int frameIteration = 1, direction = 0, prevX, prevY;
    private double speed = 5;
    private boolean isDying = false;    // When false, activates death animation sequence
    private int deathTimer = 50;        // Timer used for death animation
    private int ghostType;              // Determines ghost color
    private boolean scorable = true;    // Prevents duplicate scoring in model
    private Random random = new Random();
    private boolean edible;
    private int edibleTime = 150, edibleTimer = edibleTime;

    public Ghost() {
        this(0,0,27,40);
    }

    public Ghost(int x, int y, int w, int h) {
        super(x,y,w,h);
        direction = 0;
        ghostType = random.nextInt(4);


        // // Initializes ghost animation images
        // if (skeleImages == null) {
        //     skeleImages = new BufferedImage[MAX_SKELE_IMAGES + 1];
        //     for (int i = 1; i < MAX_SKELE_IMAGES + 1; i++) {
        //         skeleImages[i] = View.loadImage("skeleton" + i + ".png");
        //     }
            
        // }
        // Initializes ghost animation images
        if (ghostImages == null) {
            ghostImages = new BufferedImage[4][MAX_IMAGES_PER_DIRECTION * 4 * 4];
            for (int j = 0; j < 4; j++) {
                for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++) {
                    ghostImages[j][i] = View.loadImage(j + "-" + i + ".png");
                }
            }
        }

        // Initializes death animation images
        if (deathImages == null) {
            deathImages = new BufferedImage[MAX_DEATH_IMAGES];
            for (int i = 1; i < MAX_DEATH_IMAGES; i++) {
                deathImages[i] = View.loadImage("ghost" + i + ".png");
            }
        }
    }

    public void draw(Graphics g, int yFixed) {
        // Allows for the player to teleport horizontally
        // ----------------------------------------------------
        if (x > Game.MAP_WIDTH) {
            x = Game.MAP_WIDTH - 800;
        }
        else if (x < Game.MAP_WIDTH - 800) {
            x = Game.MAP_WIDTH;
        }
        if(isDying) {
            if(deathTimer > 40){
                g.drawImage(deathImages[1], x, y + yFixed, w, h, null);            
            }
            else if (deathTimer > 30) {
                g.drawImage(deathImages[2], x, y + yFixed, w, h, null);            
            }
            else if (deathTimer > 20) {
                g.drawImage(deathImages[3], x, y + yFixed, w, h, null);            
            }
            else if (deathTimer > 10) {
                g.drawImage(deathImages[4], x, y + yFixed, w, h, null);            
            }
            else {
                g.drawImage(deathImages[5], x, y + yFixed, w, h, null);

            }
        }
        else {
            // g.drawImage(skeleImages[frameIteration], x, y + yFixed, w, h, null);

            g.drawImage(ghostImages[ghostType][MAX_IMAGES_PER_DIRECTION * direction + frameIteration], x, y + yFixed, w, h, null);
        }

    }
    public boolean update() {
        // Makes fruit move
        // --------------------------------
        if (!isDying) {
            move();
        }
        // --------------------------------
        if(isDying) {
            deathTimer--;
            if(deathTimer <= 0) {
                isValid = false;
            }
        }
        if(edible) {
            edibleTimer--;
            if(edibleTimer <= 0) {
                edibleTimer = edibleTime;
                edible = false;
            }
            System.out.println("Time left: " + edibleTimer);

        }
        return isValid;
    }

    public void eatGhost() {
        isDying = true;
        scorable = false;
    }
    
    public void edible() {
        edible = true;
    }

    public boolean eatPlayer() {
        return !edible;
    }
    public void move() {
        iterateFrame();
        prevX = x;
        prevY = y;


        if(direction == 0) {
            x-=speed;
        }
        if(direction == 1) {
            y-=speed;
        }
        if(direction == 2) {
            x+=speed;
        }
        if(direction == 3) {
            y+=speed;
        }
    }

    public void iterateFrame() {
        frameIteration++;
        // Sets current frame to 1 if it exceeds 2
        if(frameIteration > MAX_IMAGES_PER_DIRECTION) {
            frameIteration = 1;
        }
    }

    // Prevents player from being drawn within a collision
    public void getOutOfWall(Wall wall) {

        // Right
        if (x + w >= wall.getX() && prevX + w <= wall.getX()) {
            x = wall.getX() - w;
        }
        // Left
        if (x <= wall.getX() + wall.getW() && prevX >= wall.getX() + wall.getW()) {
            x = wall.getX() + wall.getW();
        }
        
        if ((y + h >= wall.getY() && y + h <= wall.getY() + wall.getH()) && prevY + h <= wall.getY()) {
            y = wall.getY() - h;
        }
        if ((y >= wall.getY() && y <= wall.getY() + wall.getH()) && prevY >= wall.getY() + wall.getH()) {

            y = wall.getY() + wall.getH();
        }
        // Randomly change direction
        int tempDirection = direction;
        while (tempDirection == direction) {
            direction = random.nextInt(4);
        }
    }

    public boolean scorable() {
        return scorable;
    }

    @Override
    public boolean isGhost() {
        return true;
    }

    @Override
    public boolean isMoving() {
        return true;
    }

    // Custom toString() method used for debugging
    @Override 
    public String toString()
    {
        return "Ghost (x,y) = (" + x + ", " + y + ")";
    }

    // Marshals this object into a JSON DOM
    public Json marshal()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("ghostType", ghostType);
        return ob;
    }

    // Unmarshaling constructor
    Ghost(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        ghostType = (int)ob.getLong("ghostType");


        // // Initializes ghost animation images
        // if (skeleImages == null) {
        //     skeleImages = new BufferedImage[MAX_SKELE_IMAGES + 1];
        //     for (int i = 1; i < MAX_SKELE_IMAGES + 1; i++) {
        //         skeleImages[i] = View.loadImage("skeleton" + i + ".png");
        //     }
            
        // }

        // Initializes ghost animation images
        if (ghostImages == null) {
            ghostImages = new BufferedImage[4][MAX_IMAGES_PER_DIRECTION * 4 * 4];
            for (int j = 0; j < 4; j++) {
                for (int i = 1; i < MAX_IMAGES_PER_DIRECTION * 4 + 1; i++) {
                    ghostImages[j][i] = View.loadImage(j + "-" + i + ".png");
                }
            }
        }

        if (deathImages == null) {
            // Initializes array of player images
            deathImages = new BufferedImage[MAX_DEATH_IMAGES];
            for (int i = 1; i < MAX_DEATH_IMAGES; i++) {
                deathImages[i] = View.loadImage("ghost" + i + ".png");
            }
        }
    }
}
            