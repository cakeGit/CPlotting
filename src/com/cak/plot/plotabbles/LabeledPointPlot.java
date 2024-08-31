package com.cak.plot.plotabbles;

import com.cak.plot.Graphics3d;
import com.cak.plot.Vector3f;

public class LabeledPointPlot extends PointPlot {
    
    String label;
    
    public LabeledPointPlot(Vector3f pos, String label) {
        super(pos);
        this.label = label;
    }
    
    @Override
    public void draw(Graphics3d g3d) {
        super.draw(g3d);
        g3d.showString(pos, label);
    }
    
}
