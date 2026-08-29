public class Division extends Operation{


    public Division(){

        super("/");
    }

    @Override
    public double mathOperation(double value1, double value2){

        if (value2 == 0) {

            throw new ArithmeticException("Error");

        }

        return value1 / value2;

    }

}