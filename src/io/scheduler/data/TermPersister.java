package io.scheduler.data;

import java.sql.SQLException;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.field.types.IntType;

public class TermPersister extends IntType {

	private static TermPersister singleton;

	protected TermPersister() {
		super(SqlType.INTEGER, new Class<?>[] { Term.class });
	}

	public static TermPersister getSingleton() {
		if (singleton == null) {
			singleton = new TermPersister();
		}
		return singleton;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.field.BaseFieldConverter#javaToSqlArg(com.j256.ormlite
	 * .field.FieldType, java.lang.Object)
	 */
	@Override
	public Object javaToSqlArg(FieldType fieldType, Object javaObject)
			throws SQLException {
		Term term = (Term) javaObject;
		return term.toInt();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.j256.ormlite.field.BaseFieldConverter#sqlArgToJava(com.j256.ormlite
	 * .field.FieldType, java.lang.Object, int)
	 */
	@Override
	public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos)
			throws SQLException {
		return new Term((int) sqlArg);
	}

}
