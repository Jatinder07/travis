package travis.queue;

import java.util.logging.Logger;

public class MessageReceiverImpl implements MessageReceiver{

	private InstructionMessage instructionMessage;
	private static final Logger LOGGER = Logger.getLogger(MessageReceiverImpl.class.getName());
	
	@Override
	public void receive(String message) {
		LOGGER.info("Message Recieved: "+message);
		if (message == null || message.trim().equals("")){
			throw new InvalidMessageException("Message cannot be blank");
		}
			instructionMessage = parseAndValidateMessage(message);
	}
	
	/*
	 * This method will parse and validate the incoming message
	 */
	private InstructionMessage parseAndValidateMessage(String message){
		
		String[] arrOfStr = message.split("\\s+");
		
		instructionMessage = new InstructionMessage(ProductType.valueOf(arrOfStr[0]), arrOfStr[1], Integer.parseInt(arrOfStr[2]), 
				Integer.parseInt(arrOfStr[3]), DateUtil.parseMessageDate(arrOfStr[4]));
		
		instructionMessage.validate();
		return instructionMessage;
	}
	
	public InstructionMessage getInstructionMessage() {
		return instructionMessage;
	}

	public void setInstructionMessage(InstructionMessage instructionMessage) {
		this.instructionMessage = instructionMessage;
	}

}
