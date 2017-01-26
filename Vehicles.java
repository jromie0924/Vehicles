import java.util.*;
import java.io.*;

public class Vehicles {

  private ArrayList<Vehicle> library;

  // Default constructor
  public Vehicles() {
    library = new ArrayList<Vehicle>();
  }

  // Constructor
  public Vehicles(String filename) {
    Scanner in = null;
    try {
      in = new Scanner(new File(filename));
    } catch(FileNotFoundException e) {
      System.err.println(filename + " not found. Exiting.");
      System.exit(1);
    }

    library = new ArrayList<Vehicle>();

    in.nextLine();
    String[] line = in.nextLine().split("\t");
    Vehicle car = new Vehicle(line);
    library.add(car);

    while(in.hasNextLine()) {
      String nextline = in.nextLine();
      String[] params = nextline.split("\t");
      Vehicle anotherVehicle = new Vehicle(params);
      library.add(anotherVehicle);
    }
  }

  public void addVehicle() {
    Vehicle vehicle = new Vehicle();
    library.add(vehicle);
  }

  public void delete(int index) {
    try{
      library.remove(index);
    } catch(IndexOutOfBoundsException e) {
      System.out.println("This index is out of bounds.");
    }
  }

  // Loop through and delete the first Vehicle that matches vin.
  // This method assumes the realistic that each vin is distinct
  // For large lists this can improve performance
  public void delete(String vin) {
    boolean found = false;
    for(int a = 0; a < library.size(); a++) {
      if(library.get(a).getVin().equals(vin)) {
        library.remove(a);
        found = true;
        break;
      }
    }

    if(!found) {
      System.out.println("This VIN was not found in inventory.");
    }
  }

  public void getInfo(String vin) {
    boolean found = false;
    for(int a = 0; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.getVin().equalsIgnoreCase(vin)) {
        found = true;
        current.printVehicle();
      }
    }
    if(!found) {
      System.out.println("\nThis VIN was not found in inventory.\n");
    }
  }

  public void printVehicles() {
    for(int a = 0; a < library.size(); a++) {
      library.get(a).printVehicle();
    }
  }

  public static void listOptions() {
    System.out.println("- addVehicle");
    System.out.println("- deleteIndex(x), where x is the index of a vehicle in the list."); // error checking needed
    System.out.println("- delete(VIN), where VIN is a particular vehicle's VIN"); // error checking needed
    System.out.println("- listAll");
    System.out.println("- getInfo(VIN) will get print the information for a vehicle with VIN. The vehicle must be in the inventory, however.");
    System.out.println("- findByYear(yr)"); // write this method
    System.out.println("- findByMake(mk)"); // write this method
    System.out.println("- addToMileage(vehicle(s), miles) -- this will take increase the mileage on each of the vehicles entered by the amount of miles specified."); // write this method
    System.out.println("- Help, print this option menu");
    System.out.println("- exit");
  }

  public static void main(String[] args) {
    boolean quit = false;
    Vehicles garage = new Vehicles("carData.txt");
    Scanner in = new Scanner(System.in);
    System.out.println("\n\n\nHello! This program is a basic console vehicle inventory management system.\n");
    System.out.println("You can type the following commands:");
    listOptions();

    while(!quit) {
      System.out.print("\n--> ");
      String command = in.next();

      switch(command.toUpperCase()) {
        case "ADDVEHICLE":
          garage.addVehicle();
          break;

        case "LISTALL":
          garage.printVehicles();
          break;

        case "EXIT":
          quit = true;
          break;

        case "HELP":
          System.out.println();
          listOptions();
          continue;

        default:
          String upper = command.toUpperCase();
          if(upper.contains("DELETEINDEX")) {
            int idx = Integer.parseInt(upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")")));
            garage.delete(idx);
          }

          else if(upper.contains("DELETE")) {
            String vin = upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")"));
            garage.delete(vin);
          }

          else if(upper.contains("GETINFO")) {
            String vin = upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")"));
            garage.getInfo(vin);
          }

          // continue here
      }
    }
  }

}
