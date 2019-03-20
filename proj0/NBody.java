public class NBody {
    /**
     * A method recieves a file name and returns a double
     * corresponding to the radius of the universe in that file
     */
    private static int readNumber(String filename) {
        In in = new In(filename);
        int number = in.readInt();
        return number;
    }

    public static double readRadius(String filename) {
        In in = new In(filename);
        int i = in.readInt();
        double r = in.readDouble();
        return r;
    }

    /**
     * Given a file name and return an array of Bodys corresponding
     * to the bodies in the file.
     */
    public static Body[] readBodies(String filename) {
        In in = new In(filename);
        int number = in.readInt();
        double r = in.readDouble();
        int i;
        Body[] bodies = new Body[number];
        for (i = 0; i < number; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            bodies[i] = new Body(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
        }
        return bodies;
    }

    public static void main(String[] args) {
        // Never forget to convert args to what the type you want.
        // Convert String to double.
        // the way to use args.
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        int number = readNumber(filename);
        double r = readRadius(filename);
        Body[] bodies = readBodies(filename);
        int time;
        // Draw picture now
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-r, r);
        for (time = 0; time <= T; ) {
            double[] xForces = new double[number];
            double[] yForces = new double[number];
            int index = 0;
            for (Body b : bodies) {
                xForces[index] = b.calcNetForceExertedByX(bodies);
                yForces[index] = b.calcNetForceExertedByY(bodies);
                index++;
            }
            index = 0;
            for (Body b : bodies) {
                b.update(dt, xForces[index], yForces[index]);
                index++;
            }
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Body b : bodies) {
                b.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }
        StdOut.printf("%d\n", bodies.length);
        StdOut.printf("%.2e\n", r);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
                    bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
    }
}