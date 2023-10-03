// basic x,y coordinate

public class SokoPoint {
    public int x;
    public int y;
    public SokoPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean canMove(char[][] mapData) {

        boolean ans =  checkPoint(mapData, this, null, 0);

        return ans;
    }

    public boolean checkPoint(char[][] mapData, SokoPoint sp, SokoPoint ignore, int depth) {
        if (depth == 3) return false;

        SokoPoint[] dirs = {
            sp.went('u'), 
            sp.went('l'), 
            sp.went('d'), 
            sp.went('r')
        };

        boolean[] dirBlocked = new boolean[5];
        int dbInd = 0;

        for (SokoPoint s : dirs) {
            char c = s.representing(mapData);
            if (s.equals(ignore) || c == '#') {
                dirBlocked[dbInd] = false;
            }
            else if (c == ' ' || c == '@' || c == '+' || c == '.') {
                dirBlocked[dbInd] = true;
            } else {
                dirBlocked[dbInd] = checkPoint(mapData, s, sp, depth + 1);
            }
            dbInd++;
        }

        dirBlocked[dbInd++] = dirBlocked[0];

        int consecutiveFalses = 0;
        
        for (boolean b : dirBlocked) {
            if (b == false) consecutiveFalses++;
            else consecutiveFalses = 0;

            if (consecutiveFalses >= 2) {
                // System.out.println(sp + " - x");
                return false;
            }
        }

        // System.out.println(sp + " -   /");

        return true;
    }
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
    public SokoPoint went(char d) {
        SokoPoint copy = this.getCopy();
        copy.move(d);
        return copy;
    }
    public void move(char d) {
        switch (d) {
            case 'u':
            x--;
            break;
            case 'd':
            x++;
            break;
            case 'l':
            y--;
            break;
            case 'r':
            y++;
            break;
        }
    }
    public SokoPoint getCopy() {
        return new SokoPoint(x, y);
    }
    public char representing(char[][] mapData) {
        return mapData[x][y];
    }
    public boolean equals(SokoPoint sp) {
        return sp != null && (x == sp.x && y == sp.y);
    }
}