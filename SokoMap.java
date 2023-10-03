// used to convert txt to map data -> char[][]
// returns SokoMap

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class SokoMap {

    public char[][] mapData;
    public int rows;
    public int cols;
    public int boxes;

    private SokoMap(int rows, int cols, int boxes, char[][] mapData) {
        this.rows = rows;
        this.cols = cols;
        this.boxes = boxes;
        this.mapData = mapData;
    }

    // adds data to empty array
    public static SokoMap mapToArray(String mapName) throws FileNotFoundException {
        // get file
        File file = new File(String.format("maps/%s.txt", mapName));

        // scanner
        Scanner scanner = new Scanner(file);
        
        // put data into char[][]
        String[] lines = new String[100];
        int rows = 0;
        int cols = 0;
        int boxes = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            lines[rows] = line;

            // update map dims
            rows++;
            cols = Math.max(cols, line.length());
        }

        // close sc
        scanner.close();
        
        // make array
        char[][] map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            int j;
            for (j = 0; j < lines[i].length(); j++) {
                char c = lines[i].charAt(j);
                map[i][j] = c;

                if (c == '*' || c == '$') boxes++;
            }

            // pad spaces at end of string
            for (int k = j; k < cols; k++) {
                map[i][k] = ' ';
            }
        }
        
        // construct SokoMap
        return new SokoMap(rows, cols, boxes, map);
    }

    public static void printMap(SokoMap sm) {
        char[][] map = sm.mapData;
        for (char[] row : map) {
            for (char col : row) {
                System.out.print(col);
            }
            System.out.print("\n");
        }
    }

    public SokoMap getEndState() {
        char[][] endState = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = mapData[i][j];

                if (c == '$') {
                    endState[i][j] = ' ';
                }
                else if (c == '.') {
                    endState[i][j] = '*';
                } 
                else {
                    endState[i][j] = mapData[i][j];
                }
            }
        }
        return new SokoMap(rows, cols, boxes, endState);
    }

    public ArrayList<ArrayList<SokoPoint>> getBoxesAndGoals() {
        ArrayList<ArrayList<SokoPoint>> results = new ArrayList<ArrayList<SokoPoint>>();
        ArrayList<SokoPoint> boxes = new ArrayList<>();
        ArrayList<SokoPoint> goals = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char c = mapData[i][j];

                if (c == '$') {
                    boxes.add(new SokoPoint(i, j));
                }
                else if (c == '.' || c == '+') {
                    goals.add(new SokoPoint(i, j));
                }
            }
        }
        results.add(boxes);
        results.add(goals);
        return results;
    }

    public SokoMap goTo(char dir) {
        char[] edit = new char[3];
        char[][] newMap = new char[rows][cols];

        SokoPoint player = null;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMap[i][j] = mapData[i][j];

                if (player == null && (mapData[i][j] == '@' || mapData[i][j] == '+')) {
                    player = new SokoPoint(i, j);
                }
            }
        }
        
        edit[0] = player.representing(mapData);
        if (dir == 'u') {
            edit[1] = mapData[player.x - 1][player.y];
            if (player.x - 2 >= 0)
                edit[2] = mapData[player.x - 2][player.y];
            else
                edit[2] = '#';
        }
        else if (dir == 'd') {
            edit[1] = mapData[player.x + 1][player.y];
            if (player.x + 2 < rows)
                edit[2] = mapData[player.x + 2][player.y];
            else
                edit[2] = '#';
        }
        if (dir == 'l') {
            edit[1] = mapData[player.x][player.y - 1];
            if (player.y - 2 >= 0)
                edit[2] = mapData[player.x][player.y - 2];
            else
                edit[2] = '#';
        }
        if (dir == 'r') {
            edit[1] = mapData[player.x][player.y + 1];
            if (player.y + 2 < cols)
                edit[2] = mapData[player.x][player.y + 2];
            else
                edit[2] = '#';
        }

        if (edit[1] == '#' || 
        ((edit[1] == '*' || edit[1] == '$') && (edit[2] != ' ' && edit[2] != '.'))) {
            return null;
        }

        edit[0] = edit[0] == '+' ? '.' : ' ';

        if (edit[1] == '$' || edit[1] == '*') {
            
            edit[1] = (edit[1] == '*') ? '+' : '@';
            edit[2] = (edit[2] == '.') ? '*' : '$';

        } else {

            edit[1] = (edit[1] == '.') ? '+' : '@';
        }

        if (dir == 'u') {
            newMap[player.x][player.y] = edit[0];
            newMap[player.x - 1][player.y] = edit[1];
            if (player.x - 2 >= 0)
                newMap[player.x - 2][player.y] = edit[2];
        }
        else if (dir == 'd') {
            newMap[player.x][player.y] = edit[0];
            newMap[player.x + 1][player.y] = edit[1];
            if (player.x + 2 < rows)
                newMap[player.x + 2][player.y] = edit[2];
        }
        if (dir == 'l') {
            newMap[player.x][player.y] = edit[0];
            newMap[player.x][player.y - 1] = edit[1];
            if (player.y - 2 >= 0)
                newMap[player.x][player.y - 2] = edit[2];
        }
        if (dir == 'r') {
            newMap[player.x][player.y] = edit[0];
            newMap[player.x][player.y + 1] = edit[1];
            if (player.y + 2 < cols)
                newMap[player.x][player.y + 2] = edit[2];
        }

        return new SokoMap(rows, cols, boxes, newMap);
    }
}