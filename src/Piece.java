public class Piece {
    public enum Kind {
        PAWN,
        ROOK,
        BISHOP,
        QUEEN,
        KNIGHT,
        KING;

        public static Kind parse(char piece) {
            switch (piece) {
                case 'P':
                    return PAWN;
                case 'R':
                    return ROOK;
                case 'Q':
                    return QUEEN;
                case 'B':
                    return BISHOP;
                case 'N':
                    return KNIGHT;
                case 'K':
                    return KING;
                default:
                    return null;
            }
        }
    }

    public final Kind kind;
    public final Color color;

    public Piece(Kind kind, Color color) {
        this.kind = kind;
        this.color = color;
    }

    @Override
    public String toString() {
        switch (this.kind) {
            case PAWN:
                return "P";

            case ROOK:
                return "R";
            
            case BISHOP:
                return "B";
        
            case QUEEN:
                return "Q";
                
            case KNIGHT:
                return "N";

            case KING:
            default:
                return "K";
        }
    }
    
    public boolean canMakeMove(Square src, Square dest, Board board) {

        if (this.color != board.getCurrentPlayer()) {
            return false;
        }
        
        return this.isValidMove(src, dest, board);
    }

    public boolean isValidMove(Square src, Square dest, Board board) {
        // first check what type of piece it is
        // make equation for each type of piece that gives all valid squares based on src
        // check if dest satisfies the corresponding equation based on src and piece kind

        boolean validSquare, validMove, validLength;

        switch (this.kind) {
            case PAWN:
                // TODO: en passant
                int startRow = this.color == Color.WHITE ? Board.WHITE_PAWNS_START_ROW
                    : Board.BLACK_PAWNS_START_ROW;

                boolean canGoForward = board.pathIsFree(src, dest)
                    && Square.isVerticalPath(src, dest)
                    && (Square.straightPathLength(src, dest) == 1
                    || (Square.straightPathLength(src, dest) == 2
                    && startRow == src.row));

                boolean canGoDiagonal = board.hasOpposingPiece(dest, this.color)
                    && Square.isDiagonalPath(src, dest)
                    && Square.straightPathLength(src, dest) == 1;

                boolean validDirection = this.color == Color.WHITE ? dest.row > src.row
                    : src.row > dest.row;

                return validDirection && (canGoForward || canGoDiagonal);

            case ROOK:
                validSquare = this.validSquare(dest, board);
                validMove = Square.isHorizontalPath(src, dest) 
                    || Square.isVerticalPath(src, dest);

                return validSquare && validMove && board.pathIsFree(src, dest);
            
            case BISHOP:
                validSquare = this.validSquare(dest, board);
                validMove = Square.isDiagonalPath(src, dest);

                return validSquare && validMove && board.pathIsFree(src, dest);
        
            case QUEEN:
                validSquare = this.validSquare(dest, board);
                validMove = Square.isStraightPath(src, dest);
                
                return validSquare && validMove && board.pathIsFree(src, dest);
                
            case KNIGHT:
                boolean validKnightPath = (Square.horizontalPathLength(src, dest) == 2
                    && Square.verticalPathLength(src, dest) == 1)
                    || (Square.horizontalPathLength(src, dest) == 1
                    && Square.verticalPathLength(src, dest) == 2);
                
                return this.validSquare(dest, board) && validKnightPath;

            case KING:
            default:
                validSquare = this.validSquare(dest, board);
                validMove = Square.isStraightPath(src, dest);
                validLength = Square.straightPathLength(src, dest) == 1;

                return validSquare && validMove && validLength && board.pathIsFree(src, dest);
        }
    }

    public boolean validSquare(Square dest, Board board) {
        return !board.hasPiece(dest) || board.hasOpposingPiece(dest, this.color);
    }
}