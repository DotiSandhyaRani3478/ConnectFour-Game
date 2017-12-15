/* Connect-Four Game(AI)
 * by
 * Akshay Chandrachood (A04742395)
 * Doti SandhyaRani (A04714047)
 */

import java.util.*;

public class Console_Interface {

	private static int eval;
	private static int algo;
	private static int number_of_steps = 0;
	
	/* main method */
	
	public static void main(String[] args) throws CloneNotSupportedException{
			
		long startTime=0000;
		display_interface();
		user_input();
		int nodes=0;
		
		/* selecting the algorithm and evaluation function */
		
		try{
			if(algo==1){

				startTime = System.currentTimeMillis();
				MiniMax minMax = new MiniMax();
				minMax.setEvaluationFunction(eval);
				minMax.minmaxAlgo();
				
			}
			
			else if(algo==2){

				startTime = System.currentTimeMillis();
				AlphaBeta aB = new AlphaBeta();
				aB.setEvaluationFunction(eval);
				aB.opponetPlayer();						
			}
			else{
				System.out.println("\nError!! \n");
			}
		}catch(Exception e){
			 e.printStackTrace();
			 
		}
		
		/* Computing the Execution time */
		
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("\nExecution Time: "+ totalTime + " ms ");
		
	}
	
	/* user input to select algorithm and evaluation function */
	
	public static void user_input(){
		Scanner in = new Scanner(System.in);	

		System.out.println("\nPlease choose the algorithm: \n");
		System.out.println("\n1. MinMax Algorithm \t 2. Alpha-Beta Pruning \n");
		algo = in.nextInt();
		
		while(algo!=1 && algo!=2){
			System.out.println("\nPlease enter valid no..\n");
			System.out.println("\nPlease select the algorithm:");
			System.out.println("\n1. MinMax Algorithm \t 2.Alpha-Beta Pruning \n");
			algo = in.nextInt();
		}
		
		System.out.println("\nPlease enter evaluation function: (1/2): \n");
		eval = in.nextInt();
		
		while(eval!=1 && eval!=2){
			System.out.println("\nPlease enter valid no..\n");
			System.out.println("\nPlease enter evaluation function: (1/2): ");
			eval = in.nextInt();
		}
	}
	
	/* Connect-Four game UI */
	
	public static void display_interface(){
		System.out.print("************************************************************************\n");
		System.out.print("************************************************************************\n");
		System.out.print("********************* WELCOME TO CONNECT-FOUR GAME *********************\n");
		System.out.print("************************************************************************\n");
		System.out.print("************************************************************************\n");

		
	}

}
