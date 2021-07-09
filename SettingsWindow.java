package tic_tac;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SettingsWindow extends JFrame {
    private static final int WINDOW_WIDTH = 350;
    private static final int WINDOW_HEIGHT = 300;
    private static final int MIN_WIN_LENGHT = 3;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private static final String FIELD_SIZE_PREFIX = "Field size: ";
    private static final String WIN_LENGHT_PREFIX = "Win lenght: ";

    private JSlider sliderWinLenght;
    private JSlider sliderFieldSize;
    private JRadioButton humanVsAI;
    private JRadioButton humanVsHuman;
    private  GameWindow gameWindow;


    public  SettingsWindow(GameWindow gameWindow) {
        this.gameWindow = gameWindow;
        setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        setLocationRelativeTo(this.gameWindow);
        setResizable(false);
        setTitle("Settings. create your new game ");
        setLayout(new GridLayout(10,1 ));

        addChooseGameMode();
        addSetFieldSize();

        JButton buttonStart = new JButton("Start new game");
        buttonStart.addActionListener(e -> {
            submitSettings(gameWindow);
        });
        add(buttonStart);
    }

    private void submitSettings(GameWindow gameWindow) {
        int gameMode;
        if (humanVsAI.isSelected()){
            gameMode = GameMap.MODE_VS_AI;
        } else {
            gameMode = GameMap.MODE_VS_HUMAN;
        }
        int fieldSize = sliderFieldSize.getValue();
        int winLenght = sliderWinLenght.getValue();
        gameWindow.startGame(gameMode,fieldSize,winLenght);
        setVisible(false);
    }

    private void addSetFieldSize() {
        JLabel labelFieldSize = new JLabel(FIELD_SIZE_PREFIX + MIN_FIELD_SIZE);
        JLabel labelWinLenght = new JLabel(WIN_LENGHT_PREFIX + MIN_WIN_LENGHT);

        sliderFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MAX_FIELD_SIZE);
        sliderFieldSize = new JSlider(MIN_WIN_LENGHT, MAX_FIELD_SIZE, MAX_FIELD_SIZE);

        sliderFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int currentValue = sliderFieldSize.getValue();
                labelFieldSize.setText(FIELD_SIZE_PREFIX + currentValue);
                sliderWinLenght.setMaximum(currentValue);
            }
        });

        sliderWinLenght.addChangeListener(e -> labelWinLenght.setText(WIN_LENGHT_PREFIX + sliderWinLenght.getValue()));
        add(new JLabel("Choose field size:"));
        add(labelFieldSize);
        add(sliderFieldSize);
        add(new JLabel("Choose win lenght:"));
        add(labelWinLenght);
        add(sliderWinLenght);
    }

    private void addChooseGameMode() {
        add(new JLabel("Choose game mode:"));
        humanVsAI = new JRadioButton("Human versus AI", true);
        humanVsHuman = new JRadioButton("Human versus human");
        ButtonGroup gameMode = new ButtonGroup();
        gameMode.add(humanVsAI);
        gameMode.add(humanVsHuman);
        add(humanVsAI);
        add(humanVsHuman);
    }


}
