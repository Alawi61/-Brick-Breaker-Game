/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testGame;
 
import javax.swing.JPanel; // حاوية ل باقي المكونات 
import javax.swing.Timer; // كلاس يطلق حدث او اكثر بعد تاخير معين
import java.awt.Graphics2D; // توفير قدرات رسم عالية 
import java.awt.Rectangle;//   تمثيل مستطيلات عن احداثيات معينة
import java.awt.Graphics;//  كلاس يعطي دوال عشان رسم بعض الاشكال او الصور
import java.awt.Color;// كلاس يوفر الوان عند استخدام الرسومات
import java.awt.Font;//كلاس يمثل خطوط الكتابة
import java.awt.event.ActionEvent;//كلاس يمثل حدث
import java.awt.event.ActionListener;//
import java.awt.event.KeyEvent;// كلاس يمثل مفتاح الحدث 
import java.awt.event.KeyListener;
public class GamePlay  extends JPanel implements KeyListener, ActionListener {


    
     private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    private MapGenerator map;

    public GamePlay() {
        // صنع نموذج جديد من نوع ماب جينيراتور مع  ثلاثة صفوف و سبعة اعمدة
map = new MapGenerator(3, 7);

// اضافة لهذا النموذج  (keyListener )
addKeyListener(this);

//التركيز على نموذج اللعب 
setFocusable(true);

// تعطيل مفاتيح الاجتياز
setFocusTraversalKeysEnabled(false);

// صنع توقيت مع تاخير معين
Timer = new Timer(delay, this);

// بدا توقيت 
Timer.start();

    }
    
     public void paint(Graphics g) {
        // جعل الخلفية سوداء و لما يصير الطوبة ب صفر تتحول للون الاسود
         g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
         // رسم الخريطة (المراحل)
map.draw((Graphics2D) g);

// رسم جدران الشاشة باللون الأصفر
g.setColor(Color.yellow);
g.fillRect(0, 0, 3, 592);
g.fillRect(0, 0, 692, 3);
g.fillRect(691, 0, 3, 592);

// رسم نقاط اللاعب وعرضها في الزاوية العلوية اليمنى من الشاشة
g.setColor(Color.white);
g.setFont(new Font("serif", Font.BOLD, 25));
g.drawString("" + score, 590, 30);

// رسم عنصر اللاعب باللون الماجنتا وعرضه في الجزء السفلي من الشاشة
g.setColor(Color.MAGENTA);
g.fillRect(playerX, 550, 100, 8);

// رسم الكرة باللون الأزرق وعرضها في الموقع الحالي
g.setColor(Color.BLUE);
g.fillOval(ballposX, ballposY,20,20);

        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            // في حال سقوط الكرة راح تظهر رسالة  انتهاء اللعبة باللون الابيض
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);
// عند الخسارة تظهر رسالة اعادة اللعبة 
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
        }
        //  تحقق الشرط في هذا الجزء من الكود إذا كانت جميع الكتل قد تم تدميرها واظهتر رسالة انتهاء اللعبة
        if(totalbricks == 0){
            play = false;
            ballYdir = -2;
            ballXdir = -1;
            g.setColor(Color.green);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("    Game Over: "+score,190,300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);


        }
// تحرير  الموارد الي استخدمت بالرسم
        g.dispose();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();
      //  يتحقق اذا باقي يلعب 
        if (play) {
            // اذا تحققق الشرط يتاكد اذا كانت الكرة تصتدم ب المجداف
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                  // يتححق اذ الطوبة تحطمت او لا 
                    if (map.map[i][j] > 0) {
                     // قياسات الطوب من طول و عرض 
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;
                       //  تشكيل الطوب على شكل مستطيلات 
                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;
                        // يتحقق اذا الكرة اصتدمت تاكرة مع الطوب 
                        if (ballrect.intersects(brickrect)) {
                            // اذا تححق الشرط الطوب ياخذ قيمة صفر و يتدمر 
                            map.setBricksValue(0, i, j);
                           // تقليل عدد الطوب
                            totalbricks--;
                           // يزيد عدد نقاط اللاعب بخمس نقاط للطوبة
                            score += 5;
                            // تغيير اتجاه الكرة حسب الاتجاه الي صدمت فيه الطوبه
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            // الخروج من لووب (A)
                            break A;
                        }
                    }


                }
            }

           // تتحرك الكرة حسب اتجاهها 
            ballposX += ballXdir;
            ballposY += ballYdir;
            // يتحقق اذا الكرة اصطدمت بالجدار و اذا تحقق الشرط تغير اتجاه الكرة
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

       }

 // اعطاء اللاعب التحكم ب المجداف 
    @Override
    public void keyReleased(KeyEvent e) {

    }
    // استدعاء الدالة عند ضغط احد ازرار الكيبورد
    @Override
    public void keyPressed(KeyEvent e) {
       // اذا ضغطت سهم يمين سوف يتحرك المجداف لليمين مالم يكن ملتصق بالجدار اليمين 
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
       // اذا ضغطت سهم يسار سوف يتحرك المجداف لليسار مالم يكن ملتصق بالجدار اليسار
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        // في حال الفوز او الخسارة و راح يقول اضغط ادخال للاعادة
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }


        }
        // تحريك المجداف عشرين بيسكل لليمين عند الضغط على سهم يمين 
        public void moveRight ()
        {
            play = true;
            playerX += 20;
        }
         // تحريك المجداف عشرين بيسكل لليسار عند الضغط على سهم يسار 
        public void moveLeft ()
        {
            play = true;
            playerX -= 20;
        }
    public static void main(String[] args) {
        JFrame obj = new JFrame();
        GamePlay gameplay = new GamePlay();
        obj.setBounds(10,10,700,600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gameplay);
    }
    
}
