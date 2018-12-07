import View.*;
import Model.*;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import java.util.PriorityQueue;

public class TestView extends TestCase {

    private View view;
    private Backyard backyard;
    private PriorityQueue<Sprite>[][] map;
    private Sprite zombie;

    /**
     * Set up test data
     */
    @Before
    public void setUp(){
        this.view = new View();
        this.backyard = new Backyard();
        this.map = this.backyard.getMap();
    }

    /**
     * Test View creation
     */
    @Test
    public void testViewCreation(){
        //Test Overall View Works
        assertNotNull(this.view);

        //Sprite Buttons
        assertNotNull(view.getAddSunflower());
        assertNotNull(view.getAddPeashooter());
        assertNotNull(view.getAddRepeater());
        assertNotNull(view.getAddWallnut());

        assertNotNull(view.getSave());
        assertNotNull(view.getSkip());
        assertNotNull(view.getShovel());
        assertNotNull(view.getExit());

        //Components
        assertNotNull(view.getButtonGrid());
        assertNotNull(view.getUndo());
        assertNotNull(view.getRedo());

        assertNotNull(view.getGenWave());
        assertNotNull(view.getLoad());
    }

    /**
     * Tests if score board works are required.
     */
    @Test
    public void testScoreBoard() {
        //Test scorecard
        this.backyard.addSprite(0, 0, this.zombie);
        assertEquals(map[0][0].peek(),this.zombie);
        assertEquals(view.getZombieAlive().getText(), "Zombies Alive: 1");
    }

    /**
     * Tests if tile clicks work.
     */
    @Test
    public void testClickable() {
        //Checks if adding sprite to tile works.
        view.getAddSunflower().doClick();
        view.getButtonGrid()[2][4].doClick();
        assertFalse(view.getButtonGrid()[2][4].isEmpty());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestView.class);
    }
}
