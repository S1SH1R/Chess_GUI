import javax.swing.*;
public class king_reg{
    public boolean isMoveValid(JButton[][] chessboard, int init_x, int init_y, int final_x, int final_y){
        int rowDelta = Math.abs(final_x - init_x);
        int colDelta = Math.abs(final_y - init_y);
        if(rowDelta >= 1 || colDelta >= 1){
            return true;
        }
        return false;
    }
}

