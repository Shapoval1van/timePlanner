package com.timePlanner.dao.extractors;


import com.timePlanner.dao.mappers.CompanyMapper;
import com.timePlanner.dao.mappers.ProjectMapper;
import com.timePlanner.dto.Company;
import com.timePlanner.dto.Project;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CompanyExtractor implements ResultSetExtractor<List<Company>>{


    @Override
    public List<Company> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Company> companyMap = new HashMap<>();
        ProjectMapper projectMapper = new ProjectMapper();
        CompanyMapper companyMapper = new CompanyMapper();
        while (resultSet.next()){
            int companyId = resultSet.getInt("companyId");
            Company company = companyMap.get(companyId);
            if(company == null){
                company = companyMapper.mapRow(resultSet, 0);
                companyMap.put(companyId , company);
            }
            if(resultSet.getInt("projectId")!=0){
                Set<Project> projectSet = new HashSet<>();
                Project project = projectMapper.mapRow(resultSet, 0);
                projectSet = company.getProjects();
                if(projectSet!=null){
                    projectSet.add(project);
                }else {
                    projectSet = new HashSet<>();
                    projectSet.add(project);
                }
                company.setProjects(projectSet);
            }
        }
        return new ArrayList<>(companyMap.values());
    }
}
