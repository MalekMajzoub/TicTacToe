package malek;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToeMain {

	public static void main(String[] args) {
		TicTacToe game = new TicTacToe();
		// scan from the user their desired move
		Scanner scan = new Scanner(System.in);
		// scan from the user type of AI (opponent)
		Scanner scanTypeOfAI = new Scanner(System.in);
		Random rnd = new Random(); // generates random move for the AI
		String move; // the user's input
		int sideHuman = 5; // to initialize the human side
		int sideComp = 5; // to initialize the computer side
		int typeOfAI = 0; // to initialize type of AI
		int c = 0; // to determine the playing sides and limit the user's choice only from first
					// legal move
		boolean typeOfAIValidator = true;
		// a do-while to ensure that the user inputs either 1 or 2

		while (typeOfAIValidator) {

			try {

				System.out.println(
						"Do you want to play with:(Select 1 or 2)\n\t\t 1.AI choosing random moves\n\t\t 2.AI choosing optimal moves(You will not win)");
				typeOfAI = scanTypeOfAI.nextInt();
				if (typeOfAI == 1 || typeOfAI == 2)
					typeOfAIValidator = false;

			} catch (InputMismatchException e) {
				scanTypeOfAI.nextLine();
				System.out.println("You enterd invalid input!");

			}
		}
		// the below board displays directions for the user
		game.getBoard();
		System.out.println(
				"\nYou can notice that the reference for each position is provided for you.\nKeep in mind that to reference"
						+ " a certain square, you should input column letter followed by row number\n");

		// the do-while loop here ensures that the game will continue as long as the
		// board is
		// not full
		do {
			boolean validateHuman = false; // keeps on looping as long as user doesn't supply the game with an order or
											// legal move

			do { // the do-while loop here makes sure that the user doesn't choose a non empty
					// position
				System.out.println("Enter your move in the following form: columnLetter,rowNum,space,X/O");
				move = scan.nextLine(); // takes input from user
				int col = 5, row = 5; // For initialization purposes

				if (move.equalsIgnoreCase("new")) {
					game.clearBoard(); // clears board if user inputs new
					System.out.println("Board is cleared");
					c = 0; // resets counter to zero if the user chooses to play a new game to update the
							// user's choice of playing as X/O

					continue;// to ask the user for input after clearing the board
				}
				if (move.equalsIgnoreCase("dump"))
					game.getBoard(); // displays the current board
				if (move.equalsIgnoreCase("quit"))
					break;// terminates the program
				else { // the if statement makes sure that the user inputs a legal move and chooses a
						// correct side
					if (move.length() == 4 && (move.charAt(3) == 'X' || move.charAt(3) == 'x' || move.charAt(3) == 'O'
							|| move.charAt(3) == 'o')) {
						c++;// increment the value of the counter to 1 to fix the user's side

						// the two loops below fix the user's and computer's playing sides as X/O
						if ((move.charAt(3) == ('X') || move.charAt(3) == 'x') && c == 1) {
							sideHuman = 0;
							sideComp = 1;
							System.out.println("The computer will be playing as O");
						} else if ((move.charAt(3) == ('O') || move.charAt(3) == 'o') && c == 1) {
							sideHuman = 1;
							sideComp = 0;
							System.out.println("The computer will be playing as X");
						}
						// link the correct columns and rows to user's choice
						if (move.charAt(0) == 'a')
							col = 0;
						if (move.charAt(0) == 'b')
							col = 1;
						if (move.charAt(0) == 'c')
							col = 2;
						if (move.charAt(1) == '3')
							row = 0;
						if (move.charAt(1) == '2')
							row = 1;
						if (move.charAt(1) == '1')
							row = 2;

						if (game.playMove(sideHuman, row, col))
							validateHuman = true;// if the move entered by the user is valid(position is empty) then it
													// should exit the loop
					}
				}
			} while (!validateHuman); // breaks out of the loop if user's input is valid
			game.getBoard();// print the current state of the board

			// if the Human wins, the program should terminate
			if (game.isAWin(sideHuman)) {
				System.out.println("You won!! Congratulations");
				break;
			}
			if (game.boardIsFull() && game.isAWin(sideHuman) == false && game.isAWin(sideComp) == false) {
				System.out.println("It's a tie.");
				break;
			}

			if (typeOfAI == 1) { // the user chooses to play against computer making random moves
				System.out.print("\nThe Computer played: \n");
				boolean validate = false; // to keep looping if the random move is already played by either one of the
											// sides
				do {
					if (game.playMove(sideComp, rnd.nextInt(3), rnd.nextInt(3)))
						validate = true;// if the move entered by the computer is valid(position is empty) then we
										// should exit the loop

				} while (!validate);

			}

			if (typeOfAI == 2) { // user chooses to play against computer making optimal moves
				System.out.print("\nThe Computer played: \n");
				Best best = new Best(sideComp); // to extract the optimal move obtained by best
				best = game.chooseMove(sideComp); // choose best move for the indicated side (computer)
				game.playMove(sideComp, best.row, best.column); // play the best move chosen earlier
			}
			game.getBoard();
			// if the computer wins, the program should terminate
			if (game.isAWin(sideComp)) {
				System.out.println("The computer won. Better luck next time");
				break;
			}
			// if the board is full and none of the playing sides have won, then it should
			// display a tie
			if (game.boardIsFull() && game.isAWin(sideHuman) == false && game.isAWin(sideComp) == false) {
				System.out.println("It's a tie.");
				break;
			}
		} while (game.boardIsFull() == false); // keep the game running as long as the board is not full

		scan.close();
		scanTypeOfAI.close();

	}

}