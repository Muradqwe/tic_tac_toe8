package tic_tac;



import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGTH = 640;
    private GameMap gameMap;

    public GameWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH,WINDOW_HEIGTH);
        setLocationRelativeTo(null);
        setTitle("Tic tac toe");
        setResizable(false);
        JButton btnStart = new JButton("<html><body><b>START</b></body></html ");
        JButton btnStop = new JButton("<html><body><b>EXIT</b></body></html ");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,2));
        buttonPanel.add(btnStart);
        buttonPanel.add(btnStop);

        gameMap = new GameMap();
        SettingsWindow settingsWindow = new SettingsWindow(this);


        add(buttonPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);
        setVisible(true);
        btnStart.addActionListener(e -> settingsWindow.setVisible(true));
        btnStop.addActionListener(e -> System.exit(0));
    }

    public void startGame( int gameMode, int fieldSize, int winLenght) {
        gameMap.StartNewGame(gameMode,fieldSize, winLenght);

    }


}
