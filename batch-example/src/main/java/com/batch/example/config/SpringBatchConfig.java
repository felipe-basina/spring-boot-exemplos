package com.batch.example.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.batch.example.model.Employee;
import com.batch.example.processor.Processor;
import com.batch.example.processor.ProcessorEmployeeRetrieved;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Value(value = "${application.db.driverName}")
    private String databaseDriverName;

    @Value(value = "${application.db.server}")
    private String databaseServer;

    @Value(value = "${application.db.port}")
    private String databasePort;

    @Value(value = "${application.db.schema}")
    private String databaseSchema;

    @Value(value = "${application.db.user}")
    private String databaseUser;

    @Value(value = "${application.db.password}")
    private String databasePassword;
    
    public static final String FROM_FILE_TO_DATABASE_QUALIFIER = "FROM_FILE_TO_DATABASE_QUALIFIER";
    public static final String FROM_DATABASE_TO_FILE_QUALIFIER = "FROM_DATABASE_TO_FILE_QUALIFIER";
    
    public static final String ITEM_READER_FROM_FILE = "ITEM_READER_FROM_FILE";
    public static final String ITEM_READER_FROM_DATABASE = "ITEM_READER_FROM_DATABASE";
    
    public static final String ITEM_WRITER_TO_DATABASE = "ITEM_WRITER_TO_DATABASE";
    public static final String ITEM_WRITER_TO_FILE = "ITEM_WRITER_TO_FILE";

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.databaseDriverName);
        dataSource.setUrl(String.format("jdbc:mysql://%s:%d/%s", this.databaseServer,
                Integer.parseInt(this.databasePort),
                this.databaseSchema));
        dataSource.setUsername(this.databaseUser);
        dataSource.setPassword(this.databasePassword);
        return dataSource;
    }

    @Bean
    @Qualifier(value = FROM_FILE_TO_DATABASE_QUALIFIER)
    public Job jobFromFileToDatabase(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   @Qualifier(value = ITEM_READER_FROM_FILE) ItemReader<Employee> itemReader,
                   @Qualifier(value = Processor.QUALIFIER) ItemProcessor<Employee, Employee> itemProcessor,
                   @Qualifier(value = ITEM_WRITER_TO_DATABASE) ItemWriter<Employee> itemWriter) {

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<Employee, Employee>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("ETL-Load-to-Database")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    @Qualifier(value = ITEM_READER_FROM_FILE)
    public FlatFileItemReader<Employee> fileItemReader(@Value(value = "${application.input.file}") Resource resource) {
        FlatFileItemReader<Employee> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("CSV-Reader");
        // Amount of lines to skip in case on an error.
        // In this case the first line of the file because it is corresponding
        // to the header of the file
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(this.lineMapper());
        return flatFileItemReader;
    }

    private LineMapper<Employee> lineMapper() {
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(Boolean.FALSE);
        delimitedLineTokenizer.setNames(new String[] {  "id", "name", "department", "salary" });

        BeanWrapperFieldSetMapper<Employee> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(Employee.class);

        DefaultLineMapper<Employee> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(delimitedLineTokenizer);
        defaultLineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

        return defaultLineMapper;
    }

    @Bean
    @Qualifier(value = FROM_DATABASE_TO_FILE_QUALIFIER)
    public Job jobFromDatabaseToFile(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   @Qualifier(value = ITEM_READER_FROM_DATABASE) JdbcCursorItemReader<Employee> itemReader,
                   @Qualifier(value = ProcessorEmployeeRetrieved.QUALIFIER) ItemProcessor<Employee, Employee> itemProcessor,
                   @Qualifier(value = ITEM_WRITER_TO_FILE) FlatFileItemWriter<Employee> itemWriter) {

        Step step = stepBuilderFactory.get("ETL-database-load")
                .<Employee, Employee>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("ETL-Load-to-File")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
    
	@Bean
	@Qualifier(value = ITEM_READER_FROM_DATABASE)
	public JdbcCursorItemReader<Employee> readerFromDatabase() {
		JdbcCursorItemReader<Employee> reader = new JdbcCursorItemReader<>();
		reader.setDataSource(this.dataSource());
		reader.setSql("SELECT id, name, department, salary FROM employee");
		reader.setRowMapper(new EmployeeRowMapper());
		return reader;
	}

	public class EmployeeRowMapper implements RowMapper<Employee> {
		@Override
		public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Employee(rs.getLong("id"),
					rs.getString("name"),
					rs.getString("department"),
					rs.getDouble("salary"), null);
		}
	}
	
	@Bean
	@Qualifier(value = ITEM_WRITER_TO_FILE)
	public FlatFileItemWriter<Employee> writerToFile(@Value(value = "${application.output.file}") FileSystemResource resource) {
		FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<Employee>();
		writer.setResource(resource);
		writer.setLineAggregator(new DelimitedLineAggregator<Employee>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<Employee>() {
					{
						setNames(new String[] { "id", "name", "department", "salary", "timestamp" });
					}
				});
			}
		});
		return writer;
	}

}
