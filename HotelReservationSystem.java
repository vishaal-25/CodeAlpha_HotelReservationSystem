import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Room {
    String type;
    int totalRooms;
    int booked;

    Room(String type, int totalRooms) {
        this.type = type;
        this.totalRooms = totalRooms;
        this.booked = 0;
    }

    boolean isAvailable() {
        return booked < totalRooms;
    }
}

class Booking {
    String bookingId;
    String customer;
    String roomType;

    Booking(String bookingId, String customer, String roomType) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.roomType = roomType;
    }
}

public class HotelReservationSystem {
    static Scanner sc = new Scanner(System.in);
    static HashMap<String, Room> rooms = new HashMap<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int bookingCounter = 1000;

    public static void main(String[] args) throws IOException {
        rooms.put("Standard", new Room("Standard", 5));
        rooms.put("Deluxe", new Room("Deluxe", 3));
        rooms.put("Suite", new Room("Suite", 2));

        while (true) {
            System.out.println("\n--- Hotel Reservation System ---");
            System.out.println("1. Book Room");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View All Bookings");
            System.out.println("4. Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> bookRoom();
                case 2 -> cancelBooking();
                case 3 -> viewBookings();
                case 4 -> System.exit(0);
            }
        }
    }

    static void bookRoom() throws IOException {
        sc.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
        String type = sc.nextLine();

        Room r = rooms.get(type);
        if (r != null && r.isAvailable()) {
            r.booked++;
            String id = "H" + bookingCounter++;
            Booking b = new Booking(id, name, type);
            bookings.add(b);
            saveReceipt(b);
            System.out.println("Booking Confirmed! ID: " + id);
        } else {
            System.out.println("Room not available. Suggesting alternatives...");
            for (Room alt : rooms.values()) {
                if (alt.isAvailable()) {
                    System.out.println("Available: " + alt.type);
                }
            }
        }
    }

    static void cancelBooking() {
        System.out.print("Enter Booking ID to cancel: ");
        String id = sc.next();
        for (Booking b : bookings) {
            if (b.bookingId.equals(id)) {
                bookings.remove(b);
                rooms.get(b.roomType).booked--;
                System.out.println("Booking Cancelled.");
                return;
            }
        }
        System.out.println("Booking not found.");
    }

    static void viewBookings() {
        for (Booking b : bookings) {
            System.out.println(b.bookingId + " | " + b.customer + " | " + b.roomType);
        }
    }

    static void saveReceipt(Booking b) throws IOException {
        FileWriter fw = new FileWriter(b.bookingId + "_Receipt.txt");
        fw.write("Booking ID: " + b.bookingId + "\n");
        fw.write("Customer: " + b.customer + "\n");
        fw.write("Room Type: " + b.roomType + "\n");
        fw.write("Status: Confirmed\n");
        fw.close();
    }
}
