import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class CatchTheDrop extends JFrame {
    private static long last_frame_time;
    private static Image background10;
    private static Image go2;
    private static Image drop7;
    private static float drop7_left=200;
    private static float drop7_top=-100;
    private static float drop7_v=200;
    private static int score;

    private static CatchTheDrop catch_the_drop;
    public static void main(String[] args) throws IOException {
        background10= ImageIO.read(CatchTheDrop.class.getResourceAsStream("background10.jpg"));
        go2=ImageIO.read(CatchTheDrop.class.getResourceAsStream("go2.jpg"));
        drop7=ImageIO.read(CatchTheDrop.class.getResourceAsStream("drop7.png"));
        catch_the_drop = new CatchTheDrop();
        catch_the_drop.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        catch_the_drop.setLocation(200,100);
        catch_the_drop.setSize(906,478);
        catch_the_drop.setResizable(false);
        last_frame_time=System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop7_right = drop7_left + drop7.getWidth(null);
                float drop7_bottom = drop7_top + drop7.getHeight(null);
                boolean is_drop7 = x>= drop7_left && x<= drop7_right && y>= drop7_top && y<= drop7_bottom;
                if (is_drop7) {
                    drop7_top =-100;
                    drop7_left=(int) (Math.random() * (game_field.getWidth()- drop7.getWidth(null)));
                    drop7_v=drop7_v + 20;
                    score=score+2;
                    catch_the_drop.setTitle("SCORE: "+score);


                }
            }
        });
        catch_the_drop.add(game_field);
        catch_the_drop.setVisible(true);

    }
    private static void Paint(Graphics g){
        long current_time=System.nanoTime();
        float delta_time=(current_time-last_frame_time)*0.000000001f;
        last_frame_time=current_time;

        drop7_top=drop7_top+drop7_v *delta_time;
        g.drawImage(background10, 0,0,null);
        g.drawImage(drop7,(int)drop7_left,(int)drop7_top,null);
        if (drop7_top>catch_the_drop.getHeight())g.drawImage(go2,280,120,null);

    }

    private static class GameField extends JPanel{

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Paint(g);
            repaint();
        }
    }
}
