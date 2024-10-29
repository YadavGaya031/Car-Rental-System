import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;

    private String brand;

    private String model;

    private double rent;

    private boolean isAvailable;

    public Car(String carId, String brand, String model, double rent){
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.rent = rent;
        this.isAvailable = true;
    }

    public String getCarId(){
        return carId;
    }

    public String getBrand(){
        return brand;
    }

    public String getModel(){
        return model;
    }

    public double calculaterent(int rentaldays){
        return rent * rentaldays;
    }

    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getName(){
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days){
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getDays(){
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;

    private List<Customer> customers;

    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void  addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days){
        if(car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else{
            System.out.println("Car is not available currently.");
        }
    }

    public void returnCar(Car car){
        car.returnCar();
        Rental rentalToRemove = null;
        for(Rental rental : rentals){
            if(rental.getCar() == car){
                rentalToRemove = rental;
                break;
            }
        }
        if(rentalToRemove != null){
            rentals.remove(rentalToRemove);
        } else{
            System.out.println("Cas was not rented.");
        }
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("Car Rental System");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choices: ");

            int choice = sc.nextInt();
            sc.nextLine();

            if(choice == 1){
                System.out.println("Rent a Car");
                System.out.print("Enter your name: ");
                String customerName = sc.nextLine();

                System.out.println("Available Cars:");
                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId() + " - " + car.getBrand() + " - " + car.getModel());
                    }
                }

                System.out.print("Enter The Car Id you want to rent:");
                String carId = sc.nextLine();

                System.out.print("Enter the number the days for rental:");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer(   "CUS" + (customers.size() + 1), customerName);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for(Car car : cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if(selectedCar != null){
                    double totalPrice = selectedCar.calculaterent(rentalDays);
                    System.out.println("Rental Information");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price : $%.2f%n", totalPrice);

                    System.out.print("\nConfirm rental [Y/N]: ");
                    String confirm = sc. nextLine();

                    if(confirm.equalsIgnoreCase( "Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental Cancelled.");
                    }
                } else{
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if(choice == 2){
                System.out.println("\nReturn a Car\n");
                System.out.println("Enter the car ID you want to return: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for(Car car : cars) {
                    if(car.getCarId().equals(carId) && !car.isAvailable()) {
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


                if(carToReturn != null) {
                    returnCar(carToReturn);
                    System.out.println("car returned successfully by " + customer.getName());
                } else{
                    System.out.println("car was not rented or rental information missing.");
                }
            } else{
                System.out.println("Invalid car ID or car is not rented.");
            }
            }else if (choice == 3){
                break;
            } else{
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the car Rental System!");
    }
}

public class Main{
    public static void main(String[] args){
        CarRentalSystem rentalSystem = new CarRentalSystem();

        Car car1 = new Car("C001",  "TOYOTA",  "Innova", 60.0);
        Car car2 = new Car("C002",  "Mahindra", "Scorpio", 70.0);
        Car car3 = new Car("C003", "Mahindra", "Thar", 150.0);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        rentalSystem.menu();
    }
}
