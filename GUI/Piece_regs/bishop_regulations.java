import javax.swing.*;
public class bishop_regulations extends rook_regulation{
    public boolean isMovingInDiagnol(JButton[][] chessboard, int initial_x, int initial_y, int final_x, int final_y){
        int column_difference = Math.abs(initial_y - final_y);
        int row_difference = Math.abs(initial_x - final_x);
        if(column_difference != row_difference){
            return false;
        }
        if(JChess.allianceOfPieceOnSquare(chessboard[final_x][final_y].getIcon()).equals("white") && JChess.allianceOfPieceOnSquare(chessboard[initial_x][initial_y].getIcon()).equals("white")){
            return false;
        }
        else if(JChess.allianceOfPieceOnSquare(chessboard[final_x][final_y].getIcon()).equals("black") && JChess.allianceOfPieceOnSquare(chessboard[initial_x][initial_y].getIcon()).equals("black")){
            return false;
        }
        if(isItObstructed(chessboard, initial_x, initial_y, final_x, final_y)){
            return true;
        }
        return false;
    }
    private boolean isItObstructed(JButton[][] chessboard, int initial_x, int initial_y, int final_x, int final_y){
        int column_difference = Math.abs(initial_y - final_y);
        int row_difference = Math.abs(initial_x - final_x);
        for(int i = 1; i<row_difference; i++){
            if(initial_x<final_x && initial_y<final_y){
                if(chessboard[initial_x + i][initial_y + i].getIcon() != null){
                    return false;
                }
            }
            else if(initial_x<final_x && initial_y>final_y){
                if(chessboard[initial_x + i][initial_y - i].getIcon() != null){
                    return false;
                }
            }
            else if(initial_x>final_x && initial_y<final_y){
                if(chessboard[initial_x - i][initial_y + i].getIcon() != null){
                    return false;
                }
            }
            else if(initial_x>final_x && initial_y>final_y){
                if(chessboard[initial_x - i][initial_y - i].getIcon() != null){
                    return false;
                }
            }
        }
        return true;
    }
}

