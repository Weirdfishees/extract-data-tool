package nl.ing.ultra.jobsteps;

import java.sql.SQLException;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;

import nl.ing.ultra.domain.DummyFooter;
import nl.ing.ultra.domain.DummyHeader;
import nl.ing.ultra.domain.DummyTable;
import nl.ing.ultra.domain.DummyTableRowMapper;
import oracle.jdbc.pool.OracleDataSource;

@Configuration
public class UnloadStep {
	
	// tag::readerwriterprocessor[]
    @Bean
    public ItemReader<DummyTable> reader() throws Exception {
    	JdbcCursorItemReader<DummyTable> reader = new JdbcCursorItemReader<DummyTable>();
    	reader.setDataSource(dataSource());
    	// SET IN GUI
    	reader.setMaxRows(100000);
    	
    	// SET IN GUI
    	reader.setSql("SELECT VOORNAAM1,"
    			+ "ACHTERNAAM,"
    			+ "ROEPNAAM,"
    			+ "INITIALEN "
    			+ "FROM LUCY01.KLANT_PARTICULIER");    	
    	reader.setRowMapper(rowMapper(new DummyTableRowMapper()));    	     
        return reader;
    }
    
    @Bean
	public ItemWriter<DummyTable> writer() {
		FlatFileItemWriter<DummyTable> writer = new FlatFileItemWriter<DummyTable>();
		
		// SET IN GUI
		writer.setResource(new FileSystemResource("target/unload.csv"));
		
		writer.setShouldDeleteIfExists(true);
		writer.setLineAggregator(new DelimitedLineAggregator<DummyTable>(){
			{				
				
				// SET IN GUI
				setDelimiter("|");
				
				setFieldExtractor(new BeanWrapperFieldExtractor<DummyTable>() {
					{
						setNames(new String[] { "column1","column2","column3","column4"});
					}
				}
				
				);
			}
		});
		writer.setHeaderCallback(getHeader());
		writer.setFooterCallback(getFooter());
		return writer;
	}
    
    // end::readerwriterprocessor[]
    
    // tag::beans
    @Bean
    public RowMapper<DummyTable> rowMapper(DummyTableRowMapper mapper){
    	return mapper;
    }
    
    @Bean
	public OracleDataSource dataSource() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setDriverType("");
		dataSource.setURL("");
		dataSource.setUser("");
		dataSource.setPassword("");
		return dataSource;
	}
    
    @Bean 
    public DummyHeader getHeader(){
    	DummyHeader header = new DummyHeader();    	
    	
    	// SET IN GUI
    	header.setHeader("HEADER");
    	
    	
    	return header;
    }
    
    @Bean 
    public DummyFooter getFooter(){
    	DummyFooter footer = new DummyFooter();    	
    	
    	// SET IN GUI
    	footer.setFooter("FOOTER");
    	
    	
    	return footer;
    }
    // end::beans
    
}
