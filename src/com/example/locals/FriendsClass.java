/*Final Project
 * 
 * FriendsClass.java
 * 
 * Akshay Pandian
 * Swathi Balasubramanya Ayas
 */
package com.example.locals;

public class FriendsClass {
	String name;
	String objectID;

	public FriendsClass(String name, String objectID) {
		super();
		this.name = name;
		this.objectID = objectID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		String friend = (String) o;
		if (this.objectID.equals(friend))
			return true;
		return true;
	}

}
