/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.util.Arrays;
import java.util.List;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphUtils;
import com.imsweb.mph.internal.TempRuleResult;

public class Mp2007HeadAndNeckGroup extends MphGroup {

    public Mp2007HeadAndNeckGroup() {
        super(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, MphConstants.MP_2007_HEAD_AND_NECK_GROUP_NAME, "C000-C148, C300-C329", null, null, "9590-9989, 9140", "2-3,6", "2007-9999");

        // M3 - Tumors on the right side and the left side of a paired site are multiple primaries.  
        MphRule rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M3") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                List<String> pairedSites = Arrays.asList("C079", "C080,C081", "C090,C091,C098,C099", "C300", "C310,C312", "C301");
                if (GroupUtility.isPairedSites(i1.getPrimarySite(), i2.getPrimarySite(), pairedSites)) {
                    if (!GroupUtility.validLaterality(i1.getLaterality(), i2.getLaterality())) {
                        result.setResult(MphUtils.MpResult.QUESTIONABLE);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known laterality for paired sites of head and neck should be provided.");
                    }
                    else if (GroupUtility.areOppositeSides(i1.getLaterality(), i2.getLaterality()))
                        result.setResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
                return result;
            }
        };
        rule.setQuestion("Are there tumors in both the left and right sides of a paired site?");
        rule.setReason("Tumors on the right side and the left side of a paired site are multiple primaries.");
        _rules.add(rule);

        //M4- Tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M4") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_LIP, MphConstants.LOWER_LIP))
                    result.setResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004)?");
        rule.setReason("Tumors on the upper lip (C000 or C003) and the lower lip (C001 or C004) are multiple primaries.");
        _rules.add(rule);

        //M5- Tumors on the upper gum (C030) and the lower gum (C031) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M5") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.UPPER_GUM, MphConstants.LOWER_GUM))
                    result.setResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors on the upper gum (C030) and the lower gum (C031)?");
        rule.setReason("Tumors on the upper gum (C030) and the lower gum (C031) are multiple primaries.");
        _rules.add(rule);

        //M6- Tumors in the nasal cavity (C300) and the middle ear (C301) are multiple primaries.
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M6") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                if (GroupUtility.differentCategory(i1.getPrimarySite(), i2.getPrimarySite(), MphConstants.NASAL_CAVITY, MphConstants.MIDDLE_EAR))
                    result.setResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        rule.setQuestion("Are there tumors in the nasal cavity (C300) and the middle ear (C301)?");
        rule.setReason("Tumors in the nasal cavity (C300) and the middle ear (C301) are multiple primaries.");
        _rules.add(rule);

        //M7- Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.
        rule = new MphRulePrimarySiteCode(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M7");
        _rules.add(rule);

        //M8- An invasive tumor following an insitu tumor more than 60 days after diagnosis are multiple primaries.
        rule = new MphRuleBehavior(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M8");
        _rules.add(rule);

        //M9- Tumors diagnosed more than five (5) years apart are multiple primaries.
        rule = new MphRuleDiagnosisDate(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M9");
        _rules.add(rule);

        //M10 - 
        rule = new MphRule(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M10") {
            @Override
            public TempRuleResult apply(MphInput i1, MphInput i2) {
                TempRuleResult result = new TempRuleResult();
                String hist1 = i1.getHistology(), hist2 = i2.getHistology();
                List<String> nosList = Arrays.asList("8000", "8010", "8140", "8070", "8720", "8800");
                if ((nosList.contains(hist1) && MphConstants.NOS_VS_SPECIFIC.containsKey(hist1) && MphConstants.NOS_VS_SPECIFIC.get(hist1).contains(hist2)) || (nosList.contains(hist2)
                        && MphConstants.NOS_VS_SPECIFIC.containsKey(hist2) && MphConstants.NOS_VS_SPECIFIC.get(hist2).contains(hist1)))
                    result.setResult(MphUtils.MpResult.SINGLE_PRIMARY);

                return result;
            }
        };
        rule.setQuestion("Is there cancer/malignant neoplasm, NOS (8000) and another is a specific histology? or\n" +
                "Is there carcinoma, NOS (8010) and another is a specific carcinoma? or\n" +
                "Is there adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma? or\n" +
                "Is there squamous cell carcinoma, NOS (8070) and another is a specific squamous cell carcinoma? or\n" +
                "Is there melanoma, NOS (8720) and another is a specific melanoma? or\n" +
                "Is there sarcoma, NOS (8800) and another is a specific sarcoma?");
        rule.setReason("Abstract as a single primary* when one tumor is:\n" +
                "- Cancer/malignant neoplasm, NOS (8000) and another is a specific histology or\n" +
                "- Carcinoma, NOS (8010) and another is a specific carcinoma or\n" +
                "- Adenocarcinoma, NOS (8140) and another is a specific adenocarcinoma or\n" +
                "- Squamous cell carcinoma, NOS (8070) and another is specific squamous cell carcinoma or\n" +
                "- Melanoma, NOS (8720) and another is a specific melanoma or\n" +
                "- Sarcoma, NOS (8800) and another is a specific sarcoma");
        _rules.add(rule);

        //M11- Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.        
        rule = new MphRuleHistologyCode(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M11");
        _rules.add(rule);

        //M12- Tumors that do not meet any of the criteria are abstracted as a single primary.
        rule = new MphRuleNoCriteriaSatisfied(MphConstants.MP_2007_HEAD_AND_NECK_GROUP_ID, "M12");
        rule.getNotes().add("When an invasive tumor follows an in situ tumor within 60 days, abstract as a single primary.");
        rule.getNotes().add("All cases covered by Rule M12 have the same first 3 numbers in ICD-O-3 histology code.");
        rule.getExamples().add("Multifocal tumors in floor of mouth.");
        rule.getExamples().add("An in situ and invasive tumor diagnosed within60 days.");
        rule.getExamples().add("In situ following an invasive tumor more than 60 days apart.");
        _rules.add(rule);
    }
}
