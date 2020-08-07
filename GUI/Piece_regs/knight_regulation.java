import javax.swing.*;
public class knight_regulation{
    public boolean isValidMove(JButton[][] chessboard, int init_r, int init_c, int final_r, int final_c){
        int rowDif = Math.abs(final_r - init_r);
        int colDif = Math.abs(final_c - init_c);
        if(JChess.allianceOfPieceOnSquare(chessboard[final_r][final_c].getIcon()).equals("white") && JChess.allianceOfPieceOnSquare(chessboard[init_r][init_c].getIcon()).equals("white")){
            return false;
        }
        else if(JChess.allianceOfPieceOnSquare(chessboard[final_r][final_c].getIcon()).equals("black") && JChess.allianceOfPieceOnSquare(chessboard[init_r][init_c].getIcon()).equals("black")){
            return false;
        }
        if(chessboard[final_r][final_c].getIcon() == JChess.whitePieces.get(5) || chessboard[final_r][final_c].getIcon() == JChess.blackPieces.get(5)){
            return false;
        }
        if(colDif == 1 && rowDif == 2){
            return true;
        }
        if (colDif == 2 && rowDif == 1){
            return true;
        }
        return false;
    }
}
    
