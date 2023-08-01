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

        for (int i = SIZE - 1; i >= 0; i--) {
            board += i + 1 + "   ";

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
            new Piece(Piece.Kind.KNIGHT, color),
            new Piece(Piece.Kind.BISHOP, color),
            new Piece(Piece.Kind.QUEEN, color),
            new Piece(Piece.Kind.KING, color),
            new Piece(Piece.Kind.BISHOP, color),
            new Piece(Piece.Kind.KNIGHT, color),
            new Piece(Piece.Kind.ROOK, color),
        };
    }

    private void initPieces(Color color) {
        initPawns(color);
        initRoyals(color);
    }

    public boolean makeMove(Move move) {
        Piece movingPiece = pieces[move.src.row][move.src.column];
        Piece[][] gameBoard = this.pieces;
        int destRow = move.dest.row;
        int destColumn = move.dest.column;
        int srcRow = move.src.row;
        int srcColumn = move.src.column;
        
        if (move.piece == movingPiece.kind) {
            if (movingPiece.isValidMove(move.src, move.dest, this)) {
                gameBoard[destRow][destColumn] = movingPiece;
                gameBoard[srcRow][srcColumn] = null;
                this.changeTurn();

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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

    public boolean gameOver() {
        int kingCount = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (this.pieces[i][j] != null && this.pieces[i][j].kind == Piece.Kind.KING) {
                    kingCount++;
                }
            }
        }
        
        return kingCount != 2;
    }
}