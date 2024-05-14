class Polynomial {
  private double[] coefficients;

  Polynomial() {
    this.coefficients = new double[]{0};
  }

  Polynomial(double[] coefficients) {
    this.coefficients = coefficients.clone();
  }

  public Polynomial add(Polynomial p) {
    int max_len = Math.max(this.coefficients.length, p.coefficients.length);
    double[] sum_coefficients = new double[max_len];
    
    for (int i = 0; i < max_len; i++){
      int sum = 0;
      if (i < this.coefficients.length) sum += this.coefficients[i];
      if (i < p.coefficients.length)  sum += p.coefficients[i];
      sum_coefficients[i] = sum;
    }

    return new Polynomial(sum_coefficients);
  }

  public double evaluate(double x){
    double result = 0;

    for (int i = 0; i < this.coefficients.length; i++)
      result += this.coefficients[i]*Math.pow(x, i);

    return result;
  }

  public boolean hasRoot(double x){
    return evaluate(x) == 0;
  }

  public void print_polynomial() {
    for (double num:this.coefficients)
      System.out.println(num);
  }
}