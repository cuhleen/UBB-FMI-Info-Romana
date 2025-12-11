public class PorscheCar extends Car{
    private String model;

    PorscheCar(int year, double price, String model){
        super(year, price);
        this.model = model;
    }

    @Override
    public String toString(){
        return super.toString() + "Model: " + model;
    }
}
