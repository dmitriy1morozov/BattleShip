package battleship;

public class Cell {
    //===============================Fields=====================================
    protected boolean mIsShot;
    protected boolean mIsShip;
    //===============================Construct==================================
    public Cell() {
        mIsShot = false;
        mIsShip = false;
    }
    //===============================Public Methods=============================
    public boolean isShot(){
        return mIsShot;
    }
    
    public boolean isShip(){
        return mIsShip;
    }
    
    public void occupy(){
        mIsShot = false;
        mIsShip = true;
    }
    public void clear(){
        mIsShot = false;
        mIsShip = false;
    }
    
    /**    
     * @return true - hit a deck, hit already fired deck or hit checked cell: The player has another try
     * false - missed - change player.
     */
    public boolean fire(){
        //Hit already shot Cell
        if(mIsShot) return true;
        //Change flag
        mIsShot = true;
        //If missed - next player, if injured a ship - one more try
        return mIsShip;
    }
    
    public void setAdjoined(){
        mIsShot = true;
        mIsShip = false;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Cell)) return false;
        Cell cell = (Cell)obj;
        
        if(cell.isShip() == this.isShip() && cell.isShot() == this.isShot()){
            return true;
        } else{
            return false;
        }
    }
    
    @Override
    public String toString(){
        return "(isShot = " + mIsShot + " ; isShip = " + mIsShip + ")";
    }
}