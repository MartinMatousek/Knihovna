package app;

import utils.Gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logic implements ActionListener {

    private static final DateTimeFormatter TIMEFORMAT = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");
    public static RandomAccessFile fileBooks;
    public static String[] books;

    /**
     *
     * Reads file contents and saves them to String. Splits into array of
     * Strings. Returns array of Strings.
     *
     */
    public static String[] readFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = "\n";

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(file), "UTF8"));
        String str;
        while ((str = in.readLine()) != null) {
            fileContents.append(str + "\n");
        }
        books = fileContents.toString().split("\n");
        return books;
    }

    /**
     *
     * Returns filtered array of Strings which contain given phrase.
     *
     */
    public static String[] changeFilter(String filter) {
        ArrayList<String> obj = new ArrayList<String>();
        String[] filtered = new String[books.length];
        for (int i = 0; i < books.length; i++) {
            if (books[i].toLowerCase().contains(filter.toLowerCase())) {
                obj.add(books[i]);
            }
        }
        for (int i = 0; i < obj.size(); i++) {
            filtered[i] = obj.get(i);
        }
        return filtered;
    }

    /**
     *
     * Adds information about who and when borrowed specified book. Returns
     * array of Strings with additional information.
     *
     */
    public static String[] lendBook(String selectedText, String nameField) {
        if (nameField.matches("\\p{L}+[ ]\\p{L}+")) {
            for (int i = 0; i < books.length; i++) {
                if (selectedText.equals(books[i])) {
                    books[i] = selectedText + " - vypůjčena " + "(" + nameField + ") " + LocalDateTime.now().format(TIMEFORMAT);
                }

            }
        }
        return books;
    }

    /**
     *
     * Removes additional information about specified book. Returns array of
     * Strings without the additional information.
     *
     */
    public static String[] returnBook(String selectedText) {
        for (int i = 0; i < books.length; i++) {
            if (selectedText.equals(books[i])) {

                String[] temp = books[i].split(" -");
                books[i] = temp[0];
            }

        }

        return books;
    }

    /**
     *
     * Saves current array of Strings(books) to file.
     *
     */
    public static void save() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir + "\\books.txt");
        FileWriter fw = new FileWriter(file);

        for (int i = 0; i < books.length; i++) {
            fw.append(books[i] + "\r\n");
        }
        fw.close();
        fileBooks = new RandomAccessFile(file, "rw");

    }

    /**
     * 
     * Declares the input file, saves it contents to array of Strings and initializes GUI.
     *  
     */
    public static void init() throws FileNotFoundException, IOException {
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir + "\\books.txt");
        fileBooks = new RandomAccessFile(file, "rw");
        books = readFile(file.getAbsolutePath());
        Gui okno = new Gui(books);
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setVisible(true);
        okno.pack();
        okno.setLayout(null);
        Gui.searchLabel.setLocation(15, 21);
        Gui.searchField.setLocation(60, 20);
        Gui.buttonOK.setLocation(290, 16);
        Gui.scrollList.setSize(580, 300);
        Gui.scrollList.setLocation(10, 60);
        Gui.nameLabel.setLocation(15, 371);
        Gui.nameField.setLocation(60, 370);
        Gui.helpLabel.setLocation(15, 392);
        Gui.buttonLend.setLocation(290, 366);
        Gui.buttonReturn.setLocation(370, 366);
        okno.setSize(650, 550);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
