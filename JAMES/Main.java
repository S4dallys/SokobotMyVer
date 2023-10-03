

public class Main {
    public static void main(String[] args) {
        char[][] mapData = {{' ', '#', '#', '#', '#', '#', '#'},
    {' ', '#', ' ', ' ', '.', ' ', '#'},
    {'#', '#', ' ', ' ', ' ', ' ', '#'},
    {'#', '.', '.', ' ', '.', ' ', '#'},
    {'#', '#', '#', '#', '#', '#', '#'}};

    char[][] itemsData = {{' ', ' ', ' ', ' ', ' ', ' ', ' '},
    {' ', ' ', '@', ' ', ' ', ' ', ' '},
    {' ', ' ', '$', '$', '$', ' ', ' '},
    {' ', ' ', ' ', ' ', '$', ' ', ' '},
    {' ', ' ', ' ', ' ', ' ', ' ', ' '}};

    SokoBot sk = new SokoBot();
    String ans = sk.solveSokobanPuzzle(7, 5, mapData, itemsData);
    System.out.println(ans);
    }
}
