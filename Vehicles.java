// Jackson Romie
// Class Vehicles

import java.util.*;
import java.io.*;
import java.nio.file.*;

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
    String[] line = in.nextLine().split(",");
    Vehicle car = new Vehicle(line);
    library.add(car);

    while(in.hasNextLine()) {
      String nextline = in.nextLine();
      String[] params = nextline.split(",");
      Vehicle anotherVehicle = new Vehicle(params);
      library.add(anotherVehicle);
    }
  }

  public void addVehicle() throws IOException {
    Vehicle vehicle = new Vehicle();
    library.add(vehicle);

    // Update the data file
    try {
      String line = "";
      line += vehicle.getVin() + ",";
      line += vehicle.getMake() + ",";
      line += vehicle.getModel() + ",";
      line += vehicle.getColor() + ",";
      line += Integer.toString(vehicle.getYear()) + ",";
      line += Double.toString(vehicle.getMsrp()) + ",";
      line += Double.toString(vehicle.getMilesSinceLastOilChange()) + ",";
      line += Double.toString(vehicle.getMileage()) + ",";
      line += Double.toString(vehicle.getWeight()) + "\r\n";

      Files.write(Paths.get("carData.csv"), line.getBytes(), StandardOpenOption.APPEND);
    } catch(IOException e) {
      throw(e);
    }
  }

  public void delete(int index) {
    File currentFile = new File("carData.csv");
    File tempFile = new File("temp.csv");
    String vin = "";
    try {
      vin = library.get(index).getVin();
      library.remove(index);
    } catch(IndexOutOfBoundsException e) {
      System.out.println("This index is out of bounds.");
      return;
    }

    try {
      BufferedReader reader = new BufferedReader(new FileReader(currentFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
      String line;
      while((line = reader.readLine()) != null) {
        String[] params = line.split(",");
        String fileVin = params[0];
        if(fileVin.equals(vin)) {
          continue;
        } else {
          writer.write(line + "\r\n");
        }
      }
      writer.close();
      boolean success = tempFile.renameTo(currentFile);
      if(success) {
        System.out.println("Vehicle successfully removed.");
      } else {
        System.out.println("Vehicle not correctly removed from data file.");
      }

    } catch(IOException e) {
      System.err.println("Unable to communicate with data files");
      System.exit(1);
    }
  }

  // Loop through and delete the first Vehicle that matches vin.
  // This method assumes the realistic that each vin is distinct
  // For large lists this can improve performance
  public void delete(String vin) {
    File currentFile = new File("carData.csv");
    File tempFile = new File("temp.csv");
    boolean found = false;
    for(int a = 0; a < library.size(); a++) {
      if(library.get(a).getVin().equalsIgnoreCase(vin)) {
        library.remove(a);
        found = true;
        break;
      }
    }

    try {
      BufferedReader reader = new BufferedReader(new FileReader(currentFile));
      BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
      String line;
      while((line = reader.readLine()) != null) {
        String[] params = line.split(",");
        String fileVin = params[0];
        if(fileVin.equalsIgnoreCase(vin)) {
          continue;
        } else {
          writer.write(line + "\r\n");
        }
      }
      writer.close();
      boolean success = tempFile.renameTo(currentFile);
      if(success) {
        System.out.println("Vehicle successfully removed.");
      } else {
        System.out.println("Vehicle not correctly removed from data file.");
      }

    } catch(IOException e) {
      System.err.println("Unable to communicate with data files");
      System.exit(1);
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

  public void findByYear(int yr) {
    ArrayList<Vehicle> list = new ArrayList<Vehicle>();
    for(int a = 0; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.getYear() == yr) {
        list.add(current);
      }
    }

    int listLen = list.size();
    if(listLen == 0) {
      System.out.println("No vehicles found in inventory that are " + yr + " models.");
    } else {
      for(int a = 0; a < listLen; a++) {
        list.get(a).printVehicle();
      }
    }
  }

  public void findByMake(String mk) {
    ArrayList<Vehicle> list = new ArrayList<Vehicle>();
    for(int a = 0; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.getMake().equalsIgnoreCase(mk)) {
        list.add(current);
      }
    }

    int listLen = list.size();
    if(listLen == 0) {
      System.out.println("No vehicles found in inventory that are " + mk + "s.");
    } else {
      for(int a = 0; a < listLen; a++) {
        list.get(a).printVehicle();
      }
    }
  }

  public void addToMileage(double miles, String... vins) {
    int len = vins.length;
    int num = len;
    ArrayList<Vehicle> list = new ArrayList<Vehicle>();
    for(int a = 0; a < len; a++) {
      boolean found = false;
      for(int b = 0; b < library.size(); b++) {
        Vehicle current = library.get(b);
        if(current.getVin().equals(vins[a])) {
          current.changeMileage(miles);
          found = true;
          break;
        }
      }
      if(!found) {
        System.out.println("VIN \"" + vins[a] + "\" not found.");
        num--;
      }
    }
    System.out.println("Increased the miles on " + num + " vehicles.");
  }

  public void avgMsrp() {
    int num = library.size();
    double sum = 0;
    for(int a = 0; a < num; a++) {
      sum += library.get(a).getMsrp();
    }

    sum /= num;
    double average = (sum * 100.0);
    average = Math.round(average);
    average /= 100;
    System.out.println("The average MSRP in this inventory is $" + average);
  }

  public void maxMsrp() {
    double max = 0;
    for(int a = 0; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.getMsrp() > max) {
        max = current.getMsrp();
      }
    }

    max *= 100;
    max = Math.round(max);
    max /= 100;

    System.out.println("The max MSRP in this inventory is $" + max);
  }

  public void minMsrp() {
    double min = library.get(0).getMsrp();
    for(int a = 1; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.getMsrp() < min) {
        min = current.getMsrp();
      }
    }

    min *= 100;
    min = Math.round(min);
    min /= 100;

    System.out.println("The min MSRP in this inventory is $" + min);
  }

  public void avgMileage() {
    int num = library.size();
    double sum = 0;
    for(int a = 0; a < num; a++) {
      sum += library.get(a).getMileage();
    }

    double avg = sum / num;
    avg *= 100;
    avg = Math.round(avg);
    avg /= 100;

    System.out.println("The average mileage in this inventory is " + avg + " miles.");
  }

  public void maxMileage() {
    double max = 0;
    for(int a = 0; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.getMileage() > max) {
        max = current.getMileage();
      }
    }

    max *= 100;
    max = Math.round(max);
    max /= 100;

    System.out.println("The vehicle with the most miles in this inventory has " + max + " miles.");
  }

  public void minMileage() {
    double min = library.get(0).getMileage();
    for(int a = 1; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.getMileage() < min) {
        min = current.getMileage();
      }
    }

    min *= 100;
    min = Math.round(min);
    min /= 100;

    System.out.println("The vehicle with the least amount of miles on it in this inventory has " + min + " miles.");
  }

  public void getVehiclesNeedingOil() {
    for(int a = 0; a < library.size(); a++) {
      Vehicle current = library.get(a);
      if(current.needsOilChange()) {
        current.printVehicle();
      }
    }
  }

  public static void listOptions() {
    System.out.println("- addVehicle");
    System.out.println("- deleteIndex(x), where x is the index of a vehicle in the list."); // error checking needed
    System.out.println("- delete(VIN), where VIN is a particular vehicle's VIN"); // error checking needed
    System.out.println("- listAll -- list all vehicles in inventory");
    System.out.println("- getInfo(VIN) -- print the information for a vehicle with VIN. The vehicle must be in the inventory, however.");
    System.out.println("- findByYear(yr)"); // write this method
    System.out.println("- findByMake(mk)"); // write this method
    System.out.println("- addToMileage(vehicle(s), miles) -- this will take increase the mileage on each of the vehicles entered by the amount of miles specified."); // write this method
    System.out.println("- help -- print this option menu");
    System.out.println("- exit");
  }

  public static void main(String[] args) {
    boolean quit = false;
    Vehicles garage = new Vehicles("carData.csv");
    Scanner in = new Scanner(System.in);
    System.out.println("\n\n\nHello! This program is a basic console vehicle inventory management system.");
    System.out.println("The file \"carData.txt\" has already been read, and the inventory has been filled with its vehicle data.");
    System.out.println("You can type the following commands:\n");
    listOptions();

    while(!quit) {
      System.out.print("\n--> ");
      String command = in.nextLine().replaceAll("\\s+", "");

      switch(command.toUpperCase()) {
        case "ADDVEHICLE":
          try {
            garage.addVehicle();
          } catch(IOException e) {
            System.err.println("Cannot find data file");
            System.exit(1);
          }
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
            try{
              String vin = upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")"));
              garage.delete(vin);
            } catch (StringIndexOutOfBoundsException e) {
              System.out.println("This is not a valid command.");
              continue;
            }
          }

          else if(upper.contains("GETINFO")) {
            String vin = upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")"));
            garage.getInfo(vin);
          }

          else if(upper.contains("FINDBYYEAR")) {
            int yr = Integer.parseInt(upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")")));
            garage.findByYear(yr);
          }

          else if(upper.contains("FINDBYMAKE")) {
            String mk = upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")"));
            garage.findByMake(mk);
          }

          else if(upper.contains("ADDTOMILEAGE")) {
            try {
              String[] params = upper.substring(upper.indexOf("(") + 1, upper.lastIndexOf(")")).split(",");
              String[] vins = new String[params.length - 1];
              for(int a = 1; a < params.length; a++) {
                vins[a - 1] = params[a];
              }

              double miles = Double.parseDouble(params[0]);

              garage.addToMileage(miles, vins);
            } catch (StringIndexOutOfBoundsException | NumberFormatException e) {
              System.out.println("Incorrect format. Be sure that you enter the available commands as follows:\n");
              listOptions();
            }
          }

          else if(upper.equals("AVERAGEMSRP")) {
            garage.avgMsrp();
          }

          else if(upper.equals("MAXMSRP")) {
            garage.maxMsrp();
          }

          else if(upper.equals("MINMSRP")) {
            garage.minMsrp();
          }

          else if(upper.equals("AVERAGEMILEAGE")) {
            garage.avgMileage();
          }

          else if(upper.equals("MAXMILEAGE")) {
            garage.maxMileage();
          }

          else if(upper.equals("MINMILEAGE")) {
            garage.minMileage();
          }

          else if(upper.equals("NEEDOIL")) {
            garage.getVehiclesNeedingOil();
          }

          else {
            System.out.println("That is not a valid command. Please try again. Enter \"help\" for a list of commands.\n");
          }
      }
    }
  }

}
