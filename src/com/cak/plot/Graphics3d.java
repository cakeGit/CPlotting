package com.cak.plot;

import java.awt.*;

public class Graphics3d {
    
    Graphics g;
    CameraProjector projector;
    
    public Graphics3d(Graphics g, CameraProjector projector) {
        this.g = g;
        this.projector = projector;
    }
    
    public void showString(float x, float y, float z, String str) {
        Vector2i screenPos = project(x, y, z);
        g.drawString(str, screenPos.x(), screenPos.y());
    }
    
    public void showLine(float xFrom, float yFrom, float zFrom, float xTo, float yTo, float zTo) {
        Vector2i from = project(xFrom, yFrom, zFrom);
        Vector2i to = project(xTo, yTo, zTo);
        g.drawLine(from.x(), from.y(), to.x(), to.y());
    }
    
    public void showCircle(float x, float y, float z, int r) {
        Vector2i screenPos = project(x, y, z);
        g.fillOval(screenPos.x() - r, screenPos.y() - r, 2*r, 2*r);
    }
    
    private Vector2i project(float x, float y, float z) {
        return projector.projectToScreen(x, y, z);
    }
    
    public void colored(Color color) {
        g.setColor(color);
    }
    
    public void showString(ProjectableVector pos, String string) {
        showString(pos.x(), pos.y(), pos.z(), string);
    }
    
    public void showLine(ProjectableVector from, ProjectableVector to) {
        showLine(from.x(), from.y(), from.z(), to.x(), to.y(), to.z());
    }
    
    public void showCircle(ProjectableVector pos, Integer r) {
        showCircle(pos.x(), pos.y(), pos.z(), r);
    }
    
}
