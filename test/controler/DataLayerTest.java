package controler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DataLayerTest {
	
	DataLayer dl;
	String VALID_COSTCENTER = "103100";
	String INVALID_COSTCENTER = "000000";
	
	@Before
	void init() throws DatabaseCommunicationsException {
		DataLayer dl = new DataLayer();
		dl.connect();
		dl.load();
	}

	@Test
	void testDataLayer() throws DatabaseCommunicationsException {
		DataLayer dl = new DataLayer();
		assertNotNull(dl);
	}

	@Test
	void testGetCostCenterList() {
		fail("Not yet implemented");
	}

	@Test
	void testQueryFromFile() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Get a CostCenter")
	void testGetCostCenter1() throws NotFoundException {
		assertEquals(VALID_COSTCENTER, dl.getCostCenter(VALID_COSTCENTER).getId());
	}
	@Test
	@DisplayName("CostCenter not found")
	void testGetCostCenter2() throws NotFoundException {
		
		assertThrows(NotFoundException.class, () -> {
			dl.getCostCenter(INVALID_COSTCENTER);
		});
	}

	@Test
	void testInsertProject() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateProject() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertNetwork() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertWbs() {
		fail("Not yet implemented");
	}
	
	@Test
	void testIsConnected() throws DatabaseCommunicationsException {
		DataLayer testdl = new DataLayer();
		testdl.connect();
		assertTrue(testdl.isConnected());
		testdl.disconnect();
		assertFalse(testdl.isConnected());	
	}

	@Test
	void testUpdateNetwork() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertRun() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateRun() {
		fail("Not yet implemented");
	}

	@Test
	void testInsertCostCenter() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateCostCenter() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRun() {
		fail("Not yet implemented");
	}

	@Test
	void testGetNetwork() {
		fail("Not yet implemented");
	}

	@Test
	void testGetWbs() {
		fail("Not yet implemented");
	}

	@Test
	void testGetProject() {
		fail("Not yet implemented");
	}

	@Test
	void testGetRunList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetCSDMapping() {
		fail("Not yet implemented");
	}

	@Test
	void testQueryProjects() {
		fail("Not yet implemented");
	}

	@Test
	void testQueryRuns() {
		fail("Not yet implemented");
	}

	@Test
	void testLog() {
		dl.log("Login");
	}

	@Test
	void testConnect() throws DatabaseCommunicationsException {
		DataLayer testdl = new DataLayer();
		testdl.connect();
		assertTrue(testdl.isConnected());
	}

	@Test
	void testConnectStringString() {
		fail("Not yet implemented");
	}

	@Test
	void testRefresh() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserHash() {
		fail("Not yet implemented");
	}

	@Test
	void testImportCost() {
		fail("Not yet implemented");
	}

	@Test
	void testDisconnect() throws DatabaseCommunicationsException {
		DataLayer testdl = new DataLayer();
		testdl.connect();
		testdl.disconnect();
		assertFalse(testdl.isConnected());
	}

}
