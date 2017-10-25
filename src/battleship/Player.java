package battleship;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {
    //===============================Fields=====================================
    private final String mName;
    private GameBoard mGameBoard;
    private ArrayList<Ship> mShipList;
    //===============================Construct==================================
    public Player(String name) {
        this.mName = name;
        this.mGameBoard = new GameBoard();
    }    
    //===============================Private Methods============================ 
    private void removeDrownShips(){
        Iterator<Ship> iterator = mShipList.iterator();
        while(iterator.hasNext()){
            Ship ship = iterator.next();
            if(ship.isDrown()){
                for(Coordinates cell:ship.getCoordinates()){
                    mGameBoard.adjoinCell(cell);
                }
                iterator.remove();
            }            
        }
    }
    
    private Ship findInjuredShip(){
        for(Ship ship:mShipList){
            if(ship.isInjured()){
                return ship;
            }
        }
        return null;
    }
    
    private Coordinates findInjuredDeckCoordinates(){
        Ship injuredShip = findInjuredShip();
        if(injuredShip == null) return null;
        
        Cell[] deckCell = injuredShip.getDeckCells();
        for(int i =0; i < deckCell.length; i++){
            if(deckCell[i].isShot() && deckCell[i].isShip()){
                return injuredShip.getCoordinates()[i];
            }
        }
        return null;
    }
    //===============================Public Methods=============================
    public String getName(){
        return this.mName;
    }
    
    public void playRandomMap(){
        mShipList = mGameBoard.getAi().generateRandomShipsMap();
    }

    public Coordinates searchPossibleDeck(){
        Coordinates injuredDeck = findInjuredDeckCoordinates();
        return mGameBoard.getAi().searchPossibleDeck(injuredDeck);        
    }
    
    /**    
     * @return true - hit a deck, hit already fired deck or hit checked cell: The player has another try
     * false - missed - change player.
     */
    public boolean fireRandomCell(){
        Coordinates randomCell = mGameBoard.getAi().getRandomNotFiredCellCoordinates();
        return fireCell(randomCell.getX(), randomCell.getY());
    }

    /**    
     * @return true - hit a deck, hit already fired deck or hit checked cell: The player has another try
     * false - missed - change player.
     */
    public boolean fireCell(final int x, final int y){
        if(mGameBoard.fireCell(x, y)){
            removeDrownShips();
            return true;
        }
        return false;
    }
    
    public boolean isDefeated(){
        return mShipList.isEmpty();
    }
    
    public void printGameBoard(){
        mGameBoard.print();
    }
}
