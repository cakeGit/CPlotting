package com.cak.plot;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class PlottingWindowListener implements WindowListener {
    
    Plotting parent;
    
    public PlottingWindowListener(Plotting parent) {
        this.parent = parent;
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
    
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        parent.kill();
    }
    
    @Override
    public void windowClosed(WindowEvent e) {
    
    }
    
    @Override
    public void windowIconified(WindowEvent e) {
    
    }
    
    @Override
    public void windowDeiconified(WindowEvent e) {
    
    }
    
    @Override
    public void windowActivated(WindowEvent e) {
    
    }
    
    @Override
    public void windowDeactivated(WindowEvent e) {
    
    }
    
}
