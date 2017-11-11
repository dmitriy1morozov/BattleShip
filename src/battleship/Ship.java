package battleship;

public class Ship {
    //===============================Fields=====================================
    private final Cell[] mDeck;
    private final Coordinates[] mDeckCoordinates;
    private final boolean mIsHorizontal;
    
    //===============================Construct==================================
    public Ship(Coordinates[] coordinates, Cell[] cells) {
        this.mDeck = cells;
        this.mDeckCoordinates = coordinates;
        if(coordinates.length > 1){
            mIsHorizontal = coordinates[0].getX() != coordinates[1].getX();           
        }
        else{
            mIsHorizontal = false;
        }
    }

    //===============================Getters====================================
    public Cell[] getDeckCells() {
        return mDeck;
    }
    
    public Coordinates[] getCoordinates(){
        return mDeckCoordinates;
    }
    //===============================Public Methods=============================
    public boolean isHorizontal(){
        return mIsHorizontal;
    }
    
    public boolean isDrown() {
        for(Cell deck:mDeck){
            if(deck.isShip() && !deck.isShot()) return false;
        }
        return true;
    }
    
    /**
     * @return true - only if the ship is hit at least once and is not drown
     */
    public boolean isInjured(){
        if(isDrown())return false;
        for(Cell deck:mDeck){
            if(deck.isShip() && deck.isShot()) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Ship)) return false;
        Ship ship = (Ship)obj;
        
        if(ship.getCoordinates().length != this.getCoordinates().length 
                || ship.getDeckCells().length != this.getDeckCells().length){
            return false;
        }
        
        for (int i = 0; i < this.getCoordinates().length; i++) {
            Coordinates coordinates1 = ship.getCoordinates()[i];
            Coordinates coordinates2 = this.getCoordinates()[i];
            Cell cell1 = ship.getDeckCells()[i];
            Cell cell2 = this.getDeckCells()[i];
            if(!coordinates1.equals(coordinates2) 
                    || cell1.isShip()!= cell2.isShip() || cell1.isShot()!= cell2.isShot()){
                return false;
            }           
        }
        return true;
    }    
}