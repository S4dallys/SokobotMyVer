// a* implementation

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

class SokoPathfinding {

    public static void main(String[] args) {
        try {
            System.out.println(SokoPathfinding.aStar(SokoMap.mapToArray("fourboxes2")));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // try {
        //     SokoMap sm = SokoMap.mapToArray("twoboxes1");
        //     SokoMap left = sm.goTo('l');

        //     SokoMap.printMap(sm);
        //     SokoMap.printMap(left);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }

    }
    public static String aStar(SokoMap sm) {
        PriorityQueue<SokoNode> openList = new PriorityQueue<>(new SokoNodeComparator());
        ArrayList<String> closeList = new ArrayList<>();
        String path = "";

        SokoNode startNode = new SokoNode(sm, null, '\0', 0);
        
        startNode.setHCost();
   
        int gInc = sm.boxes;

        openList.add(startNode);

        while(!openList.isEmpty()) {
            SokoNode currentNode = openList.poll();
            closeList.add(currentNode.toString());

            // SokoMap.printMap(currentNode.state);

            if (currentNode.hCost == 0) {
                // win
                System.out.println("I WIN");
                while (currentNode.parent != null) {
                    path = currentNode.dir + path;
                    currentNode = currentNode.parent;
                }
                return path;
            }

            // TODO generate children
            SokoNode[] children = currentNode.getChildren(gInc);

            outer:
            for (SokoNode child : children) {
                if (child.state == null) {
                    continue;
                }

                if (closeList.contains(child.toString())) {
                    continue;
                }

                for (SokoNode sn : openList) {
                    if (sn.toString().equals(child.toString())) {
                        continue outer;
                    }
                }

                child.setHCost();
                openList.add(child);
                // try {
                // Runtime.getRuntime().exec("cls");  
                // } catch (Exception e) {}
                // SokoMap.printMap(child.state);
            }


        }
        return "Path Not Found";
    }
}

class SokoNodeComparator implements Comparator<SokoNode> {
    public int compare(SokoNode n1, SokoNode n2) {
        return n1.getFCost() - n2.getFCost();
    }
}