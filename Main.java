package Car_Rental_Java_project;

import java.util.*;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String customerName;

    public Customer(String customerId, String customerName) {
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }

}

class carRentalSystem {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public carRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCars(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int rentalDays) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, rentalDays));
        } else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car) {
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented !!");

        }
        car.returnCar();
    }

    public void menu() {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("====CAR RENTAL SYSTEM====");
            System.out.println("1.Rent a car");
            System.out.println("2.Return a car");
            System.out.println("3.Exit");
            System.out.println("Enter your choice");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter your name:");
                String customerName = sc.nextLine();
                System.out.println("\n");
                System.out.println("==== Available Cars ====");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(
                                "CarID:" + car.getCarId() + " | " + "Brand:" + car.getBrand() + " -- " + "Model:"
                                        + car.getModel());
                    }
                }
                System.out.println("\n");
                System.out.print("Enter the Car Id you want to Rent:");
                String carId = sc.nextLine();
                System.out.print("Enter the number of days you want the car rented:");
                int rentalDays = sc.nextInt();
                sc.nextLine();
                Customer newCustomer = new Customer("cus" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPricec = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n");
                    System.out.println("==== RENTAL INFORMATION ====");
                    System.out.println("Customer name:" + newCustomer.getCustomerName());
                    System.out.println("Customer ID :" + newCustomer.getCustomerId());
                    System.out.println("Brand:" + selectedCar.getBrand() + " " + "Model :" + selectedCar.getModel());
                    System.out.println("Rental Days :" + rentalDays);
                    System.out.println("Total price :" + totalPricec + "$");
                    System.out.println("\n");

                    System.out.println("Confirm your Rental(Y/N)");
                    String cfmChoice = sc.nextLine();

                    if (cfmChoice.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                    } else {
                        System.out.println("Rental Cancelled !!");
                    }
                } else {
                    System.out.println("Invalid Car id or Car no Available!!");
                }
            } else if (choice == 2) {
                System.out.println("==== RETURN CAR ====");
                System.out.print("Enter the car id you want to return:");
                String carid = sc.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carid) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getCustomerName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                }

            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
            System.out.println("\nThank you for using the Car Rental System!");
        }
        sc.close();
    }
}

public class Main {
    public static void main(String[] args) {

        carRentalSystem crs = new carRentalSystem();

        Car car1 = new Car("101", "Benz", "S1", 1200);
        Car car2 = new Car("102", "TATA", "S2", 800);
        Car car3 = new Car("103", "BMW", "S3", 1500);

        crs.addCars(car1);
        crs.addCars(car2);
        crs.addCars(car3);

        crs.menu();
    }
}
