package com.ysumma.job;

import com.ysumma.job.configuration.JobConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(JobConfiguration.class)
public class JobBatchLoadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobBatchLoadingApplication.class, args);
	}

}
