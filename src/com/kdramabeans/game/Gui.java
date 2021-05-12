package com.kdramabeans.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.kdramabeans.game.Game.*;
import static com.kdramabeans.game.Game.music;

public class Gui {

    private JFrame window;
    private JPanel titleNamePanel, buttonPanel, mainTextPanel, generalButtonPanel;
    private JLabel titleNameLabel, gifLabel, sceneLabel;
    private JButton startButton, nextButton, enterButton, restartButton, quitButton, helpButton, musicButton;
    public static JTextArea mainTextArea, statusArea, userPrompt, inventoryArea;
    public static JTextField mainTextField;
    private Container container;
    private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 30);
    private static final Font normalFont = new Font("Times New Roman", Font.PLAIN, 15);
    private TextFieldHandler textHandler = new TextFieldHandler();

    /*
      ctor that initializes the home page of the game
     */
    public Gui() throws Exception {
        item = new DataParser();
        window = new JFrame();
        titleNamePanel = new JPanel();
        buttonPanel = new JPanel();

        // JFrame setup
        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.WHITE);
        window.setLayout(null);
        window.setResizable(false);
        window.setTitle("KDramaBeans Game");
        container = window.getContentPane();

        // Panel Title (can be used to place all the text and scenario we want to use)
        titleNamePanel.setBounds(100, 100, 600, 150);
        titleNamePanel.setBackground(Color.white);
        titleNameLabel = new JLabel("You are my Destiny!");
        titleNameLabel.setForeground(Color.black);
        titleNameLabel.setFont(titleFont);


        try {
            Icon imgGif = new ImageIcon(getClass().getResource("/koreanair.gif"));
            gifLabel = new JLabel(imgGif);
            gifLabel.setBounds(7, 170, 800, 200);
            container.add(gifLabel);
        } catch (NullPointerException e) {
            System.out.println("Can't Find Image");
        }

        // start button setup - should link to the start of the game
        buttonPanel.setBounds(300, 500, 200, 100);
        buttonPanel.setBackground(Color.white);
        Image startImage = findImage("startButton.png", 200, 75);
        startButton = new JButton(new ImageIcon(startImage));
        startButton.setBackground(Color.white);
        startButton.setForeground(Color.black);
        startButton.setFont(normalFont);
        startButton.setBorderPainted(false);
        startButton.addActionListener(textHandler);

        // calls up all the components and makes the screen visible
        titleNamePanel.add(titleNameLabel);
        buttonPanel.add(startButton);
        container.add(titleNamePanel);
        container.add(buttonPanel);
        window.setVisible(true);
    }

    public void createGameScreen() {
        container.remove(gifLabel);
        // disables to home page panel and will display panel below
        buttonPanel.setVisible(false);

        titleNamePanel.setVisible(false);

        // sets up the panel
        mainTextPanel = new JPanel();
        mainTextPanel.setBounds(100, 250, 600, 350);
        mainTextPanel.setBackground(Color.white);

        //sets up scene image
        try {
            Icon scenePng = new ImageIcon(getClass().getResource("/random.jpg"));
            sceneLabel = new JLabel(scenePng);
            sceneLabel.setBorder(new LineBorder(Color.black));
            sceneLabel.setBounds(0, 50, 800, 200);
            container.add(sceneLabel, SwingConstants.CENTER);
        } catch (Exception e) {
            System.out.println("Can't Find Image");
        }

        // sets up the textArea
        mainTextArea = new JTextArea(printStatus());
        mainTextArea.setBounds(100, 250, 600, 250);
        mainTextArea.setBackground(Color.white);
        mainTextArea.setForeground(Color.black);
        mainTextArea.setFont(normalFont);
        mainTextArea.setLineWrap(true);

        //sets up inventory area
        inventoryArea = new JTextArea(player.printGrabbedItems() + "\n" + player.printEvidence());
        inventoryArea.setBounds(100, 650, 600, 50);
        inventoryArea.setBackground(Color.white);
        inventoryArea.setForeground(Color.black);
        inventoryArea.setEditable(false);
        // sets up enter button
        Image enterImage = findImage("enterButton.png", 100, 50);
        enterButton = new JButton(new ImageIcon(enterImage));
        enterButton.setBorderPainted(false);
        buttonPanel.setBounds(550, 700, 150, 75);
        enterButton.setBackground(Color.white);
        enterButton.setForeground(Color.black);
        enterButton.setFont(normalFont);
        enterButton.addActionListener(textHandler);
        buttonPanel.add(enterButton);
        generalButtons();

        // x,y,width,height

        // sets up the statusArea
        statusArea = new JTextArea();
        statusArea.setBounds(100, 600, 600, 300);
        statusArea.setBackground(Color.white);
        statusArea.setForeground(Color.black);
        statusArea.setFont(normalFont);
        statusArea.setLineWrap(true);
        statusArea.setText("Commands:\n\n" + showHelp());

        // set up textField for userInput
        mainTextField = new JTextField();
        mainTextField.setText("");
        mainTextField.setBounds(100, 700, 450, 75);
        mainTextField.setBackground(Color.white);
        mainTextField.setForeground(Color.black);
        mainTextField.setFont(normalFont);
        mainTextField.addKeyListener(textHandler);

        mainTextPanel.add(mainTextArea);
        mainTextPanel.add(statusArea);
        container.add(mainTextField);
        container.add(mainTextPanel);
        container.add(inventoryArea);
        buttonPanel.setVisible(true);
    }


    public Image findImage(String path, int width, int height) {
        Image foundImage = null;
        try {
            foundImage = ImageIO.read(new File(this.getClass().getResource("/" + path).toURI())).getScaledInstance(width, height, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("Can't find Image: " + path);
            e.printStackTrace();
        }
        return foundImage;
    }

    public void generalButtons() {
        generalButtonPanel = new JPanel();

        Image quitImage = findImage("xButton.png", 50, 50);
        quitButton = new JButton(new ImageIcon(quitImage));
        quitButton.setBorderPainted(false);

        Image restartImage = findImage("restart.png", 50, 50);
        restartButton = new JButton(new ImageIcon(restartImage));
        restartButton.setBorderPainted(false);

        Image helpImage = findImage("infoButton.png", 50, 50);
        helpButton = new JButton(new ImageIcon(helpImage));
        helpButton.setBorderPainted(false);

        Image musicImage = findImage("sound.png", 50, 50);
        musicButton = new JButton(new ImageIcon(musicImage));
        musicButton.setBorderPainted(false);

        generalButtonPanel.setBounds(0, -10, 1300, 60);
        generalButtonPanel.add(restartButton);
        generalButtonPanel.add(musicButton);
        generalButtonPanel.add(helpButton);
        generalButtonPanel.add(quitButton);
        musicButton.addActionListener(textHandler);
        quitButton.addActionListener(textHandler);
        helpButton.addActionListener(textHandler);
        restartButton.addActionListener(textHandler);
        container.add(generalButtonPanel);
    }

    public class TextFieldHandler implements KeyListener, ActionListener {

        // restart, quit, help, enter(click)
        @Override
        public void keyTyped(KeyEvent e) {
            keyPressed(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_ENTER) {
                playGame();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Map<Object, Runnable> allActions = new HashMap<>() {{
                put(enterButton, Game::playGame);
                put(quitButton, () -> System.exit(0));
                put(restartButton, () -> {
                    System.out.println("Restarting...");
                    story.restartGame();
                    player.clearItems();

                    mainTextArea.setText(printStatus());
                    inventoryArea.setText("Inventory is Empty!");
                    statusArea.setText("");
                });
                put(helpButton, () -> statusArea.setText("These are your commands:\n\n" + showHelp()));
                put(startButton, () -> {
                    startButton.getParent().remove(startButton);
                    createGameScreen();
                    music = new BGM("goblin.wav");
                });
                put(musicButton, () -> {
                    if (music.isPlaying()) {
                        music.pauseSong();
                    } else {
                        music.playSong();
                    }
                });
                put(nextButton, Gui.this::createGameScreen);
            }};
            allActions.getOrDefault(e.getSource(), () -> System.out.println("You have not selected a button.")).run();
        }
    }

    private String showHelp() {
        return "[examine,look,see,view] [Item] - to get the item description.\n" +
                "[grab,get,acquire,attain,snatch] [Item] - to add item to your inventory.\n" +
                "[drop,remove] [Item] - to drop item from your inventory.\n" +
                "[use,throw] [Item] - to use item in a scene.\n" +
                "[choose,go,move,select] [Option] - to go to next scene.\n";
    }
}