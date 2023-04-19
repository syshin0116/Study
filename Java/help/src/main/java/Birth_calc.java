import javax.swing.*;
import java.awt.*;

public class Birth_calc{
    public static void main(String[] args) {
        Dimension dim = new Dimension(800, 500);

        JFrame frame = new JFrame("JTextField_JTextArea Test1");
        frame.setLocation(200,400);
        frame.setPreferredSize(dim);

        JTextField textField = new JTextField();
        textField.setText("asdf");
        textField.setEnabled(false);
    }
}