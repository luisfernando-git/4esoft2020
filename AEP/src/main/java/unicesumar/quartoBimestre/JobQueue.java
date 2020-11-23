package unicesumar.quartoBimestre;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class JobQueue {
	
	private LinkedList<Integer> jobs = new LinkedList<>();
	
    private AtomicInteger processing = new AtomicInteger(0);
    
    private JobQueueListener queueListener;
    
    private JobQueueCompletedListener completedListener;

    public JobQueue() {
        super();
    }

    public void addJobQueueListener(JobQueueListener queueListener, JobQueueCompletedListener completedListener) {
        this.queueListener = queueListener;
        this.completedListener = completedListener;
    }

    public interface JobQueueListener {
        void jobQueueChanged(int newSize);
    }

    public interface JobQueueCompletedListener {
        void jobQueueCompletedChanged(int newSize);
    }

	public synchronized void queueJob(int job) {
	    synchronized (this) {
	        this.jobs.add(job);
	        if (this.queueListener != null) {
	            this.queueListener.jobQueueChanged(this.jobs.size());
	        }
	
	    }
	}
	
	public synchronized Integer getNextJob() {
	    synchronized (this) {
	        if (this.jobs.isEmpty()) {
	            return null;
	        }
	
	        Integer job = this.jobs.getFirst();
	        System.out.println("Getting another job!");
	        return job;
	    }
	}
	
	public synchronized void assignJob() {
	    synchronized (this) {
	        this.processing.incrementAndGet();
	        if (this.queueListener != null) {
	            this.completedListener.jobQueueCompletedChanged(this.processing.get());
	        }
	    }
	}
	
	public synchronized void concludeJob() {
	    synchronized (this) {
	        this.processing.decrementAndGet();
	        if (!this.jobs.isEmpty()) {
	            this.jobs.removeFirst();
	        }
	        System.out.println("Concluding job!");
	        if (this.queueListener != null) {
	            this.queueListener.jobQueueChanged(this.jobs.size());
	        }
	        if (this.queueListener != null) {
	            this.completedListener.jobQueueCompletedChanged(this.processing.get());
	        }
	    }
	}
}
