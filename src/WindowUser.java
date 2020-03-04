import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class WindowUser {
    JFrame mainWindowJFrame;
    private JTextField bookIDJTxt, bookTitleJTxt, authorNameJTxt, pubYearJTxt, bookISBMJTxt, bookStatusJTxt;
    JLabel bookIdJLbl, bookTitleJLbl, authorNameJLbl, pubYearJLbl, bookISBNJLbl, bookStatusJLbl;
    static private JTable jTable;
    JPanel leftBookFormPanel;
    JButton btnAddMusic, btnLoadData, btnRefresh, btnSettings, btnCancel;

    final static int J_TABLE_WIDTH = 900;

    private static JButton btnAdd;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    static DefaultTableModel model = new DefaultTableModel();

    public WindowUser() {
        run();
        initModel();
        sort();
        refreshTable();
    }

    private void initModel() {
        Object[] col = {"BookID", "BookTitle", "BookAuthorName", "BookPublicationYear", "BookISBN", "BookStatus", "Shelf_ShelfID"};
        model.setColumnIdentifiers(col);
        jTable.setModel(model);
    }

    private void sort() {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        jTable.setRowSorter(sorter);
    }

    private void run() {
        mainWindowJFrame = createMainJFrame("Library Management System");

        leftBookFormPanel = buildMusicFormJPanel();
        JPanel rightBookTablePanel = new JPanel();
        JTextArea infoTxt = new JTextArea();

        rightBookTablePanel.setBounds(10, 170, 244, 143);
        rightBookTablePanel.setLayout(null);
        rightBookTablePanel.add(infoTxt);

        infoTxt.setEditable(false);
        infoTxt.setBounds(0, 0, 244, 143);

        mainWindowJFrame.add(leftBookFormPanel);
        mainWindowJFrame.add(rightBookTablePanel);

        initializeButtons();

        addButtonsToMusicFrame();

        btnAddMusic.addActionListener(e -> onBtnAddToLibraryClick());

        btnSettings.addActionListener(e -> {
            mainWindowJFrame.dispose();
            new Settings();
        });

        btnLoadData.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();

            if (jTable.getSelectedRow() < 0) {
                NotificationManager.Error("Select a row to display");
            } else {
                conn = DBConnection.getConnection();
                int i = jTable.getSelectedRow();
                infoTxt.setText("");
                infoTxt.append("Book Information: \n"
                        + "\nID:\t" + model.getValueAt(i, 1).toString()
                        + "\nTitle:\t" + model.getValueAt(i, 2).toString()
                        + "\nAuthor Name:\t" + model.getValueAt(i, 3).toString()
                        + "\nPublication Year:\t" + model.getValueAt(i, 4).toString()
                        + "\nISBN:\t" + model.getValueAt(i, 5).toString()
                        + "\nStatus:\t" + model.getValueAt(i, 6).toString());
            }
        });

        btnRefresh.addActionListener(e -> refreshTable());

        jTable = new JTable();
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {
                        "BookID", "BookTitle", "BookAuthorName", "BookPublicationYear", "BookISBN", "BookStatus", "Shelf_ShelfID"
                }
        ));

        JScrollPane scrollPane = new JScrollPane(jTable);
        scrollPane.setBounds(264, 11, J_TABLE_WIDTH, 489);
        mainWindowJFrame.add(scrollPane);

    }

    private JFrame createMainJFrame(String title) {
        JFrame mJFrame = new JFrame(title);
        mJFrame.setBounds(100, 100, J_TABLE_WIDTH + 280, 550);
        mJFrame.setLayout(null);
        mJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // automatic close when different frame
        mJFrame.setLocationRelativeTo(null);
        mJFrame.setResizable(false);
        mJFrame.setVisible(true);
        return mJFrame;
    }

    private void addButtonsToMusicFrame() {
        mainWindowJFrame.add(btnAddMusic);
        mainWindowJFrame.add(btnLoadData);
        mainWindowJFrame.add(btnRefresh);
        mainWindowJFrame.add(btnSettings);
    }

    private void initializeButtons() {
        btnAddMusic = new JButton("Add to Library");
        btnLoadData = new JButton("Load Data");
        btnRefresh = new JButton("Refresh Data");
        btnSettings = new JButton("Settings");

        btnAddMusic.setBounds(10, 11, 244, 23);
        btnLoadData.setBounds(10, 86, 244, 23);
        btnRefresh.setBounds(10, 111, 244, 23);
        btnSettings.setBounds(10, 136, 244, 23);
    }

    private void initializeLabels() {
        bookIdJLbl = new JLabel("IDS");
        bookTitleJLbl = new JLabel("Title");
        authorNameJLbl = new JLabel("Author");
        pubYearJLbl = new JLabel("Pub. Year");
        bookISBNJLbl = new JLabel("ISBN");
        bookStatusJLbl = new JLabel("Status");

        bookIdJLbl.setBounds(0, 0, 46, 14);
        bookTitleJLbl.setBounds(0, 25, 46, 14);
        authorNameJLbl.setBounds(0, 50, 70, 14);
        pubYearJLbl.setBounds(0, 75, 89, 14);
        bookISBNJLbl.setBounds(0, 100, 46, 14);
        bookStatusJLbl.setBounds(0, 125, 46, 14);
    }

    private void initializeTextFields() {
        bookIDJTxt = new JTextField();
        bookTitleJTxt = new JTextField();
        authorNameJTxt = new JTextField();
        pubYearJTxt = new JTextField();
        bookISBMJTxt = new JTextField();
        bookStatusJTxt = new JTextField();

        bookIDJTxt.setBounds(119, 0, 125, 20);
        bookTitleJTxt.setBounds(119, 25, 125, 20);
        authorNameJTxt.setBounds(119, 50, 125, 20);
        pubYearJTxt.setBounds(119, 75, 125, 20);
        bookISBMJTxt.setBounds(119, 100, 125, 20);
        bookStatusJTxt.setBounds(119, 125, 125, 20);
    }

    private JPanel buildMusicFormJPanel() {
        JPanel ms = new JPanel();

        initializeLabels();
        initializeTextFields();

        ms.setBounds(10, 324, 244, 176);
        ms.setLayout(null);

        // all buttons within the music Form
        ms.add(bookIdJLbl);
        ms.add(bookTitleJLbl);
        ms.add(authorNameJLbl);
        ms.add(pubYearJLbl);
        ms.add(bookISBNJLbl);
        ms.add(bookStatusJLbl);

        ms.add(pubYearJTxt);
        ms.add(bookIDJTxt);
        ms.add(bookTitleJTxt);
        ms.add(authorNameJTxt);
        ms.add(bookISBMJTxt);
        ms.add(bookStatusJTxt);
        ms.setVisible(false);

        return ms;
    }

    // update the contents of the table
    public void refreshTable() {
        conn = DBConnection.getConnection();

        if(conn != null) {

            String sql = "SELECT * FROM Book";
            System.out.println("refreshTable- SQL : " + sql);

            try {
                pst = conn.prepareStatement(sql);
                rs = pst.executeQuery();
                Object [] columnData = new Object[7];
                model.setRowCount(0);

                while (rs.next()) {
                    columnData[0] = rs.getString("BookID");
                    columnData[1] = rs.getString("BookTitle");
                    columnData[2] = rs.getString("BookAuthorName");
                    columnData[3] = rs.getString("BookPublicationYear");
                    columnData[4] = rs.getString("BookISBN");
                    columnData[5] = rs.getString("BookStatus");
                    columnData[6] = rs.getString("Shelf_ShelfID");
                    model.addRow(columnData);
                }
            } catch (Exception e) {
                NotificationManager.Warning("[refreshTable] " + e.getMessage());
            }
        }

    }

    private void onBtnAddToLibraryClick() {
        leftBookFormPanel.setVisible(true);

        btnAdd = new JButton("Add"); // make this single instance
        btnAdd.setEnabled(true);
        btnAdd.addActionListener(e1 -> {
            if (
                    pubYearJTxt.getText().isEmpty() ||
                            authorNameJTxt.getText().isEmpty()  ||
                            bookTitleJTxt.getText().isEmpty()  ||
                            bookIDJTxt.getText().isEmpty()
            ) {
                NotificationManager.Message("Alert", "Please fill out all fields.");
            } else {
                BookObject bookObject = createBookObject();
                /*boolean success = addToTable(bookObject);

                //display success or error message depending on boolean returned by addTable()
                if(!success){
                    NotificationManager.Error("Error occurred in the database process. Please try again.");
                } else {
                    NotificationManager.Success("Book was added successfully");

                    bookIDJTxt.setText("");
                    bookTitleJTxt.setText("");
                    authorNameJTxt.setText("");
                    pubYearJTxt.setText("");
                    bookISBMJTxt.setText("");
                    bookStatusJTxt.setText("");
                }*/

                leftBookFormPanel.setVisible(false);
            }
        });
        btnAdd.setBounds(0, 153, 116, 23);

        btnCancel = new JButton("Cancel");
        btnCancel.setBounds(128, 153, 116, 23);
        btnCancel.addActionListener(e -> {
            pubYearJTxt.setText("");
            authorNameJTxt.setText("");
            bookTitleJTxt.setText("");
            bookIDJTxt.setText("");
            bookISBMJTxt.setText("");
            bookStatusJTxt.setText("");

            leftBookFormPanel.setVisible(false);
        });

        leftBookFormPanel.add(btnAdd);
        leftBookFormPanel.add(btnCancel);

        leftBookFormPanel.revalidate(); // update changes
        leftBookFormPanel.repaint(); // update changes
    }

    private BookObject createBookObject() {
        BookObject bookObject = new BookObject();

        String bookID = bookIDJTxt.getText();
        String bookTitle = bookTitleJTxt.getText();
        String authorName = authorNameJTxt.getText();
        String pubYear = pubYearJTxt.getText();
        String bookISBM = bookISBMJTxt.getText();
        String bookStatus = bookStatusJTxt.getText();

        System.out.println("book ID" + bookID);
        bookObject.setId(bookID);
        bookObject.setTitle(bookTitle);
        bookObject.setAuthor(authorName);
        bookObject.setPubYear(pubYear);
        bookObject.setIsbm(bookISBM);
        bookObject.setStatus(bookStatus);

        return bookObject;
    }
}
