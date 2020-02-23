import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class MainWindow {
	JFrame frmMusicManagementSystem;
	private JTextField albumArtist, albumTitle, artist, title, year, genre;
	JLabel lblTitle, lblArtist, lblAlbum, lblAlbumArtist, lblYear, lblGenre;
	private JTable jTable;
	JPanel musicForm;
	JButton btnAddMusic, btnRemove, btnEdit, btnLoadData, btnSort, btnSettings, btnCancel;

	final static int J_TABLE_WIDTH = 900;

	private static JButton btnModify, btnAdd;
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	DefaultTableModel model = new DefaultTableModel();

	public MainWindow() {
		run();

		Object[] col = {"BookID", "BookTitle", "BookAuthorName", "BookPublicationYear", "BookISBN", "BookStatus", "Shelf_ShelfID"};
		model.setColumnIdentifiers(col);
		jTable.setModel(model);
		
//		sort();
		refreshTable();
	}
	
	private void sort() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		jTable.setRowSorter(sorter);
	}
	
	private void run() {
		frmMusicManagementSystem = createMainJFrame("Library Management System");

		musicForm = buildMusicFormJPanel();
		JPanel panel = new JPanel();
		JTextArea jtxtData = new JTextArea();

		panel.setBounds(10, 170, 244, 143);
		panel.setLayout(null);
		panel.add(jtxtData);

		jtxtData.setEditable(false);
		jtxtData.setBounds(0, 0, 244, 143);

		frmMusicManagementSystem.add(musicForm);
		frmMusicManagementSystem.add(panel);

		initializeButtons();

		addButtonsToMusicFrame();
		
		btnAddMusic.addActionListener(e -> onBtnAddToLibraryClick());

		btnRemove.addActionListener(e -> {
			DefaultTableModel model = (DefaultTableModel) jTable.getModel();
			if(jTable.getSelectedRow() < 0) {
				NotificationManager.Warning("Select a row to delete");
			}
			else {

				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this from your library?",
						"Alert",
						JOptionPane.YES_OPTION);
				if(response == 0) {
					String id= (String)model.getValueAt(jTable.getSelectedRow(), 0);
					removeTable(id);
					model.removeRow(jTable.getSelectedRow());
				}
			}

		});

		btnEdit.addActionListener(e -> { // modify data kasama ng mga load data

			conn = DBConnection.getConnection();

			int i = jTable.getSelectedRow(); // returns -1 if not table selected

			if (i < 0) {
				NotificationManager.Error("Select a row to modify."); // run!!s <3
				return;
			}

			title.setText((String)model.getValueAt(i, 1).toString()); // Nag error kapag walang laman
			artist.setText((String)model.getValueAt(i, 2).toString());
			albumTitle.setText((String)model.getValueAt(i, 3).toString());
			albumArtist.setText((String)model.getValueAt(i, 4).toString());
			year.setText((String)model.getValueAt(i, 5).toString());
			genre.setText((String)model.getValueAt(i, 6).toString());

			// Modify button from the form
			btnModify = new JButton("Modify"); // make this single instance
			btnModify.setBounds(0, 153, 116, 23);

			btnModify.addActionListener(new ActionListener(){ // nasa music form

				public void actionPerformed(ActionEvent e) {

					MusicObject musicObject = createMusicObject();
					boolean success = false;
					success = modifyTable(musicObject);

					//display success or error message depending on boolean returned by addTable()
					if(!success){
						NotificationManager.Error("Error occurred in the database process. Please try again.");
					} else {
						NotificationManager.Message("Alert","Music was successfully updated!");

						title.setText("");
						artist.setText("");
						albumTitle.setText("");
						albumArtist.setText("");
						year.setText("");
						genre.setText("");

						musicForm.setVisible(false);
					}
				}

			});

			try {
				musicForm.remove(btnAdd);
			} catch(NullPointerException ex) {
				System.out.println("btnmodify not yet clicked, nothing to worry about doggo"); // will throw exception if btnmodify wasn't clicked
			}

			musicForm.add(btnModify); // button needs to be remove first
			musicForm.revalidate(); // update changes
			musicForm.repaint(); // update changes
			if(jTable.getSelectedRow() < 0) {
				NotificationManager.Error("Select a row to modify.");
			}
			else {
				musicForm.setVisible(true);
				btnModify.setEnabled(true);
			}

		});

		btnSettings.addActionListener(e -> {
			frmMusicManagementSystem.dispose();
			new Settings();
		});

		btnLoadData.addActionListener(e -> {
			DefaultTableModel model = (DefaultTableModel) jTable.getModel();

			if(jTable.getSelectedRow() < 0) {
				NotificationManager.Error("Select a row to display");
			} else {

				conn = DBConnection.getConnection();

				int i = jTable.getSelectedRow();

					jtxtData.setText("");
					jtxtData.append("Track Information: \n"
							+ "\nTitle:\t" + (String)model.getValueAt(i, 1).toString()
							+ "\nArtist:\t" + (String)model.getValueAt(i, 2).toString()
							+ "\nAlbum:\t" + (String)model.getValueAt(i, 3).toString()
							+ "\nAlbum artist:\t" + (String)model.getValueAt(i, 4).toString()
							+ "\nYear:\t" + (String)model.getValueAt(i, 5).toString()
							+ "\nGenre:\t" + (String)model.getValueAt(i, 6).toString());

			}

		});

		btnSort.addActionListener(e -> {
			NotificationManager.Message("Alert", "To sort data, click on the column of the table.");
		});

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
		frmMusicManagementSystem.add(scrollPane);

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
		frmMusicManagementSystem.add(btnAddMusic);
		frmMusicManagementSystem.add(btnRemove);
		frmMusicManagementSystem.add(btnEdit);
		frmMusicManagementSystem.add(btnLoadData);
		frmMusicManagementSystem.add(btnSort);
		frmMusicManagementSystem.add(btnSettings);
	}

	private void initializeButtons() {
		btnAddMusic = new JButton("Add to Library");
		btnRemove = new JButton("Remove from Library");
		btnEdit = new JButton("Modify Data");
		btnLoadData = new JButton("Load Data");
		btnSort = new JButton("Sort Data");
		btnSettings = new JButton("Settings");

		btnAddMusic.setBounds(10, 11, 244, 23);
		btnRemove.setBounds(10, 36, 244, 23);
		btnEdit.setBounds(10, 61, 244, 23);
		btnLoadData.setBounds(10, 86, 244, 23);
		btnSort.setBounds(10, 111, 244, 23);
		btnSettings.setBounds(10, 136, 244, 23);
	}

	private void initializeLabels() {
		lblTitle = new JLabel("Title");
		lblArtist = new JLabel("Artist");
		lblAlbum = new JLabel("Album Title");
		lblAlbumArtist = new JLabel("Album Artist");
		lblYear = new JLabel("Year");
		lblGenre = new JLabel("Genre");

		lblTitle.setBounds(0, 0, 46, 14);
		lblArtist.setBounds(0, 25, 46, 14);
		lblAlbum.setBounds(0, 50, 70, 14);
		lblAlbumArtist.setBounds(0, 75, 89, 14);
		lblYear.setBounds(0, 100, 46, 14);
		lblGenre.setBounds(0, 125, 46, 14);

	}

	private void initializeTextFields() {
		title = new JTextField();
		albumArtist = new JTextField();
		albumTitle = new JTextField();
		artist = new JTextField();
		year = new JTextField();
		genre = new JTextField();

		title.setBounds(119, 0, 125, 20);
		albumArtist.setBounds(119, 75, 125, 20);
		albumTitle.setBounds(119, 50, 125, 20);
		artist.setBounds(119, 25, 125, 20);
		genre.setBounds(119, 125, 125, 20);
		year.setBounds(119, 100, 125, 20);
	}

	private JPanel buildMusicFormJPanel() {
		JPanel ms = new JPanel();

		initializeLabels();
		initializeTextFields();

		ms.setBounds(10, 324, 244, 176);
		ms.setLayout(null);

		// all buttons within the music Form
		ms.add(lblTitle);
		ms.add(lblArtist);
		ms.add(lblAlbum);
		ms.add(lblAlbumArtist);
		ms.add(lblYear);
		ms.add(lblGenre);

		ms.add(title);
		ms.add(albumArtist);
		ms.add(albumTitle);
		ms.add(artist);
		ms.add(year);
		ms.add(genre);
		ms.setVisible(false);

		return ms;
	}

	private void onBtnAddToLibraryClick() {
		musicForm.setVisible(true);

		btnAdd = new JButton("Add"); // make this single instance
		btnAdd.setEnabled(true);
		btnAdd.addActionListener(e1 -> {

			if (
					title.getText().isEmpty() ||
					artist.getText().isEmpty()  ||
					albumTitle.getText().isEmpty()  ||
					albumArtist.getText().isEmpty()
			)
			{
				NotificationManager.Message("Alert", "Please fill out all fields.");
			} else {
				MusicObject musicObject = createMusicObject();
				boolean success = false;
				success = addTable(musicObject);

				//display success or error message depending on boolean returned by addTable()
				if(!success){
					NotificationManager.Error("Error occurred in the database process. Please try again.");

				} else {
					NotificationManager.Success("Music was added successfully");

					title.setText("");
					artist.setText("");
					albumTitle.setText("");
					albumArtist.setText("");
					year.setText("");
					genre.setText("");

				}

				musicForm.setVisible(false);
			}
		});
		btnAdd.setBounds(0, 153, 116, 23);

		try	{
			musicForm.remove(btnModify);
		} catch(NullPointerException ex) {
			System.out.println("btn modify not yet clicked, nothing to worry about doggo"); // will throw exception if btnmodify wasn't clicked
		}

		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(128, 153, 116, 23);
		btnCancel.addActionListener(e -> {
			title.setText("");
			artist.setText("");
			albumTitle.setText("");
			albumArtist.setText("");
			year.setText("");
			genre.setText("");

			musicForm.setVisible(false);
		});

		musicForm.add(btnAdd);
		musicForm.add(btnCancel);

		musicForm.revalidate(); // update changes
		musicForm.repaint(); // update changes
	}
	
	// edit existing data on the table
	public boolean modifyTable(MusicObject musicObject) {

		boolean success = false;

		conn = DBConnection.getConnection();

		String id= (String)model.getValueAt(jTable.getSelectedRow(), 0);
		String sql = "UPDATE Music SET Title =\"" + musicObject.getTitle()
					+ "\", Artist =\"" + musicObject.getArtist()
					+ "\", AlbumTitle =\"" + musicObject.getAlbumTitle()
					+ "\", AlbumArtist =\"" + musicObject.getAlbumArtist()
					+ "\", Year =\"" + musicObject.getYear()
					+ "\", Genre =\"" + musicObject.getGenre()
					+ "\" WHERE MusicID = '" + id + "'";

		System.out.println("modifyTable- SQL : " + sql);

		try {
			pst = conn.prepareStatement(sql);
			int count = pst.executeUpdate();	// count is used to determine if there was a row added in the database

			// check if success
			if(count > 0){
				success= true;	//return success so success message displays
				// then redraw the table
				DefaultTableModel model= (DefaultTableModel) jTable.getModel();
				model.setRowCount(0);
				refreshTable();
			}

		}

		catch (Exception e) {
			NotificationManager.Warning("[modifyTable] " + e.getMessage());
		}

		return success;

	}

	// adding data to the table
	public boolean addTable(MusicObject musicObject){

		boolean success= false;

		conn = DBConnection.getConnection();

		if(conn != null) {

			String sql = "INSERT INTO Music (Title, Artist, AlbumTitle, AlbumArtist, Year, Genre) ";
			sql += "VALUES (";
			sql += "'" + musicObject.getTitle() + "',";
			sql += "'" + musicObject.getArtist() + "',";
			sql += "'" + musicObject.getAlbumTitle() + "',";
			sql += "'" + musicObject.getAlbumArtist() + "',";
			sql += "'" + musicObject.getYear() + "',";
			sql += "'" + musicObject.getGenre() + "'";
			sql += ")";

			System.out.println("addTable- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				int count = pst.executeUpdate();	// count is used to determine if there was a row added in the database

				// check if success
				if(count > 0){
					success= true;	//return success so success message displays
					// then redraw the table
					DefaultTableModel model= (DefaultTableModel) jTable.getModel();
					model.setRowCount(0);
					refreshTable();
				}

			}

			catch (Exception e) {
				NotificationManager.Warning("[addTable] " + e.getMessage());
			}
		}

		return success;

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

	// removing data from the table
	private void removeTable(String id) {

		conn= DBConnection.getConnection();

		if(conn != null){

			String sql= "DELETE FROM Music WHERE MusicID = " + id;
			System.out.println("removeTable- SQL : " + sql);

			try {
				pst= conn.prepareStatement(sql);
				pst.executeUpdate();

			} catch (Exception e) {
				NotificationManager.Warning("[removeTable] " + e.getMessage());
			}

		}

	}

	private MusicObject createMusicObject() {
		MusicObject musicObject= new MusicObject();

		String albumArtistString= albumArtist.getText();
		String albumTitleString= albumTitle.getText();
		String artistString= artist.getText();
		String titleString= title.getText();
		String yearString= year.getText();
		String genreString= genre.getText();

		musicObject.setAlbumArtist(albumArtistString);
		musicObject.setAlbumTitle(albumTitleString);
		musicObject.setArtist(artistString);
		musicObject.setTitle(titleString);
		musicObject.setYear(yearString);
		musicObject.setGenre(genreString);

		return musicObject;
	}
}
