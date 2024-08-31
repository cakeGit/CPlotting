package com.cak.plot.plotabbles;

import com.cak.plot.Graphics3d;
import com.cak.plot.PlotStack;
import com.cak.plot.Vector3f;

public class PointPlot extends PlotComponent {
    
    Vector3f pos;
    
    public PointPlot(Vector3f pos) {
        this.pos = pos;
    }
    
    @Override
    public void draw(Graphics3d g3d) {
        super.draw(g3d);
        g3d.showCircle(pos, 3);
    }
    
    @Override
    public PlotStack.Layer getLayer() {
        return PlotStack.Layer.POINT;
    }
    
    @Override
    public Vector3f getDepthReference() {
        return pos;
    }
    
}
