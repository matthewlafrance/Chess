public class Board {
    public static final int SIZE = 8;
    public static final int WHITE_ROYALS_START_ROW = 0;
    public static final int WHITE_PAWNS_START_ROW = 1;
    public static final int BLACK_ROYALS_START_ROW = SIZE - 1;
    public static final int BLACK_PAWNS_START_ROW = SIZE - 2;

    private Piece[][] pieces = new Piece[SIZE][SIZE];
    private Color currentPlayer = Color.WHITE;

    public Board() {
        initPieces(Color.WHITE);
        initPieces(Color.BLACK);
    }

    @Override
    public String toString() {
        String board = "";

        /*
            8   R  B  N  Q  K  N  B  R
            7   P  P  P  P  P  P  P  P
            6   .  .  .  .  .  .  .  .
            5   .  .  .  .  .  .  .  .
            4   .  .  .  .  .  .  .  .
            3   .  .  .  .  .  .  .  .
            2   P  P  P  P  P  P  P  P
            1   R  B  N  Q  K  N  B  R

                a  b  c  d  e  f  g  h
        */

        for (int i = 0; i < SIZE; i++) {
            board += SIZE - i + "   ";

            for (int j = 0; j < SIZE; j++) {
                board += pieces[i][j] == null ? "." : pieces[i][j];
                board += "  ";
            }

            board += "\n";
        }

        board += "\n    ";

        for (int i = 0; i < SIZE; i++) {
            board += (char)('a' + i) + "  ";
        }

        return board; 
    }

    private void initPawns(Color color) {
        int row = color == Color.WHITE ? WHITE_PAWNS_START_ROW : BLACK_PAWNS_START_ROW;

        for (int i = 0; i < SIZE; i++) {
            pieces[row][i] = new Piece(Piece.Kind.PAWN, color);
        }
    }

    private void initRoyals(Color color) {
        int row = color == Color.WHITE ? WHITE_ROYALS_START_ROW : BLACK_ROYALS_START_ROW;

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

    public void makeMove(Move move) {
        // if piece is on right square and the ending square is valid
        // check differently based on what piece it is
        // execute move
            // take the piece on initial square and replace the destination square with the piece, removing it from initial

        Piece movingPiece = pieces[move.src.row][move.src.column];
        
        if (move.piece == movingPiece.kind) {
            if (movingPiece.isValidMove(move.src, move.dest, this)) {
                System.out.println("valid move");
            } else {
                System.out.println("invalid move");
            }
        } else {
            System.out.println("wrong piece");
        }

        this.changeTurn();
    }

    public boolean hasPiece(Square sq) {
        return this.getPiece(sq) != null;
    }

    public boolean hasOpposingPiece(Square sq, Color color) {
        return this.hasPiece(sq) && (this.getPiece(sq).color != color);
    }

    public Piece getPiece(Square sq) {
        return this.pieces[sq.row][sq.column];
    }

    public void changeTurn() {
        this.currentPlayer = this.currentPlayer.opposite();
    }

    public Color getCurrentPlayer() {
        return this.currentPlayer;
    }

    public boolean pathIsFree(Square src, Square dest) {
        // -1 to move back, 1 to move forward, 0 to not move
        if (!Square.isStraightPath(src, dest)) {
            // TODO: maybe throw an exception for bad path
            return false;
        }

        int dr = Integer.signum(dest.row - src.row);
        int dc = Integer.signum(dest.column - src.column);

        Square current = new Square(src.column + dc, src.row + dr);

        while (!current.equals(dest)) {
            if (this.hasPiece(current)) {
                return false;
            }

            current.row += dr;
            current.column += dc;
        }

        return true;
    }
}