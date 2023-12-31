import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Chess 👑");
        System.out.println();
        System.out.println("Format moves in format 'piece oldposition newposition' with no spaces");
        System.out.println("Pieces are abbreviated to: pawn P, bishop B, knight N, rook R, queen Q, king K");
        System.out.println("Positions follow normal chess terminology, columns 1-8, rows a-h");
        System.out.println();

        Scanner input = new Scanner(System.in);
        Board chessBoard = new Board();
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println(chessBoard);
            System.out.println();

            if (chessBoard.isInCheck(chessBoard.getCurrentPlayer())) {
                System.out.println("" + chessBoard.getCurrentPlayer() + " is in check");
            }

            System.out.print(chessBoard.getCurrentPlayer() + " to move: ");
            Move move = Move.parse(input.next());

            while (move == null || !chessBoard.makeMove(move)) {
                System.out.println("error: invalid move");
                System.out.print("try again: ");
                move = Move.parse(input.next());
            }

            System.out.println();

            gameOver = chessBoard.gameOver();
        }

        System.out.println("Game Over! " + chessBoard.getCurrentPlayer().opposite() + " Wins!");
    }
}