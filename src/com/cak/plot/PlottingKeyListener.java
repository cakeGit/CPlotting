package com.cak.plot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class PlottingKeyListener implements KeyListener {
    
    HashSet<Integer> heldKeys = new HashSet<>();
    
    @Override
    public void keyTyped(KeyEvent e) {
    
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        heldKeys.add(e.getKeyCode());
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        heldKeys.remove(e.getKeyCode());
    }
    
    public boolean keyIsDown(Integer code) {
        return heldKeys.contains(code);
    }
    
}
