import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
public class JChess extends JFrame{
    private Container contents;

    //Components;
    private JButton[][] squares = new JButton[8][8]; // 2D Array of JButton objects. Each tile in on squares will be a JButton object.

    //Colour for squares. Default is white so only black is needed for now. Can have different coloured tiles which will be updated later.
    private Color lightTileColour = Color.decode("#FFFACD");
    private Color darkTileColour = Color.decode("#593E1A");

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    //starting and current position for the pieces.
    //Upper left corner is (0,0) and so rank 8 is "0" and rank 1 is "7" because arrays used
    private int initial_row;
    private int initial_col;
    private int final_row;
    private int final_col;
    
    //Total Moves played
    private static int moveNumber = 1;// Once game ends resetMoves() resets this to 1

    //Arraylists for whtie and black pieces. Makes identifiying what Icon(piece) is on what square(JButton)
    public static final ArrayList<ImageIcon> whitePieces = new ArrayList<ImageIcon>();
    public static final ArrayList<ImageIcon> blackPieces = new ArrayList<ImageIcon>();
    private final String[] promotablepieces = {"knight", "bishop", "rook", "queen"};
    
    //Variable determines if a new window has been opened. Binary switch function
    private int isWindowOpen;

    public JChess(){
        super("Chess");
        final JMenuBar chessMenuBar = new JMenuBar();
        populateMenuBar(chessMenuBar);
        setJMenuBar(chessMenuBar);
        contents = getContentPane();
        contents.setLayout(new GridLayout(8,8));
        //create event handlers
        PieceSelector pieceSelector = new PieceSelector();

        //Adding Icons to the White and black piece arraylists
        whitePieces.add(new ImageIcon("WP.PNG"));
        whitePieces.add(new ImageIcon("WN.PNG"));
        whitePieces.add(new ImageIcon("WB.PNG"));
        whitePieces.add(new ImageIcon("WR.PNG"));
        whitePieces.add(new ImageIcon("WQ.PNG"));
        whitePieces.add(new ImageIcon("WK.PNG"));
        //black pieces
        blackPieces.add(new ImageIcon("BP.PNG"));
        blackPieces.add(new ImageIcon("BN.PNG"));
        blackPieces.add(new ImageIcon("BB.PNG"));
        blackPieces.add(new ImageIcon("BR.PNG"));
        blackPieces.add(new ImageIcon("BQ.PNG"));
        blackPieces.add(new ImageIcon("BK.PNG"));

        //Adds board components by creating JButton class and adding it to the 2d Array JButton, Squares
        for(int i = 0; i<8; i++)
        {
            for(int j = 0; j<8; j++)
            {
                squares[i][j] = new JButton();
                if((i+j) % 2 != 0){
                    darkTileColour.darker();
                    squares[i][j].setBackground(darkTileColour);
                }
                contents.add(squares[i][j]);
                squares[i][j].addActionListener(pieceSelector);
            }
        }
        //Set the image of each piece on the respective starting tiles
        for(int column = 0; column<8; column++){
            squares[6][column].setIcon(whitePieces.get(0));
        }
        for(int column = 0; column<8; column++){
            squares[1][column].setIcon(blackPieces.get(0));
        }
        // for the rooks
        squares[7][7].setIcon(whitePieces.get(3));
        squares[7][0].setIcon(whitePieces.get(3));
        squares[0][0].setIcon(blackPieces.get(3));
        squares[0][7].setIcon(blackPieces.get(3));
        // for the knights
        squares[7][1].setIcon(whitePieces.get(1));
        squares[7][6].setIcon(whitePieces.get(1));
        squares[0][1].setIcon(blackPieces.get(1));
        squares[0][6].setIcon(blackPieces.get(1));
        //for the bishops
        squares[7][2].setIcon(whitePieces.get(2));
        squares[7][5].setIcon(whitePieces.get(2));
        squares[0][2].setIcon(blackPieces.get(2));
        squares[0][5].setIcon(blackPieces.get(2));
        //for the queen 
        squares[7][3].setIcon(whitePieces.get(4));
        squares[0][3].setIcon(blackPieces.get(4));
        // for the kings
        squares[7][4].setIcon(whitePieces.get(5));
        squares[0][4].setIcon(blackPieces.get(5));

        //Set the size of the window to be displayed
        setSize(OUTER_FRAME_DIMENSION);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void populateMenuBar(final JMenuBar tableMenuBar){
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createViewMenu());
    }

    private void fileChooser(){
        JFileChooser loadPGN = new JFileChooser();
        FileNameExtensionFilter txt = new FileNameExtensionFilter("Txt files", "txt");
        loadPGN.setFileFilter(txt);
        int filechoosen = loadPGN.showOpenDialog(loadPGN);
        if(filechoosen == JFileChooser.APPROVE_OPTION){

        }
    }
    //Opens gamehistory pane
    private void openGameHistoryPane(){
        GameHistoryPane open = new GameHistoryPane(); 
    }

    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem LoadGames = new JMenuItem("Load Games");
        LoadGames.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    fileChooser();
                }
            });
        fileMenu.add(LoadGames);
        return fileMenu;
    }
    
    private JMenu createViewMenu(){
        final JMenu viewMenu = new JMenu("View");
        final JMenuItem notation = new JMenuItem("Notation");
        notation.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                isWindowOpen = 1;
                openGameHistoryPane();
            }
        });
        viewMenu.add(notation);
        return viewMenu;
    }

    public static String allianceOfPieceOnSquare(Icon icon){
        for(int i = 0; i<whitePieces.size(); i++){
            if(whitePieces.get(i) == icon){
                return "white";
            }
            if(blackPieces.get(i) == icon){
                return "black";
            }
        }
        return "null";
    }
    
    private void addWhiteMove(){
        MoveReading write = new MoveReading(squares, initial_row, initial_col, final_row, final_col);
        GameHistoryPane.DataModel rowAdder = new GameHistoryPane.DataModel();
        rowAdder.setValueAt(write.moveName(final_row, final_col), 1, 0);
    }
    
    private void addBlackMove(){
        MoveReading write = new MoveReading(squares, initial_row, initial_col, final_row, final_col);
        GameHistoryPane.DataModel rowAdder = new GameHistoryPane.DataModel();
        rowAdder.setValueAt(write.moveName(final_row, final_col), moveNumber, 1);
    }

    private boolean validateMove(int final_r, int final_c){
        final_row = final_r; // setting the final row equal to the insatnce variable so it can be used to output the move names
        final_col = final_c; // setting the final column
                                                        //White pawn validation
        if(squares[initial_row][initial_col].getIcon() == whitePieces.get(0)){
            //System.out.println(1234);
            pawn_regulations one = new pawn_regulations();
            if(final_r == 0 && one.isMoveLegal(squares, initial_row, initial_col, final_r, final_c)){
                int selected = JOptionPane.showOptionDialog(null, "Promote your pawn", "Pick a piece", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, whitePieces.get(0), promotablepieces, promotablepieces[0]);
                addWhiteMove();
                if(selected == 0){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(whitePieces.get(1));
                    return true;
                }
                else if(selected == 1){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(whitePieces.get(2));
                    return true;
                }
                else if(selected == 2){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(whitePieces.get(3));
                    return true;
                }
                else if(selected == 3){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(whitePieces.get(4));
                    return true;
                }
                return false;
            }
            if(one.isMoveLegal(squares, initial_row, initial_col, final_r, final_c)){
                addWhiteMove();
                MoveReading write = new MoveReading(squares, initial_row, initial_col, final_row, final_col);
                System.out.println(write.moveName(final_row, final_col));
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(whitePieces.get(0));
                return true;
            }
        }
        
                                                            //White knight Validation
        else if(squares[initial_row][initial_col].getIcon() == whitePieces.get(1)){
            //System.out.println(1234);
            knight_regulation one = new knight_regulation();
            if(one.isValidMove(squares, initial_row, initial_col, final_r, final_c)){
                addWhiteMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(whitePieces.get(1));
                return true;
            }
        }
        
                                                            //White Bishop Validation
        else if(squares[initial_row][initial_col].getIcon() == whitePieces.get(2)){
            //System.out.println(1234);
            bishop_regulations one = new bishop_regulations();
            if(one.isMovingInDiagnol(squares, initial_row, initial_col, final_r, final_c)){
                addWhiteMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(whitePieces.get(2));
                return true;
            }
        }
        
                                                            //White Rook Validation
        else if(squares[initial_row][initial_col].getIcon() == whitePieces.get(3)){
            //System.out.println(1234);
            rook_regulation one = new rook_regulation();
            if(one.isMovingInStraightLine(squares, initial_row, initial_col, final_r, final_c)){
                addWhiteMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(whitePieces.get(3));
                return true;
            }
        }
        
                                                            //White Queen Validation
        else if(squares[initial_row][initial_col].getIcon() == whitePieces.get(4)){
            //System.out.println(1234);
            queen_regulation one = new queen_regulation();
            if(one.isMoveLegal(squares, initial_row, initial_col, final_r, final_c)){
                addWhiteMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(whitePieces.get(4));
                return true;
            }
        }
        //White King Validation
        else if(squares[initial_row][initial_col].getIcon() == whitePieces.get(5)){
            //System.out.println(1234);
            king_reg one = new king_reg();
            if(one.isMoveValid(squares, initial_row, initial_col, final_r, final_c)){
                addWhiteMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(whitePieces.get(5));
                return true;
            }
        }
        //Black pawn validation
        else if(squares[initial_row][initial_col].getIcon() == blackPieces.get(0)){
            //System.out.println(1234);
            pawn_regulations one = new pawn_regulations();
            if(final_r == 7 && one.isMoveLegal(squares, initial_row, initial_col, final_r, final_c)){
                int selected = JOptionPane.showOptionDialog(null, "Promote your pawn", "Pick a piece", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, blackPieces.get(0), promotablepieces, promotablepieces[0]);
                addBlackMove();
                if(selected == 0){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(blackPieces.get(1));
                    return true;
                }
                else if(selected == 1){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(blackPieces.get(2));
                    return true;
                }
                else if(selected == 2){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(blackPieces.get(3));
                    return true;
                }
                else if(selected == 3){
                    squares[initial_row][initial_col].setIcon(null);
                    squares[final_r][final_c].setIcon(blackPieces.get(4));
                    return true;
                }
                return false;
            }
            if(one.isMoveLegal(squares, initial_row, initial_col, final_r, final_c)){
                addBlackMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(blackPieces.get(0));
                return true;
            }
        }
        //Black Knight Validation
        else if(squares[initial_row][initial_col].getIcon() == blackPieces.get(1)){
            //System.out.println(1234);
            knight_regulation one = new knight_regulation();
            if(one.isValidMove(squares, initial_row, initial_col, final_r, final_c)){
                addBlackMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(blackPieces.get(1));
                return true;
            }
        }
        //Black Bishop Validation
        else if(squares[initial_row][initial_col].getIcon() == blackPieces.get(2)){
            //System.out.println(1234);
            bishop_regulations one = new bishop_regulations();
            if(one.isMovingInDiagnol(squares, initial_row, initial_col, final_r, final_c)){
                addBlackMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(blackPieces.get(2));
                return true;
            }
        }
        //Black Rook Validation
        else if(squares[initial_row][initial_col].getIcon() == blackPieces.get(3)){
            //System.out.println(1234);
            rook_regulation one = new rook_regulation();
            if(one.isMovingInStraightLine(squares, initial_row, initial_col, final_r, final_c)){
                addBlackMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(blackPieces.get(3));
                return true;
            }
        }
        //black Queen Validation
        else if(squares[initial_row][initial_col].getIcon() == blackPieces.get(4)){
            //System.out.println(1234);
            queen_regulation one = new queen_regulation();
            if(one.isMoveLegal(squares, initial_row, initial_col, final_r, final_c)){
                addBlackMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(blackPieces.get(4));
                return true;
            }
        }
        //Blac king validation
        else if(squares[initial_row][initial_col].getIcon() == blackPieces.get(5)){
            //System.out.println(1234);
            king_reg one = new king_reg();
            if(one.isMoveValid(squares, initial_row, initial_col, final_r, final_c)){
                addBlackMove();
                squares[initial_row][initial_col].setIcon(null);
                squares[final_r][final_c].setIcon(blackPieces.get(5));
                return true;
            }
            return false;
        }
        return false;
    }
    private static void resetMoves(){
        moveNumber = 1;
    }
    /*private void processClick(int row, int col){ // adds circles int eh squares where a piece can move.
    if(squares[row][col].getIcon() == whitePieces.get(1)){

    }
    }*/
    /*private void afterMoveMadeClick(){ // removes circles after the move has been complete 
    }*/
    private class PieceSelector implements ActionListener{
        private int clickCounter = 0; //This will help keep track of the number of times the button is pressed so that i can store the right initial and final coordinates for the pieces

        //Tells the program what action was performed by taking in the event that occured
        public void actionPerformed(ActionEvent e){ // one of the classes in ActionListetener
            Object source = e.getSource();// where the click happened or the source ofthe event
            for(int i = 0; i<8; i++){
                for(int j = 0; j<8; j++){
                    if(clickCounter == 0 && source == squares[i][j]){
                        initial_row = i;
                        initial_col = j;
                        //processClick(i,j);
                        clickCounter++;
                        //System.out.println("1");
                        return;
                    }
                    else if(clickCounter == 1 && source == squares[i][j] && validateMove(i, j)){
                        // afterMoveMadeClick();
                        //System.out.println(1203021230)
                        moveNumber++;
                        clickCounter--;
                    }
                    //System.out.println(clickCounter);//some error message to say there is no piece on that square.

                }
            }
        }
    }
}

