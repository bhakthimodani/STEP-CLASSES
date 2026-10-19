// Shape.java
public abstract class Shape {
    private static int counter = 1;
    private final String shapeId;

    public Shape() {
        shapeId = "SH-" + counter++;
    }

    public abstract double calculateArea();

    public void scale(double factor) {
    }

    public void scale(double xFactor, double yFactor) {
        scale(xFactor);
        scale(yFactor);
    }

    public String getShapeId() {
        return shapeId;
    }

    public static void printArea(Shape s) {
        System.out.println(s.calculateArea());
    }
}

// CircleShape.java
class CircleShape extends Shape {
    private double radius;

    public CircleShape(double radius) {
        this.radius = radius;
    }

    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    public void scale(double factor) {
        radius *= factor;
    }
}

// SquareShape.java
class SquareShape extends Shape {
    private double side;

    public SquareShape(double side) {
        this.side = side;
    }

    public double calculateArea() {
        return side * side;
    }

    public void scale(double factor) {
        side *= factor;
    }
}

// Main.java
class Main {
    public static void main(String[] args) {
        CircleShape c = new CircleShape(5.0);
        SquareShape sq = new SquareShape(4.0);

        System.out.println(c.calculateArea());
        System.out.println(sq.calculateArea());

        sq.scale(2.0);
        System.out.println(sq.calculateArea());

        Shape.printArea(c);
        System.out.println(c.getShapeId());
    }
}