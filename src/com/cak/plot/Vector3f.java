package com.cak.plot;

public record Vector3f(float x, float y, float z) implements ProjectableVector {
    
    public static final Vector3f ZERO = new Vector3f(0, 0, 0);
    
    @Override
    public String toString() {
        return String.format("(%f, %f, %f)", x, y, z);
    }
    
    public double magnitude() {
        return Math.sqrt(
            x * x + y * y + z * z
        );
    }
    
    public Vector3f add(Vector3f other) {
        return new Vector3f(this.x + other.x, this.y + other.y, this.z + other.z);
    }
    
    public Vector3f mul(Vector3f other) {
        return new Vector3f(this.x * other.x, this.y * other.y, this.z * other.z);
    }
    
    public Vector3f scale(float s) {
        return this.mul(new Vector3f(s, s, s));
    }
    
    public enum Axis {
        X(new Vector3f(1, 0, 0)),
        Y(new Vector3f(0, 1, 0)),
        Z(new Vector3f(0, 0, 1));
        
        final Vector3f normal;
        
        Axis(Vector3f normal) {
            this.normal = normal;
        }
        
        public Vector3f getNormal() {
            return normal;
        }
        
        public Axis getPrimaryPlaneAxis() {
            return switch (this) {
                case X -> Y;
                case Y, Z -> X;
            };
        }
        
        public Axis getSecondaryPlaneAxis() {
            return switch (this) {
                case X, Y -> Z;
                case Z -> Y;
            };
            
        }
    }
    
}
