package battleship;

import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.*;

public class CellTest {
    
    public CellTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of occupy method, of class Cell.
     */
    @Test
    public void testOccupy() {
        System.out.println("occupy");
        Cell instance = new Cell();
        instance.occupy();
        boolean result = instance.isShip() && !instance.isShot();
        assertTrue(result);
    }

    /**
     * Test of clear method, of class Cell.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Cell instance = new Cell();
        instance.clear();
        boolean result = !instance.isShot() && !instance.isShip();
        assertTrue(result);
    }
    
    /**
     * Test of setAdjoined method, of class Cell.
     */
    @Test
    public void testSetAdjoined() {
        System.out.println("setAdjoined");
        Cell instance = new Cell();
        instance.setAdjoined();
        boolean result = !instance.isShip() && instance.isShot();
        assertTrue(result);
    }

    @Test
    public void testFire_Missed() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT! CAUTION!! REFLECTION DETECTED!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, false);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, false);
        
        //No additional move for missing a cell
        assertFalse(instance.fire());
        assertTrue(instance.isShot());
        assertFalse(instance.isShip());
    }
    
    @Test
    public void testFire_Hit() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT! CAUTION!! REFLECTION DETECTED!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, true);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, false);

        //Bonus move for hitting a ship
        assertTrue(instance.fire());
        assertTrue(instance.isShot());
        assertTrue(instance.isShip());
    }
    
    @Test
    public void testFire_ShotAlreadyMissedCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT! CAUTION!! REFLECTION DETECTED!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, false);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, true);
        
        assertTrue(instance.fire());
        assertTrue(instance.isShot());
        assertFalse(instance.isShip());
    }
    
    @Test
    public void testFire_ShotAlreadyHitCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT! CAUTION!! REFLECTION DETECTED!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, true);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, true);
        
        assertTrue(instance.fire());
        assertTrue(instance.isShot());
        assertTrue(instance.isShip());
    }
}