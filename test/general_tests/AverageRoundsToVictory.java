package general_tests;

import battleship.Player;
import java.util.Iterator;
import java.util.LinkedList;
import org.junit.Test;

public class AverageRoundsToVictory {
    
    @Test
    public void testAverageRoundsToVictory() {
        System.out.println("testAverageRoundsToVictory");
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
            if(player.isDefeated())return false;
        }while(anotherTry);
        return true;
    }   
}
