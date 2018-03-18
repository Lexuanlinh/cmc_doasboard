package com.cmc.dashboard.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class WriteCsv {
	static int firstTime = 0;
	static int hasDate = 0;

	public static <T> void writeCsv(String fileName, List<String> header, List<ArrayList<String>> content)
			throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(239);
	    fos.write(187);
	    fos.write(191);
	    
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(fos, "UTF-8"));
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
		csvPrinter.printRecord(header);
		csvPrinter.printRecords(content);
		csvPrinter.flush();
		csvPrinter.close();

	}
	
	public static String getStringLine(List<String> list){
		String stringLine = list.toString();
		return stringLine.substring(1, stringLine.length()-1);
	}

	public static void addList(List<String> list, String parame) {

		if (firstTime == 0) {
			list.add(parame);
			firstTime++;
		} else {
			for (String string : list) {
				if (string.equalsIgnoreCase(parame) == true) {
					hasDate++;
				}
			}
			if (hasDate == 0) {
				list.add(parame);
			}
			hasDate = 0;
		}
	}
}
