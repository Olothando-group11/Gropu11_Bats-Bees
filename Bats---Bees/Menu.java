import java.awt.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Menu extends JPanel implements ActionListener{
	static boolean game_state = false;
	int boardWidth = 360;
	int boardHeight = 640;
	Image menuImg = new ImageIcon(getClass().getResource("menu.png")).getImage();
	JButton batStart = new JButton();
	JButton beeStart = new JButton();
	
	Menu(){
		
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		setBackground(Color.black);
		batStart.setBounds(130,320,100,30);
		batStart.setText("Select");
		batStart.setFont (new Font("Times New Roman", Font.BOLD, 25));
		batStart.addActionListener(this);
		batStart.setBackground(Color.lightGray);
		batStart.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(null);
		this.add(batStart);
		
		beeStart.setBounds(130,540,100,30);
		beeStart.setText("Select");
		beeStart.setFont (new Font("Times New Roman", Font.BOLD, 25));
		beeStart.addActionListener(this);
		beeStart.setBackground(Color.lightGray);
		beeStart.setBorder(BorderFactory.createEtchedBorder());
		this.add(beeStart);
		
	}

public void paintComponent(Graphics g){
	super.paintComponent(g);
	g.drawImage(menuImg,0,0, boardWidth, boardHeight, null);
}

public void actionPerformed(ActionEvent e){
	if (e.getSource() == batStart){
		BatsBees batsbees = new BatsBees();
		App.frame.add(batsbees);
		App.frame.pack();
		batsbees.requestFocus();
		App.frame.setVisible(true);
		batStart.setVisible(false);
		beeStart.setVisible(false);}
		
	if (e.getSource() == beeStart){
		JustBees justbees = new JustBees();
		App.frame.add(justbees);
		App.frame.pack();
		justbees.requestFocus();
		App.frame.setVisible(true);
		beeStart.setVisible(false);
		batStart.setVisible(false);}
		
}

}