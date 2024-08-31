package com.cak.plot;

import java.awt.*;
import java.util.Map;

public class PlotPallet {
    
    public static final Color AXIS = new Color(69, 69, 69);
    public static final Color MAJOR_GRID = new Color(165, 165, 165);
    public static final Color MINOR_GRID = new Color(231, 231, 231);
    
    public static final Map<Vector3f.Axis, Color> AXIS_COLORED = Map.of(
        Vector3f.Axis.X, new Color(55, 62, 202),
        Vector3f.Axis.Y, new Color(62, 202, 55),
        Vector3f.Axis.Z, new Color(202, 55, 62)
    );
    
}
