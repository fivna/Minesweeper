import java.util.Scanner;
public class App {
    public static void clearScreen(){
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
    public static String [][] revealPossibleTiles (int rowIndex, int colIndex, String[][] revealedMinesweeperGrid, String [][] hiddenMinesweeperGrid, boolean[][] tilesRevealed, int numRows, int numCols){
        if (rowIndex < 0 || rowIndex >= numRows || colIndex < 0 || colIndex >= numCols || tilesRevealed[rowIndex][colIndex] || "🚩".equals(hiddenMinesweeperGrid[rowIndex][colIndex])) {
            return hiddenMinesweeperGrid;
        }
        tilesRevealed[rowIndex][colIndex] = true;
        hiddenMinesweeperGrid[rowIndex][colIndex] = revealedMinesweeperGrid[rowIndex][colIndex];
        if (revealedMinesweeperGrid[rowIndex][colIndex].equals("⬜")) {
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex + 1, colIndex, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex - 1, colIndex, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex, colIndex + 1, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex, colIndex - 1, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex + 1, colIndex + 1, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex - 1, colIndex - 1, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex + 1, colIndex - 1, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
            hiddenMinesweeperGrid = revealPossibleTiles(rowIndex - 1, colIndex + 1, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
        }
        return hiddenMinesweeperGrid;
    }
    public static void main(String[] args){
        clearScreen();
        System.out.println("MINESWEEPER - BY FIONA");
        System.out.println();
        Scanner input = new Scanner(System.in);
        int numRows = 0;
        int numCols = 0;
        int numBombs = 0;
        boolean gridChosen = false; 
        while (!gridChosen){
            System.out.println("Choose a grid: Small (s), Medium (m), large (l)");
            char gridChoice = input.nextLine().charAt(0);
            if (gridChoice == 's'){
                numRows = 6;
                numCols = 6;
                numBombs = 9;
                gridChosen = true;
            }
            else if (gridChoice == 'm'){
                numRows = 14;
                numCols = 9;
                numBombs = 25;
                gridChosen = true;
            }
            else if (gridChoice == 'l'){
                numRows = 20;
                numCols = 9;
                numBombs = 35;
                gridChosen = true;
            }
            else {
                System.out.println("Please enter a valid character that corresponds to the grid type you would like to choose.");
            }
        }
        clearScreen();
        boolean [][] bombExists = new boolean[numRows][numCols];
        String [][] revealedMinesweeperGrid = new String[numRows][numCols];
        String[][] hiddenMinesweeperGrid = new String[numRows][numCols];
        System.out.println("MINESWEEPER - BY FIONA");
        System.out.println();
        System.out.print("  ");
        for (int i = 1; i < numCols + 1; i++){
            System.out.print( i + " ");
        }
        System.out.println();
        for (int i = 0; i < hiddenMinesweeperGrid.length; i++){
            for (int j = 0; j < hiddenMinesweeperGrid[0].length; j++){
                hiddenMinesweeperGrid[i][j] = "🟨";
            }
        }
        for (int i = 0; i < hiddenMinesweeperGrid.length; i++){
            System.out.print((char)(i + 97) + " ");
            for (int j = 0; j < hiddenMinesweeperGrid[0].length; j++){
                System.out.print(hiddenMinesweeperGrid[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Number of Bombs Left: " + numBombs);
        String command = "";
        boolean bombClicked = false;
        boolean unmarkedBombs = true;
        boolean allTilesRevealed = false;
        boolean gameWon = false;
        int colIndex;
        int rowIndex;
        int numBombsPlaced = 0;
        boolean[][] tilesRevealed = new boolean[numRows][numCols];
        for (int i = 0; i < tilesRevealed.length; i++){
            for (int j = 0; j < tilesRevealed[0].length; j++){
                tilesRevealed[i][j] = false;
            }
        }
        boolean firstSelection = true;
        while (!bombClicked && !command.equals("q") && !gameWon){
            if (!firstSelection){
                unmarkedBombs = false;
                for (int i = 0; i < hiddenMinesweeperGrid.length; i++){
                    for (int j = 0; j < hiddenMinesweeperGrid[0].length; j++){
                        if (bombExists[i][j] && !hiddenMinesweeperGrid[i][j].equals("🚩") || !bombExists[i][j] && hiddenMinesweeperGrid[i][j].equals("🚩")){
                            unmarkedBombs = true;
                        }
                    }
                }
                allTilesRevealed = true;
                for (int k = 0; k < hiddenMinesweeperGrid.length; k++){
                    for (int l = 0; l < hiddenMinesweeperGrid[0].length; l++){
                        if (hiddenMinesweeperGrid[k][l].equals("🟨")){
                            allTilesRevealed = false;
                        }
                    }
                }
            }
            if (allTilesRevealed && !unmarkedBombs){
                gameWon = true;
                break;
            }
            if (!firstSelection){
                System.out.println("Number of Bombs Left: " + numBombs);
            }
            System.out.println("Type c to select a tile or q to quit:");
            command = input.nextLine();
            if (command.equals("c") || command.equals("j")){
                System.out.println("Coordinates of Tile (number + letter): ");
                
                String coordinates = input.nextLine();
                colIndex = (int)(coordinates.charAt(0)-49);
                rowIndex = (int)(coordinates.charAt(1)-97);
                System.out.println("Action (j - deselect tile, f - place a flag, r - remove a flag, v - reveal the tile): ");
                command = input.nextLine();
                if (command.equals("v")){
                    if (firstSelection){
                        firstSelection = false;
                        numBombsPlaced = 0;
                        for (int i = 0; i < bombExists.length; i++){
                            for (int j = 0; j < bombExists[0].length; j++){
                            bombExists[i][j] = false;
                            }
                        }
                        while (numBombsPlaced < numBombs){
                            int bombRow = (int)(Math.random() * numRows);
                            int bombCol = (int)(Math.random() * numCols);
                            if (bombRow != rowIndex && bombCol != colIndex && bombRow != rowIndex - 1 && bombRow != rowIndex + 1 && bombRow != colIndex - 1 && bombRow != colIndex + 1){
                                if (!bombExists[bombRow][bombCol]){
                                    bombExists[bombRow][bombCol] = true;
                                    numBombsPlaced++;
                                }
                            }
                        }
                        for (int i = 0; i < revealedMinesweeperGrid.length; i++){
                            for (int j = 0; j < revealedMinesweeperGrid[0].length; j++){
                                int adjacentBombs = 0;
                                if (!bombExists[i][j]){
                                    if (i + 1 < numRows && bombExists[i + 1][j]){
                                    adjacentBombs++;
                                    }
                                    if (i - 1 >= 0 && bombExists[i - 1][j]){
                                    adjacentBombs++;
                                    }
                                    if (j + 1 < numCols && bombExists[i][j + 1]){
                                        adjacentBombs++;
                                    }
                                    if (j - 1 >= 0 && bombExists[i][j - 1]){
                                        adjacentBombs++;
                                    }
                                    if (i + 1 < numRows && j + 1 < numCols && bombExists[i + 1][j + 1]){
                                        adjacentBombs++;
                                    }
                                    if (i + 1 < numRows && j - 1 >= 0 && bombExists[i + 1][j - 1]){
                                        adjacentBombs++;
                                    }
                                    if (i - 1 >= 0 && j + 1 < numCols && bombExists[i - 1][j + 1]){
                                        adjacentBombs++;
                                    }
                                    if (i - 1 >= 0 && j - 1 >= 0 && bombExists[i - 1][j - 1]){
                                        adjacentBombs++;
                                    }
                                }
                                switch (adjacentBombs){
                                    case 0:
                                        if (!bombExists[i][j]){
                                            revealedMinesweeperGrid[i][j] = "⬜";
                                        }
                                        break;
                                    case 1:
                                        revealedMinesweeperGrid[i][j] = "1️⃣ ";
                                        break;
                                    case 2:
                                        revealedMinesweeperGrid[i][j] = "2️⃣ ";
                                        break;
                                    case 3:
                                        revealedMinesweeperGrid[i][j] = "3️⃣ ";
                                        break;
                                    case 4:
                                        revealedMinesweeperGrid[i][j] = "4️⃣ ";
                                        break;
                                    case 5:
                                        revealedMinesweeperGrid[i][j] = "5️⃣ ";
                                        break;
                                    case 6:
                                        revealedMinesweeperGrid[i][j] = "6️⃣ ";
                                        break;
                                    case 7:
                                        revealedMinesweeperGrid[i][j] = "7️⃣ ";
                                        break;
                                    case 8:
                                        revealedMinesweeperGrid[i][j] = "8️⃣ ";
                                        break;
                                }  
                                if (bombExists[i][j]){
                                    revealedMinesweeperGrid[i][j] = "💣";
                                }
                            }  
                        }
                    }
                    if (bombExists[rowIndex][colIndex]){
                        bombClicked = true;
                        break;
                    }
                    revealPossibleTiles(rowIndex, colIndex, revealedMinesweeperGrid, hiddenMinesweeperGrid, tilesRevealed, numRows, numCols);
                    for (int i = 0; i < tilesRevealed.length; i++){
                        for (int j = 0; j < tilesRevealed[0].length; j++){
                            if (tilesRevealed[i][j]){
                                hiddenMinesweeperGrid[i][j] = revealedMinesweeperGrid[i][j];
                            }
                        }
                    }
                }
                else if (command.equals("f")){
                    hiddenMinesweeperGrid[rowIndex][colIndex] = "🚩";
                    numBombs--;
                }
                else if (command.equals("r")){
                    if (rowIndex >= 0 && rowIndex < tilesRevealed.length && colIndex >= 0 && colIndex < tilesRevealed[0].length && hiddenMinesweeperGrid[rowIndex][colIndex] == "🚩"){
                        hiddenMinesweeperGrid[rowIndex][colIndex] = "🟨";
                        numBombs++;
                    }
                }
                else {
                    System.out.println("Please enter a valid command.");
                }
            }  
            clearScreen();
            System.out.println("MINESWEEPER - BY FIONA");
            /*System.out.println();
            System.out.print("  ");
                for (int i = 1; i < numCols + 1; i++){
                        System.out.print( i + " ");
                }
                System.out.println();
                for (int i = 0; i < revealedMinesweeperGrid.length; i++) {
                    System.out.print((char)(i + 'a') + " ");
                    for (int j = 0; j < revealedMinesweeperGrid[0].length; j++) {
                        System.out.print(revealedMinesweeperGrid[i][j]);
                    }
                    System.out.println();
                }         */
            System.out.println();
            System.out.println();
            System.out.print("  ");
            for (int i = 1; i < numCols + 1; i++){
                    System.out.print( i + " ");
            }
            System.out.println();
            for (int i = 0; i < hiddenMinesweeperGrid.length; i++) {
                System.out.print((char)(i + 'a') + " ");
                for (int j = 0; j < hiddenMinesweeperGrid[0].length; j++) {
                    System.out.print(hiddenMinesweeperGrid[i][j]);
                }
                System.out.println();
            }         
            System.out.println();
        }
        if (!unmarkedBombs && allTilesRevealed){
            System.out.println("Congratulations, you won!");   
        }
        else if (bombClicked){
            clearScreen();
            System.out.println("MINESWEEPER - BY FIONA");
            System.out.println();
            System.out.println("Sorry, you clicked on a bomb! Game over.");
            System.out.println();
            System.out.print("  ");
            for (int i = 1; i < numCols + 1; i++){
                    System.out.print( i + " ");
            }
            System.out.println();
            for (int i = 0; i < hiddenMinesweeperGrid.length; i++) {
                System.out.print((char)(i + 'a') + " ");
                for (int j = 0; j < hiddenMinesweeperGrid[0].length; j++) {
                    if (bombExists[i][j]){
                        hiddenMinesweeperGrid[i][j] = "💥";
                    }
                    System.out.print(hiddenMinesweeperGrid[i][j]);
                }
                System.out.println();
            }        
        }
        else if (command.equals("q")){
            clearScreen();
            System.out.println("MINESWEEPER - BY FIONA");
            System.out.println();
            System.out.println("You quit -- game over.");
            System.out.println();
            System.out.print("  ");
            for (int i = 1; i < numCols + 1; i++){
                    System.out.print( i + " ");
            }
            System.out.println();
            for (int i = 0; i < hiddenMinesweeperGrid.length; i++) {
                System.out.print((char)(i + 'a') + " ");
                for (int j = 0; j < hiddenMinesweeperGrid[0].length; j++) {
                    if (bombExists[i][j]){
                        hiddenMinesweeperGrid[i][j] = "💥";
                    }
                    System.out.print(hiddenMinesweeperGrid[i][j]);
                }
                System.out.println();
            }        
        }
    }
}
