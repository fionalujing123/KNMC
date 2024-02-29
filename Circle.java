package SearchCircleSkyline;

import java.util.ArrayList;

public class Circle {
    public int id;
	public int k;
	public double dis;
	public double centerX;
	public double centerY;
	public double radius;
	public ArrayList<Point> p;
	public double discenq;// 圆心到查询点的距离
	public int cirnum;// 圆内点的个数
	public boolean isdominate;
	public String lowzval;//圆心的zdc-值
	public String upperzval;//圆心的zdc+值
	public void setlowZval(String lowzval) {
		this.lowzval = lowzval;
	}

	public String getlowZval() {
		return lowzval;
	}
	public void setupperZval(String upperzval) {
		this.upperzval = upperzval;
	}

	public String getupperZval() {
		return upperzval;
	}
	public Circle() {
	}
	public Circle(int id, double centerX, double centerY) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.centerX = centerX;
		this.centerY = centerY;
		// this.p = p;
	}
	public Circle(double centerX, double centerY) {
		// TODO Auto-generated constructor stub
		this.centerX = centerX;
		this.centerY = centerY;
		// this.p = p;
	}

	// public void setCenter(double centerX, double centerY, ArrayList<Point> p) {
	// this.centerX = centerX;
	// this.ceterY = centerY;
	// this.p = p;
	// }
	public void setcenterX(double centerX) {
		this.centerX = centerX;
	}
	public double getcenterX() {
        return centerX;
    }
	public void setcenterY(double centerY) {
		this.centerY = centerY;
	}
	public double getcenterY() {
        return centerY;
    }


	public double disCP(Circle c, Point a) {
		double val = 0;
		val += (c.centerX - a.x) * (c.centerY - a.y);
		return (double) Math.sqrt(val);

	}
	public double getDiscenq() {
        return discenq;
    }
	
	public int getCirnum() {
        return cirnum;
    }
	public ArrayList<Point> getP() {
        return p;
    }

    public void setCirnum(int cirnum) {
        this.cirnum = cirnum;
    }
	public boolean isdominate() {
        return isdominate;
    }

    public void isdominate(boolean dominate) {
    	isdominate = dominate;
    }
    
    

}
