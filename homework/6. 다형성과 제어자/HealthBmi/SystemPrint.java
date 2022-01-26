package com.ssafy.hw06.step2;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SystemPrint extends PrintProcess {

	public void printWho(String inputName) throws FileNotFoundException {

		HealthBmiReport.printWho(inputName);
	}

	@Override
	public void print() throws IOException {
		HealthBmiReport.printHealth();
	}

}
