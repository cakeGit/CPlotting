package com.cak.plot;

import com.cak.demo.Quaternion;

public class CameraProjector {
    
    final float scale = 3;
    
    Plotting parent;
    
    public boolean orthro = false;
    public Quaternion displayRotation = new Quaternion(0, 0, 0);
    
    
    public CameraProjector(Plotting parent) {
        this.parent = parent;
    }
    
    public Vector2i projectToScreen(float x, float y, float z) {
        Vector3f screen = orient(x, y, z);
        
        return new Vector2i(
            (int) ((screen.x() * scale) + (parent.getWidth() / 2f)),
            (int) ((screen.y() * scale) + (parent.getHeight() / 2f))
        );
    }
    
    public Vector3f orient(Vector3f vector3f) {
        return orient(vector3f.x(), vector3f.y(), vector3f.z());
    }
    
    /**
     * Return the screen position, with z being the depth
     * */
    public Vector3f orient(float x, float y, float z) {
        Vector3f rotated = rotate(x, y, z);
        
//        float perspective = (100 - rotated.z())/50;
        float perspective = (orthro ? 1f : (200+rotated.z())/100);
        return rotated.mul(new Vector3f(perspective, perspective, perspective));
        
//        return rotated;
//        float perspectiveDepth = ((1000-rotated.z()) / 10);
//        return rotated.mul(new Vector3f(perspectiveDepth, perspectiveDepth, 1f));
    }
    
    public Vector3f rotate(float x, float y, float z) {
        return displayRotation.rotate(new Vector3f(x, y, z));
    }
    
    
}
