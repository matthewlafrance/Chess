public class Board {
    private static final int SIZE = 8;
    private Piece[][] pieces = new Piece[SIZE][SIZE];

    public Board() {
        initPieces(Color.WHITE);
        initPieces(Color.BLACK);
    }

    @Override
    public String toString() {
        String board = "";
        char column = 'h';

        /*
            a   R  B  N  Q  K  N  B  R
            b   P  P  P  P  P  P  P  P
            c   .  .  .  .  .  .  .  .
            d   .  .  .  .  .  .  .  .
            e   .  .  .  .  .  .  .  .
            f   .  .  .  .  .  .  .  .
            g   P  P  P  P  P  P  P  P
            h   R  B  N  Q  K  N  B  R

                1  2  3  4  5  6  7  8
        */
        
        for (int i = 0; i < SIZE; i++) {
            board += column + "   ";
            column -= 1;

            for (int j = 0; j < SIZE; j++) {
                board += pieces[i][j] == null ? "." : pieces[i][j];
                board += "  ";
            }

            board += "\n";
        }

        board += "\n    ";

        for (int i = 1; i <= SIZE; i++) {
            board += i + "  ";
        }

        return board; 
    }

    private void initPawns(Color color) {
        int row = color == Color.WHITE ? 1 : SIZE - 2;

        for (int i = 0; i < SIZE; i++) {
            pieces[row][i] = new Piece(Piece.Kind.PAWN, color);
        }
    }

    private void initRoyals(Color color) {
        int row = color == Color.WHITE ? 0 : SIZE - 1;

        pieces[row] = new Piece[]{
            new Piece(Piece.Kind.ROOK, color),
            new Piece(Piece.Kind.BISHOP, color),
            new Piece(Piece.Kind.KNIGHT, color),
            new Piece(Piece.Kind.QUEEN, color),
            new Piece(Piece.Kind.KING, color),
            new Piece(Piece.Kind.KNIGHT, color),
            new Piece(Piece.Kind.BISHOP, color),
            new Piece(Piece.Kind.ROOK, color),
        };
    }

    private void initPieces(Color color) {
        initPawns(color);
        initRoyals(color);
    }
}