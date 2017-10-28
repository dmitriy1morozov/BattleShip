package battleship;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;


public class Player {
    //===============================Fields=====================================
    private final String mName;
    private final GameBoard mBoard;
    private ArrayList<Ship> mShipList;
    
//===============================Construct==================================
    public Player(String name) {
        this.mName = name;
        this.mBoard = new GameBoard();
        this.mShipList = new ArrayList();
    } 
    //TODO this Constructor is used for Unit tests only. We need it to generate a test-suiting board.
    //This is a security weakness, because one can create a suiting board to cheat during game.
    public Player(String name, GameBoard gameBoard) {
        this.mName = name;
        this.mBoard = gameBoard;
        this.mShipList = new ArrayList();
    }
    
    //===============================Private Methods============================
    private void clearAdjoinedCellsFromBoard() {
        for(int j = 0; j < GameBoard.ROWS_NUMBER; j++){
            for(int i = 0; i < GameBoard.COLUMNS_NUMBER; i++){
                Cell curCell = mBoard.getCell(i, j);
                if(curCell.isShot() && !curCell.isShip()){
                    curCell.clear();
                }
            }
        }
    }
    
    /**
     * Removes drown ships if found
     */
    private void refreshShipList(){
        Iterator<Ship> iterator = mShipList.iterator();
        while(iterator.hasNext()){
            Ship ship = iterator.next();
            if(ship.isDrown()){
                for(Coordinates cell:ship.getCoordinates()){
                    mBoard.markAdjoinCells(cell);
                }
                iterator.remove();
            }            
        }
    }
    
    /**
     * Searches for an already injured ship, gets its injured deck coordinates.
     * Then searches for a possible deck nearby in 4 directions (Up, Down, Left, Right)
     * @return null = nothing found. Coordinates = found possible deck's coordinates
     */
    private Coordinates searchPossibleDeck(){
        Ship injuredShip = searchInjuredShip();
        if(injuredShip == null) return null;
        Coordinates injuredDeck = searchInjuredDeckCoordinates(injuredShip);
        if(injuredDeck == null) return null;

        ArrayList<Coordinates> possibleDecks = new ArrayList<>();
        if(isFoundHorizontalShip(injuredDeck)){
            possibleDecks.add(searchDeck(injuredDeck, 1, 0));
            possibleDecks.add(searchDeck(injuredDeck, -1, 0));
        } else if(isFoundVerticalShip(injuredDeck)){
            possibleDecks.add(searchDeck(injuredDeck, 0, 1));
            possibleDecks.add(searchDeck(injuredDeck, 0, -1));
        } else {
            possibleDecks.add(searchDeck(injuredDeck, 1, 0));
            possibleDecks.add(searchDeck(injuredDeck, -1, 0));
            possibleDecks.add(searchDeck(injuredDeck, 0, 1));
            possibleDecks.add(searchDeck(injuredDeck, 0, -1));
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
    private Coordinates searchInjuredDeckCoordinates(final Ship injuredShip){
        if(injuredShip == null) return null;
        
        Cell[] deckCell = injuredShip.getDeckCells();
        for(int i =0; i < deckCell.length; i++){
            if(deckCell[i].isShot() && deckCell[i].isShip()){
                return injuredShip.getCoordinates()[i];
            }
        }
        return null;
    }    
    private Ship searchInjuredShip(){
        for(Ship ship:mShipList){
            if(ship.isInjured()){
                return ship;
            }
        }
        return null;
    }
    /**
     * Searches for a possible deck
     * @param injuredDeck - starting point of search
     * @param dx - x-coordinate of search vector. Allowed input = [-1,1]
     * @param dy - y-coordinate of search vector. Allowed input = [-1,1]
     * @return 
     */
    private Coordinates searchDeck(final Coordinates injuredDeck, final int dx, final int dy){
        int searchX = injuredDeck.getX();
        int searchY = injuredDeck.getY();
        for(int i = 1; i < 4; i++){
            searchX +=dx;
            searchY +=dy;
            if(!mBoard.areCoordinatesWithinBoard(searchX, searchY)){
                return null;
            }        
            Cell reachedCell = mBoard.getCell(searchX, searchY);
            
            //reached the Cell with MISSED flag
            if(reachedCell.isShot() && !reachedCell.isShip()){
                return null;
            }
            //reached the Cell with HIT_DECK flag
            if(reachedCell.isShot() && reachedCell.isShip()){
                continue;
            }
            //reached the Cell with possible deck
            if(!reachedCell.isShot()){
                return new Coordinates(searchX, searchY);
            }
        }
        return null;
    }
    private boolean isFoundHorizontalShip(final Coordinates injuredDeck){ 
        if(mBoard.areCoordinatesWithinBoard(injuredDeck.stepRight(1))){
            Cell stepRight = mBoard.getCell(injuredDeck.stepRight(1));
            if(stepRight.isShot() && stepRight.isShip()){
                return true;
            }
        }
        
        if(mBoard.areCoordinatesWithinBoard(injuredDeck.stepLeft(1))){
            Cell stepLeft = mBoard.getCell(injuredDeck.stepLeft(1));
            if(stepLeft.isShot() && stepLeft.isShip()){
                return true;
            }
        }
        return false;
    }
    private boolean isFoundVerticalShip(final Coordinates injuredDeck){
        if(mBoard.areCoordinatesWithinBoard(injuredDeck.stepUp(1))){
            Cell stepUp = mBoard.getCell(injuredDeck.stepUp(1));
            if(stepUp.isShot() && stepUp.isShip()){
                return true;
            }
        }
        
        if(mBoard.areCoordinatesWithinBoard(injuredDeck.stepDown(1))){
            Cell stepDown = mBoard.getCell(injuredDeck.stepDown(1));
            if(stepDown.isShot() && stepDown.isShip()){
                return true;
            }
        }
        return false;
    }
    //===============================Public Methods=================================================
    //===============================General Player methods=================================================
    public String getName(){
        return this.mName;
    }
    public boolean isDefeated(){
        return mShipList.isEmpty();
    }
    public void printGameBoard(){
        mBoard.print();
    }
     
    /**    
     * @return true - hit a deck, hit already fired deck or hit checked cell: The player has another try
     * false - missed - change player.
     */
    public boolean fireCell(final int x, final int y){
        boolean result = mBoard.getCell(x, y).fire();
        mBoard.refreshFreeCellList();
        if(result) refreshShipList();
        return result;
    }
    public boolean fireCell(final Coordinates coord){
        return fireCell(coord.getX(), coord.getY());
    }
    
    /**
     * Check whether the ship could be positioned according to rules of classic BattleShip.
     * @param firstCell - startCell
     * @param decksNumber - number of Decks for the ship to try positioning
     * @param isHorizontal - true = Horizontal; false = Vertical
     * @return - successfully positioned ship object or null - such ship can not be placed
     */
    public Ship tryShipPosition(final Coordinates firstCell, final int decksNumber, final boolean isHorizontal){
        Coordinates[] resultCoordinates = new Coordinates[decksNumber];
        Cell[] resultDeckCell = new Cell[decksNumber];
        
        int tryX = firstCell.getX();
        int tryY = firstCell.getY();
        
        for(int i = 0; i < decksNumber; i++){
            //Check if within GameBoard bounds
            if(!mBoard.areCoordinatesWithinBoard(tryX, tryY)){
                return null;
            }
            
            Cell tryCell = mBoard.getCell(tryX, tryY);
            //Check if not placing to an occupied or neighbor cell
            if(tryCell.isShip() || (!tryCell.isShip() && tryCell.isShot())){
                return null;
            } else{
                resultCoordinates[i] = new Coordinates(tryX, tryY);
                resultDeckCell[i] = tryCell;
            }
            //Iterating to the next deck
            if(isHorizontal)    tryX++;
            else                tryY++;
        }
        return new Ship(resultCoordinates, resultDeckCell);
    }
    
    /**
     * 
     * @param ship
     * @return true = successfully placed, false = cannot place such ship on Board.
     */
    public boolean placeShip(Ship ship){
        if(ship == null) return false;
        Cell[] cells = ship.getDeckCells();
        for(Cell cell:cells){
            cell.occupy();
        }
        return true;
    }   
    //===================================Ai specific methods========================================
    public Ship generateRandomShip(final int decksNumber) {
        Ship generatedShip;
        while(true) {                
            int randomFreeCellIndex = ThreadLocalRandom.current().nextInt(0, mBoard.getFreeCellList().size());
            Coordinates randomFreeCell = (Coordinates)mBoard.getFreeCellList().get(randomFreeCellIndex);
            boolean isHorizontal = ThreadLocalRandom.current().nextBoolean();
            generatedShip = tryShipPosition(randomFreeCell, decksNumber, isHorizontal);
            //TODO Add a method to check whether the Board is able to accomodate a ship of deckNumber
            if(generatedShip != null) return generatedShip;
        }
    }

    public void generateRandomMap(){     
        //Classic rules 4-decks, 3-decks, 2-decks, 1-deck.
        for(int deckNumber = 4; deckNumber > 0; deckNumber--)
        {
            //Generate collection of of Ships with equal deckNumber
            for(int i = 0; i <= 4-deckNumber; i++){
                Ship ship = generateRandomShip(deckNumber); 
                placeShip(ship);
                mShipList.add(ship);
                for(Coordinates deck:ship.getCoordinates()){
                    mBoard.markAdjoinCells(deck);
                }
                mBoard.refreshFreeCellList();
            }            
        }        
        clearAdjoinedCellsFromBoard();
    }

    public Coordinates getRandomNotFiredCellCoordinates() {
        while(true){
            int randomIndex = ThreadLocalRandom.current().nextInt(0, mBoard.getFreeCellList().size());
            return (Coordinates)mBoard.getFreeCellList().get(randomIndex);
        }
    }
    
    /**    
     * @return true - hit a deck, hit already fired deck or hit checked cell: The player has another try
     * false - missed - change player.
     */
    public boolean fireRandomCell(){
        Coordinates possibleDeck = searchPossibleDeck();
        if(possibleDeck == null) possibleDeck = getRandomNotFiredCellCoordinates();
        return fireCell(possibleDeck);
    }
}