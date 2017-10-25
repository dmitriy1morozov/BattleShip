package battleship;

import static battleship.Cell.*;
import java.lang.reflect.Field;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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
        boolean expResult = true;
        boolean result = instance.isShip() && !instance.isShot();
        assertEquals(expResult, result);
    }

    /**
     * Test of clear method, of class Cell.
     */
    @Test
    public void testClear() {
        System.out.println("clear");
        Cell instance = new Cell();
        instance.clear();
        boolean expResult = true;
        boolean result = !instance.isShot() && !instance.isShip();
        assertEquals(expResult, result);
    }

    @Test
    public void testFireFreeCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //Reflection to edit fields
        Field field = instance.getClass().getDeclaredField("mIsShip");
        field.setAccessible(true);
        field.set(instance, true);
        System.out.println("isShip = " + instance.isShot());
                
        boolean expResult = false;
        boolean expIsShot = true;
        boolean expIsShip = true;
            
        boolean result = instance.fire();
        boolean isShot = instance.isShot();
        boolean isShip = instance.isShip();

        assertEquals(expResult, result);
        assertEquals(expIsShot, isShot);
        assertEquals(expIsShip, isShip);
    }
    
    /**
     * Test of setAdjoined method, of class Cell.
     */
    @Test
    public void testSetAdjoined() {
        System.out.println("setAdjoined");
        Cell instance = new Cell();
        instance.setAdjoined();
        boolean expResult = true;
        boolean result = !instance.isShip() && instance.isShot();
        assertEquals(expResult, result);
    }
}