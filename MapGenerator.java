
package gameplay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
// كلاس يصنع الطوب و يرسمها على الشاشة
public class MapGenerator {
    public int map[][];
    public int bricksWidth;
    public int bricksHeight;
   //Constructor for the MapGenerator class which 
    //initializes the map array with values of 1, representing unbroken bricks.
    // يظهر الطوب الي ما انصدم ب قيمة واحد
    // يحسب الطول و العرض حبت عدد الصفوف و الاعمدة
    public MapGenerator(int row , int col){
        map = new int[row][col];
         for (int[] map1 : map) {
             for (int j = 0; j < map[0].length; j++) {
                 map1[j] = 1;
             }
         }
        bricksWidth = 540/col;
        bricksHeight = 150/row;
    }
    //دالة تتحقق اذا كانت قيمة الطوبة اكبر من صفر يعني الطوبة ما انكسرت
    //و اذا كانت الطوبه غير مكسورة يحول لونها للاحمر
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Color.red);
                    g.fillRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.black);
                    g.drawRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);

                }
            }

        }
    }
    //دالة تعطي قيمة معينة لكل طوبة داخل المصفوفة 
    //قيمة المعامل تتغير حسب الطوبة اذا صفر مكسورة و اذا ةاحد غير مكسورة
    public void setBricksValue(int value,int row,int col)
    {
        map[row][col] = value;

    }
    
}

