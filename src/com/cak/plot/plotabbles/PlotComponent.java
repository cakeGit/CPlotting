package com.cak.plot.plotabbles;

import com.cak.plot.Graphics3d;
import com.cak.plot.PlotStack;
import com.cak.plot.Vector3f;

import java.awt.*;

public abstract class PlotComponent {
    
    Color color;
    
    public PlotComponent() {
        this(Color.BLACK);
    }
    
    public PlotComponent(Color color) {
        this.color = color;
    }
    
    public void draw(Graphics3d g3d) {
        g3d.colored(color);
    }
    
    public abstract PlotStack.Layer getLayer();
    
    public abstract Vector3f getDepthReference();
    
    public PlotComponent colored(Color color) {
        this.color = color;
        return this;
    }
    
}
