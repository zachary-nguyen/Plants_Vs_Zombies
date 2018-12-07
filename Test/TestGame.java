import Controller.Game;
import Model.Backyard;
import Model.Wave;
import junit.framework.TestCase;
import junitx.framework.FileAssert;
import org.junit.Test;

import java.io.File;

public class TestGame extends TestCase {

    private Game game;
    private Backyard backyard;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        game = new Game();
        backyard = new Backyard();
    }


    /**
     * Test the save functionality
     */
    @Test
    public void testSave(){
        String filename = "saveTest";
        String pathname = TestGame.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20"," ");

        game.saveGame(filename,pathname);

        File file  = new File(pathname + "/" + filename +".ser");
        assertTrue(file.exists());
    }

    /**
     * Test the load functionality
     */
    @Test
    public void testLoad(){

        String filename = "saveTest";
        String pathname = TestGame.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20"," ");
        Backyard backyard = game.getBackyard();
        game.saveGame(filename,pathname);
        Backyard loadBackyard = game.loadGame(filename +".ser", pathname);

        assertEquals(backyard,loadBackyard); //Assert that the loaded game equals the game you saved
        assertEquals(loadBackyard,game.getBackyard()); //Assert that the controllers backyard equals the backyard that was loaded
    }

    /**
     * Test the undo functionality
     */
    @Test
    public void testUndo(){

        //Assert that the stacks start off empty
        assertTrue(game.getUndo().empty());
        assertTrue(game.getRedo().empty());

        //Assert that nothing happens when there is nothing to undo
        game.undo();
        assertTrue(game.getUndo().empty());
        assertTrue(game.getRedo().empty());

        //push something into the undo stack
        game.getUndo().push(game.getBackyard().cloneBackyard());
        assertTrue(game.getUndo().size() == 1);
        assertTrue(game.getRedo().empty());

        //Verify that when you undo its pops from the undo stack and is added to the redo stack
        Backyard undoBackyard = game.getUndo().peek();
        game.undo();
        assertTrue(game.getUndo().empty());
        assertEquals(1, game.getRedo().size());
        assertEquals(undoBackyard, game.getRedo().peek());

    }

    /**
     * Test the redo functionality
     */
    @Test
    public void testRedo(){

        //Assert that the stacks start off empty
        assertTrue(game.getUndo().empty());
        assertTrue(game.getRedo().empty());

        //Assert that nothing happens when there is nothing to undo
        game.redo();
        assertTrue(game.getUndo().empty());
        assertTrue(game.getRedo().empty());

        //push something into the undo stack
        game.getRedo().push(game.getBackyard().cloneBackyard());
        assertTrue(game.getRedo().size() == 1);
        assertTrue(game.getUndo().empty());

        //Verify that when you undo its pops from the undo stack and is added to the redo stack
        Backyard redoBackyard = game.getRedo().peek();
        game.redo();
        assertTrue(game.getRedo().empty());
        assertTrue(game.getUndo().size() ==1);
        assertTrue(redoBackyard.equals(game.getUndo().peek()));

    }

    /**
     * Verify the export xml is saving levels properly
     */
    @Test
    public void testExportXml(){
        //Create a new wave to export
        Wave exportWave = new Wave(1,2,3,4);
        Wave exportWave2 = new Wave(1,2,3,4);

        File exportedFile = exportWave.exportToXml("Wave1","Test");
        File compareFile = exportWave2.exportToXml("Wave2","Test");
        FileAssert.assertEquals(exportedFile,compareFile);
    }

    /**
     * Test the actionlisteners
     */
    @Test
    public void testActionListeners(){
        assertEquals("load",this.game.getView().getLoad().getActionCommand());
        assertEquals("save",this.game.getView().getSave().getActionCommand());
        assertEquals("undo",this.game.getView().getUndo().getActionCommand());
        assertEquals("redo",this.game.getView().getRedo().getActionCommand());
        assertEquals("genWave",this.game.getView().getGenWave().getActionCommand());
        assertEquals("sunflower",this.game.getView().getAddSunflower().getActionCommand());
        assertEquals("peashooter",this.game.getView().getAddPeashooter().getActionCommand());
        assertEquals("repeater",this.game.getView().getAddRepeater().getActionCommand());
        assertEquals("wallnut",this.game.getView().getAddWallnut().getActionCommand());
        assertEquals("exit",this.game.getView().getExit().getActionCommand());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestGame.class);
    }

}
