import java.util.Stack;

/**
 * This class, once completed, should provide a recursive depth first solution
 * to finding the SHORTEST route between two squares on a GameBoard.
 * 
 * @see GameBoard
 * @see GameSquare
 * @author Joe Finney
 */
public class MazeSquare extends GameSquare
{
	private GameBoard board;			// A reference to the GameBoard this square is part of.
	private boolean target;				// true if this square is the target of the search.
	private Stack <MazeSquare> stack = new Stack<>();
	private boolean visited;
	
	
	private static int shortestCount;	// The shortest path found so far in this search.

	/**
	 * Create a new GameSquare, which can be placed on a GameBoard.
	 * 
	 * @param x the x co-ordinate of this square on the game board.
	 * @param y the y co-ordinate of this square on the game board.
	 * @param board the GameBoard that this square resides on.
	 */
	public MazeSquare(int x, int y, GameBoard board)
	{
		super(x, y);
		this.board = board;
	}

	/**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the end point for the search.

	 */	
    public void leftClicked()
	{
		board.reset(0);
		board.reset(1);
		this.setHighlight(true);
		this.target = true;
	}
    
    /**
	 * A method that is invoked when a user clicks on this square.
	 * This defines the start point for the search. 
	 */	
	public void rightClicked()
	{
		board.reset(0);
		MazeSquare.shortestCount = 0;
		this.setHighlight(true);
		Maze(this);

		System.out.println(" *** COMPLETE: SHORTEST ROUTE " + (MazeSquare.shortestCount == 0 ? "IMPOSSIBLE" : MazeSquare.shortestCount) + " ***");
	}

	/**
	 * A method that is invoked when a reset() method is called on GameBoard.
	 * 
	 * @param n An unspecified value that matches that provided in the call to GameBoard reset()
	 */
	public void reset(int n)
	{
		if (n == 0){
			this.setHighlight(false);
		}
		else if (n == 1){
			this.target = false;
		}
	}

	/**
	 * DFS method 
	 * Will look at every possible square and find the nearest route to its target location. 
	 * @param square the starting square that is pressed.
	 */
	public void Maze(MazeSquare square){
		square.setVisited(true);
		stack.push(square);
		
		//Updating shortest route
		if (square.getTarget() == true){
			if((MazeSquare.shortestCount>stack.size()) || (MazeSquare.shortestCount == 0)){
				
				board.reset(0);

				for (MazeSquare list : stack){
					list.setHighlight(true);
				}

				MazeSquare.shortestCount = (stack.size()-1);
			}
		}
		
		// If new route is larger then nearest route, go back to previous square and update stack
		if((MazeSquare.shortestCount != 0) && (stack.size()>shortestCount)){
			square.setVisited(false);
			stack.pop();
 			return;
		}

		// Look left sqaure 
		if(square.getWall(0) == false){
			MazeSquare next = (MazeSquare)board.getSquareAt(square.getXLocation() - 1,square.getYLocation());
			if(next.getVisited() == false){
				Maze(next);
			}
		}

		// Look right square 
		if(square.getWall(1) == false){
			MazeSquare next = (MazeSquare)board.getSquareAt(square.getXLocation() + 1,square.getYLocation());
			if(next.getVisited() == false){
				Maze(next);
			}
		}

		// Look up square
		if(square.getWall(2) == false){
			MazeSquare next = (MazeSquare)board.getSquareAt(square.getXLocation(), square.getYLocation() -1);
			if(next.getVisited() == false){
				Maze(next);
			}
		}
		
		// Look down square
		if(square.getWall(3) == false){
			MazeSquare next = (MazeSquare)board.getSquareAt(square.getXLocation(), square.getYLocation() + 1);
			if(next.getVisited() == false){
				Maze(next);
			}
		}
		
		// If there is dead end, go back to previous square and update stack.
		square.setVisited(false);
		stack.pop();
 		return;

	}

	public boolean getTarget(){
		return target;
	}

	/**
	 * sets the square to visited
	 * @param y will be made either true or false
	 */
	public void setVisited(boolean y){
		visited = y;
	}

	public boolean getVisited(){
		return visited;
	}
}
