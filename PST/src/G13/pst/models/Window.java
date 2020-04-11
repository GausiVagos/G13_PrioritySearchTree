package G13.pst.models;

public class Window {
    private Point start;
    private Point end;

    public Window(Point p1, Point p2) {
        if(p1.getX() < p2.getX() && p1.getY() < p2.getY()) {
            this.start = p1;
            this.end = p2;
        } else {
            this.start = p2;
            this.end = p1;
        }
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public void setStart(Point start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "Window [" + this.start.toString() + " " + this.end.toString() + "]";
    }
}
