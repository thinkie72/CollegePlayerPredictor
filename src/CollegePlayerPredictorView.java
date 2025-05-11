import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CollegePlayerPredictorView {
    private CollegePlayerPredictor predictor;

    public CollegePlayerPredictorView() {
        predictor = new CollegePlayerPredictor();

        JFrame frame = new JFrame("NBA Player Predictor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 700);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextField nameField = new JTextField(10);
        JTextField heightField = new JTextField(10);
        JTextField weightField = new JTextField(10);
        JTextField ppgField = new JTextField(10);
        JTextField rpgField = new JTextField(10);
        JTextField apgField = new JTextField(10);
        JCheckBox blueBloodCheck = new JCheckBox("Blue Blood?");

        JTextArea outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        JButton submitButton = new JButton("Predict");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int height = Integer.parseInt(heightField.getText());
                    int weight = Integer.parseInt(weightField.getText());
                    double ppg = Double.parseDouble(ppgField.getText());
                    double rpg = Double.parseDouble(rpgField.getText());
                    double apg = Double.parseDouble(apgField.getText());
                    boolean blueBlood = blueBloodCheck.isSelected();

                    CollegePlayerPredictor.Result result = predictor.compare(name, height, weight, ppg, rpg, apg, blueBlood);

                    StringBuilder sb = new StringBuilder();
                    sb.append("Similar Players:\n");
                    for (Player p : result.similarPlayers) {
                        sb.append(p.getName())
                                .append(": ")
                                .append(p.getHt()).append(" in, ")
                                .append(p.getWt()).append(" lbs | NCAA: ")
                                .append(String.format("%.1f PPG, %.1f RPG, %.1f APG", p.getcPPG(), p.getcRPG(), p.getcAPG()))
                                .append(" | NBA: ")
                                .append(String.format("%.1f PPG, %.1f RPG, %.1f APG", p.getpPPG(), p.getpRPG(), p.getpAPG()))
                                .append("\n");
                    }
                    sb.append("\nPredicted Pro Stats for ").append(name).append(":\n");
                    sb.append(String.format("%.1f PPG, %.1f RPG, %.1f APG\n", result.avgPPG, result.avgRPG, result.avgAPG));
                    sb.append("Role: ").append(result.role);

                    outputArea.setText(sb.toString());
                } catch (NumberFormatException ex) {
                    outputArea.setText("Invalid input. Please check your values.");
                }
            }
        });

        panel.add(new JLabel("Name:")); panel.add(nameField);
        panel.add(new JLabel("Height (inches):")); panel.add(heightField);
        panel.add(new JLabel("Weight (lbs):")); panel.add(weightField);
        panel.add(new JLabel("College PPG:")); panel.add(ppgField);
        panel.add(new JLabel("College RPG:")); panel.add(rpgField);
        panel.add(new JLabel("College APG:")); panel.add(apgField);
        panel.add(blueBloodCheck);
        panel.add(submitButton);
        panel.add(scrollPane);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CollegePlayerPredictorView();
    }
}
