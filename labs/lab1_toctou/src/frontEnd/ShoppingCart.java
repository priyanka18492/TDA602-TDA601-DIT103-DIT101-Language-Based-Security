package frontEnd;
import backEnd.*;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class ShoppingCart extends Thread{


	Scanner scanner =new Scanner(System.in);
	Wallet wallet;
	Pocket pocket;
	String choice;

	public ShoppingCart(String threadID,String choice) throws Exception
	{
		super(threadID);

		System.out.println(threadID);


		// Create new wallets and pockets
		wallet = new Wallet();
		pocket = new Pocket();
		this.choice=choice;

		// 1. Print the current balance of the user
		System.out.println("Your balance: " + wallet.getBalance());

		// 2. Print the product list and their prices
		System.out.print(Store.asString());

		// 3. Ask user what product they would like to buy
		System.out.println("What would you like to to buy?: "+choice);

		// Run the thread
		start();

	}

	public void run() {
		//Display info about this particular thread
		try {
			buyProduct(choice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void buyProduct(String choice) throws Exception
	{

		// Read user input using scanner library
		// String choice=scanner.next();

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

		Thread.currentThread().sleep(3000);
		
		/////////////////////////////////////////////////////////////////////////////////////////////
        try {
            wallet.safeWithdraw(itemCost);
            pocket.addProduct(choice);
            
			// 7. Print the new balance
			System.out.println("Your new balance is: " + wallet.getBalance());
            
        } catch (Exception e) {
            System.out.println(String.format(e.getMessage())+". Exiting...");
        }

		
		////////////////////////////////////////////////////////////////////////////////////////////

		scanner.close();

	}

	public static void main(String[] args) throws Exception  {

		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("pocket.txt"), "utf-8"))) {
			writer.write("book\n");
		}
		
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream("wallet.txt"), "utf-8"))) {
			writer.write("30000\n");
		}
		ShoppingCart shoppingCart1=new ShoppingCart("shoppingCart1","car");
		Thread.currentThread().sleep(1000);
		ShoppingCart shoppingCart2=new ShoppingCart("shoppingCart2","pen");


	}

}
