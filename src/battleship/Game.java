package battleship;

public class Game {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player1.generateRandomMap();
        player2.generateRandomMap();
        
        System.out.println("------------------Player1------------------");
        player1.printGameBoard();
        System.out.println("------------------Player2------------------");
        player2.printGameBoard();
        
        System.out.println("------------------START GAME------------------");
        while(attackPlayer(player1) && attackPlayer(player2));
    }

    /**
     * @return true - continue game; false - gameOver
     */
    private static boolean attackPlayer(Player player) {
        //Shooting
        boolean anotherTry;
        do{
            anotherTry = player.fireRandomCell();
            
            System.out.println("------------------" + player.getName() + "------------------");
            player.printGameBoard();
            if(player.isDefeated()){
                System.out.println("Game Over. " + player.getName() + " is defeated");
                return false;
            }
        }while(anotherTry);
        return true;
    }
}