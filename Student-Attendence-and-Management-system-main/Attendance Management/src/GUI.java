import javax.swing.*;
import java.awt.*;

public class GUI {
    public static JButton JButton(String text)
    {
        JButton button = new JButton(text);
        button.setBounds(150,450,250,150);
        button.setFocusable(false);
        button.setText(text);
        button.setBackground(new Color(128, 98, 214));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Poppins",Font.BOLD,15));
        button.setBorder(BorderFactory.createEtchedBorder());
        button.setPreferredSize(new Dimension(100,50));
        return button;
    }

    public static JLabel JLabel(String text)
    {
        JLabel label = new JLabel(text);
        label.setBounds(100,150,200,20);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Poppins",Font.BOLD,20));
        return label;
    }
    public static JTextField JTextField()
    {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Poppins",Font.PLAIN,15));
        textField.setBorder(BorderFactory.createEtchedBorder());
        return textField;
    }


}
