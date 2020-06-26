import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class ResultatVindu extends JFrame {

    private String resultat;

    public ResultatVindu(String resultat) {
        this.resultat = resultat;
        setTitle("Resultat");
        setSize(400,400);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        visResultat();
    }

    private void visResultat() {
        JPanel innhold = new JPanel();
        innhold.setAlignmentX(Component.CENTER_ALIGNMENT);
        innhold.setLayout(new BoxLayout(innhold, BoxLayout.Y_AXIS));

        JLabel resultatTekst = new JLabel("<html>" + resultat.replaceAll("\n","<br>") + "</html>");
        resultatTekst.setBorder(BorderFactory.createTitledBorder("Resultat"));

        JButton avsluttKnapp = new JButton();
        avsluttKnapp.setLayout(null);
        avsluttKnapp.setText("Avslutt");
        avsluttKnapp.addActionListener(e -> lukkVindu());

        innhold.add(resultatTekst);
        innhold.add(avsluttKnapp);
        getContentPane().add(innhold);
    }

    public void lukkVindu() {
        this.dispose();
    }
}
