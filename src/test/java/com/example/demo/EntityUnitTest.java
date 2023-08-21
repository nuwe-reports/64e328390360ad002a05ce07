package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

    private Doctor d1;

    private Patient p1;

    private Room r1;

    private Appointment a1;
    private Appointment a2;
    private Appointment a3;

    @Test
    void testDoctorEntity() {
        // Crea un objeto Doctor de ejemplo
        d1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        entityManager.persistAndFlush(d1);

        // Recupera el Doctor de la base de datos y verifica si es igual al original
        Doctor retrievedDoctor = entityManager.find(Doctor.class, d1.getId());
        assertThat(retrievedDoctor).isEqualTo(d1);

        // Prueba los métodos getter
        assertThat(retrievedDoctor.getId()).isEqualTo(d1.getId());
        assertThat(retrievedDoctor.getFirstName()).isEqualTo(d1.getFirstName());
        assertThat(retrievedDoctor.getLastName()).isEqualTo(d1.getLastName());
        assertThat(retrievedDoctor.getAge()).isEqualTo(d1.getAge());
        assertThat(retrievedDoctor.getEmail()).isEqualTo(d1.getEmail());
    }

    @Test
    void testPatientEntity() {
        // Crea un objeto Doctor de ejemplo
        p1 = new Patient("David", "Palacin", 24, "david@gmail.com");
        entityManager.persistAndFlush(p1);

        Patient retrievedPatient = entityManager.find(Patient.class, p1.getId());
        assertThat(retrievedPatient).isEqualTo(p1);

        // Prueba los métodos getter
        assertThat(retrievedPatient.getId()).isEqualTo(p1.getId());
        assertThat(retrievedPatient.getFirstName()).isEqualTo(p1.getFirstName());
        assertThat(retrievedPatient.getLastName()).isEqualTo(p1.getLastName());
        assertThat(retrievedPatient.getAge()).isEqualTo(p1.getAge());
        assertThat(retrievedPatient.getEmail()).isEqualTo(p1.getEmail());
    }

    @Test
    void testRoomEntity() {
        // Crea un objeto Doctor de ejemplo
        r1 = new Room("room1");
        entityManager.persistAndFlush(r1);

        Room retrievedRoom = entityManager.find(Room.class, r1.getId());
        assertThat(retrievedRoom).isEqualTo(r1);

        // Prueba los métodos getter
        assertThat(retrievedRoom.getId()).isEqualTo(r1.getId());
        assertThat(retrievedRoom.getRoomName()).isEqualTo(r1.getRoomName());
    }

    @Test
    // Case 1: A.starts == B.starts
    void testAppointmentOverlaps_SameStarts() {
        p1 = new Patient("David", "Palacin", 24, "david@gmail.com");
        d1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        r1 = new Room("room1");
        LocalDateTime startTime = LocalDateTime.of(2023, 8, 21, 10, 0);
        LocalDateTime endTimeA = LocalDateTime.of(2023, 8, 21, 11, 10);
        LocalDateTime endTimeB = LocalDateTime.of(2023, 8, 21, 12, 10);

        a1 = new Appointment(p1, d1, r1, startTime, endTimeA);
        a2 = new Appointment(p1, d1, r1, startTime, endTimeB);

        boolean overlap = a1.overlaps(a2);
        assertThat(overlap).isTrue();
    }

    @Test
    // Case 2: A.finishes == B.finishes
    void testAppointmentOverlaps_SameFinishes() {
        p1 = new Patient("David", "Palacin", 24, "david@gmail.com");
        d1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        r1 = new Room("room1");
        LocalDateTime startTimeA = LocalDateTime.of(2023, 8, 21, 11, 0);
        LocalDateTime startTimeB = LocalDateTime.of(2023, 8, 21, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 8, 21, 13, 0);

        a1 = new Appointment(p1, d1, r1, startTimeA, endTime);
        a2 = new Appointment(p1, d1, r1, startTimeB, endTime);

        boolean overlap = a1.overlaps(a2);
        assertThat(overlap).isTrue();
    }

    @Test
    // Case 3: A.starts < B.finishes && B.finishes < A.finishes
    void testAppointmentOverlaps_AStartsEarlierAndFinishLater() {
        p1 = new Patient("David", "Palacin", 24, "david@gmail.com");
        d1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        r1 = new Room("room1");

        LocalDateTime startTimeA = LocalDateTime.of(2023, 8, 21, 9, 0);
        LocalDateTime endTimeA = LocalDateTime.of(2023, 8, 21, 14, 0);

        LocalDateTime startTimeB = LocalDateTime.of(2023, 8, 21, 10, 0);
        LocalDateTime endTimeB = LocalDateTime.of(2023, 8, 21, 11, 0);

        a1 = new Appointment(p1, d1, r1, startTimeA, endTimeA);
        a2 = new Appointment(p1, d1, r1, startTimeB, endTimeB);

        boolean overlap = a1.overlaps(a2);
        assertThat(overlap).isTrue();
    }

    @Test
    // Case 3: A.starts < B.finishes && B.finishes < A.finishes
    void testAppointmentOverlaps_BStartsEarlierAndFinishLater() {
        p1 = new Patient("David", "Palacin", 24, "david@gmail.com");
        d1 = new Doctor("John", "Doe", 35, "john.doe@example.com");
        r1 = new Room("room1");

        LocalDateTime startTimeB = LocalDateTime.of(2023, 8, 21, 9, 0);
        LocalDateTime endTimeB = LocalDateTime.of(2023, 8, 21, 14, 0);

        LocalDateTime startTimeA = LocalDateTime.of(2023, 8, 21, 10, 0);
        LocalDateTime endTimeA = LocalDateTime.of(2023, 8, 21, 11, 0);

        a1 = new Appointment(p1, d1, r1, startTimeB, endTimeB);
        a2 = new Appointment(p1, d1, r1, startTimeA, endTimeA);

        boolean overlap = a1.overlaps(a2);
        assertThat(overlap).isTrue();
    }
}
