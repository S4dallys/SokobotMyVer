import java.util.ArrayList;

public class SokoNode {
    public int gCost;
    public int hCost;
    public char dir;
    public SokoMap state;
    public SokoNode parent;

    public SokoNode(SokoMap state, SokoNode parent, char dir, int gCost) {
        this.state = state;
        this.gCost = gCost;
        this.parent = parent;
        this.dir = dir;
    }

    public int getFCost() {
        return gCost + hCost;
    }

    public void setHCost() {
        ArrayList<ArrayList<SokoPoint>> bag = state.getBoxesAndGoals();
        int[] distances = new int[bag.get(0).size()];
        SokoPoint minGoal = null;
        // int minDistance = 69;
        int i = 0;
        int distance = 0;

        if (bag.get(1).size() == 0) {
            hCost = 0;
            return;
        }

        for (SokoPoint box : bag.get(0)) {
            distance = 0;
            for (SokoPoint goal : bag.get(1)) {
                distance += Math.abs(box.x - goal.x) + Math.abs(box.y - goal.y);
                // if (minGoal == null || distance < minDistance) {
                //     minGoal = goal;
                //     minDistance = distance;
                // }
            }
            // distances[i++] = minDistance;
            // minGoal = null;
            // bag.get(1).remove(minGoal);
            distances[i++] = (int) distance / bag.get(1).size();
        }

        int hCost = 0;

        for (int d : distances) {
            hCost += d;
        }


        this.hCost = (int) hCost / bag.get(0).size();
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public SokoNode[] getChildren(int gCostInc) {
        SokoNode[] children = new SokoNode[4];

        children[0] = new SokoNode(state.goTo('u'), this, 'u', gCost + gCostInc);
        children[1] = new SokoNode(state.goTo('l'), this, 'l', gCost + gCostInc);
        children[2] = new SokoNode(state.goTo('d'), this, 'd', gCost + gCostInc);
        children[3] = new SokoNode(state.goTo('r'), this, 'r', gCost + gCostInc);

        for (SokoNode child : children) {
            if (child.state != null && SokoHeuristic.isImpossible(child.state)) {
                child.state = null;
            }
        }

        return children;
    }

    public String toString() {
        char[][] map = state.mapData;
        String str = "";
        for (char[] s : map) {
            str += new String(s);
        }

        return str;
    }
}
