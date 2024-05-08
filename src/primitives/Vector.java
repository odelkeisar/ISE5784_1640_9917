package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 cannot be defined");
    }
    public Vector(Double3 _xyz) {
        super(_xyz);
        if(this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("Vector 0 cannot be defined");
    }
    public Vector add(Vector v) {
        return new Vector(this.xyz.add(v.xyz));
    }
    public Vector scale(double scale) {
        return new Vector(this.xyz.scale(scale));
    }

    public double dotProduct(Vector v) {
        return this.xyz.d1 * v.xyz.d1 + this.xyz.d2 * v.xyz.d2 + this.xyz.d3 * v.xyz.d3;
    }
    public Vector crossProduct(Vector v) {
        double newX = this.xyz.d2 * v.xyz.d3 - this.xyz.d3 * v.xyz.d2;
        double newY = this.xyz.d3 * v.xyz.d1 - this.xyz.d1 * v.xyz.d3;
        double newZ = this.xyz.d1 * v.xyz.d2 - this.xyz.d2 * v.xyz.d1;
        return new Vector(new Double3(newX, newY, newZ));
    }
    public double lengthSquared(){
        return dotProduct(this);
    }
public double length(){
        return Math.sqrt(lengthSquared());
}

public Vector normalize(){
        return new Vector(this.scale(1/this.length()).xyz);
}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Vector) && super.equals(obj);
    }
    @Override
   public String toString(){
        return super.toString();
    }







}
