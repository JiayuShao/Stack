import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.sound.sampled.*;
/**
Name: Ryan Shao
Date: June 10
Course: ICS 4U
Program: Stack Game
*/
public class StackGame extends JPanel implements ActionListener, KeyListener {
	// Declare and Initialize Globle variables
	private double aX = 205, aY = 305, bX = 355, bY = 410, cX = 205, cY = 515, dX = 55, dY = 410, eX = 55, eY = 485,
			fX = 205, fY = 590, gX = 355, gY = 485, hX = 55, hY = 720, iX = 205, iY = 720, jX = 355, jY = 720;
	private boolean start = false,restart = false;
	private int cubeNumLeft = 0, cubeNumRight = 0,score = 0,rnd,min = 3,countShiftColor=0,rndColor=0,bR, bG, bB;
	private char colorShift = 'U',trigger ='C',dir = 'R';
	private Color greenSurface,greenDark,greenLight,greenBot1,greenBot2;	
	private Timer cubeTimer;
	private CubeAbstract[] right = new CubeRight[100],left = new CubeLeft[100];
	private Graphics2D g2;
	private Clip clip;
	private ImageIcon backGround;
	private JFrame frame = new JFrame();
	private JLabel stackName = new JLabel("STACK"),instruction = new JLabel("Instruction:\n Press <Space> To Stack The Cube"),inputName = new JLabel("Please Input Your Name:"),incorrect = new JLabel("*You Have To Input Something, Please Try Again.");
	private JTextField name;
	private String stringName;
	private JLabel highscoreName = new JLabel("HIGHSCORE"),scoreBoard;
	private JTextArea highscoreBoard;
	private JButton exitButton,startbtn;
	private File scoreFile = new File("Highscore.txt");
	private BufferedWriter out;
	private BufferedReader in;

	// Constructor
	public StackGame() {
		//Random picture background
		rnd = (int) (Math.random() * 6);
		backGround = new ImageIcon("backGroundPics/backgroundPic" + rnd + ".png");
		setLayout(null);
		setFocusable(true);
		cubeTimer = new Timer(10, this);
		
		// Initialize array of cubes
		for (int i = 0; i < right.length; i++) {
			right[i] = new CubeRight();
			left[i] = new CubeLeft();
		}

		// Set TextField Name
		name = new JTextField(20);
		name.setBounds(200, 222, 190, 25);

		// Set Input Name
		inputName.setBounds(10, 210, 350, 50);
		inputName.setFont(new Font("Britannic Bold", Font.PLAIN, 17));
		inputName.setForeground(Color.WHITE);
		inputName.setOpaque(false);

		// Set the scoreBoard
		scoreBoard = new JLabel(score + "");
		scoreBoard.setBounds(170, 50, 180, 180);
		scoreBoard.setFont(new Font("Britannic Bold", Font.PLAIN, 120));
		scoreBoard.setForeground(Color.WHITE);
		scoreBoard.setOpaque(false);

		// Set stack name
		stackName.setBounds(90, 60, 300, 150);
		stackName.setFont(new Font("Britannic Bold", Font.PLAIN, 80));
		stackName.setForeground(Color.WHITE);
		scoreBoard.setOpaque(false);

		// Set highscore name
		highscoreName.setBounds(75, 0, 300, 150);
		highscoreName.setFont(new Font("Britannic Bold", Font.PLAIN, 50));
		highscoreName.setForeground(Color.RED);
		highscoreName.setOpaque(false);

		// Set instruction
		instruction.setBounds(40, 80, 350, 250);
		instruction.setFont(new Font("Britannic Bold", Font.PLAIN, 17));
		instruction.setForeground(Color.WHITE);
		instruction.setOpaque(false);

		// Set incorrect
		incorrect.setBounds(120, 230, 350, 50);
		incorrect.setFont(new Font("Britannic Bold", Font.PLAIN, 13));
		incorrect.setForeground(Color.RED);
		incorrect.setOpaque(false);

		// Set the Start button
		startbtn = new JButton();
		startbtn.setIcon(new ImageIcon("play.png"));
		startbtn.setBorderPainted(false);
		startbtn.setContentAreaFilled(false);
		startbtn.setOpaque(false);
		startbtn.setBounds(150, 350, 100, 100);
		startbtn.addActionListener(this);

		// Set the Start button
		exitButton = new JButton();
		exitButton.setIcon(new ImageIcon("exitButton.png"));
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setOpaque(false);
		exitButton.setBounds(150, 600, 100, 100);
		exitButton.addActionListener(this);

		// Set Scoreboard JTextArea
		highscoreBoard = new JTextArea(30, 52);
		highscoreBoard.setBounds(55, 120, 300, 500);
		highscoreBoard.setEditable(false);
		highscoreBoard.setBackground(Color.BLACK);
		highscoreBoard.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		highscoreBoard.setFont(new Font("Courier New", Font.PLAIN, 17));
		highscoreBoard.setForeground(Color.WHITE);
		highscoreBoard.setText(String.format("\n%-2s%-20s%-20s\n", "", "Name", "Score"));

		// Set the Game
		frame.setContentPane(this);
		frame.setTitle("Stack");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(backGround.getIconWidth(), backGround.getIconHeight());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		frame.add(startbtn);
		frame.add(stackName);
		frame.add(instruction);
		frame.add(inputName);
		frame.add(name);

		// Initialize a sound clip
		try {
			// Open an audio input stream.
			File soundFile = new File("stackGame1.wav");
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			// Get a sound clip resource.
			clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	// Paint Component
	public void paintComponent(Graphics g) {
		//Color for the base
		bR = 140;
		bG = 172;
		bB = 77;
		greenSurface = new Color(bR, bG, bB);
		greenDark = new Color(bR - 71, bG - 80, bB - 28);
		greenLight = new Color(bR - 56, bG - 55, bB - 18);
		greenBot1 = new Color(50, 63, 39);
		greenBot2 = new Color(58, 73, 44);

		super.paintComponent(g);
		g2 = (Graphics2D) g;

		// Set the background
		g2.drawImage(backGround.getImage(), 0, 0, this);

		// Set the location of the base
		g2.setColor(greenSurface);
		Polygon surface = new Polygon();
		surface.addPoint((int) aX, (int) aY);
		surface.addPoint((int) bX, (int) bY);
		surface.addPoint((int) cX, (int) cY);
		surface.addPoint((int) dX, (int) dY);
		g2.fillPolygon(surface);
		
		g2.setColor(greenDark);
		Polygon surface2 = new Polygon();
		surface2.addPoint((int) cX, (int) cY);
		surface2.addPoint((int) dX, (int) dY);
		surface2.addPoint((int) eX, (int) eY);
		surface2.addPoint((int) fX, (int) fY);
		g2.fillPolygon(surface2);

		g2.setColor(greenLight);
		Polygon surface3 = new Polygon();
		surface3.addPoint((int) bX, (int) bY);
		surface3.addPoint((int) cX, (int) cY);
		surface3.addPoint((int) fX, (int) fY);
		surface3.addPoint((int) gX, (int) gY);
		g2.fillPolygon(surface3);

		g2.setColor(greenBot1);
		Polygon bottomLeft = new Polygon();
		bottomLeft.addPoint((int) eX, (int) eY);
		bottomLeft.addPoint((int) fX, (int) fY);
		bottomLeft.addPoint((int) iX, (int) iY);
		bottomLeft.addPoint((int) hX, (int) hY);
		g2.fillPolygon(bottomLeft);

		g2.setColor(greenBot2);
		Polygon bottomRight = new Polygon();
		bottomRight.addPoint((int) fX, (int) fY);
		bottomRight.addPoint((int) gX, (int) gY);
		bottomRight.addPoint((int) jX, (int) jY);
		bottomRight.addPoint((int) iX, (int) iY);
		g2.fillPolygon(bottomRight);

		// Draw
		if (start == true) {
			for (int i = 0; i < left.length; i++) {
				if (i < cubeNumRight) {
					right[i].draw(g2);
				}
				if (i < cubeNumLeft) {
					left[i].draw(g2);
				}
			}

			if (cubeNumRight == cubeNumLeft) {
				right[cubeNumRight].draw(g2);
			} 
			else if (cubeNumRight != cubeNumLeft) {
				left[cubeNumLeft].draw(g2);
			}
		}

	}

	// Main Method
	public static void main(String[] args) {
		new StackGame();
	}

	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyPressed(KeyEvent e) {
		// When the keyspace is pressed
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			//Sounds start
			clip.start();
			clip.setFramePosition(0);
			//Cute the cube
			cut(cubeNumRight);

			if (restart == false) {
				//Decelerate the existing cubes
				for (int i = 0; i < right.length; i++) {
					if (i <= cubeNumRight) {
						right[i].decelerate();
					}
				}
				for (int i = 0; i < left.length; i++) {
					if (i <= cubeNumLeft) {
						left[i].decelerate();
					}
				}
				//Decelerate the base
				aY = aY + 19;
				bY = bY + 19;
				cY = cY + 19;
				dY = dY + 19;
				eY = eY + 19;
				fY = fY + 19;
				gY = gY + 19;
				hY = hY + 19;
				iY = iY + 19;
				jY = jY + 19;
				
				//Set Color for the cubes coming from right
				if (dir == 'R') {
					trigger='C';
					//Green Color
					if(rndColor==0){
						if (right[cubeNumRight].getG() > 240) {
							colorShift = 'D';
							countShiftColor++;
						} else if (right[cubeNumRight].getG() < 160) {
							colorShift = 'U';
							countShiftColor++;
							if(countShiftColor==2){
								left[cubeNumLeft].setRGB(132, 177,209);
								trigger='I';
							}
						}
					}
					//Blue Color
					else if(rndColor==1){
						if (right[cubeNumRight].getB() > 240) {
							colorShift = 'D';
							countShiftColor++;
						} else if (right[cubeNumRight].getB() < 200) {
							colorShift = 'U';
							countShiftColor++;
							if(countShiftColor==4){
								left[cubeNumLeft].setRGB(188,91,97);
								trigger='I';
							}
						}
					}
					//Red Color
					else{
						if (right[cubeNumRight].getR() > 240) {
							colorShift = 'D';
							countShiftColor++;
						} else if (right[cubeNumRight].getR() < 185) {
							colorShift = 'U';
							countShiftColor++;
							if(countShiftColor==6){
								left[cubeNumLeft].setRGB(150,182,87);
								trigger='I';
							}
						}
					}
					if(countShiftColor==0||countShiftColor==6){
						rndColor=0;
						countShiftColor=0;
					}
					else if(countShiftColor==2){
						rndColor=1;
					}
					else if(countShiftColor==4){
						rndColor=2;
					}
					// Color switching up and down
					if (colorShift == 'U' && trigger=='C'){
						left[cubeNumLeft].setRGB(right[cubeNumRight].getR() + 10, right[cubeNumRight].getG() + 10,
								right[cubeNumRight].getB() + 10);
					}
					else if(colorShift == 'D' && trigger=='C'){
						left[cubeNumLeft].setRGB(right[cubeNumRight].getR() - 10, right[cubeNumRight].getG() - 10,
								right[cubeNumRight].getB() - 10);
					}

					cubeNumRight = cubeNumRight + 1;
					right[cubeNumRight].setSpeed(left[cubeNumLeft].getSpeed() + 0.1);
					dir = 'L';
				}
				//Cubes coming from the left side
				else {
					trigger='C';
					if(rndColor==0){
						if (left[cubeNumLeft].getG() > 240) {
							colorShift = 'D';
							countShiftColor++;
						} else if (left[cubeNumLeft].getG() < 160) {
							colorShift = 'U';
							countShiftColor++;
							if(countShiftColor==2){
								right[cubeNumRight].setRGB(132, 177,209);
								trigger='I';
							}
						}
					}
					else if(rndColor==1){
						if (left[cubeNumLeft].getB() > 240) {
							colorShift = 'D';
							countShiftColor++;
						} else if (left[cubeNumLeft].getB() < 200) {
							colorShift = 'U';
							countShiftColor++;
							if(countShiftColor==4){
								right[cubeNumRight].setRGB(188,91,97);
								trigger='I';
							}
						}
					}
					else{
						if (left[cubeNumLeft].getR() > 240) {
							colorShift = 'D';
							countShiftColor++;
						} else if (left[cubeNumLeft].getR() < 185) {
							colorShift = 'U';
							countShiftColor++;
							if(countShiftColor==6){
								right[cubeNumRight].setRGB(150, 182,87);
								trigger='I';
							}
						}
					}
					if(countShiftColor==0||countShiftColor==6){
						rndColor=0;
						countShiftColor=0;
					}
					else if(countShiftColor==2){
						rndColor=1;
					}
					else if(countShiftColor==4){
						rndColor=2;
					}
					if (colorShift == 'U' && trigger=='C')
						right[cubeNumRight].setRGB(left[cubeNumLeft].getR() + 10, left[cubeNumLeft].getG() + 10,
								left[cubeNumLeft].getB() + 10);
					else if((colorShift == 'D' && trigger=='C'))
						right[cubeNumRight].setRGB(left[cubeNumLeft].getR() - 10, left[cubeNumLeft].getG() - 10,
								left[cubeNumLeft].getB() - 10);

					cubeNumLeft = cubeNumLeft + 1;
					left[cubeNumLeft].setSpeed(right[cubeNumRight].getSpeed() + 0.1);
					dir = 'R';
				}
				//Count score
				score++;
				if (score == 10) {
					scoreBoard.setBounds(125, 50, 180, 180);
				}
			} else {
				restart = false;
			}
			scoreBoard.setText(score + "");
			repaint();

		}
	}

	// Action Performed
	public void actionPerformed(ActionEvent e) {
		//cubeTimer
		if (e.getSource() == cubeTimer) {
			// Move the cube.
			if (dir == 'R')
				right[cubeNumRight].move();
			else
				left[cubeNumLeft].move();
		}
		//Exit Button
		if (e.getSource() == exitButton) {
			JOptionPane.showMessageDialog(null, "Thank You For Playing.", "Stack", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		//Start button
		if (e.getSource() == startbtn) {
			stringName = "";
			// Eliminate Non Character Letters
			for (int i = 0; i < name.getText().length(); i++) {
				if (Character.isLetter(name.getText().charAt(i)) == true) {
					stringName += name.getText().charAt(i);
				}
			}
			if (stringName.equals("")) {
				frame.add(incorrect);
			} else {
				stringName = "";
				for (int i = 0; i < name.getText().length(); i++) {
					stringName += name.getText().charAt(i);
				}
				frame.add(scoreBoard);
				start = true;
				cubeTimer.start();
				frame.remove(incorrect);
				frame.remove(startbtn);
				frame.remove(instruction);
				frame.remove(stackName);
				frame.remove(name);
				frame.remove(inputName);
				addKeyListener(this);
			}
		}
		repaint();
	}

	// Cut Method
	public void cut(int i) {
		if (cubeNumRight == 0 && dir == 'R') {
			if (right[0].getaX() <= aX && right[0].getaX() > dX + min) {
				right[0].setcX(cX);
				right[0].setcY(cY - 20);
				right[0].setdX(dX);
				right[0].setdY(dY - 20);
				right[0].seteX(eX);
				right[0].seteY(dY);
				right[0].setfX(fX);
				right[0].setfY(cY);

				left[0].setCube(right[0].getaX() - right[0].getaX(), right[0].getbX() - right[0].getaX(),
						right[0].getcX() - right[0].getaX(), right[0].getdX() - right[0].getaX(),
						right[0].geteX() - right[0].getaX(), right[0].getfX() - right[0].getaX(),
						right[0].getgX() - right[0].getaX(), right[0].getaY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getbY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getcY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getdY() - right[0].getaX() * 7 / 10 - 19,
						right[0].geteY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getfY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getgY() - right[0].getaX() * 7 / 10 - 19);

			} else if (right[0].getaX() < bX - min && right[0].getaX() > aX) {
				right[0].setaX(aX);
				right[0].setaY(aY - 20);
				right[0].setbX(bX);
				right[0].setbY(bY - 20);
				right[0].setgX(bX);
				right[0].setgY(bY);

				left[0].setCube(right[0].getaX() - right[0].getaX(), right[0].getbX() - right[0].getaX(),
						right[0].getcX() - right[0].getaX(), right[0].getdX() - right[0].getaX(),
						right[0].geteX() - right[0].getaX(), right[0].getfX() - right[0].getaX(),
						right[0].getgX() - right[0].getaX(), right[0].getaY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getbY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getcY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getdY() - right[0].getaX() * 7 / 10 - 19,
						right[0].geteY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getfY() - right[0].getaX() * 7 / 10 - 19,
						right[0].getgY() - right[0].getaX() * 7 / 10 - 19);
			} else {
				theEnd();
			}

		} else if (dir == 'L') {

			if (left[cubeNumLeft].getbX() < right[cubeNumLeft].getbX()
					&& left[cubeNumLeft].getbX() > right[cubeNumLeft].getaX() + min) {
				left[cubeNumLeft].setaX(right[cubeNumLeft].getaX());
				left[cubeNumLeft].setaY(right[cubeNumLeft].getaY() - 20);
				left[cubeNumLeft].setdX(right[cubeNumLeft].getdX());
				left[cubeNumLeft].setdY(right[cubeNumLeft].getdY() - 20);
				left[cubeNumLeft].seteX(right[cubeNumLeft].geteX());
				left[cubeNumLeft].seteY(right[cubeNumLeft].geteY() - 20);

				double value = 405 - left[cubeNumLeft].getaX();
				right[cubeNumLeft + 1].setCube(left[cubeNumLeft].getaX() + value, left[cubeNumLeft].getbX() + value,
						left[cubeNumLeft].getcX() + value, left[cubeNumLeft].getdX() + value,
						left[cubeNumLeft].geteX() + value, left[cubeNumLeft].getfX() + value,
						left[cubeNumLeft].getgX() + value, left[cubeNumLeft].getaY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getbY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getcY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getdY() - value * 7 / 10 - 19,
						left[cubeNumLeft].geteY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getfY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getgY() - value * 7 / 10 - 19);

			}

			else if (left[cubeNumLeft].getaX() < right[cubeNumLeft].getbX() - min
					&& left[cubeNumLeft].getaX() >= right[cubeNumLeft].getaX()) {
				left[cubeNumLeft].setbX(right[cubeNumLeft].getbX());
				left[cubeNumLeft].setbY(right[cubeNumLeft].getbY() - 20);
				left[cubeNumLeft].setcX(right[cubeNumLeft].getcX());
				left[cubeNumLeft].setcY(right[cubeNumLeft].getcY() - 20);
				left[cubeNumLeft].setgX(right[cubeNumLeft].getgX());
				left[cubeNumLeft].setgY(right[cubeNumLeft].getgY() - 20);
				left[cubeNumLeft].setfX(right[cubeNumLeft].getfX());
				left[cubeNumLeft].setfY(right[cubeNumLeft].getfY() - 20);

				double value = 405 - left[cubeNumLeft].getaX();
				right[cubeNumLeft + 1].setCube(left[cubeNumLeft].getaX() + value, left[cubeNumLeft].getbX() + value,
						left[cubeNumLeft].getcX() + value, left[cubeNumLeft].getdX() + value,
						left[cubeNumLeft].geteX() + value, left[cubeNumLeft].getfX() + value,
						left[cubeNumLeft].getgX() + value, left[cubeNumLeft].getaY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getbY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getcY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getdY() - value * 7 / 10 - 19,
						left[cubeNumLeft].geteY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getfY() - value * 7 / 10 - 19,
						left[cubeNumLeft].getgY() - value * 7 / 10 - 19);
			} else {
				theEnd();
			}

		} else if (dir == 'R') {
			if (right[cubeNumRight].getaX() <= left[cubeNumRight - 1].getaX()
					&& right[cubeNumRight].getaX() > left[cubeNumRight - 1].getdX() + min) {
				right[cubeNumRight].setcX(left[cubeNumLeft - 1].getcX());
				right[cubeNumRight].setcY(left[cubeNumLeft - 1].getcY() - 20);
				right[cubeNumRight].setdX(left[cubeNumLeft - 1].getdX());
				right[cubeNumRight].setdY(left[cubeNumLeft - 1].getdY() - 20);
				right[cubeNumRight].seteX(left[cubeNumLeft - 1].geteX());
				right[cubeNumRight].seteY(left[cubeNumLeft - 1].geteY() - 20);
				right[cubeNumRight].setfX(left[cubeNumLeft - 1].getfX());
				right[cubeNumRight].setfY(left[cubeNumLeft - 1].getfY() - 20);

				left[cubeNumLeft].setCube(right[cubeNumRight].getaX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getbX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getcX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getdX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].geteX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getfX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getgX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getaY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getbY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getcY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getdY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].geteY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getfY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getgY() - right[cubeNumRight].getaX() * 7 / 10 - 19);
			}

			else if (right[cubeNumRight].getdX() < left[cubeNumRight - 1].getaX() - min
					&& right[cubeNumRight].getdX() > left[cubeNumLeft - 1].getdX()) {
				right[cubeNumRight].setaX(left[cubeNumLeft - 1].getaX());
				right[cubeNumRight].setaY(left[cubeNumLeft - 1].getaY() - 20);
				right[cubeNumRight].setbX(left[cubeNumLeft - 1].getbX());
				right[cubeNumRight].setbY(left[cubeNumLeft - 1].getbY() - 20);
				right[cubeNumRight].setgX(left[cubeNumLeft - 1].getgX());
				right[cubeNumRight].setgY(left[cubeNumLeft - 1].getgY() - 20);

				left[cubeNumLeft].setCube(right[cubeNumRight].getaX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getbX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getcX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getdX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].geteX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getfX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getgX() - right[cubeNumRight].getaX(),
						right[cubeNumRight].getaY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getbY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getcY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getdY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].geteY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getfY() - right[cubeNumRight].getaX() * 7 / 10 - 19,
						right[cubeNumRight].getgY() - right[cubeNumRight].getaX() * 7 / 10 - 19);
			}

			else {
				theEnd();
			}

		}
	}
	//When the player died method
	public void theEnd() {
		//Stop Timer
		cubeTimer.stop();
		int option = JOptionPane.showConfirmDialog(null,
				"Game Over! You Got " + score + " Points! Would You Like To Try Again?", "Stack",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		//If player pressed restart
		if (option == JOptionPane.YES_OPTION) {
			restart = true;
			cubeNumRight = 0;
			cubeNumLeft = 0;
			aX = 205;
			aY = 305;
			bX = 355;
			bY = 410;
			cX = 205;
			cY = 515;
			dX = 55;
			dY = 410;
			eX = 55;
			eY = 485;
			fX = 205;
			fY = 590;
			gX = 355;
			gY = 485;
			hX = 55;
			hY = 720;
			iX = 205;
			iY = 720;
			jX = 355;
			jY = 720;
			right = new CubeRight[100];
			left = new CubeLeft[100];
			score = 0;
			dir = 'R';
			rnd = (int) (Math.random() * 6);
			scoreBoard.setBounds(170, 50, 180, 180);
			backGround = new ImageIcon("backGroundPics/backgroundPic" + rnd + ".png");
			for (int i1 = 0; i1 < right.length; i1++) {
				right[i1] = new CubeRight();
				left[i1] = new CubeLeft();
			}
			cubeTimer.start();
			rndColor=0;
			countShiftColor=0;
			colorShift='U';
		} 
		//If player decide not to restart, display the highscore
		else {
			try {
				out = new BufferedWriter(new FileWriter(scoreFile,true));
				out.write(String.format("%-2s%-20s%-20s", "",stringName, score));
				out.newLine();

				out.close();
			} catch (IOException ie) {
			}

			// Read the score from the file
			String data;
			try {
				in = new BufferedReader(new FileReader(scoreFile));
				data = in.readLine();

				while (data != null) {
					highscoreBoard.append(data);
					highscoreBoard.append("\n");
					data = in.readLine();
				}
				in.close();
			} catch (IOException ioe) {
			}
			restart = false;
			frame.add(highscoreName);
			start = false;
			cubeTimer.stop();
			frame.remove(scoreBoard);
			frame.add(highscoreBoard);
			removeKeyListener(this);
			frame.add(exitButton);
		}
	}
}
