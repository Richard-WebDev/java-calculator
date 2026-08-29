public abstract class  Operation {

    private String nameOperation;
    

    public Operation(String nameOperation){

        this.nameOperation = nameOperation;

    }

    public String getNameOperation(){

        return this.nameOperation;
    }  



    public abstract double mathOperation(double value1, double value2);

}