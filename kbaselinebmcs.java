package SearchCircleSkyline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
//import java.util.Date;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class kbaselinebmcs {
	/**
	 * @param args
	 */
	Point q = new Point(100, 101);// 查询点
	// Point q ;
	double dis = 0;
	double dc = 0;
	int pnum = 0;
	Point[] points = null;
	// long sum = 0;
	long sumd = 0;
	int k = 3;

	public ArrayList<Circle> circles = new ArrayList<Circle>();
	public ArrayList<Point> plist = new ArrayList<Point>();

	public kbaselinebmcs(double dc, int pnum) {
		this.dc = dc;
		this.pnum = pnum;
		points = new Point[pnum];
	}

	// 读数据
	public void init(String input) {
		// this.dataPath = input;
		try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			String s = null;
			int j = 0;
			while ((s = br.readLine()) != null) {
				StringTokenizer tk = new StringTokenizer(s);
				// int id = Integer.parseInt(tk.nextToken());
				double x = Double.parseDouble(tk.nextToken());
				double y = Double.parseDouble(tk.nextToken());
				// points[j] = new Point(id, x, y);
				points[j] = new Point(x, y);

				plist.add(points[j]);
				j++;
			}

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}

	}

//找到所有的圆
	public void method(double dc) {
		Point p1 = null;
		Point p2 = null;
		int w = 0;

		for (int i = 0; i < plist.size(); i++) {
			p1 = plist.get(i);
			double qp1 = q.dis(p1);
			for (int j = i + 1; j < plist.size(); j++) {
				p2 = plist.get(j);
				double qp2 = q.dis(p2);
				// 给定两点的距离
				double d12 = p1.dis(p2);
				Point ctem1 = new Point();
				if (d12 <= 2 * dc && d12 > 0) {
					Circle cc = new Circle();
					cc.p = new ArrayList<Point>();
					if (qp1 > qp2) {
						if (p1.x > q.x) {
							ctem1.x = (double) (q.x + ((qp1 - dc) / qp1) * Math.abs(p1.x - q.x));
						} else if ((p1.x == q.x)) {
							ctem1.x = q.x;

						} else {
							ctem1.x = (double) (q.x - ((qp1 - dc) / qp1) * Math.abs(p1.x - q.x));
						}
						if (p1.y > q.y) {
							ctem1.y = (double) (q.y + ((qp1 - dc) / qp1) * Math.abs(p1.y - q.y));
						} else if (p1.y == q.y) {
							ctem1.y = q.y;
						} else {
							ctem1.y = (double) (q.y - ((qp1 - dc) / qp1) * Math.abs(p1.y - q.y));
						}

						if (p2.dis(ctem1) <= dc) {
							cc.centerX = ctem1.x;
							cc.centerY = ctem1.y;
						} else {
							cc.centerX = CircleCenter(p1.x, p1.y, p2.x, p2.y).x;
							cc.centerY = CircleCenter(p1.x, p1.y, p2.x, p2.y).y;
						}

						double disc1 = (double) Math.sqrt(
								((cc.centerX - q.x) * (cc.centerX - q.x) + (cc.centerY - q.y) * (cc.centerY - q.y)));
						double disc = (double) (Math.round(disc1 * 100)) / 100;
						// 圆心到查询点的距离
						cc.discenq = disc;
						// 圆内包含的点的个数几点
						for (int n = 0; n < pnum; n++) {
							sumd++;
							double cdp1 = (double) Math.sqrt((cc.centerX - points[n].x) * (cc.centerX - points[n].x)
									+ (cc.centerY - points[n].y) * (cc.centerY - points[n].y));
							double cdp = (double) (double) (Math.round(cdp1 * 100)) / 100;
							if (cdp <= dc) {
								cc.p.add(points[n]);
								// System.out.println(points[n].x+" "+points[n].y);
								cc.cirnum++;
							}

						}

						if (cc.cirnum >= k) {
							circles.add(cc);
							w++;
						}

					} else {

						if (p2.x > q.x) {
							ctem1.x = (double) (q.x + ((qp2 - dc) / qp2) * Math.abs(p2.x - q.x));
						} else if (p2.x == q.x) {
							ctem1.x = q.x;
						} else {
							ctem1.x = (double) (q.x - ((qp2 - dc) / qp2) * Math.abs(p2.x - q.x));
						}
						if (p2.y > q.y) {
							ctem1.y = (double) (q.y + ((qp2 - dc) / qp2) * Math.abs(p2.y - q.y));
						} else if (p2.y == q.y) {
							ctem1.y = q.y;
						} else {
							ctem1.y = (double) (q.y - ((qp2 - dc) / qp2) * Math.abs(p2.y - q.y));
						}

						if (p1.dis(ctem1) <= dc) {
							cc.centerX = ctem1.x;
							cc.centerY = ctem1.y;
						} else {
							cc.centerX = CircleCenter(p1.x, p1.y, p2.x, p2.y).x;
							cc.centerY = CircleCenter(p1.x, p1.y, p2.x, p2.y).y;
						}
						// circles.add(cc);

						double disc1 = (double) Math.sqrt(
								((cc.centerX - q.x) * (cc.centerX - q.x) + (cc.centerY - q.y) * (cc.centerY - q.y)));
						double disc = (double) (Math.round(disc1 * 100)) / 100;
						cc.discenq = disc;
						for (int n = 0; n < pnum; n++) {
							sumd++;
							double cdp1 = (double) Math.sqrt((cc.centerX - points[n].x) * (cc.centerX - points[n].x)
									+ (cc.centerY - points[n].y) * (cc.centerY - points[n].y));
							double cdp = (double) (double) (Math.round(cdp1 * 100)) / 100;
							if (cdp <= dc) {
								cc.p.add(points[n]);
								// System.out.println(points[n].x+" "+points[n].y);
								cc.cirnum++;
							}

						}
						if (cc.cirnum >= k) {
							circles.add(cc);
							w++;
						}
					}

				}
				if (w % 10000 == 0) {
					System.out.println("w:" + w);
				}

			}

		}
		for (int o = 0; o < circles.size(); o++) {
			if (o < 5) {
				System.out.println(circles.get(o).discenq + " x: " + circles.get(o).centerX + " y: "
						+ circles.get(o).centerY + " p size: " + circles.get(o).p.size());
				for (int M = 0; M < circles.get(o).p.size(); M++) {
					System.out.println(" ppx: " + circles.get(o).p.get(M).x + " ppy: " + circles.get(o).p.get(M).y);
				}
			}
		}

		System.out.println("circleswith size :" + circles.size());

		Collections.sort(circles, new Comparator<Circle>() {
			@Override
			public int compare(Circle cc1, Circle cc2) {
				if (cc1.discenq > cc2.discenq) {
					return 1;
				} else if (cc1.discenq == cc2.discenq) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		List<Circle> circle = circles.stream()
				.collect(Collectors.collectingAndThen(
						Collectors
								.toCollection(() -> new TreeSet<>(Comparator.comparing(Circle -> Circle.getDiscenq()))),
						ArrayList::new));

		for (int o = 0; o < circle.size(); o++) {
			if (o < 5) {
				System.out.println(circle.get(o).discenq + " x: " + circle.get(o).centerX + " y: "
						+ circle.get(o).centerY + " p size: " + circle.get(o).p.size());
				for (int p = 0; p < circle.get(o).p.size(); p++) {
					System.out.println(" px: " + circle.get(o).p.get(p).x + " py: " + circle.get(o).p.get(p).y);
				}
			}
		}

	}

	public int BinarySearch(ArrayList<Point> plist, String lowz) {
		int low = 0;
		int high = plist.size() - 1;
		int middle = 0;
		int a = lowz.compareTo(plist.get(0).zval);
		int b = lowz.compareTo(plist.get(plist.size() - 1).zval);
		if (a < 0 && b > 0 && low > high) {
			return -1;
		}
		while (low <= high) {
			middle = (low + high) / 2;
			if (plist.get(middle).zval.compareTo(lowz) > 0) {
				high = middle - 1;
			} else if (plist.get(middle).zval.compareTo(lowz) < 0) {
				low = middle + 1;
			} else {
				return middle;
			}
		}

		return low;
	}

	public Point CircleCenter(double x1, double y1, double x2, double y2) {
		Point p1 = new Point();
		Point p2 = new Point();
		Point p = new Point();
		double dispc1 = 0;
		double dispc2 = 0;
		double disp12 = (double) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		if (disp12 == 2 * dc) {
			p.x = (x1 + x2) / 2;
			p.y = (y1 + y2) / 2;
			return p;
		} else {
			if (x1 != x2) {
				// System.out.println(x1 + " x1x2jin " + x2);
				double c1 = (x2 * x2 - x1 * x1 + y2 * y2 - y1 * y1) / (2 * (x2 - x1));
				double c2 = (y2 - y1) / (x2 - x1); // 斜率
				double A = (c2 * c2 + 1);
				double B = (2 * x1 * c2 - 2 * c1 * c2 - 2 * y1);
				double C = x1 * x1 - 2 * x1 * c1 + c1 * c1 + y1 * y1 - dc * dc;
				if (y1 == y2) {
					p1.x = (x1 + x2) / 2;
					p1.y = (double) (y1 + Math.sqrt(dc * dc - ((y1 - y2) / 2) * ((y1 - y2) / 2)));
					p2.x = (x1 + x2) / 2;
					p2.y = (double) (y1 - Math.sqrt(dc * dc - ((y1 - y2) / 2) * ((y1 - y2) / 2)));
					dispc1 = q.dis(p1);
					dispc2 = q.dis(p2);
					if (dispc1 < dispc2) {
						return p1;
					} else {
						return p2;
					}

				} else {
					p1.y = (double) ((-B + Math.sqrt(B * B - 4 * A * C)) / (2 * A));
					p1.x = c1 - c2 * p1.y;
					dispc1 = q.dis(p1);
					p2.y = (double) ((-B - Math.sqrt(B * B - 4 * A * C)) / (2 * A));
					p2.x = c1 - c2 * p2.y;

					dispc2 = q.dis(p2);
					if (dispc1 < dispc2) {
						return p1;
					} else {
						return p2;
					}
				}
			} else {
				p1.y = (y1 + y2) / 2;
				p1.x = (double) (x1 + (Math.sqrt(dc * dc - ((y1 - y2) / 2) * ((y1 - y2) / 2))));
				p2.y = (y1 + y2) / 2;
				p2.x = (double) (x1 - (Math.sqrt(dc * dc - ((y1 - y2) / 2) * ((y1 - y2) / 2))));

				dispc1 = q.dis(p1);
				dispc2 = q.dis(p2);
				if (dispc1 < dispc2) {
					return p1;
				} else {
					return p2;
				}
			}
		}
	}

	public Circle compCircleCenter(Point p1, Point p2, Point p3) {
		double X = ((p2.y - p1.y) * (p3.y * p3.y - p1.y * p1.y + p3.x * p3.x - p1.x * p1.x)
				- (p3.y - p1.y) * (p2.y * p2.y - p1.y * p1.y + p2.x * p2.x - p1.x * p1.x))
				/ (2 * (p3.x - p1.x) * (p2.y - p1.y) - 2 * ((p2.x - p1.x) * (p3.y - p1.y)));
		double Y = ((p2.x - p1.x) * (p3.x * p3.x - p1.x * p1.x + p3.y * p3.y - p1.y * p1.y)
				- (p3.x - p1.x) * (p2.x * p2.x - p1.x * p1.x + p2.y * p2.y - p1.y * p1.y))
				/ (2 * (p3.y - p1.y) * (p2.x - p1.x) - 2 * ((p2.y - p1.y) * (p3.x - p1.x)));
		double radius = (double) Math.sqrt((p1.x - X) * (p1.x - X) + (p1.y - Y) * (p1.y - Y));
		Circle c = new Circle(X, Y);
		c.radius = radius;

		return c;
	}

	public void out(String output) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(output));
			for (int i = 0; i < circles.size(); i++) {
				bw.write(" " + circles.get(i).discenq + "\r\n");
			}

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Point q = new Point();
		// q.id = 0;
		// q.setX(2);
		// q.setY(3);
		// java -jar -in wa15w.txt -k 10 -num 10000
		/*
		 * String input=null; double k; for(int i = 0; i < args.length; i++) {
		 * if(args[i].equals("-in")) { input = args[++i]; } //if }
		 */
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		double dc = 1;
		int pnum = 389942;// 数据集包含的点数
		kbaselinebmcs sc = new kbaselinebmcs(dc, pnum);
		// sc.init(input);
		sc.init("TS.txt");
		long startTime = System.currentTimeMillis();
		sc.method(dc);
		// sc.skylineCircle();
		long endTime = System.currentTimeMillis();
		System.out.println("Times " + (endTime - startTime) + "ms");
		sc.out("result.txt");

	}

}
