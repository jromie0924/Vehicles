// Jackson Romie
// Class Vehicle

import java.util.*;
import java.io.*;

// This class defines a vehicle.
public class Vehicle {
  private String vin;
  private String make;
  private String model;
  private String color;
  private String disclaimer;

  private int year;

  private boolean dueForOilChange;

  private double msrp;
  private double milesSinceLastOilChange;
  private double oilChangeFreq;
  private double numMiles;
  private double weight;

  // Default constructor
  // Promts user for vehicle parameters, then updates the car data file realtime.
  public Vehicle() {
    Scanner reader = new Scanner(System.in);
    System.out.print("What's the vehicle's VIN? --> ");
    vin = reader.nextLine();
    System.out.print("\nWho makes the vehicle? --> ");
    make = reader.nextLine();
    System.out.print("\nWhat model? --> ");
    model = reader.nextLine();
    System.out.print("\nWhat color? --> ");
    color = reader.nextLine();
    System.out.print("\nWhat year? --> ");

    try {
      year = reader.nextInt();
    } catch(Exception e) {
      System.out.println("This field must be a number.");
    }

    System.out.print("\nWhat's the " + year + " " + make + " " + model + "'s MSRP? --> ");

    try {
      msrp = reader.nextDouble();
    } catch(Exception e) {
      System.out.println("This field must be a number.");
    }

    if(!make.equalsIgnoreCase("TESLA")) {
      System.out.print("\nHow many miles since the last oil change? --> ");
      try {
        milesSinceLastOilChange = reader.nextDouble();
      } catch(Exception e) {
        System.out.println("This field must be a number.");
      }

    } else {
      milesSinceLastOilChange = -1;
    }

    System.out.print("\nHow many miles are on it? --> ");

    try {
      numMiles = reader.nextDouble();
    } catch(Exception e) {
      System.out.println("This field must be a number.");
    }

    System.out.print("\nHow much does the car weigh? --> ");

    try {
      weight = reader.nextDouble();
    } catch(Exception e) {
      System.out.println("This field must be a number.");
    }

    switch (make) {
      case "Subaru":
        disclaimer = "2016 \u00a9 Fuji Heavy Industries Ltd. All rights reserved";
        break;

      case "Ford":
        disclaimer = "\u00a9 2015 Ford Motor Company";
        break;

      case "BMW":
        disclaimer = "\u00a9 Copyright BMW AG, Munich, Germany";
        break;

      case "Mitsubishi":
        disclaimer = "\u00a9 Copyright 2017 Mitsubishi Corporation. All Rights Reserved";
        break;

      case "Porsche":
        disclaimer = "\u00a9 Porsche Cars North America, Inc.";
        break;

      case "Tesla":
        disclaimer = "Tesla Motors \u00a9 2017";
        break;

      case "Volvo":
        disclaimer = "\u00a9 Copyright AB Volvo 2017";
        break;

      default:
        disclaimer = "";
        break;
    }

    oilChangeFreq = 3000;

    Scanner reqReader;
    try {
      reqReader = new Scanner(new File("specialReqs.csv"));
      ArrayList<String> makes = new ArrayList<String>();
      while(reqReader.hasNextLine()) {
        String[] params = reqReader.nextLine().split(",");
        if(make.equalsIgnoreCase(params[0])) {
          oilChangeFreq = Double.parseDouble(params[1]);
        }
      }
    } catch(FileNotFoundException e) {
      System.err.println("Could not find special requirements file.");
      System.exit(1);
    }

    if(milesSinceLastOilChange >= oilChangeFreq) {
      dueForOilChange = true;
    } else {
      dueForOilChange = false;
    }

    System.out.println("\nVehicle added.");
  }

  // Constructor used at program startup
  // The "params" parameter is a line from the carData.csv data file.
  public Vehicle(String[] params) {
    vin = params[0];
    make = params[1];
    model = params[2];
    color = params[3];

    year = Integer.parseInt(params[4]);

    msrp = Double.parseDouble(params[5]);
    milesSinceLastOilChange = Double.parseDouble(params[6]);
    numMiles = Double.parseDouble(params[7]);
    weight = Double.parseDouble(params[8]);
    oilChangeFreq = 3000;

    switch (make) {
      case "Subaru":
        disclaimer = "2016 \u00a9 Fuji Heavy Industries Ltd. All rights reserved";
        break;

      case "Ford":
        disclaimer = "\u00a9 2015 Ford Motor Company";
        break;

      case "BMW":
        disclaimer = "\u00a9 Copyright BMW AG, Munich, Germany";
        break;

      case "Mitsubishi":
        disclaimer = "\u00a9 Copyright 2017 Mitsubishi Corporation. All Rights Reserved";
        break;

      case "Porsche":
        disclaimer = "\u00a9 Porsche Cars North America, Inc.";
        break;

      case "Tesla":
        disclaimer = "Tesla Motors \u00a9 2017";
        milesSinceLastOilChange = -1;
        break;

      case "Volvo":
        disclaimer = "\u00a9 Copyright AB Volvo 2017";
        break;

      default:
        disclaimer = "";
        break;
    }

    Scanner reqReader;
    try {
      reqReader = new Scanner(new File("specialReqs.csv"));
      ArrayList<String> makes = new ArrayList<String>();
      while(reqReader.hasNextLine()) {
        String[] reqParams = reqReader.nextLine().split(",");
        if(make.equalsIgnoreCase(reqParams[0])) {
          oilChangeFreq = Double.parseDouble(reqParams[1]);
        }
      }
    } catch(FileNotFoundException e) {
      System.err.println("Could not find special requirements file.");
      System.exit(1);
    }
  //  System.out.println(make);
  //  System.out.println(oilChangeFreq);
    if(milesSinceLastOilChange >= oilChangeFreq) {
      dueForOilChange = true;
    } else {
      dueForOilChange = false;
    }
  }

  // Prints out vehicle's information
  public void printVehicle() {
    System.out.println(year + " " + make + " " + model);
    System.out.println("VIN: " + vin);
    System.out.println("Color: " + color);
    System.out.println("Weight: " + weight + " lbs");
    System.out.println("MSRP: $" + msrp);
    System.out.println("Mileage: " + numMiles);
    System.out.println("Miles Since Last Oil Change: " + milesSinceLastOilChange);
    if(dueForOilChange) {
      System.out.println("*** NEEDS OIL CHANGE ***");
    }
    System.out.println(disclaimer);
    System.out.print("\n\n");
  }

  // Alter vehicle's oil changing requirements.
  public void changeOilNeeds(double newFreq) {
    oilChangeFreq = newFreq;
    if(milesSinceLastOilChange >= oilChangeFreq) {
      dueForOilChange = true;
    } else {
      dueForOilChange = false;
    }
  }

  // Change the vehicle's mileage.
  public void changeMileage(double miles) {
    numMiles += miles;
  }

  // Return vehicle's VIN.
  public String getVin() {
    return vin;
  }

  // Return vehicle's manufacturer name.
  public String getMake() {
    return make;
  }

  // Return vehicle model.
  public String getModel() {
    return model;
  }

  // Return vehicle color.
  public String getColor() {
    return color;
  }

  // Return vehicle model year.
  public int getYear() {
    return year;
  }

  // Return whether vehicle requires oil change.
  public boolean needsOilChange() {
    return dueForOilChange;
  }

  // Return vehicle MSRP.
  public double getMsrp() {
    return msrp;
  }

  // Return miles since vehicle's last oil change.
  public double getMilesSinceLastOilChange() {
    return milesSinceLastOilChange;
  }

  // Return how many miles are on vehile.
  public double getMileage() {
    return numMiles;
  }

  // Return vehicle weight.
  public double getWeight() {
    return weight;
  }
}
