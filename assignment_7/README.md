# CS548 Assignment 8
## Noriel Valdes

I had to make a bunch of changes to my existing code in order to get all of the requests working as expected.

First of all, in Assignment 7, I totally forgot to initialize the DTO factories in `PatientResoruce` and `ProviderResource`, so those calls had to be added to each respective constructor.  The methods below had been left empty, only containing a TODO comment.

```java
public PatientResource() {
    patientDtoFactory = new PatientDtoFactory(); // NEW
}
public ProviderResource() {
    providerDtoFactory = new ProviderDtoFactory(); // NEW
    treatmentDtoFactory = new TreatmentDtoFactory(); // NEW
}
```

---

Secondly, neither `PatientService` nor `ProviderService` were initializing the `TreatmentDAO` member of the `Patient` and `Provider` entity classes.  I don't know how I managed to get Assignment 6 working without fixing this bug, but trying to add a treatment through REST threw a `NullPointerException`.  Adding a private `TreatmentDAO` to each service, initializing it in the `@PostConstruct initialize()` function so that I can reference the `EntityManager`, and then making sure to set the appropriate field of the entity classes solved the problem.

```java
public TreatmentDto getTreatment(long id, long tid)
        throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
    try {
        Patient patient = patientDAO.getPatient(id);
        patient.setTreatmentDAO(treatmentDAO); // NEW
        TreatmentExporter visitor = new TreatmentExporter();
        return patient.exportTreatment(tid, visitor);
    } catch (PatientExn e) {
        throw new PatientNotFoundExn(e.toString());
    } catch (TreatmentExn e) {
        throw new PatientServiceExn(e.toString());
    }
}
```

---

Finally, the `Provider` and `Patient` links in treatment representations kept coming up with an ID of 0, meaning that the fields were null.  To fix this, I had to go all the way back to the TreatmentExporter from Assignment 5 and add arguments for `patient_id` (the primary key, not the patient's SSN) and `provider_id` to each export function throughout all of the projects.  After explicitly passing these values from the entities to the exporter, I only had to set their values in the DTO and the representation picked them up just fine.

```java
public TreatmentDto exportDrugTreatment(
        long tid,
        long patient_id, // NEW
        long provider_id, // NEW
        String diagnosis,
        String drug,
        float dosage) {
    TreatmentDto dto = factory.createTreatmentDto();
    dto.setId(tid); // NEW
    dto.setPatient(patient_id); // NEW
    dto.setProvider(provider_id); // NEW
    dto.setDiagnosis(diagnosis);
    DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
    drugInfo.setDosage(dosage);
    drugInfo.setDrug(drug);
    dto.setDrugTreatment(drugInfo);
    return dto;
}
```
