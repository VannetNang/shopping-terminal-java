import java.util.Scanner;

public class ShoppingList {

    // Categories as arrays
    static String[] fruits = {"apple", "banana", "orange"};
    static double[] fruitPrices = {1.0, 0.5, 0.8};

    static String[] snacks = {"chips", "chocolate", "cookies"};
    static double[] snackPrices = {1.8, 2.5, 2.0};

    // Cart arrays
    static String[] cart = new String[50];
    static double[] cartPrices = new double[50];
    static int cartCount = 0;

    // Color Codes for terminal
    static final String RESET = "\u001B[0m";
    static final String CYAN = "\u001B[36m";
    static final String GREEN = "\u001B[32m";
    static final String RED = "\u001B[31m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String BOLD = "\u001B[1m";

    // ================= MAIN COMPONENT ==================
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printHeader("SHOPPING MENU");
            System.out.println(GREEN + "1." + RESET + " 🛒 Add Item to Cart");
            System.out.println(GREEN + "2." + RESET + " 🗑️ Remove Item from Cart");
            System.out.println(GREEN + "3." + RESET + " 💳 View Cart & Checkout");
            System.out.println(GREEN + "4." + RESET + " 🚪 Exit");
            printFooter();

            System.out.print(YELLOW + "👉 Enter choice: " + RESET);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> chooseCategory(scanner);

                case 2 -> {
                    scanner.nextLine();
                    System.out.print(YELLOW + "📝 Enter item name to remove: " + RESET);
                    String removeItem = scanner.nextLine().toLowerCase();
                    removeItem(removeItem);
                }

                case 3 -> checkout();

                case 4 -> {
                    System.out.println("\n👋 " + GREEN + "Thank you for shopping. Goodbye!" + RESET);
                    scanner.close();
                    return;
                }

                default -> System.out.println(RED + "❌ Invalid choice. Try again." + RESET);
            }
        }
    }

    // Choose category
    public static void chooseCategory(Scanner scanner) {
        printHeader("CHOOSE CATEGORY");
        System.out.println(GREEN + "1." + RESET + " 🍎 Fruits");
        System.out.println(GREEN + "2." + RESET + " 🍪 Snacks");
        printFooter();

        System.out.print(YELLOW + "👉 Enter category number: " + RESET);
        int categoryChoice = scanner.nextInt();

        switch (categoryChoice) {
            case 1 -> chooseItem(scanner, fruits, fruitPrices, "Fruits");
            case 2 -> chooseItem(scanner, snacks, snackPrices, "Snacks");
            default -> System.out.println(RED + "❌ Invalid category choice." + RESET);
        }
    }

    // Choose item from category
    public static void chooseItem(Scanner scanner, String[] items, double[] prices, String categoryName) {
        printHeader("AVAILABLE " + categoryName.toUpperCase());

        for (int i = 0; i < items.length; i++) {
            System.out.printf(GREEN + "%d." + RESET + " %-15s $%.2f%n", (i + 1), capitalize(items[i]), prices[i]);
        }

        printFooter();

        System.out.print(YELLOW + "👉 Enter item number: " + RESET);
        int itemChoice = scanner.nextInt();

        if (itemChoice < 1 || itemChoice > items.length) {
            System.out.println(RED + "❌ Invalid item choice." + RESET);
            return;
        }

        String item = items[itemChoice - 1];
        double price = prices[itemChoice - 1];

        cart[cartCount] = item;
        cartPrices[cartCount] = price;
        cartCount++;

        System.out.println(GREEN + "✅ Added " + capitalize(item) + " to cart!" + RESET);
    }

    // Remove item from cart
    public static void removeItem(String item) {
        for (int i = 0; i < cartCount; i++) {
            if (cart[i] != null && cart[i].equalsIgnoreCase(item)) {
                for (int j = i; j < cartCount - 1; j++) {
                    cart[j] = cart[j + 1];
                    cartPrices[j] = cartPrices[j + 1];
                }
                cart[cartCount - 1] = null;
                cartPrices[cartCount - 1] = 0;
                cartCount--;
                System.out.println(RED + "🗑️ Removed " + capitalize(item) + " from cart." + RESET);
                return;
            }
        }
        System.out.println(RED + "❌ Item not found in cart." + RESET);
    }

    // Checkout
    public static void checkout() {
        if (cartCount == 0) {
            System.out.println(RED + "\n🛍️ Your cart is empty." + RESET);
            return;
        }

        int totalItems = cartCount;
        double totalCost = 0.0;

        printHeader("🛒 CART SUMMARY");
        for (int i = 0; i < cartCount; i++) {
            System.out.printf(GREEN + "%d." + RESET + " %-15s $%.2f%n", (i + 1), capitalize(cart[i]), cartPrices[i]);
            totalCost += cartPrices[i];
        }
        printFooter();

        System.out.println(BOLD + BLUE + "📦 Total items: " + RESET + totalItems);
        System.out.println(BOLD + BLUE + "💲 Total cost: " + RESET + "$" + String.format("%.2f", totalCost));

        // Clear the cart after checkout
        for (int i = 0; i < cartCount; i++) {
            cart[i] = null;
            cartPrices[i] = 0;
        }
        cartCount = 0;

        System.out.println(GREEN + "\n🧼 Cart has been cleared after checkout!" + RESET);
    }

    // ==================== END MAIN  =====================


    // ============ UI CSS COMPONENT ==============
    // Capitalize first letter of item
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // Fancy header and footer
    public static void printHeader(String title) {
        System.out.println("\n" + CYAN + "╔══════════════════════════════════════╗" + RESET);
        System.out.println(CYAN + "║ " + BOLD + centerText(title, 36) + CYAN + " ║" + RESET);
        System.out.println(CYAN + "╚══════════════════════════════════════╝" + RESET);
    }

    public static void printFooter() {
        System.out.println(CYAN + "────────────────────────────────────────" + RESET);
    }

    // Center text inside the header box
    public static String centerText(String text, int width) {
        int totalPadding = Math.max(0, width - text.length());
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    // ================ END UI =====================
}
