public class Addition extends Operation{


    public Addition(){

        super("+");
    }

    @Override
    public double mathOperation(double value1, double value2){

        return value1 + value2;

    }

}
