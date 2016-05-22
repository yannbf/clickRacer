package app;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import factory.AbstractFactory;
import factory.FactoryProducer;
import factory.Settings;
import interfaces.Client;
import interfaces.Connection;

public class ClientApplication extends JPanel implements MouseListener, ActionListener {

	static int goal = 10;
	static boolean inGame = true;

	public int numberOfClicks = 0;
	
	Connection connection;
	String playerName = "";
	String winner = "fulanim";
	Timer timer;

	public ClientApplication() {

		addMouseListener(this);

		Settings clientType = Settings.TCP;
		String address = "localhost";
		int port = 5000;

		AbstractFactory clientFactory = FactoryProducer.getFactory(Settings.CLIENT);
		Client client = clientFactory.getClient(clientType);

		try {
			connection = client.connectToServer(address, port);

			playerName = JOptionPane.showInputDialog("Digite seu nome!");
			ClientHandler rc = new ClientHandler(this, connection);
			rc.start();

			// Thread pra atualizar a tela. Vai chamar ActionListener a cada
			// 50ms
			timer = new Timer(50, this);
			timer.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

   	    int meio = getWidth()/2;
        g.drawString("Player: " + playerName, meio-30, 50);
        g.drawString("Goal: " + goal,getWidth()-100, 50);
        g.drawString("Número de clicks: "+ String.valueOf(numberOfClicks), meio-100, 300);
    }

	public void click(String message) {
		numberOfClicks++;
	}

	// Método para pintura
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(inGame){
			revalidate();
			repaint();
		} else {
			JOptionPane.showMessageDialog(null, "Temos um vencedor! " + winner);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Até então não serve pra nada essa mensagem
		String message = playerName + " clicou ";
		connection.send(message + numberOfClicks + " vezes!");
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	//----------------------------------------
	
	private static void createAndShowUI() {
		JFrame frame = new JFrame("Joguinho");
		frame.getContentPane().add(new ClientApplication());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 600);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}
}
