package com.ysumma.job.configuration;

import com.ysumma.job.model.Company;
import com.ysumma.job.step.Step1;
import com.ysumma.job.step.Step2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private DataSource dataSource;

    @Value("${app.chunk.size}")
    private int chunkSize;

    @Bean
    Job helloWorldJob() {
        return jobBuilderFactory.get("importEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Company, Company>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2").tasklet(new Step2()).build();
    }

    @Bean
    public FlatFileItemReader<Company> reader() {
        FlatFileItemReader<Company> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("employee_v3.txt"));
        reader.setLineMapper(new DefaultLineMapper<Company>() {{
            setLineTokenizer(new DelimitedLineTokenizer("|") {{
                setNames(new String[] { "ruc", "name", "taxpayerStatus", "residenceCondition", "location", "roadType", "streetName", "zoneCode", "zoneType", "number", "interior", "lot", "department", "apple", "kilometer"});
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Company>() {{
                setTargetType(Company.class);
            }});
        }});
        return reader;
    }

    @Bean
    public CompanyItemProcessor processor() {
        return new CompanyItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Company> writer() {
        JdbcBatchItemWriter<Company> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO COMPANY(RUC,RAZON_SOCIAL,TAXPAYER_STATUS,RESIDENCE_CONDITION,LOCATION,ROAD_TYPE,STREET_NAME,ZONE_CODE,ZONE_TYPE,NUMBER,INTERIOR,LOT,DEPARTMENT,APPLE,KILOMETER) " +
                "VALUES (:ruc, :name, :taxpayerStatus, :residenceCondition, :location, :roadType, :streetName, :zoneCode, :zoneType, :number, :interior, :lot, :department, :apple, :kilometer)");
        writer.setDataSource(dataSource);
        return writer;
    }

}