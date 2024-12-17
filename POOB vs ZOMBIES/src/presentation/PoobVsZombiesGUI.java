package presentation;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;

import domain.Planta;
import domain.PoobVsZombies;
import domain.Zombie;


public class PoobVsZombiesGUI {
    private JFrame frame;
    private GameBoard gameboard;
    private java.util.List<Planta> selectedPlants = new java.util.ArrayList<>();
    private java.util.List<Zombie> selectedZombies = new java.util.ArrayList<>();
    private PoobVsZombies juego;
    private JLabel sunLabel;
    private JLabel brainLabel;


    public PoobVsZombiesGUI() {
        frame = new JFrame("Plants vs Zombies");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        createMenuBar();
        juego = new PoobVsZombies();
    }

    /**
     * Crea la barra de menú para la aplicación.
     */

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem newGameItem = new JMenuItem("Nuevo Juego");
        JMenuItem openGameItem = new JMenuItem("Abrir Partida");
        JMenuItem saveGameItem = new JMenuItem("Guardar Partida");
        JMenuItem exitItem = new JMenuItem("Salir");

        newGameItem.addActionListener(e -> showPlantSelectionScreen());
        openGameItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Abrir partida no implementado."));
        saveGameItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Guardar partida no implementado."));
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newGameItem);
        fileMenu.add(openGameItem);
        fileMenu.add(saveGameItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
    }

    private void showPlantSelectionScreen() {
        createSelectModeScreen();
    }

    /**
     * Crea la pantalla para seleccionar plantas con imágenes y botones interactivos.
     *
     * @param mode El modo de juego seleccionado (por ejemplo, "Player vs Player" o "Single Player").
     */

    private void createSelectPlantsScreen(String mode) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(100, 200, 100)); // Fondo verde claro para la pantalla

        JLabel title = new JLabel("Selecciona tus Plantas - " + mode, SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        // Panel para botones de plantas
        JPanel plantsPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 fila, 3 columnas
        plantsPanel.setOpaque(true);
        plantsPanel.setBackground(new Color(80, 170, 80));

        // Definir las plantas disponibles con sus nombres y rutas de imágenes
        String[][] plants = {
                {"sunflower", "/resources/sunflower.png"},
                {"peashooter", "/resources/peashooter.png"},
                {"wallnut", "/resources/wallnut.png"},
                {"potatoMine", "/resources/potatoMine.png"},
                {"eciPlanta", "/resources/eciPlanta.png"}
        };

        // Lista para almacenar las plantas seleccionadas
        java.util.List<Planta> selectedPlants = new java.util.ArrayList<>();

        // Botón "Listo", declarado como final para ser usado dentro del ActionListener
        JButton readyButton = new JButton("Listo");
        readyButton.setEnabled(false); // Deshabilitado inicialmente
        readyButton.setBackground(new Color(50, 150, 50));
        readyButton.setForeground(Color.WHITE);
        readyButton.setFocusPainted(false);
        readyButton.setBorder(BorderFactory.createRaisedBevelBorder());
        readyButton.addActionListener(e -> {
            if (selectedPlants.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Selecciona al menos una planta antes de continuar.");
            } else {
                this.selectedPlants = new java.util.ArrayList<Planta>(selectedPlants); // Guardar la selección
                if (mode.equals("Player vs Player")) {
                    createSelectZombiesScreen(); // Mostrar selección de zombies
                } else if (!mode.equals("Player vs Player")) {
                    GameBoard gameboard = new GameBoard(frame);
                    gameboard.showGameBoard(
                            (ArrayList<Planta>) selectedPlants
                    );
                }
            }
        });

        // Crear botones con imágenes y nombres de plantas
        for (String[] plant : plants) {
            JButton plantButton = new JButton(plant[0]);
            plantButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            plantButton.setHorizontalTextPosition(SwingConstants.CENTER);
            plantButton.setForeground(Color.BLACK);
            plantButton.setFont(new Font("Arial", Font.BOLD, 14));
            plantButton.setFocusPainted(false);
            plantButton.setBorder(BorderFactory.createRaisedBevelBorder());
            plantButton.setBackground(new Color(200, 255, 200));

            // Agregar la imagen de la planta
            try {
                ImageIcon plantIcon = new ImageIcon(getClass().getResource(plant[1]));
                Image scaledImage = plantIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                plantButton.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                plantButton.setText(plant[0] + " (Imagen no disponible)");
            }

            // Agregar acción para seleccionar/deseleccionar plantas
            plantButton.addActionListener(e -> {
                if (selectedPlants.contains(plant[0])) {
                    selectedPlants.remove(plant[0]);
                    plantButton.setBackground(new Color(200, 255, 200)); // Desmarcar
                } else {
                    selectedPlants.add(Planta.getPlanta(plant[0]));
                    plantButton.setBackground(new Color(100, 200, 100)); // Marcar
                }
                readyButton.setEnabled(!selectedPlants.isEmpty()); // Habilitar "Listo" si hay plantas seleccionadas
            });

            plantsPanel.add(plantButton);
        }

        JButton backButton = new JButton("Volver");
        backButton.setBackground(new Color(100, 200, 100));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createRaisedBevelBorder());
        backButton.addActionListener(e -> createSelectModeScreen());

        // Panel inferior con botones de control
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(new Color(100, 200, 100));
        controlPanel.add(backButton);
        controlPanel.add(readyButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(plantsPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void createSelectZombiesScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(200, 100, 100)); // Fondo rojo claro para los zombies

        JLabel title = new JLabel("Selecciona tus Zombies", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        // Panel para botones de zombies
        JPanel zombiesPanel = new JPanel(new GridLayout(1, 4, 10, 10)); // 1 fila, 3 columnas
        zombiesPanel.setOpaque(true);
        zombiesPanel.setBackground(new Color(170, 80, 80));

        // Definir los zombies disponibles con sus nombres y rutas de imágenes
        String[][] zombies = {
                {"basicZombie", "/resources/basicZombie.png"},
                {"coneheadZombie", "/resources/coneheadZombie.png"},
                {"bucketheadZombie", "/resources/bucketheadZombie.png"},
                {"brainstain", "/resources/brainstain.png"}
        };

        // Lista para almacenar los zombies seleccionados
        java.util.List<Zombie> selectedZombies = new java.util.ArrayList<>();

        // Botón "Listo", declarado como final para ser usado dentro del ActionListener
        JButton readyButton = new JButton("Listo");
        readyButton.setEnabled(false); // Deshabilitado inicialmente
        readyButton.setBackground(new Color(150, 50, 50));
        readyButton.setForeground(Color.WHITE);
        readyButton.setFocusPainted(false);
        readyButton.setBorder(BorderFactory.createRaisedBevelBorder());
        this.gameboard = new GameBoard(frame);
        readyButton.addActionListener(e -> {
            if (selectedZombies.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Selecciona al menos un zombie antes de continuar.");
            } else {
                this.selectedZombies = new java.util.ArrayList<Zombie>(selectedZombies); // Guardar la selección
                this.gameboard.showGameBoard(
                        (ArrayList<Planta>) selectedPlants,
                        (ArrayList<Zombie>) selectedZombies
                );
            }
        });

        // Crear botones con imágenes y nombres de zombies
        for (String[] zombie : zombies) {
            JButton zombieButton = new JButton(zombie[0]);
            zombieButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            zombieButton.setHorizontalTextPosition(SwingConstants.CENTER);
            zombieButton.setForeground(Color.BLACK);
            zombieButton.setFont(new Font("Arial", Font.BOLD, 14));
            zombieButton.setFocusPainted(false);
            zombieButton.setBorder(BorderFactory.createRaisedBevelBorder());
            zombieButton.setBackground(new Color(255, 200, 200));

            // Agregar la imagen del zombie
            try {
                ImageIcon zombieIcon = new ImageIcon(getClass().getResource(zombie[1]));
                Image scaledImage = zombieIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                zombieButton.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                zombieButton.setText(zombie[0] + " (Imagen no disponible)");
            }

            // Agregar acción para seleccionar/deseleccionar zombies
            zombieButton.addActionListener(e -> {
                if (selectedZombies.contains(zombie[0])) {
                    selectedZombies.remove(zombie[0]);
                    zombieButton.setBackground(new Color(255, 200, 200)); // Desmarcar
                } else {
                    selectedZombies.add(Zombie.getZombie(zombie[0]));
                    zombieButton.setBackground(new Color(200, 100, 100)); // Marcar
                }
                readyButton.setEnabled(!selectedZombies.isEmpty()); // Habilitar "Listo" si hay zombies seleccionados
            });

            zombiesPanel.add(zombieButton);
        }

        JButton backButton = new JButton("Volver");
        backButton.setBackground(new Color(200, 100, 100));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createRaisedBevelBorder());
        backButton.addActionListener(e -> createSelectPlantsScreen("Player vs Player"));

        // Panel inferior con botones de control
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(new Color(200, 100, 100));
        controlPanel.add(backButton);
        controlPanel.add(readyButton);

        panel.add(title, BorderLayout.NORTH);
        panel.add(zombiesPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }


    private JButton createPlantOrZombieButton(String name, String imagePath) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(60, 60));
        button.setToolTipText(name);
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            button.setText(name);
            button.setForeground(Color.WHITE);
        }
        button.setBackground(new Color(200, 200, 200));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());


        button.addActionListener(e -> System.out.println("Seleccionaste: " + name));

        return button;
    }


    private void createSelectModeScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(50, 150, 50)); // Fondo verde para todo el panel principal

        // Panel para los botones de selección de modo
        JPanel modesPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // 3 filas, 1 columna
        modesPanel.setOpaque(true);
        modesPanel.setBackground(new Color(30, 100, 30));

        // Textos para los modos de juego
        String[] modeTexts = {"Player vs Machine", "Player vs Player", "Machine vs Machine"};

        // Crear botones para los diferentes modos de juego con textos personalizados
        for (String modeText : modeTexts) {
            JButton modeButton = new JButton(modeText);
            modeButton.setBackground(new Color(60, 170, 60));
            modeButton.setForeground(Color.WHITE);
            modeButton.setFocusPainted(false);
            modeButton.setBorder(BorderFactory.createRaisedBevelBorder());
            modeButton.addActionListener(e -> handleModeSelection(modeText)); // Manejar selección del modo
            modesPanel.add(modeButton);
        }

        JLabel title = new JLabel("Selecciona tu Modo de Juego", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        title.setForeground(Color.WHITE);

        JButton backButton = new JButton("Volver");
        backButton.setBackground(new Color(50, 150, 50));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createRaisedBevelBorder());
        backButton.addActionListener(e -> showMainMenu());

        panel.add(title, BorderLayout.NORTH);
        panel.add(modesPanel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        frame.setContentPane(panel);
        frame.revalidate();
        frame.repaint();
    }

    private void handleModeSelection(String mode) {
        if (mode.equals("Player vs Player")) {
            // Solicitar nombres de los jugadores
            JTextField player1Field = new JTextField();
            JTextField player2Field = new JTextField();

            player1Field.setBackground(new Color(200, 255, 200)); // Fondo verde para el campo
            player2Field.setBackground(new Color(200, 255, 200)); // Fondo verde para el campo

            Object[] message = {
                    "Nombre del Jugador 1:", player1Field,
                    "Nombre del Jugador 2:", player2Field
            };

            int option = JOptionPane.showConfirmDialog(
                    frame,
                    message,
                    "Nombres de los Jugadores",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (option == JOptionPane.OK_OPTION) {
                String player1Name = player1Field.getText().trim();
                String player2Name = player2Field.getText().trim();

                if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Bienvenidos " + player1Name + " y " + player2Name + "!");
                    createSelectPlantsScreen(mode);
                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingresa nombres válidos para ambos jugadores.");
                }
            }
        } else if (mode.equals("Player vs Machine")) {
            String playerName = JOptionPane.showInputDialog(
                    frame,
                    "Ingrese el nombre del jugador:",
                    "Nombre del Jugador",
                    JOptionPane.PLAIN_MESSAGE
            );
            if (playerName != null && !playerName.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Bienvenido " + playerName + "!");
                createSelectPlantsScreen(mode);
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingresa un nombre válido.");
            }
        } else {
            createSelectPlantsScreen(mode); // Machine vs Machine no requiere nombres
        }
    }

    public void showLoadingScreen() {
        JPanel loadingPanel = new JPanel();
        loadingPanel.setLayout(new BorderLayout());
        JLabel loadingImage = new JLabel(new ImageIcon(getClass().getResource("/resources/pantallaCarga.jpg")));
        loadingImage.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel loadingText = new JLabel("Cargando", SwingConstants.CENTER);
        loadingText.setFont(new Font("Arial", Font.BOLD, 24));
        loadingText.setForeground(Color.WHITE);
        loadingText.setBorder(new EmptyBorder(10, 0, 10, 0)); // Espaciado
        loadingPanel.setBackground(Color.BLACK);
        loadingPanel.add(loadingImage, BorderLayout.CENTER);
        loadingPanel.add(loadingText, BorderLayout.SOUTH);
        frame.setContentPane(loadingPanel);
        frame.setVisible(true);

        // Animación del texto "Cargando"
        Timer loadingAnimation = new Timer();
        loadingAnimation.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                loadingText.setText(loadingText.getText().length() < 10 ? loadingText.getText() + "." : "Cargando");
            }
        }, 0, 500); // Ejecutar cada medio segundo

        // Cambiar al menú principal después de 3 segundos
        TimerTask mainMenuTask = new TimerTask() {
            @Override
            public void run() {
                loadingAnimation.cancel(); // Detener la animación
                showMainMenu(); // Mostrar el menú principal
            }
        };
        new Timer().schedule(mainMenuTask, 3000); // Iniciar el timer para cambiar al menú principal

    }

    private void showMainMenu() {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        JLabel backgroundImage = new JLabel(new ImageIcon(getClass().getResource("/resources/FondoPoobvsZombie.jpg")));
        backgroundImage.setBounds(0, 0, 800, 600);
        layeredPane.add(backgroundImage, Integer.valueOf(0));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(0, 0, 800, 600);
        JButton startButton = createMenuButton("Iniciar Juego", 300, 250);
        JButton settingsButton = createMenuButton("Configuración", 300, 320);
        JButton creditsButton = createMenuButton("Créditos", 300, 390);
        JButton exitButton = createMenuButton("Salir", 300, 460);
        JButton SelectModeAndModesButton = createMenuButton("Seleccionar Plantas y Modos", 300, 320); // Nuevo botón

        startButton.addActionListener(e -> showPlantSelectionScreen());
        settingsButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Configuración no disponible aun."));
        creditsButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Créditos: Juego desarrollado por Julian Cardenas y Juan Jose Mejia."));
        exitButton.addActionListener(e -> System.exit(0));
        SelectModeAndModesButton.addActionListener(e -> createSelectModeScreen());


        buttonPanel.add(startButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(creditsButton);
        buttonPanel.add(exitButton);
        buttonPanel.add(SelectModeAndModesButton);

        layeredPane.add(buttonPanel, Integer.valueOf(1));
        frame.setContentPane(layeredPane);
        frame.revalidate();
        frame.repaint();
    }


    /**
     * Muestra el tablero de juego una vez que se han seleccionado las plantas y zombies.
     */



    /**
     * Crea la pantalla para seleccionar zombies con imágenes y botones interactivos.
     */

    private JButton createMenuButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 200, 40);
        button.setBackground(new Color(50, 150, 50));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        return button;
    }


    public JFrame getMainFrame() {
        return frame;
    }


    /**
     * Muestra la pantalla de selección de modos de juego.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PoobVsZombiesGUI gui = new PoobVsZombiesGUI();
            gui.showLoadingScreen();
            gui.frame.setVisible(true); // Hacer visible el frame
        });
    }
}
