package nl.sander.extract.domain;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import nl.sander.extract.domain.DummyTable;

public class DummyFooter implements ItemWriter<DummyTable>, FlatFileFooterCallback {

	private ItemWriter<DummyTable> delegate;

	private String footer;

	public void writeFooter(Writer writer) throws IOException {
		writer.write(footer);
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

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

}
