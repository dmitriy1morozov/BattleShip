package battleship;

//The class is used to describe Cell/Deck coordinates
public class Coordinates {
    //===============================Fields=====================================
    private final int mX;
    private final int mY;
    //===============================Construct==================================
    public Coordinates(final int x, final int y) {
        this.mX = x;
        this.mY = y;
    }
    //===============================Getters====================================
    public int getX() {
        return mX;
    }

    public int getY() {
        return mY;
    }
    
    public boolean equals(Coordinates coord){
        return(coord.getX() == mX && coord.getY() == mY);
    }
    
    public Coordinates stepRight(int steps){
        return (new Coordinates(mX+steps, mY));
    }
    public Coordinates stepLeft(int steps){
        return (new Coordinates(mX-steps, mY));
    }
    public Coordinates stepUp(int steps){
        return(new Coordinates(mX, mY-steps));
    }
    public Coordinates stepDown(int steps){
        return(new Coordinates(mX, mY+steps));
    }
}