package nl.sander.extract.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class DummyTableRowMapper implements RowMapper<DummyTable>{

	public DummyTable mapRow(ResultSet rs, int rowNumber) throws SQLException {
		DummyTable dummyTable = new DummyTable();
		dummyTable.setColumn1(rs.getString(1));
		dummyTable.setColumn2(rs.getString(2));
		dummyTable.setColumn3(rs.getString(3));
		dummyTable.setColumn4(rs.getString(4));
		
		return dummyTable;
	}

}
