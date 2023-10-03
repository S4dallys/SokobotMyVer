// heuristic function

import java.util.ArrayList;

public class SokoHeuristic {
    
    // position is unwinnable if any box cannot be moved
        // this is true if two consecutive directions are blocked
    // first H should be amount of 'maneurability' boxes have
    // next should be distance of each box from goal

    public static void main(String[] args) {
        try 
        { 
            SokoMap sm = SokoMap.mapToArray("fourboxes3"); 
            System.out.println(
                isImpossible(sm)
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean isImpossible(SokoMap sm) {
        ArrayList<SokoPoint> boxes = new ArrayList<>();

        for (int i = 0; i < sm.rows; i++) {
            for (int j = 0; j < sm.cols; j++) {
                char c = sm.mapData[i][j];
                if (c == '$') {
                    boxes.add(new SokoPoint(i, j));
                }
            }
        }

        for (SokoPoint sp : boxes) {
            if (!sp.canMove(sm.mapData)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isComplete(SokoMap sm) {
        for (int i = 0; i < sm.rows; i++) {
            for (int j = 0; j < sm.cols; j++) {
                char c = sm.mapData[i][j];
                if (c == '$' || c== '.') return false;
            }
        }
        return true;
    }
}


