import greenfoot.Greenfoot;
import greenfoot.World;

public class MenuWorld extends World {

    public MenuWorld() {
        super(600, 400, 1);
        Greenfoot.setWorld(new GameWorld());
    }
}
