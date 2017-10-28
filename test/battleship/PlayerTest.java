/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.concurrent.ThreadLocalRandom;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 *
 * @author borsh
 */
public class PlayerTest {
    Cell mCellFree;
    Cell mCellMissed;
    Cell mCellInjured;
    Cell mCellNotInjured;
    
    public PlayerTest() {
        mCellFree = mock(Cell.class);
        when(mCellFree.isShot()).thenReturn(false);
        when(mCellFree.isShip()).thenReturn(false);
        mCellMissed = mock(Cell.class);
        when(mCellMissed.isShot()).thenReturn(true);
        when(mCellMissed.isShip()).thenReturn(false);
        mCellInjured = mock(Cell.class);
        when(mCellInjured.isShot()).thenReturn(true);
        when(mCellInjured.isShip()).thenReturn(true);
        mCellNotInjured = mock(Cell.class);
        when(mCellNotInjured.isShot()).thenReturn(false);
        when(mCellNotInjured.isShip()).thenReturn(true);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void testFireCell() {
        System.out.println("fireCell");
        GameBoard gameBoard = new GameBoard();
        GameBoard spyGameBoard = spy(gameBoard);
        when(spyGameBoard.getCell(0, 0)).thenReturn(mCellFree);
        when(spyGameBoard.getCell(1, 0)).thenReturn(mCellMissed);
        when(spyGameBoard.getCell(2, 0)).thenReturn(mCellInjured);
        when(spyGameBoard.getCell(3, 0)).thenReturn(mCellNotInjured);
        
        Player instance = new Player("RandomName", spyGameBoard);
        instance.printGameBoard();
        
        int x = ThreadLocalRandom.current().nextInt(0, 4);
        boolean result = instance.fireCell(x, 0);
        boolean expResult = false;
        if(x == 0) {
            expResult = false;
        } else if(x > 0 && x < 4){
            expResult = true;
        } 
        assertEquals(expResult, result);
    }


    

    /**
     * Test of tryShipPosition method, of class Player.
     */
    @Test
    public void testTryShipPosition() {
        System.out.println("tryShipPosition");
        Coordinates firstCell = null;
        int decksNumber = 0;
        boolean isHorizontal = false;
        Player instance = null;
        Ship expResult = null;
        Ship result = instance.tryShipPosition(firstCell, decksNumber, isHorizontal);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of placeShip method, of class Player.
     */
    @Test
    public void testPlaceShip() {
        System.out.println("placeShip");
        Ship ship = null;
        Player instance = null;
        boolean expResult = false;
        boolean result = instance.placeShip(ship);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateRandomShip method, of class Player.
     */
    @Test
    public void testGenerateRandomShip() {
        System.out.println("generateRandomShip");
        int decksNumber = 0;
        Player instance = null;
        Ship expResult = null;
        Ship result = instance.generateRandomShip(decksNumber);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateRandomMap method, of class Player.
     */
    @Test
    public void testGenerateRandomMap() {
        System.out.println("generateRandomMap");
        Player instance = null;
        instance.generateRandomMap();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRandomNotFiredCellCoordinates method, of class Player.
     */
    @Test
    public void testGetRandomNotFiredCellCoordinates() {
        System.out.println("getRandomNotFiredCellCoordinates");
        Player instance = null;
        Coordinates expResult = null;
        Coordinates result = instance.getRandomNotFiredCellCoordinates();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fireRandomCell method, of class Player.
     */
    @Test
    public void testFireRandomCell() {
        System.out.println("fireRandomCell");
        Player instance = null;
        boolean expResult = false;
        boolean result = instance.fireRandomCell();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
