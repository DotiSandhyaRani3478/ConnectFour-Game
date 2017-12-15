/* Connect-Four Game(AI)
 * by
 * Akshay Chandrachood (A04742395)
 * Doti SandhyaRani (A04714047)
 */
import java.awt.Point;
import java.util.ArrayList;

public class Evaluations {
	 
	 Board current_board;
	 int rows = 6;
	 int cols = 7;
	 int nodes=0;
	 
	 //called from each AI class
	 //current status of board and function number are the arguments to this function
	 
	 public int evaluateBoard(char[][] b, int f_no){
		 int score=0;
		 
		//call respective evaluation function which returns integer 
		//value to represent goodness of Position from the standpoint of Player
		 
		 if(f_no==1){			 
			 score = eval_function_one(b);
		 }
		 else{
			 score = eval_function_two(b);
		 }
		 
		 return score;
	 }
	 
	 
	//check for 4 consecutive occurrences of same player's disc
	//or check if player win
	 
	public boolean isGoal(char player, char[][]board){
			
		//create string XXXX or 0000
		String find=""+player+""+player+""+player+""+player;
			
		//checking 4 occurrences of player's disk in each row
		for(int i=0; i<this.rows; i++)
			if(String.valueOf(board[i]).contains(find))
				return true;
			
		//checking 4 occurrences of player's disk in each column
		for(int j=0; j<this.cols; j++){
			String col="";
		for(int i=0; i<this.rows; i++)
			col+=board[i][j];
			
		//return true if string XXXX or 0000 found
		if(col.contains(find))
			return true;
		}
			
		
		ArrayList<Point> pos_right=new ArrayList<Point>();
		ArrayList<Point> pos_left=new ArrayList<Point>();
			
		for(int j=0; j<this.cols-4+1; j++)
			pos_right.add(new Point(0,j));
		for(int j=4-1; j<this.cols; j++)
			pos_left.add(new Point(0,j));	
		for(int i=1; i<this.rows-4+1; i++){
				pos_right.add(new Point(i,0));
			pos_left.add(new Point(i,this.cols-1));
		}
		
		//checking 4 occurrences of player's disk at each right diagonal
		for (Point p : pos_right) {
			String d="";
			int x=p.x, y=p.y;
			while(true){				
				if (x>=this.rows||y>=this.cols)
					break;
				d+=board[x][y];
				x+=1; y+=1;
			}
			if(d.contains(find))
				return true;
		}
			
		//checking 4 occurrences of player's disk at each left diagonal
		for (Point p : pos_left) {
			String d="";
			int x=p.x, y=p.y;
			while(true){
				if(y<0||x>=this.rows||y>=this.cols)
					break;
				d+=board[x][y];
				x+=1; y-=1;
			}
			if(d.contains(find))
				return true;
		}
		
		return false;	
	}
		
		
    //First Evaluation Function - simple
		
	public int eval_function_one(char[][] board){   
		 
		//check for XXXX, if found return 1000
		if (isGoal('X',board)){   
			return 1000;
		}
				
		//check for 0000, if found return -1000
		if (isGoal('0',board)){
			return -1000;
	    }
				
		return 0;
	}
		
	
	
	//Second Evaluation function - complex
	//this evaluation function uses quadratic equation to get the value of the position
	// euation: y = m^(x)+c 
	//where y: value of the position
	//      m: slope (any constant value) in this case it is assumed 5
	//      c: any constant (to avoid returning value 0)
	//      x: number of consecutive occurrences of player's disk
	
		
	public static int eval_function_two(char[][] board){
		int valueX=1;
		int value0 = 1, value=0;
		int score=1,scoreX_H=0, score0_H=0, scoreX_V=0, score0_V=0;
		int scoreX_R=1, score0_R=1;
		int m=5,c=10;
		
			
		//horizontal checking for XXXX or 0000
		for(int i=0;i<6;i++){
			valueX=1; value0=1;
			for(int j=0;j<6;j++){
					
				if(board[i][j]=='X' && board[i][j]==board[i][j+1] && valueX<4){
					valueX++;
					scoreX_H += (int)Math.pow(m, valueX)+c;
					if(valueX==4){
						return scoreX_H;
					}
				}
					
				else if(board[i][j]=='0' && board[i][j]==board[i][j+1] && value0<4){
					value0++;
					score0_H += (int)Math.pow(m, value0)+c;
					score0_H*=-1;
						
					if(value0==4){
						return score0_H;
					}
				}
					
				else if(board[i][j+1]==' '){
					//score = 0;
					continue;
				}
					
				else if(board[i][j]!=board[i][j+1] && board[i][j+1]!=' '){
					valueX=1; value0=1;
					scoreX_H=0; score0_H=0;
					continue;
				}
					
			}
			//System.out.println("\nHoriz Score0: "+score0_H);
			score += ((valueX > value0) ? scoreX_H : score0_H);
		}
			
		
		//vertical checking for XXXX or 0000
		
		for(int j=0;j<7;j++){
			valueX=1; value0 = 1;
			for(int i=0;i<5;i++){
							
				if(board[i][j]=='X' && board[i][j]==board[i+1][j] && valueX<4){
					valueX++;
					scoreX_V += (int)Math.pow(m, valueX)+c;
					if(valueX==4){
						return scoreX_V;
					}
				}
								
				else if(board[i][j]=='0' && board[i][j]==board[i+1][j] && value0<4){
					value0++;
					score0_V -= (int)Math.pow(m, value0)+c;
											
					if(value0==4){
						return score0_V;
					}
				}
								
				else if(board[i+1][j]==' '){
					//score = 0;
					continue;
				}
								
				else if(board[i][j]!=board[i+1][j] && board[i+1][j]!=' '){
					valueX=1; value0=1;
					scoreX_V=0; score0_V=0;
					continue;
				}					
							
			}
			//System.out.println("\nVerti Score0: "+score0_V);
			score += ((valueX > value0) ? scoreX_V : score0_V);					
		}
			
			
	   //right diagonal checking for XXXX or 0000
		
		valueX=1; value0=1;
			
		for(int i =0; i < 3; i++){		 
		     for(int j=0; j<4; j++){
		           if(board[i][j]=='X' && board[i][j]==board[i+1][j+1] && valueX<4){
		        	   valueX++;
		            	scoreX_R+= (int)Math.pow(5, valueX)+10;
		            	if(valueX==4){
							return scoreX_R;
						}
		            }
		            	
		       else if(board[i][j]=='0' && board[i][j]==board[i+1][j+1] && value0<4){
		    	   value0++;
					score0_R -= (int)Math.pow(m, value0)+c;
					if(value0==4){
						return score0_R;
						}
					}
		            	
		        else if(board[i+1][j+1]==' '){
					continue;
				}
						
				else if(board[i][j]!=board[i+1][j+1] && board[i+1][j+1]!=' '){
					valueX=1; value0=1;
					scoreX_R=0; score0_R=0;
					continue;
				}
		        
		      //  score += ((valueX > value0) ? scoreX_R : score0_R);	
		    }
		     score += ((valueX > value0) ? scoreX_R : score0_R);	
		 }


		//System.out.println("\nTotal Score: "+score);
			
			
		//left diagonal checking for XXXX or 0000
		
		for(int i=0;i<1;i+=1){
			score += function(i,3,i+1,board); 
			
		}
		
			
			return score;
		}
	
	
	static int function(int z, int y, int count, char[][] board){
		
		int valueX=1, value0=1,scoreX_L=0,score0_L=0,score=0;
		
		if(count==1){
			for(int x=0;x<4;x++){
					for(int i=0+z,j=y+x; i<=y+x && i<5 && j>0;i++,j--){
						
						if(board[i][j]=='X' && board[i][j]==board[i+1][j-1] && valueX<4){
		            		valueX++;
		            		scoreX_L+= (int)Math.pow(5, valueX)+10;
		            		if(valueX==4){
								return scoreX_L;
							}
		            	}	
						
						else if(board[i][j]=='0' && board[i][j]==board[i+1][j-1] && value0<4){
							value0++;
							score0_L -= (int)Math.pow(5, value0)+10;
							if(value0==4){
								return score0_L;
							}
						}
						
						else if(board[i][j]==' '){
							continue;
						}
						
						
						else if(board[i][j]!=board[i+1][j-1] && board[i+1][j-1]!=' '){
							valueX=1; value0=1;
							scoreX_L=0; score0_L=0;
							continue;
						}
						
					}  
				 score += ((valueX > value0) ? scoreX_L : score0_L);		
			  }
		}
		  
		else{
		
			for(int i=0+z,j=y+3; i<=y+3 && i<6 && j>-1;i++,j--){
				
				if(board[i][j]=='X' && board[i][j]==board[i+1][j-1] && valueX<4){
            		valueX++;
            		scoreX_L+= (int)Math.pow(5, valueX)+10;
            		if(valueX==4){
						return scoreX_L;
					}
            	}	//end_if
				
				else if(board[i][j]=='0' && board[i][j]==board[i+1][j-1] && value0<4){
					value0++;
					score0_L -= (int)Math.pow(5, value0)+10;
					if(value0==4){
						return score0_L;
					}
				}
				
				else if(board[i+1][j-1]==' '){
					continue;
				}
				
				else if(board[i][j]!=board[i+1][j+1] && board[i+1][j+1]!=' '){
					valueX=1; value0=1;
					scoreX_L=0; score0_L=0;
					continue;
				}		
			}
			score += ((valueX > value0) ? scoreX_L : score0_L);	
		}
			   	
		return score;
	}
		
}
