/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.ArrayList;
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
    
   /** Stub class for testing pursposes ONLY
    *     ABCDEFGHIJ
    *   1 +ooXXXo.oX
    *   2 +oooooo.oo
    *   3 ooXo....oo
    *   4 +ooo....oX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    private class GameBoardSpy_AllPositions extends GameBoard{
        public GameBoardSpy_AllPositions() {
            super();
            mCell[0][0] = new CellSpy(false, true);
            mCell[0][1] = new CellSpy(false, true);
            mCell[1][0] = new CellSpy(true, false);
            mCell[1][1] = new CellSpy(true, false);
            mCell[1][2] = new CellSpy(true, false);
            mCell[0][2] = new CellSpy(true, false);

            mCell[0][3] = new CellSpy(false, true);
            mCell[0][4] = new CellSpy(false, true);
            mCell[1][3] = new CellSpy(true, false);
            mCell[1][3] = new CellSpy(true, false);
            mCell[1][4] = new CellSpy(true, false);
            mCell[1][5] = new CellSpy(true, false);
            mCell[0][5] = new CellSpy(true, false);

            mCell[0][9] = new CellSpy(true, true);
            mCell[0][8] = new CellSpy(true, false);
            mCell[1][8] = new CellSpy(true, false);
            mCell[1][9] = new CellSpy(true, false);

            mCell[3][0] = new CellSpy(true, true);
            mCell[4][0] = new CellSpy(true, true);
            mCell[5][0] = new CellSpy(true, true);
            mCell[2][0] = new CellSpy(true, false);
            mCell[2][1] = new CellSpy(true, false);
            mCell[3][1] = new CellSpy(true, false);
            mCell[4][1] = new CellSpy(true, false);
            mCell[5][1] = new CellSpy(true, false);
            mCell[6][1] = new CellSpy(true, false);
            mCell[6][0] = new CellSpy(true, false);
            
            mCell[9][0] = new CellSpy(true, true);
            mCell[8][0] = new CellSpy(true, false);
            mCell[8][1] = new CellSpy(true, false);
            mCell[9][1] = new CellSpy(true, false);

            mCell[9][3] = new CellSpy(true, true);
            mCell[9][4] = new CellSpy(true, true);
            mCell[9][5] = new CellSpy(true, true);
            mCell[9][6] = new CellSpy(true, true);
            mCell[9][2] = new CellSpy(true, false);
            mCell[8][2] = new CellSpy(true, false);
            mCell[8][3] = new CellSpy(true, false);
            mCell[8][4] = new CellSpy(true, false);
            mCell[8][5] = new CellSpy(true, false);
            mCell[8][6] = new CellSpy(true, false);
            mCell[8][7] = new CellSpy(true, false);
            mCell[9][7] = new CellSpy(true, false);

            mCell[9][9] = new CellSpy(true, true);
            mCell[8][9] = new CellSpy(true, true);
            mCell[9][8] = new CellSpy(true, false);
            mCell[8][8] = new CellSpy(true, false);
            mCell[7][8] = new CellSpy(true, false);
            mCell[7][9] = new CellSpy(true, false);

            mCell[3][9] = new CellSpy(true, true);
            mCell[4][9] = new CellSpy(true, true);
            mCell[5][9] = new CellSpy(true, true);
            mCell[2][9] = new CellSpy(true, false);
            mCell[2][8] = new CellSpy(true, false);
            mCell[3][8] = new CellSpy(true, false);
            mCell[4][8] = new CellSpy(true, false);
            mCell[5][8] = new CellSpy(true, false);
            mCell[6][8] = new CellSpy(true, false);
            mCell[6][9] = new CellSpy(true, false);

            mCell[2][2] = new CellSpy(true, true);
            mCell[1][1] = new CellSpy(true, false);
            mCell[2][1] = new CellSpy(true, false);
            mCell[3][1] = new CellSpy(true, false);
            mCell[3][2] = new CellSpy(true, false);
            mCell[3][3] = new CellSpy(true, false);
            mCell[2][3] = new CellSpy(true, false);
            mCell[1][3] = new CellSpy(true, false);
            mCell[1][2] = new CellSpy(true, false);

            mCell[5][6] = new CellSpy(true, true);
            mCell[4][5] = new CellSpy(true, false);
            mCell[5][5] = new CellSpy(true, false);
            mCell[6][5] = new CellSpy(true, false);
            mCell[6][6] = new CellSpy(true, false);
            mCell[6][7] = new CellSpy(true, false);
            mCell[5][7] = new CellSpy(true, false);
            mCell[4][7] = new CellSpy(true, false);
            mCell[4][6] = new CellSpy(true, false);
            //TODO I don't like this since it invokes another method from the class. 
            //The current method we are testing SHOULD BE isolated.
            this.refreshFreeCellList();
        }
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

  /** 
    * Z - shot cell
    *     ABCDEFGHIJ
    *   1 ZooXXXo.oX
    *   2 +oooooo.oo
    *   3 ooXo....oo
    *   4 +ooo....oX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    @Test
    public void testFireCell_HitDeck() {
        System.out.println("testFireCell_HitDeck");
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        assertTrue(gameBoard.getFreeCellList().contains(new Coordinates(0,0)));
        assertTrue(instance.fireCell(0, 0));
        assertFalse(gameBoard.getFreeCellList().contains(new Coordinates(0,0)));
    }
    
   /** 
    * Z - shot cell
    *     ABCDEFGHIJ
    *   1 +ooXXXo.oZ
    *   2 +oooooo.oo
    *   3 ooXo....oo
    *   4 +ooo....oX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    @Test
    public void testFireCell_DoubleHitDeck() {
        System.out.println("testFireCell_DoubleHitDeck");
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        assertFalse(gameBoard.getFreeCellList().contains(new Coordinates(9,0)));
        assertTrue(instance.fireCell(9, 0));
        assertFalse(gameBoard.getFreeCellList().contains(new Coordinates(9,0)));
    }
    
   /** 
    * O - shot cell
    *     ABCDEFGHIJ
    *   1 +ooXXXoOoX
    *   2 +oooooo.oo
    *   3 ooXo....oo
    *   4 +ooo....oX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    @Test
    public void testFireCell_MissedCell() {
        System.out.println("testFireCell_MissedCell");
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        assertTrue(gameBoard.getFreeCellList().contains(new Coordinates(7,0)));
        assertFalse(instance.fireCell(7, 0));
        assertFalse(gameBoard.getFreeCellList().contains(new Coordinates(7,0)));
    }
    
   /** 
    * O - shot cell
    *     ABCDEFGHIJ
    *   1 +OoXXXo.oX
    *   2 +oooooo.oo
    *   3 ooXo....oo
    *   4 +ooo....oX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    @Test
    public void testFireCell_DoubleMissedCell() {
        System.out.println("testFireCell_DoubleMissedCell");
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        assertFalse(gameBoard.getFreeCellList().contains(new Coordinates(1,0)));
        assertTrue(instance.fireCell(1, 0));
        assertFalse(gameBoard.getFreeCellList().contains(new Coordinates(1,0)));
    }
    
   /** 
    * ZZZ - ship
    *     ABCDEFGHIJ
    *   1 ZZZ.......
    *   2 ..........
    *   3 ..........
    *   4 ..........
    *   5 ..........
    *   6 ..........
    *   7 ..........
    *   8 ..........
    *   9 ..........
    *  10 ..........
    */
    @Test
    public void testTryShipPosition_Success() {
        System.out.println("testTryShipPosition_Success");
        Coordinates firstCell = new Coordinates(0, 0);
        int decksNumber = 3;
        boolean isHorizontal = true;
        
        GameBoard gameBoard = new GameBoard();
        Player instance = new Player("RandomName", gameBoard);
        
        Coordinates[] expCoordinates = {new Coordinates(0, 0), new Coordinates(1, 0), new Coordinates(2, 0)};
        Cell[] expCells = {new Cell(), new Cell(), new Cell()};
        Ship expResult = new Ship(expCoordinates, expCells);
        Ship result = instance.tryShipPosition(firstCell, decksNumber, isHorizontal);
        assertEquals(expResult, result);
    }
    
   /** 
    * ZZZ - ship
    *     ABCDEFGHIJ
    *   1 .........ZZZ
    *   2 ..........
    *   3 ..........
    *   4 ..........
    *   5 ..........
    *   6 ..........
    *   7 ..........
    *   8 ..........
    *   9 ..........
    *  10 ..........
    */
    @Test
    public void testTryShipPosition_FailOutOfBounds() {
        System.out.println("testTryShipPosition_FailOutOfBounds");
        Coordinates firstCell = new Coordinates(9, 0);
        int decksNumber = 3;
        boolean isHorizontal = true;
        
        GameBoard gameBoard = new GameBoard();
        Player instance = new Player("RandomName", gameBoard);
        
        Ship result = instance.tryShipPosition(firstCell, decksNumber, isHorizontal);
        assertNull(result);
    }
    
   /** 
    * ZZZ - ship
    *     ABCDEFGHIJ
    *   1 +ooXZXo.oX
    *   2 +oooZoo.oo
    *   3 ooXoZ...oo
    *   4 +ooo....oX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    @Test
    public void testTryShipPosition_FailOverLayAnotherShip() {
        System.out.println("testTryShipPosition_FailOverLayAnotherShip");
        Coordinates firstCell = new Coordinates(4, 0);
        int decksNumber = 3;
        boolean isHorizontal = false;
        
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        Ship result = instance.tryShipPosition(firstCell, decksNumber, isHorizontal);
        assertNull(result);
    }
    
   /** 
    * ZZZ - ship
    *     ABCDEFGHIJ
    *   1 +ooXXXo.ZX
    *   2 +oooooo.Zo
    *   3 ooXo....Zo
    *   4 +ooo....oX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    @Test
    public void testTryShipPosition_FailOverLayAdjoin() {
        System.out.println("testTryShipPosition_FailOverLayAdjoin");
        Coordinates firstCell = new Coordinates(8, 0);
        int decksNumber = 3;
        boolean isHorizontal = false;
        
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        Ship result = instance.tryShipPosition(firstCell, decksNumber, isHorizontal);
        assertNull(result);
    }

   /** 
    * ZZZ - ship
    *     ABCDEFGHIJ
    *   1 +ooXXXo.+X
    *   2 +oooooo.+o
    *   3 ooXo....+o
    *   4 +ooo.ZZZoX
    *   5 +o......oX
    *   6 oo..ooo.oX
    *   7 ....oXo.oX
    *   8 ....ooo.oo
    *   9 oooooooooo
    *  10 XooXXXooXX
    */
    @Test
    public void testPlaceShip_Success() {
        System.out.println("testPlaceShip_Success");
        
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        Coordinates[] expCoordinates = {new Coordinates(5, 3), new Coordinates(6, 3), new Coordinates(7, 3)};
        Cell[] expCells = {new Cell(), new Cell(), new Cell()};
        Ship ship = new Ship(expCoordinates, expCells);
        
        assertTrue(instance.placeShip(ship));
        for (int i = 0; i < ship.getCoordinates().length; i++) {
            if(!(ship.getDeckCells()[i].isShot() == false && ship.getDeckCells()[i].isShip() == true)){
                Coordinates coord = ship.getCoordinates()[i];
                Cell cell = ship.getDeckCells()[i];
                fail("Ship's deck index = " + i + " is improper! Coordinates = " + coord.toString() + 
                        " ; isShot = " + cell.isShot() + " ; isShip = " + cell.isShip());
            }
        }
    }
    
    @Test
    public void testPlaceShip_Fail_NullShip() {
        System.out.println("testPlaceShip_Fail_NullShip");
        
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);
        
        Ship ship = null;
        assertFalse(instance.placeShip(ship));
    }
    
    
    
    //----------------------------------Ai methods--------------------------------------------------
    @Test
    public void testGenerateRandomShip() {
        System.out.println("generateRandomShip");
        GameBoard gameBoard = new GameBoardSpy_AllPositions();
        Player instance = new Player("RandomName", gameBoard);

        int decksNumber = ThreadLocalRandom.current().nextInt(1, 4);
        Ship generatedShip = instance.generateRandomShip(decksNumber);
        assertNotNull(generatedShip);
        Coordinates[] shipDecks = generatedShip.getCoordinates();
        
        //TODO Also, we are asserting that the ship doesn't change its direction. E.g. curved ship. 
        //Check if all generatedShip descks are not overlaying any existent ship
        for(Coordinates coord:shipDecks){
            if(gameBoard.getCell(coord).isShip() || gameBoard.getCell(coord).isShot()){
                //TEST FAILED error message
                String shipDescription = "";
                for (int k = 0; k < generatedShip.getCoordinates().length; k++) {
                    shipDescription = shipDescription + 
                            generatedShip.getCoordinates()[k].toString() + 
                            generatedShip.getDeckCells()[k].toString() + "\n";
                }
                System.out.println("Error generating a ship: " + shipDescription + " on the gameBoard \n" + gameBoard.toString());
                fail("Generate Ship error. Overlaying an existent ship");
            }
        }
        
        //Check all adjoined cells. They should be not occupied by any deck.
        //This guarantees that the ship is not "toching" any other ship. 
        int x0 = shipDecks[0].getX();
        int y0 = shipDecks[0].getY();
        int decksX = 0;
        int decksY = 0;
        if(generatedShip.isHorizontal()){
            decksX = generatedShip.getCoordinates().length;
        } else{
            decksY = generatedShip.getCoordinates().length;
        }
            
        //Check that all adjoined cells are not any existent ship's deck.
        //All of them should be empty or missed
        for (int j = y0-1; j < y0+decksY+1; j++) {
            for (int i = x0-1; i < x0+decksX+1; i++) {  
                //Check if coordinates are withing gameBoard bounds
                if(i < 0 || i >= GameBoard.COLUMNS_NUMBER || j < 0 || j >= GameBoard.ROWS_NUMBER) break;
                //Each cell should not be a ship
                boolean result = gameBoard.getCell(i, j).isShip();
                //TEST FAILED error message
                if(result){
                    String shipDescription = "";
                    for (int k = 0; k < generatedShip.getCoordinates().length; k++) {
                        shipDescription = shipDescription + 
                                generatedShip.getCoordinates()[k].toString() + 
                                generatedShip.getDeckCells()[k].toString() + "\n";
                    }
                    System.out.println("Error generating a ship: " + shipDescription + " on the gameBoard \n" + gameBoard.toString());
                    fail("Generate Ship error. Ship is touching an existent one");
                }
            }
        }     
    }

    @Test
    public void testGenerateRandomMap() {
        System.out.println("generateRandomMap");
        
        GameBoard gameBoard = new GameBoard();
        Player instance = new Player("RandomPlayer", gameBoard);
        instance.generateRandomMap();
        //TODO invent a proper test
    }
}
