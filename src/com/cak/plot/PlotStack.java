package com.cak.plot;

import com.cak.plot.plotabbles.PlotComponent;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PlotStack {
 
    HashMap<Layer, List<PlotComponent>> stackByLayer = new HashMap<>();
    
    public PlotStack() {
        for (Layer layer : Layer.values()) {
            stackByLayer.put(layer, new ArrayList<>());
        }
    }
    
    public void plot(PlotComponent plotComponent) {
        stackByLayer.get(plotComponent.getLayer()).add(plotComponent);
    }
    
    public List<PlotComponent> getComponents(Layer layer, CameraProjector projector) {
        List<PlotComponent> stack = stackByLayer.get(layer);
        stack.sort(Comparator.comparingDouble(c -> projector.orient(c.getDepthReference()).z()));
        return stack;
    }
    
    public enum Layer {
        LINE, POINT
    }
    
}
