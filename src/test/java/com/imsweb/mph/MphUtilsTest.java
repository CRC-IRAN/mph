/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

public class MphUtilsTest {
/*
    @Test
    public void testFindCancerGroup() {
        //Invalid and unknown primary site, histology or behavior ----> Undefined group
        Assert.assertNull(MphUtils.findCancerGroup(null, "8100", "1"));
        Assert.assertNull(MphUtils.findCancerGroup("123", "8100", "2"));
        Assert.assertNull(MphUtils.findCancerGroup("C4567", "8100", "0"));
        Assert.assertNull(MphUtils.findCancerGroup("D456", "8100", "0"));
        Assert.assertNull(MphUtils.findCancerGroup("C809", "8100", "3"));
        Assert.assertNull(MphUtils.findCancerGroup("C329", null, "0"));
        Assert.assertNull(MphUtils.findCancerGroup("C000", "10", "0"));
        Assert.assertNull(MphUtils.findCancerGroup("C005", "8100", "01"));
        Assert.assertNull(MphUtils.findCancerGroup("C005", "8100", "5"));
        Assert.assertNull(MphUtils.findCancerGroup("C005", "8100", "A"));
        //lymphoma and leukemia
        Assert.assertNull(MphUtils.findCancerGroup("C329", "9590", "3"));
        // non reportable
        Assert.assertNull(MphUtils.findCancerGroup("C180", "8100", "1"));
        Assert.assertNull(MphUtils.findCancerGroup("C440", "8725", "0"));

        //Head and Neck
        Assert.assertEquals(new Mp2007HeadAndNeckGroup(), MphUtils.findCancerGroup("C005", "8100", "3"));

        //Colon
        Assert.assertEquals(new Mp2007ColonGroup(), MphUtils.findCancerGroup("C180", "8100", "3"));

        //Lung
        Assert.assertEquals(new Mp2007LungGroup(), MphUtils.findCancerGroup("C340", "8100", "3"));

        //Melanoma
        Assert.assertEquals(new Mp2007MelanomaGroup(), MphUtils.findCancerGroup("C440", "8725", "3"));

        //Breast
        Assert.assertEquals(new Mp2007BreastGroup(), MphUtils.findCancerGroup("C500", "8100", "3"));

        //Kidney
        Assert.assertEquals(new Mp2007KidneyGroup(), MphUtils.findCancerGroup("C649", "8100", "3"));

        //Urinary
        Assert.assertEquals(new Mp2007UrinaryGroup(), MphUtils.findCancerGroup("C672", "8100", "3"));

        //Benign Brain 
        Assert.assertEquals(new Mp2007BenignBrainGroup(), MphUtils.findCancerGroup("C751", "8100", "0"));

        //Malignant Brain 
        Assert.assertEquals(new Mp2007MalignantBrainGroup(), MphUtils.findCancerGroup("C751", "8100", "3"));

        //Other Sites        
        Assert.assertEquals(new Mp2007OtherSitesGroup(), MphUtils.findCancerGroup("C887", "8200", "3")); //primary site not in groups
        Assert.assertEquals(new Mp2007OtherSitesGroup(), MphUtils.findCancerGroup("C445", "8800", "3")); //melanoma with excluded histology
        Assert.assertEquals(new Mp2007OtherSitesGroup(), MphUtils.findCancerGroup("C180", "9140", "3")); //Kaposi sarcoma
        Assert.assertEquals(new Mp2007OtherSitesGroup(), MphUtils.findCancerGroup("C751", "8100", "2")); //Brain which is neither malignant nor benign
    }

    @Test
    public void testComputePrimariesSpecialCases() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //Invalid properties
        i1.setPrimarySite("C809");
        i2.setPrimarySite("C080");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());
        Assert.assertTrue(output.getReason().contains("Valid"));

        // Years before 2007
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2006");
        i1.setPrimarySite("C701");
        i2.setPrimarySite("C700");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("0");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.NOT_APPLICABLE, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());
        Assert.assertTrue(output.getReason().contains("2007"));
        //years after 2007
        i2.setDateOfDiagnosisYear("2007");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        //invalid years
        i2.setDateOfDiagnosisYear("XXXX");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.NOT_APPLICABLE, output.getResult());
        //9999 years
        i2.setDateOfDiagnosisYear("9999");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.NOT_APPLICABLE, output.getResult());
        //blank year
        i2.setDateOfDiagnosisYear(null);
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.NOT_APPLICABLE, output.getResult());

        //Unknown tumor group
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C080");
        i1.setHistologyIcdO3("9590");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());
        Assert.assertTrue(output.getReason().contains("groups"));
        //Different group
        i1.setPrimarySite("C080");
        i2.setPrimarySite("C342");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertTrue(output.getAppliedRules().isEmpty());
        Assert.assertTrue(output.getReason().contains("different"));
    }

    @Test
    public void testComputePrimariesBenignBrain() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.
        //This will never happen, since the two conditions belong to different cancer group.

        // M4 - Tumors with ICD-O-3 topography codes that are different at the second (C?xx) and/or third characters (Cx?x), or fourth (Cxx?) are multiple primaries.
        i1.setPrimarySite("C701");
        i2.setPrimarySite("C700");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("0");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        // M5 - Tumors on both sides (left and right) of a paired site (Table 1) are multiple primaries.
        i1.setPrimarySite("C714");
        i2.setPrimarySite("C714");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("0");
        i1.setLaterality("1");
        i2.setLaterality("4");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        i2.setLaterality("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("paired"));

        // M6 - An atypical choroid plexus papilloma (9390/1) following a choroid plexus papilloma, NOS (9390/0) is a single primary.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("9390");
        i2.setHistologyIcdO3("9390");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("0");
        i1.setLaterality("1");
        i2.setLaterality("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Unable"));
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("2"); //9390/0 is following 9390/1 -- continue to the next rule
        i1.setDateOfDiagnosisDay("1");
        i2.setDateOfDiagnosisDay("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisMonth("1"); //9390/0 and 9390/1 diagnosed same day -- continue to the next rule
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());
        i1.setDateOfDiagnosisMonth("2");
        i2.setDateOfDiagnosisMonth("1"); //9390/1 is following 9390/0 Single primary
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("choroid"));

        // M7 - A neurofibromatosis, NOS (9540/1) following a neurofibroma, NOS (9540/0) is a single primary.
        i1.setHistologyIcdO3("9540");
        i2.setHistologyIcdO3("9540");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("neurofibroma"));

        // M8 - Tumors with two or more histologic types on the same branch in Chart 1 are a single primary.
        i1.setHistologyIcdO3("9383");
        i2.setHistologyIcdO3("9444");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));

        // M9 - Tumors with multiple histologic types on different branches in Chart 1 are multiple primaries.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("9383");
        i2.setHistologyIcdO3("9562");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));

        // M10 - Tumors with two or more histologic types and at least one of the histologies is not listed in Chart 1 are multiple primaries.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("9383");
        i2.setHistologyIcdO3("9562");
        i1.setBehaviorIcdO3("0");
        i2.setBehaviorIcdO3("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("8740");
        i2.setHistologyIcdO3("8730");
        i1.setBehaviorIcdO3("1");
        i2.setBehaviorIcdO3("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setPrimarySite("C720");
        i2.setPrimarySite("C720");
        i1.setHistologyIcdO3("8746");
        i2.setHistologyIcdO3("8740");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesBreast() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        // M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        //This never happens. Breast is C500-C509,
        //M5- Tumors diagnosed more than five (5) years apart are multiple primaries.
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2009");
        i2.setDateOfDiagnosisYear("2014");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("diagnosis date"));
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("five"));

        //M6- Inflammatory carcinoma in one or both breasts is a single primary. (8530/3)
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8530");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("carcinoma"));

        //M7- Tumors on both sides (right and left breast) are multiple primaries.
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8730");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisYear("2010");
        i1.setLaterality("1");
        i2.setLaterality("9");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        i2.setLaterality("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));

        //M8- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setPrimarySite("C500");
        i1.setHistologyIcdO3("8530");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C509");
        i2.setHistologyIcdO3("8730");
        i2.setBehaviorIcdO3("2");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i2.setDateOfDiagnosisYear("2009");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("no enough diagnosis date")); //not sure if they are 60 days apart
        i2.setDateOfDiagnosisYear("2007");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("60 days"));

        //M9- Tumors that are intraductal or duct and Paget Disease are a single primary.
        i1.setDateOfDiagnosisYear("2008");
        i1.setBehaviorIcdO3("2");
        i1.setHistologyIcdO3("8401"); //intraductal
        i2.setDateOfDiagnosisYear("2007");
        i2.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8542"); //paget
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Paget"));

        //M10- Tumors that are lobular (8520) and intraductal or duct are a single primary.
        i2.setHistologyIcdO3("8520"); //lobular
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lobular"));

        //M11- Multiple intraductal and/or duct carcinomas are a single primary.
        i2.setHistologyIcdO3("8500"); //duct
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("duct"));
        i2.setHistologyIcdO3("8230"); //another intraductal
        i2.setBehaviorIcdO3("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("duct"));

        //M12- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8500");
        i2.setHistologyIcdO3("8510");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M13- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8506");
        i2.setHistologyIcdO3("8508");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesColon() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        // M3 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more malignant polyps is a single primary.
        i1.setPrimarySite("C180");
        i1.setHistologyIcdO3("8220");
        i1.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setPrimarySite("C180");
        i2.setHistologyIcdO3("8262");
        i2.setBehaviorIcdO3("2");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyps"));
        i1.setBehaviorIcdO3("2"); //at least one should be malignant
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(1, output.getAppliedRules().size());
        i2.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8220"); //polyp
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(1, output.getAppliedRules().size());
        i2.setPrimarySite("C185"); //not same site, no problem as long as it is colon
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(1, output.getAppliedRules().size());
        i2.setHistologyIcdO3("8265"); // not in polyp group, continue to the next rule
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(1, output.getAppliedRules().size());

        //M4- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) and/or fourth (C18?) character are multiple primaries.
        i1.setPrimarySite("C180");
        i2.setPrimarySite("C185");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        i2.setPrimarySite("C180"); //not different, continue to the next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(2, output.getAppliedRules().size());

        //M5- Tumors diagnosed more than one (1) year apart are multiple primaries.
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2013"); //definitely more than a year apart
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("one"));
        i2.setDateOfDiagnosisYear("2014"); //not enough information
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M5"));
        i2.setDateOfDiagnosisYear("2015"); //definitely less than a year apart, continue to next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());

        //M6- An invasive tumor following an insitu tumor more than 60 days after diagnosis is a multiple primary.
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("04"); // Not sure if they are 60 days apart
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M6"));
        i1.setDateOfDiagnosisMonth("02");
        i2.setDateOfDiagnosisMonth("05"); //April + May. definitely greater than 60 days
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("60"));
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2"); //insitu is following invasive, continue to next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(4, output.getAppliedRules().size());

        //M7- A frank malignant or in situ adenocarcinoma and an insitu or malignant tumor in a polyp are a single primary.
        i1.setHistologyIcdO3("8220");
        i2.setHistologyIcdO3("8003");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("adenocarcinoma"));
        i1.setHistologyIcdO3("8222"); //not polyp or adenocarcinoma, continue to next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(5, output.getAppliedRules().size());

        //M8 NOS vs Specific
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8148");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));
        i1.setHistologyIcdO3("8130"); //not in the NOS list
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(6, output.getAppliedRules().size());

        //M9- Multiple insitu and/or malignant polyps are a single primary.
        i1.setHistologyIcdO3("8213");
        i2.setHistologyIcdO3("8213");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyps"));
        i2.setHistologyIcdO3("8265");//not polyp, continue to next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(7, output.getAppliedRules().size());

        //M10- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8213");
        i2.setHistologyIcdO3("8265");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));
        i1.setHistologyIcdO3("8265");
        i2.setHistologyIcdO3("8265"); //not different in histology, continue to next step
        //M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesHeadAndNeck() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - Tumors on the right side and the left side of a paired site are multiple primaries.
        i1.setPrimarySite("C090");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setPrimarySite("C098");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i2.setLaterality("9");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        i2.setLaterality("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("paired"));

        //M4- Tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004) are multiple primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C000");
        i1.setHistologyIcdO3("8000");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C001");
        i2.setHistologyIcdO3("8100");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("lip"));

        //M5- Tumors on the upper gum (C030) and the lower gum (C031) are multiple primaries.
        i1.setPrimarySite("C030");
        i2.setPrimarySite("C031");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("gum"));

        //M6- Tumors in the nasal cavity (C300) and the middle ear (C301) are multiple primaries.
        i1.setPrimarySite("C300");
        i2.setPrimarySite("C301");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("nasal"));

        //M7- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C000");
        i2.setPrimarySite("C148");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        i1.setPrimarySite("C138");
        i2.setPrimarySite("C148");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        //M8- An invasive tumor following an insitu tumor more than 60 days after diagnosis are multiple primaries.
        i1.setPrimarySite("C147");
        i2.setPrimarySite("C148");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisMonth("7");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        i2.setDateOfDiagnosisMonth("11");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M8"));

        //M9- Tumors diagnosed more than five (5) years apart are multiple primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C147");
        i2.setPrimarySite("C148");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8100");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2009");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("five"));
        i2.setDateOfDiagnosisYear("2010");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M9"));

        //M10 -
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C147");
        i2.setPrimarySite("C148");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8000");
        i2.setHistologyIcdO3("8004");
        i1.setDateOfDiagnosisYear("2013");
        i2.setDateOfDiagnosisYear("2010");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));
        i1.setHistologyIcdO3("8070");
        i2.setHistologyIcdO3("8323");
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1 = new MphInput();
        i2 = new MphInput();
        i1.setPrimarySite("C147");
        i2.setPrimarySite("C148");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8900");
        i2.setHistologyIcdO3("8910");
        i1.setDateOfDiagnosisYear("2013");
        i2.setDateOfDiagnosisYear("2010");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i2.setHistologyIcdO3("8904");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesKidney() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - Wilms tumors are a single primary. (8960/3)
        i1.setPrimarySite("C649");
        i1.setHistologyIcdO3("8960");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C649");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Wilms"));

        // M4 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        // This will never happen since all kidney tumors are C649

        // M5 - Tumors in both the right kidney and in the left kidney are multiple primaries.
        i1.setHistologyIcdO3("8060");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both the right kidney and in the left"));

        // M6 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        i1.setHistologyIcdO3("8060");
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisYear("2012");
        i2.setDateOfDiagnosisMonth("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("three"));

        // M7 - An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setHistologyIcdO3("8060");
        i1.setBehaviorIcdO3("2");
        i2.setHistologyIcdO3("8960");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i1.setDateOfDiagnosisMonth("1");
        i2.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisMonth("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));

        // M8 - One tumor with a specific renal cell type and another tumor with a different specific renal cell type are multiple primaries (table 1 in pdf).
        i1.setHistologyIcdO3("8510"); //Medullary carcinoma
        i1.setBehaviorIcdO3("3");
        i2.setHistologyIcdO3("8260"); //Papillary (Chromophil)
        i2.setBehaviorIcdO3("3");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("renal cell type"));

        // M9
        i1.setHistologyIcdO3("8312");
        i2.setHistologyIcdO3("8317");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));

        // M10- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8312");
        i2.setHistologyIcdO3("8370");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8300");
        i2.setHistologyIcdO3("8305");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesLung() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        //This will never be true, lung group is C340-C349, 2nd and 3rd characters are always the same.


        //M4- At least one tumor that is non-small cell carcinoma (8046) and another tumor that is small cell carcinoma (8041-8045) are multiple primaries.
        i1.setPrimarySite("C342");
        i1.setHistologyIcdO3("8046");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C349");
        i2.setHistologyIcdO3("8043");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("carcinoma"));

        //M5- A tumor that is adenocarcinoma with mixed subtypes (8255) and another that is bronchioloalveolar (8250-8254) are multiple primaries.
        i1.setHistologyIcdO3("8253");
        i2.setHistologyIcdO3("8255");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("adenocarcinoma"));

        //M6- A single tumor in each lung is multiple primaries.
        i1.setHistologyIcdO3("8153");
        i2.setHistologyIcdO3("8155");
        i1.setLaterality("2");
        i2.setLaterality("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("each lung"));
        i1.setLaterality("9");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        i1.setLaterality("4");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > 4);

        //M7- Multiple tumors in both lungs with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8150");
        i2.setHistologyIcdO3("8165");
        i1.setLaterality("1");
        i2.setLaterality("4");
        i1.setPrimarySite("C342");
        i2.setPrimarySite("C349");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));
        //if they are on the same lung, dont apply this rule
        i1.setLaterality("1");
        i2.setLaterality("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertTrue(output.getAppliedRules().size() > 5);

        //M8- Tumors diagnosed more than three (3) years apart are multiple primaries.
        i1.setHistologyIcdO3("8160");
        i2.setHistologyIcdO3("8165");
        i1.setDateOfDiagnosisYear("2013");
        i1.setDateOfDiagnosisMonth("09");
        i2.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisMonth("08");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("years"));
        i2.setDateOfDiagnosisMonth("09");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());

        //M9- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2010");
        i2.setDateOfDiagnosisMonth("7");
        i1.setDateOfDiagnosisMonth(null);
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        i2.setDateOfDiagnosisMonth("11");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());

        //M10- Tumors with non-small cell carcinoma, NOS (8046) and a more specific non-small cell carcinoma type (chart 1) are a single primary.
        i2.setBehaviorIcdO3("3");
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8310");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("8046"));

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8046");
        i2.setHistologyIcdO3("8021");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology codes"));

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8045");
        i2.setHistologyIcdO3("8041");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesMalignantBrain() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;
        // M4 - An invasive brain tumor (/3) and either a benign brain tumor (/0) or an uncertain/borderline brain tumor (/1) are always multiple primaries.
        //This will never happen, since the two conditions belong to different cancer group.

        // M5- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C700");
        i2.setPrimarySite("C725");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        // M6 - A glioblastoma or glioblastoma multiforme (9440) following a glial tumor is a single primary.
        i1.setPrimarySite("C710");
        i2.setPrimarySite("C714");
        i1.setHistologyIcdO3("9440");
        i2.setHistologyIcdO3("9380");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult()); // can't tell which tumor follows which
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M6"));
        i1.setDateOfDiagnosisYear("2016"); //i1, 9440 is following glial
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("glial"));
        i1.setDateOfDiagnosisYear("2014"); //glial is following i1 (9440), continue to the next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(3, output.getAppliedRules().size());


        // M7 - Tumors with ICD-O-3 histology codes on the same branch in Chart 1 or Chart 2 are a single primary.
        i1.setPrimarySite("C710");
        i2.setPrimarySite("C714");
        i1.setHistologyIcdO3("9508");
        i2.setHistologyIcdO3("9490");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));
        i1.setHistologyIcdO3("9503");
        i2.setHistologyIcdO3("9410");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));
        i1.setHistologyIcdO3("9100");
        i2.setHistologyIcdO3("9071");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 2"));

        // M8 - Tumors with ICD-O-3 histology codes on different branches in Chart 1 or Chart 2 are multiple primaries.
        i1.setHistologyIcdO3("9505");
        i2.setHistologyIcdO3("9523");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 1"));
        i1.setHistologyIcdO3("9539");
        i2.setHistologyIcdO3("9540");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Chart 2"));

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8230");
        i2.setHistologyIcdO3("8240");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        // M10- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8230");
        i2.setHistologyIcdO3("8235");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
        i1.setHistologyIcdO3("9397");
        i2.setHistologyIcdO3("9397");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesMelanoma() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //M3- Melanomas in sites with ICD-O-3 topography codes that are different at the second (C?xx), third (Cx?x) or fourth (C44?) character are multiple primaries.
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C447");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        //M4- Melanomas with different laterality are multiple primaries.
        i2.setPrimarySite("C442");
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        //melanoma mid-line laterality is considered as different laterality of right or left
        i2.setLaterality("5");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        i2.setLaterality("4");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));

        //M5- Melanomas with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setPrimarySite("C442");
        i1.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("3");
        i2.setPrimarySite("C442");
        i2.setHistologyIcdO3("8780");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        //M6- An invasive melanoma that occurs more than 60 days after an in situ melanoma is a multiple primary.
        i1.setPrimarySite("C442");
        i2.setPrimarySite("C442");
        i1.setHistologyIcdO3("8725");
        i2.setHistologyIcdO3("8720");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("1");
        i1.setDateOfDiagnosisYear("2009");
        i2.setDateOfDiagnosisYear("2009"); // same year no month information
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M6"));
        i2.setDateOfDiagnosisYear("2011"); // invasive on 2006, insitu on 2004
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));

        //M7- Melanomas diagnosed more than 60 days apart are multiple primaries.
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("60"));

        //M8- Melanomas that do not meet any of the above criteria are abstracted as a single primary.
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2011");
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesOtherSites() {
        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        //M3- Adenocarcinoma of the prostate is always a single primary. (8140)
        i1.setPrimarySite("C619");
        i2.setPrimarySite("C619");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("prostate"));

        //M4- Retinoblastoma is always a single primary (unilateral or bilateral). (9510, 9511, 9512, 9513)
        i1.setHistologyIcdO3("9510");
        i2.setHistologyIcdO3("9513");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Retinoblastoma"));

        //M5- Kaposi sarcoma (any site or sites) is always a single primary.
        i1.setPrimarySite("C400");
        i2.setPrimarySite("C619");
        i1.setHistologyIcdO3("9140");
        i2.setHistologyIcdO3("9140");
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Kaposi sarcoma"));

        //M6- Follicular and papillary tumors in the thyroid within 60 days of diagnosis are a single primary. (C739, 8340)
        i1.setPrimarySite("C739");
        i2.setPrimarySite("C739");
        i1.setHistologyIcdO3("8340");
        i2.setHistologyIcdO3("8340");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setDateOfDiagnosisYear("2011");
        i2.setDateOfDiagnosisYear("2011"); // same year month unknown
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Unable"));
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("02"); // within 60 days definitely
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("thyroid"));

        //M7- Bilateral epithelial tumors (8000-8799) of the ovary within 60 days are a single primary. Ovary = C569
        i1.setPrimarySite("C569");
        i2.setPrimarySite("C569");
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8799");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ovary"));

        // M8 - Tumors on both sides (right and left) of a site listed in Table 1 are multiple primaries.
        i1.setPrimarySite("C622");
        i2.setPrimarySite("C629");
        i1.setHistologyIcdO3("8001");
        i2.setHistologyIcdO3("8799");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("laterality"));
        i1.setLaterality("1");
        i2.setLaterality("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));
        i1.setPrimarySite("C740");
        i2.setPrimarySite("C749");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));
        i1.setPrimarySite("C630");
        i2.setPrimarySite("C630");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("both sides"));

        //M9 - Adenocarcinoma in adenomatous polyposis coli (familial polyposis) with one or more in situ or malignant polyps is a single primary.
        i1.setPrimarySite("C199");
        i2.setPrimarySite("C209");
        i1.setHistologyIcdO3("8220");
        i2.setHistologyIcdO3("8262");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("adenomatous"));
        i1.setBehaviorIcdO3("2"); // Both are insitu, continue to next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(7, output.getAppliedRules().size());

        //M10 - Tumors diagnosed more than one (1) year apart are multiple primaries.
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2013");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("one"));
        i1.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisMonth("01");
        i2.setDateOfDiagnosisYear("2014"); //not enough information
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("M10"));
        i2.setDateOfDiagnosisYear("2015"); //less than a year, continue to the next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(8, output.getAppliedRules().size());

        //M11 - Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C199");
        i2.setPrimarySite("C209");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        //M12 - Tumors with ICD-O-3 topography codes that differ only at the fourth character (Cxx?) and are in any one of the following primary sites are multiple primaries. ** Anus and anal canal (C21_) Bones, joints, and articular cartilage (C40_- C41_) Peripheral nerves and autonomic nervous system (C47_) Connective subcutaneous and other soft tissues (C49_) Skin (C44_)
        i1.setPrimarySite("C471");
        i2.setPrimarySite("C472");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(10, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));
        i2.setPrimarySite("C471"); // not differ at the 4th character
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(10, output.getAppliedRules().size());
        i1.setPrimarySite("C461");
        i2.setPrimarySite("C462"); // not in the above list
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(10, output.getAppliedRules().size());

        //M13 - A frank in situ or malignant adenocarcinoma and an in situ or malignant tumor in a polyp are a single primary.
        i1.setHistologyIcdO3("8220"); //polyp
        i2.setHistologyIcdO3("8141"); //adenocarcinoma
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(11, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyp"));
        i2.setHistologyIcdO3("8211"); //both are polyp, continue to the next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(11, output.getAppliedRules().size());

        //M14 - Multiple in situ and/or malignant polyps are a single primary.
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(12, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("polyps"));

        //M15 - An invasive tumor following an in situ tumor more than 60 days after diagnosis is a multiple primary.
        i1.setPrimarySite("C199");
        i2.setPrimarySite("C197");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        i1.setDateOfDiagnosisMonth("07");
        i2.setDateOfDiagnosisMonth("01");
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8140");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(13, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));
        i1.setBehaviorIcdO3("2");
        i2.setBehaviorIcdO3("3"); //isitu is following invasive, go to next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(13, output.getAppliedRules().size());

        //M16 - NOS VS Specific
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8147");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(14, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("NOS"));
        i2.setHistologyIcdO3("8313"); //not specific for 8140, go to next step
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(14, output.getAppliedRules().size());

        //M17- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8170");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(15, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));
        i2.setHistologyIcdO3("8149"); //different only on the last digit
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertNotEquals(15, output.getAppliedRules().size());

        //M18- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setHistologyIcdO3("8140");
        i2.setHistologyIcdO3("8149");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(16, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    }

    @Test
    public void testComputePrimariesUrinary() {

        MphInput i1 = new MphInput(), i2 = new MphInput();
        MphOutput output;

        // M3 - When no other urinary sites are involved, tumor(s) in the right renal pelvis AND tumor(s) in the left renal pelvis are multiple primaries. (C659)
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C659");
        i1.setHistologyIcdO3("8720");
        i2.setHistologyIcdO3("8780");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        i1.setLaterality("1");
        i2.setLaterality("2");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2015");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(1, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("renal"));

        // M4 - When no other urinary sites are involved, tumor(s) in both the right ureter AND tumor(s) in the left ureter are multiple primaries. (C669)
        i1.setPrimarySite("C669");
        i2.setPrimarySite("C669");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(2, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("ureter"));

        // M5- An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C679");
        i1.setHistologyIcdO3("8720");
        i2.setHistologyIcdO3("8780");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("2");
        i1.setDateOfDiagnosisYear("2007");
        i2.setDateOfDiagnosisYear("2007");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.QUESTIONABLE, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Unable"));
        i1.setDateOfDiagnosisMonth("05");
        i2.setDateOfDiagnosisMonth("01");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(3, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("invasive"));

        // M6 - Bladder tumors with any combination of the following histologies: papillary carcinoma (8050), transitional cell carcinoma (8120-8124),
        // or papillary transitional cell carcinoma (8130-8131), are a single primary.
        i1.setPrimarySite("C672");
        i2.setPrimarySite("C679");
        i1.setHistologyIcdO3("8050");
        i2.setHistologyIcdO3("8123");
        i1.setBehaviorIcdO3("3");
        i2.setBehaviorIcdO3("3");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(4, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Bladder"));

        // M7 - Tumors diagnosed more than three (3) years apart are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C679");
        i1.setHistologyIcdO3("8720");
        i2.setHistologyIcdO3("8180");
        i1.setDateOfDiagnosisYear("2015");
        i2.setDateOfDiagnosisYear("2008");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(5, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("three"));

        // M8 - Urothelial tumors in two or more of the following sites are a single primary* (See Table 1 of pdf)
        // Renal pelvis (C659), Ureter(C669), Bladder (C670-C679), Urethra /prostatic urethra (C680)
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C680");
        i1.setHistologyIcdO3("8131");
        i2.setHistologyIcdO3("8020");
        i1.setDateOfDiagnosisYear("2008");
        i2.setDateOfDiagnosisYear("2007");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(6, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("Urothelial"));

        // M9- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C680");
        i1.setHistologyIcdO3("8130");
        i2.setHistologyIcdO3("8150");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(7, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("histology"));

        // M10- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        i1.setPrimarySite("C659");
        i2.setPrimarySite("C680");
        i1.setHistologyIcdO3("8630");
        i2.setHistologyIcdO3("8630");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.MULTIPLE_PRIMARIES, output.getResult());
        Assert.assertEquals(8, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("topography"));

        // M11- Tumors that do not meet any of the criteria are abstracted as a single primary.
        i1.setPrimarySite("C670");
        i2.setPrimarySite("C675");
        i1.setHistologyIcdO3("8630");
        i2.setHistologyIcdO3("8630");
        output = MphUtils.computePrimaries(i1, i2);
        Assert.assertEquals(MphUtils.MPResult.SINGLE_PRIMARY, output.getResult());
        Assert.assertEquals(9, output.getAppliedRules().size());
        Assert.assertTrue(output.getReason().contains("criteria"));
    } */
}
