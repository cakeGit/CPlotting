import com.cak.demo.Quaternion;
import com.cak.plot.*;
import com.cak.plot.plotabbles.LinePlot;
import com.cak.plot.plotabbles.PointPlot;
import com.cak.shitatp.Line;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Main {
    
    private static final List<Line> geoVertecies = new ArrayList<>();
    
    static {
        geoVertecies.addAll(rotate(createSphereAxisOutline(), 0));
        geoVertecies.addAll(rotate(createSphereAxisOutline(), 1));
        geoVertecies.addAll(rotate(createSphereAxisOutline(), 2));
        
        List<Line> additionalVertecies = new ArrayList<>();
        additionalVertecies.addAll(geoVertecies.stream().map(v -> new Line(v.getFrom().scale(3f), v.getTo().scale(3f))).toList());
        additionalVertecies.addAll(geoVertecies.stream().map(v -> new Line(v.getFrom().scale(20f).mul(new Vector3f(1f, 0.1f, 1f)), v.getTo().scale(20f).mul(new Vector3f(1f, 0.1f, 1f)))).toList());
        geoVertecies.addAll(additionalVertecies);
    }
    
    private static Collection<Line> rotate(List<Line> sphereAxisOutline, int ordinal) {
        return sphereAxisOutline.stream().map(line ->
            new Line(
                rotateVector(line.getFrom(), ordinal),
                rotateVector(line.getTo(), ordinal)
            )
        ).toList();
    }
    
    private static Vector3f rotateVector(Vector3f from, int ordinal) {
        return switch (ordinal) {
            case 0 -> from;
            case 1 -> new Vector3f(from.y(), from.x(), from.z());
            case 2 -> new Vector3f(from.x(), from.z(), from.y());
            default -> null;
        };
    }
    
    private static List<Line> createSphereAxisOutline() {
        List<Line> result = new ArrayList<>();
        double step = Math.PI / 4;
        for (double latitude = 0; latitude < Math.PI * 2; latitude += step) {
            for (double longditude = 0; longditude < Math.PI * 2 - step; longditude += step) {
                result.add(new Line(new Vector3f(
                    (float) (Math.sin(longditude) * Math.cos(latitude)),
                    (float) Math.sin(latitude),
                    (float) (Math.cos(longditude) * Math.cos(latitude))
                ).scale(5f), new Vector3f(
                    (float) (Math.sin(longditude + step) * Math.cos(latitude)),
                    (float) Math.sin(latitude),
                    (float) (Math.cos(longditude + step) * Math.cos(latitude))
                ).scale(5f)));
                if (latitude == Math.PI / 2 || latitude == Math.PI * 1.5f) break;
            }
        }
        return result;
    }
    
    static Plotting plotting;
    
    public static void main(String[] args) {
        plotting = new Plotting();
        plotting.addPlotter(Main::plotDemo);
        plotting.addTickListener(Main::onTick);
    }
    
    static boolean orthroToggleHeld = false;
    
    private static void onTick() {
        Quaternion result = new Quaternion(0, 0, 0);
        
        float angleScale = 20;
        
        Point mousePos = plotting.getMousePosition();
        
        float verticalOffset = 0f;
        float horizontalOffset = 0f;
        if (mousePos != null) {
            verticalOffset = (float) (angleScale * (0.5 - ((double) mousePos.y / plotting.getHeight())));
            horizontalOffset = -(float) (2f * angleScale * (0.5 - ((double) mousePos.x / plotting.getWidth())));
        }
        
        result = result.rotate(Vector3f.Axis.X, (-20 + verticalOffset));
        result = result.rotate(Vector3f.Axis.Y, (-45 + horizontalOffset));
        
        plotting.projector.displayRotation = result;
        boolean oldOrthroToggleHeld = orthroToggleHeld;
        orthroToggleHeld = plotting.keyListener.keyIsDown(KeyEvent.VK_P);
        
        if (orthroToggleHeld != oldOrthroToggleHeld && orthroToggleHeld)
            plotting.projector.orthro = !plotting.projector.orthro;
    }
    
    static Quaternion quaternion = new Quaternion(0, 0, 0);
    
    private static void plotDemo(PlotStack stack, PlottingKeyListener listener) {
        quaternion = quaternion.multiply(new Quaternion(-0.01f, 0.033f, 0));
        quaternion = quaternion.multiply(new Quaternion(0, 0.01f, -0.002f));
        
        Quaternion rotation = quaternion;
        
        for (Line vertex : geoVertecies) {
            Vector3f orientedFrom = rotation.rotate(vertex.getFrom());
            Vector3f orientedTo = rotation.rotate(vertex.getTo());
            
            stack.plot(new PointPlot(orientedFrom).colored(Color.GRAY));
            stack.plot(new LinePlot(orientedFrom, orientedTo).colored(vertex.color));
        }
    }
    
    private static String createNoiseText() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++)
            result.append(createRandomChar());
        return result.toString();
    }
    
    private static String createRandomChar() {
        char character = (char) (0xffff * Math.random()); // Primitive cast magic
        return String.valueOf(character);
    }
    
}