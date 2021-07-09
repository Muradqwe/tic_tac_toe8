package tic_tac;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class GameMap extends JPanel {
    public static final int MODE_VS_AI = 0;
    public static final int MODE_VS_HUMAN = 1;
    public static final Random RANDOM = new Random();
    private static final int DOT_HUMAN = '1';
    private static final int DOT_AI = '2';
    private static final int DOT_EMPTY = '0';
    private static final int DOT_PADDING = '7';
    private static final int STATE_DRAW = '0';
    private static final int STATE_WIN_HUMAN = '1';
    private static final int STATE_WIN_AI = '2';

    private int stateGameOver;
    private int[][] field;
    private int fieldSizeX;
    private int fieldSizeY;
    private int winLenght;
    private int cellWidth;
    private int cellHeight;
    private boolean isGameOver;
    private boolean isInitialized;
    private int gameMode;
    private int playerNumTurn;


    private int scoreHuman;
    private int scoreAi;

    public GameMap() {
        isInitialized = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                update(e);
            }
        });
    }

    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized) return;
        int dot = gameMode == MODE_VS_AI ? DOT_HUMAN : playerNumTurn == 1 ? DOT_HUMAN : DOT_AI;
        if (!playerTurn(e, dot)) return;
        if (gameCheck(dot, STATE_WIN_HUMAN)) return;
        if (gameMode == MODE_VS_AI) {
            aiTurn();
            repaint();
            if (gameCheck(DOT_AI, STATE_WIN_AI)) return;
        }
    }

    private boolean playerTurn(MouseEvent event, int dot) {
        int cellX = event.getX() / cellWidth;
        int cellY = event.getX() / cellHeight;
        if (!isCellValid(cellY, cellX) || isCellEmpty(cellY,cellX)) return false;
        field[cellY][cellX] = dot;
        repaint();
        playerNumTurn = playerNumTurn == 1? 2 : 1;
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void render(Graphics g) {
        if (!isInitialized) return;
        int width = getWidth();
        int height = getHeight();
        cellHeight = height /  fieldSizeY;
        cellWidth = width / fieldSizeX;
        g.setColor( Color.BLACK);

        for (int i = 0; i < fieldSizeY; i++) {
            int y = i * cellHeight;
            g.drawLine(0, y, width, y);
            
        }
        for (int i = 0; i < fieldSizeX; i++) {
            int x = i * cellWidth;
            g.drawLine(x, 0, x, height);

        }
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++){
                if (isCellEmpty(y, x )) continue;
                if (field[y][x] ==DOT_HUMAN){
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);
                } else if (field[y][x] == DOT_AI){
                    g.setColor(Color.RED);
                    g.fillRect(x * cellWidth + DOT_PADDING,
                            y * cellHeight + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeight - DOT_PADDING * 2);


                }
            }
            
        }
        if (isGameOver){
            showGameOverMessage(g);
        }
    }

    private void showGameOverMessage(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,getHeight()/2 - 60,getWidth(),120);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 58));
        switch (stateGameOver) {
            case STATE_DRAW -> g.drawString("DRAW!!!", getWidth()/ 4, getHeight() / 2);
            case STATE_WIN_HUMAN -> g.drawString("HUMAN WINS!!!", getWidth()/ 4, getHeight() / 2);
            case STATE_WIN_AI -> g.drawString("AI WINS!!!", getWidth()/ 4, getHeight() / 2);
        }
    }

    private boolean gameCheck(int dot, int stateGameOver) {
        if (checkWin(dot, winLenght)) {
            this.stateGameOver = stateGameOver;
            isGameOver = true;
            repaint();
            return true;
        }

        if (checkDraw()) {
           this.stateGameOver = STATE_DRAW;
           isGameOver = true;
           repaint();
           return true;
        }
        return false;
    }

    private boolean checkDraw() {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (isCellEmpty(y,x));

            }

        }
        return false;
    }


    private boolean CheckDraw () {
            for (int y = 0; y < fieldSizeY; y++) {
                for (int x = 0; x < fieldSizeX; x++) {
                    if (isCellEmpty(y, x)) return false;

                }

            }
            return true;
        }
    private void aiTurn () {
            if (scanField(DOT_AI, winLenght)) return;
            if (scanField(DOT_HUMAN, winLenght)) return;
            if (scanField(DOT_AI, winLenght - 1)) return;
            if (scanField(DOT_HUMAN, winLenght - 1)) return;
            if (scanField(DOT_AI, winLenght - 2)) return;
            if (scanField(DOT_HUMAN, winLenght - 2)) return;
            aiTurnEasy();

        }
    private void aiTurnEasy () {
            int x;
            int y;

            do {

                x = RANDOM.nextInt(fieldSizeX);
                y = RANDOM.nextInt(fieldSizeY);
            } while (!isCellEmpty(x, y));
            field[y][x] = DOT_AI;
        }

    private boolean scanField ( int dot, int lenght){
            for (int y = 0; y < fieldSizeY; y++) {
                for (int x = 0; x < fieldSizeX; x++) {
                    if (isCellEmpty(y, x)) {
                        field[y][x] = dot;
                        if (checkWin(dot, lenght)) {
                            if (dot == DOT_AI) return true;
                            if (dot == DOT_HUMAN) {
                                field[y][x] = DOT_AI;
                                return true;
                            }
                        }
                        field[y][x] = DOT_EMPTY;

                    }
                }

            }
            return false;
        }
    private boolean checkWin ( int dot, int length){
            for (int y = 0; y < fieldSizeY; y++) {            // проверяем всё поле
                for (int x = 0; x < fieldSizeX; x++) {
                    if (checkLine(x, y, 1, 0, length, dot)) return true;    // проверка  по +х
                    if (checkLine(x, y, 1, 1, length, dot)) return true;    // проверка по диагонали +х +у
                    if (checkLine(x, y, 0, 1, length, dot)) return true;    // проверка линию по +у
                    if (checkLine(x, y, 1, -1, length, dot)) return true;    // проверка по диагонали +х -у
//                if (checkLine(i, j, -1, 0, length, dot)) return true;	// проверка  по +х
//                if (checkLine(i, j, -1, 1, length, dot)) return true;	// проверка по диагонали +х +у
//                if (checkLine(i, j, 0, -1, length, dot)) return true;	// проверка линию по +у
//                if (checkLine(i, j, -1, -1, length, dot)) return true;	// проверка по диагонали +х -у
                }
            }
            return false;
        }
    private boolean checkLine ( int x, int y, int incrementX, int incrementY, int len, int dot){
            int endXLine = x + (len - 1) * incrementX;            // конец линии по Х
            int endYLine = y + (len - 1) * incrementY;            // конец по У
            if (!isCellValid(endYLine, endXLine)) return false;    // Выход линии за пределы
            for (int i = 0; i < len; i++) {                    // идем по линии
                if (field[y + i * incrementY][x + i * incrementX] != dot) return false;    // символы одинаковые?
            }
            return true;
        }


    private boolean isCellValid ( int y, int x){
            return x >= 0 && y >= 0 && x < fieldSizeX;
        }

    private boolean isCellEmpty ( int y, int x){
            return field[y][x] == DOT_EMPTY;
        }


    public void StartNewGame ( int gameMode, int fieldSize, int winLenght){
            this.gameMode = gameMode;
            this.fieldSizeX = this.fieldSizeY = fieldSize;
            this.winLenght = winLenght;
            this.playerNumTurn = 1;
            field = new int[fieldSizeY][fieldSizeX];
            isInitialized = true;
            isGameOver = false;
            repaint();


        }
}