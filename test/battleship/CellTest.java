package battleship;

import static battleship.Cell.*;
import java.lang.reflect.Field;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;
import org.mockito.*;

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

    @Test
    public void testFireNotShotFreeCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, false);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, false);
        
        boolean expResult = false;
        boolean expIsShot = true;
        boolean expIsShip = false;
            
        boolean result = instance.fire();
        boolean isShot = instance.isShot();
        boolean isShip = instance.isShip();

        assertEquals(expResult, result);
        assertEquals(expIsShot, isShot);
        assertEquals(expIsShip, isShip);
    }
    
    @Test
    public void testFireNotShotOccupiedCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, true);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, false);
        
        boolean expResult = true;
        boolean expIsShot = true;
        boolean expIsShip = true;
            
        boolean result = instance.fire();
        boolean isShot = instance.isShot();
        boolean isShip = instance.isShip();

        assertEquals(expResult, result);
        assertEquals(expIsShot, isShot);
        assertEquals(expIsShip, isShip);
    }
    
    @Test
    public void testFireShotFreeCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, false);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, true);
        
        boolean expResult = true;
        boolean expIsShot = true;
        boolean expIsShip = false;
            
        boolean result = instance.fire();
        boolean isShot = instance.isShot();
        boolean isShip = instance.isShip();

        assertEquals(expResult, result);
        assertEquals(expIsShot, isShot);
        assertEquals(expIsShip, isShip);
    }
    
    @Test
    public void testFireShotOccupiedCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fireFreeCell");
        Cell instance = new Cell();
        
        //TODO DON"T USE THAT!
        //Reflection to edit fields
        Field field1 = instance.getClass().getDeclaredField("mIsShip");
        field1.setAccessible(true);
        field1.set(instance, true);
        Field field2 = instance.getClass().getDeclaredField("mIsShot");
        field2.setAccessible(true);
        field2.set(instance, true);
        
        boolean expResult = true;
        boolean expIsShot = true;
        boolean expIsShip = true;
            
        boolean result = instance.fire();
        boolean isShot = instance.isShot();
        boolean isShip = instance.isShip();

        assertEquals(expResult, result);
        assertEquals(expIsShot, isShot);
        assertEquals(expIsShip, isShip);
    }
}