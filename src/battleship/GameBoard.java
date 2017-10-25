package battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;

public class GameBoard {
    //===============================Fields=====================================
    public final static int COLUMNS_NUMBER = 10;
    public final static int ROWS_NUMBER = 10;
    private Cell[][] mCell;
    private LinkedList<Coordinates> mFreeCellList;
    private final Ai mAi;
    
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
        mAi = new Ai();
    }
    //===============================Private Methods============================ 
    private void excludeShipFromFreeCellList(final Ship ship) {
        Coordinates[] decksCoordinates = ship.getCoordinates();
        for(Coordinates deck:decksCoordinates){
            mFreeCellList.remove(deck);
        }
    }    
    
    private void adjoinSingleNeighbour(final int x, final int y){
        if(areCoordinatesWithinBoard(x,y) && !getCell(x, y).isShip()){
            mCell[x][y].setAdjoined();
        }
    }

    private void occupyCells(Cell[] cells){
        for(Cell cell:cells) {
            cell.occupy();
        }
    }
    
    private boolean areNeighbors(final Coordinates cell1, final Coordinates cell2){
        int dx = cell1.getX() - cell2.getX();
        int dy = cell1.getY() - cell2.getY();
        if((Math.abs(dx) == 1 && dy == 0) ||
           (Math.abs(dy) == 1 && dx == 0)) {
            return true;
        }
        return false;
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
    
    public Ai getAi(){
        return mAi;
    }
    //===============================Public Methods=============================
    public boolean areCoordinatesWithinBoard(final int x, final int y){
        return(x >=0 && x < COLUMNS_NUMBER && y >= 0 && y < ROWS_NUMBER);
    }
    
//    public boolean isCellInjuredDeck(int x, int y){
//        return (mCell[x][y].getFlag() == Cell.HIT_DECK);
//    }
//    public boolean isCellInjuredDeck(Coordinates coordinates){
//        return isCellInjuredDeck(coordinates.getX(), coordinates.getY());
//    }
//    public boolean isCellMissed(int x, int y){
//        return(mCell[x][y].getFlag() == Cell.MISSED);
//    }
//    public boolean isCellMissed(Coordinates coordinates){
//        return isCellMissed(coordinates.getX(), coordinates.getX());
//    }
//    public boolean isCellNotShot(int x, int y){
//        return (mCell[x][y].getFlag() == Cell.NOT_CHECKED ||
//                mCell[x][y].getFlag() == Cell.OCCUPIED);
//    }
//    public boolean isCellNotShot(Coordinates coordinates){
//        return isCellNotShot(coordinates.getX(), coordinates.getY());
//    }
    
//    //TODO This couple methods explicitly say that the cell contains a deck. 
//    //It should not be available for an opponent
//    public boolean isDeck(int x, int y){
//        return (mCell[x][y].getFlag() == Cell.OCCUPIED ||
//                mCell[x][y].getFlag() == Cell.HIT_DECK);
//    }
//    public boolean isDeck(Coordinates coordinates){
//        return isDeck(coordinates.getX(), coordinates.getY());
//    }
    
    /**
     * Mark all neighbor cells to given one as CHECKED (except for occupied ones). This is created 
     * to prevent shooting ship's neighbor cells.
     * @param coordinates - cell Coordinates of a ship
     */
    public void adjoinCell(final Coordinates coordinates) {
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
    
    /**
     * Check whether the ship could be positioned according to rules of classic BattleShip.
     * @param firstCell - startCell
     * @param decksNumber - number of Decks for the ship to try positioning
     * @param isHorizontal - true = Horizontal; false = Vertical
     * @return - successfully positioned ship object or null - such ship can not be placed
     */
    public Ship tryShipPosition(Coordinates firstCell, final int decksNumber, final boolean isHorizontal){
        Coordinates[] coordinates = new Coordinates[decksNumber];
        Cell[] deckCell = new Cell[decksNumber];
        
        int tryX = firstCell.getX();
        int tryY = firstCell.getY();
        
        for(int i = 0; i < decksNumber; i++){
            if(isHorizontal)    tryX++;
            else                tryY++;
            //Check if within GameBoard bounds
            if(!areCoordinatesWithinBoard(tryX, tryY)){
                return null;
            }
            
            //Check if not placing to an occupied or neighbor cell
            if(mCell[tryX][tryY].isShip() || (!mCell[tryX][tryY].isShip() && mCell[tryX][tryY].isShot())){
                return null;
            } else{
                coordinates[i] = new Coordinates(tryX, tryY);
                deckCell[i] = mCell[tryX][tryY];
            }
        }
        
        return new Ship(coordinates, deckCell);
    }
    

    
    /**    
     * @return true - hit a deck, hit already fired deck or hit checked cell: The player has another try
     * false - missed - change player.
     */
    public boolean fireCell(final int x, final int y){
        boolean result = mCell[x][y].fire();
        refreshFreeCellList();
        return result;
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

    
    /**
     *  AI class which is responsible for generation, randomization and searches on the Board
     */
    class Ai{
        //===============================Construct==================================
        private Ai(){
        }
        //===============================Private Methods============================
        private void clearAdjoinedCellsFromBoard() {
            for(int j = 0; j < ROWS_NUMBER; j++){
                for(int i = 0; i < COLUMNS_NUMBER; i++){
                    if(getCell(i, j).isShot() && !getCell(i, j).isShip()){
                        mCell[i][j].clear();
                    }
                }
            }
        }
        
        private Coordinates searchLeft(final Coordinates injuredDeck){
            return searchTo(injuredDeck, -1, 0);
        }
        private Coordinates searchUp(final Coordinates injuredDeck){
            return searchTo(injuredDeck, 0, -1);
        }
        private Coordinates searchRight(final Coordinates injuredDeck){
            return searchTo(injuredDeck, 1, 0);
        }
        private Coordinates searchDown(final Coordinates injuredDeck){
            return searchTo(injuredDeck, 0, 1);
        }    
        /**
         * Searches for a possible deck
         * @param injuredDeck - start point of search
         * @param dx - x coordinate of search vector (allowed)
         * @param dy - y coordinate of search vector
         * @return 
         */
        private Coordinates searchTo(final Coordinates injuredDeck, final int dx, final int dy){
            int x = injuredDeck.getX();
            int y = injuredDeck.getY();
            for(int i = 1; i < 4; i++){
                if(!areCoordinatesWithinBoard(x+i*dx, y+i*dy)){
                    return null;
                }        

                int searchX = x+i*dx;
                int searchY = y+i*dy;
                //reached the Cell with MISSED flag
                if(getCell(searchX, searchY).isShot() && !getCell(searchX, searchY).isShip()){
                    return null;
                }
                //reached the Cell with HIT_DECK flag
                if(getCell(searchX, searchY).isShot() && getCell(searchX, searchY).isShip()){
                    continue;
                }
                //reached the Cell with possible deck
                if(!getCell(searchX, searchY).isShot()){
                    return new Coordinates(searchX, searchY);
                }
            }
            return null;
        }    
        
        private boolean foundHorizontalShip(Coordinates injuredDeck){ 
            int x = injuredDeck.getX();
            int y = injuredDeck.getY();
            if(GameBoard.this.areCoordinatesWithinBoard(x+1, y)
                && getCell(x+1, y).isShot() && getCell(x+1, y).isShip()){
                    return true;
            }
            if(GameBoard.this.areCoordinatesWithinBoard(x-1, y) 
                    && getCell(x-1, y).isShot() && getCell(x-1, y).isShip()){
                return true;
            }
            return false;
        }
        private boolean foundVerticalShip(Coordinates injuredDeck){
            int x = injuredDeck.getX();
            int y = injuredDeck.getY();
            if(GameBoard.this.areCoordinatesWithinBoard(x, y+1) 
                    && getCell(x, y+1).isShot() && getCell(x, y+1).isShip()){
                return true;
                
            }
            if(GameBoard.this.areCoordinatesWithinBoard(x, y-1) 
                    && getCell(x, y-1).isShot() && getCell(x, y-1).isShip()){
                return true;
            }
            return false;
        }
        //===============================Public Methods=============================
        public Ship generateRandomShip(final int decksNumber) {
            Ship ship;
            do {                
                int randomFreeCellIndex = ThreadLocalRandom.current().nextInt(0, getFreeCellList().size());
                Coordinates randomFreeCell = (Coordinates)getFreeCellList().get(randomFreeCellIndex);
                boolean isHorizontal = ThreadLocalRandom.current().nextBoolean();
                ship = tryShipPosition(randomFreeCell, decksNumber, isHorizontal);
            } while (ship == null);
            occupyCells(ship.getDeckCells());
            return ship;
        }
        
        public ArrayList<Ship> generateRandomShipsMap(){     
            ArrayList<Ship> shipArmada = new ArrayList();                
            //Classic rules 4-decks, 3-decks, 2-decks, 1-deck.
            for(int deckNumber = 4; deckNumber > 0; deckNumber--)
            {
                //Generate collection of of Ships with equal deckNumber
                for(int i = 0; i <= 4-deckNumber; i++){
                    Ship ship = generateRandomShip(deckNumber);  
                    excludeShipFromFreeCellList(ship);
                    for(Coordinates cell:ship.getCoordinates()){
                        adjoinCell(cell);
                    }
                    shipArmada.add(ship);
                }            
            }        
            clearAdjoinedCellsFromBoard();
            refreshFreeCellList();
            return shipArmada;
        }

        
        public Coordinates searchPossibleDeck(Coordinates injuredDeck){
            if(injuredDeck == null) return null;

            ArrayList<Coordinates> possibleDecks = new ArrayList<>();
            
            if(foundHorizontalShip(injuredDeck)){
                possibleDecks.add(searchLeft(injuredDeck));
                possibleDecks.add(searchRight(injuredDeck));
            } else if(foundVerticalShip(injuredDeck)){
                possibleDecks.add(searchUp(injuredDeck));
                possibleDecks.add(searchDown(injuredDeck));
            } else {
                possibleDecks.add(searchLeft(injuredDeck));
                possibleDecks.add(searchRight(injuredDeck));
                possibleDecks.add(searchUp(injuredDeck));
                possibleDecks.add(searchDown(injuredDeck));
            }
            
            //Remove all nulls
            possibleDecks.removeAll(Collections.singleton(null));
            
            //Get a random Cell from possibleDecks collection
            if(possibleDecks.size() > 0){
                return possibleDecks.get(ThreadLocalRandom.current().nextInt(0, possibleDecks.size()));
            } else {
                return null;
            }
        }
        
        public Coordinates getRandomNotFiredCellCoordinates() {
            while(true){
                int randomIndex = ThreadLocalRandom.current().nextInt(0, mFreeCellList.size());
                return mFreeCellList.get(randomIndex);
            }
        }
    }
}