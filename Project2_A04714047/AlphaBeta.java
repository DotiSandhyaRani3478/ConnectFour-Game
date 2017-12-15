/* Connect-Four Game(AI)
 * by
 * Akshay Chandrachood (A04742395)
 * Doti SandhyaRani (A04714047)
 */

/* Implementing alphaBeta pruning algorithm */

import java.util.Scanner;
public class AlphaBeta { 
    private Board b;
    private Scanner scan;
    private int nextMoveLocation=-1;
    private int maxDepth = 3;        
    private int func_no;
    private int nodes_generated = 0;
    private int nodes_expanded=0;
    int steps = 0;
    int number_of_steps = 0;
  
    /*Constructor creating object to Board class */
    
    public AlphaBeta(){
    	Board b = new Board();
    	State s = new State();
        this.b = b;
        scan = new Scanner(System.in);
    }
 
    public int gameStatus(Board b){
       int score1 = 0, score2 = 0;
        for(int i=5;i>=0;--i){
            for(int j=0;j<=6;++j){
                if(b.board[i][j]==' ') continue;
                
                /* Checking cells to the right */
                
                if(j<=3){
                    for(int k=0;k<4;++k){ 
                            if(b.board[i][j+k]=='X') score1++;
                            else if(b.board[i][j+k]=='0') score2++;
                            else break; 
                    }
                    if(score1==4)return 1; else if (score2==4)return 2;
                    score1 = 0; score2 = 0;
                } 
      
                /* Checking cells up */
                
                if(i>=3){
                    for(int k=0;k<4;++k){
                            if(b.board[i-k][j]=='X') score1++;
                            else if(b.board[i-k][j]=='0') score2++;
                            else break;
                    }
                    if(score1==4)return 1; else if (score2==4)return 2;
                    score1 = 0; score2 = 0;
                } 
                
                /* Checking diagonal up-right */
                
                if(j<=3 && i>= 3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j+k]=='X') score1++;
                        else if(b.board[i-k][j+k]=='0') score2++;
                        else break;
                    }
                    if(score1==4)return 1; else if (score2==4)return 2;
                    score1 = 0; score2 = 0;
                }
                
                /* Checking diagonal up-left */
                
                if(j>=3 && i>=3){
                    for(int k=0;k<4;++k){
                        if(b.board[i-k][j-k]=='X') score1++;
                        else if(b.board[i-k][j-k]=='0') score2++;
                        else break;
                    } 
                    if(score1==4)return 1; else if (score2==4)return 2;
                    score1 = 0; score2 = 0;
                }  
            }
        }
        
        for(int j=0;j<7;++j){
        	
            /* Game has not ended yet */
        	
            if(b.board[0][j]==' ')return -1;
        }
    	/*Game draw */

        return 0;
    }
    
    
    public void setEvaluationFunction(int f1){
    	func_no = f1;
    }
    
    /* evaluates the board */
    
    public int evaluateBoard(Board b){
      
    	int score = 0;
    	Evaluations e1 = new Evaluations();
    	score = e1.evaluateBoard(b.board, func_no);
        return score;
    } 
    
    /* Returns the action with value v */

    public int minimax(int depth, int turn, int alpha, int beta){
        
    		if(beta<=alpha){

        	if(turn == 1) return Integer.MAX_VALUE; 
        	else return Integer.MIN_VALUE;
        	}
        int gameStatus = gameStatus(b);
        
        if(gameStatus==1) return Integer.MAX_VALUE/2;
        else if(gameStatus==2) return Integer.MIN_VALUE/2;
        else if(gameStatus==0) return 0; 
        
        if(depth==maxDepth) return evaluateBoard(b);
        
        int maxScore=Integer.MIN_VALUE, minScore = Integer.MAX_VALUE;
                
        for(int j=0;j<=6;++j){
        	steps = steps+1;
			int currentScore = 0;
            nodes_expanded++;
            
            if(!b.isSlotEmpty(j)) continue; 
            nodes_generated++;
            
            if(turn==1){

            		nodes_generated++;
            		b.placeMove(j, 'X');
                    currentScore = minimax(depth+1, 2, alpha, beta);
                    
                    if(depth==0){
                        if(currentScore > maxScore)
                        	nextMoveLocation = j; 
                        if(currentScore == Integer.MAX_VALUE/2){
                        	b.discardMove(j);
                        	break;
                        	}
                    }
                    
                    maxScore = Math.max(currentScore, maxScore);
                    alpha = Math.max(currentScore, alpha);  
            } 
            else if(turn==2){
            		nodes_generated++;
                    b.placeMove(j, '0');
                    currentScore = minimax(depth+1, 1, alpha, beta);
                    minScore = Math.min(currentScore, minScore);
                    
                    beta = Math.min(currentScore, beta); 
            }  
            b.discardMove(j); 
            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) 
            	break; 
        }  
        return turn==1?maxScore:minScore;
    }
    
    public int getAIMove(){
        nextMoveLocation = -1;
        minimax(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return nextMoveLocation;
    }
	public void set_steps(int n_steps){
		number_of_steps =n_steps;
	}
	public int get_steps(){
		return number_of_steps;
	}
	
	/* player2 playing against player1 */
	
    public void opponetPlayer(){
       
        b.displayBoard();
        b.placeMove(3, 'X');
        b.displayBoard();
        long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
        int steps =0;
        while(true){ 
        	b.placeMove(getAIMove(), '0'); 
            b.displayBoard();
            
            int gameStatus = gameStatus(b);
            if(gameStatus==1){	
            	System.out.println();
        		System.out.println("*******************************************");
            	System.out.println("\nCongratulations!! Player O Won this Game!");
            	break; }
            else if(gameStatus==2){
            	System.out.println();
        		System.out.println("*******************************************");
            	System.out.println("\nCongratulations!! Player X Won this Game!");
            	break; }
            else if(gameStatus==0){
            	System.out.println();
        		System.out.println("*******************************************");
            	System.out.println("Draw!");break;}
            
            b.placeMove(getAIMove(), 'X');
            b.displayBoard();
            gameStatus = gameStatus(b);
           if(gameStatus==1){
        	   System.out.println();
       		   System.out.println("*******************************************");
       		   System.out.println("\nCongratulations!! Player X Won this Game!");
           		break;}
            else if(gameStatus==2){
            	System.out.println();
        		System.out.println("*******************************************");
        		System.out.println("\nCongratulations!! Player O Won this Game!");break;}
            else if(gameStatus==0){
            	System.out.println();
        		System.out.println("*******************************************");
        		System.out.println("Draw!");break;}
			steps =steps+1;

        }
        
        long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		long actualMemUsed=afterUsedMem-beforeUsedMem;
		
		System.out.println();
		System.out.println("*******************************************");
		System.out.println("\nDisplaying the performance of the algorithm:");
        	System.out.println("\nNodes generated: "+ nodes_generated);
		System.out.println ("\nNodes expanded: "+ nodes_expanded);
		System.out.println("\nNumber of Steps (alpha-Beta Pruning): "+ steps);
		System.out.println("\nMemory needed for 1 node is 42 bytes.");
		int m = 42 * nodes_generated;
		System.out.println("\nTotal memory needed for the algorithm is: "+ m +" bytes = " + m/1024 + " kb");
		System.out.println("\nTotal Memory Usage by the Program (in kb): "+actualMemUsed/1024);
		
    	}

	public int getNodes() {
		return 0;
	}
    
}
