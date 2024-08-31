package com.cak.plot.plotabbles;

import com.cak.plot.Graphics3d;
import com.cak.plot.PlotStack;
import com.cak.plot.Vector3f;

public class LinePlot extends PlotComponent {
    
    Vector3f from;
    Vector3f to;
    
    public LinePlot(Vector3f from, Vector3f to) {
        this.from = from;
        this.to = to;
    }
    
    @Override
    public void draw(Graphics3d g3d) {
        super.draw(g3d);
        g3d.showLine(from, to);
    }
    
    @Override
    public PlotStack.Layer getLayer() {
        return PlotStack.Layer.LINE;
    }
    
    @Override
    public Vector3f getDepthReference() {
        return to.add(from).scale(0.5f);
    }
    
    
}
