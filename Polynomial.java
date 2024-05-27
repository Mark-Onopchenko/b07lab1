import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Polynomial {
  private double[] coefficients;
  private int[] exponents;

  Polynomial() {
    this.coefficients = new double[]{0};
    this.exponents = new int[]{0};
  }

  Polynomial(double[] coefficients, int[] exponents) {
    this.coefficients = coefficients.clone();
    this.exponents = exponents.clone();
  }

  Polynomial(File file) {
    String text = "";
    try {
      Scanner scanner = new Scanner(file);
      text = scanner.nextLine();
      scanner.close();
    } catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    
    text = text.replaceAll("-", "+-");
    String[] p_arr = text.split("\\+");
    this.coefficients = new double[p_arr.length];
    this.exponents = new int[p_arr.length];
    for (int i = 0; i < p_arr.length; i++) {
      String[] components = p_arr[i].split("x");
      this.coefficients[i] = Double.parseDouble(components[0]);
      if (components.length > 1)
        this.exponents[i] = Integer.parseInt(components[1]);
      else
        this.exponents[i] = 0;
    }
  }

  public Polynomial add(Polynomial p) {
    List<Double> sum_coefficients = new ArrayList<>();
    List<Integer> sum_exponents = new ArrayList<>();

    int p1_len = this.exponents.length;
    int p2_len = p.exponents.length;
    
    int e1 = 0;
    int e2 = 0;
    while (e1 < p1_len || e2 < p2_len){
      // Appends the bigger polynomial to the sum
      if (e1 >= p1_len) {
        sum_coefficients.add(p.coefficients[e2]);
        sum_exponents.add(p.exponents[e2]);
        e2++;
        continue;
      } else if (e2 >= p2_len) {
        sum_coefficients.add(this.coefficients[e1]);
        sum_exponents.add(this.exponents[e1]);
        e1++;
        continue;
      }

      // Sums coefficients with same exponents
      if (this.exponents[e1] == p.exponents[e2]) {
        double c_sum = this.coefficients[e1] + p.coefficients[e2];
        if (c_sum != 0){
          sum_coefficients.add(this.coefficients[e1] + p.coefficients[e2]);
          sum_exponents.add(this.exponents[e1]);
        }
        e1++;
        e2++;
        continue;
      }

      if (this.exponents[e1] < p.exponents[e2]) {
        sum_coefficients.add(this.coefficients[e1]);
        sum_exponents.add(this.exponents[e1]);
        e1++;
      }
      else if (this.exponents[e1] > p.exponents[e2]) {
        sum_coefficients.add(p.coefficients[e2]);
        sum_exponents.add(p.exponents[e2]);
        e2++;
      }
    }

    // Length of the resulting polynomial
    int p_len = sum_coefficients.size();
    double[] p_coefficients = new double[p_len];
    int[] p_exps = new int[p_len];

    // Convert from Lists to arrays
    for (int i = 0; i < p_len; i++) {
      p_coefficients[i] = sum_coefficients.get(i);
      p_exps[i] = sum_exponents.get(i);
    }

    return new Polynomial(p_coefficients, p_exps);
  }

  public Polynomial multiply(Polynomial p) {
    int p1_len = this.coefficients.length;
    int p2_len = p.coefficients.length;

    Polynomial[] polynomials = new Polynomial[p1_len];

    for (int i = 0; i < p1_len; i++) {
      double[] coeffs = new double[p2_len];
      int[] exps = new int[p2_len];

      for (int j = 0; j < p2_len; j++) {
        coeffs[j] = this.coefficients[i] * p.coefficients[j];
        exps[j] = this.exponents[i] + p.exponents[j];
      }

      polynomials[i] = new Polynomial(coeffs, exps);
    }

    Polynomial sum = polynomials[0];

    for (int i = 0; i < p1_len - 1; i++)
      sum = sum.add(polynomials[i+1]);

    return sum;
  }

  public double evaluate(double x){
    double result = 0;

    for (int i = 0; i < this.exponents.length; i++)
      result += this.coefficients[i]*Math.pow(x, exponents[i]);

    return result;
  }

  public boolean hasRoot(double x){
    return evaluate(x) == 0;
  }

  public void saveToFile(String file_name) {
    String polynomial = "";

    for (int i = 0; i < this.coefficients.length; i++){
      if (i != 0 && coefficients[i] >= 0)
        polynomial += "+";
        
      polynomial += Double.toString(coefficients[i]) + (exponents[i] == 0 ? "" : "x" + Integer.toString(exponents[i]));
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file_name, false))) {
      writer.append(polynomial);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void print_polynomial() {
    for (int i = 0; i < coefficients.length; i++)
      System.out.print("(" + coefficients[i] + ")x^" + exponents[i] + " ");
    System.out.println();
  }
}