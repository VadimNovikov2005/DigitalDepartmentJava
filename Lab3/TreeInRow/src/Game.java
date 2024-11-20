import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private static final int delta = 50;
    private static final int width = delta * 8;
    private static final int height = width + 100;

    private static int score = 0;
    private int cursorX = 0;
    private int cursorY = 100;
    private static  ArrayList<Integer> circleX = new ArrayList<>();
    private static  ArrayList<Integer> circleY = new ArrayList<>();
    private static ArrayList<Color> colorArrayList = new ArrayList<>();
    private static final Color[] colorArray = {Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.GRAY};

    static {
        int x = 0, y = 100;
        for (int i = 1; i < 65; i++) {
            circleX.add(x);
            if (i > 1 && i % 8 == 0) {
                x = 0;
            }
            else {
                x += delta;
            }
        }
        for (int i = 1; i < 65; i++) {
            circleY.add(y);
            if(i > 1 && i % 8 == 0) {
                y += delta;
            }
        }
        GetColor();
    }

    public Game() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(250, this);
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tree-in-row");
        Game game = new Game();
        frame.add(game);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public static void GetColor() {
        Random random = new Random();
        for (int i = 0; i < 64; i++) {
            colorArrayList.add(colorArray[random.nextInt(colorArray.length)]);
        }
    }

    public static boolean FindEmptyElem() {
        for (Color color : colorArrayList) {
            if (color == Color.WHITE) {
                return true;
            }
        }
        return false;
    }

    public static void Swap(int index1, int index2) {
        Color buff1 = colorArrayList.get(index1);
        Color buff2 = colorArrayList.get(index2);
        colorArrayList.remove(index1);
        colorArrayList.add(index1, buff2);
        colorArrayList.remove(index2);
        colorArrayList.add(index2, buff1);
    }

    public static void GenerateCircle() {
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            if (colorArrayList.get(i) == Color.WHITE) {
                colorArrayList.remove(i);
                colorArrayList.add(i, colorArray[random.nextInt(colorArray.length)]);
            }
        }
    }

    public static int WriteRow(int variable, int start, int end, int step, int[] array, int index) {
        if (colorArrayList.get(variable - step) != Color.WHITE) {
            for (int j = start; j < end; j++) {
                array[index] = variable - j * step;
                index++;
            }
        }
        return index;
    }

    public static boolean FindRow(boolean delete) {
        int[] indexArray = new int[64*2];
        int index = 0;
        int lenRow = 1;
        Color color = colorArrayList.getFirst();
        int t = 1;
        while (t < colorArrayList.size()) {
            if (circleX.get(t) > 0) {
                if (colorArrayList.get(t) == color) {
                    lenRow++;
                } else {
                    if (lenRow >= 3) {
                        index = WriteRow(t, 1,lenRow + 1, 1, indexArray, index);
                    }
                    color = colorArrayList.get(t);
                    lenRow = 1;
                }
            } else {
                if (lenRow >= 3) {
                    index = WriteRow(t, 1, lenRow + 1, 1, indexArray, index);
                }
                lenRow = 1;
                color = colorArrayList.get(t);
            }
            t++;
        }
        t--;
        if (lenRow >= 3) {
            index = WriteRow(t, 0, lenRow, 1, indexArray, index);
        }


        for (int i = 0; i < 8; i++) {
            lenRow = 1;
            color = colorArrayList.get(i);
            int j = i + 8;
            while (j < 64) {
                if (colorArrayList.get(j) == color) {
                    lenRow++;
                }
                else {
                    if (lenRow >= 3) {
                        index = WriteRow(j, 1, lenRow + 1, 8, indexArray, index);
                    }
                    color = colorArrayList.get(j);
                    lenRow = 1;
                }
                j += 8;
            }
            j -= 8;
            if (lenRow >= 3) {
                index = WriteRow(j, 0, lenRow, 8, indexArray, index);
            }
        }
        if (delete) {
            for (int i = 0; i < index; i++) {
                colorArrayList.remove(indexArray[i]);
                colorArrayList.add(indexArray[i], Color.WHITE);
                score++;
            }
        }

        return index != 0;
    }

    public static boolean EndGame() {
        if (!FindRow(false)) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (i < 7) {
                        Swap(i*8 + j, i*8 + j + 8); // по вертикали вниз
                        if (FindRow(false)) {
                            Swap(i*8 + j, i*8 + j + 8);
                            return false;
                        }
                        Swap(i*8 + j, i*8 + j + 8);
                    }

                    if (i > 0) {
                        Swap(i*8 + j, i*8 + j - 8); // по вертикали вверх
                        if (FindRow(false)) {
                            Swap(i*8 + j, i*8 + j - 8);
                            return false;
                        }
                        Swap(i*8 + j, i*8 + j - 8);
                    }

                    if (j > 0) {
                        Swap(i*8 + j, i*8 + j - 1); // по горизонтали влево
                        if (FindRow(false)) {
                            Swap(i*8 + j, i*8 + j - 1);
                            return false;
                        }
                        Swap(i*8 + j, i*8 + j - 1);
                    }

                    if (j < 7) {
                        Swap(i*8 + j, i*8 + j + 1);
                        if (FindRow(false)) {
                            Swap(i*8 + j, i*8 + j + 1);
                            return false;
                        }
                        Swap(i*8 + j, i*8 + j + 1);
                    }
                }
            }

            return true;
        }

        return false;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,width,height);
        g.setColor(Color.WHITE);
        g.fillRect(0,100, width,height - 100);

        g.setColor(Color.PINK);
        g.fillRect(cursorX, cursorY, delta, delta);

        for (int i = 0; i < colorArrayList.size(); i++) {
            g.setColor(colorArrayList.get(i));
            g.fillOval(circleX.get(i), circleY.get(i), delta,delta);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Score: " + score, 10, 60);
        g.setColor(Color.BLACK);
        if (EndGame() && !FindEmptyElem()) {
            g.setFont(new Font("Arial", Font.PLAIN, 40));
            g.drawString("Game over", 120, 300);
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (FindEmptyElem() || !EndGame()) {
            for (int i = colorArrayList.size() - 1; i >= colorArrayList.size() - 8; i--) {
                for (int j = i; j >= 8; j -= 8) {
                    if (colorArrayList.get(j) == Color.WHITE) {
                        Swap(j, j - 8);
                    }
                }
            }

            repaint();

            GenerateCircle();
            if (!FindEmptyElem()){
                FindRow(true);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (!EndGame() && !FindEmptyElem()) {
            if (key == KeyEvent.VK_LEFT && cursorX > 0) {
                cursorX -= delta;
            }
            if (key == KeyEvent.VK_RIGHT && cursorX < width - delta) {
                cursorX += delta;
            }
            if (key == KeyEvent.VK_UP && cursorY > 100) {
                cursorY -= delta;
            }
            if (key == KeyEvent.VK_DOWN && cursorY < height - delta) {
                cursorY += delta;
            }

            int index = cursorX / delta + (cursorY - 100) / delta * 8;
            switch (key){
                case KeyEvent.VK_W:
                    if (index > 7) {
                        Swap(index, index - 8);
                        repaint();
                        if (!FindRow(false)) {
                            Swap(index, index - 8);
                            repaint();
                        }
                    }
                    break;
                case KeyEvent.VK_S:
                    if (index < 56) {
                        Swap(index, index + 8);
                        repaint();
                        if (!FindRow(false)) {
                            Swap(index, index + 8);
                            repaint();
                        }
                    }
                    break;
                case KeyEvent.VK_A:
                    if (index % 8 > 0) {
                        Swap(index, index - 1);
                        repaint();
                        if (!FindRow(false)) {
                            Swap(index, index - 1);
                            repaint();
                        }
                    }
                    break;
                case KeyEvent.VK_D:
                    if (index % 8 < 7) {
                        Swap(index, index + 1);
                        repaint();
                        if (!FindRow(false)) {
                            Swap(index, index + 1);
                            repaint();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
