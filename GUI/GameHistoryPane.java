import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.List;
public class GameHistoryPane extends JFrame{
    private final DataModel model;
    private final JScrollPane gamePanel;
    private JTable table;
    private static final Dimension GAME_PANEL_DIMENSION = new Dimension(100, 600);
    //private static final String[] columnNames = {"White", "Black"};
    /*String[][] data = { 
            { "Kundan Kumar Jha", "4031"}, 
            { "Anand Jha", "6014"} 
        }; */
    public GameHistoryPane(){
        super("MovesPlayed");
        this.setLayout(new BorderLayout());
        this.model = new DataModel();
        this.table = new JTable(model);
        table.setRowHeight(15);
        //table.setRowHeight(10);
        this.gamePanel = new JScrollPane(table);
        gamePanel.setColumnHeaderView(table.getTableHeader());
        this.add(gamePanel, BorderLayout.CENTER);
        setSize(GAME_PANEL_DIMENSION);
        setResizable(false);
        setVisible(true);
    }
    private static class Row {

        private String whiteMove;
        private String blackMove;
        
        public String getWhiteMove(){
            return this.whiteMove;
        }

        public String getBlackMove(){
            return this.blackMove;
        }

        public void setWhiteMove(final String move){
            this.whiteMove = move;
        }

        public void setBlackMove(final String move){
            this.blackMove = move;
        }

    }
     public static class DataModel extends DefaultTableModel{

        private final Vector<Row> values;
        private static final String[] NAMES = {"White", "Black"};

        DataModel(){
            super();
            this.values = new Vector<>();
            
        }
        //clear is used to reset the game history 
        public void clear(){
            this.values.clear();
            setRowCount(0); //Method in super class.
        } 
        //Row count getter method
        
        public int getRowCount(){
            if(this.values == null){
                return 0;
            }
            return this.values.size();
        }
        //Column count getter method
        
        public int getColumnCount(){
            return NAMES.length;
        }
        // Gets the value(move name) at a particular cell in the table.
        
        public Object getValueAt(final int row, final int col){
            final Row currentRow = this.values.get(row);
            if(col == 0){
                return currentRow.getWhiteMove();
            } 
            else if (col == 1){
                return currentRow.getBlackMove();
            }
            return null;
        }
        
        private void insterRow(int row){
            
        }
        
        public void setValueAt(final Object aValue,
                               final int row,
                               final int col){
            final Row currentRow;
            if(this.values.size() <= row){
                currentRow = new Row();
                this.values.add(currentRow);
            } 
            else{
                currentRow = this.values.get(row);
            }
            if(col == 0){
                currentRow.setWhiteMove((String) aValue);
                fireTableRowInserted(row, row);
            } 
            else if(col == 1){
                currentRow.setBlackMove((String)aValue);
                fireTableCellUpdated(row, col);
            }
            fireTableDataChanged();
        }
        public String getColumnName(final int col){
            return NAMES[col];
        }
    }
}
    

