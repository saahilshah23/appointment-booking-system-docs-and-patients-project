import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BookingsTest 
{

	Bookings appointments;

	@BeforeEach
	public void setUp() throws Exception {
		appointments = new Bookings("1");
	}
	
	@Test
	@DisplayName("Check Doctor ID")
	//Checks that the correct doctor ID is stored from the constructor
	public void testDoctorID() 
	{
		assertEquals("1", appointments.getDoctorID());
	}

	@Test
	@DisplayName("Check number of Columns of Query")
	//Check we return 5 columns of information containing the doctor's info
	public void testQueryColumnsID() {
		try {
			assertEquals(3, appointments.getPreparedStatement().getMetaData().getColumnCount());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	@DisplayName("Check Doctor Info")
	//Check that resultSet from the doctor info query is not null
	public void testDoctorResultSet() {
			assertNotNull(appointments.getDoctorResultSet());
	}

	@Test
	@DisplayName("Check Patient Info")
	//Check that resultSet from the patient info query is not null
	public void testPatientResultSet() {
			assertNotNull(appointments.getPatientResultSet());
	}
}
