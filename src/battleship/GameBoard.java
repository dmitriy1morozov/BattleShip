package battleship;

import java.util.LinkedList;

public class GameBoard {
    //===============================Fields=====================================
    public final static int COLUMNS_NUMBER = 10;
    public final static int ROWS_NUMBER = 10;
    
    private final Cell[][] mCell;
    private LinkedList<Coordinates> mFreeCellList;
    
    //===============================Construct==================================
    public GameBoard() {
        mCell = new Cell[COLUMNS_NUMBER][ROWS_NUMBER];
        mFreeCellList = new LinkedList<>();
        for(int j = 0; j < ROWS_NUMBER; j++){
            for(int i = 0; i < COLUMNS_NUMBER; i++){
                mCell[i][j] = new Cell();
                mFreeCellList.add(new Coordinates(i,j));
            }
        }
    }
    //===============================Private Methods============================ 
    private void adjoinSingleNeighbour(final int x, final int y){
        if(areCoordinatesWithinBoard(x,y) && !getCell(x, y).isShip()){
            mCell[x][y].setAdjoined();
        }
    }
    
    //===============================Getter=====================================
    public LinkedList getFreeCellList(){
        return mFreeCellList;
    }
    
    public Cell getCell(int x, int y){
        return mCell[x][y];
    }    
    public Cell getCell(Coordinates coordinates){
        return getCell(coordinates.getX(), coordinates.getY());
    }
    
    //===============================Public Methods=============================
    public boolean areCoordinatesWithinBoard(final int x, final int y){
        return(x >=0 && x < COLUMNS_NUMBER && y >= 0 && y < ROWS_NUMBER);
    }
    public boolean areCoordinatesWithinBoard(final Coordinates coordinates){
        int x = coordinates.getX();
        int y = coordinates.getY();
        return areCoordinatesWithinBoard(x,y);
    }
    
    /**
     * Mark all neighbor cells to given one as CHECKED (except for occupied ones). This is created 
     * to prevent shooting ship's neighbor cells.
     * @param coordinates - cell Coordinates of a ship
     */
    public void markAdjoinCells(final Coordinates coordinates) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        
        adjoinSingleNeighbour(x-1, y-1);
        adjoinSingleNeighbour(x, y-1);
        adjoinSingleNeighbour(x+1, y-1);
        adjoinSingleNeighbour(x+1, y);
        adjoinSingleNeighbour(x+1, y+1);
        adjoinSingleNeighbour(x, y+1);
        adjoinSingleNeighbour(x-1, y+1);
        adjoinSingleNeighbour(x-1, y);
    }
    
    public void refreshFreeCellList(){
        mFreeCellList.clear();
        for(int j = 0; j < ROWS_NUMBER; j++){
            for(int i = 0; i < COLUMNS_NUMBER; i++){
                if(!getCell(i, j).isShot()){
                    mFreeCellList.add(new Coordinates(i,j));
                }
            }
        }
    }
    
    //--------------------------------------------------------------------------
    public void print(){
        char[] dictionary = "   ABCDEFGHIJ".toCharArray();
        for(char letter:dictionary){
            System.out.print(letter);
        }
        System.out.println("");
        
        for(int j = 0; j < ROWS_NUMBER; j++){
            String rowNumber;
            if(j+1 < 10){
                rowNumber = " " + (j+1) + " ";
            } else{
                rowNumber = (j+1) + " ";
            }
            System.out.print(rowNumber);
            
            for(int i = 0; i < COLUMNS_NUMBER; i++){
                if(!getCell(i,j).isShot() && getCell(i,j).isShip()) System.out.print("+");
                if(!getCell(i,j).isShot() && !getCell(i,j).isShip()) System.out.print(".");
                if(getCell(i,j).isShot() && getCell(i,j).isShip()) System.out.print("X");
                if(getCell(i,j).isShot() && !getCell(i,j).isShip()) System.out.print("o");
            }
            System.out.println();
        }
    }
}