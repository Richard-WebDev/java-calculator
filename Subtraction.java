public class Subtraction extends Operation{


    public Subtraction(){

        super("-");
    }

    @Override
    public double mathOperation(double value1, double value2){

        return value1 - value2;

    }

}