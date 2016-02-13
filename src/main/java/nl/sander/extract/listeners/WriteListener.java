package nl.sander.extract.listeners;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemWriteListener;

import nl.sander.extract.domain.DummyTable;

public class WriteListener implements ItemWriteListener<DummyTable>{
	
	private final static Log logger = LogFactory.getLog(WriteListener.class);
	
	private int counter;
	private int logSize;
	protected long start;
	

	@Override
	public void beforeWrite(List<? extends DummyTable> items) {
		
	}

	@Override
	public void afterWrite(List<? extends DummyTable> items) {
		if (counter == 0) {
			start = System.currentTimeMillis();
		}
		for (@SuppressWarnings("unused") DummyTable item : items) {
			counter++;
			if (counter >= logSize) {
				double second = (float) (System.currentTimeMillis() - start) / 1000;
				logger.info(String.format("Wrote %d records in %f seconds", logSize, second));
				counter = 0;
			}
		}
		
		
	}

	@Override
	public void onWriteError(Exception exception, List<? extends DummyTable> items) {
		
	}

	public int getLogSize() {
		return logSize;
	}

	public void setLogSize(int logSize) {
		this.logSize = logSize;
	}	

}
