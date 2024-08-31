package com.cak.plot;

import com.cak.plot.plotabbles.PlotComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.HashSet;
import java.util.Set;

public class Plotting extends JFrame {
    
    public float time;
    int referenceBounds = 50;
    int gridSize = 1;
    int majorGridSize = 5;
    
    Vector3f.Axis gridPlane = Vector3f.Axis.Y;
    
    Set<Plotter> plotters = new HashSet<>();
    
    //Create a reference so that keys currently being pressed can be tracked
    PlottingKeyListener keyListener = new PlottingKeyListener();
    CameraProjector projector = new CameraProjector(this);
    
    BufferStrategy strategy;
    
    Timer renderTimer = new Timer(1000 / 25, e -> {
        time += 1000f / 25;
        Graphics g = strategy.getDrawGraphics();
        this.paint(g);
        strategy.show();
    });
    
    public Plotting() {
        super();
        setTitle("CPlotting Frame");
        setSize(new Dimension(500, 400));
        setVisible(true);
        repaint();
        addWindowListener(new PlottingWindowListener(this));
        addKeyListener(keyListener);
        setIgnoreRepaint(true);
        createBufferStrategy(2);
        strategy = getBufferStrategy();
        renderTimer.start();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        Graphics3d g3d = new Graphics3d(g, projector);
        
        boolean showOriginAndAxisInfo = keyListener.keyIsDown(KeyEvent.VK_SHIFT);
        
        g3d.colored(Color.BLACK);
        
        gridLines(gridPlane.getPrimaryPlaneAxis(), gridPlane.getSecondaryPlaneAxis(), g3d);
        gridLines(gridPlane.getSecondaryPlaneAxis(), gridPlane.getPrimaryPlaneAxis(), g3d);
        
        for (Vector3f.Axis axis : Vector3f.Axis.values()) {
            g3d.colored(showOriginAndAxisInfo ? PlotPallet.AXIS_COLORED.get(axis) : PlotPallet.AXIS);
            g3d.showLine(axis.normal.scale(-referenceBounds), axis.normal.scale(referenceBounds));
        }
        
        if (showOriginAndAxisInfo) {
            g3d.colored(PlotPallet.AXIS);
            g3d.showString(0, 0, 0, "Origin");
            
            g3d.colored(PlotPallet.AXIS_COLORED.get(Vector3f.Axis.X));
            g3d.showString(referenceBounds, 0, 0, "+X");
            g3d.showString(-referenceBounds, 0, 0, "-X");
            
            g3d.colored(PlotPallet.AXIS_COLORED.get(Vector3f.Axis.Y));
            g3d.showString(0, referenceBounds, 0, "+Y");
            g3d.showString(0, -referenceBounds, 0, "-Y");
            
            g3d.colored(PlotPallet.AXIS_COLORED.get(Vector3f.Axis.Z));
            g3d.showString(0, 0, referenceBounds, "+Z");
            g3d.showString(0, 0, -referenceBounds, "-Z");
        }
        
        PlotStack stack = new PlotStack();
        for (Plotter plotter : plotters) {
            plotter.plot(stack, keyListener);
        }
        
        for (PlotStack.Layer layer : PlotStack.Layer.values()) {
            java.util.List<PlotComponent> components = stack.getComponents(layer, projector);
            
            components.forEach(c -> c.draw(g3d));
        }
        
    }
    
    private void gridLines(Vector3f.Axis axis, Vector3f.Axis secondary, Graphics3d g3d) {
        for (int offset = -referenceBounds; offset <= referenceBounds; offset += gridSize) {
            Vector3f from = axis.normal.scale(-referenceBounds)
                .add(secondary.normal.scale(offset));
            Vector3f to = axis.normal.scale(referenceBounds)
                .add(secondary.normal.scale(offset));
            
            if (offset == 0) continue;
            
            Color color = offset % majorGridSize == 0 ? PlotPallet.MAJOR_GRID :
                    PlotPallet.MINOR_GRID;
            
            g3d.colored(color);
            g3d.showLine(from, to);
        }
    }
    
    public void kill() {
        renderTimer.stop();
        dispose();
    }
    
    public void addPlotter(Plotter plotter) {
        plotters.add(plotter);
    }
    
}
