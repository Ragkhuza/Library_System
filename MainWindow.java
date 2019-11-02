package finals;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

public class MainWindow {

	JFrame frmMusicManagementSystem;
	private JTextField albumArtist;
	private JTextField albumTitle;
	private JTextField artist;
	private JTextField title;
	private JTextField year;
	private JTextField genre;
	private JTable table;

	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	DefaultTableModel model = new DefaultTableModel();


	public void openMainWindow() {
		MainWindow window = new MainWindow();
		window.frmMusicManagementSystem.setVisible(true);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main (String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmMusicManagementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// edit existing data on the table
	public boolean modifyTable(MusicObject musicObject) {

		boolean success = false;

		conn = DBConnection.getConnection();

		String id= (String)model.getValueAt(table.getSelectedRow(), 0);
		String sql = "UPDATE Music SET Title ='" + musicObject.getTitle()
					+ "', Artist ='" + musicObject.getArtist()
					+ "', AlbumTitle ='" + musicObject.getAlbumTitle()
					+ "', AlbumArtist ='" + musicObject.getAlbumArtist()
					+ "', Year = '" + musicObject.getYear()
					+ "', Genre = '" + musicObject.getGenre()
					+ "' WHERE MusicID = '" + id + "'";

		System.out.println("modifyTable- SQL : " + sql);

		try {
			pst = conn.prepareStatement(sql);
			int count = pst.executeUpdate();	// count is used to determine if there was a row added in the database

			// check if success
			if(count > 0){
				success= true;	//return success so success message displays
				// then redraw the table
				DefaultTableModel model= (DefaultTableModel)table.getModel();
				model.setRowCount(0);
				refreshTable();
			}

		}

		catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
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
					DefaultTableModel model= (DefaultTableModel)table.getModel();
					model.setRowCount(0);
					refreshTable();
				}

			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
		}

		return success;

	}

	// update the contents of the table
	public void refreshTable() {
		conn = DBConnection.getConnection();

		if(conn != null) {

			String sql = "SELECT MusicID, Title, Artist, AlbumTitle, AlbumArtist, Year, Genre FROM Music";
			System.out.println("refreshTable- SQL : " + sql);

			try {
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				Object [] columnData = new Object[7];

				while (rs.next()) {
					columnData[0] = rs.getString("MusicID");
					columnData[1] = rs.getString("Title");
					columnData[2] = rs.getString("Artist");
					columnData[3] = rs.getString("AlbumTitle");
					columnData[4] = rs.getString("AlbumArtist");
					columnData[5] = rs.getString("Year");
					columnData[6] = rs.getString("Genre");
					model.addRow(columnData);
				}
			}

			catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
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
				JOptionPane.showMessageDialog(null, e);
			}

		}

	}

	/**
	 * Create the application.
	 * @throws SQLException
	 */
	public MainWindow() {
		initialize();

		Object col[] = {"MusicID", "Title", "Artist", "AlbumTitle", "AlbumArtist", "Year", "Genre"};
		model.setColumnIdentifiers(col);
		table.setModel(model);

		refreshTable();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frmMusicManagementSystem = new JFrame(); // Ito iyong main window
		frmMusicManagementSystem.setTitle("Music Management System");
		frmMusicManagementSystem.setBounds(100, 100, 900, 550);
		frmMusicManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMusicManagementSystem.getContentPane().setLayout(null);

		JPanel musicForm = new JPanel();
		musicForm.setBounds(10, 324, 244, 176);
		frmMusicManagementSystem.getContentPane().add(musicForm);
		musicForm.setLayout(null);

			JLabel lblTitle = new JLabel("Title");
			lblTitle.setBounds(0, 0, 46, 14);
			musicForm.add(lblTitle);

			JLabel lblArtist = new JLabel("Artist");
			lblArtist.setBounds(0, 25, 46, 14);
			musicForm.add(lblArtist);

			JLabel lblAlbum = new JLabel("Album Title");
			lblAlbum.setBounds(0, 50, 70, 14);
			musicForm.add(lblAlbum);

			JLabel lblAlbumArtist = new JLabel("Album Artist");
			lblAlbumArtist.setBounds(0, 75, 89, 14);
			musicForm.add(lblAlbumArtist);

			JLabel lblYear = new JLabel("Year");
			lblYear.setBounds(0, 100, 46, 14);
			musicForm.add(lblYear);

			JLabel lblGenre = new JLabel("Genre");
			lblGenre.setBounds(0, 125, 46, 14);
			musicForm.add(lblGenre);

			title = new JTextField();
			title.setBounds(119, 0, 125, 20);
			musicForm.add(title);
			title.setColumns(10);

			albumArtist = new JTextField();
			albumArtist.setBounds(119, 75, 125, 20);
			musicForm.add(albumArtist);
			albumArtist.setColumns(10);

			albumTitle = new JTextField();
			albumTitle.setBounds(119, 50, 125, 20);
			musicForm.add(albumTitle);
			albumTitle.setColumns(10);

			artist = new JTextField();
			artist.setBounds(119, 25, 125, 20);
			musicForm.add(artist);
			artist.setColumns(10);

			year = new JTextField();
			year.setBounds(119, 100, 125, 20);
			musicForm.add(year);
			year.setColumns(10);

			genre = new JTextField();
			genre.setBounds(119, 125, 125, 20);
			musicForm.add(genre);
			genre.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(10, 145, 244, 168);
		frmMusicManagementSystem.getContentPane().add(panel);
		panel.setLayout(null);

		JTextArea jtxtData = new JTextArea();
		jtxtData.setEditable(false);
		jtxtData.setBounds(0, 0, 244, 168);
		panel.add(jtxtData);

		JButton btnAddMusic = new JButton("Add to Library");
		btnAddMusic.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				musicForm.setVisible(true);

				JButton btnAdd = new JButton("Add");
				btnAdd.setEnabled(true);
				btnAdd.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {

						if (
								title.getText().isEmpty() ||
								artist.getText().isEmpty()  ||
								albumTitle.getText().isEmpty()  ||
								albumArtist.getText().isEmpty()
							)
						{

							JOptionPane.showMessageDialog(null, "Please fill out all fields.");

						}

						else {
							MusicObject musicObject = createMusicObject();
							boolean success = false;
							success = addTable(musicObject);

							//display success or error message depending on boolean returned by addTable()
							if(!success){
								JOptionPane.showMessageDialog(null, "Error occurred in the database process. Please try again.",
										"Error",
										JOptionPane.OK_OPTION);
							} else {
								JOptionPane.showMessageDialog(null, "Music was added successfully",
										"Alert",
										JOptionPane.INFORMATION_MESSAGE);

								title.setText("");
								artist.setText("");
								albumTitle.setText("");
								albumArtist.setText("");
								year.setText("");
								genre.setText("");
							}

							musicForm.setVisible(false);
						}
					}
				});

				btnAdd.setBounds(0, 153, 116, 23);
				musicForm.add(btnAdd);

			}
		});



		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(128, 153, 116, 23);
		musicForm.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				title.setText("");
				artist.setText("");
				albumTitle.setText("");
				albumArtist.setText("");
				year.setText("");
				genre.setText("");

				musicForm.setVisible(false);
			}
		});

		musicForm.setVisible(false);

		btnAddMusic.setBounds(10, 11, 244, 23);
		frmMusicManagementSystem.getContentPane().add(btnAddMusic);

		JButton btnRemove = new JButton("Remove from Library");
		btnRemove.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				if(table.getSelectedRow() < 1) {
					JOptionPane.showMessageDialog(null, "Select a row to delete",
							"Alert",
							JOptionPane.OK_OPTION);
				} else {
					String id= (String)model.getValueAt(table.getSelectedRow(), 0);
					removeTable(id);
					model.removeRow(table.getSelectedRow());
				}
			}

		});

		btnRemove.setBounds(10, 36, 244, 23);
		frmMusicManagementSystem.getContentPane().add(btnRemove);

		JButton btnEdit = new JButton("Modify Data");

		btnEdit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				conn = DBConnection.getConnection();

				int i = table.getSelectedRow();

				title.setText((String)model.getValueAt(i, 1).toString());
				artist.setText((String)model.getValueAt(i, 2).toString());
				albumTitle.setText((String)model.getValueAt(i, 3).toString());
				albumArtist.setText((String)model.getValueAt(i, 4).toString());
				year.setText((String)model.getValueAt(i, 5).toString());
				genre.setText((String)model.getValueAt(i, 6).toString());


				JButton btnModify = new JButton("Modify");

				btnModify.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {

						MusicObject musicObject = createMusicObject();
						boolean success = false;
						success = modifyTable(musicObject);



						//display success or error message depending on boolean returned by addTable()
						if(!success){
							JOptionPane.showMessageDialog(null, "Error occurred in the database process. Please try again.",
									"Error",
									JOptionPane.OK_OPTION);
						} else {
							JOptionPane.showMessageDialog(null, "Music was successfully updated.",
									"Alert",
									JOptionPane.INFORMATION_MESSAGE);

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

				btnModify.setBounds(0, 153, 116, 23);
				musicForm.add(btnModify);

				if(table.getSelectedRow() < 1) {
					JOptionPane.showMessageDialog(null, "Select a row to modify",
							"Alert",
							JOptionPane.OK_OPTION);
				} else {
					musicForm.setVisible(true);
					btnModify.setEnabled(true);
				}


			}
		});
		
		

		btnEdit.setBounds(10, 61, 244, 23);
		frmMusicManagementSystem.getContentPane().add(btnEdit);

		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(e -> {
			frmMusicManagementSystem.dispose();
			Settings.showWindow();
		});
		btnSettings.setBounds(10, 111, 244, 23);
		frmMusicManagementSystem.getContentPane().add(btnSettings);

		JButton btnLoadData = new JButton("Load Data");
		btnLoadData.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				DefaultTableModel model = (DefaultTableModel) table.getModel();

				if(table.getSelectedRow() <= 0) {
					JOptionPane.showMessageDialog(null, "Select a row to display",
							"Alert",
							JOptionPane.OK_OPTION);
				} else {

					conn = DBConnection.getConnection();

					int i = table.getSelectedRow();

						jtxtData.setText("");
						jtxtData.append("Track Information: \n"
								+ "\nTitle:\t" + (String)model.getValueAt(i, 1).toString()
								+ "\nArtist:\t" + (String)model.getValueAt(i, 2).toString()
								+ "\nAlbum:\t" + (String)model.getValueAt(i, 3).toString()
								+ "\nAlbum artist:\t" + (String)model.getValueAt(i, 4).toString()
								+ "\nYear:\t" + (String)model.getValueAt(i, 5).toString()
								+ "\nGenre:\t" + (String)model.getValueAt(i, 6).toString());

				}

			}
		});
		btnLoadData.setBounds(10, 86, 244, 23);
		frmMusicManagementSystem.getContentPane().add(btnLoadData);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Title", "Artist", "AlbumTitle", "AlbumArtist", "Year", "Genre"
			}
		));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(264, 11, 610, 489);
		frmMusicManagementSystem.getContentPane().add(scrollPane);

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
