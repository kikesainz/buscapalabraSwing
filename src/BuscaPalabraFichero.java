import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class BuscaPalabraFichero extends JFrame {
    private JTextField searchField;
    private JTextArea resultsArea;
    private JButton searchButton, openButton;
    private File selectedFile;
    private JPanel panelCentral;
    JLabel etiqueta;

    public BuscaPalabraFichero() {
        setTitle("Buscador de palabra en ficheros");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior para los controles
        JPanel controlPanel = new JPanel();
        openButton = new JButton("Abrir archivo");
        searchField = new JTextField(20);
        JLabel searchLabel = new JLabel("Palabra a buscar:");
        searchButton = new JButton("Buscar");
        searchButton.setEnabled(false);  // Deshabilitar hasta que un archivo esté seleccionado

        openButton.addActionListener(this::abrirFileChooser);
        searchButton.addActionListener(this::performSearch);

        controlPanel.add(openButton);
        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        add(controlPanel, BorderLayout.NORTH);
        
        //panel central: lo creamos y lo situamos en el centro del frame
        panelCentral = new JPanel();
      	etiqueta = new JLabel("Fichero a analizar:");
        panelCentral.add(etiqueta);
      
       add(panelCentral, BorderLayout.CENTER);

        // Área de texto para resultados
        resultsArea = new JTextArea();
       resultsArea.setPreferredSize(new Dimension(500,500));
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
       
        add(scrollPane, BorderLayout.SOUTH);
        
        setVisible(true);
    }

    private void abrirFileChooser(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            etiqueta.setText("Fichero a Analizar: " + selectedFile.getAbsolutePath());
            resultsArea.setText("Archivo seleccionado: " + selectedFile.getAbsolutePath() + "\n");
            searchButton.setEnabled(true);  // Habilitar el botón de búsqueda
        }
    }

    private void performSearch(ActionEvent e) {
        String searchWord = searchField.getText().trim();
        int count = 0;
        if (selectedFile != null && !searchWord.isEmpty()) {
        	try {
				FileReader fr = new FileReader(selectedFile);
				BufferedReader bf  = new BufferedReader(fr);
				String linea;
				while ((linea = bf.readLine()) !=null) {
					if (linea.contains(searchWord)) count++;
				}
			} catch (FileNotFoundException ex) {
				// TODO Auto-generated catch block
				 JOptionPane.showMessageDialog(this, "Archivo no encontrado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "Error al leer de fichero: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}

        }
        
        resultsArea.append("Palabra: " + searchWord + " encontradada " +count +" veces en fichero: " + selectedFile.getName() );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BuscaPalabraFichero::new);
    }
}

