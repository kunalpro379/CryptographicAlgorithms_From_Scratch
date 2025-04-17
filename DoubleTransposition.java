import java.util.*;

public class DoubleTranspositionCipher {

    public static String encrypt(String text, String rowKey, String columnKey) {
        text = text.replaceAll("\\s+", "").toUpperCase();

        int numRows = rowKey.length();
        int numCols = columnKey.length();
        int totalLen = numRows * numCols;

        while (text.length() < totalLen) {
            text += "X";
        }

        // Fill matrix row-wise
        char[][] matrix = new char[numRows][numCols];
        int index = 0;
        for (int i = 0; i < numRows; i++)
            for (int j = 0; j < numCols; j++)
                matrix[i][j] = text.charAt(index++);

        // Row transposition
        Integer[] rowOrder = getOrder(rowKey);
        char[][] rowTransposed = new char[numRows][numCols];
        for (int i = 0; i < numRows; i++)
            rowTransposed[i] = matrix[rowOrder[i]];

        // Column transposition
        Integer[] colOrder = getOrder(columnKey);
        char[][] finalMatrix = new char[numRows][numCols];
        for (int j = 0; j < numCols; j++)
            for (int i = 0; i < numRows; i++)
                finalMatrix[i][j] = rowTransposed[i][colOrder[j]];

        // Read column-wise
        StringBuilder cipher = new StringBuilder();
        for (char[] row : finalMatrix)
            for (char ch : row)
                cipher.append(ch);

        return cipher.toString();
    }

    private static Integer[] getOrder(String key) {
        Character[] keyArray = new Character[key.length()];
        for (int i = 0; i < key.length(); i++)
            keyArray[i] = key.charAt(i);

        Map<Character, Queue<Integer>> map = new TreeMap<>();
        for (int i = 0; i < key.length(); i++) {
            map.computeIfAbsent(keyArray[i], k -> new LinkedList<>()).add(i);
        }

        Integer[] order = new Integer[key.length()];
        int i = 0;
        for (Queue<Integer> q : map.values()) {
            while (!q.isEmpty()) {
                order[i++] = q.poll();
            }
        }
        return order;
    }

    public static void main(String[] args) {
        String text = "WE ARE DISCOVERED";
        String rowKey = "ZEBRAS";
        String colKey = "KEY";

        String cipher = encrypt(text, rowKey, colKey);
        System.out.println("Encrypted Text: " + cipher);
    }
}
