package nl.ing.ultra.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ItemReadListener;

import nl.ing.ultra.domain.DummyTable;

public class ReadListener implements ItemReadListener<DummyTable>{
	
	
	private int logSize;
	
	private final static Log logger = LogFactory.getLog(WriteListener.class);	
	private int counter;
	protected long start;

	@Override
	public void beforeRead() {		
		
	}

	@Override
	public void afterRead(DummyTable item) {
		if (counter == 0) {
			start = System.currentTimeMillis();
		}
		counter++;
		if (counter >= logSize) {
			double second = (float) (System.currentTimeMillis() - start) / 1000;
			logger.info(String.format("Read %d records in %f seconds", logSize, second));
			counter = 0;
		}
		
	}

	@Override
	public void onReadError(Exception ex) {		
		
	}

	public int getLogSize() {
		return logSize;
	}

	public void setLogSize(int logSize) {
		this.logSize = logSize;
	}

}
