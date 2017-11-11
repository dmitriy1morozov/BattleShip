package battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class GameBoardTest {
    
    /**
    *    ABCDEFGHIJ
    *   1 X..XXX...X
    *   2 X.........
    *   3 ..X.......
    *   4 X........X
    *   5 X........X
    *   6 .........X
    *   7 .....X...X
    *   8 ..........
    *   9 ..........
    *  10 X..XXX..XX
    */
    private class GameBoardSpy_AllPositions extends GameBoard{
        public GameBoardSpy_AllPositions() {
            super();
            mCell[0][0] = new CellSpy(true, true);
            mCell[0][1] = new CellSpy(true, true);

            mCell[0][3] = new CellSpy(true, true);
            mCell[0][4] = new CellSpy(true, true);

            mCell[0][ROWS_NUMBER-1] = new CellSpy(true, true);

            mCell[3][0] = new CellSpy(true, true);
            mCell[4][0] = new CellSpy(true, true);
            mCell[5][0] = new CellSpy(true, true);

            mCell[COLUMNS_NUMBER-1][0] = new CellSpy(true, true);

            mCell[COLUMNS_NUMBER-1][3] = new CellSpy(true, true);
            mCell[COLUMNS_NUMBER-1][4] = new CellSpy(true, true);
            mCell[COLUMNS_NUMBER-1][5] = new CellSpy(true, true);
            mCell[COLUMNS_NUMBER-1][6] = new CellSpy(true, true);

            mCell[COLUMNS_NUMBER-1][ROWS_NUMBER-1] = new CellSpy(true, true);
            mCell[COLUMNS_NUMBER-2][ROWS_NUMBER-1] = new CellSpy(true, true);

            mCell[3][ROWS_NUMBER-1] = new CellSpy(true, true);
            mCell[4][ROWS_NUMBER-1] = new CellSpy(true, true);
            mCell[5][ROWS_NUMBER-1] = new CellSpy(true, true);

            mCell[2][2] = new CellSpy(true, true);

            mCell[5][6] = new CellSpy(true, true);
        }
    }
    
    /**
     * Returns the difference between two boards: the Coordinates which have different contents
     * E.g.: gameBoard1.getCell(0, 0) = missedCell
     * E.g.: gameBoard2.getCell(0, 0) = emptyCell
     * @param gameBoard1 - comparing board1
     * @param gameBoard2 - comparing board2
     * @return Coordinates ArrayList of different Cells
     */
    private ArrayList<Coordinates> difference(GameBoard gameBoard1, GameBoard gameBoard2){
        ArrayList<Coordinates> difference = new ArrayList();
        for(int j = 0; j < GameBoard.ROWS_NUMBER; j++){
            for(int i = 0; i < GameBoard.COLUMNS_NUMBER; i++){
                if(!gameBoard1.getCell(i, j).equals(gameBoard2.getCell(i, j))){
                    difference.add(new Coordinates(i, j));
                }
            }
        }
        return difference;
    }
    
//--------------------------------------------------------------------------------------------------    

    @Test
    public void testAreCoordinatesWithinBoard_true() {
        System.out.println("areCoordinatesWithinBoard");
        int x = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
        int y = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
        
        GameBoard instance = new GameBoard();
        boolean result = instance.areCoordinatesWithinBoard(x, y);
        assertTrue(result);
    }
    @Test
    public void testAreCoordinatesWithinBoard_false() {
        System.out.println("areCoordinatesWithinBoard");
        int x0 = ThreadLocalRandom.current().nextInt(0, GameBoard.COLUMNS_NUMBER);
        int y0 = ThreadLocalRandom.current().nextInt(0, GameBoard.ROWS_NUMBER);
        int x1 = ThreadLocalRandom.current().nextInt(GameBoard.COLUMNS_NUMBER, GameBoard.COLUMNS_NUMBER + 100);
        int y1 = ThreadLocalRandom.current().nextInt(GameBoard.ROWS_NUMBER, GameBoard.ROWS_NUMBER + 100);
        int x2 = ThreadLocalRandom.current().nextInt(-100, 0);
        int y2 = ThreadLocalRandom.current().nextInt(-100, 0);
        int xOptions[] = {x0, x1, x2};
        int yOptions[] = {y0, y1, y2};
        int randomIndexX;
        int randomIndexY;
        do{
            randomIndexX = ThreadLocalRandom.current().nextInt(xOptions.length);
            randomIndexY = ThreadLocalRandom.current().nextInt(yOptions.length);
        }while(randomIndexX==0 && randomIndexY==0);
        int x = xOptions[randomIndexX];
        int y = yOptions[randomIndexY];
        
        GameBoard instance = new GameBoard();
        boolean result = instance.areCoordinatesWithinBoard(x, y);
        assertFalse(result);
    }

    /**
     *    ABCDEFGHIJ
     *   1 Xo.XXX...X
     *   2 Xo........
     *   3 ooX.......
     *   4 X........X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_LeftTopCorner() {
        System.out.println("markAdjoinCells_LeftTopCorner");
        
        GameBoard standard = new GameBoardSpy_AllPositions();        
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(0, 0));
        instance.markAdjoinCells(new Coordinates(0, 1));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(1, 0));
        expResult.add(new Coordinates(1, 1));
        expResult.add(new Coordinates(0, 2));
        expResult.add(new Coordinates(1, 2));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }        
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 X.oXXXo..X
     *   2 X.ooooo...
     *   3 ..X.......
     *   4 X........X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_TopBorder() {
        System.out.println("markAdjoinCells_TopBorder");
        
        GameBoard standard = new GameBoardSpy_AllPositions();        
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(3, 0));
        instance.markAdjoinCells(new Coordinates(4, 0));
        instance.markAdjoinCells(new Coordinates(5, 0));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(2, 0));
        expResult.add(new Coordinates(6, 0));
        expResult.add(new Coordinates(2, 1));
        expResult.add(new Coordinates(3, 1));
        expResult.add(new Coordinates(4, 1));
        expResult.add(new Coordinates(5, 1));
        expResult.add(new Coordinates(6, 1));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }    
    }
    
     /**
     *    ABCDEFGHIJ
     *   1 X..XXX..oX
     *   2 X.......oo
     *   3 ..X.......
     *   4 X........X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_RightTopCorner() {
        System.out.println("markAdjoinCells_RightTopCorner");
        
        GameBoard standard = new GameBoardSpy_AllPositions();        
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 0));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 0));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 1));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 1));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 X..XXX...X
     *   2 X.........
     *   3 ..X.....oo
     *   4 X.......oX
     *   5 X.......oX
     *   6 ........oX
     *   7 .....X..oX
     *   8 ........oo
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_RightBorder() {
        System.out.println("markAdjoinCells_RightBorder");
        
        GameBoard standard = new GameBoardSpy_AllPositions();        
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 3));
        instance.markAdjoinCells(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 4));
        instance.markAdjoinCells(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 5));
        instance.markAdjoinCells(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 6));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 2));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 2));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 3));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 4));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 5));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 6));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-2, 7));
        expResult.add(new Coordinates(GameBoard.COLUMNS_NUMBER-1, 7));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 X..XXX...X
     *   2 X.........
     *   3 ..X.......
     *   4 X........X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 .......ooo
     *  10 X..XXX.oXX
     */
    @Test
    public void testMarkAdjoinCells_RightBottomCorner() {
        System.out.println("markAdjoinCells_RightBottomCorner");
        
        GameBoard standard = new GameBoardSpy_AllPositions();
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(GameBoard.COLUMNS_NUMBER-1, GameBoard.ROWS_NUMBER-1));
        instance.markAdjoinCells(new Coordinates(GameBoard.COLUMNS_NUMBER-2, GameBoard.ROWS_NUMBER-1));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(7, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(8, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(9, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(7, GameBoard.ROWS_NUMBER-1));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 X..XXX...X
     *   2 X.........
     *   3 ..X.......
     *   4 X........X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 ..ooooo...
     *  10 X.oXXXo.XX
     */
    @Test
    public void testMarkAdjoinCells_BottomBorder() {
        System.out.println("markAdjoinCells_BottomBorder");
        
        GameBoard standard = new GameBoardSpy_AllPositions();
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(3, GameBoard.ROWS_NUMBER-1));
        instance.markAdjoinCells(new Coordinates(4, GameBoard.ROWS_NUMBER-1));
        instance.markAdjoinCells(new Coordinates(5, GameBoard.ROWS_NUMBER-1));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(2, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(3, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(4, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(5, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(6, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(2, GameBoard.ROWS_NUMBER-1));
        expResult.add(new Coordinates(6, GameBoard.ROWS_NUMBER-1));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 X..XXX...X
     *   2 X.........
     *   3 ..X.......
     *   4 X........X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 oo........
     *  10 Xo.XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_LeftBottomCorner() {
        System.out.println("markAdjoinCells_LeftBottomCorner");
        
        GameBoard standard = new GameBoardSpy_AllPositions();
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(0, GameBoard.ROWS_NUMBER-1));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(0, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(1, GameBoard.ROWS_NUMBER-2));
        expResult.add(new Coordinates(1, GameBoard.ROWS_NUMBER-1));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 X..XXX...X
     *   2 X.........
     *   3 ooX.......
     *   4 Xo.......X
     *   5 Xo.......X
     *   6 oo.......X
     *   7 .....X...X
     *   8 ..........
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_LeftBorder() {
        System.out.println("markAdjoinCells_LeftBorder");
        
        GameBoard standard = new GameBoardSpy_AllPositions();
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(0, 3));
        instance.markAdjoinCells(new Coordinates(0, 4));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(0, 2));
        expResult.add(new Coordinates(1, 2));
        expResult.add(new Coordinates(1, 3));
        expResult.add(new Coordinates(1, 4));
        expResult.add(new Coordinates(0, 5));
        expResult.add(new Coordinates(1, 5));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 X..XXX...X
     *   2 Xooo......
     *   3 .oXo......
     *   4 Xooo.....X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_Innder1() {
        System.out.println("markAdjoinCells_Innder1");
        
        GameBoard standard = new GameBoardSpy_AllPositions();
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(2, 2));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(1, 1));
        expResult.add(new Coordinates(2, 1));
        expResult.add(new Coordinates(3, 1));
        expResult.add(new Coordinates(1, 2));
        expResult.add(new Coordinates(3, 2));
        expResult.add(new Coordinates(1, 3));
        expResult.add(new Coordinates(2, 3));
        expResult.add(new Coordinates(3, 3));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    /**
     *    ABCDEFGHIJ
     *   1 X..XXX...X
     *   2 X.........
     *   3 ..X.......
     *   4 X........X
     *   5 X........X
     *   6 ....ooo..X
     *   7 ....oXo..X
     *   8 ....ooo...
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_Innder2() {
        System.out.println("markAdjoinCells_Innder2");
        
        GameBoard standard = new GameBoardSpy_AllPositions();
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(5, 6));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(4, 5));
        expResult.add(new Coordinates(5, 5));
        expResult.add(new Coordinates(6, 5));
        expResult.add(new Coordinates(4, 6));
        expResult.add(new Coordinates(6, 6));
        expResult.add(new Coordinates(4, 7));
        expResult.add(new Coordinates(5, 7));
        expResult.add(new Coordinates(6, 7));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
    /**
     *    ABCDEFGHIJ
     *   1 Xo.XXX...X
     *   2 Xooo......
     *   3 ooXo......
     *   4 Xooo.....X
     *   5 X........X
     *   6 .........X
     *   7 .....X...X
     *   8 ..........
     *   9 ..........
     *  10 X..XXX..XX
     */
    @Test
    public void testMarkAdjoinCells_CrossedAdjoins() {
        System.out.println("markAdjoinCells_CrossedAdjoins");
        
        GameBoard standard = new GameBoardSpy_AllPositions();
        GameBoard instance = new GameBoardSpy_AllPositions();
        instance.markAdjoinCells(new Coordinates(0, 0));
        instance.markAdjoinCells(new Coordinates(0, 1));
        instance.markAdjoinCells(new Coordinates(2, 2));
        ArrayList result = difference(instance, standard);

        ArrayList<Coordinates> expResult = new ArrayList();
        expResult.add(new Coordinates(1, 0));
        expResult.add(new Coordinates(1, 1));
        expResult.add(new Coordinates(2, 1));
        expResult.add(new Coordinates(3, 1));
        expResult.add(new Coordinates(0, 2));
        expResult.add(new Coordinates(1, 2));
        expResult.add(new Coordinates(3, 2));
        expResult.add(new Coordinates(1, 3));
        expResult.add(new Coordinates(2, 3));
        expResult.add(new Coordinates(3, 3));

        assertArrayEquals(expResult.toArray(), result.toArray());
        for(Coordinates coord:expResult){
            boolean isMissedCell = !instance.getCell(coord).isShip() && instance.getCell(coord).isShot();
            if(!isMissedCell){
                fail("A different cell is incorrect."
                        + "  mCell" + coord.toString() + ".isShot()==" + instance.getCell(coord).isShot()
                        + "  mCell" + coord.toString() + ".isShip()==" + instance.getCell(coord).isShip());
            }
        }
    }
    
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
        
        //checking
        Iterator<Coordinates> iterator1 = sideEffect.iterator();
        Iterator<Coordinates> iterator2 = expSideEffect.iterator();
        while(iterator1.hasNext() && iterator2.hasNext()){
            assertTrue(iterator1.next().equals(iterator2.next()));
        }
    }
}