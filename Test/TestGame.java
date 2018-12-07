import Controller.Game;
import Model.Backyard;
import junit.framework.TestCase;

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


    public void testSave(){
        String filename = "saveTest";
        String pathname = TestGame.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20"," ");

        game.saveGame(filename,pathname);

        File file  = new File(pathname + "/" + filename +".ser");
        assertTrue(file.exists());
    }

    public void testLoad(){

        String filename = "saveTest";
        String pathname = TestGame.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20"," ");
        Backyard backyard = game.getBackyard();
        game.saveGame(filename,pathname);
        Backyard loadBackyard = game.loadGame(filename +".ser", pathname);

        assertEquals(backyard, loadBackyard); //Assert that the loaded game equals the game you saved
        assertEquals(loadBackyard,game.getBackyard()); //Assert that the controllers backyard equals the backyard that was loaded
    }

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

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestGame.class);
    }

}
