package testing;


import logic.Field;

public class Test_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Field [][] initBoard = new Field [6][6];
		int [][] farben = { {1,2,3,4,5,2,},
							{6,1,5,1,3,5,},
							{4,3,6,2,5,1,},
							{5,1,3,6,2,5,},
							{2,4,5,2,6,3,},
							{3,5,6,1,3,2,} };
		
		
		int [][] farbenEnd = {	{1,1,1,1,1,1,},
								{1,1,2,1,1,1,},
								{2,2,2,2,2,2,},
								{2,2,2,2,2,2,},
								{2,2,2,2,2,2,},
								{2,2,2,2,2,2,}};

		
		
		
		
		
		
		for (int i = 0; i < initBoard.length; i++) {
			for (int j = 0; j < initBoard[0].length; j++) {
				
				initBoard[i][j] = new Field(i,j,farbenEnd[i][j]);
			}
		}
		
		
		
		
		
		
		
		Testing test = new Testing(initBoard);
		
		boolean start = test.isStartklar();
		boolean end = test.isEndConfig();
		
		System.out.print(end);
	}

}
