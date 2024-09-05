package com.cak.demo;

import com.cak.plot.Vector3f;

public class Quaternion {
    
    /**
     * a, b, c form the non-real vector components, and correspond to i, j, k <br>
     * w is the real component
     */
    final float w, a, b, c;
    
    public Quaternion(float a, float b, float c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.w = (float) (Math.sqrt(1 - (a*a + b*b + c*c)));
    }
    
    public Quaternion(float w, float a, float b, float c) {
        this.w = w;
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public static void runDemo() {
        //Rotate a point 0, 1, 0
        Vector3f vector = new Vector3f(0, 10, 0);
        Quaternion quaternionVector = Quaternion.ofCartesian(vector);
        
        System.out.println("Starting with vector: " + vector);
        System.out.println("as quaternion: " + quaternionVector);
        
        Quaternion rotation = new Quaternion(0, 0, 0.5f);
        System.out.println("Rotation: " + rotation);
        Quaternion result = rotation.rotate(quaternionVector);
        
        System.out.println("Rotation conjugate: " + rotation.conjugate());
        
        Vector3f resultVector = result.toCartesian();
        
        System.out.println("Result: " + resultVector);
        System.out.println("as quaternion: " + result);
        
        //look for any error (magnitude should stay the same in rotation)
        double magnitudeDifference = (vector.magnitude() - resultVector.magnitude());
        
        System.out.println("Magnitude diff " + magnitudeDifference + " ("+(magnitudeDifference < 0.1 ? "NONE" : "ERROR!")+")");
    
    }
    
    public Quaternion rotate(Quaternion vector) {
        vector = this.multiply(vector);
        
        Quaternion conjugate = this.conjugate();
        
        return vector.multiply(conjugate);
    }
    
    public Quaternion conjugate() {
        return new Quaternion(w, -a, -b, -c);
    }
    
    public Quaternion multiply(Quaternion other) {
        
        //following the multiplication table of quaternions
        float newW = this.w * other.w
            + (-this.a * other.a)
            + (-this.b * other.b)
            + (-this.c * other.c);
        
        //Any multiplications that result in an i term will go here
        float newA = this.w * other.a
            + this.a * other.w
            + this.b * other.c
            + (-this.c * other.b);
        
        float newB = this.w * other.b
            + this.b * other.w
            + this.c * other.a
            + (-this.a * other.c);
        
        float newC = this.w * other.c
            + this.c * other.w
            + this.a * other.b
            + (-this.b * other.a);
        
        return new Quaternion(newW, newA, newB, newC);
    }
    
    public static Quaternion ofCartesian(Vector3f vec) {
        return new Quaternion(0, vec.x(), vec.y(), vec.z());
    }
    
    public Vector3f toCartesian() {
        return new Vector3f(a, b, c);
    }
    
    public Vector3f rotate(Vector3f t) {
        return this.rotate(Quaternion.ofCartesian(t)).toCartesian();
    }
    
    @Override
    public String toString() {
        return w + " + " + a + "i + " + b + "j + " + c + "k";
    }
    
    public float getW() {
        return w;
    }
    
    public float getA() {
        return a;
    }
    
    public float getB() {
        return b;
    }
    
    public float getC() {
        return c;
    }
    
    public Quaternion rotate(Vector3f.Axis axis, float i) {
        Vector3f rotation = axis.getNormal().scale((float) Math.sin(Math.toRadians(i / 2f)));
        return this.multiply(new Quaternion(rotation.x(), rotation.y(), rotation.z()));
    }
    
}
