package nl.sander.extract.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import nl.sander.extract.domain.DummyTable;
import nl.sander.extract.jobsteps.UnloadStep;
import nl.sander.extract.listeners.ReadListener;
import nl.sander.extract.listeners.WriteListener;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {	
	
	// TODO: use annotation or some kind of wiring to inject instead of instantiation?
	UnloadStep UnloadStep = new UnloadStep();		
	
	@Autowired
	private StepBuilderFactory steps;		
	
	@Autowired
	private ReadListener readListener;
	
	@Autowired
	private WriteListener writeListener;		

	// tag::jobstep[]
	@Bean
	public Job unloadTestData(JobBuilderFactory jobs) throws Exception {
		return jobs
				.get("unloadTestData")
				.incrementer(new RunIdIncrementer())				
				.start(step1())				
				.build();
	}
	
	@Bean
	public Step step1() throws Exception{
		return steps.
				get("UnloadStep")
				.<DummyTable,DummyTable> chunk(100000)
				.reader(UnloadStep.reader())
				.writer(UnloadStep.writer())
				.listener(readListener)
				.listener(writeListener)
				.build();
	}		
	// end::jobstep[]
	
	// tag::beans
	@Bean
	public ReadListener getReadListener(){
		ReadListener listener = new ReadListener();
		listener.setLogSize(25000);
		return listener;
	}
	
	@Bean
	public WriteListener getWriteListener(){
		WriteListener listener = new WriteListener();
		listener.setLogSize(25000);
		return listener;
	}
	// end::beans
	
 }
