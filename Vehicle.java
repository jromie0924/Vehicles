import java.util.*;
import java.io.*;

public class Vehicle {
  // Instance variables
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

  public Vehicle() {
    Scanner reader = new Scanner(System.in);
    System.out.print("What's the vehicle's VIN? --> ");
    vin = reader.next();
    System.out.print("\nWho makes the vehicle? --> ");
    make = reader.next();
    System.out.print("\nWhat model? --> ");
    model = reader.next();
    System.out.print("\nWhat color? --> ");
    color = reader.next();
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

    if(make.equalsIgnoreCase("SUBARU")) {
      if(milesSinceLastOilChange >= 7000) {
        dueForOilChange = true;
      } else {
        dueForOilChange = false;
      }
    } else {
      if(milesSinceLastOilChange >= 3000) {
        dueForOilChange = true;
      } else {
        dueForOilChange = false;
      }
    }
    System.out.println("\nVehicle added.");
  }

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
        oilChangeFreq = 7000;
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
        oilChangeFreq = 0;
        milesSinceLastOilChange = -1;
        break;

      case "Volvo":
        disclaimer = "\u00a9 Copyright AB Volvo 2017";
        break;

      default:
        disclaimer = "";
        break;
    }

    if(milesSinceLastOilChange >= oilChangeFreq) {
      dueForOilChange = true;
    } else {
      dueForOilChange = false;
    }
  }

  public void printVehicle() {
    System.out.println(year + " " + make + " " + model);
    System.out.println("VIN: " + vin);
    System.out.println("Color: " + color);
    System.out.println("Weight: " + weight);
    System.out.println("MSRP: $" + msrp);
    System.out.println("Mileage: " + numMiles);
    System.out.println("Miles Since Last Oil Change: " + milesSinceLastOilChange);
    if(dueForOilChange) {
      System.out.println("*** NEEDS OIL CHANGE ***");
    }
    System.out.println(disclaimer);
    System.out.print("\n\n");
  }

  public void changeMileage(double miles) {
    numMiles += miles;
  }

  public String getVin() {
    return vin;
  }

  public String getMake() {
    return make;
  }

  public String getColor() {
    return color;
  }

  public int getYear() {
    return year;
  }

  public boolean needsOilChange() {
    return dueForOilChange;
  }

  public double getMsrp() {
    return msrp;
  }

  public double getMilesSinceLastOilChange() {
    return milesSinceLastOilChange;
  }

  public double getMileage() {
    return numMiles;
  }

  public double getWeight() {
    return weight;
  }
}
