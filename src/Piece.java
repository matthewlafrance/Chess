public class Piece {
    public enum Kind {
        PAWN,
        ROOK,
        BISHOP,
        QUEEN,
        KNIGHT,
        KING,
    }

    private Kind kind;
    private Color color;

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
                return "N";
        }
    }

}