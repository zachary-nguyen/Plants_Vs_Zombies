import Model.Bullet;
import Model.Peashooter;
import Model.Sunflower;
import Model.Zombie;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class TestAllSprites extends TestCase {

    private Bullet bullet;
    private Zombie zombie;
    private Sunflower sunflower;
    private Peashooter peashooter;

    /**
     * Set up test data
     */
    @Before
    public void setUp(){
       this.bullet = new Bullet(100,2);
       this.zombie = new Zombie();
       this.sunflower = new Sunflower();
       this.peashooter = new Peashooter();
    }

    /**
     * Test if bullets is created properly
     */
    @Test
    public void testBulletCreation(){
        assertNotNull(this.bullet);
        assertEquals(100,this.bullet.getDamage());
        assertEquals(2,this.bullet.getSpeed());
    }

    /**
     * Verify sunflower is properly created.
     */
    @Test
    public void testSunflowerCreation(){
        assertNotNull(this.sunflower);
        assertEquals(0,this.sunflower.getDamage());
        assertEquals(100,this.sunflower.getHealth());
        assertEquals(4,this.sunflower.getCounter());
        assertEquals(50,this.sunflower.getCost());

    }

    /**
     * Test zombie is created properly
     */
    @Test
    public void testZombieCreation(){
        assertNotNull(this.zombie);
        assertEquals(0,this.zombie.getCounter());
        assertEquals(50,this.zombie.getDamage());
        assertEquals(1,this.zombie.getSpeed());
        assertEquals(100,this.zombie.getHealth());
    }

    /**
     * Test peashooter is created properly
     */
    @Test
    public void testPeashooterCreation(){
        assertNotNull(this.peashooter);
        assertEquals(35,this.peashooter.getDamage());
        assertEquals(100,this.peashooter.getHealth());
        assertEquals(3,this.peashooter.getCounter());
        assertEquals(100,this.peashooter.getCost());
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestBackyard.class);
    }
}
