package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.lang.NullPointerException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import Controller.MainControl;
import File.MultiReader;
import Image.ImageModel;

public class MainView {

    private static int MAX_IMAGES = 10000;
    // Declaring main view components
    private JFrame mainWindow;
    private JButton openFileBtn;
    private JTextField fileNameTxt;
    private JLabel fileNameLbl;
    private JLabel kValueLbl;
    private JTextField txt = new JTextField(3);
    private JButton grayScaleBtn;
    private JButton rgbButton;
    private JButton edgeButton;
    private JButton classificationBtn;
    private JPanel displayPanel;
    private JLabel imageLabel;
    private List<JLabel> imageLabelList;
    private JFileChooser fileChooser;
    

    MainControl controller;
    List<ImageModel> imgModelList;

    // Default constructor
    public MainView() {
    	
    	imgModelList = new ArrayList<>();
        controller = new MainControl(null);

        mainWindow = new JFrame("Aimee Alexander - ST20134867");
        mainWindow.setLayout(new BorderLayout());

        JPanel fileSelectPanel = new JPanel();

        fileNameLbl = new JLabel("File name: ");
        fileNameTxt = new JTextField(50);
        fileNameTxt.setEnabled(false);
        openFileBtn = new JButton("Open File");
        
        kValueLbl = new JLabel("Insert k value: ");
        
        

        // Add components to the JPanel
        fileSelectPanel.add(fileNameLbl);
        fileSelectPanel.add(fileNameTxt);
        fileSelectPanel.add(openFileBtn);
        
        // Add JPanel to the JFrame's north position
        mainWindow.add(fileSelectPanel, BorderLayout.PAGE_START);

        // Creating main operation button panel
        JPanel opButtonPanel = new JPanel();

        rgbButton = new JButton("RGB Image");
        grayScaleBtn = new JButton("Grayscale Image");
        edgeButton = new JButton("Edge Image");
        classificationBtn = new JButton("Start classification");

        opButtonPanel.add(rgbButton);
        opButtonPanel.add(grayScaleBtn);
        opButtonPanel.add(edgeButton);
        opButtonPanel.add(kValueLbl);
        opButtonPanel.add(txt);
        opButtonPanel.add(classificationBtn);
        
        // A JPanel with box layout
        JPanel northLayout = new JPanel();
        northLayout.setLayout(new BoxLayout(northLayout, BoxLayout.Y_AXIS));

        // add both flow layouts to northLayout
        northLayout.add(fileSelectPanel);
        northLayout.add(opButtonPanel);

        // add northLayout to mainWindow's north
        mainWindow.add(northLayout, BorderLayout.NORTH);

        // instantiate a new JPanel to hold image labels
        displayPanel = new JPanel();
        displayPanel.setPreferredSize(new Dimension(500, 500));
        displayPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        // adding multiple JLabel to allow for display of multiple images from a file
        imageLabelList = new ArrayList<>();
        for (int i = 0; i < MAX_IMAGES; i++) {
            imageLabel = new JLabel();
            imageLabelList.add(imageLabel);
            displayPanel.add(imageLabel);
        }
        
        // add image panel to the centre of the main window
        mainWindow.add(displayPanel, BorderLayout.CENTER);

        // Make the program quit, as the window is closed
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the width and height of mainWindow
        // mainWindow.setSize(new Dimension(200, 100));;
        mainWindow.pack();
        // Display the window.
        mainWindow.setVisible(true);
        

//Creating event handler method for the buttons to be able to open file directory     

openFileBtn.addActionListener(new ActionListener() {
	@Override
                     
public void actionPerformed(ActionEvent e) {
    File selectedFile = showFileChooserDialog();
    //File selectedFile = new File("data_batch_1.bin");
if (selectedFile != null) {
    fileNameTxt.setText(selectedFile.getAbsolutePath());
    try {

        if (selectedFile.getAbsolutePath().endsWith("bin")) {
            MultiReader multiReader = new MultiReader(selectedFile);
            multiReader.read();
            imgModelList = multiReader.getData();
        } else {
            imgModelList.add(new ImageModel(selectedFile));
        }

    } catch (IOException e1) {

        System.out.println("IO Exception in "
                + "imageLoad(); " + e.toString());

        JOptionPane.showMessageDialog(null,
                "Error in loading the selected image!",
                "Image Error",
                JOptionPane.ERROR_MESSAGE);
        fileNameTxt.setText("");
    }
} else
    fileNameTxt.setText("No file selected");
}

private File showFileChooserDialog() {

    fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new
            File(System.getProperty("user.home")));
        int status = fileChooser.showOpenDialog(mainWindow);

        File selected_file = null;
        if (status == JFileChooser.APPROVE_OPTION) {
            selected_file = fileChooser.getSelectedFile();
        }
        return selected_file;

    }
});

//Creating action listener to recognise action events when buttons are clicked
rgbButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < imgModelList.size() && i < MAX_IMAGES; i++) {
            ImageModel imgModel = imgModelList.get(i);

            BufferedImage img = imgModel.getRGBImage();
            if (img != null) {
                displayImage(i, img);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No image file is choosen",
                "Image error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
});

grayScaleBtn.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < imgModelList.size() && i < MAX_IMAGES; i++) {
            ImageModel imgModel = imgModelList.get(i);
            BufferedImage img = imgModel.getGrayscaleImage();
            if (img != null)
                displayImage(i, img);
            else {
                JOptionPane.showMessageDialog(null,
                        "Error in loading the grayscale image",
                "Image error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
});

edgeButton.addActionListener(new ActionListener() {

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < imgModelList.size() && i < MAX_IMAGES; i++) {
            ImageModel imgModel = imgModelList.get(i);
            BufferedImage img = imgModel.getEdgeImage();
            if (img != null)
                displayImage(i, img);
            else {
                JOptionPane.showMessageDialog(null,
                        "Error in loading the edge image",
                "Image error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    });
}

    private void displayImage(int position, BufferedImage img) {
        imageLabelList.get(position).setIcon(new ImageIcon(img));
    }

    private void displayImage(BufferedImage img) {
        displayImage(0, img);
    }
}