import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int [] e1 = {0,3};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,-9};
        int [] e2 = {1,4};
        Polynomial p2 = new Polynomial(c2, e2);

        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1)); //expect 5.8041
        if(s.hasRoot(1))
            System.out.println("1 is a root of s"); //expect this
        else
            System.out.println("1 is not a root of s");
        
        Polynomial product = p1.multiply(p2);
        System.out.println("product(1) = " + product.evaluate(1)); //expect -121

        File f = new File("InFile.txt");
        try {
			PrintStream output = new PrintStream("InFile.txt");
			output.print("5.0-3.0x2+7.0x8");
			output.close();
		} catch (FileNotFoundException ex) {}
        Polynomial p3 = new Polynomial(f);
        p3.saveToFile("OutFile.txt");

        Polynomial s2 = p1.add(p3);
        System.out.println("s2(-1) = " + s2.evaluate(-1)); //expect 10
        if (s2.hasRoot(1))
            System.out.println("1 is a root of s2");
        else
            System.out.println("1 is not a root of s2"); //expect this
    }
}