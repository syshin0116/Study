
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

class Calculator extends JFrame implements ActionListener{

    String operator = "";
    String number = "";
    String operand_left = "";
    String operand_right = "";

    JTextField jtf;

    public Calculator() {

        Container ct = getContentPane();
        ct.setLayout(new BorderLayout(10,10));

        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp3 = new JPanel();

        jp1.setLayout(new GridLayout(2,2,5,5));
        jtf = new JTextField();
        jp1.add(jtf);
        jtf.setText("61,755.0");
        jtf.setEditable(false);
        jtf.setHorizontalAlignment(JTextField.RIGHT);
        jp1.setBorder(new TitledBorder("계산 결과 창"));

        jp2.setLayout(new GridLayout(1,2,5,5));
        JButton btn_bs = new JButton("Backspace");
        JButton btn_c = new JButton("C");

        btn_bs.addActionListener(this);
        btn_c.addActionListener(this);

        jp2.add(btn_bs);
        jp2.add(btn_c);
        jp2.setBorder(new TitledBorder("계산기 입력 버튼"));

        jp3.setLayout(new GridLayout(4,5,5,5));


        String btn_txt[][]= {{"7","8","9","/","sqrt"},
                {"4","5","6","*","%"},
                {"1","2","3","-","1/x"},
                {"0","+/-",".","+","="}};



        JButton btn[][] = new JButton[4][5];

        for(int i=0; i<4; i++) {
            for(int j=0; j<5; j++) {
                btn[i][j] = new JButton(btn_txt[i][j]);
                btn[i][j].addActionListener(this);
                jp3.add(btn[i][j]);
            }
        }

        ct.add(jp1, BorderLayout.NORTH);
        ct.add(jp2, BorderLayout.CENTER);
        ct.add(jp3, BorderLayout.SOUTH);

        setTitle("Calculator");
        setSize(400,300);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        //System.out.println(e.getActionCommand());

        if(e.getActionCommand()=="C") {
            jtf.setText("0");
            operator = "";
            operand_left = "";
            operand_right = "";
            number = "";
        }else if(e.getActionCommand()=="=") {
            operand_right = number;
            double result=0.0;

            if(operator.charAt(0)=='+') {
                result = Double.parseDouble(operand_left) + Double.parseDouble(operand_right);
                System.out.println(result);
                jtf.setText(String.valueOf(result));
            }else if(operator.charAt(0)=='-') {
                result = Double.parseDouble(operand_left) + Double.parseDouble(operand_right);
                jtf.setText(String.valueOf(result));
                System.out.println(result);
            }else if(operator.charAt(0)=='*') {
                jtf.setText(String.valueOf(result));
                result = Double.parseDouble(operand_left) + Double.parseDouble(operand_right);
                System.out.println(result);
            }else if(operator.charAt(0)=='/') {
                result = Double.parseDouble(operand_left) + Double.parseDouble(operand_right);
                jtf.setText(String.valueOf(result));
                System.out.println(result);
            }
        }else if(e.getActionCommand()=="+"||e.getActionCommand()=="-"||e.getActionCommand()=="*"||e.getActionCommand()=="/") {
            operand_left = number;
            operator = e.getActionCommand();
            number = "";
            System.out.println(operand_left+", "+ operator);
        }else if(e.getActionCommand()=="Backspace") {
            System.out.println(jtf.getText());
            int length = jtf.getText().length();
            if(length>0) {
                System.out.println("length = "+length);
                String subStr = jtf.getText().substring(0, length-1);
                jtf.setText(subStr);
            }
        }else {
            number = number + e.getActionCommand();
            jtf.setText(number);
        }
        if(e.getActionCommand()=="sqrt") {
            double data = Double.parseDouble(jtf.getText());
            double data2 = Math.sqrt(data);
            System.out.println(data2);
            //- / * sqrt
        }
    }
}

public class CalculatorExam {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new Calculator();
    }

}
