import java.io.File;

public class Driver {
  public static void main(String [] args) {
      // Constructors test
      Polynomial p = new Polynomial();

      double [] c1 = {1, 1};
      int [] e1 = {5, 6};
      Polynomial p1 = new Polynomial(c1, e1);
      System.out.println("Polynomial 1:");
      p1.print_polynomial();

      double [] c2 = {1, 1, -1};
      int [] e2 = {1, 2, 5};
      Polynomial p2 = new Polynomial(c2, e2);
      System.out.println("Polynomial 2:");
      p2.print_polynomial();

      Polynomial p3 = new Polynomial(new File("polynomial.txt"));
      System.out.println("Polynomial 3:");
      p3.print_polynomial();

      // Methods Test
      Polynomial sum = p1.add(p2);
      System.out.println("Polynomial 1 + Polynomial 2");
      sum.print_polynomial();


      Polynomial product = p1.multiply(p3);
      System.out.println("Polynomial 1 * Polynomial 3");
      product.print_polynomial();

      product.saveToFile("product.txt");

      System.out.println("product @ x=1 = " + product.evaluate(1));
      
      if(sum.hasRoot(1))
        System.out.println("1 is a root of sum");
      else
        System.out.println("1 is not a root of sum");
    }
}