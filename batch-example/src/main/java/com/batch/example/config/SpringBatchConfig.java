package com.batch.example.config;

import com.batch.example.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Value(value = "${application.db.driverName}")
    private String databaseDriverName;

    @Value(value = "${application.db.server}")
    private String databaseServer;

    @Value(value = "${application.db.port}")
    private int databasePort;

    @Value(value = "${application.db.schema}")
    private String databaseSchema;

    @Value(value = "${application.db.user}")
    private String databaseUser;

    @Value(value = "${application.db.password}")
    private String databasePassword;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(this.databaseDriverName);
        dataSource.setUrl(String.format("jdbc:mysql://%s:%d/%s", this.databaseServer, this.databasePort, this.databaseSchema));
        dataSource.setUsername(this.databaseUser);
        dataSource.setPassword(this.databasePassword);
        return dataSource;
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<Employee> itemReader,
                   ItemProcessor<Employee, Employee> itemProcessor,
                   ItemWriter<Employee> itemWriter) {

        Step step = stepBuilderFactory.get("ETL-file-load")
                .<Employee, Employee>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
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

}
