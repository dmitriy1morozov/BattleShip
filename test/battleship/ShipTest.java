package battleship;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

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

    //---------------------------------------Drown--------------------------------------------------
    @Test
    public void testIsDrown_true() {
        System.out.println("testDrown");
        
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            Cell injuredDeck = new CellSpy(true, true);
            cell[i] = injuredDeck;
        }
        Ship instance = new Ship(coordinates, cell);
        assertTrue(instance.isDrown());
    }
    @Test
    public void testIsDrown_false() {
        System.out.println("testNotDrown");
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            Cell injuredDeck = new CellSpy(true, true);
            cell[i] = injuredDeck;
        }
        
        Cell unhurtDeck = new CellSpy(false, true);
        if(mDeckNumber == 1){
            cell[0] = unhurtDeck;
        } else{
            cell[ThreadLocalRandom.current().nextInt(0, mDeckNumber)] = unhurtDeck;
        }
        Ship instance = new Ship(coordinates, cell);
        assertFalse(instance.isDrown());
    }

    //---------------------------------------Injured------------------------------------------------
    @Test
    public void testIsInjured_true() {
        System.out.println("testInjured");
        
        int deckNumber = ThreadLocalRandom.current().nextInt(2, 5);
        Coordinates[] coordinates = new Coordinates[deckNumber];
        Cell[] cell = new Cell[deckNumber];
        for (int i = 0; i < deckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            Cell unhurtDeck = new CellSpy(false, true);
            cell[i] = unhurtDeck;
        }
        Cell injuredDeck = new CellSpy(true, true);
        cell[ThreadLocalRandom.current().nextInt(0, deckNumber)] = injuredDeck;
        Ship instance = new Ship(coordinates, cell);
        assertTrue(instance.isInjured());
    }
    
    @Test
    public void testIsInjured_false() {
        System.out.println("testNotInjured");
        
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            Cell unhurtDeck = new CellSpy(false, true);
            cell[i] = unhurtDeck;
        }
        Ship instance = new Ship(coordinates, cell);
        assertFalse(instance.isInjured());
    }
    
    //---------------------------------------Equals-------------------------------------------------
    @Test
    public void testEquals(){
        System.out.println("testEquals");
        
        Coordinates[] coordinates = new Coordinates[mDeckNumber];
        Cell[] cell = new Cell[mDeckNumber];
        for (int i = 0; i < mDeckNumber; i++) {
            coordinates[i] = new Coordinates(2+i, 3);
            boolean isShot = ThreadLocalRandom.current().nextBoolean();
            boolean isShip = true;
            cell[i] = new CellSpy(isShot, isShip);
        }
        Ship ship1 = new Ship(coordinates, cell);
        Ship ship2 = new Ship(coordinates, cell);
        
       assertEquals(ship1, ship2);
    }
    
    
    //---------------------------------------Learning-----------------------------------------------
    /**
     * TODO Just learning Remove it.
     */
    @Test(timeout = 100)
    public void testArraysSort_Performance(){
        System.out.println("testPerformance");
        int[] array= {5,4,8,1,9,12};
        for (int i = 0; i < 1000000; i++) {
            Arrays.sort(array);
            array[3] = ThreadLocalRandom.current().nextInt(0, 15);
            array[4] = ThreadLocalRandom.current().nextInt(0, 15);
        }
    }
    
    /**
     * TODO Just learning Remove it.
     */
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testArraysSort_Exception(){
        System.out.println("testException");
        int[] numbers= {};
        System.out.println(numbers[1]);
    }
}