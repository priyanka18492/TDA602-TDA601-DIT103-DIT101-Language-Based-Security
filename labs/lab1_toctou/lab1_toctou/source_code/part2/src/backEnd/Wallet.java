package backEnd;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

// Import Library required for locking the shared resource
import java.nio.channels.FileLock;

public class Wallet {
   /**
    * The RandomAccessFile of the wallet file
    */  
   private RandomAccessFile file;

   /**
    * Creates a Wallet object
    *
    * A Wallet object interfaces with the wallet RandomAccessFile
    */
    public Wallet () throws Exception {
	this.file = new RandomAccessFile(new File("wallet.txt"), "rw");
	
    }

   /**
    * Gets the wallet balance. 
    *
    * @return                   The content of the wallet file as an integer
    */
    /////////////////// Part 2 ///////////////////
    // Lock the resource before accessing it.
    // Unlock the resource after accessing.
    public int getBalance() throws IOException {
    
    // Lock the resource
    FileLock lock = file.getChannel().lock();
    
    // Get the balance as in original API
	this.file.seek(0);
	int balance =Integer.parseInt(this.file.readLine());
	
	// Release the resource and return balance
	lock.release();
	
    return balance;
    }

   /**
    * Sets a new balance in the wallet
    *
    * @param  newBalance          new balance to write in the wallet
    */
    // Lock the resource before accessing it.
    // Unlock the resource after accessing.
    
    public void setBalance(int newBalance) throws Exception {
    	
    // Lock the resource
    FileLock lock = file.getChannel().lock();
    
	this.file.setLength(0);
	@SuppressWarnings("deprecation")
	String str = new Integer(newBalance).toString()+'\n'; 
	this.file.writeBytes(str); 
	
	// Unlock the resource
	lock.release();
    }
    
    public void safeWithdraw(int valueToWithdraw) throws Exception {
        FileLock lock = file.getChannel().lock();
        
        // Get the balance as in original API
    	this.file.seek(0);
    	int currentBalance =Integer.parseInt(this.file.readLine());
    	
        if (currentBalance < valueToWithdraw) {
            throw new Exception("Insufficient funds!");
        }
        
        int newBalance=currentBalance-valueToWithdraw;
        
    	this.file.setLength(0);
    	@SuppressWarnings("deprecation")
    	String str = new Integer(newBalance).toString()+'\n'; 
    	this.file.writeBytes(str); 
    	
        lock.release();
    }

   /**
    * Closes the RandomAccessFile in this.file
    */
    public void close() throws Exception {
	this.file.close();
    }
    
}
