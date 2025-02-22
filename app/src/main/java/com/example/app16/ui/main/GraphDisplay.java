package com.example.app16.ui.main;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//implements the graphs and correlate it to f/e
public class GraphDisplay extends Drawable implements Drawable.Callback {

    private String graphKind = "line"; // could also be "scatter" or "bar"
    private Canvas canvas = null;
    private Random rand = new Random();

    private final Paint bluePaint;
    private final Paint blackPaint;
    private final Paint orangePaint;
    private final Paint textPaint;
    private final int offset = 50; // space for axes and labels
    private final int divisions = 12; // scale divisions

    public ArrayList<Double> xpoints;
    public ArrayList<Double> ypoints;
    private ArrayList<Double> zpoints;
    private ArrayList<String> xlabels;
    private HashMap<String, ArrayList<Double>> linesx;
    private HashMap<String, ArrayList<Double>> linesy;
    private HashMap<String, Double> labelsx;
    private HashMap<String, Double> labelsy;

    private String xName = "";
    private String yName = "";

    private static GraphDisplay instance = null;

    public GraphDisplay() {
        // Set up color and text size
        bluePaint = new Paint();
        bluePaint.setARGB(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        blackPaint = new Paint();
        blackPaint.setARGB(255, 0, 0, 0);
        orangePaint = new Paint();
        orangePaint.setARGB(255, 128, 128, 0);
        textPaint = new Paint();
        textPaint.setARGB(255, 0, 0, 0);
        textPaint.setTextSize((float) 28.0);
        xpoints = new ArrayList<Double>();
        ypoints = new ArrayList<Double>();
        zpoints = new ArrayList<Double>();
        xlabels = new ArrayList<String>();
        linesx = new HashMap<String, ArrayList<Double>>();
        linesy = new HashMap<String, ArrayList<Double>>();
        labelsx = new HashMap<String, Double>();
        labelsy = new HashMap<String, Double>();
    }

    public static GraphDisplay defaultInstance() {
        if (instance == null) {
            instance = new GraphDisplay();
        }
        return instance;
    }

    public void invalidateDrawable(Drawable drawable) {
        xpoints = new ArrayList<Double>();
        ypoints = new ArrayList<Double>();
        zpoints = new ArrayList<Double>();
        xlabels = new ArrayList<String>();
        linesx = new HashMap<String, ArrayList<Double>>();
        linesy = new HashMap<String, ArrayList<Double>>();
        labelsx = new HashMap<String, Double>();
        labelsy = new HashMap<String, Double>();
    }

    public void scheduleDrawable(Drawable who,
                                 Runnable what,
                                 long when) {
    }

    public void unscheduleDrawable(Drawable who,
                                   Runnable what) {
    }

    public void setXScalar(ArrayList<Double> xvalues) {
        xpoints = xvalues;
    }

    public void setXNominal(ArrayList<String> xvalues) {
        xlabels = xvalues;
    }

    public void setYPoints(ArrayList<Double> yvalues) {
        ypoints = yvalues;
    }

    public void setZPoints(ArrayList<Double> zvalues) {
        zpoints = zvalues;
    }

    public void setxname(String xn) {
        xName = xn;
    }

    public void setyname(String yn) {
        yName = yn;
    }

    public void setGraphKind(String kind) {
        graphKind = kind;
    }

    public void addLine(String name, ArrayList<Double> xvalues, ArrayList<Double> yvalues) {
        linesx.put(name, xvalues);
        linesy.put(name, yvalues);
    }

    public void addLabel(String name, double x, double y) {
        labelsx.put(name, new Double(x));
        labelsy.put(name, new Double(y));
    }

    @Override
    public void draw(Canvas canvas) {
        this.canvas = canvas;

        if (xpoints.size() == 0) {
            if (xlabels.size() == 0) {
                return;
            } else {
                this.drawNominalScaler(canvas);
                return;
            }
        }

        // Get the drawable's bounds
        int width = getBounds().width();
        int height = getBounds().height();
        float radius = 10;

        double minx = xpoints.get(0);
        double maxx = xpoints.get(0);

        for (int i = 1; i < xpoints.size(); i++) {
            double xcoord = xpoints.get(i);
            if (xcoord < minx) {
                minx = xcoord;
            }
            if (xcoord > maxx) {
                maxx = xcoord;
            }
        }

        ArrayList<String> linekeys = new ArrayList<String>();
        linekeys.addAll(linesx.keySet());

        for (int j = 0; j < linekeys.size(); j++) {
            String key = linekeys.get(j);
            ArrayList<Double> linexvals = linesx.get(key);
            for (int k = 0; k < linexvals.size(); k++) {
                double linex = linexvals.get(k).doubleValue();
                if (linex < minx) {
                    minx = linex;
                }
                if (linex > maxx) {
                    maxx = linex;
                }
            }
        }

        ArrayList<String> labelkeys = new ArrayList<String>();
        labelkeys.addAll(labelsx.keySet());

        for (int i = 0; i < labelkeys.size(); i++) {
            String labelkey = labelkeys.get(i);
            double labelx = labelsx.get(labelkey);
            if (labelx < minx) {
                minx = labelx;
            }
            if (labelx > maxx) {
                maxx = labelx;
            }
        }

        double deltax = maxx - minx;

        // ArrayList<String> xmarkers = new ArrayList<String>();
        double xstep = deltax / 12.0;

        for (int i = 0; i <= 12; i++) {
            double xcoord = i * xstep * (width - 100) / deltax;
            int currx = (int) xcoord + 45;
            double xvalue = minx + i * xstep;
            xvalue = Math.round(xvalue * 100);
            canvas.drawText("" + (xvalue / 100), currx, height - 20, textPaint);
        }

        double miny = ypoints.get(0);
        double maxy = ypoints.get(0);

        for (int i = 1; i < ypoints.size(); i++) {
            double ycoord = ypoints.get(i);
            if (ycoord < miny) {
                miny = ycoord;
            }
            if (ycoord > maxy) {
                maxy = ycoord;
            }
        }

        for (int i = 0; i < zpoints.size(); i++) {
            double ycoord = zpoints.get(i);
            if (ycoord < miny) {
                miny = ycoord;
            }
            if (ycoord > maxy) {
                maxy = ycoord;
            }
        }

        for (int j = 0; j < linekeys.size(); j++) {
            String key = linekeys.get(j);
            ArrayList<Double> lineyvals = linesy.get(key);
            for (int k = 0; k < lineyvals.size(); k++) {
                double liney = lineyvals.get(k).doubleValue();
                if (liney < miny) {
                    miny = liney;
                }
                if (liney > maxy) {
                    maxy = liney;
                }
            }
        }

        for (int i = 0; i < labelkeys.size(); i++) {
            String labelkey = labelkeys.get(i);
            double labely = labelsy.get(labelkey);
            if (labely < miny) {
                miny = labely;
            }
            if (labely > maxy) {
                maxy = labely;
            }
        }

        double deltay = maxy - miny;
        double ystep = deltay / 12.0;

        for (int i = 0; i <= 12; i++) {
            double ycoord = i * ystep * (height - 100) / deltay;
            int curry = (int) ycoord + 45;
            double yvalue = miny + i * ystep;
            yvalue = Math.round(yvalue * 1000);
            canvas.drawText("" + (yvalue / 1000), 5, height - curry, textPaint);
        }

        // ArrayList<String> ymarkers = new ArrayList<String>();

        int prevx = (int) ((xpoints.get(0) - minx) * (width - 100) / deltax + 50);
        int prevy = height - (int) ((ypoints.get(0) - miny) * (height - 100) / deltay + 50);
        canvas.drawCircle(prevx, prevy, radius, bluePaint);
        // Draw a circule at each point:
        for (int i = 1; i < xpoints.size() && i < ypoints.size(); i++) {
            double xcoord = (xpoints.get(i) - minx) * (width - 100) / deltax;
            double ycoord = (ypoints.get(i) - miny) * (height - 100) / deltay;
            int currx = (int) xcoord + 50;
            int curry = height - ((int) ycoord + 50);
            canvas.drawCircle(currx, curry, radius, bluePaint);
            if (graphKind.equals("line")) {
                canvas.drawLine(prevx, prevy, currx, curry, blackPaint);
            }
            prevx = currx;
            prevy = curry;
        }

        if (zpoints.size() > 0) {
            prevx = (int) ((xpoints.get(0) - minx) * (width - 100) / deltax + 50);
            int prevz = height - (int) ((zpoints.get(0) - miny) * (height - 100) / deltay + 50);
            canvas.drawCircle(prevx, prevz, radius, orangePaint);
            // Draw a circule at each point:
            for (int i = 1; i < xpoints.size() && i < zpoints.size(); i++) {
                double xcoord = (xpoints.get(i) - minx) * (width - 100) / deltax;
                double zcoord = (zpoints.get(i) - miny) * (height - 100) / deltay;
                int currx = (int) xcoord + 50;
                int currz = height - ((int) zcoord + 50);
                canvas.drawCircle(currx, currz, radius, orangePaint);
                if (graphKind.equals("line")) {
                    canvas.drawLine(prevx, prevz, currx, currz, orangePaint);
                }
                prevx = currx;
                prevz = currz;
            }
        }

        for (int p = 0; p < linekeys.size(); p++) {
            String key = linekeys.get(p);
            ArrayList<Double> linexvals = linesx.get(key);
            ArrayList<Double> lineyvals = linesy.get(key);

            int previousx = (int) ((linexvals.get(0) - minx) * (width - 100) / deltax + 50);
            int previousy = height - (int) ((lineyvals.get(0) - miny) * (height - 100) / deltay + 50);
            canvas.drawCircle(previousx, previousy, radius, bluePaint);

            for (int i = 1; i < linexvals.size() && i < lineyvals.size(); i++) {
                double xcoord = (linexvals.get(i) - minx) * (width - 100) / deltax;
                double ycoord = (lineyvals.get(i) - miny) * (height - 100) / deltay;
                int currx = (int) xcoord + 50;
                int curry = height - ((int) ycoord + 50);
                canvas.drawCircle(currx, curry, radius, bluePaint);
                if (graphKind.equals("line")) {
                    canvas.drawLine(previousx, previousy, currx, curry, blackPaint);
                }
                previousx = currx;
                previousy = curry;
            }
        }

        for (int i = 0; i < labelkeys.size(); i++) {
            String labkey = labelkeys.get(i);
            int labx = (int) ((labelsx.get(labkey) - minx) * (width - 100) / deltax) + 50;
            int laby = height - ((int) ((labelsy.get(labkey) - miny) * (height - 100) / deltay) + 50);
            canvas.drawText(labkey, labx, laby, textPaint);
        }

        canvas.drawText(xName, width - 50, height, textPaint);
        canvas.drawText(yName, 15, 25, textPaint);
    }

    private void drawNominalScaler(Canvas canvas) {
        int width = getBounds().width();
        int height = getBounds().height();
        float radius = 10;

        int nsize = xlabels.size();
        double xstep = (width - 100) / nsize;

        for (int i = 0; i < nsize; i++) {
            double xcoord = i * xstep;
            int currx = (int) xcoord + 45;
            String xvalue = String.valueOf(xlabels.get(i));
            canvas.drawText(xvalue, currx, height - 20, textPaint);
        }

        double miny = ypoints.get(0);
        double maxy = ypoints.get(0);

        for (int i = 1; i < ypoints.size(); i++) {
            double ycoord = ypoints.get(i);
            if (ycoord < miny) {
                miny = ycoord;
            }
            if (ycoord > maxy) {
                maxy = ycoord;
            }
        }

        for (int i = 0; i < zpoints.size(); i++) {
            double ycoord = zpoints.get(i);
            if (ycoord < miny) {
                miny = ycoord;
            }
            if (ycoord > maxy) {
                maxy = ycoord;
            }
        }

        double deltay = maxy - miny;
        double ystep = deltay / 12.0;

        for (int i = 0; i <= 12; i++) {
            double ycoord = i * ystep * (height - 100) / deltay;
            int curry = (int) ycoord + 45;
            double yvalue = miny + i * ystep;
            yvalue = Math.round(yvalue * 1000);
            canvas.drawText("" + (yvalue / 1000), 5, height - curry, textPaint);
        }

        // ArrayList<String> ymarkers = new ArrayList<String>();

        int prevx = 50;
        int prevy = height - (int) ((ypoints.get(0) - miny) * (height - 100) / deltay + 50);
        canvas.drawCircle(prevx, prevy, radius, bluePaint);
        // Draw a circule at each point:
        for (int i = 1; i < nsize && i < ypoints.size(); i++) {
            double xcoord = i * xstep;
            int currx = (int) xcoord + 50;
            double ycoord = (ypoints.get(i) - miny) * (height - 100) / deltay;

            int curry = height - ((int) ycoord + 50);
            canvas.drawCircle(currx, curry, radius, bluePaint);
            if (graphKind.equals("line")) {
                canvas.drawLine(prevx, prevy, currx, curry, blackPaint);
            }
            prevx = currx;
            prevy = curry;
        }

        if (zpoints.size() > 0) {
            prevx = 50;
            int prevz = height - (int) ((zpoints.get(0) - miny) * (height - 100) / deltay + 50);
            canvas.drawCircle(prevx, prevz, radius, orangePaint);
            // Draw a circule at each point:
            for (int i = 1; i < nsize && i < zpoints.size(); i++) {
                double xcoord = i * xstep;
                double zcoord = (zpoints.get(i) - miny) * (height - 100) / deltay;
                int currx = (int) xcoord + 50;
                int currz = height - ((int) zcoord + 50);
                canvas.drawCircle(currx, currz, radius, orangePaint);
                if (graphKind.equals("line")) {
                    canvas.drawLine(prevx, prevz, currx, currz, orangePaint);
                }
                prevx = currx;
                prevz = currz;
            }
        }

        canvas.drawText(xName, width - 50, height, textPaint);
        canvas.drawText(yName, 15, 25, textPaint);
    }


    @Override
    public void setAlpha(int alpha) {
        // This method is required
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // This method is required
    }

    @Override
    public int getOpacity() {
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        return PixelFormat.OPAQUE;
    }

    public void redraw() {
        draw(canvas);
    }
}
