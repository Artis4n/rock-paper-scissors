package threads;

/**
 * A combination of a semaphore and shared data
 * used for thread communication.
 * @param <E> 
 */
class MVar<E> {
	private E state; //current object
	private boolean isSet = false; //creates a state and sets it to false

	/**
	 * Used to store an object
	 * @param s
	 */
	public synchronized void putMVar(E s) {
		while (isSet) {
			try {
				wait();
			} catch (InterruptedException e) {
				// handle exception
			}
		}
		isSet = true;
		state = s;
		notifyAll();
	}

	/**
	 * Used to take an object which is stored
	 * @return the object stored
	 */
	public synchronized E takeMVar() {
		while (!isSet) {
			try {
				wait();
			} catch (InterruptedException e) {
				// handle exception
			}
		}
		isSet = false;
		notifyAll();
		return state;
	}
}
