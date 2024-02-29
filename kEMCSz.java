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
import java.util.List;
//import java.util.Date;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class kEMCSz {
	/**
	 * @param args
	 */
	Point q = new Point(-77.032, 38.892);// 查询点
	// Point q ;
	double dis = 0;
	double dc = 0;
	int pnum = 0;
	Point[] points = null;
	long sum = 0;
	int sumcircle = 0;
	long sumz = 0;
	long sumd = 0;
	long sumsky = 0;
	long sumdominate = 0;
	int k = 8;

	public ArrayList<Circle> circles = new ArrayList<Circle>();
	public ArrayList<Point> plist = new ArrayList<Point>();

	public kEMCSz(double dc, int pnum) {
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
				// 每个的z值
				int[] pzval = { (int) Math.round(x), (int) Math.round(y) };

				points[j].zval = Zorder.valueOf(2, pzval);
				// System.out.println(points[j].zval);
				int[] pcupperzval = { (int) Math.ceil((x + 2 * dc)), (int) Math.ceil((y + 2 * dc)) };
				points[j].pupperzval = Zorder.valueOf(2, pcupperzval);

				int[] plowzval = new int[2];
				plowzval[0] = (x - 2 * dc > 0 ? (int) Math.floor((x - 2 * dc)) : 0);
				plowzval[1] = (y - 2 * dc > 0 ? (int) Math.floor((y - 2 * dc)) : 0);
				points[j].plowzval = Zorder.valueOf(2, plowzval);

				plist.add(points[j]);
				j++;
			}

			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();

		}
		// 数据按照z值从小到大排序
		Collections.sort(plist, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				if (p1 != null && p2 != null) {
					if (p1.zval.compareTo(p2.zval) > 0) {
						return 1;
					} else if (p1.zval.compareTo(p2.zval) < 0) {
						return -1;
					} else {
						return 0;
					}
				}
				return 0;
			}
		});
	}

//找到所有的圆
	public void method(double dc) {
		Point p1 = null;
		Point p2 = null;
		int w = 0;
		for (int i = 0; i < plist.size(); i++) {
			p1 = plist.get(i);
			double qp1 = q.dis(p1);
			Circle c1 = new Circle();

			int ppostion1 = BinarySearch(plist, p1.plowzval);
			int ppostion2 = BinarySearch(plist, p1.pupperzval);

			int ppostionstart = 0;
			int ppostionend = 0;

			if (ppostion1 >= 1) {
				ppostionstart = ppostion1 - 1;
			} else {
				ppostionstart = 0;
			}

			if (ppostion2 < plist.size()) {
				ppostionend = ppostion2 + 1;

			} else {
				ppostionend = plist.size();

			}

			for (int j = i + 1; j < ppostionend; j++) {

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
						cc.discenq = disc;

						// 每个圆的上界和下界z值
						int[] clowzval = new int[2];
						clowzval[0] = (int) (cc.centerX - dc > 0 ? (int) Math.floor(cc.centerX - dc) : 0);
						clowzval[1] = (int) (cc.centerY - dc > 0 ? (int) Math.floor(cc.centerY - dc) : 0);
						cc.lowzval = Zorder.valueOf(2, clowzval);
						int[] cupperzval = { (int) Math.ceil(cc.centerX + dc), (int) Math.ceil(cc.centerY + dc) };
						cc.upperzval = Zorder.valueOf(2, cupperzval);

						// 计算圆内点的个数
						int postion1 = BinarySearch(plist, cc.lowzval);
						int postion2 = BinarySearch(plist, cc.upperzval);
						// System.out.println(postion);
						int postionstart = 0;
						int postionend = 0;
						if (postion1 >= 1) {
							postionstart = postion1 - 1;
						} else {
							postionstart = 0;
						}
						if (postion2 < plist.size()) {
							postionend = postion2 + 1;
						} else {
							postionend = plist.size();
						}
						int z = postionend - postionstart;
						sumz = sumz + z;
						for (int n = postionstart; n < postionend; n++) {
							sumd++;
							double cdp1 = (double) Math
									.sqrt((cc.centerX - plist.get(n).x) * (cc.centerX - plist.get(n).x)
											+ (cc.centerY - plist.get(n).y) * (cc.centerY - plist.get(n).y));

							double cdp = (double) (Math.round(cdp1 * 100)) / 100;
							if (cdp <= dc) {
								cc.p.add(points[n]);
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

						// w++;
						double disc1 = (double) Math.sqrt(
								((cc.centerX - q.x) * (cc.centerX - q.x) + (cc.centerY - q.y) * (cc.centerY - q.y)));
						double disc = (double) (Math.round(disc1 * 100)) / 100;
						cc.discenq = disc;
						// 每个圆的上界和下界z值
						int[] clowzval = new int[2];
						clowzval[0] = (int) (cc.centerX - dc > 0 ? (int) Math.floor(cc.centerX - dc) : 0);
						clowzval[1] = (int) (cc.centerY - dc > 0 ? (int) Math.floor(cc.centerY - dc) : 0);
						cc.lowzval = Zorder.valueOf(2, clowzval);
						int[] cupperzval = { (int) Math.ceil(cc.centerX + dc), (int) Math.ceil(cc.centerY + dc) };
						cc.upperzval = Zorder.valueOf(2, cupperzval);

						int postion1 = BinarySearch(plist, cc.lowzval);
						int postion2 = BinarySearch(plist, cc.upperzval);
						// System.out.println(postion);
						int postionstart = 0;
						int postionend = 0;
						if (postion1 >= 1) {
							postionstart = postion1 - 1;
						} else {
							postionstart = 0;
						}
						if (postion2 < plist.size()) {
							postionend = postion2 + 1;
						} else {
							postionend = plist.size();
						}
						int z = postionend - postionstart;
						sumz = sumz + z;
						for (int n = postionstart; n < postionend; n++) {
							sumd++;
							double cdp1 = (double) Math
									.sqrt((cc.centerX - plist.get(n).x) * (cc.centerX - plist.get(n).x)
											+ (cc.centerY - plist.get(n).y) * (cc.centerY - plist.get(n).y));

							double cdp = (double) (Math.round(cdp1 * 100)) / 100;
							if (cdp <= dc) {
								cc.p.add(points[n]);
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
			if (o < 2) {
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
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		double dc = 0.01;
		int pnum = 112;// 数据集包含的点数
		kEMCSz sc = new kEMCSz(dc, pnum);
		sc.init("gas.txt");
		long startTime = System.currentTimeMillis();
		sc.method(dc);
		// sc.skylineCircle();
		long endTime = System.currentTimeMillis();
		System.out.println("Times " + (endTime - startTime) + "ms");
		sc.out("result.txt");

	}

}
