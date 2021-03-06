package com.apap.tutorial6.repository;

import com.apap.tutorial6.model.FlightModel;
import com.apap.tutorial6.model.PilotModel;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.Optional;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlightDbTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private FlightDb flightDb;

    @Test
    public void whenFindByFlightNumber_thenReturnFlight() {
        //given
        PilotModel pilotModel = new PilotModel();
        pilotModel.setLicenseNumber("4172");
        pilotModel.setName("Coki");
        pilotModel.setFlyHour(59);
        entityManager.persist(pilotModel);
        entityManager.flush();

        FlightModel flightModel = new FlightModel();
        flightModel.setFlightNumber("X550");
        flightModel.setOrigin("Depok");
        flightModel.setDestination("Bekasi");
        flightModel.setTime(new Date(new java.util.Date().getTime()));
        entityManager.persist(flightModel);
        entityManager.flush();

        //when
        Optional<FlightModel> found = flightDb.findByFlightNumber(flightModel.getFlightNumber());

        //then
        assertThat(found.get(), Matchers.notNullValue());
        assertThat(found.get(), Matchers.equalTo(flightModel));
    }
}
