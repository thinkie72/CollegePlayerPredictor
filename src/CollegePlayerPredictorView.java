import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CollegePlayerPredictorView {
    private static JFrame frame;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    // Input fields
    private static JTextField nameField, heightField, weightField, ppgField, apgField, rpgField;
    private static JRadioButton yesButton, noButton;

    // Output components
    private static JTable resultTable;
    private static JTextArea playerSummary;
    private static JTextArea predictedAverages;

    public static void main(String[] args) {
        frame = new JFrame("Player Predictor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel inputPanel = createInputPanel();
        JPanel resultPanel = createResultPanel();

        mainPanel.add(inputPanel, "InputForm");
        mainPanel.add(resultPanel, "ResultScreen");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 5, 5));

        nameField = new JTextField();
        heightField = new JTextField();
        weightField = new JTextField();
        ppgField = new JTextField();
        apgField = new JTextField();
        rpgField = new JTextField();

        yesButton = new JRadioButton("Yes");
        noButton = new JRadioButton("No");
        ButtonGroup blueBloodGroup = new ButtonGroup();
        blueBloodGroup.add(yesButton);
        blueBloodGroup.add(noButton);

        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Height (inches):")); panel.add(heightField);
        panel.add(new JLabel("Weight (lbs):")); panel.add(weightField);
        panel.add(new JLabel("College PPG:")); panel.add(ppgField);
        panel.add(new JLabel("College APG:")); panel.add(apgField);
        panel.add(new JLabel("College RPG:")); panel.add(rpgField);
        panel.add(new JLabel("Attended Blue Blood School?"));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(yesButton);
        radioPanel.add(noButton);
        panel.add(radioPanel);

        JButton submitButton = new JButton("Enter");
        panel.add(new JLabel());
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    double height = Double.parseDouble(heightField.getText());
                    double weight = Double.parseDouble(weightField.getText());
                    double ppg = Double.parseDouble(ppgField.getText());
                    double apg = Double.parseDouble(apgField.getText());
                    double rpg = Double.parseDouble(rpgField.getText());
                    boolean blueBlood = yesButton.isSelected();

                    // Predict role
                    String description;
                    double impact = ppg + apg + rpg;
                    if (ppg > 15) description = "Scorer";
                    else if (apg > 5) description = "Playmaker";
                    else if (rpg > 7) description = "Rebounder";
                    else if (impact > 20) description = "All-Around Contributor";
                    else description = "Developmental Prospect";

                    // Predict pro averages (replace this with real logic)
                    double proPPG = ppg * 0.6;
                    double proAPG = apg * 0.7;
                    double proRPG = rpg * 0.65;

                    // Simulated similar players
                    String[][] predictionData = {
                            {"J. Smith", "15.1", "6.2", "4.3", "78", "205", "Starter"},
                            {"T. Brown", "8.3", "1.2", "2.9", "76", "198", "Bench Scorer"},
                            {"L. Adams", "11.7", "3.4", "6.1", "80", "215", "Role Player"},
                            {"C. Green", "6.9", "5.0", "1.8", "74", "185", "Facilitator"},
                            {"M. Fox", "13.5", "2.9", "5.0", "77", "202", "Rising Star"}
                    };

                    updateResultsTable(predictionData);
                    updatePlayerSummary(name, ppg, apg, rpg, description);
                    updateProAverages(proPPG, proAPG, proRPG);

                    cardLayout.show(mainPanel, "ResultScreen");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers!", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    private static JPanel createResultPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("Predicted NBA Comparisons", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(title, BorderLayout.NORTH);

        // Table
        String[] columnNames = {"Name", "PPG", "APG", "RPG", "Height", "Weight", "Role"};
        resultTable = new JTable(new String[5][7], columnNames);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Bottom summary
        playerSummary = new JTextArea();
        playerSummary.setFont(new Font("SansSerif", Font.PLAIN, 16));
        playerSummary.setLineWrap(true);
        playerSummary.setWrapStyleWord(true);
        playerSummary.setEditable(false);
        playerSummary.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane summaryScrollPane = new JScrollPane(playerSummary);
        summaryScrollPane.setPreferredSize(new Dimension(700, 120));
        panel.add(summaryScrollPane, BorderLayout.SOUTH);

        // Right panel for predicted stats + restart
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Predicted Pro Averages"));

        predictedAverages = new JTextArea();
        predictedAverages.setFont(new Font("Monospaced", Font.PLAIN, 14));
        predictedAverages.setEditable(false);
        predictedAverages.setPreferredSize(new Dimension(150, 100));
        rightPanel.add(predictedAverages);

        JButton restartButton = new JButton("Restart");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> {
            // Clear input fields
            nameField.setText("");
            heightField.setText("");
            weightField.setText("");
            ppgField.setText("");
            apgField.setText("");
            rpgField.setText("");
            yesButton.setSelected(false);
            noButton.setSelected(false);

            // Switch back to input screen
            cardLayout.show(mainPanel, "InputForm");
        });
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(restartButton);

        panel.add(rightPanel, BorderLayout.EAST);
        return panel;
    }

    private static void updateResultsTable(String[][] data) {
        String[] columnNames = {"Name", "PPG", "APG", "RPG", "Height", "Weight", "Role"};
        resultTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    private static void updatePlayerSummary(String name, double ppg, double apg, double rpg, String description) {
        String summary = String.format(
                "%s's College Averages:\n" +
                        "Points Per Game: %.1f\n" +
                        "Assists Per Game: %.1f\n" +
                        "Rebounds Per Game: %.1f\n\n" +
                        "Predicted Role: %s",
                name, ppg, apg, rpg, description
        );
        playerSummary.setText(summary);
    }

    private static void updateProAverages(double ppg, double apg, double rpg) {
        String summary = String.format(
                "PPG: %.1f\nAPG: %.1f\nRPG: %.1f",
                ppg, apg, rpg
        );
        predictedAverages.setText(summary);
    }
}