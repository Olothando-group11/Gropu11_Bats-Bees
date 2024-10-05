import java.awt.*;

public class Spike {
    private int x;        // Spike's x position
    private int y;        // Spike's y position
    private int width;    // Spike's width
    private int height;   // Spike's height
    private Image img;    // Spike's image
    private boolean passed; // Whether the bat has passed this spike (for scoring)

    // Constructor to initialize the spike's position, size, and image
    public Spike(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.passed = false;
    }

    // Method to draw the spike
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    // Method to update the spike's position
    public void update(double velx) {
        x += velx;
    }

    // Getter methods
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public boolean isPassed() { return passed; }

    // Setter methods
    public void setPassed(boolean passed) { this.passed = passed; }
}