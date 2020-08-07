import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class pawn_regulations{
    public boolean isMoveLegal(JButton[][] chessboard, int initial_x, int initial_y, int final_x, int final_y){
        int column_difference = Math.abs(initial_y - final_y);
        int row_difference = Math.abs(initial_x - final_x);
        if(row_difference == 2 && isItFirstMove(initial_x) && column_difference == 0 && chessboard[final_x][final_y].getIcon() !=null){
            return false;
        }
        if(row_difference == 2 && isItFirstMove(initial_x) && column_difference == 0){
            return true;
        }
        else if(row_difference == 1 && column_difference == 0 && chessboard[final_x][final_y].getIcon() == null){
            return true;
        }
        if((initial_x - final_x) == 1 && column_difference == 1 && JChess.allianceOfPieceOnSquare(chessboard[final_x][final_y].getIcon()).equals("black")){
            return true;
        }
        if((final_x - initial_x) == -1 && column_difference == 1 && JChess.allianceOfPieceOnSquare(chessboard[final_x][final_y].getIcon()).equals("white")){
            return true;
        }
        //another else if for an enpassant will be added later
        return false;
    }
    private boolean isItFirstMove(int initial_x){
        if(initial_x == 6 || initial_x == 1){
            return true;
        }
        return false;
    }
    /*public int valueOnTile(int[][] chessBoard, int row, int column){
        if(chessBoard[row][column] > 0){
            
        }
    }
    */     
}


