package Lesson4;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TickTackToe {

    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';
    private static final char DOT_EMPTY = '•';
    private static final int SIZE = 3;
    public static final String DRAW = "Ничья!";
    public static final String AI_WIN = "Искусственный интеллект победил!";
    public static final String HUMAN_WIN = "Поздравляю! Вы победили!";

    private static int firstMoveAi = 0;
    private static char[][] map;
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        initMap();
        printMap();
        move();
    }

    private static void move() {
        while (true) {
            humanTurn();
            printMap();
            if (isWin(DOT_X)) {
                if (isMapFull()) {
                    System.out.println(DRAW);
                    break;
                }
                message(DOT_X);
                break;
            }

            System.out.println();

            aiTurn();
            printMap();
            if (isWin(DOT_O)) {
                if (isMapFull()) {
                    System.out.println(DRAW);
                    break;
                }
                message(DOT_O);
                break;
            }
        }
    }

    private static void aiTurn() {
        int count = 0;
        int row = 0, col = 0;
        boolean check = true;

        if (SIZE == 3 && firstMoveAi == 0) {
            if (isEmptyCell(2,2)) {
                row = 1;
                col = 1;
                check = false;
            }
            firstMoveAi = 1;
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (isEmptyCell(i + 1, j + 1)) {
                    map[i][j] = DOT_O;
                    if (isWin(DOT_O)) {
                        row = i;
                        col = j;
                        check = false;
                    }
                    map[i][j] = DOT_EMPTY;
                }
            }
        }

        if (check) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                   if (isEmptyCell(i + 1, j + 1)) {
                       map[i][j] = DOT_X;
                       if (isWin(DOT_X)) {
                           row = i;
                           col = j;
                           check = false;
                       }
                       map[i][j] = DOT_EMPTY;
                   }
                }
            }
        }

        if (check) {
            while (count < 4) {
                count++;
                int chose = random.nextInt(4);
                switch (chose) {
                    case 0: {
                        if (isEmptyCell(1, 1)) {
                            row = 0;
                            col = 0;
                            break;
                        }
                    }
                    case 1: {
                        if (isEmptyCell(1, SIZE)) {
                            row = 0;
                            col = SIZE - 1;
                            break;
                        }
                    }
                    case 2: {
                        if (isEmptyCell(SIZE, 1)) {
                            row = SIZE - 1;
                            col = 0;
                            break;
                        }
                    }
                    case 3: {
                        if (isEmptyCell(SIZE, SIZE)) {
                            row = SIZE - 1;
                            col = SIZE - 1;
                            break;
                        }
                    }
                    default: {
                        if (count == 4) {
                            do {
                                row = random.nextInt(SIZE);
                                col = random.nextInt(SIZE);
                            } while (!isEmptyCell(row + 1, col + 1));
                        }
                        continue;
                    }
                }
            }
        }
        map[row][col] = DOT_O;
    }

    private static void humanTurn() {
        System.out.println("Введите координаты row col: ");
        int row = 0;
        int col = 0;
        do {
            row = readIndex();
            col = readIndex();

            if (!checkDigit(row) || !checkDigit(col)) {
                System.out.println("Координаты должны быть от 1 до " + SIZE);
                continue;
            }
            if (isEmptyCell(row, col)) {
                break;
            }
            System.out.println("Эта точка уже занята.");
        } while (true);
        map[row - 1][col - 1] = DOT_X;
    }

    private static void message(char sign) {
        if (sign == DOT_X) {
            System.out.println(HUMAN_WIN);
        } else {
            System.out.println(AI_WIN);
        }
    }

    private static boolean isWin(char sign) {
        if (isMapFull()) {
            return true;
        }
        if (checkGameOver(sign)) {
            return true;
        }
        return false;
    }

    private static boolean checkGameOver(char sign) {
        int count = 0;
        int column = map[0].length - 1;
        for (int q = 0; q < SIZE; q++) {
            if (map[q][column] == sign) {
                count++;
                column--;
                if (count == SIZE) {
                    count = 0;
                    return true;
                }
            } else {
                count = 0;
                break;
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == sign) {
                    count++;
                    if (count == SIZE) {
                        count = 0;
                        return true;
                    }
                } else {
                    count = 0;
                    break;
                }
            }
        }

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[j][i] == sign) {
                    count++;
                    if (count == SIZE) {
                        count = 0;
                        return true;
                    }
                } else {
                    count = 0;
                    break;
                }
            }
        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i == j) {
                    if (map[i][j] == sign) {
                        count++;
                        if (count == SIZE) {
                            count = 0;
                            return true;
                        }
                    } else {
                        count = 0;
                        break;
                    }
                }
            }
        }

        return false;
    }

    private static int readIndex() {
        while (!scanner.hasNextInt()) {
            System.out.println("Координаты должны иметь целочисленное значение!");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static boolean isEmptyCell(int row, int col) {
        return map[row - 1][col - 1] == DOT_EMPTY;
    }

    private static boolean isMapFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (isEmptyCell(row + 1, col + 1)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean checkDigit(int digit) {
        return digit >=1 && digit <= SIZE;
    }

    private static void initMap() {
        map = new char[SIZE][SIZE];

        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], DOT_EMPTY);
        }
    }

    public static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < map.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

    }
}
