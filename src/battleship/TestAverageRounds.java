package battleship;

import java.util.Iterator;
import java.util.LinkedList;

public class TestAverageRounds {
    public static void main(String[] args) {
        LinkedList<Integer> movesStats = new LinkedList<>();
        for(int i = 0; i < 1000; i++){
            int moves = singleGame();
            movesStats.add(moves);
        }
        
        int sum = 0;
        Iterator iterator = movesStats.iterator();
        while(iterator.hasNext()){
            sum+=(int)iterator.next();
        }
        double averageDefeatRounds = sum / movesStats.size();
        
        System.out.println("Average defeat rounds = " + averageDefeatRounds);
    }
    
    private static int singleGame(){
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player1.generateRandomMap();
        player2.generateRandomMap();
        
        int moves = 0;
        while(attackPlayer(player1) && attackPlayer(player2)){
            moves++;
        }
        return moves;
    }
    
    /**
     * @return true - continue game
     * @return false - game is over
     */
    private static boolean attackPlayer(Player player){
        //Shooting
        boolean anotherTry;
        do{
            anotherTry = player.fireRandomCell();
            
            if(player.isDefeated()){
                System.out.println("Game Over. " + player.getName() + " is defeated");
                return false;
            }
        }while(anotherTry);
        
        return true;
    }
}
