package Employee.Show_Accounts;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ReadMain extends JPanel implements ActionListener {
    JPanel upperPanel, lowerPanel, holderPanel;
    GridBagConstraints gbc;
    Dimension size;
    Image bg;
    private JButton showAllAccBtn, showUserCredAccBtn, showDebitAccbtn, exitBtn;
    CardLayout cardLayout = new CardLayout();
    ShowAllUsers readUser = new ShowAllUsers();
    ShowCredit creditUser = new ShowCredit();
    ShowDebit debitUser = new ShowDebit();
    Empty empty = new Empty();
    //private Connection connection;

    public ReadMain() {
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();
        setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 50), 2));
        setLayout(new GridBagLayout());

        createPanel();
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

    public void createPanel() {
        holderPanel = new JPanel();
        holderPanel.setOpaque(false);

        lowerPanel = new JPanel(cardLayout);
        lowerPanel.setBackground(new Color(255, 255, 255, 230));
        lowerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        lowerPanel.setOpaque(false);

        upperPanel = new JPanel();
        upperPanel.setBackground(new Color(22, 180, 161));
        upperPanel.setLayout(new GridBagLayout());
        upperPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public void createButtons() {
        size = new Dimension(150, 40);

        showAllAccBtn = styleButton("All Accounts");
        showAllAccBtn.addActionListener(this);

        showUserCredAccBtn = styleButton("Credit Account");
        showUserCredAccBtn.addActionListener(this);

        showDebitAccbtn = styleButton("Debit Account");
        showDebitAccbtn.addActionListener(this);

        exitBtn = styleButton("Back");
        exitBtn.addActionListener(this);
    }

    private JButton styleButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(size);
        btn.setFocusPainted(false);
        btn.setForeground(Color.black);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createLineBorder(new Color(22, 180, 161), 2));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        return btn;
    }

    public void layoutButtons() {
        holderPanel.setLayout(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridy = 0;
        buttonGbc.insets = new Insets(0, 10, 0, 10);

        buttonGbc.gridx = 0;
        holderPanel.add(showAllAccBtn, buttonGbc);

        buttonGbc.gridx = 1;
        holderPanel.add(showUserCredAccBtn, buttonGbc);

        buttonGbc.gridx = 2;
        holderPanel.add(showDebitAccbtn, buttonGbc);

        buttonGbc.gridx = 3;
        holderPanel.add(exitBtn, buttonGbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        upperPanel.add(holderPanel, gbc);
        card();
    }

    public void card() {
        lowerPanel.add(readUser, "Show All Accounts");
        lowerPanel.add(creditUser, "Show Credit");
        lowerPanel.add(debitUser, "Show Debit");
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

    // public void setConnection(Connection connection) {
    //     this.connection = connection;
    // }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }

        g.setColor(new Color(255, 255, 255, 150));
        g.fillRect(70, 30, getWidth() - 140, 2);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showAllAccBtn) {
            cardLayout.show(lowerPanel, "Show All Accounts");
        } else if (e.getSource() == showUserCredAccBtn) {
            cardLayout.show(lowerPanel, "Show Credit");
        } else if (e.getSource() == showDebitAccbtn) {
            cardLayout.show(lowerPanel, "Show Debit");
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
