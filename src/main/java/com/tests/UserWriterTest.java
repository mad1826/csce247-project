package com.tests;

import com.model.UserManager;
import com.model.datahandlers.DataWriter;

public class UserWriterTest {
	public static void main(String[] args) {
		UserManager userManager = UserManager.getInstance();
		userManager.createUser("John", "Smith", "js@gmail.com", "123aA#21");
		userManager.createUser("Jane", "Smith", "jas@gmail.com", "31980u32843u#21");

		DataWriter.setData(userManager);
	}
}
