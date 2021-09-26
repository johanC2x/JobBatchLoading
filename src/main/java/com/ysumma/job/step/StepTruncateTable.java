package com.ysumma.job.step;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@StepScope
public class StepTruncateTable implements Tasklet {

    private final DataSource dataSource;

    private static String queryExecute = "TRUNCATE TABLE int_company;";

    @Autowired
    public StepTruncateTable(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public RepeatStatus execute(StepContribution var1, ChunkContext chunkContext) {
        new JdbcTemplate(this.dataSource).batchUpdate(queryExecute);
        return RepeatStatus.FINISHED;
    }

}
