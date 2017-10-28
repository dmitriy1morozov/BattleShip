package battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import static org.hamcrest.Matchers.isOneOf;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

public class GameBoardTest {
    
    public GameBoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getFreeCellList method, of class GameBoard.
     */
    @Test
    public void testGetFreeCellList() {
        System.out.println("getFreeCellList");
        LinkedList<Coordinates> expResult = new LinkedList();
        for (int j = 0; j < GameBoard.ROWS_NUMBER; j++) {
            for (int i = 0; i < GameBoard.COLUMNS_NUMBER; i++) {
                expResult.add(new Coordinates(i,j));
            }
        }
        
        GameBoard instance = new GameBoard();
        LinkedList<Coordinates> result = instance.getFreeCellList();
        
        Iterator<Coordinates> iterator1 = result.iterator();
        Iterator<Coordinates> iterator2 = expResult.iterator();
        while(iterator1.hasNext() && iterator2.hasNext()){
            assertTrue(iterator1.next().equals(iterator2.next()));
        }
    }

    /**
     * Test of areCoordinatesWithinBoard method, of class GameBoard.
     */
    @Test
    public void testAreCoordinatesWithinBoard() {
        System.out.println("areCoordinatesWithinBoard");
        int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
        int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
        
        GameBoard instance = new GameBoard();
        boolean expResult = true;
        boolean result = instance.areCoordinatesWithinBoard(x, y);
        assertEquals(expResult, result);
    }

    /**
     * Test of adjoinCell method, of class GameBoard.
     */
    @Test
    public void testAdjoinCell() {
        System.out.println("adjoinCell");
        GameBoard instance = new GameBoard();
        
        int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
        int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
        Coordinates coordinates = new Coordinates(x, y);
        instance.markAdjoinCells(coordinates);
        
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {
                if(i == 0 && j == 0) break;
                if(x+i < 0 || x+i >= GameBoard.COLUMNS_NUMBER || y+j < 0 || y+j >= GameBoard.ROWS_NUMBER) break;
                assertTrue(instance.getCell(x+i, y+j).isShot());
            }
        }
    }

    /**
     * Test of refreshFreeCellList method, of class GameBoard.
     */
    @Test
    public void testRefreshFreeCellList() {
        System.out.println("refreshFreeCellList");
        GameBoard instance = new GameBoard();
        GameBoard spyInstance = spy(instance);
        
        //Generate random missed cells on board
        Cell mockCellMissed = mock(Cell.class);
        when(mockCellMissed.isShot()).thenReturn(true);
        when(mockCellMissed.isShip()).thenReturn(false);
        for (int i = 0; i < 10; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
            int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
            when(spyInstance.getCell(x, y)).thenReturn(mockCellMissed);
        }
        spyInstance.refreshFreeCellList();
        LinkedList<Coordinates> sideEffect = spyInstance.getFreeCellList();
        
        //Expected sideEffect
        LinkedList<Coordinates> expSideEffect = new LinkedList();
        for (int j = 0; j < GameBoard.ROWS_NUMBER; j++) {
            for (int i = 0; i < GameBoard.COLUMNS_NUMBER; i++) {
                if(!spyInstance.getCell(i, j).isShot()){
                    expSideEffect.add(new Coordinates(i,j));
                }
            }
        }
        
        //test
        Iterator<Coordinates> iterator1 = sideEffect.iterator();
        Iterator<Coordinates> iterator2 = expSideEffect.iterator();
        while(iterator1.hasNext() && iterator2.hasNext()){
            assertTrue(iterator1.next().equals(iterator2.next()));
        }
    }

    /**
     * Test of tryShipPosition method, of class GameBoard.
     */
    @Test
    public void testTryShipPosition() {
        System.out.println("tryShipPosition");
        Coordinates firstCell = new Coordinates(3,9);
        int decksNumber = 3;
        boolean isHorizontal = true;
        
        //result
        GameBoard instance = new GameBoard();
        Ship result = instance.tryShipPosition(firstCell, decksNumber, isHorizontal);

        //expResult
        Ship expResult;
        Coordinates[] expCoordinates = new Coordinates[decksNumber];
        Cell[] expCells = new Cell[decksNumber];
        int dx = 0;
        int dy = 0;
        if(isHorizontal){
            dx = 1;
        } else {
            dy = 1;
        }
        if(firstCell.getX() < 0 || firstCell.getY() < 0 ||
                firstCell.getX() + decksNumber*dx > GameBoard.COLUMNS_NUMBER || 
                firstCell.getY() + decksNumber*dy > GameBoard.ROWS_NUMBER){
            expResult = null;
        } else{
            for (int i = 0; i < decksNumber; i++) {
                int x = firstCell.getX() + i*dx;
                int y = firstCell.getY() + i*dy;
                expCoordinates[i] = new Coordinates(x, y);
                expCells[i] = new Cell();
            }
            expResult = new Ship(expCoordinates, expCells);
        }
        
        //test
        if(result == null || expResult == null){
           assertNull(result);
           assertNull(expResult);
        } else {
            assertTrue(result.equals(expResult));
        }
    }

    /**
     * Test of fireCell method, of class GameBoard.
     */
    @Test
    public void testFireCell() {
        System.out.println("fireCell");
        int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
        int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
        
        GameBoard instance = new GameBoard();
        boolean expResult = false;
        boolean result = instance.fireCell(x, y);
        assertEquals(expResult, result);
    }
    
    //==============================================Ai tests========================================

    @Test
    public void testGenerateRandomShip() {
        System.out.println("generateRandomShip");
        GameBoard instance = new GameBoard();
        GameBoard spyInstance = spy(instance);
        
        //Generate random missed cells on board
        Cell mockCellMissed = mock(Cell.class);
        when(mockCellMissed.isShot()).thenReturn(true);
        when(mockCellMissed.isShip()).thenReturn(false);
        for (int i = 0; i < 10; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
            int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
            when(spyInstance.getCell(x, y)).thenReturn(mockCellMissed);
        }
        
        //TODO I don't like this since it invokes another method from the class to be used. The current method we are testing is not isolated.
        spyInstance.refreshFreeCellList();

        int decksNumber = ThreadLocalRandom.current().nextInt(0, 4);
        Ship generatedShip = spyInstance.getAi().generateRandomShip(decksNumber);
        Coordinates[] shipCoordinates = generatedShip.getCoordinates();
        
        boolean expResult = false;
        
        //Checking adjoined cells. The adjoined cell coordinates are relative coordinates to the deck we are checking
        //(-1,-1), (1, -1), (1,1), (-1, 1)
        //They should be not occupied by any deck.
        //This guarantees that the ship is not "toching" any other ship. 
        //Also, we are asserting that the ship doesn't change its direction. E.g. curved ship.
        for(int k = 0; k < shipCoordinates.length; k++) {
            int x = shipCoordinates[k].getX();
            int y = shipCoordinates[k].getY();
            
            //(-1,-1), (1, -1), (1,1), (-1, 1)
            int delta = 2;
            //If the ship is one-deck then we should check every adjoined cell to make sure that they are not occupied
            //The adjoined cell coordinates are relative coordinates to the deck coordinates we are checking
            //(-1,-1),(0,-1),(1, -1),(1,0),(1,1),(0,1),(-1, 1),(-1,0)
            if(shipCoordinates.length == 1) delta = 1;
            for (int j = -1; j <= 1; j+=delta) {
                for (int i = -1; i <= 1; i+=delta) {  
                    if(i==0 && j==0)break; //prevent checking the deck cell
                    if(x+i < 0 || x+i >= GameBoard.COLUMNS_NUMBER || y+j < 0 || y+j >= GameBoard.ROWS_NUMBER) break;
                    boolean result = instance.getCell(x+i, y+j).isShip();
                    assertEquals(expResult, result);
                }
            }     
        }           
    }
    
    @Test
    public void testGenerateRandomShipMap() {
        System.out.println("generateRandomShipMap");
        GameBoard instance = new GameBoard();
        
        ArrayList<Ship> shipList = instance.getAi().generateRandomShipsMap();
        
        for (Ship ship:shipList) {
            Coordinates[] shipCoordinates = ship.getCoordinates();
            
            boolean expResult = false;
            //Checking adjoined cells. The adjoined cell coordinates are relative coordinates to the deck we are checking
            //(-1,-1), (1, -1), (1,1), (-1, 1)
            //They should be not occupied by any deck.
            //This guarantees that the ship is not "toching" any other ship. 
            //Also, we are asserting that the ship doesn't change its direction. E.g. curved ship.
            for(int k = 0; k < shipCoordinates.length; k++) {
                int x = shipCoordinates[k].getX();
                int y = shipCoordinates[k].getY();

                //(-1,-1), (1, -1), (1,1), (-1, 1)
                int delta = 2;
                //If the ship is one-deck then we should check every adjoined cell to make sure that they are not occupied
                //The adjoined cell coordinates are relative coordinates to the deck coordinates we are checking
                //(-1,-1),(0,-1),(1, -1),(1,0),(1,1),(0,1),(-1, 1),(-1,0)
                if(shipCoordinates.length == 1) delta = 1;
                for (int j = -1; j <= 1; j+=delta) {
                    for (int i = -1; i <= 1; i+=delta) {  
                        if(i==0 && j==0)break; //prevent checking the deck cell
                        if(x+i < 0 || x+i >= GameBoard.COLUMNS_NUMBER || y+j < 0 || y+j >= GameBoard.ROWS_NUMBER) break;
                        boolean result = instance.getCell(x+i, y+j).isShip();
                        assertEquals(expResult, result);
                    }
                }     
            }  
        }
    }
    
    //TODO complete the test
    @Test
    public void testSearchPossibleDeck(){
        System.out.println("searchPossibleDeck");
        GameBoard instance = new GameBoard();
        ArrayList<Ship> armada = instance.getAi().generateRandomShipsMap();
        Ship targetShip = armada.get(ThreadLocalRandom.current().nextInt(0, armada.size()));
        int targetDeckIndex = ThreadLocalRandom.current().nextInt(0, targetShip.getCoordinates().length);
        Coordinates targetDeck = targetShip.getCoordinates()[targetDeckIndex];
        instance.fireCell(targetDeck.getX(), targetDeck.getY());
        
        //Test that possibleDeck is not yet hit and not yet checked
        Coordinates possibleDeck = instance.getAi().searchPossibleDeck(targetDeck);
        assertTrue(!instance.getCell(possibleDeck).isShot());
        
        int injuredX = targetDeck.getX();
        int injuredY = targetDeck.getY();
        int possibleX = possibleDeck.getX();
        int possibleY = possibleDeck.getY();
        
        //Test that possible deck is on one line (vertical/horizontal) with injured deck
        assertTrue((injuredX==possibleX && injuredY!=possibleY) 
                || (injuredX!=possibleX && injuredY==possibleY));
        
        System.out.println("injuredX = " + injuredX);
        System.out.println("injuredY = " + injuredY);
        System.out.println("possibleX = " + possibleX);
        System.out.println("possibleY = " + possibleY);
        
        //Test if all hit decks of the ship and the possible deck are neighbors. 
        //Prevent gaps between ship and possible deck.
        //First - horizontal ship, else - vertical.
        int dx = 0;
        int dy = 0;
        if(injuredX < possibleX){
            dx = 1;
        }else if(injuredX > possibleX){
            dx = -1;
        }else{
            dx = 0;
        }
        if(injuredY < possibleY){
            dy = 1;
        }else if(injuredY > possibleY){
            dy = -1;
        }else{
            dy = 0;
        }
        int stepsBetweenInjuredAndPossible = Math.abs(possibleX-injuredX) + Math.abs(possibleY-injuredY);
        for(int i=0; i<stepsBetweenInjuredAndPossible; i++){
            Coordinates checkDeck = new Coordinates(injuredX+i*dx, injuredY+i*dy);
            assertTrue(instance.getCell(checkDeck).isShip());
        }
    }
    
    @Test
    public void testGetRandomNotFiredCellCoordinates() {
        System.out.println("getRandomNotFiredCellCoordinates");
        GameBoard instance = new GameBoard();
        GameBoard spyInstance = spy(instance);
        
        //Generate random missed cells on board
        Cell mockCellMissed = mock(Cell.class);
        when(mockCellMissed.isShot()).thenReturn(true);
        when(mockCellMissed.isShip()).thenReturn(false);
        for (int i = 0; i < 10; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
            int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
            when(spyInstance.getCell(x, y)).thenReturn(mockCellMissed);
        }
        //TODO I don't like this since it invokes another method from the class to be used. The current method we are testing is not isolated.
        spyInstance.refreshFreeCellList();

        Coordinates resultCoordinates = spyInstance.getAi().getRandomNotFiredCellCoordinates();
        boolean result = spyInstance.getCell(resultCoordinates).isShot();
        boolean expResult = false;
        assertEquals(expResult, result);
    }
}