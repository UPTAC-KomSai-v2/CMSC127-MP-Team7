package Employee.Show_Accounts;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReadMain extends JPanel implements ActionListener {
    private static final String SHOW_ALL = "Show All Accounts";
    private static final String SHOW_CREDIT = "Show Credit";
    private static final String SHOW_DEBIT = "Show Debit";

    private JPanel upperPanel, lowerPanel, holderPanel;
    private GridBagConstraints gbc;
    private Dimension size;
    private Image bg;
    private JButton showAllAccBtn, showUserCredAccBtn, showDebitAccBtn, exitBtn;
    private CardLayout cardLayout = new CardLayout();
    private ShowAllUsers readUser = new ShowAllUsers();
    private ShowCredit creditUser = new ShowCredit();
    private ShowDebit debitUser = new ShowDebit();
    private Empty empty = new Empty();
    @SuppressWarnings("unused")
    private Connection connection;

    public ReadMain() {
        bg = new ImageIcon(getClass().getResource("/Files/bg2.png")).getImage();
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2));
        setLayout(new GridBagLayout());

        createPanels();
        createButtons();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;

        add(upperPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        add(lowerPanel, gbc);

        layoutButtons();
    }

    private void createPanels() {
        holderPanel = new JPanel();
        holderPanel.setOpaque(false);

        lowerPanel = new JPanel(cardLayout);
        lowerPanel.setBackground(new Color(255, 255, 255, 230));
        lowerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        lowerPanel.setOpaque(false);

        upperPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) {
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        upperPanel.setLayout(new GridBagLayout());
        upperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    private void createButtons() {
        size = new Dimension(150, 40);

        showAllAccBtn = createButton(SHOW_ALL);
        showUserCredAccBtn = createButton(SHOW_CREDIT);
        showDebitAccBtn = createButton(SHOW_DEBIT);
        exitBtn = createButton("Back");
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
        button.setForeground(Color.black);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(22, 180, 161), 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addActionListener(this);
        return button;
    }

    private void layoutButtons() {
        holderPanel.setLayout(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridy = 0;
        buttonGbc.insets = new Insets(0, 10, 0, 10);

        addButtonToPanel(holderPanel, buttonGbc, 0, showAllAccBtn);
        addButtonToPanel(holderPanel, buttonGbc, 1, showUserCredAccBtn);
        addButtonToPanel(holderPanel, buttonGbc, 2, showDebitAccBtn);
        addButtonToPanel(holderPanel, buttonGbc, 3, exitBtn);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        upperPanel.add(holderPanel, gbc);

        card();
    }

    private void addButtonToPanel(JPanel panel, GridBagConstraints gbc, int xPos, JButton button) {
        gbc.gridx = xPos;
        panel.add(button, gbc);
    }

    private void card() {
        lowerPanel.add(readUser, SHOW_ALL);
        lowerPanel.add(creditUser, SHOW_CREDIT);
        lowerPanel.add(debitUser, SHOW_DEBIT);
        lowerPanel.add(empty, "Empty");
        cardLayout.show(lowerPanel, "Empty");
    }

    public void resetView() {
        cardLayout.show(lowerPanel, "Empty");
        revalidate();
        repaint();
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        creditUser.setConnection(connection);
        debitUser.setConnection(connection);
        readUser.setConnection(connection);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(255, 255, 255, 150));
        g.fillRect(70, 30, getWidth() - 140, 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showAllAccBtn) {
            cardLayout.show(lowerPanel, SHOW_ALL);
        } else if (e.getSource() == showUserCredAccBtn) {
            cardLayout.show(lowerPanel, SHOW_CREDIT);
        } else if (e.getSource() == showDebitAccBtn) {
            cardLayout.show(lowerPanel, SHOW_DEBIT);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Admin Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new ReadMain());
            frame.setVisible(true);
        });
    }
}
