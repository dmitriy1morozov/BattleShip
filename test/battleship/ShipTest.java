package battleship;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShipTest {
    
    private final int mDeckNumber = 3;
    private final Ship mInstance;
    
    public ShipTest() {
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            cell[i] = new Cell();
        }
        mInstance = new Ship(coordinates, cell);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getDeckCells method, of class Ship.
     */
    @Test
    public void testGetDeckCells() {
        System.out.println("getDeckCells");
        
        Cell[] expResult = new Cell[mDeckNumber];
        for (int i = 0; i < mDeckNumber; i++) {
            expResult[i] = new Cell();
        }
        
        Cell[] result = mInstance.getDeckCells();
        for (int i = 0; i < result.length; i++) {
            assertEquals(expResult[i].isShip(),result[i].isShip());
            assertEquals(expResult[i].isShot(),result[i].isShot());
        }
    }

    /**
     * Test of getCoordinates method, of class Ship.
     */
    @Test
    public void testGetCoordinates() {
        System.out.println("getCoordinates");
        
        Coordinates[] expResult = new Coordinates[mDeckNumber];
        for (int i = 0; i < mDeckNumber; i++) {
            expResult[i] = new Coordinates(2+i, 3);
        }
        
        Coordinates[] result = mInstance.getCoordinates();
        for (int i = 0; i < result.length; i++) {
            assertTrue(expResult[i].equals(result[i]));
        }
    }

    /**
     * Test of isHorizontal method, of class Ship.
     */
    @Test
    public void testIsHorizontal() {
        System.out.println("isHorizontal");
        
        boolean expResult = true;
        boolean result = mInstance.isHorizontal();
        assertEquals(expResult, result);
    }

    /**
     * Test of isDrown method, of class Ship.
     */
    @Test
    public void testIsDrown() {
        System.out.println("isDrown");
        
        boolean expResult = false;
        boolean result = mInstance.isDrown();
        assertEquals(expResult, result);
    }

    /**
     * Test of isInjured method, of class Ship.
     */
    @Test
    public void testIsInjured() {
        System.out.println("isInjured");
        
        boolean expResult = false;
        boolean result = mInstance.isInjured();
        assertEquals(expResult, result);
    }
}