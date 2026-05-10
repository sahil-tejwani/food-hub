import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class FOODHUB extends JFrame {

    static class User {
        int id;
        String name, email, password;

        User(int id, String name, String email, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    static class FoodItem {
        int id;
        String name;
        double price;
        int quantity;

        FoodItem(int id, String name, double price, int quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
    }

    static class Order {
        int id, userId, foodId, quantity;
        double total;
        String time;

        Order(int id, int userId, int foodId, int quantity, double total, String time) {
            this.id = id;
            this.userId = userId;
            this.foodId = foodId;
            this.quantity = quantity;
            this.total = total;
            this.time = time;
        }
    }

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<FoodItem> foods = new ArrayList<>();
    static ArrayList<Order> orders = new ArrayList<>();

    static int userIdCounter = 1;
    static int orderIdCounter = 1;
    static int currentUserId = -1;
    static boolean isAdmin = false;

    JFrame frame;

    public FOODHUB() {

        users.add(new User(userIdCounter++, "Sahil", "sahil@gmail.com", "123"));
        users.add(new User(userIdCounter++, "Admin", "admin@gmail.com", "admin"));

        foods.add(new FoodItem(1, "Burger", 200, 10));
        foods.add(new FoodItem(2, "Pizza", 300, 5));
        foods.add(new FoodItem(3, "Sandwich", 120, 8));

        showLoginPage();
    }

    void showLoginPage() {

        frame = new JFrame("FOODHUB");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel title = new JLabel("FOODHUB");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setBounds(170, 30, 200, 40);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(80, 100, 100, 30);

        JTextField emailField = new JTextField();
        emailField.setBounds(180, 100, 200, 30);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(80, 150, 100, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(180, 150, 200, 30);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(100, 230, 120, 40);

        JButton signupBtn = new JButton("Signup");
        signupBtn.setBounds(250, 230, 120, 40);

        frame.add(title);
        frame.add(emailLabel);
        frame.add(emailField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginBtn);
        frame.add(signupBtn);

        loginBtn.addActionListener(e -> {

            String email = emailField.getText();
            String pass = String.valueOf(passField.getPassword());

            for (User u : users) {

                if (u.email.equals(email) && u.password.equals(pass)) {

                    currentUserId = u.id;
                    isAdmin = email.equals("admin@gmail.com");

                    JOptionPane.showMessageDialog(frame, "Login Successful");

                    frame.dispose();

                    showDashboard();

                    return;
                }
            }

            JOptionPane.showMessageDialog(frame, "Invalid Login");
        });

        signupBtn.addActionListener(e -> showSignupPage());

        frame.setVisible(true);
    }

    void showSignupPage() {

        JFrame signupFrame = new JFrame("Signup");
        signupFrame.setSize(400, 350);
        signupFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Name");
        nameLabel.setBounds(50, 50, 100, 30);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 180, 30);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(50, 100, 100, 30);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 100, 180, 30);

        JLabel passLabel = new JLabel("Password");
        passLabel.setBounds(50, 150, 100, 30);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(150, 150, 180, 30);

        JButton createBtn = new JButton("Create Account");
        createBtn.setBounds(120, 220, 150, 40);

        signupFrame.add(nameLabel);
        signupFrame.add(nameField);
        signupFrame.add(emailLabel);
        signupFrame.add(emailField);
        signupFrame.add(passLabel);
        signupFrame.add(passField);
        signupFrame.add(createBtn);

        createBtn.addActionListener(e -> {

            String name = nameField.getText();
            String email = emailField.getText();
            String pass = String.valueOf(passField.getPassword());

            users.add(new User(userIdCounter++, name, email, pass));

            JOptionPane.showMessageDialog(signupFrame, "Signup Successful");

            signupFrame.dispose();
        });

        signupFrame.setVisible(true);
    }

    void showDashboard() {

        JFrame dash = new JFrame("Dashboard");
        dash.setSize(600, 500);
        dash.setLayout(null);

        JButton viewFoodBtn = new JButton("View Food");
        viewFoodBtn.setBounds(180, 40, 200, 40);

        JButton searchBtn = new JButton("Search Food");
        searchBtn.setBounds(180, 100, 200, 40);

        JButton orderBtn = new JButton("Order Food");
        orderBtn.setBounds(180, 160, 200, 40);

        JButton ordersBtn = new JButton("View Orders");
        ordersBtn.setBounds(180, 220, 200, 40);

        JButton cancelBtn = new JButton("Cancel Order");
        cancelBtn.setBounds(180, 280, 200, 40);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(180, 400, 200, 40);

        dash.add(viewFoodBtn);
        dash.add(searchBtn);
        dash.add(orderBtn);
        dash.add(ordersBtn);
        dash.add(cancelBtn);
        dash.add(logoutBtn);

        JButton addFoodBtn = new JButton("Add Food");

        if (isAdmin) {
            addFoodBtn.setBounds(180, 340, 200, 40);
            dash.add(addFoodBtn);

            addFoodBtn.addActionListener(e -> addFood());
        }

        viewFoodBtn.addActionListener(e -> viewFood());

        searchBtn.addActionListener(e -> searchFood());

        orderBtn.addActionListener(e -> orderFood());

        ordersBtn.addActionListener(e -> viewOrders());

        cancelBtn.addActionListener(e -> cancelOrder());

        logoutBtn.addActionListener(e -> {
            dash.dispose();
            showLoginPage();
        });

        dash.setVisible(true);
    }

    void viewFood() {

        StringBuilder data = new StringBuilder();

        for (FoodItem f : foods) {
            data.append("ID: ").append(f.id)
                    .append("   Name: ").append(f.name)
                    .append("   Price: ").append(f.price)
                    .append("   Qty: ").append(f.quantity)
                    .append("\n");
        }

        JTextArea area = new JTextArea(data.toString());
        JOptionPane.showMessageDialog(null, new JScrollPane(area), "Food Menu", JOptionPane.INFORMATION_MESSAGE);
    }

    void searchFood() {

        String name = JOptionPane.showInputDialog("Enter Food Name");

        if (name == null) return;

        StringBuilder data = new StringBuilder();

        for (FoodItem f : foods) {

            if (f.name.toLowerCase().contains(name.toLowerCase())) {

                data.append("ID: ").append(f.id)
                        .append("   Name: ").append(f.name)
                        .append("   Price: ").append(f.price)
                        .append("\n");
            }
        }

        if (data.length() == 0) {
            data.append("No Food Found");
        }

        JTextArea area = new JTextArea(data.toString());

        JOptionPane.showMessageDialog(null, new JScrollPane(area));
    }

    void orderFood() {

        String foodId = JOptionPane.showInputDialog("Enter Food ID");

        if (foodId == null) return;

        String qtyText = JOptionPane.showInputDialog("Enter Quantity");

        if (qtyText == null) return;

        int fid = Integer.parseInt(foodId);
        int qty = Integer.parseInt(qtyText);

        for (FoodItem f : foods) {

            if (f.id == fid) {

                if (f.quantity >= qty) {

                    f.quantity -= qty;

                    double total = f.price * qty;

                    LocalDateTime now = LocalDateTime.now();

                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                    String time = now.format(format);

                    orders.add(new Order(orderIdCounter++, currentUserId, fid, qty, total, time));

                    JOptionPane.showMessageDialog(null, "Order Placed\nTotal = " + total);

                    return;
                } else {

                    JOptionPane.showMessageDialog(null, "Not Enough Stock");

                    return;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Food Not Found");
    }

    void viewOrders() {

        StringBuilder data = new StringBuilder();

        for (Order o : orders) {

            if (o.userId == currentUserId) {

                data.append("Order ID: ").append(o.id)
                        .append("   Food: ").append(getFoodName(o.foodId))
                        .append("   Qty: ").append(o.quantity)
                        .append("   Total: ").append(o.total)
                        .append("   Time: ").append(o.time)
                        .append("\n");
            }
        }

        if (data.length() == 0) {
            data.append("No Orders");
        }

        JTextArea area = new JTextArea(data.toString());

        JOptionPane.showMessageDialog(null, new JScrollPane(area));
    }

    void cancelOrder() {

        String orderId = JOptionPane.showInputDialog("Enter Order ID");

        if (orderId == null) return;

        int oid = Integer.parseInt(orderId);

        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order o = iterator.next();
            if (o.id == oid && o.userId == currentUserId) {

                iterator.remove();

                JOptionPane.showMessageDialog(null, "Order Cancelled");

                return;
            }
        }

        JOptionPane.showMessageDialog(null, "Order Not Found");
    }

    void addFood() {

        String name = JOptionPane.showInputDialog("Food Name");

        if (name == null) return;

        String priceText = JOptionPane.showInputDialog("Price");

        if (priceText == null) return;

        String qtyText = JOptionPane.showInputDialog("Quantity");

        if (qtyText == null) return;

        double price = Double.parseDouble(priceText);

        int qty = Integer.parseInt(qtyText);

        foods.add(new FoodItem(foods.size() + 1, name, price, qty));

        JOptionPane.showMessageDialog(null, "Food Added");
    }

    String getFoodName(int id) {

        for (FoodItem f : foods) {

            if (f.id == id) {
                return f.name;
            }
        }

        return "Unknown";
    }

    public static void main(String[] args) {
        new FOODHUB();
    }
}