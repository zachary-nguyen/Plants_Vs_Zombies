import Controller.Game;
import Model.Backyard;
import Model.Sprite;
import View.View;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;

public class TestView extends TestCase {

    private Game game;
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
        this.game = new Game();

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
        assertEquals(map[0][0].peek(),this.zombie);
        assertEquals("Zombies Alive: ", view.getZombieAlive().getText());
        assertEquals("Score: ", view.getScore().getText());
        assertEquals("Wave: ",view.getWave().getText());
        assertEquals("Sun: ",view.getSun().getText());
        assertEquals("Zombies Remaining: ",view.getZombieLeft().getText());

        //update the panel
        view.updateScorePanel(1,0,300,0,5);
        assertEquals(map[0][0].peek(),this.zombie);
        assertEquals("Zombies Alive: 0", view.getZombieAlive().getText());
        assertEquals("Score: 0", view.getScore().getText());
        assertEquals("Wave: 1",view.getWave().getText());
        assertEquals("Sun: 300",view.getSun().getText());
        assertEquals("Zombies Remaining: 5",view.getZombieLeft().getText());

    }

    /**
     * Tests if tile clicks work.
     */
    @Test
    public void testClickable() {
        //Checks if adding sprite to tile works.
        game.getView().getAddSunflower().doClick();
        game.getView().getButtonGrid()[0][0].doClick();

        //verify that a sunflower is addded to the view
        assertNotNull(game.getBackyard().getMap()[0][0].peek());

        //now remove the sunflower
        game.getView().getShovel().doClick();
        game.getView().getButtonGrid()[0][0].doClick();

        assertNull(game.getBackyard().getMap()[0][0].peek());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestView.class);
    }
}
