import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("Welcome to Chess 👑");
        System.out.println();
        System.out.println("Format moves in format 'piece oldposition newposition'");
        System.out.println("Pieces are abbreviated to: pawn P, bishop B, knight N, rook R, queen Q, king K");
        System.out.println("Positions follow normal chess terminology, columns a-h, rows 1-8");
        System.out.println();

        Scanner input = new Scanner(System.in);
        Board chessBoard = new Board();

        while (true) {
            System.out.println(chessBoard);
            System.out.println();
            System.out.print(chessBoard.getCurrentPlayer().toString() + " to move: ");
            Move move = Move.parse(input.next());

            while (move == null || !chessBoard.makeMove(move)) {
                System.out.println("error: invalid move");
                System.out.print("try again: ");
                move = Move.parse(input.next());
            }

            System.out.println();
        }
    }
}