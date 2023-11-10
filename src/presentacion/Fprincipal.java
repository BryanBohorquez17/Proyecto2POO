package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Fprincipal extends JFrame implements ActionListener {
    private JLabel lResultado;
    private JLabel lResultado2;
    private JButton jBotones[][] = new JButton[8][8];
    private String matriz[][] = {
    		
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""},
        {"", "", "", "", "", "", "", ""}
    };

    public Fprincipal() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Othello POO");
        this.setSize(800, 800);
        this.setLayout(new BorderLayout());

        // Etiquetas de resultado
        this.lResultado = new JLabel("0", SwingConstants.LEFT);
        this.lResultado2 = new JLabel("0", SwingConstants.RIGHT);

        // Panel para las etiquetas de resultado
        JPanel pEtiquetas = new JPanel(new BorderLayout());
        pEtiquetas.add(lResultado, BorderLayout.WEST);
        pEtiquetas.add(lResultado2, BorderLayout.EAST);
        this.add(pEtiquetas, BorderLayout.NORTH);

        // Panel para los botones
        JPanel pBotones = new JPanel();
        pBotones.setLayout(new GridLayout(8, 8));
        this.add(pBotones, BorderLayout.CENTER);

        // Agregar dos fichas, una blanca y una negra, en el centro
        matriz[3][3] = "B"; // Ficha negra
        matriz[3][4] = "N"; // Ficha blanca

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.jBotones[i][j] = new JButton(this.matriz[i][j]);
                pBotones.add(this.jBotones[i][j]);
                this.jBotones[i][j].setBackground(Color.GREEN);
                this.jBotones[i][j].addActionListener(this);
            }
        }
    }

    public static void main(String[] args) {
        Fprincipal fprincipal = new Fprincipal();
        fprincipal.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }
}
