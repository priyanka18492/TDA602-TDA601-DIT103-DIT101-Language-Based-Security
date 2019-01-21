package fontEnd;
import backEnd.*;
import java.util.Scanner;

public class ShoppingCart {

	public static void main(String[] args) throws Exception  {


		Scanner scanner = new Scanner(System.in);

		// Create new wallets and pockets
		Wallet wallet = new Wallet();
		Pocket pocket = new Pocket();

		// 1. Print the current balance of the user
		System.out.println("Your balance: " + wallet.getBalance());

		// 2. Print the product list and their prices
		System.out.print(Store.asString());

		// 3. Ask user what product they would like to buy
		System.out.print("What would you like to to buy?: ");

		// Read user input using scanner library
		String choice=scanner.next();

		// Check if choice is in the hashmap (i.e store)
		while(Store.products.containsKey(choice)==false)
		{
			System.out.println("Selected product is not available, please choose one of the following products: ");
			System.out.print(Store.asString());
			choice=scanner.next();
		}


		// 4. Check if the amount of credits is enough, if not stop the execution.

		int itemCost = Store.products.get(choice);
		int currentBalance=wallet.getBalance();

		if(currentBalance>=itemCost)
		{
			int newBalance=currentBalance-itemCost;
			
			// 5. Withdraw the price of the product from the wallet.
			wallet.setBalance(newBalance);
			
			// 6. Add the name of the product to the pocket file.
			pocket.addProduct(choice);

			// 7. Print the new balance
			System.out.println("Your new balance is: " + newBalance);

			// 8. Print items in pocket
			System.out.println("Your pocket contains the following items:");
			pocket.asString();

		}
		else
		{
			System.out.println("Your wallet does not contain enough credits");
			System.exit(1);
		}



		scanner.close();
	}

}
