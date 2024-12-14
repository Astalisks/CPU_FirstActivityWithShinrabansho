import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BouncingBall extends JPanel implements ActionListener {
    private int x = 0;     // ボールのX座標
    private int y = 0;     // ボールのY座標
    private int dx = 2;    // 水平方向の移動量
    private int dy = 2;    // 垂直方向の移動量
    private int ballSize = 30; // ボールの直径
    
    private Color ballColor = Color.RED;  // ボールの色
    
    public BouncingBall() {
        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(ballColor);
        g.fillOval(x, y, ballSize, ballSize);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int width = getWidth();
        int height = getHeight();
        
        x += dx;
        y += dy;
        
        // 左右の壁に当たったら跳ね返る
        if (x < 0) {
            x = 0;
            dx = -dx;
            changeColor();
        } else if (x + ballSize > width) {
            x = width - ballSize;
            dx = -dx;
            changeColor();
        }
        
        // 上下の壁に当たったら跳ね返る
        if (y < 0) {
            y = 0;
            dy = -dy;
            changeColor();
        } else if (y + ballSize > height) {
            y = height - ballSize;
            dy = -dy;
            changeColor();
        }
        
        repaint();
    }
    
    private void changeColor() {
        // ランダムな色に変更
        ballColor = new Color((int)(Math.random()*255), (int)(Math.random()*255), (int)(Math.random()*255));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Ball");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        
        BouncingBall panel = new BouncingBall();
        frame.add(panel);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
