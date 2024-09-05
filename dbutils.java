import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGame extends JFrame implements ActionListener {
    private final int WIDTH = 600;
    private final int HEIGHT = 600;
    private final int DOT_SIZE = 10;
    private final int DELAY = 100;

    private boolean isRunning = false;
    private boolean isGameOver = false;
    private int score = 0;

    private Random random = new Random();

    private ArrayList<int[]> snakeBody = new ArrayList<>();
    private int[] apple = new int[2];

    private Timer timer;

    public SnakeGame() {
        setTitle("Snake Game");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        add(panel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (isRunning) {
                    int keyCode = e.getKeyCode();
                    if (keyCode == KeyEvent.VK_UP && snakeBody.get(0)[1] != 1) {
                        snakeBody.get(0)[1] = -1;
                    } else if (keyCode == KeyEvent.VK_DOWN && snakeBody.get(0)[1] != -1) {
                        snakeBody.get(0)[1] = 1;
                    } else if (keyCode == KeyEvent.VK_LEFT && snakeBody.get(0)[0] != 1) {
                        snakeBody.get(0)[0] = -1;
                    } else if (keyCode == KeyEvent.VK_RIGHT && snakeBody.get(0)[0] != -1) {
                        snakeBody.get(0)[0] = 1;
                    }
                }
            }
        });

        timer = new Timer(DELAY, this);
        startGame();
    }

    public void startGame() {
        isRunning = true;
        isGameOver = false;
        score = 0;
        snakeBody.clear();
        snakeBody.add(new int[]{WIDTH / 2, HEIGHT / 2});
        generateApple();
        timer.start();
    }

    public void generateApple() {
        apple[0] = random.nextInt(WIDTH / DOT_SIZE) * DOT_SIZE;
        apple[1] = random.nextInt(HEIGHT / DOT_SIZE) * DOT_SIZE;
    }

    public void checkCollision() {
        if (snakeBody.get(0)[0] < 0 || snakeBody.get(0)[0] >= WIDTH ||
                snakeBody.get(0)[1] < 0 || snakeBody.get(0)[1] >= HEIGHT) {
            isGameOver = true;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeBody.get(0)[0] == snakeBody.get(i)[0] && snakeBody.get(0)[1] == snakeBody.get(i)[1]) {
                isGameOver = true;
            }
        }
    }

    public void updateSnake() {
        int[] head = new int[]{snakeBody.get(0)[0] + snakeBody.get(0)[0], snakeBody.get(0)[1] + snakeBody.get(0)[1]};
        snakeBody.add(0, head);

        if (!isEatingApple()) {
            snakeBody.remove(snakeBody.size() - 1);
        } else {
            score++;
            generateApple();
        }
    }

    public boolean isEatingApple() {
        return snakeBody.get(0)[0] == apple[0] && snakeBody.get(0)[1] == apple[1];
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (int[] dot : snakeBody) {
            g.setColor(Color.GREEN);
            g.fillRect(dot[0], dot[1], DOT_SIZE, DOT_SIZE);
        }

        g.setColor(Color.RED);
        g.fillRect(apple[0], apple[1], DOT_SIZE, DOT_SIZE);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 10, 20);

        if (isGameOver) {
            g.drawString("Game Over", WIDTH / 2 - 50, HEIGHT / 2 - 20);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning && !isGameOver) {
            updateSnake();
            checkCollision();
            repaint();
        }
    }

    public static void main(String[] args) {
        SnakeGame game = new SnakeGame();
        game.setVisible(true);
    b}
}
