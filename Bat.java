import java.awt.*;

public class Bat {
    private int x;       // Bat's x position
    private int y;       // Bat's y position
    private int height;  // Bat's height
    private int width;   // Bat's width
    private int velocityY; // Bat's vertical velocity
    private Image img;   // Bat's image

    // Constructor to initialize the bat's position, size, and image
    public Bat(int x, int y, int width, int height, Image img) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.img = img;
        this.velocityY = 0;
    }

    // Method to draw the bat
    public void draw(Graphics g) {
        g.drawImage(img, x, y, width, height, null);
    }

    // Method to update the bat's position with gravity
    public void update(int gravity) {
        velocityY += gravity;
        y += velocityY;
        y = Math.max(y, 0); // Prevent the bat from going above the screen
    }

    // Method to make the bat jump
    public void jump() {
        velocityY = -9;
    }

    // Getter methods
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }

    // Setter methods
    public void setY(int y) { this.y = y; }
    public void setVelocityY(int velocityY) { this.velocityY = velocityY; }

    // Method to check for collision with a spike
    public boolean collision(Spike spike) {
        return x < spike.getX() + spike.getWidth() &&
               x + width > spike.getX() &&
               y < spike.getY() + spike.getHeight() &&
               y + height > spike.getY();
    }
}