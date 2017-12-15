/* Connect-Four Game(AI)
 * by
 * Akshay Chandrachood (A04742395)
 * Doti SandhyaRani (A04714047)
 */
import java.util.Arrays;
import java.util.ArrayList;
class State implements Cloneable
{
	private char[][] board = null;
	private static int nodes;
	int number_of_steps=0;
	int rows, cols;
	int nodes_expanded =0;
	
	public Board b1 = new Board();
	State(){
		
	}
	
	/* Constructor initializing rows and columns */

	public State(int n_rows, int n_cols){
		this.rows=n_rows;
		this.cols=n_cols;
		board = b1.getBoard();
		this.nodes = 0;
	}
	
	/* copy constructor */

	public boolean equals(Object obj){
	
		State other=(State)obj;
		return Arrays.deepEquals(this.board, other.board);
	
	}
	
	/* copying current status of the board to the new board(temporary board) */

	public Object clone() throws CloneNotSupportedException {
        State new_state=new State(this.rows, this.cols);
		for (int i=0; i<this.rows; i++)
			new_state.board[i] = (char[]) this.board[i].clone();
		return new_state;
	}
	

	public ArrayList<Integer> getLegalActions(){
		ArrayList<Integer> actions=new ArrayList<Integer>();
		for(int j=0; j<this.cols; j++)
			if(board[0][j]==' ')
				actions.add(j);
		nodes+= actions.size();
		return actions;
	}
	

	/* returns a list of nodes representing the moves 
	 * that can be made by Player in Position */
	
	public State moveGen(char agent, int action) throws CloneNotSupportedException{
		
		int row;
		for(row=0; row<this.rows && this.board[row][action]!='X' && this.board[row][action]!='O'; row++);
		State new_state=(State)this.clone();
		new_state.board[row-1][action]=agent;
		return new_state;
	}
	
	public void printBoard(){
		
		b1.displayBoard();
	}
	
	/* getter and setter methods 
	 * for calculating no. of steps and no. of nodes generated */
	
	public void set_steps(int n_steps){
		number_of_steps =n_steps;
	}
	public int get_steps(){
		return number_of_steps;
	}
	
	public static int getNodes(){
		return nodes;
	}
	
	public boolean isGoal(char agent){
	
		return b1.isGoal(agent);
	}
	
	public char[][] getBoard(){
		
		return this.board;
	}
	
}

/* Implementing MinMax algorithm */

class minimaxAgent{
	
	int depth;
	int x=0;
	int nodes_generated = 0;
	int nodes_expanded=0;
	private int func_no;
	private int number_of_steps =0;
	
	public minimaxAgent(){
		
	}
	
	/* constructor initializing the depth */

	public minimaxAgent(int depth)
	{
		this.depth = depth;
	}	
	
	public int getAction(State st) throws CloneNotSupportedException
	{
		double val = max_value(st, depth);
		return x;
		
	}
	
	/* returns the max value after evaluating the board */

	public double max_value(State st, int d) throws CloneNotSupportedException
	{
		Evaluations e1 = new Evaluations();
		
		MiniMax c1 = new MiniMax();
		func_no = c1.getFunctionNo();
		
		ArrayList<Integer> children = new ArrayList<Integer>();
		if(d ==0)
		return e1.evaluateBoard(st.getBoard(),func_no);	//pass func no
		
		else{
		children = st.getLegalActions();
		double v = -10000000;
		int steps=0;

		double z;
		int i;
		nodes_generated =  nodes_generated + children.size();
		nodes_expanded++;
		for( i =0; i<children.size();i++){
			steps = steps+1;
			st.set_steps(steps);
			
			z = min_value(st.moveGen('O',children.get(i)),d);
			if(z >= v){
				v =z;
				this.x = i;
			}
		}
		
		return v;
		}
	}
	
	/* returns the min value after evaluating the board */

	public double min_value(State st, int d) throws CloneNotSupportedException
	{
		Evaluations e1 = new Evaluations();
		int steps=0;
		ArrayList<Integer> children = new ArrayList<Integer>();
		if(d == 0)
		return e1.evaluateBoard(st.getBoard(),func_no);  //pass eval fun no
		else
		{
		children = st.getLegalActions();
		
		double v = 10000000;
		int x=0;
		double z;
		
		nodes_generated =  nodes_generated + children.size();
		nodes_expanded++;
		
		for(int i =0; i<children.size();i++){
			steps = steps+1;
			st.set_steps(steps);
			
			z= max_value(st.moveGen('X',children.get(i)),d-1);
			if(z <= v)
				v=z;
		}
		return v;
		}		
	}
	
	public int getNodes(){
		return nodes_generated;
	} 
	public int getExpandedNodes(){
		return nodes_expanded;
	} 

}

/* MiniMaxAlgo */

public class MiniMax{
	
	private static int eval_function_no;
	private static int nodes=0;
	private static int expanded_nodes=0;
	private static int steps=0;
	private static int total_steps=0;

	/* setter to set the evaluation function no. */

	public void setEvaluationFunction(int f1){
	  	eval_function_no = f1;
	}
	
	/* getter to get the evaluation function no. */

	public int getFunctionNo(){
		return eval_function_no;
	}

	public static void minmaxAlgo() throws CloneNotSupportedException{
		
		int depth = 3;	
		
		/* creating object for minimax class and State class */
		long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
		minimaxAgent mma = new minimaxAgent(depth);
		State s = new State(6,7);
		while(true){
			int action = mma.getAction(s);
			nodes += mma.getNodes();
			expanded_nodes += mma.getExpandedNodes();
			steps = steps + s.get_steps();
		
			s = s.moveGen('O', action);
			s.printBoard();      
			if(s.isGoal('O')){
				long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				long actualMemUsed=afterUsedMem-beforeUsedMem;
				System.out.println("\nCongratulations!! Player O Won this Game!");
				System.out.println(" ");
				System.out.println("********************************************");
				System.out.println("\nDisplaying the performance of the algorithm:");
				System.out.println("\nNodes generated: " + nodes);
				System.out.println("\nNodes expanded: " + expanded_nodes);
				System.out.println("\nNumber of Steps: "+ steps);
				System.out.println("\nMemory needed for 1 node is 42 bytes.");
				int m = 42 * nodes;
				System.out.println("\nTotal memory needed for the algorithm is: "+ m +" bytes = " + m/1024 + " kb");
				System.out.println("\nTotal Memory Usage by the Program (in kb): "+actualMemUsed/1024);
				break;
			}
			
			int enemy_move = mma.getAction(s);
			s = s.moveGen('X', enemy_move);
			s.printBoard();
			if(s.isGoal('X'))
			{
				long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
				long actualMemUsed=afterUsedMem-beforeUsedMem;
				System.out.println("\nCongratulations!! Player X Won this Game!");
				System.out.println(" ");
				System.out.println("*******************************************");
				System.out.println("\nDisplaying the performance of the algorithm:");
				System.out.println("\nNodes generated: " + nodes);
				System.out.println("\nNodes expanded: " + expanded_nodes);
				System.out.println("\nNumber of Steps: "+ steps);
				System.out.println("\nMemory needed for 1 node is 42 bytes.");
				int m = 42 * nodes;
				System.out.println("\nTotal memory needed for the algorithm is: "+ m +" bytes = " + m/1024 + " kb");
				System.out.println("\nTotal Memory Usage by the Program (in kb): "+actualMemUsed/1024);
				break;
			}
		
		}
	}		
	
	
}
