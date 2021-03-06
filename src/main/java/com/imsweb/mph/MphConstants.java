/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imsweb.mph.mpgroups.GroupUtility;

public class MphConstants {

    //Group Ids for the set of rules
    public static final String MP_1998_HEMATO_GROUP_ID = "mp_1998_hemato";
    public static final String MP_2001_HEMATO_GROUP_ID = "mp_2001_hemato";
    public static final String MP_2010_HEMATO_GROUP_ID = "mp_2010_hemato";
    public static final String MP_2004_SOLID_MALIGNANT_GROUP_ID = "mp_2004_solid_malignant";
    public static final String MP_2004_BENIGN_BRAIN_GROUP_ID = "mp_2004_benign_brain";
    public static final String MP_2007_BENIGN_BRAIN_GROUP_ID = "mp_2007_benign_brain";
    public static final String MP_2007_BREAST_GROUP_ID = "mp_2007_breast";
    public static final String MP_2007_COLON_GROUP_ID = "mp_2007_colon";
    public static final String MP_2007_HEAD_AND_NECK_GROUP_ID = "mp_2007_head_and_neck";
    public static final String MP_2007_KIDNEY_GROUP_ID = "mp_2007_kidney";
    public static final String MP_2007_LUNG_GROUP_ID = "mp_2007_lung";
    public static final String MP_2007_MALIGNANT_BRAIN_GROUP_ID = "mp_2007_malignant_brain";
    public static final String MP_2007_MELANOMA_GROUP_ID = "mp_2007_melanoma";
    public static final String MP_2007_OTHER_SITES_GROUP_ID = "mp_2007_other_sites";
    public static final String MP_2007_URINARY_GROUP_ID = "mp_2007_urinary";

    //Group Names for the set of rules
    public static final String MP_1998_HEMATO_GROUP_NAME = "1998 Hematopoietic";
    public static final String MP_2001_HEMATO_GROUP_NAME = "2001 Hematopoietic";
    public static final String MP_2010_HEMATO_GROUP_NAME = "2010 Hematopoietic";
    public static final String MP_2004_SOLID_MALIGNANT_GROUP_NAME = "2004 Solid Malignant";
    public static final String MP_2004_BENIGN_BRAIN_GROUP_NAME = "2004 Benign Brain";
    public static final String MP_2007_BENIGN_BRAIN_GROUP_NAME = "2007 Benign Brain";
    public static final String MP_2007_BREAST_GROUP_NAME = "2007 Breast";
    public static final String MP_2007_COLON_GROUP_NAME = "2007 Colon";
    public static final String MP_2007_HEAD_AND_NECK_GROUP_NAME = "2007 Head and Neck";
    public static final String MP_2007_KIDNEY_GROUP_NAME = "2007 Kidney";
    public static final String MP_2007_LUNG_GROUP_NAME = "2007 Lung";
    public static final String MP_2007_MALIGNANT_BRAIN_GROUP_NAME = "2007 Malignant Brain";
    public static final String MP_2007_MELANOMA_GROUP_NAME = "2007 Melanoma";
    public static final String MP_2007_OTHER_SITES_GROUP_NAME = "2007 Other Sites";
    public static final String MP_2007_URINARY_GROUP_NAME = "2007 Urinary";

    //Topographies
    public static final String COLON = "C18";
    public static final String RECTOSIGMOID = "C199";
    public static final String RECTUM = "C209";
    public static final String BREAST = "C50";
    public static final String OVARY = "C569";
    public static final String PROSTATE = "C619";
    public static final String RENAL_PELVIS = "C659";
    public static final String URETER = "C669";
    public static final String BLADDER = "C67";
    public static final String URETHRA = "C680";
    public static final String THYROID = "C739";
    public static final String LYMPH_NODE = "C77";
   
    public static final List<String> EXACT_MATCH_SITES = Collections.unmodifiableList(Arrays.asList("C18", "C21", "C38", "C40", "C41", "C44", "C47", "C49"));
    public static final List<String> TONGUE = Collections.unmodifiableList(Arrays.asList("C01", "C02"));
    public static final List<String> MOUTH = Collections.unmodifiableList(Arrays.asList("C05", "C06"));
    public static final List<String> SALIVARY = Collections.unmodifiableList(Arrays.asList("C07", "C08"));
    public static final List<String> OROPHARYNX = Collections.unmodifiableList(Arrays.asList("C09", "C10"));
    public static final List<String> HYPOPHARYNX = Collections.unmodifiableList(Arrays.asList("C12", "C13"));
    public static final List<String> BILIARY = Collections.unmodifiableList(Arrays.asList("C23", "C24"));
    public static final List<String> SINUS = Collections.unmodifiableList(Arrays.asList("C30", "C31"));
    public static final List<String> LUNG = Collections.unmodifiableList(Arrays.asList("C33", "C34"));
    
    public static final String MEDIASTINUM = "C370-C383,C388";
    public static final String FEMALE_GENITAL = "C510-C529,C577-C579";
    public static final String OVARY_OR_FEMALE_GENITAL = "C569-C574";
    
    public static final List<String> MALE_GENITAL = Collections.unmodifiableList(Arrays.asList("C60", "C63"));
    public static final List<String> KIDNEY_OR_URINARY = Collections.unmodifiableList(Arrays.asList("C64", "C65", "C66", "C68"));
    public static final List<String> ENDOCRINE = Collections.unmodifiableList(Arrays.asList("C74", "C75"));
    public static final List<String> UPPER_LIP =Collections.unmodifiableList( Arrays.asList("C000", "C003"));
    public static final List<String> LOWER_LIP = Collections.unmodifiableList(Arrays.asList("C001", "C004"));
    public static final List<String> UPPER_GUM = Collections.unmodifiableList(Collections.singletonList("C030"));
    public static final List<String> LOWER_GUM = Collections.unmodifiableList(Collections.singletonList("C031"));
    public static final List<String> NASAL_CAVITY = Collections.unmodifiableList(Collections.singletonList("C300"));
    public static final List<String> MIDDLE_EAR = Collections.unmodifiableList(Collections.singletonList("C301"));
    public static final String ALL_PAIRED_SITES =
            "C079,C080-C081,C090-C099,C300-C301,C310,C312,C340-C349,C384,C400-C403,C413-C414,C441-C443,C445-C447,C471-C472,C491-C492,C500-C509,C569,C570,C620-C629,C630-C631,C649,C659,C669,C690-C699,C700,C710-C714,C722-C725,C740-C749,C754";

    //Behavior
    public static final String BENIGN = "0";
    public static final String UNCERTAIN = "1";
    public static final String INSITU = "2";
    public static final String MALIGNANT = "3";

    //Treatment Status
    public static final String TREATMENT_GIVEN = "1";

    //Laterality
    public static final String RIGHT = "1";
    public static final String LEFT = "2";
    public static final String BOTH = "4";
    public static final String MID_LINE = "5";

    //Histologies
    public static final List<String> CARCINOMA_NOS = GroupUtility.expandList("8010");
    public static final List<String> CARCINOMA_SPECIFIC = GroupUtility.expandList(
            "8000-8005,8011-8015,8020-8022,8030-8035,8041-8046,8050-8052,8070-8078,8080-8084,8090-8098,8102,8110,8120-8124,8130-8131,8140-8148,8150-8157,8160-8162,8170-8175,"
                    + "8180,8190,8200-8201,8210-8211,8214-8215,8220-8221,8230-8231,8240-8247,8249,8250-8256,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8319,8320-8323,"
                    + "8330-8333,8335,8337,8340-8347,8350,8370,8380-8384,8390,8400-8403,8407-8410,8413,8420,8430,8440-8442,8450-8453,8460-8462,"
                    + "8470-8473,8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,8540-8543,8550-8551,8560-8562,8570-8576,8580-8586,8588-8589,9070,9100,9110");
    public static final List<String> NON_SMALL_CELL_CARCINOMA = GroupUtility.expandList("8046");
    public static final List<String> SPECIFIC_NON_SMALL_CELL_CARCINOMA = GroupUtility.expandList(
            "8012-8014,8022,8031-8033,8052,8070-8073,8082-8084,8123,8140,8200,8230,8250-8255,8260,8310,8333,8430,8470,8480-8481,8490,8550,8560,8972,8980");
    public static final List<String> SMALL_CELL_CARCINOMA = GroupUtility.expandList("8041-8045");
    public static final String INFLAMMATORY_CARCINOMA = "8530";
    public static final List<String> ADENOCARCINOMA_NOS = GroupUtility.expandList("8140");
    public static final List<String> ADENOCARCINOMA_SPECIFIC = GroupUtility.expandList("8000-8005,8010-8011,8020-8022,8046,8141-8148,8154,8160-8162,8190,"
            + "8200-8201,8210-8211,8214-8215,8220-8221,8230-8231,8244-8245,8250-8255,8260-8263,8270-8272,8280-8281,8290,8300,8310,8312-8320,8322-8323,8330-8333,8335,8337,8350,"
            + "8370,8380-8384,8390,8400-8403,8407-8409,8410,8413,8420,8440-8442,8450-8453,8460-8462,8470-8473,8480-8482,8490,8500-8504,8507-8508,8510,8512-8514,8520-8525,8530,"
            + "8540-8543,8550-8551,8561-8562,8570-8576");
    public static final List<String> ADENOCARCINOMA_WITH_MIXED_SUBTYPES = GroupUtility.expandList("8255");
    public static final List<String> BRONCHIOALVEOLAR = GroupUtility.expandList("8250-8254");
    public static final List<String> MELANOMA_NOS = GroupUtility.expandList("8720");
    public static final List<String> MELANOMA_SPECIFIC = GroupUtility.expandList("8000-8005,8721-8790");
    public static final List<String> SARCOMA_NOS = GroupUtility.expandList("8800");
    public static final List<String> SARCOMA_SPECIFIC = GroupUtility.expandList("8000-8005,8801-8806,8810-8815,8830,8832-8833,8840,8842,8850-8855,8857-8858,8890-8891,"
            + "8894-8896,8900-8902,8910,8912,8920-8921,8930-8931,8933,8935-8936,8990-8991,9040-9044,9180-9243,9260-9261");
    public static final List<String> FOLLICULAR = GroupUtility.expandList("8290,8330-8332,8335,8340,8346");
    public static final List<String> PAPILLARY = GroupUtility.expandList("8050,8052,8260,8340-8343,8344,8347");
    public static final String PAPILLARY_CARCINOMA = "8050";
    public static final String KAPOSI_SARCOMA = "9140";
    public static final List<String> TRANSITIONAL_CELL_CARCINOMA = GroupUtility.expandList("8120-8124");
    public static final List<String> PAPILLARY_TRANSITIONAL_CELL_CARCINOMA = GroupUtility.expandList("8130-8131");
    public static final List<String> PAGET_DISEASE = GroupUtility.expandList("8540-8543");
    public static final List<String> INTRADUCTAL_CARCINOMA = GroupUtility.expandList("8201,8230,8401,8500-8501,8503-8504,8507");
    public static final List<String> DUCT_CARCINOMA = GroupUtility.expandList("8022,8035,8500-8503,8508");
    public static final List<String> LOBULAR_CARCINOMA = GroupUtility.expandList("8520,8522,8524");
    public static final List<String> LYMPHOMA_NOS_AND_NON_HODGKIN_LYMPHOMA = GroupUtility.expandList("9590-9591,9670-9729");
    public static final List<String> NON_HODGKIN_LYMPHOMA = GroupUtility.expandList("9591,9670-9729");
    public static final List<String> HODGKIN_LYMPHOMA = GroupUtility.expandList("9596,9650-9667");
    public static final List<String> HEMATOPOIETIC_NOS_HISTOLOGIES = GroupUtility.expandList("9591,9670,9702,9729,9760,9800,9808,9809,9811,9820,9832,9835,9860,9861,9863,9960,9964,9987");
    public static final List<String> POLYP = GroupUtility.expandList("8210-8211,8213,8220-8221,8261-8263");
    public static final List<String> FAMILLIAL_POLYPOSIS = GroupUtility.expandList("8220-8221");
    public static final List<String> FAMILIAL_ADENOMATOUS_POLYPOSIS = GroupUtility.expandList("8220");
    public static final List<String> PTLD = GroupUtility.expandList("9971"); // post-transplant lymphoproliferative disorder
    public static final List<String> BCELL = GroupUtility.expandList("9680,9684");
    public static final List<String> TCELL = GroupUtility.expandList("9702,9705,9708,9709,9716-9719,9724,9726,9729,9827,9831,9834,9837");
    public static final List<String> PLASMACYTOMA = GroupUtility.expandList("9731-9732,9734");
    public static final List<String> MAST_CELL_SARCOMA = GroupUtility.expandList("9740");
    public static final List<String> MAST_CELL_LEUKEMIA = GroupUtility.expandList("9742");
    public static final List<String> MYELOID_SARCOMA = GroupUtility.expandList("9930");
    public static final List<String> MYELOID_LEUKEMIA = GroupUtility.expandList("9840,9861,9865-9867,9869-9874,9891,9895-9898,9910-9911,9931");
    public static final List<String> RETINO_BLASTOMA = GroupUtility.expandList("9510-9513");
    public static final String WILMS = "8960";
    public static final String PAPILLOMA = "9390";
    public static final String NEUROFIBROMATOSIS = "9540";
    public static final List<String> SPECIFIC_RENAL_CELL_HISTOLOGIES = GroupUtility.expandList("8260,8310,8316-8320,8510,8959");
    public static final String GLIOBLASTOMA_NOS_AND_MULTIFORME = "9440";
    public static final List<String> GLIAL_TUMOR = GroupUtility.expandList("9380-9382,9400-9401,9410-9411,9420-9421,9423-9424,9430,9440-9442");
    public static final List<String> UROTHELIAL = GroupUtility.expandList("8120,8130,8131,8082,8122,8031,8020");

    //Histology Charts
    public static final Map<String, List<String>> NOS_VS_SPECIFIC;

    static {
        Map<String, List<String>> content = new HashMap<>();
        content.put("8000", GroupUtility.expandList("8001-9999")); //Cancer/malignant neoplasm, NOS
        content.put("8010", GroupUtility.expandList("8011-8015")); //Carcinoma, NOS
        content.put("8140", GroupUtility.expandList("8141-8145,8147-8148")); //Adenocarcinoma, NOS
        content.put("8070", GroupUtility.expandList("8071-8078,8080-8084,8094,8323")); //Squamous cell carcinoma, NOS
        content.put("8720", GroupUtility.expandList("8721-8723,8726,8728,8730,8740,8741-8746,8761,8770-8774,8780")); //Melanoma, NOS
        content.put("8800", GroupUtility.expandList("8801-8806")); //Sarcoma, NOS
        content.put("8312", GroupUtility.expandList("8313-8320")); //Renal cell carcinoma, NOS
        NOS_VS_SPECIFIC = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> BENIGN_BRAIN_2004_HISTOLOGY_GROUPING;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9390", "Choroid plexus neoplasms");
        content.put("9383", "Ependymomas");
        content.put("9394", "Ependymomas");
        content.put("9444", "Ependymomas");
        content.put("9384", "Neuronal and neuronal-glial neoplasms");
        content.put("9412", "Neuronal and neuronal-glial neoplasms");
        content.put("9413", "Neuronal and neuronal-glial neoplasms");
        content.put("9442", "Neuronal and neuronal-glial neoplasms");
        content.put("9505/1", "Neuronal and neuronal-glial neoplasms");
        content.put("9506", "Neuronal and neuronal-glial neoplasms");
        content.put("9540", "Neurofibromas");
        content.put("9541", "Neurofibromas");
        content.put("9550", "Neurofibromas");
        content.put("9560/0", "Neurofibromas");
        content.put("9560/1", "Neurinomatosis ");
        content.put("9562", "Neurothekeoma");
        content.put("9570", "Neuroma");
        content.put("9571/0", "Perineurioma, NOS");
        BENIGN_BRAIN_2004_HISTOLOGY_GROUPING = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> BENIGN_BRAIN_2007_CHART1;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9383/1", "Ependymomas"); //Subependymoma
        content.put("9394/1", "Ependymomas"); //Myxopapillary Ependymoma
        content.put("9444/1", "Ependymomas"); //Choroid glioma
        content.put("9384/1", "Neuronal and neuronal-glial neoplasms"); //Subependymal giant cell astrocytoma
        content.put("9412/1", "Neuronal and neuronal-glial neoplasms"); //Desmoplastic infantile astrocytoma
        content.put("9413/0", "Neuronal and neuronal-glial neoplasms"); //Dysembryoplastic neuroepithelial tumor
        content.put("9442/1", "Neuronal and neuronal-glial neoplasms"); //Gliofibroma
        content.put("9505/1", "Neuronal and neuronal-glial neoplasms"); //Ganglioglioma
        content.put("9506/1", "Neuronal and neuronal-glial neoplasms"); //Central neurocytoma
        content.put("9540/0", "Neurofibromas"); //Neurofibroma, NOS
        content.put("9540/1", "Neurofibromas"); //Neurofibromatosis, NOS
        content.put("9541/0", "Neurofibromas"); //Melanotic neurofibroma
        content.put("9550/0", "Neurofibromas"); //Plexiform neurofibroma
        content.put("9560/0", "Neurofibromas"); //Neurilemoma, NOS
        content.put("9560/1", "Neurinomatosis"); //Neurinomatosis
        content.put("9562", "Neurothekeoma"); //Neurothekeoma
        content.put("9570", "Neuroma"); //Neuroma
        content.put("9571/0", "Perineurioma, NOS"); //Perineurioma, NOS
        BENIGN_BRAIN_2007_CHART1 = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> MALIGNANT_BRAIN_2007_CHART1;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9503", "Neuroepithelial"); //This is included in all branches
        content.put("9508", "Embryonal tumors"); //Atypical tetratoid/rhabdoid tumor
        content.put("9392", "Embryonal tumors"); //Ependymoblastoma
        content.put("9501", "Embryonal tumors"); //Medulloepithelioma
        content.put("9502", "Embryonal tumors"); //Teratoid medulloepthelioma
        content.put("9470", "Embryonal tumors"); //Medulloblastoma
        content.put("9471", "Embryonal tumors"); //Demoplastic
        content.put("9474", "Embryonal tumors"); //Large cell
        content.put("9472", "Embryonal tumors"); //Medullomyoblastoma
        content.put("9473", "Embryonal tumors"); //Supratentorial primitive neuroectodermal tumor (PNET)
        content.put("9500", "Embryonal tumors"); //Neuroblastoma
        content.put("9490", "Embryonal tumors"); //Ganglioneuroblastoma
        content.put("9391", "Ependymal tumors"); //Ependymoma, NOS
        content.put("9392", "Ependymal tumors"); //Anasplastic ependymoma
        content.put("9393", "Ependymal tumors"); //Papillary ependymoma
        content.put("9362", "Pineal tumors"); //Pineoblastoma
        content.put("9390", "Choroid plexus tumors"); //Choroid plexus carcinoma
        content.put("9505", "Neuronal and mixed neuronal-glial tumors"); //Ganglioglioma, anaplastic  // Ganglioglioma, malignant
        content.put("9522", "Neuroblastic tumors"); //Olfactory neuroblastoma
        content.put("9521", "Neuroblastic tumors"); //Olfactory neurocytoma
        content.put("9523", "Neuroblastic tumors"); //Olfactory neuroepithlioma
        content.put("9380", "Glial tumors"); //Glioma, NOS
        content.put("9430", "Glial tumors"); //Astroblastoma
        content.put("9381", "Glial tumors"); //Gliomatosis cerebri
        content.put("9423", "Glial tumors"); //Polar spongioblastoma
        content.put("9382", "Glial tumors"); //Mixed glioma
        content.put("9400", "Glial tumors"); //Astrocytoma, NOS
        content.put("9401", "Glial tumors"); //Anaplastic astrocytoma
        content.put("9420", "Glial tumors"); //Fibrillary astrocytoma
        content.put("9411", "Glial tumors"); //Gemistocytic astrocytoma
        content.put("9410", "Glial tumors"); //Protoplasmic astromytoma
        content.put("9421", "Glial tumors"); //Pilocytic astrocytoma
        content.put("9424", "Glial tumors"); //Pleomorphic xanthoastrocytoma
        content.put("9440", "Glial tumors"); //Glioblastoma, NOS and Glioblastoma multiforme
        content.put("9441", "Glial tumors"); //Giant cell glioblastoma
        content.put("9442", "Glial tumors"); //Gliosarcoma
        content.put("9450", "Oligodendroglial tumors"); //Oligodendroglioma NOS
        content.put("9451", "Oligodendroglial tumors"); //Oligodendroglioma anaplastic
        content.put("9460", "Oligodendroglial tumors"); //Oligodendroblastoma
        MALIGNANT_BRAIN_2007_CHART1 = Collections.unmodifiableMap(content);
    }

    public static final Map<String, String> MALIGNANT_BRAIN_2007_CHART2;

    static {
        Map<String, String> content = new HashMap<>();
        content.put("9540", "Periphera Nerve"); //Malignant peripheral nerve sheath tumor
        content.put("9561", "Periphera Nerve"); //Malignant peripheral nerve sheath tumor with rhabdomyoblastic differentiation (MPNST)
        content.put("9560", "Periphera Nerve"); //Neurilemoma, malignant
        content.put("9571", "Periphera Nerve"); //Perineurioma, malignant
        content.put("9100", "Germ Cell Tumors"); //Choriocarcinoma
        content.put("9070", "Germ Cell Tumors"); //Embryonal carcionoma
        content.put("9064", "Germ Cell Tumors"); //Germinoma
        content.put("9080", "Germ Cell Tumors"); //Immature teratoma
        content.put("9085", "Germ Cell Tumors"); //Mixed germ cell tumor
        content.put("9084", "Germ Cell Tumors"); //Teratoma with malignant transformation
        content.put("9071", "Germ Cell Tumors"); //Yolk sac tumor
        content.put("9539", "Meningioma, malignant"); //Meningeal sarcomatosis
        content.put("9538", "Meningioma, malignant"); //Papillary meningioma, rhadboid meningioma
        MALIGNANT_BRAIN_2007_CHART2 = Collections.unmodifiableMap(content);
    }
}
