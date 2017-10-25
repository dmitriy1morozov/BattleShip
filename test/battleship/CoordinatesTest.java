package battleship;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoordinatesTest {
    
    public CoordinatesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of equals method, of class Coordinates.
     */
    @Test
    public void testEqualsTrue() {
        System.out.println("equals");
        int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
        int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
        Coordinates coord = new Coordinates(x, y);
        Coordinates instance = new Coordinates(x, y);
        boolean expResult = true;
        boolean result = instance.equals(coord);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of equals method, of class Coordinates.
     */
    @Test
    public void testEqualsFalse() {
        System.out.println("doesn't equal");
        int x = ThreadLocalRandom.current().nextInt(0, ((int)(GameBoard.COLUMNS_NUMBER)/2));
        int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
        Coordinates coord = new Coordinates(x, y);
        int x2 = ThreadLocalRandom.current().nextInt(((int)(GameBoard.COLUMNS_NUMBER)/2), GameBoard.COLUMNS_NUMBER);
        Coordinates instance = new Coordinates(x2, y);
        boolean expResult = false;
        boolean result = instance.equals(coord);
        assertEquals(expResult, result);
    }
}