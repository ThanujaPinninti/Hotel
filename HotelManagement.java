import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Serializable class for food items
class Food implements Serializable {
    int itemno;
    int quantity;
    float price;

    // Constructor to initialize food item with item number and quantity
    Food(int itemno, int quantity) {
        this.itemno = itemno;
        this.quantity = quantity;
        // Set price based on item number
        switch (itemno) {
            case 1: price = quantity * 50; break;
            case 2: price = quantity * 60; break;
            case 3: price = quantity * 70; break;
            case 4: price = quantity * 30; break;
        }
    }
}

// Serializable class for single room
class Singleroom implements Serializable {
    String name;
    String contact;
    String gender;
    String identityProfile;
    ArrayList<Food> food = new ArrayList<>();

    // Default constructor
    Singleroom() {
        this.name = "";
    }

    // Constructor to initialize room with customer details
    Singleroom(String name, String contact, String gender, String identityProfile) {
        this.name = name;
        this.contact = contact;
        this.gender = gender;
        this.identityProfile = identityProfile;
    }
}

// Serializable class for double room, extending single room
class Doubleroom extends Singleroom implements Serializable {
    String name2;
    String contact2;
    String gender2;
    String identityProfile2;

    // Default constructor
    Doubleroom() {
        this.name = "";
        this.name2 = "";
    }

    // Constructor to initialize room with details of two customers
    Doubleroom(String name, String contact, String gender, String identityProfile, String name2, String contact2, String gender2, String identityProfile2) {
        super(name, contact, gender, identityProfile);
        this.name2 = name2;
        this.contact2 = contact2;
        this.gender2 = gender2;
        this.identityProfile2 = identityProfile2;
    }
}

// Custom exception for handling room unavailability
class NotAvailable extends Exception {
    @Override
    public String toString() {
        return "Not Available!";
    }
}

// Holder class to keep track of all rooms in the hotel
class holder implements Serializable {
    Doubleroom luxury_doublerrom[] = new Doubleroom[10]; // Luxury double rooms
    Doubleroom deluxe_doublerrom[] = new Doubleroom[20]; // Deluxe double rooms
    Singleroom luxury_singleerrom[] = new Singleroom[10]; // Luxury single rooms
    Singleroom deluxe_singleerrom[] = new Singleroom[20]; // Deluxe single rooms
}

// Main hotel class with static methods to handle hotel operations
class HotelManagement {
    static holder hotel_ob = new holder();
    static Scanner sc = new Scanner(System.in);

    // Method to get a valid contact number
    static String getContactNumber() {
        String contact;
        while (true) {
            contact = sc.next();
            if (contact.matches("\\d{10}")) {
                break;
            } else {
                System.out.print("Invalid contact number. Enter a 10-digit contact number: ");
            }
        }
        return contact;
    }

    // Method to get a valid gender
    static String getGender() {
        String gender;
        while (true) {
            gender = sc.next();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other")) {
                break;
            } else {
                System.out.print("Invalid gender. Enter 'Male', 'Female', or 'Other': ");
            }
        }
        return gender;
    }

    // Method to get customer details and allocate room
    static void CustDetails(int i, int rn) {
        String name, contact, gender, identityProfile;
        String name2 = null, contact2 = null, gender2 = null, identityProfile2 = null;
        
        System.out.print("\nEnter customer name: ");
        name = sc.next();
        System.out.print("Enter contact number: ");
        contact = getContactNumber();
        System.out.print("Enter gender: ");
        gender = getGender();
        System.out.print("Enter identity profile: ");
        identityProfile = sc.next();

        if (i < 3) {
            System.out.print("Enter second customer name: ");
            name2 = sc.next();
            System.out.print("Enter contact number: ");
            contact2 = getContactNumber();
            System.out.print("Enter gender: ");
            gender2 = getGender();
            System.out.print("Enter identity profile: ");
            identityProfile2 = sc.next();
        }

        // Allocate room based on room type
        switch (i) {
            case 1:
                hotel_ob.luxury_doublerrom[rn] = new Doubleroom(name, contact, gender, identityProfile, name2, contact2, gender2, identityProfile2);
                break;
            case 2:
                hotel_ob.deluxe_doublerrom[rn] = new Doubleroom(name, contact, gender, identityProfile, name2, contact2, gender2, identityProfile2);
                break;
            case 3:
                hotel_ob.luxury_singleerrom[rn] = new Singleroom(name, contact, gender, identityProfile);
                break;
            case 4:
                hotel_ob.deluxe_singleerrom[rn] = new Singleroom(name, contact, gender, identityProfile);
                break;
            default:
                System.out.println("Wrong option");
                break;
        }
    }

    // Method to book a room
    static void bookroom(int i) {
        int j;
        int rn;
        System.out.println("\nChoose room number from: ");
        // Display available rooms based on room type
        switch (i) {
            case 1:
                for (j = 0; j < hotel_ob.luxury_doublerrom.length; j++) {
                    if (hotel_ob.luxury_doublerrom[j] == null) {
                        System.out.print((j + 1) + ",");
                    }
                }
                break;
            case 2:
                for (j = 0; j < hotel_ob.deluxe_doublerrom.length; j++) {
                    if (hotel_ob.deluxe_doublerrom[j] == null) {
                        System.out.print((j + 11) + ",");
                    }
                }
                break;
            case 3:
                for (j = 0; j < hotel_ob.luxury_singleerrom.length; j++) {
                    if (hotel_ob.luxury_singleerrom[j] == null) {
                        System.out.print((j + 31) + ",");
                    }
                }
                break;
            case 4:
                for (j = 0; j < hotel_ob.deluxe_singleerrom.length; j++) {
                    if (hotel_ob.deluxe_singleerrom[j] == null) {
                        System.out.print((j + 41) + ",");
                    }
                }
                break;
            default:
                System.out.println("Enter valid option");
                return;
        }

        // Get room number from user and allocate room
        System.out.print("\nEnter room number: ");
        try {
            rn = sc.nextInt();
            if (i == 1) rn--; else if (i == 2) rn -= 11; else if (i == 3) rn -= 31; else rn -= 41;
            if ((i == 1 && hotel_ob.luxury_doublerrom[rn] != null) ||
                (i == 2 && hotel_ob.deluxe_doublerrom[rn] != null) ||
                (i == 3 && hotel_ob.luxury_singleerrom[rn] != null) ||
                (i == 4 && hotel_ob.deluxe_singleerrom[rn] != null)) {
                throw new NotAvailable();
            }
            CustDetails(i, rn);
        } catch (Exception e) {
            System.out.println("Invalid Option");
            return;
        }
        System.out.println("Room Booked");
    }

    // Method to display features of a room type
    static void features(int i) {
        switch (i) {
            case 1: System.out.println("Number of double beds: 1\nAC: Yes\nFree breakfast: Yes\nCharge per day: 4000"); break;
            case 2: System.out.println("Number of double beds: 1\nAC: No\nFree breakfast: Yes\nCharge per day: 3000"); break;
            case 3: System.out.println("Number of single beds: 1\nAC: Yes\nFree breakfast: Yes\nCharge per day: 2200"); break;
            case 4: System.out.println("Number of single beds: 1\nAC: No\nFree breakfast: Yes\nCharge per day: 1200"); break;
            default: System.out.println("Enter valid option"); break;
        }
    }

    // Method to check room availability
    static void availability(int i) {
        int j, count = 0;
        switch (i) {
            case 1:
                for (j = 0; j < 10; j++) {
                    if (hotel_ob.luxury_doublerrom[j] == null) count++;
                }
                break;
            case 2:
                for (j = 0; j < hotel_ob.deluxe_doublerrom.length; j++) {
                    if (hotel_ob.deluxe_doublerrom[j] == null) count++;
                }
                break;
            case 3:
                for (j = 0; j < hotel_ob.luxury_singleerrom.length; j++) {
                    if (hotel_ob.luxury_singleerrom[j] == null) count++;
                }
                break;
            case 4:
                for (j = 0; j < hotel_ob.deluxe_singleerrom.length; j++) {
                    if (hotel_ob.deluxe_singleerrom[j] == null) count++;
                }
                break;
            default: System.out.println("Enter valid option"); break;
        }
        System.out.println("Number of rooms available: " + count);
    }

    // Method to generate and display the bill
    static void bill(int rn, int rtype) {
        double amount = 0;
        String list[] = {"Sandwich", "Pasta", "Noodles", "Coke"};
        System.out.println("\n*******");
        System.out.println(" Bill:-");
        System.out.println("*******");

        switch (rtype) {
            case 1:
                amount += 4000;
                System.out.println("\nRoom Charge - " + 4000);
                System.out.println("\n===============");
                System.out.println("Food Charges:- ");
                System.out.println("===============");
                System.out.println("Item   Quantity    Price");
                break;
            // Add more cases for other room types as needed
            default: System.out.println("Invalid room type"); break;
        }

        // Calculate and display food charges if any
        ArrayList<Food> foodItems = null;
        switch (rtype) {
            case 1: foodItems = hotel_ob.luxury_doublerrom[rn].food; break;
            case 2: foodItems = hotel_ob.deluxe_doublerrom[rn].food; break;
            case 3: foodItems = hotel_ob.luxury_singleerrom[rn].food; break;
            case 4: foodItems = hotel_ob.deluxe_singleerrom[rn].food; break;
        }
        if (foodItems != null) {
            for (Food item : foodItems) {
                System.out.println(list[item.itemno - 1] + "   " + item.quantity + "    " + item.price);
                amount += item.price;
            }
        }
        System.out.println("\nTotal Amount: " + amount);
    }

    public static void main(String[] args) {
        int choice, rtype, rn;

        do {
            System.out.println("\n1. Book Room\n2. Features\n3. Availability\n4. Bill\n5. Exit\n\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("1. Luxury Double Room\n2. Deluxe Double Room\n3. Luxury Single Room\n4. Deluxe Single Room\n\nEnter room type: ");
                    rtype = sc.nextInt();
                    bookroom(rtype);
                    break;

                case 2:
                    System.out.println("1. Luxury Double Room\n2. Deluxe Double Room\n3. Luxury Single Room\n4. Deluxe Single Room\n\nEnter room type: ");
                    rtype = sc.nextInt();
                    features(rtype);
                    break;

                case 3:
                    System.out.println("1. Luxury Double Room\n2. Deluxe Double Room\n3. Luxury Single Room\n4. Deluxe Single Room\n\nEnter room type: ");
                    rtype = sc.nextInt();
                    availability(rtype);
                    break;

                case 4:
                    System.out.print("Room Number - ");
                    rn = sc.nextInt();
                    System.out.println("1. Luxury Double Room\n2. Deluxe Double Room\n3. Luxury Single Room\n4. Deluxe Single Room\n\nEnter room type: ");
                    rtype = sc.nextInt();
                    bill(rn - 1, rtype);
                    break;

                case 5:
                    System.out.println("Thank you for using our services!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
                    break;
            }
        } while (choice != 5);
    }
}
