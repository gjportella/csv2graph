package br.unb.cic.conversion.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public final class CsvUtil {

	private static final String CHARSET_NAME_UTF_8 = "UTF-8";

	public static final BufferedReader createInput(String inputFilePath) throws Exception {

		BufferedReader input = new BufferedReader(
				new InputStreamReader(new FileInputStream(inputFilePath), CHARSET_NAME_UTF_8));
		return input;
	}

	public static final BufferedWriter createOutput(String outputFilePath) throws Exception {

		BufferedWriter output = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(outputFilePath), CHARSET_NAME_UTF_8));
		return output;
	}
}
