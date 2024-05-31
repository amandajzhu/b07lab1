import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Polynomial {
	double[] coeff;
	int[] exp;

	public Polynomial() {
		coeff = new double[0];
		exp = new int[0];
	}
	
	public Polynomial(double[] c, int[] e) {
		coeff = c;
		exp = e;
	}

	public Polynomial(File f) {
		try {
			Scanner input = new Scanner(f);
			String str = input.next();
			String[] tmpArr = str.split("-", 0);
			String[] arr = new String[str.split("[+-]", 0).length];
			int idx = 0;
			for (int i = 0; i < tmpArr.length; i++) {
				if (i > 0)
					tmpArr[i] = "-" + tmpArr[i];
				String[] tmpArr2 = tmpArr[i].split("[+]", 0);
				for (String s:tmpArr2) {
					arr[idx] = s;
					idx++;
				}
			}
			
			coeff = new double[arr.length];
			exp = new int[arr.length];
			int i = 0;
			for (String s:arr) {
				String[] term = s.split("x", 0);
				coeff[i] = Double.parseDouble(term[0]);
				if (term.length > 1) {
					exp[i] = Integer.parseInt(term[1]);
				} else {
					exp[i] = 0;
				}
				i++;
			}
		} catch (FileNotFoundException ex) {}
	}

	public Polynomial add(Polynomial p) {
		int deg = 0;
		for (int i = 0; i < exp.length; i++) {
			deg++;
		}
		for (int i = 0; i < p.exp.length; i++) {
			boolean contains = false;
			for (int j = 0; j < exp.length; j++) {
				if (p.exp[i] == exp[j]) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				deg++;
			}
		}
		int[] newExp = new int[deg];
		double[] newCoeff = new double[deg];
		int min = 0;
		int x = 0;
		int y = 0;
		if (exp.length >= p.exp.length) {
			min = exp.length;
		} else {
			min = p.exp.length;
		}
		for (int i = 0; i < deg; i++) {
			if (x < exp.length && y < p.exp.length) {
				if (exp[x] == p.exp[y]) {
					newExp[i] = exp[x];
					newCoeff[i] = coeff[x] + p.coeff[y];
					x++;
					y++;
				} else if (exp[x] > p.exp[y]) {
					newExp[i] = p.exp[y];
					newCoeff[i] = p.coeff[y];
					y++;
				} else {
					newExp[i] = exp[x];
					newCoeff[i] = coeff[x];
					x++;
				}
			} else {
				if (x < exp.length) {
					newExp[i] = exp[x];
					newCoeff[i] = coeff[x];
					x++;
				} else if (y < p.exp.length) {
					newExp[i] = p.exp[y];
					newCoeff[i] = p.coeff[y];
					y++;
				}
			}
		}
		return new Polynomial(newCoeff, newExp);
	}

	public double evaluate(double d) {
		double result = 0;
		for (int i = 0; i < coeff.length; i++) {
			result += coeff[i] * Math.pow(d, exp[i]);
		}
		return result;
	}

	public boolean hasRoot(double d) {
		if (evaluate(d) == 0) return true;
		return false;
	}

	public Polynomial multiply(Polynomial p) {
		double[] newCoeff = new double[coeff.length + p.coeff.length];
		int[] newExp = new int[coeff.length + p.coeff.length];
		int newIdx = 0;
		for (int i = 0; i < coeff.length; i++) {
			for (int j = 0; j < p.coeff.length; j++) {
				newCoeff[newIdx] = coeff[i] * p.coeff[j];
				newExp[newIdx] = exp[i] + p.exp[j];
				newIdx++;
			}
		}
		int len = newExp.length;
		for (int i = 0; i < newExp.length; i++) {
			for (int j = i+1; j < newExp.length; j++) {
				if (newExp[i] == newExp[j]) {
					len--;
					newCoeff[i] += newCoeff[j];
					newCoeff[j] = 0;
				}
			}
		}
		double[] finalCoeff = new double[len];
		int[] finalExp = new int[len];
		int f = 0;
		for (int i = 0; i < newCoeff.length; i++) {
			if (newCoeff[i] != 0) {
				finalCoeff[f] = newCoeff[i];
				finalExp[f] = newExp[i];
				f++;
			}
		}
		return new Polynomial(finalCoeff, finalExp);
	}

	public void saveToFile(String fileName) {
		String poly = "";
		for (int i = 0; i < coeff.length; i++) {
			if (!poly.equals("")) {
				if (coeff[i] > 0) {
					poly += '+';
				}
			}
			if (exp[i] == 0) {
				poly += coeff[i];
			} else {
				poly += coeff[i] + "x" + exp[i];
			}
		}
		try {
			PrintStream output = new PrintStream(fileName);
			output.print(poly);
			output.close();
		} catch (FileNotFoundException ex) {}
	}
}