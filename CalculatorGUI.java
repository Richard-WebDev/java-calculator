import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorGUI extends JFrame {

    private final Addition addition = new Addition();
    private final Subtraction subtraction = new Subtraction();
    private final Division division = new Division();
    private final Multiplication multiplication = new Multiplication();

    private final JTextField display;
    private double firstValue = 0;
    private String selectedOperator = "";
    private boolean startNewNumber = true;

    // Palette Colori Vintage Casio Personal-Mini
    private final Color COLOR_BODY_LEFT = new Color(30, 33, 36);    // Plastica nera/antracite sinistra
    private final Color COLOR_BODY_RIGHT = new Color(235, 227, 213); // Plastica beige/panna destra
    private final Color COLOR_SCREEN_BG = new Color(12, 22, 24);    // Fondo scuro del display a vuoto
    private final Color COLOR_CYAN_DIGIT = new Color(51, 235, 255); // Ciano luminescente dei vecchi tubi VFD
    private final Color COLOR_KEY_RED = new Color(224, 42, 47);     // Rosso acceso per il tasto AC
    private final Color COLOR_KEY_DARK = new Color(64, 64, 64);     // Grigio antracite per i tasti numerici
    private final Color COLOR_KEY_LIGHT = new Color(140, 142, 145);  // Grigio chiaro per i tasti funzione/operatori

    public CalculatorGUI() {
        setTitle("CASIO personal-mini");
        setSize(720, 380); // Sviluppo layout orizzontale 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // La struttura principale si divide in due grandi blocchi: Sinistro (Scuro) e Destro (Beige)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        add(mainPanel);

        // =====================================================================
        // BLOCCO SINISTRO: Pannello Antracite con Display e Logo Casio
        // =====================================================================
        JPanel leftPanel = new JPanel(null); // Layout assoluto per posizionare gli elementi al millimetro
        leftPanel.setBackground(COLOR_BODY_LEFT);
        leftPanel.setBorder(BorderFactory.createMatteBorder(15, 15, 15, 5, COLOR_BODY_LEFT));

        // Interruttore finto d'accensione verde in alto a sinistra
        JPanel powerSwitch = new JPanel();
        powerSwitch.setBounds(30, 20, 50, 25);
        powerSwitch.setBackground(Color.BLACK);
        powerSwitch.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JPanel greenDot = new JPanel();
        greenDot.setBackground(new Color(40, 200, 40));
        greenDot.setPreferredSize(new Dimension(15, 15));
        powerSwitch.add(greenDot);
        leftPanel.add(powerSwitch);

        // Cornice del display scavata nella scocca
        JPanel displayContainer = new JPanel(new BorderLayout());
        displayContainer.setBounds(30, 75, 290, 110);
        displayContainer.setBackground(COLOR_SCREEN_BG);
        displayContainer.setBorder(BorderFactory.createLoweredBevelBorder());

        // Display Digitale Ciano (Allineato a destra)
        display = new JTextField("0");
        display.setFont(new Font("Courier New", Font.BOLD, 55)); // Font monospazio stile retro
        display.setForeground(COLOR_CYAN_DIGIT);
        display.setBackground(COLOR_SCREEN_BG);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        displayContainer.add(display, BorderLayout.CENTER);
        leftPanel.add(displayContainer);

        // Logo Storico "CASIO personal-mini" in basso
        JLabel logoLabel = new JLabel("CASIO personal-mini");
        logoLabel.setFont(new Font("SansSerif", Font.ITALIC | Font.BOLD, 20));
        logoLabel.setForeground(Color.LIGHT_GRAY);
        logoLabel.setBounds(40, 260, 260, 30);
        leftPanel.add(logoLabel);

        mainPanel.add(leftPanel);

        // =====================================================================
        // BLOCCO DESTRO: Pannello Beige con la Tastiera Vintage
        // =====================================================================
        JPanel rightPanel = new JPanel(new GridLayout(4, 5, 10, 10)); // Griglia 4 righe x 5 colonne
        rightPanel.setBackground(COLOR_BODY_RIGHT);
        // Margini generosi per centrare la tastiera nella scocca chiara
        rightPanel.setBorder(BorderFactory.createEmptyBorder(35, 20, 35, 20));

        
        String[] keys = {
            "AC", "7", "8", "9", "÷",
            " ",  "4", "5", "6", "×",
            "C",  "1", "2", "3", "-",
            "▶",  "0", ".", "=", "+"
        };

        for (String text : keys) {
            // Gestione dello spazio vuoto sotto il tasto AC
            if (text.equals(" ")) {
                JPanel spacer = new JPanel();
                spacer.setBackground(COLOR_BODY_RIGHT);
                rightPanel.add(spacer);
                continue;
            }

            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 22));
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createRaisedBevelBorder()); // Effetto tasto fisico sporgente

            // Colorazione mirata basata sul contrasto cromatico della foto originale
            if (text.equals("AC")) {
                btn.setBackground(COLOR_KEY_RED);
                btn.setForeground(Color.WHITE);
            } else if ("0123456789.".contains(text)) {
                btn.setBackground(COLOR_KEY_DARK);
                btn.setForeground(Color.WHITE);
            } else {
                // Tasti C, freccia, uguali e operatori matematici
                btn.setBackground(COLOR_KEY_LIGHT);
                btn.setForeground(Color.BLACK);
            }

            btn.addActionListener(new ButtonClickListener());
            rightPanel.add(btn);
        }

        mainPanel.add(rightPanel);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if ("0123456789.".contains(command)) {
                if (startNewNumber || display.getText().equals("0")) {
                    display.setText(command);
                    startNewNumber = false;
                } else {
                    // Evita l'inserimento di doppie virgole
                    if (command.equals(".") && display.getText().contains(".")) return;
                    display.setText(display.getText() + command);
                }
            } 
            else if (command.equals("AC")) {
                display.setText("0");
                firstValue = 0;
                selectedOperator = "";
                startNewNumber = true;
            } 
            else if (command.equals("C") || command.equals("▶")) {
                // Il tasto C o la freccia cancellano l'immissione corrente resettando a zero
                display.setText("0");
                startNewNumber = true;
            } 
            else if (command.equals("=")) {
                if (!selectedOperator.isEmpty()) {
                    try {
                        double secondValue = Double.parseDouble(display.getText());
                        double result = 0;

                        // Chiamata polimorfica esplicita basata sugli oggetti del tuo pacchetto
                        if (selectedOperator.equals("+")) {
                            result = addition.mathOperation(firstValue, secondValue);
                        } else if (selectedOperator.equals("-")) {
                            result = subtraction.mathOperation(firstValue, secondValue);
                        } else if (selectedOperator.equals("÷")) {
                            result = division.mathOperation(firstValue, secondValue);
                        } else if (selectedOperator.equals("×")) {
                            result = multiplication.mathOperation(firstValue, secondValue);
                        }

                        // Formattazione stile vecchi display (tronca i decimali superflui se intero)
                        if (result % 1 == 0) {
                            display.setText(String.valueOf((int) result));
                        } else {
                            display.setText(String.valueOf(result));
                        }
                    } catch (ArithmeticException ex) {
                        display.setText(ex.getMessage());
                    } catch (Exception ex) {
                        display.setText("Error");
                    }
                    selectedOperator = "";
                    startNewNumber = true;
                }
            } 
            else { // Pressione di un operatore (+, -, ×, ÷)
                firstValue = Double.parseDouble(display.getText());
                selectedOperator = command;
                startNewNumber = true;
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("\033c");

        SwingUtilities.invokeLater(() -> {
            new CalculatorGUI().setVisible(true);
        });
    }
}