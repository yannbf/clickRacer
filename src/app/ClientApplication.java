package app;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import factory.AbstractFactory;
import factory.FactoryProducer;
import factory.Settings;
import interfaces.Client;
import interfaces.Connection;
import model.Player;

public class ClientApplication extends JPanel implements MouseListener, ActionListener {

	static int goal = 10;
	static boolean inGame = true;
	
	ArrayList<Player> players;
	Connection connection;
	Timer timer;

	String playerName = "";
	String playerId;
	int numberOfClicks = 0;

	public ClientApplication() {

		addMouseListener(this);

		players = new ArrayList<Player>();

		Settings.types clientType = Settings.types.TCP;
		String address = "localhost";
		int port = 5000;

		AbstractFactory clientFactory = FactoryProducer.getFactory(clientType.CLIENT);
		Client client = clientFactory.getClient(clientType);

		try {
			connection = client.connectToServer(Settings.host, Settings.port);

			playerName = JOptionPane.showInputDialog("Digite seu nome!");
			Player player = new Player(playerName);
			playerId = player.getId();

			// Adiciona o jogador corrente na lista e atualiza a dos outros jogadores
			players.add(player);
			connection.send(player.getId() + "," + playerName + "," + 0);

			// Thread para cuidar das atualizações vindas do servidor
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

	// ------------- LÓGICA DO JOGO
	public Player findPlayerById(String id) {
		for (Player p : players) {
			if (p.getId().equals(id)) {
				return p;
			}
		}

		return null;
	}

	// Atualiza os dados de um jogador e caso ele não exista, o adiciona na lista de jogadores
	public void updatePlayers(String id, String name, int clickCount) {
		// Coloquei substring só pra visualizar melhor
		System.out.println("RECEBIDO: " + id.substring(0, 6) + "..." + ", " + name + ", " + clickCount);
		Player p = findPlayerById(id);
		if (p != null) {
			p.numberOfClicks = clickCount;
		} else {
			p = new Player(id, name, clickCount);
			players.add(p);
			JOptionPane.showMessageDialog(null, p.getPlayerName() + " started playing!");
		}
	}
	
	// Checa se há vencedor e termina o jogo caso haja
	public void checkWinner() {
		for (Player p : players) {
			if (p.getNumberOfClicks() >= goal) {
				inGame = false;
				JOptionPane.showMessageDialog(null, "Temos um vencedor! " + p.getPlayerName());
				break;
			}
		}
	}

	// ----------- ATUALIZAÇÃO DA TELA
	@Override // Equivalente ao método RUN
	public void actionPerformed(ActionEvent arg0) {
		if (inGame) {
			checkWinner();
			revalidate();
			repaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int centerOfScreen = getWidth() / 2;
		int height = 50;
		for (Player p : players) {
			g.drawString("Player: " + p.getPlayerName() + " - " + p.getNumberOfClicks() + " clicks",
					centerOfScreen - 120, height);
			height += 20;
		}
		g.drawString("Goal: " + goal, getWidth() - 100, 50);
		g.drawString("Número de clicks: " + String.valueOf(numberOfClicks), centerOfScreen - 100, 300);
	}

	// ---------- LÓGICA DE CLIQUES
	@Override
	public void mouseClicked(MouseEvent e) {
		if (inGame) {
			numberOfClicks++;

			String message = playerId + "," + playerName + "," + numberOfClicks;
			connection.send(message);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	// --------- MAIN METHOD PARA CRIAÇÃO DA TELA
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
