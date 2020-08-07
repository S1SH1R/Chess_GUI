import javax.swing.*;
public class rook_regulation{      
    public boolean isMovingInStraightLine(JButton[][] chessboard, int initial_x, int initial_y, int final_x, int final_y){
        int column_difference = Math.abs(initial_x - final_x);
        int row_difference = Math.abs(initial_y - final_y);
        if(JChess.allianceOfPieceOnSquare(chessboard[final_x][final_y].getIcon()).equals("white") && JChess.allianceOfPieceOnSquare(chessboard[initial_x][initial_y].getIcon()).equals("white")){
            return false;
        }
        else if(JChess.allianceOfPieceOnSquare(chessboard[final_x][final_y].getIcon()).equals("black") && JChess.allianceOfPieceOnSquare(chessboard[initial_x][initial_y].getIcon()).equals("black")){
            return false;
        }
        if(column_difference == 0 && row_difference > 0){
            return isItObstructed(chessboard, initial_x, initial_y, final_x, final_y);
        }
        else if(row_difference == 0 && column_difference > 0){
            return isItObstructed(chessboard, initial_x, initial_y, final_x, final_y);
        }
        return false;
    }
    private boolean isItObstructed(JButton[][] chessboard, int initial_x, int initial_y, int final_x, int final_y){
        int row_difference = Math.abs(initial_x - final_x);
        int col_difference = Math.abs(initial_y - final_y);
        if(col_difference == 0){
            if(initial_x > final_x){
                for(int i = 1; i<row_difference; i++){
                    if(chessboard[initial_x - i][initial_y].getIcon() != null){
                        return false;
                    }
                }
            }
            else if(initial_x < final_x){
                for(int i = 1; i<row_difference; i++){
                    if(chessboard[initial_x + i][initial_y].getIcon() != null){
                        return false;
                    }
                }
            }
        }
        else if(row_difference == 0){
            if(initial_y > final_y){
                for(int i = 1; i<col_difference; i++){
                    if(chessboard[initial_x][initial_y - i].getIcon() != null){
                        return false;
                    }
                }
            }
            else if(initial_y < final_y){
                for(int i = 1; i<col_difference; i++){
                    if(chessboard[initial_x][initial_y + i].getIcon() != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }
}

