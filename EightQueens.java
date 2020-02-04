/* Assignment 1:- 8Queens Problem using Uniform Cost Search Algorithm
 * State Space:- An 8X8 matrix with any arrangement of n<=8 queens
 * Initial State:- No queens on the board(i.e., all entries are null)
 * 				- - - - - - - -
 * 				- - - - - - - - 
 * 				- - - - - - - -
 * 				- - - - - - - -
 * 				- - - - - - - -
 * 				- - - - - - - -
 * 				- - - - - - - -
 * 				- - - - - - - -
 * Transition Operator:- Add a new queen in an empty row
 * Goal state:- 8 Queens placed on the board such that all queens are in non-attacking position
 * For example,
 * 				- - Q - - - - -
 * 				- - - - - Q - - 
 * 				- - - - - - - Q
 * 				Q - - - - - - -
 * 				- - - Q - - - -
 * 				- - - - - - Q -
 * 				- - - - Q - - -
 * 				- Q - - - - - -
 * Cost:- One per queen placed
 * 
 * Submitted by:- Abhinav Singh
 * Roll no:- 1801004
 * Btech 2nd Year CSE
 */

import java.util.*;
public class EightQueens
{
	int SIZE = 8;
	
	//Creating initial state
	ChessBoard initialise()
	{
		String arr = "";
		for(int i=0;i<SIZE;i++)
			for(int j=0;j<SIZE;j++)
				arr+="0";
		return new ChessBoard(arr, 0);
	}
	
	//Find the row where to insert the queen
	int find(String arr)
	{
	    int i;
		for(i=0;i<SIZE;i++)
		{
			int flag = 0;
			for(int j=0;j<SIZE;j++)
			{
				if(arr.charAt(SIZE*i+j) == '1')
				{
					flag = 1;
					break;
				}
			}
			if(flag == 0)
				return i;
		}
		return i;
	}
	
	//check if current state is valid
	boolean isValid(String arr)
	{
		for(int i=0;i<SIZE;i++)
		{
			int sum_row = 0;
			int sum_col = 0;
			for(int j=0;j<SIZE;j++)
			{
				sum_row+=(int)arr.charAt(SIZE*i+j)-48;
				sum_col+=(int)arr.charAt(SIZE*j+i)-48;
			}
			if(sum_row > 1 || sum_col > 1)
				return false;
		}
		for(int i=0;i<SIZE;i++)
		{
			int sum_left = 0;
			int sum_right = 0;
			for(int j=0;j+i<SIZE;j++)
			{
				sum_left+=(int)arr.charAt(SIZE*j+j+i)-48;
				sum_right+=(int)arr.charAt(SIZE*(j+i)+j)-48;
			}
			if(sum_left > 1 || sum_right > 1)
				return false;
		}
		for(int i=0;i<2*SIZE-1;i++)
		{
			int sum_left = 0;
			int sum_right = 0;
			if(i<SIZE)
			{
			   for(int j=0;i-j>=0;j++)
			   {
				   sum_left+=(int)arr.charAt(SIZE*j+i-j)-48;
			   }
			}
			else
			{
			   for(int j=i-SIZE+1;j<SIZE;j++)
			   {
			       sum_right+=(int)arr.charAt(SIZE*j+i-j)-48;
			   }
			}
			if(sum_left > 1 || sum_right > 1)
				return false;
		}
		return true;
	}
	
	//display valid state
	void display(String arr)
	{
		System.out.print(" ");
		for(int i=0;i<SIZE;i++)
			System.out.print("__ ");
		System.out.println();
		for(int i=0;i<SIZE;i++)
		{
			System.out.print("|");
			for(int j=0;j<SIZE;j++)
			{
				if(arr.charAt(SIZE*i+j)=='1')System.out.print("██|");
				else System.out.print("__|");
			}
			System.out.println();
		}
	}
	
	//driver function
	public static void main(String args[])
	{
		EightQueens obj = new EightQueens();
		ChessBoard sol = obj.initialise();
		
		PriorityQueue<ChessBoard> ucs = new PriorityQueue<ChessBoard>(100, new BoardComparator());
		ucs.add(sol);
		
		int count = 1;
		
		while(!(ucs.isEmpty()))
		{
			//dequeue head of the queue
			ChessBoard chs = ucs.poll();
			String ans = chs.getBoard();
			
			//check if the current state is valid
			if(!(obj.isValid(ans)))continue;
			
			//find where to insert the queen
			int i = obj.find(ans);
			
			//if the state is valid and solution is complete
			if(i==obj.SIZE)
			{
				System.out.println("========Solution "+count+"========");
				obj.display(ans);
				System.out.println("===========================\n");
				count++;
			}
			else
			{
				//explore the current node and enqueue it
				for(int j=0;j<obj.SIZE;j++)
				{
					ans = ans.substring(0, obj.SIZE*i+j)+"1"+ans.substring(obj.SIZE*i+j+1);
					ucs.offer(new ChessBoard(ans, chs.getCost()+1));
					//ans = ans.substring(0, obj.SIZE*i+j)+"0"+ans.substring(obj.SIZE*i+j+1);
					ans = chs.getBoard();
				}
			}
			
		}
	}
}

class BoardComparator implements Comparator<ChessBoard>
{
	public int compare(ChessBoard c1, ChessBoard c2) 
	{ 
        if (c1.getCost() > c2.getCost())
        	return 1;
        else if (c1.getCost() < c2.getCost()) 
            return -1; 
        return 0; 
    } 
}

class ChessBoard
{
	private String board;
	private int cost;

	public ChessBoard(String board, int cost)
	{
		this.cost = cost;
		this.board = board;
	}

	int getCost()
	{
		return this.cost;
	}
	String getBoard()
	{
		return this.board;
	}

	void setCost(int cost)
	{
		this.cost = cost;
	}
	void setBoard(String board)
	{
		this.board = board;
	}
}
