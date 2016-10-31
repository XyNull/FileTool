package xynull.View;

import xynull.Controller.Controller;

import javax.swing.*;

/**
 * Created by XyNull on 2016/10/9.
 */
public class View {
    private JPanel panelMain;
    private JButton buttonRun;
    private JTextField textFieldPath;
    private JCheckBox checkBoxIgnore;
    private JCheckBox checkBoxVerbose;
    private JTable table1;
    private JComboBox comboBoxChoose;
    private JTextArea textArea1;
    private JTextField textFieldSort;
    private JProgressBar progressBar1;
    private JLabel Target;
    private JTextField textFieldTar;
    private JLabel sortNumLabel;
    private JLabel addonsLabel;
    private JLabel chooseFunctionLabel;
    private JLabel inputPathLabel;
    private JLabel logsLabel;
    private JLabel nothingLabel;
    private JPanel PanelAdd;
    private JPanel PanelFunction;
    private JPanel PanelInput;
    private JPanel PanelLog;

    public static void main(String[] args) {
        JFrame frame = new JFrame("View");
        frame.setContentPane(new View().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        new Controller().preProcess();
    }

    public JButton getButtonRun() {
        return buttonRun;
    }

    public JCheckBox getCheckBoxIgnore() {
        return checkBoxIgnore;
    }
}
