package br.unb.cic.conversion.csv;

public abstract class CsvWrapper {

	abstract void openFile() throws Exception;

	abstract void closeFile() throws Exception;
}
