package travis.queue;

import java.util.concurrent.PriorityBlockingQueue;


public class InstructionQueue {

	private static PriorityBlockingQueue<InstructionMessage> queue = new PriorityBlockingQueue<InstructionMessage>();
	
	private static InstructionQueue instructionQueue = new InstructionQueue();
	
	private InstructionQueue(){
		
	}
	
	/*
	 * Create a singleton instance of Queue
	 */
	public static InstructionQueue getInstance(){
		if (instructionQueue == null){
			instructionQueue = new InstructionQueue();
		}
		return instructionQueue;
	}
	
	public void enqueue(InstructionMessage message){
		queue.add(message);
	}
	
	public InstructionMessage dequeue(){
		return queue.poll();
	}
	
	public InstructionMessage peek(){
		return queue.peek();
	}
	
	public int count(){
		return queue.size();
	}
	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
}
