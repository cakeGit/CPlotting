package com.cak.plot;

public class CameraProjector {
    
    final float scale = 10;
    
    Plotting parent;
    
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
        return new Vector3f(
            x - z,
            -y + ((x + z) / 2),
            -y + (x + z)/2
        );
    }
    
    /**
     * Used for sorting objects so that closest draws first
     * */
    public float getDepth(float x, float y, float z) {
        return z;
    }
    
}
