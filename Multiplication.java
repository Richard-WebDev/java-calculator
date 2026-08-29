public class Multiplication extends Operation{


    public Multiplication(){

        super("*");
    }

    @Override
    public double mathOperation(double value1, double value2){

        return value1 * value2;

    }

}