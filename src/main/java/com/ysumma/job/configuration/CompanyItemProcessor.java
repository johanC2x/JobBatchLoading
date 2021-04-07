package com.ysumma.job.configuration;

import com.ysumma.job.model.Company;
import org.springframework.batch.item.ItemProcessor;

public class CompanyItemProcessor implements ItemProcessor<Company, Company> {
    @Override
    public Company process(final Company company) throws Exception {
        final Company finalCompany = new Company(company.getRuc(),
                company.getName(),
                company.getTaxpayerStatus(),
                company.getResidenceCondition(),
                company.getLocation(),
                company.getRoadType(),
                company.getStreetName(),
                company.getZoneCode(),
                company.getZoneType(),
                company.getNumber(),
                company.getInterior(),
                company.getLot(),
                company.getDepartment(),
                company.getApple(),
                company.getKilometer());
        return finalCompany;
    }
}
