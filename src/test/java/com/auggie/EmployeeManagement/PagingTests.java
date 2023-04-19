package com.auggie.EmployeeManagement;

import com.auggie.EmployeeManagement.dto.query.EmployeeQuery;
import com.auggie.EmployeeManagement.services.EmployeeSearchService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@Log4j2
@SpringBootTest(classes = EmployeeManagementApplication.class)
public class PagingTests {

    @Autowired
    private EmployeeSearchService employeeSearchService;

    @Test
    void pagingTest(){
        Pageable pageable =PageRequest.of(0,3);
        Page<EmployeeQuery> employeeQueryPage = employeeSearchService.searchPage("admin", pageable);
        assertThat(employeeQueryPage.getNumberOfElements()).isNotEqualTo(0);
        log.info("{}", employeeQueryPage.getContent().toString());
    }
}
