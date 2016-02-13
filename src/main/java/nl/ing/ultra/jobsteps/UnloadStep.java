package nl.ing.ultra.jobsteps;

import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;
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

@Configuration
public class UnloadStep {
	
	// tag::readerwriterprocessor[]
    @Bean
    public ItemReader<DummyTable> reader() throws Exception {
    	JdbcCursorItemReader<DummyTable> reader = new JdbcCursorItemReader<DummyTable>();
    	reader.setDataSource(dataSource());    	
    	reader.setMaxRows(1000000);
    	
    	reader.setSql("SELECT EMP_NO,"
    			+ "FIRST_NAME,"
    			+ "LAST_NAME,"
    			+ "HIRE_DATE "
    			+ "FROM EMPLOYEES.EMPLOYEES");    	
    	reader.setRowMapper(rowMapper(new DummyTableRowMapper()));    	     
        return reader;
    }
    
    @Bean
	public ItemWriter<DummyTable> writer() {
		FlatFileItemWriter<DummyTable> writer = new FlatFileItemWriter<DummyTable>();
				
		writer.setResource(new FileSystemResource("target/unload.csv"));
		
		writer.setShouldDeleteIfExists(true);
		writer.setLineAggregator(new DelimitedLineAggregator<DummyTable>(){
			{						
				
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
	public BasicDataSource dataSource() throws SQLException {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/EMPLOYEES");
		dataSource.setUsername("root");
		dataSource.setPassword("entrd");
		return dataSource;
	}
    
    @Bean 
    public DummyHeader getHeader(){
    	DummyHeader header = new DummyHeader();
    	header.setHeader("HEADERPLACEHOLDER");    	
    	return header;
    }
    
    @Bean 
    public DummyFooter getFooter(){
    	DummyFooter footer = new DummyFooter();   
    	footer.setFooter("FOOTERHOLDER");    	
    	return footer;
    }
    // end::beans    
}
