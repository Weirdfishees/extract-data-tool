package nl.sander.extract.domain;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileHeaderCallback;

import nl.sander.extract.domain.DummyTable;

public class DummyHeader implements ItemWriter<DummyTable>, FlatFileHeaderCallback {

	private ItemWriter<DummyTable> delegate;

	private String header;
	
	@Override
	public void writeHeader(Writer writer) throws IOException {
		writer.write(header);		
	}

	public ItemWriter<DummyTable> getDelegate() {
		return delegate;
	}

	public void setDelegate(ItemWriter<DummyTable> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void write(List<? extends DummyTable> items) throws Exception {		
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
