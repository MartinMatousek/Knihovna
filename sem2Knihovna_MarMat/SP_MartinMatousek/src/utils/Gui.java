package utils;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.*;
import app.Logic;

public class Gui extends JFrame {

    public static JLabel searchLabel;
    public static JTextField searchField;
    public static JButton buttonOK;
    public static JList listOfBooks;
    public static JScrollPane scrollList;
    public static JLabel nameLabel;
    public static JTextField nameField;
    public static JLabel helpLabel;
    public static JButton buttonLend;
    public static JButton buttonReturn;

    public static String[] booksArray;

    public Gui(String[] books) {
        super("Knihovna");

        booksArray = books;

        FlowLayout layout = new FlowLayout();
        setLayout(layout);

        searchLabel = new JLabel("Hledat");
        add(searchLabel);

        searchField = new JTextField("", 20);
        add(searchField);

        listOfBooks = new JList(books);
        scrollList = new JScrollPane(listOfBooks);
        add(scrollList);
        buttonOK = new JButton("OK");
        add(buttonOK);
        buttonOK.setBackground(Color.green);
        buttonOK.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                listOfBooks.setListData(Logic.changeFilter(searchField.getText()));
            }
        ;
        });
        
        nameLabel = new JLabel("Jméno");
        add(nameLabel);

        nameField = new JTextField("", 20);
        add(nameField);

        helpLabel = new JLabel("Formát: Jméno Příjmení");
        add(helpLabel);

        buttonLend = new JButton("Půjčit");
        add(buttonLend);
        buttonLend.setBackground(Color.yellow);
        buttonLend.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    booksArray = Logic.lendBook(listOfBooks.getSelectedValue().toString(), nameField.getText());
                    Logic.save();
                    listOfBooks.setListData(Logic.changeFilter(searchField.getText()));

                } catch (IOException ex) {
                    System.out.println("IO exception");
                }
            }
        ;
        });

        buttonReturn = new JButton("Vrátit");
        add(buttonReturn);
        buttonReturn.setBackground(Color.red);
        buttonReturn.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    booksArray = Logic.returnBook(listOfBooks.getSelectedValue().toString());
                    Logic.save();
                    listOfBooks.setListData(Logic.changeFilter(searchField.getText()));

                } catch (IOException ex) {
                    System.out.println("IO Exception");
                }
            }
        ;
    }


);    
        
        
    }

}
