package finals;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
	// for calling on other classes
	public void openMainWindow() {
		MainWindow window = new MainWindow();
		window.frmMusicManagementSystem.setVisible(true);
	}
	
	// main
	public static void main (String[] args) {
		MainWindow window = new MainWindow();
		window.frmMusicManagementSystem.setVisible(true);
	}

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

	public MainWindow() {
		run();

		Object col[] = {"MusicID", "Title", "Artist", "AlbumTitle", "AlbumArtist", "Year", "Genre"};
		model.setColumnIdentifiers(col);
		table.setModel(model);
		
		sort();
		refreshTable();
	}
	
	private void sort() {
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
		table.setRowSorter(sorter);
	}
	
	private void run() {
		frmMusicManagementSystem = new JFrame(); // Ito iyong main window
		frmMusicManagementSystem.setTitle("Music Management System");
		frmMusicManagementSystem.setBounds(100, 100, 900, 550);
		frmMusicManagementSystem.setLayout(null);
		frmMusicManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // automatic close when different frame
		frmMusicManagementSystem.setLocationRelativeTo(null);
		frmMusicManagementSystem.setResizable(false);

		JPanel musicForm = new JPanel();
		JPanel panel = new JPanel();
		JTextArea jtxtData = new JTextArea();
		
		JLabel lblTitle = new JLabel("Title");
		JLabel lblArtist = new JLabel("Artist");
		JLabel lblAlbum = new JLabel("Album Title");
		JLabel lblAlbumArtist = new JLabel("Album Artist");
		JLabel lblYear = new JLabel("Year");
		JLabel lblGenre = new JLabel("Genre");
		
		title = new JTextField();
		albumArtist = new JTextField();
		albumTitle = new JTextField();
		artist = new JTextField();
		year = new JTextField();
		genre = new JTextField();
		
		lblTitle.setBounds(0, 0, 46, 14);
		lblArtist.setBounds(0, 25, 46, 14);
		lblAlbum.setBounds(0, 50, 70, 14);
		lblAlbumArtist.setBounds(0, 75, 89, 14);
		lblYear.setBounds(0, 100, 46, 14);
		lblGenre.setBounds(0, 125, 46, 14);
		
		title.setBounds(119, 0, 125, 20);
		albumArtist.setBounds(119, 75, 125, 20);
		albumTitle.setBounds(119, 50, 125, 20);
		artist.setBounds(119, 25, 125, 20);
		genre.setBounds(119, 125, 125, 20);
		year.setBounds(119, 100, 125, 20);
		
		panel.setBounds(10, 170, 244, 143);
		
		jtxtData.setEditable(false);
		jtxtData.setBounds(0, 0, 244, 143);
		
		musicForm.setBounds(10, 324, 244, 176);
		frmMusicManagementSystem.add(musicForm);
		musicForm.setLayout(null);
			
			// all buttons within the musicForm
			musicForm.add(lblTitle);		
			musicForm.add(lblArtist);		
			musicForm.add(lblAlbum);
			musicForm.add(lblAlbumArtist);
			musicForm.add(lblYear);
			musicForm.add(lblGenre);
			
			musicForm.add(title);
			musicForm.add(albumArtist);
			musicForm.add(albumTitle);
			musicForm.add(artist);
			musicForm.add(year);
			musicForm.add(genre);

		frmMusicManagementSystem.add(panel);
		panel.setLayout(null);
		panel.add(jtxtData);

		JButton btnAddMusic = new JButton("Add to Library");
		JButton btnRemove = new JButton("Remove from Library");
		JButton btnEdit = new JButton("Modify Data");
		JButton btnLoadData = new JButton("Load Data");
		JButton btnSort = new JButton("Sort Data");
		JButton btnSettings = new JButton("Settings");
		JButton btnCancel = new JButton("Cancel");
		
		btnAddMusic.setBounds(10, 11, 244, 23);
		btnRemove.setBounds(10, 36, 244, 23);
		btnEdit.setBounds(10, 61, 244, 23);
		btnLoadData.setBounds(10, 86, 244, 23);
		btnSort.setBounds(10, 111, 244, 23);
		btnSettings.setBounds(10, 136, 244, 23);
		btnCancel.setBounds(128, 153, 116, 23);
		
		frmMusicManagementSystem.add(btnAddMusic);
		frmMusicManagementSystem.add(btnRemove);
		frmMusicManagementSystem.add(btnEdit);
		frmMusicManagementSystem.add(btnLoadData);
		frmMusicManagementSystem.add(btnSort);
		frmMusicManagementSystem.add(btnSettings);
		
		btnAddMusic.addActionListener(e -> {
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

						Alert.Message("Alert", "Please fill out all fields.");

					}

					else {
						MusicObject musicObject = createMusicObject();
						boolean success = false;
						success = addTable(musicObject);

						//display success or error message depending on boolean returned by addTable()
						if(!success){
							Alert.Error("Error occurred in the database process. Please try again.");
							
						} else {
							Alert.Success("Music was added successfully");
							
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
				
		});

		musicForm.add(btnCancel);
		
		btnCancel.addActionListener(e -> {
			title.setText("");
			artist.setText("");
			albumTitle.setText("");
			albumArtist.setText("");
			year.setText("");
			genre.setText("");

			musicForm.setVisible(false);
		});

		musicForm.setVisible(false);
		
		btnRemove.addActionListener(e -> {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			if(table.getSelectedRow() < 0) {
				Alert.Warning("Select a row to delete");
			}
			else {
				
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this from your library?",
						"Alert",
						JOptionPane.YES_OPTION);
				if(response == 0) {
					String id= (String)model.getValueAt(table.getSelectedRow(), 0);
					removeTable(id);
					model.removeRow(table.getSelectedRow());
				}
			}

		});

		// mismong Modify button from the Menu
		btnEdit.addActionListener(e -> {
				
			conn = DBConnection.getConnection();

			int i = table.getSelectedRow(); // returns -1 if not table selected
			
			if (i < 0) {
				Alert.Error("Select a row to remove."); // run!!s <3
				return;
			}

			title.setText((String)model.getValueAt(i, 1).toString()); // Nag error kapag walang laman
			artist.setText((String)model.getValueAt(i, 2).toString());
			albumTitle.setText((String)model.getValueAt(i, 3).toString());
			albumArtist.setText((String)model.getValueAt(i, 4).toString());
			year.setText((String)model.getValueAt(i, 5).toString());
			genre.setText((String)model.getValueAt(i, 6).toString());

			// Modify button from the form
			JButton btnModify = new JButton("Modify");
			btnModify.setBounds(0, 153, 116, 23);

			btnModify.addActionListener(new ActionListener(){

				public void actionPerformed(ActionEvent e) {

					MusicObject musicObject = createMusicObject();
					boolean success = false;
					success = modifyTable(musicObject);
	
					//display success or error message depending on boolean returned by addTable()
					if(!success){
						Alert.Error("Error occurred in the database process. Please try again.");
					} else {
						Alert.Message("Alert","Music was successfully updated!");
	
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

			musicForm.add(btnModify);

			if(table.getSelectedRow() < 0) {
				Alert.Error("Select a row to modify.");
			}
			else {
				musicForm.setVisible(true);
				btnModify.setEnabled(true);
			}
			
		});
				
		btnSettings.addActionListener(e -> {
			frmMusicManagementSystem.dispose();
			Settings.showWindow();
		});
				
		btnLoadData.addActionListener(e -> {

			DefaultTableModel model = (DefaultTableModel) table.getModel();

			if(table.getSelectedRow() < 0) {
				Alert.Error("Select a row to display");
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

		});
		
		btnSort.addActionListener(e -> {
			Alert.Message("Alert", "To sort data, click on the column of the table.");
		});
		

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
		frmMusicManagementSystem.add(scrollPane);

	}
	
	// edit existing data on the table
		public boolean modifyTable(MusicObject musicObject) {

			boolean success = false;

			conn = DBConnection.getConnection();

			String id= (String)model.getValueAt(table.getSelectedRow(), 0);
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
					DefaultTableModel model= (DefaultTableModel)table.getModel();
					model.setRowCount(0);
					refreshTable();
				}

			}

			catch (Exception e) {
				Alert.Warning("[modifyTable] " + e.getMessage());
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
					Alert.Warning("[addTable] " + e.getMessage());
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
					Alert.Warning("[refreshTable] " + e.getMessage());
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
					Alert.Warning("[removeTable] " + e.getMessage());
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
