package SearchCircleSkyline;

import java.util.ArrayList;

//import util.Points;

public class Point {
	public int id;
	public double x;
	public double y;
	public String zval;
	public boolean isdominated;
	public String plowzval;//点的z2dc-值
	public String pupperzval;//点的z2dc+值
	public int pcirnum;// 圆内点的个数
	

    public void setpCirnum(int pcirnum) {
        this.pcirnum = pcirnum;
    }
    public int getpCirnum() {
        return pcirnum;
    }
	public void setplowZval(String plowzval) {
		this.plowzval = plowzval;
	}

	public String getplowZval() {
		return plowzval;
	}
	public void setpupperZval(String pupperzval) {
		this.pupperzval = pupperzval;
	}

	public String getpupperZval() {
		return pupperzval;
	}
	public Point(int id, double x, double y) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.x = x;
		this.y = y;
	}
	public Point() {
		// TODO Auto-generated constructor stub
		
	}
	public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
	public void setX(double x) {
		this.x = x;
	}
	public double getX() {
        return x;
    }
	public void setY(double y) {
		this.y = y;
	}
	public double getY() {
        return y;
    }
	public void setZval(String zval) {
		this.zval = zval;
	}

	public String getZval() {
		return zval;
	}
	
	
	public double dis(Point p){
		double val=0;
		val +=(x-p.x)*(x-p.x)+(y-p.y)*(y-p.y);
		return Math.sqrt(val);
		
	}
	
	//@Override
	public int compareTo(Point o) {
		return zval.compareTo(o.zval);
	}
}
