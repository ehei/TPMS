package com.ehei.tpms.server.datastore

import com.ehei.tpms.server.model.*
import com.ehei.tpms.server.util.convertToString
import com.ehei.tpms.server.util.createDateFrom
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
class RepositoryIntegrationTests(
    @Autowired
    private val entityManager: TestEntityManager
) {
    val startDate = createDateFrom(2022, 5, 15)
    val endDate =  createDateFrom(2022, 7, 1)


}