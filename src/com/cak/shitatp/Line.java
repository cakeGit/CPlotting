package com.cak.shitatp;

import com.cak.plot.Vector3f;

public class Line {
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
