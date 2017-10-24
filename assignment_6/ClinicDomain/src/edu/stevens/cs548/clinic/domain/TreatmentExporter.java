package edu.stevens.cs548.clinic.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TreatmentExporter implements ITreatmentExporter<String> {

	@Override
	public String exportDrugTreatment(long tid, String diagnosis, String drug, float dosage) {
		return String.join("\n", "Drug Treatment: " + tid, "Diagnosis: " + diagnosis, "Drug: " + drug, "Dosage: " + dosage);
	}

	@Override
	public String exportRadiology(long tid, String diagnosis, List<Date> dates) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		List<String> ret = new ArrayList<String>();
		ret.add("Radiology Treatment: " + tid);
		ret.add("Diagnosis: " + diagnosis);
		ret.add("Dates:");
		for (Date d : dates) {
			ret.add("\t" + dateFormatter.format(d));
		}
		return String.join("\n", ret);
	}

	@Override
	public String exportSurgery(long tid, String diagnosis, Date date) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
		return String.join("\n", "Surgery Treatment: " + tid, "Diagnosis: " + diagnosis, "Date: " + dateFormatter.format(date));
	}

}
