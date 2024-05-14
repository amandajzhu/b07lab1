public class Polynomial {
	double[] coeff;

	public Polynomial() {
		coeff = new double[1];
		coeff[0] = 0;
	}

	public Polynomial(double[] arr) {
		coeff = arr;
	}

	public Polynomial add(Polynomial p) {
		Polynomial sum;
		if (coeff.length >= p.coeff.length) {
			sum = new Polynomial(coeff);
			for (int i = 0; i < p.coeff.length; i++) {
				sum.coeff[i] += p.coeff[i];
			}
		} else {
			sum = new Polynomial(p.coeff);
			for (int i = 0; i < coeff.length; i++) {
				sum.coeff[i] += coeff[i];
			}
		}
		return sum;
	}

	public double evaluate(double d) {
		double result = 0;
		for (int i = 0; i < coeff.length; i++) {
			result += coeff[i] * Math.pow(d, i);
		}
		return result;
	}

	public boolean hasRoot(double d) {
		if (evaluate(d) == 0) return true;
		return false;
	}
}