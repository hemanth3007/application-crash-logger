
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrashLogUI {

    private CrashLogService service;

    public CrashLogUI() {
        service = new CrashLogService();

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Not working");
        }

        JFrame frame = new JFrame("Application Crash Logger");
        frame.setSize(1200, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(20, 20));

        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 247, 250));

        //INPUT PANEL
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Crash Details"));
        inputPanel.setBackground(new Color(240, 240, 240));

        JLabel idLabel = new JLabel("Crash ID: ");
        idLabel.setFont(idLabel.getFont().deriveFont(18f));
        JLabel messageLabel = new JLabel("Crash Message: ");
        messageLabel.setFont(messageLabel.getFont().deriveFont(18f));

        JTextField idField = createStyledTextField();
        JTextField messageField = createStyledTextField();

        inputPanel.add(idLabel);
        inputPanel.add(idField);

        inputPanel.add(messageLabel);
        inputPanel.add(messageField);

        //BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(245, 247, 250));

        JButton addButton = new RoundedButton("Add");
        JButton displayButton = new RoundedButton("Display");
        JButton filterButton = new RoundedButton("Filter");
        JButton sortButton = new RoundedButton("Sort");

        Dimension btnSize = new Dimension(120, 40);
        addButton.setPreferredSize(btnSize);
        displayButton.setPreferredSize(btnSize);
        filterButton.setPreferredSize(btnSize);
        sortButton.setPreferredSize(btnSize);

        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(filterButton);
        buttonPanel.add(sortButton);

        //OUTPUT PANEL
        JTextArea outputArea = new JTextArea(10, 2);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 18));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Output"),
                new EmptyBorder(5, 5, 5, 5)
        ));

        //ADD TO MAIN PANEL
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        frame.add(mainPanel);

        //ACTIONS
        addButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String msg = messageField.getText().trim();
            if (id.isEmpty() || msg.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!msg.contains(":")) {
                JOptionPane.showMessageDialog(frame,
                        "Use format: TYPE: message",
                        "Format Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            boolean success = service.addCrashLog(id, msg);
            if (success) {
                JOptionPane.showMessageDialog(frame, "Log added!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Error adding log!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            idField.setText("");
            messageField.setText("");       //Just to clear the fields in UI after adding a log.
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (service.getLogs().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No logs to Display!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                outputArea.setText(service.getAllLogs());
            }
        });

        filterButton.addActionListener(e -> {
            if (service.getLogs().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Logs!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            if (service.getUnexpectedCrashes().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Unexpected Logs to Filter!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            outputArea.setText(service.getUnexpectedCrashes());
        });

        sortButton.addActionListener(e -> {
            if (service.getLogs().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No Logs to Sort!", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            service.sortLogsById();
            outputArea.setText(service.getAllLogs());
        });

        frame.setVisible(true);
    }

    //Styled TextField
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("JetBrains Mono", Font.PLAIN, 20));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                new EmptyBorder(5, 5, 5, 5)
        ));
        return field;
    }
}
