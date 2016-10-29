package travis.queue;

public interface MessageReceiver {
	
	void receive(String message);
	
	public InstructionMessage getInstructionMessage();
}
