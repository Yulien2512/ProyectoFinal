package presentation;

import domain.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class GameBoard extends JPanel {
    private static final int ROWS = 5;
    private static final int COLUMNS = 10;
    private static final int CELL_SIZE = 60;// Tamaño de cada celda
    private PoobVsZombies juego = new PoobVsZombies();
    private JLabel[][] cells;
    private JFrame frame;
    private int soles;
    private int cerebros;
    private JLabel sunLabel;
    private JLabel brainLabel;


    public GameBoard(JFrame frame) {
        this.frame = frame;
        setLayout(new GridLayout(ROWS, COLUMNS));
        setPreferredSize(new Dimension(COLUMNS * CELL_SIZE, ROWS * CELL_SIZE));
        cells = new JLabel[ROWS][COLUMNS];
        this.soles = 50; // Inicializar con un valor base
        this.cerebros = 10; // Inicializar con un valor base
        initializeBoard();
        juego.iniciarGeneracionSoles(5000);
        juego.iniciarGeneracionCerebros(10000);
    }

    private void initializeBoard() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                cells[row][col] = new JLabel();
                cells[row][col].setOpaque(true);
                cells[row][col].setBackground(Color.GREEN);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                if (col == 0) {
                    // Primera columna para podadores
                    cells[row][col].setBackground(Color.GRAY);
                } else if (col == COLUMNS - 1) {
                    // Última columna para zombies
                    cells[row][col].setBackground(Color.RED);
                }

                add(cells[row][col]);
            }
        }
    }

    private String selectedPlant = null;
    private String selectedZombie = null;

    public void showGameBoard(ArrayList<Planta> selectedPlants, ArrayList<Zombie> selectedZombies) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1000, 800)); // Dimensiones ajustadas para el tablero

        // Panel superior para selección de plantas
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(139, 69, 19));
        topPanel.setBounds(0, 0, 800, 90);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));


        // Botones de plantas
        for (Planta plant : selectedPlants) {
            JButton plantButton = new JButton();
            try {
                String plantName = plant.getNombre();
                ImageIcon plantIcon = new ImageIcon(getClass().getResource("/resources/" + plantName + ".png"));
                Image scaledImage = plantIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                plantButton.setIcon(new ImageIcon(scaledImage));
                plantButton.setActionCommand(plantName);
            } catch (Exception e) {
                plantButton.setText(plant.getNombre()); // Si no se encuentra la imagen
            }

            plantButton.setBackground(new Color(139, 69, 19));
            plantButton.setFocusPainted(false);

            JLabel priceLabel = new JLabel("Precio: " + plant.getCosto());
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            priceLabel.setForeground(Color.BLUE);

            JPanel plantPanel = new JPanel(new BorderLayout());
            plantPanel.add(plantButton, BorderLayout.CENTER);
            plantPanel.add(priceLabel, BorderLayout.SOUTH);

            plantButton.addActionListener(e -> selectedPlant = e.getActionCommand());
            topPanel.add(plantPanel);
        }

        // Etiqueta de soles
        sunLabel = new JLabel("Soles: " + (juego != null ? juego.getSoles() : 0));
        sunLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sunLabel.setForeground(Color.YELLOW);
        topPanel.add(sunLabel);

        JPanel shovelPanel = new JPanel(new BorderLayout());
        JButton shovelButton = new JButton();
        shovelPanel.add(shovelButton, BorderLayout.EAST);

        ImageIcon shovelIcon = new ImageIcon(getClass().getResource("/resources/shovel.png"));
        Image scaledShovelImage = shovelIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        shovelButton.setIcon(new ImageIcon(scaledShovelImage));
        shovelButton.setBackground(new Color(139, 69, 19));
        shovelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        shovelButton.setFocusPainted(false);
        topPanel.add(shovelPanel);

        final boolean[] shovelActive = {false};
        shovelButton.addActionListener(e -> {
            shovelActive[0] = !shovelActive[0];
        });

        layeredPane.add(topPanel, Integer.valueOf(2));

// Panel inferior para selección de zombies
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(139, 69, 19));
        bottomPanel.setBounds(0, 530, 800, 85);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));

// Crear etiqueta de cerebros
        brainLabel = new JLabel("Cerebros: " + (juego != null ? juego.getCerebros() : 0));
        brainLabel.setFont(new Font("Arial", Font.BOLD, 18));
        brainLabel.setForeground(Color.PINK);
        bottomPanel.add(brainLabel);

// Configurar actualización dinámica de recursos
        this.juego = juego;
        Timer uiUpdater = new Timer();
        uiUpdater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    sunLabel.setText("Soles: " + juego.getSoles());
                    brainLabel.setText("Cerebros: " + juego.getCerebros());
                });
            }
        }, 0, 1000);

// Botones de zombies
        for (Zombie zombie : selectedZombies) {
            JButton zombieButton = new JButton();
            try {
                // Obtenemos el nombre del zombie directamente desde la instancia
                String zombieName = zombie.getNombre();

                // Cargamos la imagen del zombie
                ImageIcon zombieIcon = new ImageIcon(getClass().getResource("/resources/" + zombieName + ".png"));
                Image scaledImage = zombieIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                zombieButton.setIcon(new ImageIcon(scaledImage));
                zombieButton.setActionCommand(zombieName);

            } catch (Exception e) {
                // Si no se encuentra la imagen, usamos el nombre como texto
                zombieButton.setText(zombie.getNombre());
            }

            // Configuración adicional del botón
            zombieButton.setBackground(new Color(139, 69, 19));
            zombieButton.setFocusPainted(false);

            // Crear panel para mostrar precio del zombie
            JLabel priceLabelZombie = new JLabel("Precio: " + zombie.getCosto());
            priceLabelZombie.setFont(new Font("Arial", Font.PLAIN, 12));
            priceLabelZombie.setForeground(Color.RED);
            JPanel zombiePanel = new JPanel(new BorderLayout());
            zombiePanel.add(zombieButton, BorderLayout.CENTER);
            zombiePanel.add(priceLabelZombie, BorderLayout.SOUTH);

            // Asignamos un ActionListener que actualiza el zombie seleccionado
            zombieButton.addActionListener(e -> selectedZombie = e.getActionCommand());

            // Agregamos el panel de zombie al panel inferior
            bottomPanel.add(zombiePanel);
        }

        layeredPane.add(bottomPanel, Integer.valueOf(2));

// Imagen de fondo del tablero
        JLabel overlayImage = new JLabel(new ImageIcon(getClass().getResource("/resources/tableroPVZ.png")));
        overlayImage.setBounds(0, 80, 800, 450); // Ajustamos las dimensiones del fondo
        layeredPane.add(overlayImage, Integer.valueOf(4));

// Grilla del tablero
        JPanel buttonGrid = new JPanel(new GridLayout(5, 9));
        buttonGrid.setBounds(200, 120, 550, 380); // Ajustamos la altura para que se vea todo el tablero
        buttonGrid.setOpaque(false);



        // Crear botones de la grilla
        // Crear un mapa para asociar cada botón con una planta o un zombie
        Map<JButton, Object> buttonStateMap = new HashMap<>();

// Crear botones de la grilla
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton();
                button.setContentAreaFilled(false);
                button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 60)));

                int finalI = i;
                int finalJ = j;
                button.addActionListener(e -> {
                    if (shovelActive[0]) {
                        if (buttonStateMap.containsKey(button)) {
                            button.removeAll();
                            button.setIcon(null);
                            shovelActive[0] = false; // Desactivar la pala automáticamente;
                        }
                    } else  {
                         if(selectedPlant != null || selectedZombie != null) {
                            String selectedItem = selectedPlant != null ? selectedPlant : selectedZombie;

                            // Lógica para verificar y gastar recursos
                            boolean canPlace = false;
                            if (selectedPlant != null) {
                                // Verificar si hay suficientes soles
                                if (juego.getSoles() >= 50) {  // Costo de ejemplo
                                    juego.colocarPlanta(50);
                                    canPlace = true;
                                }
                            } else if (selectedZombie != null) {
                                // Verificar si hay suficientes cerebros
                                if (juego.getCerebros() >= 25) {  // Costo de ejemplo
                                    juego.colocarZombie(25);
                                    canPlace = true;
                                }
                            }

                            if (canPlace) {
                                try {
                                    // Intentar colocar el ícono PNG primero
                                    ImageIcon itemIcon = new ImageIcon(getClass().getResource("/resources/" + selectedItem + ".png"));
                                    addItemToBoard(selectedItem, finalI, finalJ);
                                    Image scaledImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                                    button.setIcon(new ImageIcon(scaledImage));
                                    sunLabel.setText("Soles: " + juego.getSoles());
                                    brainLabel.setText("Cerebros: " + juego.getCerebros());

                                    // Intentar colocar el GIF encima del botón
                                    String gifPath = "/resources/" + selectedItem + ".gif";
                                    try {
                                        ImageIcon gifIcon = new ImageIcon(getClass().getResource(gifPath));
                                        JLabel gifLabel = new JLabel(gifIcon);

                                        gifLabel.setSize(40, 40); // Tamaño del GIF
                                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                                        button.setLayout(new BorderLayout());
                                        button.add(gifLabel, BorderLayout.CENTER); // Agregar GIF al botón
                                        button.setIcon(null); // Quitar el ícono PNG
                                    } catch (Exception gifException) {
                                        // Si no se encuentra el GIF, mantener el PNG
                                        button.setIcon(new ImageIcon(scaledImage));
                                    }

                                    // Actualizar el estado del botón
                                    if (selectedPlant != null) {
                                        buttonStateMap.put(button, selectedPlant);
                                    } else if (selectedZombie != null) {
                                        buttonStateMap.put(button, selectedZombie);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                // Mostrar mensaje de recursos insuficientes
                                JOptionPane.showMessageDialog(frame,
                                        "No tienes suficientes recursos para colocar este elemento",
                                        "Recursos Insuficientes",
                                        JOptionPane.WARNING_MESSAGE);
                            }

                        // Deseleccionar elemento
                        selectedPlant = null;
                        selectedZombie = null;
                    }}
                });

                buttonGrid.add(button);
            }
        }


        layeredPane.add(buttonGrid, Integer.valueOf(5));
        frame.setContentPane(layeredPane);
        frame.revalidate();
        frame.repaint();
    }


    public java.util.List<Planta> selectedPlants = new java.util.ArrayList<>();
    public java.util.List<Zombie> selectedZombies = new java.util.ArrayList<>();

    private void addItemToBoard(String selectedItem, int x , int y){
        if (selectedItem.equalsIgnoreCase("basicZombie")) {
            ZombieBasico zombie = new ZombieBasico(x,y);
            selectedZombies.add(zombie);

        } else if (selectedItem.equalsIgnoreCase("bucketheadZombie")) {
            ZombieBuckethead zombie = new ZombieBuckethead(x,y);
            selectedZombies.add(zombie);

        } else if (selectedItem.equalsIgnoreCase("coneheadZombie")) {
            ZombieConehead zombie = new ZombieConehead(x,y);
            selectedZombies.add(zombie);

        } else if (selectedItem.equalsIgnoreCase("brainstain")) {
            brainstain brainstain = new brainstain(x,y);
            brainstain.iniciarProduccion();
            selectedZombies.add(brainstain);

        } else  if (selectedItem.equalsIgnoreCase("peashooter")) {
            peashooter planta = new peashooter(x,y);
            selectedPlants.add(planta);

        } else if (selectedItem.equalsIgnoreCase("sunflower")) {
            sunflower sunflower = new sunflower(x,y);
            sunflower.iniciarProduccion();
            selectedPlants.add(sunflower);

        } else if (selectedItem.equalsIgnoreCase("wallnut")) {
            wallnut wallnut = new wallnut(x,y);
            selectedPlants.add(wallnut);
        } else if (selectedItem.equalsIgnoreCase("eciplanta")) {
            eciplanta eciplanta = new eciplanta(x,y);
            eciplanta.iniciarProduccion();
            selectedPlants.add(eciplanta);

        } else if (selectedItem.equalsIgnoreCase("potatoMine")) {
            potatoMine potato = new potatoMine(x,y);
            selectedPlants.add(potato);

        }
    }

    public ArrayList<Planta> getPlantas() {
        return (ArrayList<Planta>) selectedPlants;
    }

    public ArrayList<Zombie> getZombies() {
        return (ArrayList<Zombie>) selectedZombies;
    }

    public void showGameBoard(ArrayList<Planta> selectedPlants) {
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1000, 800)); // Dimensiones ajustadas para el tablero

        // Panel superior para selección de plantas
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(139, 69, 19));
        topPanel.setBounds(0, 0, 1000, 90);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));


        // Botones de plantas
        for (Planta plant : selectedPlants) {
            JButton plantButton = new JButton();
            try {
                String plantName = plant.getNombre();
                ImageIcon plantIcon = new ImageIcon(getClass().getResource("/resources/" + plantName + ".png"));
                Image scaledImage = plantIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                plantButton.setIcon(new ImageIcon(scaledImage));
                plantButton.setActionCommand(plantName);
            } catch (Exception e) {
                plantButton.setText(plant.getNombre()); // Si no se encuentra la imagen
            }

            plantButton.setBackground(new Color(139, 69, 19));
            plantButton.setFocusPainted(false);

            JLabel priceLabel = new JLabel("Precio: " + plant.getCosto());
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            priceLabel.setForeground(Color.BLUE);

            JPanel plantPanel = new JPanel(new BorderLayout());
            plantPanel.add(plantButton, BorderLayout.CENTER);
            plantPanel.add(priceLabel, BorderLayout.SOUTH);

            plantButton.addActionListener(e -> selectedPlant = e.getActionCommand());
            topPanel.add(plantPanel);
        }

        // Etiqueta de soles
        sunLabel = new JLabel("Soles: " + (juego != null ? juego.getSoles() : 0));
        sunLabel.setFont(new Font("Arial", Font.BOLD, 18));
        sunLabel.setForeground(Color.YELLOW);
        topPanel.add(sunLabel);

        JPanel shovelPanel = new JPanel(new BorderLayout());
        JButton shovelButton = new JButton();
        shovelPanel.add(shovelButton, BorderLayout.EAST);

        ImageIcon shovelIcon = new ImageIcon(getClass().getResource("/resources/shovel.png"));
        Image scaledShovelImage = shovelIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        shovelButton.setIcon(new ImageIcon(scaledShovelImage));
        shovelButton.setBackground(new Color(139, 69, 19));
        shovelButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        shovelButton.setFocusPainted(false);
        topPanel.add(shovelPanel);

        final boolean[] shovelActive = {false};
        shovelButton.addActionListener(e -> {
            shovelActive[0] = !shovelActive[0];
        });

        layeredPane.add(topPanel, Integer.valueOf(2));

// Configurar actualización dinámica de recursos
        this.juego = juego;
        Timer uiUpdater = new Timer();
        uiUpdater.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    sunLabel.setText("Soles: " + juego.getSoles());
                });
            }
        }, 0, 1000);

// Imagen de fondo del tableo
        JLabel overlayImage = new JLabel(new ImageIcon(getClass().getResource("/resources/tableroPVZ.png")));
        overlayImage.setBounds(0, 80, 800, 450); // Ajustamos las dimensiones del fondo
        layeredPane.add(overlayImage, Integer.valueOf(4));

// Grilla del tablero
        JPanel buttonGrid = new JPanel(new GridLayout(5, 9));
        buttonGrid.setBounds(200, 120, 550, 380); // Ajustamos la altura para que se vea todo el tablero
        buttonGrid.setOpaque(false);


        Map<JButton, Object> buttonStateMap = new HashMap<>();

// Crear botones de la grilla
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton();
                button.setContentAreaFilled(false);
                button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 60)));

                int finalI = i;
                int finalJ = j;
                button.addActionListener(e -> {
                    if (shovelActive[0]) {
                        if (buttonStateMap.containsKey(button)) {
                            button.removeAll();
                            button.setIcon(null);
                            shovelActive[0] = false; // Desactivar la pala automáticamente;
                        }
                    } else  {
                        if(selectedPlant != null || selectedZombie != null) {
                            String selectedItem = selectedPlant != null ? selectedPlant : selectedZombie;

                            // Lógica para verificar y gastar recursos
                            boolean canPlace = false;
                            if (selectedPlant != null) {
                                // Verificar si hay suficientes soles
                                if (juego.getSoles() >= 50) {  // Costo de ejemplo
                                    juego.colocarPlanta(50);
                                    canPlace = true;
                                }
                            } else if (selectedZombie != null) {
                                // Verificar si hay suficientes cerebros
                                if (juego.getCerebros() >= 25) {  // Costo de ejemplo
                                    juego.colocarZombie(25);
                                    canPlace = true;
                                }
                            }

                            if (canPlace) {
                                try {
                                    // Intentar colocar el ícono PNG primero
                                    ImageIcon itemIcon = new ImageIcon(getClass().getResource("/resources/" + selectedItem + ".png"));
                                    addItemToBoard(selectedItem, finalI, finalJ);
                                    Image scaledImage = itemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                                    button.setIcon(new ImageIcon(scaledImage));
                                    sunLabel.setText("Soles: " + juego.getSoles());
                                    String gifPath = "/resources/" + selectedItem + ".gif";
                                    try {
                                        ImageIcon gifIcon = new ImageIcon(getClass().getResource(gifPath));
                                        JLabel gifLabel = new JLabel(gifIcon);

                                        gifLabel.setSize(40, 40); // Tamaño del GIF
                                        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
                                        gifLabel.setVerticalAlignment(SwingConstants.CENTER);

                                        button.setLayout(new BorderLayout());
                                        button.add(gifLabel, BorderLayout.CENTER); // Agregar GIF al botón
                                        button.setIcon(null); // Quitar el ícono PNG
                                    } catch (Exception gifException) {
                                        // Si no se encuentra el GIF, mantener el PNG
                                        button.setIcon(new ImageIcon(scaledImage));
                                    }

                                    // Actualizar el estado del botón
                                    if (selectedPlant != null) {
                                        buttonStateMap.put(button, selectedPlant);
                                    } else if (selectedZombie != null) {
                                        buttonStateMap.put(button, selectedZombie);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else {
                                // Mostrar mensaje de recursos insuficientes
                                JOptionPane.showMessageDialog(frame,
                                        "No tienes suficientes recursos para colocar este elemento",
                                        "Recursos Insuficientes",
                                        JOptionPane.WARNING_MESSAGE);
                            }

                            // Deseleccionar elemento
                            selectedPlant = null;
                        }}
                });

                buttonGrid.add(button);
            }
        }


        layeredPane.add(buttonGrid, Integer.valueOf(5));
        frame.setContentPane(layeredPane);
        frame.revalidate();
        frame.repaint();
    }
}

