package ca.iantria.raid.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import ca.iantria.raid.Main;

public class FileManager {
	
	static public boolean getContents(File aFile, Main m) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(aFile));
			try {
				m.user_id = input.readLine();
				m.highest_score = input.readLine();
				return true;
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			return false;
		}
	}

	static public void setContents(File aFile, Main m) {

		Writer output;
		try {
			output = new BufferedWriter(new FileWriter(aFile));

			try {
				output.write(m.user_id + System.getProperty("line.separator"));
				output.write(m.highest_score);
			} finally {
				output.close();
			}
		} catch (IOException e) {
		}
	}
}
