package com.cak.shitatp;

import com.cak.plot.Vector3f;

import java.awt.*;

public class Line {
    
    public Color color = new Color((int) (Math.random() * 0xAAAAAA));
    Vector3f f, t;
    
    public Line(Vector3f f, Vector3f t) {
        this.f = f;
        this.t = t;
    }
    
    public Vector3f getF() {
        return f;
    }
    
    public Vector3f getT() {
        return t;
    }
    
}
