package battleship;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ShipTest {
    
    private final int mDeckNumber;
    
    public ShipTest() {
        mDeckNumber = ThreadLocalRandom.current().nextInt(1, 5);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of isDrown method, of class Ship.
     */
    @Test
    public void testIsDrown_true() {
        System.out.println("testDrown");
        
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        Cell mockDeckInjured = mock(Cell.class);
        when(mockDeckInjured.isShot()).thenReturn(true);
        when(mockDeckInjured.isShip()).thenReturn(true);
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            cell[i] = mockDeckInjured;
        }
        Ship instance = new Ship(coordinates, cell);
        assertTrue(instance.isDrown());
    }
    
    @Test
    public void testIsDrown_false() {
        System.out.println("testNotDrown");
        
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        Cell mockDeckInjured = mock(Cell.class);
        when(mockDeckInjured.isShot()).thenReturn(true);
        when(mockDeckInjured.isShip()).thenReturn(true);
        Cell mockDeckNotInjured = mock(Cell.class);
        when(mockDeckNotInjured.isShot()).thenReturn(false);
        when(mockDeckNotInjured.isShip()).thenReturn(true);
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            cell[i] = mockDeckInjured;
        }
        if(mDeckNumber == 1){
            cell[0] = mockDeckNotInjured;
        } else{
            cell[ThreadLocalRandom.current().nextInt(0, mDeckNumber)] = mockDeckNotInjured;
        }
        Ship instance = new Ship(coordinates, cell);
        assertFalse(instance.isDrown());
    }

    /**
     * Test of isInjured method, of class Ship.
     */
    @Test
    public void testIsInjured_true() {
        System.out.println("testInjured");
        
        int deckNumber = ThreadLocalRandom.current().nextInt(2, 5);
        Coordinates[] coordinates = new Coordinates[deckNumber];
        Cell[] cell = new Cell[deckNumber];
        Cell mockDeckInjured = mock(Cell.class);
        when(mockDeckInjured.isShot()).thenReturn(true);
        when(mockDeckInjured.isShip()).thenReturn(true);
        Cell mockDeckNotInjured = mock(Cell.class);
        when(mockDeckNotInjured.isShot()).thenReturn(false);
        when(mockDeckNotInjured.isShip()).thenReturn(true);
        for (int i = 0; i < deckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            cell[i] = mockDeckNotInjured;
        }
        cell[ThreadLocalRandom.current().nextInt(0, deckNumber)] = mockDeckInjured;
        Ship instance = new Ship(coordinates, cell);
        assertTrue(instance.isInjured());
    }
    
    /**
     * Test of isInjured method, of class Ship.
     */
    @Test
    public void testIsInjured_false() {
        System.out.println("testNotInjured");
        
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        Cell mockDeckNotInjured = mock(Cell.class);
        when(mockDeckNotInjured.isShot()).thenReturn(false);
        when(mockDeckNotInjured.isShip()).thenReturn(true);
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            cell[i] = mockDeckNotInjured;
        }
        Ship instance = new Ship(coordinates, cell);
        assertFalse(instance.isInjured());
    }
}