import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;


public class Frogger extends JFrame{
    Timer myTimer;
    GamePanel game;

    public Frogger() {
        super("Frogger Dylan & Steven ltd copyright");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(810,795);

        myTimer = new Timer(10, new TickListener());
        myTimer.start();

        game = new GamePanel();
        add(game);

        setResizable(false);
        setVisible(true);
    }

    class TickListener implements ActionListener{
        public void actionPerformed(ActionEvent evt){
            if(game!= null && game.ready){
                game.move();
                game.repaint();
                game.setClick();
            }
        }
    }

    public static void main(String[] arguments) {
        Frogger frame = new Frogger();

    }
}
class Obstacle{
    private int sizex,sizey;
    private Image pic;

}
class GamePanel extends JPanel implements KeyListener {
    private int frogx,frogy;
    Frog player = new Frog();
    public boolean ready=false;
    private boolean click;
    private boolean []keys;

    private static Image back,frogPic,car1,car2,car3,log1,log2,log3;
    private static Lanes lanes[] = new Lanes [12];
    private static Image obstaclePics [] = {back,frogPic,car1,car2,car3,log1,log2,log3};

    public GamePanel(){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        try {
            back = ImageIO.read(new File("Pictures/froggerBackground.png"));
            frogPic = ImageIO.read(new File("Pictures/frogger.png"));
            car1 = ImageIO.read(new File("Pictures/car1.png"));
            car2 = ImageIO.read(new File("Pictures/car2.png"));
            car3 = ImageIO.read(new File("Pictures/car3.png"));
            log1 = ImageIO.read(new File("Pictures/log1.png"));
            log2 = ImageIO.read(new File("Pictures/log2.png"));
            log3 = ImageIO.read(new File("Pictures/log3.png"));
        }
        catch (IOException e) {
        }


        frogx = 200;
        frogy = 200;
        addKeyListener(this);
        loadLanes();
        movement();

    }

    public void setClick(){
        click=false;
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
        ready = true;
    }
    public int randint(int low, int high){
        return (int)(Math.random()*(high-low+1)+low);
    }
    public static void loadLanes(){
        for (int i = 0; i<12 ; i ++){
            Lanes makeLanes;
            if (i%2 == 1) {
                makeLanes = new Lanes(90 + 55 * i, 3, "RIGHT");
            }
            else{
                makeLanes = new Lanes(90 + 55 * i, 3 ,"LEFT");
            }
            lanes[i] = makeLanes;
        }
    }

    public static void movement(){
        for (Lanes l : lanes){
            int counter = 0;
            l.moveLanes();
            counter++;
            System.out.println(counter);

        }
    }

    public void move() {
        if (click) {
            if (keys[KeyEvent.VK_RIGHT]) {
                System.out.println("Right");
                player.moveRight();
            }

            if (keys[KeyEvent.VK_LEFT]) {
                System.out.println("Left");
                player.moveLeft();
            }
            if (keys[KeyEvent.VK_UP]) {
                System.out.println("Up");
                player.moveUp();
            }
            if (keys[KeyEvent.VK_DOWN]) {
                System.out.println("Down");
                player.moveDown();
            }
        }
        player.checkBound();
    }
    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(255,222,222));
        g.drawImage(back,0,0,this);

        //for (Lanes l : lanes){
        for (int i = 0; i<12;i++){
            g.setColor(new Color(255,222,222));
             g.drawRect(0,lanes[i].getYPos(),800,751);
            for (Area a : lanes[i].getAreas()){ //start at 6
                //System.out.println(a);
                if (i>=6 && i < 11) { /// lanes on the road
                    g.drawImage(car1, a.getAx(), a.getAy() + 5, this);
                }
                if (i>=0 && i<5){
                    int randCar = randint(1,3);
                    g.drawImage(log1, a.getAx(),a.getAy(),this);
                }
            }

        }
        g.drawImage(frogPic,player.getPosX(),player.getPosY(),this);

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}


    @Override
    public void keyPressed(KeyEvent k) {
        keys[k.getKeyCode()]=true;
        click=true;

    }

    @Override
    public void keyReleased(KeyEvent k) {
        keys[k.getKeyCode()]=false;
        click=false;
    }
}