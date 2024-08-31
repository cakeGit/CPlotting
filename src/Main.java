import com.cak.demo.Quaternion;
import com.cak.plot.*;
import com.cak.plot.plotabbles.LabeledPointPlot;
import com.cak.plot.plotabbles.LinePlot;
import com.cak.plot.plotabbles.PointPlot;
import com.cak.shitatp.Line;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    
    private static List<Line> geoVertecies = new ArrayList<>();
    
    private static List<Line> geoVerteciesB = new ArrayList<>();
    
    static {
//        for (int ordinals = 0; ordinals <= 0b111; ordinals ++) {
//            int xOrdinal = (ordinals & 0b1) * 2 - 1;
//            int yOrdinal = ((ordinals & 0b10) >> 1) * 2 - 1;
//            int zOrdinal = ((ordinals & 0b100) >> 2) * 2 - 1;
//            geoVertecies.add(new Vector3f(xOrdinal, yOrdinal, zOrdinal).scale(5f));
//        }
        
        double step = Math.PI / 10;
        for (double latitude = 0; latitude < Math.PI * 2; latitude += step) {
            for (double longditude = 0; longditude < Math.PI * 2; longditude += step) {
                geoVertecies.add(new Line(new Vector3f(
                    (float) (Math.sin(longditude) * Math.cos(latitude)),
                    (float) Math.sin(latitude),
                    (float) (Math.cos(longditude) * Math.cos(latitude))
                ).scale(5f), new Vector3f(
                    (float) (Math.sin(longditude + step) * Math.cos(latitude + step)),
                    (float) Math.sin(latitude + step),
                    (float) (Math.cos(longditude + step) * Math.cos(latitude + step))
                ).scale(5f)));
            }
        }
        geoVerteciesB.addAll(geoVertecies.stream().map(v -> new Line(v.getF().scale(2f), v.getT().scale(2f))).toList());
        geoVerteciesB.addAll(geoVertecies.stream().map(v -> new Line(v.getF().scale(1.5f), v.getT().scale(1.5f))).toList());
        geoVerteciesB.addAll(geoVertecies.stream().map(v -> new Line(v.getF().scale(4f).mul(new Vector3f(1f, 0.1f, 1f)), v.getT().scale(4f).mul(new Vector3f(1f, 0.1f, 1f)))).toList());
        geoVertecies.addAll(geoVerteciesB);
//        geoVerteciesB = geoVertecies.stream().map(v -> new Line(v.getF().scale(2f), v.getT().scale(2f))).toList();
    }
    
    static Plotting plotting;
    
    public static void main(String[] args) {
        System.out.println("Hello world!");
        
        plotting = new Plotting();
        plotting.addPlotter(Main::plotDemo);
    }
    
    static float d = 0.1f;
    static float i;
    static float j;
    static float k;
    
    static Quaternion quaternion = new Quaternion(0, 0, 0);
    
    private static void plotDemo(PlotStack stack, PlottingKeyListener listener) {
        
        quaternion = quaternion.multiply(new Quaternion(-0.1f, 0.033f, 0));
        quaternion = quaternion.multiply(new Quaternion(0, 0.1f, -0.02f));
//
//        if (constrained.magnitude() > 1)
//           constrained = constrained.scale((float) (0.99f/constrained.magnitude()));
//
//        Quaternion rotation = new Quaternion(constrained.x(), constrained.y(), constrained.z());
        Quaternion rotation = quaternion;
        
        for (Line vertex : geoVertecies) {
            Vector3f orientedf = rotation.rotate(Quaternion.ofCartesian(vertex.getF())).toCartesian();
            Vector3f orientedt = rotation.rotate(Quaternion.ofCartesian(vertex.getT())).toCartesian();
            
            if (Math.random()  < 0.1f) {
                
                stack.plot(new LabeledPointPlot(orientedf, rchr()).colored(Color.RED));
                
            }else  {
                
                stack.plot(new LinePlot(orientedf, orientedt).colored(new Color((int) (Math.random() * 0xffffff))));
            }
        }
    }
    
    private static String rchr() {
        return rsch() + rsch() + rsch()+ rsch();
    }
    
    private static String rsch() {
        char character = (char) (0xfffff * Math.random()); //!!1
        return String.valueOf(character);
    }
    
    private static float clampRound(float i) {
        return (1 - (i % 4 > 2 ? 2-i%2 : i%2));
    }
    
}