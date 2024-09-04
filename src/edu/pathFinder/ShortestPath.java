package edu.pathFinder;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Student name : P. T. R. Rodrigo
 * Student id : 20220673 / w1953311
 */
public class ShortestPath {
    private int rowCount;
    private int columnCount;
    private char[][] mapGrid;
    private int startRow;
    private int finishRow;
    private int startColumn;
    private int finishColumn;
    private final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public ShortestPath(String filePath) {
        try {
            if (!filePath.isEmpty()) {
                createMapFromFile(filePath);
                defineStartEndAndPrintMap();
                List<int[]> shortestPath = findShortestPath();
                if (shortestPath != null && !shortestPath.isEmpty()) {
                    displayShortestPath(shortestPath);
                    System.out.println();
                } else {
                    System.out.println("No path found");
                }
            }
        } catch (IOException e) {
            System.out.println("The file doesn't exist.");
        }
    }

    public void createMapFromFile(String filePath) throws IOException {
        List<String> mapLines = new ArrayList<>();
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            mapLines.add(scanner.nextLine());
        }
        this.rowCount = mapLines.size();
        this.columnCount = mapLines.get(0).length();
        this.mapGrid = new char[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                mapGrid[i][j] = mapLines.get(i).charAt(j);
            }
        }
    }

    public void defineStartEndAndPrintMap() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                char cell = mapGrid[i][j];
                if (mapGrid[i][j] == 'S') {
                    this.startRow = i;
                    this.startColumn = j;
                } else if (mapGrid[i][j] == 'F') {
                    this.finishRow = i;
                    this.finishColumn = j;
                }
                System.out.print(cell);
            }
            System.out.println();
        }
        System.out.println("Maze identified Successfully.");
    }

    private List<int[]> buildPath(int[][] parent) {
        List<int[]> path = new ArrayList<>();
        int currentRow = finishRow;
        int currentColumn = finishColumn;
        while (currentRow != startRow || currentColumn != startColumn) {
            path.add(0, new int[]{currentRow, currentColumn});
            int index = parent[currentRow][currentColumn];
            currentRow = index / columnCount;
            currentColumn = index % columnCount;
        }
        path.add(0, new int[]{startRow, startColumn});
        return path;
    }

    public List<int[]> findShortestPath() {
        boolean[][] visited = new boolean[rowCount][columnCount];
        int[][] parent = new int[rowCount][columnCount];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startColumn});
        visited[startRow][startColumn] = true;
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            if (row == finishRow && col == finishColumn) {
                return buildPath(parent);
            }
            for (int[] direction : DIRECTIONS) {
                int newRow = row;
                int newCol = col;
                while (true) {
                    newRow += direction[0];
                    newCol += direction[1];
                    if (!isValidCell(newRow, newCol) || mapGrid[newRow][newCol] == '0') {
                        break;
                    }
                    if (mapGrid[newRow][newCol] == 'F') {
                        parent[newRow][newCol] = row * columnCount + col;
                        return buildPath(parent);
                    }
                }
                newRow -= direction[0];
                newCol -= direction[1];
                if (!visited[newRow][newCol]) {
                    queue.offer(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                    parent[newRow][newCol] = row * columnCount + col;
                }
            }
        }
        return new ArrayList<>();
    }

    public void displayShortestPath(List<int[]> path) {
        System.out.println("Shortest Path:");
        System.out.println("1. Start at (" + (startColumn + 1) + "," + (startRow + 1) + ")");
        int stepCounter = 0;
        for (int i = 1; i < path.size(); i++) {
            int[] currentCell = path.get(i);
            String moveDescription = getMoveDescription(path, i, currentCell);
            System.out.println((i + 1) + ". " + moveDescription + "(" + (currentCell[1] + 1) + "," + (currentCell[0] + 1) + ")");
            stepCounter = i;
        }
        System.out.println((stepCounter + 2) + ". Done!");
    }

    private static String getMoveDescription(List<int[]> path, int i, int[] currentCell) {
        int[] previousCell = path.get(i - 1);
        int changeOfRow = currentCell[0] - previousCell[0];
        int changeOfColumn = currentCell[1] - previousCell[1];
        String moveDescription = "";
        if (changeOfColumn == 0 && changeOfRow < 0) {
            moveDescription = "Move up to ";
        } else if (changeOfRow > 0 && changeOfColumn == 0) {
            moveDescription = "Move down to ";
        } else if (changeOfRow == 0 && changeOfColumn > 0) {
            moveDescription = "Move right to ";
        } else if (changeOfRow == 0 && changeOfColumn < 0) {
            moveDescription = "Move left to ";
        }
        return moveDescription;
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < rowCount && col >= 0 && col < columnCount;
    }

}

