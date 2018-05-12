package com.coducation.smallbasic.lib;

import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

import com.coducation.smallbasic.ArrayV;
import com.coducation.smallbasic.DoubleV;
import com.coducation.smallbasic.InterpretException;
import com.coducation.smallbasic.StrV;
import com.coducation.smallbasic.Value;

public class Database {
	public static String dbName;
	public static ArrayList<String> tables;
	public static String tbName;

	// Connect to database
	public static void ConnectToDatabase(ArrayList<Value> args) {

		Connection c = null;
		if (args.size() == 1) {
			if (args.get(0) instanceof StrV) {
				dbName = ((StrV) args.get(0)).getValue();
				try {
					Class.forName("org.sqlite.JDBC");
					c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
				} catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
					System.exit(0);
				}
				System.out.println("Opened database successfully");
			} else {
				throw new InterpretException("ConnectToDatabase: Database's name have to be StrV");
			}
		} else
			throw new InterpretException("ConnectToDatabase: Unexpected # of args: " + args.size());
	}

	// get all table's name of a database
	public static ArrayList<String> getAllTablesName(String databaseName) {
		ArrayList<String> tables = new ArrayList<>();
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:resource/" + databaseName);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");
			DatabaseMetaData md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
				tables.add(rs.getString(3));
			}
			rs.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return tables;
	}

	// Create a table
	public static void CreateTable(ArrayList<Value> args) {
		// check connect database
		if (dbName != null) {
			Connection c = null;
			Statement stmt = null;
			String dStmt = "";
			ArrayList<String> allTablesName = getAllTablesName(dbName);
			boolean exst = false;
			if (args.size() == 2) {
				if (args.get(0) instanceof StrV) {
					tbName = ((StrV) args.get(0)).getValue();
					// check if table exists
					for (int i = 0; i < allTablesName.size(); i++) {
						if (allTablesName.get(i) == tbName) {
							exst = true;
							break;
						}
					}
					if (!exst) {
						if (args.get(1) instanceof ArrayV) {
							ArrayV arr = (ArrayV) args.get(1);
							ArrayV key = arr.getKey();
							for (int i = 1; i <= key.size(); i++) {
								ArrayV arr_value = (ArrayV) arr.get(key.get(Integer.toString(i)).toString());
								if (arr_value instanceof ArrayV) {
									// get column name and type
									String colName = ((StrV) arr_value.get("name")).getValue();
									String colType = ((StrV) arr_value.get("type")).getValue();
									dStmt += "\n\r " + colName + " " + colType + " ";
									int optionNum = arr_value.size() - 2;
									// get option1, option2,... if exists
									for (int j = 1; j <= optionNum; j++) {
										String optionalType = ((StrV) arr_value.get("option" + j)).getValue();
										dStmt += optionalType + " ";
									}
									if (i < key.size()) {
										dStmt += ",";
									}
								} else {
									throw new InterpretException(
											"CreateTable: Columns's information have to be ArrayV.");
								}
							}
							try {
								Class.forName("org.sqlite.JDBC");
								c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
								System.out.println("Opened database successfully");
								stmt = c.createStatement();
								String sql = "CREATE TABLE " + tbName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
										+ dStmt + ");";
								stmt.executeUpdate(sql);
								stmt.close();
								c.close();
							} catch (Exception e) {
								System.err.println(e.getClass().getName() + ": " + e.getMessage());
								System.exit(0);
							}
							System.out.println("Table created successfully");
						} else {
							throw new InterpretException("CreateTable: Columns's information have to be ArrayV.");
						}
					} else {
						throw new InterpretException("CreateTable: Table " + tbName + "already exists");
					}

				} else {
					throw new InterpretException("CreateTable: Table's name have to be StrV");
				}
			} else {
				throw new InterpretException("CreateTable: Unexpected # of args: " + args.size());
			}
		} else {
			throw new InterpretException("CreateTable: Leak of DatabaseName");
		}
	}

	// Drop a table
	public static void DropTable(ArrayList<Value> args) {
		if (dbName != null) {
			Connection c = null;
			Statement stmt = null;
			ArrayList<String> allTablesName = getAllTablesName(dbName);
			if (args.size() == 1) {
				if (args.get(0) instanceof StrV) {
					boolean exst = false;
					tbName = ((StrV) args.get(0)).getValue();
					// chek if table exists
					for (int i = 0; i < allTablesName.size(); i++) {
						//CAN'T USE: if(allTableName.get(i) == tbName){...}
						if (allTablesName.get(i).equals(tbName)) {
							exst = true;
							break;
						}
					}
					if (exst) {
						try {
							Class.forName("org.sqlite.JDBC");
							c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
							System.out.println("Opened database successfully");
							stmt = c.createStatement();
							String sql = "DROP TABLE " + tbName + ";";
							stmt.executeUpdate(sql);
							stmt.close();
							c.close();
						} catch (Exception e) {
							System.err.println(e.getClass().getName() + ": " + e.getMessage());
							System.exit(0);
						}
						System.out.println("Table droped successfully");
					} else {
						throw new InterpretException("DropTable: Table NOT found!");
					}
				} else {
					throw new InterpretException("DropTable: Table's name have to be StrV");
				}

			} else {
				throw new InterpretException("DropTable: Unexpected # of args: " + args.size());
			}
		} else {
			throw new InterpretException("DropTable: Leak of DatabaseName");
		}
	}

	// Insert record(s)
	public static void InsertQuery(ArrayList<Value> args) {
		if (dbName != null) {
			Connection c = null;
			Statement stmt = null;
			if (args.size() == 2) {
				if (args.get(0) instanceof StrV) {
					tbName = ((StrV) args.get(0)).getValue();
					if (args.get(1) instanceof ArrayV) {
						// get the records array
						ArrayV arr = (ArrayV) args.get(1);
						ArrayV key = (ArrayV) arr.getKey();
						String colNames = "";
						String colValues = "";
						// if multi-record
						if (arr.get(key.get(Integer.toString(1)).toString()) instanceof ArrayV) {
							for (int i = 1; i <= key.size(); i++) {
								colNames = "";
								colValues = "";
								ArrayV arr_value = (ArrayV) arr.get(key.get(Integer.toString(i)).toString());
								ArrayV arr_key = (ArrayV) arr_value.getKey();

								for (int j = 1; j <= arr_key.size(); j++) {
									String colValue = "";
									// column name
									String colName = arr_key.get(Integer.toString(j)).toString();
									// column value, add slash to the value
									if (arr_value.get(colName) instanceof StrV) {
										colValue = "'" + addSlash(((StrV) arr_value.get(colName)).getValue()) + "'";
									} else if (arr_value.get(colName) instanceof DoubleV) {
										colValue = addSlash(
												String.valueOf(((DoubleV) arr_value.get(colName)).getValue()));
									}
									colNames += colName;
									colValues += colValue;
									if (j < arr_key.size()) {
										colNames += ",";
										colValues += ", ";
									}
								}
								try {
									Class.forName("org.sqlite.JDBC");
									c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
									c.setAutoCommit(false);
									System.out.println("Opened database successfully");

									stmt = c.createStatement();
									String sql = "INSERT INTO " + tbName + " (" + colNames + ") VALUES (" + colValues
											+ ");";
									stmt.executeUpdate(sql);
									stmt.close();
									c.commit();
									c.close();
								} catch (Exception e) {
									System.err.println(e.getClass().getName() + ": " + e.getMessage());
									System.exit(0);
								}
							}
						}
						// if 1 record
						else if (arr.get(key.get(Integer.toString(1)).toString()) instanceof StrV || arr.get(key.get(Integer.toString(1)).toString()) instanceof DoubleV) {
							for (int i = 1; i <= arr.size(); i++) {
								// column's name
								String colName = key.get(Integer.toString(i)).toString();
								String colValue = "";
								// column's value, add slash to the value
								if (arr.get(colName) instanceof StrV) {
									colValue = "'" + addSlash(((StrV) arr.get(colName)).getValue()) + "'";
								} else if (arr.get(colName) instanceof DoubleV) {
									colValue = addSlash(String.valueOf(((DoubleV) arr.get(colName)).getValue()));
								}
								colNames += colName;
								colValues += colValue;
								if (i < arr.size()) {
									colNames += ",";
									colValues += ", ";
								}
							}
							try {
								Class.forName("org.sqlite.JDBC");
								c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
								c.setAutoCommit(false);
								System.out.println("Opened database successfully");

								stmt = c.createStatement();

								String sql = "INSERT INTO " + tbName + " (" + colNames + ") VALUES (" + colValues
										+ ");";
								stmt.executeUpdate(sql);
								stmt.close();
								c.commit();
								c.close();
							} catch (Exception e) {
								System.err.println(e.getClass().getName() + ": " + e.getMessage());
								System.exit(0);
							}
						} else {
							throw new InterpretException("InsertQuery: Unexpected arg");
						}
						System.out.println("Records created successfully");
					} else {
						throw new InterpretException("InsertQuery: Records have to be ArrayV");
					}
				} else {
					throw new InterpretException("InsertQuery: Table's name have to be StrV");
				}
			} else {
				throw new InterpretException("InsertQuery: Unexpected # of args: " + args.size());
			}
		} else {
			throw new InterpretException("InsertQuery: Leak of DatabaseName");
		}
	}

	// get all columns's info(name, type) from a table
	public static Value getAllColumnsInfo(String tbName) {
		// return type ArrayV
		ArrayV columns = new ArrayV();

		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
			c.setAutoCommit(false);
			System.out.println("Opened database successfully");

			stmt = c.createStatement();

			ResultSet rs = stmt.executeQuery("PRAGMA table_info(" + tbName + ");");
			int i = 1;
			while (rs.next()) {

				ArrayV col_info = new ArrayV();
				col_info.put("NAME", new StrV(rs.getString(2)));
				col_info.put("TYPE", new StrV(rs.getString(3)));
				columns.put(Integer.toString(i++), col_info);
			}
			rs.close();
			stmt.close();

			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		return columns;
	}

	// add slash to avoid SQL injection
	public static String addSlash(String old_str) {
		String new_str = new String();
		new_str = old_str;
		if (new_str.contains("\\")) {
			new_str = new_str.replace("\\", "\\\\");
		}
		if (new_str.contains("\'")) {
			new_str = new_str.replace("\'", "\\\'");
		}
		if (new_str.contains("\"")) {
			new_str = new_str.replace("\"", "\\\"");
		}
		return new_str;
	}

	// Show records, return an ArrayV results
	public static Value SelectQuery(ArrayList<Value> args) {
		if (dbName != null) {
			Connection c = null;
			Statement stmt = null;
			String colNames = new String();
			String conditions = " WHERE ";
			ArrayV results = new ArrayV();
			ArrayList<String> columns = new ArrayList<String>();
			if (args.size() >= 2) {
				// auto show ID
				colNames += "ID, ";
				columns.add("ID");
				if (args.get(0) instanceof StrV) {
					tbName = ((StrV) args.get(0)).getValue();
					if (args.get(1) instanceof ArrayV) {
						ArrayV arr1 = (ArrayV) args.get(1);
						for (int i = 1; i <= arr1.size(); i++) {
							// get columns name
							if (((StrV) arr1.get(Integer.toString(i))).getValue().equals("ID")) {
								continue;
							} else {
								colNames += ((StrV) arr1.get(Integer.toString(i))).getValue();
								columns.add(((StrV) arr1.get(Integer.toString(i))).getValue());
								if (i < arr1.size()) {
									colNames += ", ";
								}
							}
						}
					}
					// if the second parameter is StrV
					else if (args.get(1) instanceof StrV) {
						String args1 = ((StrV) args.get(1)).getValue();
						if (args1.equals("*")) {
							colNames = "*";
							columns.remove(0);
						} else {
							colNames += args1;
							columns.add(args1);
						}

					} else {
						throw new InterpretException("InsertOperation: Unexpected arg");
					}
					// conditions
					if (args.size() == 2) {
						conditions = "";
					} else if (args.size() == 3) {
						/* conditions can be ArrayV */
						// if (args.get(2) instanceof ArrayV) {
						// ArrayV arr2 = (ArrayV) args.get(2);
						// for (int i = 1; i <= arr2.size(); i++) {
						// conditions += ((StrV) arr2.get(Integer.toString(i))).getValue();
						// if (i < arr2.size()) {
						// conditions += " AND ";
						// }
						// }
						// } else if (args.get(2) instanceof StrV) {
						// conditions += ((StrV) args.get(2)).getValue();
						// } else {
						// throw new InterpretException("SelectQuery: Unexpected arg");
						// }
						/* conditions all in a string */
						if (args.get(2) instanceof StrV) {
							conditions += ((StrV) args.get(2)).getValue();
						} else {
							throw new InterpretException("SelectQuery: Conditions have to be StrV");
						}
					} else {
						throw new InterpretException("SelectQuery: Unexpected arg");
					}

				} else {
					throw new InterpretException("SelectQuery: Table's name have to be StrV");
				}
			} else if (args.size() == 1) {
				conditions = "";
				if (args.get(0) instanceof StrV) {
					tbName = ((StrV) args.get(0)).getValue();
					colNames += "*";
				} else {
					throw new InterpretException("SelectQuery: Table's name have to be StrV");
				}
			}

			else {
				throw new InterpretException("SelectQuery: Unexpected # of args: " + args.size());
			}
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
				c.setAutoCommit(false);
				System.out.println("Opened database successfully");
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT " + colNames + " FROM " + tbName + conditions + ";");
				if (colNames.equals("*")) {
					ArrayV cols_info = (ArrayV) getAllColumnsInfo(tbName);
					for (int j = 1; j <= cols_info.size(); j++) {
						columns.add(((StrV) ((ArrayV) cols_info.get(Integer.toString(j))).get("NAME")).getValue());
					}
				}
				int j = 0;
				while (rs.next()) {
					ArrayV result = new ArrayV();
					for (int i = 0; i < columns.size(); i++) {
						System.out.println(columns.get(i) + " = " + rs.getString(columns.get(i)));
						StrV value = new StrV(rs.getString(columns.get(i)));
						result.put(columns.get(i), value);
					}
					results.put(Integer.toString(j++), result);
				}
				rs.close();
				stmt.close();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			System.out.println("Operation done successfully");
			return results;
		} else {
			throw new InterpretException("SelectQuery: Leak of DatabaseName");
		}
	}

	// Update record(s)
	public static void UpdateQuery(ArrayList<Value> args) {
		if (dbName != null) {
			Connection c = null;
			Statement stmt = null;
			if (args.size() >= 2) {
				if (args.get(0) instanceof StrV) {
					tbName = ((StrV) args.get(0)).getValue();
					if (args.get(1) instanceof ArrayV) {
						ArrayV arr_recs = (ArrayV) args.get(1);
						ArrayV arr_recs_key = (ArrayV) arr_recs.getKey();
						// multi-record
						if (arr_recs.get(arr_recs_key.get(Integer.toString(1)).toString()) instanceof ArrayV) {
							// -> many conditions
							if (args.size() == 3) {
								if (args.get(2) instanceof ArrayV) {
									// get conditions array
									ArrayV arr_cons = (ArrayV) args.get(2);
									ArrayV arr_cons_key = (ArrayV) arr_cons.getKey();
									String[] columnsNewValues = new String[arr_recs.size() + 1];
									String[] conditions = new String[arr_recs.size() + 1];
									for (int i = 1; i <= arr_recs.size(); i++) {
										columnsNewValues[i] = "";
										conditions[i] = "";
										ArrayV arr_rec = (ArrayV) arr_recs.get(Integer.toString(i).toString());
										ArrayV arr_rec_key = (ArrayV) arr_rec.getKey();
										String arr_con = ((StrV) arr_cons.get(Integer.toString(i).toString())).getValue();
										/*Case: conditions[i] can be arrayV */
										/*ArrayV arr_con = (ArrayV) arr_cons.get(Integer.toString(i).toString());*/
										// link columnsNewValues
										for (int j = 1; j <= arr_rec.size(); j++) {
											String colValue = "";
											String colName = arr_rec_key.get(Integer.toString(j)).toString();
											if (arr_rec.get(colName) instanceof StrV) {
												colValue = "'" + ((StrV) arr_rec.get(colName)).getValue() + "'";
											} else if (arr_rec.get(colName) instanceof DoubleV) {
												colValue = String.valueOf(((DoubleV) arr_rec.get(colName)).getValue());
											}
											columnsNewValues[i] += colName + " = " + colValue;
											if (j < arr_rec_key.size()) {
												columnsNewValues[i] += ", ";
											}
										}
										// conditions
										conditions[i] += arr_con;
										/*Case: All conditions of 1 record don't have to be in 1 string*/
										/*for (int j = 1; j <= arr_rec.size(); j++) {
											String conValue = ((StrV) arr_con.get(Integer.toString(j))).getValue();
											conditions[i] += conValue;
											if (j < arr_con.size()) {
												conditions[i] += " AND ";
											}
										}*/
										try {
											Class.forName("org.sqlite.JDBC");
											c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
											c.setAutoCommit(false);
											System.out.println("Opened database successfully");
											stmt = c.createStatement();
											String sql = "UPDATE " + tbName + " SET " + columnsNewValues[i] + " WHERE "
													+ conditions[i] + ";";
											stmt.executeUpdate(sql);
											stmt.close();
											c.commit();
											c.close();
										} catch (Exception e) {
											System.err.println(e.getClass().getName() + ": " + e.getMessage());
											System.exit(0);

											System.out.println("Operation done successfully");
										}
									}
								} else {
									throw new InterpretException("UpdateQuery: Each update has respective condition");
								}
							} else {
								throw new InterpretException("UpdateQuery: Unexpected # of args: " + args.size());
							}

						}
						// 1 record
						else if (arr_recs.get(arr_recs_key.get(Integer.toString(1)).toString()) instanceof StrV || arr_recs.get(arr_recs_key.get(Integer.toString(1)).toString()) instanceof DoubleV) {
							String columnsNewValues = "";
							String conditions = "";
							// link columnsNewValues
							for (int i = 1; i <= arr_recs.size(); i++) {
								String colName = arr_recs_key.get(Integer.toString(i)).toString();
								String colValue = "";
								if (arr_recs.get(colName) instanceof StrV) {
									colValue = "'" + ((StrV) arr_recs.get(colName)).getValue() + "'";
								} else if (arr_recs.get(colName) instanceof DoubleV) {
									colValue = String.valueOf(((DoubleV) arr_recs.get(colName)).getValue());
								}
								columnsNewValues += colName + "=" + colValue;
								if (i < arr_recs.size()) {
									columnsNewValues += ", ";
								}
							}
							// if condition
							if (args.size() == 3) {
								/* conditions can be ArrayV */
								/*
								 * if (args.get(2) instanceof ArrayV) { ArrayV arr_cons = (ArrayV) args.get(2);
								 * for (int i = 1; i <= arr_cons.size(); i++) { String arr_con_value = ((StrV)
								 * arr_cons.get(Integer.toString(i))).getValue(); conditions += arr_con_value;
								 * if (i < arr_cons.size()) { conditions += " AND "; } } } //1 condition else if
								 * (args.get(2) instanceof StrV) { conditions += ((StrV)
								 * args.get(2)).getValue(); }
								 */
								/* all conditions in a string */
								if (args.get(2) instanceof StrV) {
									conditions += args.get(2);
								} else {
									throw new InterpretException("UpdateQuery: Conditions have to be StrV");
								}
							}
							try {
								Class.forName("org.sqlite.JDBC");
								c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
								c.setAutoCommit(false);
								System.out.println("Opened database successfully");
								stmt = c.createStatement();
								String sql = "UPDATE " + tbName + " SET ";
								if (args.size() == 3) {
									sql += columnsNewValues + " WHERE " + conditions + ";";
								} else if (args.size() == 2) {
									sql += columnsNewValues + ";";
								}
								stmt.executeUpdate(sql);
								stmt.close();
								c.commit();
								c.close();
							} catch (Exception e) {
								System.err.println(e.getClass().getName() + ": " + e.getMessage());
								System.exit(0);
							}
							System.out.println("Operation done successfully");

						} else {
							throw new InterpretException("UpdateQuery: Unexpected arg" + arr_recs.get(arr_recs_key.get(Integer.toString(1)).toString()));
						}
						//update 1 column in case the 2-nd parameter is StrV, such as: "NAME = 'newName'"
					} else if(args.get(1) instanceof StrV) {
						String columnsNewValues = ((StrV) args.get(1)).getValue();
						String conditions = "";
						if(args.size() == 3) {
							conditions = ((StrV) args.get(2)).getValue();
						}
						try {
							Class.forName("org.sqlite.JDBC");
							c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
							c.setAutoCommit(false);
							System.out.println("Opened database successfully");
							stmt = c.createStatement();
							String sql = "UPDATE " + tbName + " SET ";
							if (args.size() == 3) {
								sql += columnsNewValues + " WHERE " + conditions + ";";
							} else if (args.size() == 2) {
								sql += columnsNewValues + ";";
							}
							stmt.executeUpdate(sql);
							stmt.close();
							c.commit();
							c.close();
						} catch (Exception e) {
							System.err.println(e.getClass().getName() + ": " + e.getMessage());
							System.exit(0);
						}
						System.out.println("Operation done successfully");
					} else {
						throw new InterpretException("UpdateQuery: Unexpected args");
					}
				} else {
					throw new InterpretException("UpdateQuery: Table's name have to be StrV");
				}

			} else {
				throw new InterpretException("UpdateQuery: Unexpected # of args: " + args.size());
			}
		} else {
			throw new InterpretException("UpdateQuery: Leak of DatabaseName");
		}
	}

	// Delete record(s)
	public static void DeleteQuery(ArrayList<Value> args) {
		if (dbName != null) {
			Connection c = null;
			Statement stmt = null;
			String conditions = "";
			if (args.size() == 2) {
				conditions += " WHERE ";
				if (args.get(0) instanceof StrV) {
					tbName = ((StrV) args.get(0)).getValue();
					if (args.get(1) instanceof StrV) {
						conditions += ((StrV) args.get(1)).getValue();
					}
					/* conditions can be ArrayV */
					/*
					 * else if (args.get(1) instanceof ArrayV) { ArrayV arr = (ArrayV) args.get(1);
					 * for (int i = 1; i <= arr.size(); i++) { conditions += ((StrV)
					 * arr.get(Integer.toString(i))).getValue(); if (i < arr.size()) { conditions +=
					 * " AND "; } } }
					 */
					else {
						throw new InterpretException("DeleteQuery: Conditions have to be StrV");
					}
				} else {
					throw new InterpretException("DeleteQuery: Table's name have to be StrV");
				}
			}
			// non-condition
			else if (args.size() == 1) {
				if (args.get(0) instanceof StrV) {
					tbName = ((StrV) args.get(0)).getValue();
				} else {
					throw new InterpretException("DeleteQuery: Table's name have to be StrV");
				}
			} else {
				throw new InterpretException("DeleteQuery: Unexpected # of args: " + args.size());
			}

			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:resource/" + dbName);
				c.setAutoCommit(false);
				System.out.println("Opened database successfully");

				stmt = c.createStatement();
				String sql = "DELETE FROM " + tbName + conditions + ";";
				stmt.executeUpdate(sql);
				stmt.close();
				c.commit();
				c.close();
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage());
				System.exit(0);
			}
			System.out.println("Operation done successfully");
		} else {
			throw new InterpretException("DeleteQuery: Leak of DatabaseName");
		}
	}

	// test function addSlash()
	public static void main(String args[]) throws IOException {
		InputStreamReader cin = null;

		try {
			cin = new InputStreamReader(System.in);
			System.out.println("Enter characters, 'q' to quit.");
			char c;
			do {
				c = (char) cin.read();
				System.out.print(addSlash(Character.toString(c)));
			} while (c != 'q');
		} finally {
			if (cin != null) {
				cin.close();
			}
		}
	}

}
