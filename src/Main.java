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
        boolean isWhiteTurn = true;

        while (true) {
            System.out.println("chessboard here");
            System.out.println();
            if (isWhiteTurn){
                System.out.print("white to move: ");
            } else {
                System.out.print("black to move: ");
            }

            Pattern move = Pattern.compile("(P|N|Q|B|R|K)([a-h][1-8])([a-h][1-8])");
            
            while (!input.hasNext(move)) {
                System.out.println("error: invalid move");
                input.next();
                System.out.print("try again: ");
            }

            System.out.println(input.next(move));
            System.out.println();
            
            isWhiteTurn = !isWhiteTurn;
        }
    }
}