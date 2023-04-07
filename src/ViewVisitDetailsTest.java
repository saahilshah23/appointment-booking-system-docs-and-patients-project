/**
 * Test the ViewVisitDetails class
 * @author Fernando Ramirez
 */

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ViewVisitDetailsTest {

	ViewVisitDetails view;
	@BeforeEach
	void setUp() throws Exception {
		view = new ViewVisitDetails("1");
	}

	/**
	 * Check the doctor ID
	 */
	@Test
	@DisplayName("Check number of Columns of Query")
	void test() {
		assertEquals(view.getDoctorID(), "1");
	}

	@Test
	@DisplayName("Check number of Columns of Query")
	//Check we return 9 columns of information containing the doctor's info
	public void testQueryColumnsID() {
		try {
			assertEquals(9, view.getPreparedStatement().getMetaData().getColumnCount());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
