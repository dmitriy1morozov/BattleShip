package battleship;

import org.junit.*;
import static org.junit.Assert.*;

public class CellTest {

    //------------------------------------Occupy----------------------------------------------------
    @Test
    public void testOccupy() {
        System.out.println("occupy");
        Cell instance = new Cell();
        instance.occupy();
        boolean result = instance.isShip() && !instance.isShot();
        assertTrue(result);
    }
    
    //----------------------------------SetAdjoined-------------------------------------------------
    @Test
    public void testSetAdjoined() {
        System.out.println("setAdjoined");
        Cell instance = new Cell();
        instance.setAdjoined();
        boolean result = !instance.isShip() && instance.isShot();
        assertTrue(result);
    }

    //-----------------------------------Clear------------------------------------------------------
    @Test
    public void testClear_EmptyCell() {
        System.out.println("clear_EmptyCell");
        Cell instance = new CellSpy(false, false);
        instance.clear();
        boolean result = !instance.isShot() && !instance.isShip();
        assertTrue(result);
    }
    @Test
    public void testClear_HitDeck() {
        System.out.println("clear_HitDeck");
        Cell instance = new CellSpy(true, true);
        instance.clear();
        boolean result = !instance.isShot() && !instance.isShip();
        assertTrue(result);
    }
    @Test
    public void testClear_MissedCell() {
        System.out.println("clear_MissedCell");
        Cell instance = new CellSpy(true, false);
        instance.clear();
        boolean result = !instance.isShot() && !instance.isShip();
        assertTrue(result);
    }
    @Test
    public void testClear_HiddenShip() {
        System.out.println("clear_HiddenShip");
        Cell instance = new CellSpy(false, true);
        instance.clear();
        boolean result = !instance.isShot() && !instance.isShip();
        assertTrue(result);
    }
    
    //---------------------------------------Fire---------------------------------------------------
    @Test
    public void testFire_EmptyCell(){
        System.out.println("fire_EmptyCell");
        Cell instance = new CellSpy(false, false);
        
        //No additional move for missing a cell
        assertFalse(instance.fire());
        assertTrue(instance.isShot());
        assertFalse(instance.isShip());
    }
    
    @Test
    public void testFire_HitDeck(){
        System.out.println("fire_AlreadyHitDeck");
        Cell instance = new CellSpy(true, true);

        //Bonus move for hitting a ship
        assertTrue(instance.fire());
        assertTrue(instance.isShot());
        assertTrue(instance.isShip());
    }
    
    @Test
    public void testFire_MissedCell() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        System.out.println("fire_AlreadyMissedCell");
        Cell instance = new CellSpy(true, false);
        
        assertTrue(instance.fire());
        assertTrue(instance.isShot());
        assertFalse(instance.isShip());
    }
    
    @Test
    public void testFire_FoundDeck(){
        System.out.println("fire_FoundDeck");
        Cell instance = new CellSpy(false, true);
        
        assertTrue(instance.fire());
        assertTrue(instance.isShot());
        assertTrue(instance.isShip());
    }
}