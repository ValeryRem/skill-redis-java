package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class MainForm {
    private JPanel mainPanel;
    private JTextField surNameField;
    private JTextField nameField;
    private JTextField patronimField;
    private JButton actionButton;
    private JTextField unitedField;
    private JTextField surnameTextField;
    private JTextField nameTextField;
    private JTextField patronimTextField;
    private JTextField unitedNameTextField;
    private String surname;
    private String name;
    private String patronimic;

    public MainForm (){
        unitedField.setVisible(false);
        unitedNameTextField.setVisible(false);
        actionButton.addActionListener(new Action() {
            @Override
            public Object getValue(String key) {
                return null;
            }

            @Override
            public void putValue(String key, Object value) {

            }

            @Override
            public void setEnabled(boolean b) {

            }

            @Override
            public boolean isEnabled() {
                return false;
            }

            @Override
            public void addPropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void removePropertyChangeListener(PropertyChangeListener listener) {

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if(actionButton.getText().equals("Collapse")) {
                    if(!surNameField.getText().isEmpty() && !surNameField.getText().equals("Введите фамилию")){
                        surname = surNameField.getText();
                    }
                    if(surNameField.getText().isEmpty()){
                        surNameField.setText("Введите фамилию");
                    }
                    if(!nameField.getText().isEmpty() && !nameField.getText().equals("Введите имя")){
                        name = nameField.getText();
                    }
                    if(nameField.getText().isEmpty()){
                        nameField.setText("Введите имя");
                    }

                    if(patronimTextField.getText().isEmpty()) {
                        patronimField.setText("");
                    } else {
                        if (!patronimField.getText().equals("Введите отчество")) {
                            patronimic = patronimField.getText();
                        }
                    }

                    if(!nameField.getText().equals("Введите имя") && !surNameField.getText().equals("Введите фамилию") &&
                    !patronimField.getText().equals("Введите отчество")) {
                        actionButton.setText("Expand");
                        surNameField.setVisible(false);
                        nameField.setVisible(false);
                        patronimField.setVisible(false);
                        surnameTextField.setVisible(false);
                        nameTextField.setVisible(false);
                        patronimTextField.setVisible(false);
                        unitedField.setText(surname + " " + name + " " + patronimic);
                        unitedNameTextField.setVisible(true);
                        unitedField.setVisible(true);
                    }
                } else {
                    actionButton.setText("Collapse");
                    surnameTextField.setVisible(true);
                    nameTextField.setVisible(true);
                    patronimTextField.setVisible(true);
                    surNameField.setVisible(true);
                    nameField.setVisible(true);
                    patronimField.setVisible(true);
                    unitedField.setText("");
                    unitedField.setVisible(false);
                    unitedNameTextField.setVisible(false);
                }
            }
        });
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

    public JButton getActionButton() {
        return actionButton;
    }

    public JTextField getUnitedField() {
        return unitedField;
    }

    public JTextField getUnitedNameTextField() {
        return unitedNameTextField;
    }
}
