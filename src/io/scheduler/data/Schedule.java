/**
 * 
 */
package io.scheduler.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author skarahoda
 *
 */
@DatabaseTable(tableName = "schedule")
public class Schedule {

	public static final String NAME_FIELD_NAME = "name";

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String name;

	@ForeignCollectionField
	private ForeignCollection<ScheduleSUClass> classes;

	/**
	 * For ormlite
	 */
	Schedule() {
	}

	/**
	 * 
	 * @param name
	 * @throws SQLException
	 */
	public Schedule(String name) throws SQLException {
		this.setName(name);
		DatabaseConnector.createIfNotExist(this, Schedule.class);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	public Collection<SUClass> getSUClasses() {
		List<SUClass> returnVal = new ArrayList<SUClass>();
		Iterator<ScheduleSUClass> i = classes.iterator();
		while (i.hasNext()) {
			returnVal.add(i.next().getSuClass());
		}
		return returnVal;
	}

	public void addSUClass(SUClass suClass) throws SQLException {
		new ScheduleSUClass(suClass, this);
	}
}
