package Controller;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import View.*;

public class MainControl implements ActionListener {
	
	private  MainView view;
	private Image imgModel;

	public MainControl(MainView view, Image model) {
		this.view = view;
		this.imgModel = model;
	}
	
	public MainControl(MainView view) {
		// TODO Auto-generated constructor stub
		this.view = view;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Button clicked: "  + e.getActionCommand());
		
	}
}



