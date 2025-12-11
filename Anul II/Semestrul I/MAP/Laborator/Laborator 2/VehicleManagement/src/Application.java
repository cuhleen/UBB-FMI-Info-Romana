import domain.Vehicle;
import repository.VehicleRepository;
import utils.MileageUnit;

public class Application {

	public static void main(String[] args) {
        MileageUnit mileageUnit = MileageUnit.KM;
		Vehicle vehicle = new Vehicle("BV 01 ABC", 100.0, 2020, mileageUnit);
		//TODO - instantiate a vehicle
		
		VehicleRepository repository = new VehicleRepository();
		repository.addVehicle(vehicle);
	
		for (int i = 0; i < repository.getNumberOfVehicles(); i++) {
			Vehicle retrievedVehicle = repository.getVehicleAtPosition(i);
			//TODO -print vehicle details for retrievedVehicle object
            retrievedVehicle.printVehicleDetails();
		}
	}

}
