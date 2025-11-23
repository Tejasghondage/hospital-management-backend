package com.hms.frontend;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

import com.hms.config.HibernateUtil;
import com.hms.controller.AppointmentController;
import com.hms.controller.BillingController;
import com.hms.controller.DoctorController;
import com.hms.controller.InventoryController;
import com.hms.controller.PatientController;
import com.hms.controller.ReportController;
import com.hms.entity.Appointment;
import com.hms.entity.Bill;
import com.hms.entity.Doctor;
import com.hms.entity.Medicine;
import com.hms.entity.Patient;
import com.hms.exception.BusinessException;

public class HospitalManagementApplication {

	private static final PatientController patientController = new PatientController();
	private static final DoctorController doctorController = new DoctorController();
	private static final AppointmentController appointmentController = new AppointmentController();
	private static final BillingController billingController = new BillingController();
	private static final InventoryController inventoryController = new InventoryController();
	private static final ReportController reportController = new ReportController();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("=== Hospital Management Backend Demo ===");

		boolean running = true;
		while (running) {
			System.out.println("\n1. Register Patient");
			System.out.println("2. Patient Login");
			System.out.println("3. Register Doctor");
			System.out.println("4. Book Appointment");
			System.out.println("5. Confirm Payment");
			System.out.println("6. Cancel Appointment");
			System.out.println("7. Generate Bill");
			System.out.println("8. Manage Medicine");
			System.out.println("9. Monthly Revenue Report");
			System.out.println("0. Exit");
			System.out.print("Choose: ");
			int choice = Integer.parseInt(sc.nextLine());

			try {
				switch (choice) {
				case 1:
					System.out.print("Name: ");
					String name = sc.nextLine();
					System.out.print("Email: ");
					String email = sc.nextLine();
					System.out.print("Contact(10 digits): ");
					String contact = sc.nextLine();
					System.out.print("Password: ");
					String password = sc.nextLine();
					Patient p = patientController.register(name, email, contact, password);
					System.out.println("Registered Patient ID: " + p.getId());
					break;
				case 2:
					System.out.print("Email: ");
					String lemail = sc.nextLine();
					System.out.print("Password: ");
					String lpwd = sc.nextLine();
					Patient logged = patientController.login(lemail, lpwd);
					System.out.println("Logged in as: " + logged.getName() + " (ID " + logged.getId() + ")");
					break;
				case 3:
					System.out.print("Doctor Name: ");
					String dname = sc.nextLine();
					System.out.print("Email: ");
					String demail = sc.nextLine();
					System.out.print("Specialization: ");
					String spec = sc.nextLine();
					System.out.print("Fee: ");
					double fee = Double.parseDouble(sc.nextLine());
					System.out.print("Experience Years: ");
					int exp = Integer.parseInt(sc.nextLine());
					Doctor d = doctorController.register(dname, demail, spec, fee, exp);
					System.out.println("Registered Doctor ID: " + d.getId());
					break;
				case 4:
					System.out.print("Patient ID: ");
					Long pid = Long.parseLong(sc.nextLine());
					System.out.print("Doctor ID: ");
					Long did = Long.parseLong(sc.nextLine());
					System.out.print("Appointment Date (YYYY-MM-DD): ");
					LocalDate adate = LocalDate.parse(sc.nextLine());
					System.out.print("Appointment Hour (0-23): ");
					int hour = Integer.parseInt(sc.nextLine());
					LocalDateTime time = adate.atTime(hour, 0);
					Appointment ap = appointmentController.book(pid, did, time);
					System.out.println("Appointment ID: " + ap.getId() + " Status: " + ap.getStatus());
					break;
				case 5:
					System.out.print("Appointment ID: ");
					Long aid = Long.parseLong(sc.nextLine());
					System.out.print("Amount: ");
					double amt = Double.parseDouble(sc.nextLine());
					appointmentController.confirmPayment(aid, amt);
					System.out.println("Payment confirmed.");
					break;
				case 6:
					System.out.print("Appointment ID: ");
					Long caid = Long.parseLong(sc.nextLine());
					appointmentController.cancel(caid);
					System.out.println("Appointment cancelled.");
					break;
				case 7:
					System.out.print("Appointment ID: ");
					Long baid = Long.parseLong(sc.nextLine());
					System.out.print("Patient Age: ");
					int age = Integer.parseInt(sc.nextLine());
					System.out.print("Base Amount: ");
					double bamt = Double.parseDouble(sc.nextLine());
					Bill bill = billingController.generateBill(baid, age, bamt);
					System.out.println("Bill Total: " + bill.getTotal());
					break;
				case 8:
					System.out.println("1. Upsert Medicine  2. Show Low Stock  3. Show Expired  4. Refresh Status");
					int mchoice = Integer.parseInt(sc.nextLine());
					if (mchoice == 1) {
						System.out.print("Medicine Name: ");
						String mname = sc.nextLine();
						System.out.print("Quantity: ");
						int qty = Integer.parseInt(sc.nextLine());
						System.out.print("Expiry Date (YYYY-MM-DD): ");
						LocalDate ed = LocalDate.parse(sc.nextLine());
						Medicine med = inventoryController.upsert(mname, qty, ed);
						System.out.println("Medicine saved with id " + med.getId() + " status " + med.getStatus());
					} else if (mchoice == 2) {
						List<Medicine> lows = inventoryController.lowStock();
						System.out.println("Low stock medicines: ");
						for (Medicine m : lows) {
							System.out.println(m.getName() + " (" + m.getQuantity() + ")");
						}
					} else if (mchoice == 3) {
						List<Medicine> exps = inventoryController.expired();
						System.out.println("Expired medicines: ");
						for (Medicine m : exps) {
							System.out.println(m.getName() + " expiring " + m.getExpiryDate());
						}
					} else if (mchoice == 4) {
						inventoryController.refreshStatuses();
						System.out.println("Statuses refreshed.");
					}
					break;
				case 9:
					System.out.print("Year (e.g., 2025): ");
					int year = Integer.parseInt(sc.nextLine());
					System.out.print("Month (1-12): ");
					int month = Integer.parseInt(sc.nextLine());
					reportController.printMonthlyRevenue(YearMonth.of(year, month));
					break;
				case 0:
					running = false;
					break;
				default:
					System.out.println("Invalid choice");
				}
			} catch (BusinessException be) {
				System.out.println("BUSINESS ERROR: " + be.getMessage());
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}

		sc.close();
		HibernateUtil.shutdown();
		System.out.println("Bye!");
	}
}
