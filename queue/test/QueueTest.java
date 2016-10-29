package travis.queue.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import travis.queue.InstructionMessage;
import travis.queue.InstructionQueue;
import travis.queue.InvalidMessageException;
import travis.queue.MessageReceiver;
import travis.queue.MessageReceiverImpl;
import travis.queue.ProductType;

public class QueueTest {

	private InstructionQueue instructionQueue = null;
	private MessageReceiver messageReceiver = null;
	InstructionMessage instructionMessage = null;
	String messages = "A AC11 1231 50 1971­01­05T10:01:50.012+0100\nD DD11 5678 50 2015­02­05T10:04:56.012+0100\n"
			+ "B BB11 5678 50 2016­03­05T10:04:56.012+0100\nC CC11 5678 50 2015­04­05T10:04:56.012+0100\n"
			+ "C CC22 5678 50 2015­04­05T10:04:56.012+0100\n";
	String[] msgArr = messages.split("\\n");
	
	@Before
	public void init(){
		instructionQueue = InstructionQueue.getInstance();
		messageReceiver = new MessageReceiverImpl();
	}
	
	/*
	 * Test to check if enqueue, peek(), count(), dequeue() method return correct values.
	 */
	@Test
	public void queueAddPriorityTest() {

		for (int i = 0; i < msgArr.length; i++) {
			messageReceiver.receive(msgArr[i]);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		}
        //Test if queue count is correctly returned
		Assert.assertEquals(5, instructionQueue.count());
		
		//Test if queue peek() method return correct value.
		Assert.assertEquals(ProductType.A, instructionQueue.peek().getProductType());
		
		//Test if queue dequeue() method return correct value.
		Assert.assertEquals(ProductType.A, instructionQueue.dequeue().getProductType());
		
		//Test if queue peek() method return correct value.
		Assert.assertEquals(ProductType.B, instructionQueue.peek().getProductType());
		
		//Test if queue dequeue() method return correct value.
		Assert.assertEquals(ProductType.B, instructionQueue.dequeue().getProductType());
		
		Assert.assertEquals(ProductType.C, instructionQueue.dequeue().getProductType());
		Assert.assertEquals(ProductType.C, instructionQueue.dequeue().getProductType());
		Assert.assertEquals(ProductType.D, instructionQueue.dequeue().getProductType());
		 //Test if queue count is correctly returned
		Assert.assertEquals(0, instructionQueue.count());
		
		//Assert to confirm if isEmpty() method return correct value.
		Assert.assertTrue(instructionQueue.isEmpty());
	}
	
	/*
	 * This method check the Product Code validity
	 */
	@Test
	public void invalidProductCodeTest() {
		try{
			String message = "A A111 1231 50 1971­01­05T10:01:50.012+0100";
			messageReceiver.receive(message);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		} catch (InvalidMessageException ie) {
			Assert.assertEquals("The Product Code Is Invalid", ie.getMessage());
		}
		
	}
	
	/*
	 * This method check the quantity validity
	 */
	@Test
	public void invalidQuantityTest() {
		try{
			String message = "A AB11 0 50 1971­01­05T10:01:50.012+0100";
			messageReceiver.receive(message);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		} catch (InvalidMessageException ie) {
			Assert.assertEquals("Quantity Cannot Be Zero or Less Than Zero", ie.getMessage());
		}
		
	}
	
	/*
	 * This method check the UOM validity
	 */
	@Test
	public void invalidUOMTest() {
		try{
			String message = "A AB11 50 -3 1971­01­05T10:01:50.012+0100";
			messageReceiver.receive(message);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		} catch (InvalidMessageException ie) {
			Assert.assertEquals("UOM Should Be Between Zero To 256 (0 <= UOM < 256)", ie.getMessage());
		}
		
	}
	
	/*
	 * This method check the Date validity
	 */
	@Test
	public void invalidDateBeforeEpochTest() {
		try{
			String message = "A AB11 50 46 1969­01­05T10:01:50.012+0100";
			messageReceiver.receive(message);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		} catch (InvalidMessageException ie) {
			Assert.assertEquals("Date Should Be Between Unix Epoch (01/01/1970) and Current Date", ie.getMessage());
		}
		
	}
	
	/*
	 * This method check the Product Type validity
	 */
	@Test
	public void invalidDatAfterCurrentDateTest() {
		try{
			String message = "A AB11 50 46 2018­01­05T10:01:50.012+0100";
			messageReceiver.receive(message);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		} catch (InvalidMessageException ie) {
			Assert.assertEquals("Date Should Be Between Unix Epoch (01/01/1970) and Current Date", ie.getMessage());
		}
		
	}
	
	@Test
	public void invalidProductTypeTest() {
		try{
			String message = "F AB11 50 46 2018­01­05T10:01:50.012+0100";
			messageReceiver.receive(message);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		} catch (Exception ie) {
			Assert.assertEquals("No enum constant travis.queue.ProductType.F", ie.getMessage());
		}
		
	}
	
	@Test
	public void messageNullTest() {
		try{
			String message = null;
			messageReceiver.receive(message);
			instructionMessage = messageReceiver.getInstructionMessage();
			instructionQueue.enqueue(instructionMessage);
		} catch (Exception ie) {
			Assert.assertEquals("Message cannot be blank", ie.getMessage());
		}
		
	}
}
