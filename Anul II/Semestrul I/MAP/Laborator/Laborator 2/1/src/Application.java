public class Application {
    public static void main(String args[]){
        Car car = new Car(2020, 400.5);
        AudiCar audiCar = new AudiCar(2022, 1500, "Germany");
        PorscheCar porscheCar = new PorscheCar(2024, 2020, "Macan");
        PorscheCar anotherPorscheCar = new PorscheCar(2021, 2222, "Taycan");

        System.out.println(car.toString());
        System.out.println(audiCar.toString());
        System.out.println(porscheCar.toString());
        System.out.println(anotherPorscheCar.toString());

        //porscheCar = audiCar; // eroare
        System.out.println(porscheCar.toString());
    }
}

