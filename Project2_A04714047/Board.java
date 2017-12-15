/* Connect-Four Game 
 * by
 * Akshay Chandrachood (A04742395)
 * Doti SandhyaRani (A04714047)
 */

class Board{

	/* board dimensions */

	private int rows = 6;
	private int columns = 7;
	
	private static final String four_X = "XXXX";
	private static final String four_0 = "0000";
	private static String find_str="";
	
	/* board char array */

	char[][] board = new char[6][7];
	private int steps=0;
	
	/* board constructor */

    public Board(){
    	
    	board = new char[][]{
            {' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' '},
            {' ',' ',' ',' ',' ',' ',' '},    
        };

    } 
    
    /* if slot is empty return true */

    public boolean isSlotEmpty(int column){
        return board[0][column]==' ';
    }
    
    /* Placing a Move on the board */
    
    public boolean placeMove(int column, char player){ 
        if(!isSlotEmpty(column)) {System.out.println("Illegal move!"); return false;}
        for(int i=5;i>=0;i--){

            if(board[i][column] == ' ') {
                board[i][column] = player;
                return true;
            }
        }
        return false;
    }
    
    /* discard temporary child nodes (pruning) */

    public void discardMove(int column){
        for(int i=0;i<=5;++i){
            if(board[i][column] != ' ') {
                board[i][column] = ' ';
                break;
            }
        }        
    }
    
    /* Printing the board */
    
    public void displayBoard(){
        System.out.println();
        System.out.println("\n******************* Displaying Current Board Position ************");
        System.out.println("  0   1   2   3   4   5   6");
        System.out.println("+---------------------------+");
        for(int i=0;i<=5;++i){
        	System.out.print("| ");
            for(int j=0;j<=6;++j){
                System.out.print(board[i][j]+" ");
                System.out.print("| ");
            }
            System.out.println();
        }
        System.out.println("+---------------------------+\n");
    }
    
    public char[][] getBoard(){
    	return board;
    }
    
    /* Checking left diagonals for sequence of 4 X's Or 4 O's */

    public boolean call_isLeft(char player){
	
	    for(int i =0; i < 3; i++){
	            for(int j=0; j<4; j++){
	              if(isLeft("", i,  j, 4, find_str)){
	                return true;
	              }
	        }
	    }
	    return false;
    }
    
  
	public boolean isLeft(String check, int x, int y, int count, String finds){
	    
		char chk = finds.charAt(0);
	
	    if(check==finds){
	        return true;
	    }
	
	    else if(count > 0){
	        count--;
	        if(board[x][y] == finds.charAt(0)){
	            check += board[x][y];
	        }
	        return isLeft(check, x+1, y+1, count, finds);
	    }
	
	    else
	        return false;
	}


    /* Checking horizontally for sequence of 4 X's Or 4 O's */

	public boolean isHorizontal(char player){
	
	    boolean status=false;
	   	    
	    for(int i=0;i<rows;i++){
	        if(!status){
	            status = eachRow(i,player,find_str);
	        }
	        if(status)
	            break;
	    }
	    if(status){
	        return true;
	    }
	    else
	        return false;
	}

	public boolean eachRow(int r, char player, String find_str){
	    
		int count = 0;
	    String check="";
	    
	    for(int i = 0; i< columns;i++){
	        if(board[r][i] == player){
	            count++;
	            check += board[r][i];
	        }
	        else{
	          count=0;
	          check = "";
	        }
	        if(count == 4 && check==find_str){
	           break;
	        }
	    }
	
	    if(count==4)
	        return true;
	
	    else
	        return false;
	}
	
    /* Checking vertically for sequence of 4 X's Or O's */

	public boolean isVertical(char player){
	    
		boolean status=false;
		 
	    for(int i=0;i<columns; i++){
	        if(!status){
	            status = eachColumn(i,player,find_str);
	        }
	        if(status)
	            break;
	    }
	    if(status){
	        return true;
	    }
	    else
	        return false;
	}
	
	public boolean eachColumn(int c, char player, String find_str){
	   
		int count = 0;
	    String check="";
	    
	    for(int i = 0; i< rows; i++){
	        if(board[i][c] == player){
	            count++;
	            check += board[i][c];
	        }
	        else{
	          count=0;
	          check = "";
	        }
	        if(count == 4 && check==find_str){
	           break;
	        }
	    }
	
	    if(count==4)
	        return true;
	
	    else
	        return false;
	}
	
    /* Checking Right diagonals for sequence of 4 X's Or O's */

	public boolean call_isRight(char agent){

		for(int i=0;i<=2;i++){
			for(int j=6;j>=3;j--){   
				 if(isRight("", i,  j, 4, find_str)){
		                return true;
		              }
			}
		}

	    return false;
	}
	
	public boolean isRight(String check, int x, int y, int count, String finds){
	    
		char chk=' ';
				
		char chk1 = finds.charAt(0);	
		
	    if(check==finds){
	        return true;
	    }
	
	    else{
	    	if(count > 0){
	        count--;
	        if(board[x][y] == chk1){
	            check += board[x][y];
	        }
	        
	        if(x>0 && y>0)
	        	return isRight(check, x+1, y-1, count, finds);
	    }
	   }

        return false;
	}
	
	
	/* returns True/False if the palyer has won the game
	by checking all rows/columns/diagonals for a sequence of >=4 */
	
	public boolean isGoal(char c){
				
		 if(c=='X'){
		     this.find_str = "XXXX";
		 }
		 else {
		     this.find_str = "0000";
		 }
		      
	    if(isVertical(c) || isHorizontal(c) || call_isLeft(c) || call_isRight(c))
	    {
	        System.out.println("\n\n********************************************");
	        return true;
	    }
	    else{
	        return false;
	    }
	}

	
}

