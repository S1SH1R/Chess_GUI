import javax.swing.*;
public class queen_regulation extends bishop_regulations {
    public boolean isMoveLegal(JButton[][] chessboard, int initial_x, int initial_y, int final_x, int final_y){
        if(super.isMovingInDiagnol(chessboard, initial_x, initial_y, final_x, final_y)){
            return true;
        }
        else if(super.isMovingInStraightLine(chessboard, initial_x, initial_y, final_x, final_y)){
            return true;
        }
        return false;        
    }
}
