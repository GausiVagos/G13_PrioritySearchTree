package G13.pst.utils;

import G13.pst.models.Point;
import G13.pst.models.Segment;
import G13.pst.models.Window;

public class Parser {

    public static Segment createSegmentFromString(String line) throws IllegalArgumentException, NumberFormatException {
        String[] elements = line.split(" ");
        if(elements.length != 4) {
            throw new IllegalArgumentException("String cannot be parsed to segment");
        }
        Point p1 = new Point(Double.parseDouble(elements[0]), Double.parseDouble(elements[1]));
        Point p2 = new Point(Double.parseDouble(elements[2]), Double.parseDouble(elements[3]));
        Segment s = new Segment(p1,p2);
        p1.setSegment(s);
        p2.setSegment(s);
        return s;
    }

    public static Window createWindowFromString(String line) throws IllegalArgumentException, NumberFormatException {
        String[] elements = line.split(" ");
        if(elements.length != 4) {
            throw new IllegalArgumentException("String cannot be parsed to window");
        }
        Point p1 = new Point(Double.parseDouble(elements[0]), Double.parseDouble(elements[2]));
        Point p2 = new Point(Double.parseDouble(elements[1]), Double.parseDouble(elements[3]));
        return new Window(p1,p2);
    }

}
