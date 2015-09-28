package me.tyler.terraria.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PacketEditorGUI extends PrintStream{

	public PacketEditorGUI(File file) throws FileNotFoundException {
		super(file);
		initialize();
	}
	private JFrame frame;
	private JTextArea display;
	private JScrollPane scroll;

	public void setVisible(boolean flag){
		frame.setVisible(flag);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		JPanel middlePanel = new JPanel ();
	    middlePanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Console Output" ) );

	    // create the middle panel components

	    display = new JTextArea ( 16, 58 );
	    display.setEditable ( false ); // set textArea non-editable
	    scroll = new JScrollPane ( display );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );

	    //Add Textarea in to middle panel
	    middlePanel.add ( scroll );

	    // My code
	    frame = new JFrame ();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	    frame.add ( middlePanel );
	    frame.pack ();
	    frame.setLocationRelativeTo ( null );
	    frame.setVisible ( true );
		
	}
	@Override
	public void print(String x) {
		super.print(x);
		
		display.setText(display.getText() + x + "\r\n");
	}
	
	@Override
	public void print(int i) {
		super.print(i);
		
		display.setText(display.getText() + i + "\r\n");
		
	}
}
