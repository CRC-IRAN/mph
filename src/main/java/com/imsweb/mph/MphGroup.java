/*
 * Copyright (C) 2013 Information Management Services, Inc.
 */
package com.imsweb.mph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Range;

import com.imsweb.mph.internal.TempRuleResult;
import com.imsweb.mph.mpgroups.GroupUtility;

public abstract class MphGroup {

    protected String _id;

    protected String _name;

    protected String _siteInclusions;

    protected String _siteExclusions;

    protected String _histInclusions;

    protected String _histExclusions;

    protected String _behavInclusions;

    protected String _yearInclusions;

    protected List<MphRule> _rules;

    private List<Range<Integer>> _siteIncRanges;

    private List<Range<Integer>> _siteExcRanges;

    private List<Range<Integer>> _histIncRanges;

    private List<Range<Integer>> _histExcRanges;

    private List<Range<Integer>> _behavIncRanges;

    private List<Range<Integer>> _yearIncRanges;

    public MphGroup(String id, String name, String siteInclusions, String siteExclusions, String histInclusions, String histExclusions, String behavInclusions, String yearInclusions) {
        _id = id;
        _name = name;
        _siteInclusions = siteInclusions;
        _siteExclusions = siteExclusions;
        _histInclusions = histInclusions;
        _histExclusions = histExclusions;
        _behavInclusions = behavInclusions;
        _yearInclusions = yearInclusions;
        _rules = new ArrayList<>();

        // compute the raw inclusions/exclusions into ranges
        _siteIncRanges = GroupUtility.computeRange(siteInclusions, true);
        _siteExcRanges = GroupUtility.computeRange(siteExclusions, true);
        _histIncRanges = GroupUtility.computeRange(histInclusions, false);
        _histExcRanges = GroupUtility.computeRange(histExclusions, false);
        _behavIncRanges = GroupUtility.computeRange(behavInclusions, false);
        _yearIncRanges = GroupUtility.computeRange(yearInclusions, false);
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getSiteInclusions() {
        return _siteInclusions;
    }

    public String getSiteExclusions() {
        return _siteExclusions;
    }

    public String getHistInclusions() {
        return _histInclusions;
    }

    public String getHistExclusions() {
        return _histExclusions;
    }

    public String getBehavInclusions() {
        return _behavInclusions;
    }

    public String getYearInclusions() {
        return _yearInclusions;
    }

    public List<MphRule> getRules() {
        return _rules;
    }

    public boolean isApplicable(String primarySite, String histology, String behavior, int year) {
        if (!GroupUtility.validateProperties(primarySite, histology, behavior, year))
            return false;

        //Check behavior and diagnosis year
        if (!GroupUtility.isContained(_behavIncRanges, Integer.parseInt(behavior)) || !GroupUtility.isContained(_yearIncRanges, year))
            return false;

        boolean siteOk, histOk = false;

        Integer site = Integer.parseInt(primarySite.substring(1)), hist = Integer.parseInt(histology);

        // check site
        if (_siteIncRanges != null)
            siteOk = GroupUtility.isContained(_siteIncRanges, site);
        else
            siteOk = _siteExcRanges == null || !GroupUtility.isContained(_siteExcRanges, site);

        // check histology (only if site matched)
        if (siteOk) {
            if (_histIncRanges != null)
                histOk = GroupUtility.isContained(_histIncRanges, hist);
            else
                histOk = _histExcRanges == null || !GroupUtility.isContained(_histExcRanges, hist);
        }

        return siteOk && histOk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MphGroup mphGroup = (MphGroup)o;

        return _id.equals(mphGroup._id);

    }

    @Override
    public int hashCode() {
        return _id.hashCode();
    }

    public static class MphRuleHistologyCode extends MphRule {

        public MphRuleHistologyCode(String groupId, String step) {
            super(groupId, step);
            setQuestion("Do the tumors have ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number?");
            setReason("Tumors with ICD-O-3 histology codes that are different at the first (?xxx), second (x?xx) or third (xx?x) number are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            String hist1 = i1.getHistology(), hist2 = i2.getHistology();
            //If lenient mode is on 8000 is considered as same histology as 8nnn histologies
            if (MphComputeOptions.MpHistologyMatching.LENIENT.equals(options.getHistologyMatchingMode()) && (("8000".equals(hist1) && hist2.startsWith("8")) || ("8000".equals(hist2) && hist1
                    .startsWith("8"))))
                return result;
            if (!hist1.substring(0, 3).equals(hist2.substring(0, 3)))
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            return result;
        }

    }

    public static class MphRulePrimarySiteCode extends MphRule {

        public MphRulePrimarySiteCode(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third character (Cx?x)?");
            setReason("Tumors in sites with ICD-O-3 topography codes that are different at the second (C?xx) and/or third (Cx?x) character are multiple primaries.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            if (!i1.getPrimarySite().substring(1, 3).equals(i2.getPrimarySite().substring(1, 3)))
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
            return result;
        }
    }

    public static class MphRuleBehavior extends MphRule {

        public MphRuleBehavior(String groupId, String step) {
            super(groupId, step);
            setQuestion("Is there an invasive tumor following an in situ tumor more than 60 days after diagnosis?");
            setReason("An invasive tumor following an in situ tumor more than 60 days after diagnosis are multiple primaries.");
            getNotes().add("The purpose of this rule is to ensure that the case is counted as an incident (invasive) case when incidence data are analyzed.");
            getNotes().add("Abstract as multiple primaries even if the medical record/physician states it is recurrence or progression of disease.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            String beh1 = i1.getBehavior(), beh2 = i2.getBehavior();
            if (GroupUtility.differentCategory(beh1, beh2, Collections.singletonList(MphConstants.INSITU), Collections.singletonList(MphConstants.MALIGNANT))) {
                int latestDx = GroupUtility.compareDxDate(i1, i2);
                //If they are diagnosed at same date or invasive is not following insitu
                if (0 == latestDx || (1 == latestDx && !"3".equals(beh1)) || (2 == latestDx && !"3".equals(beh2)))
                    return result;
                else {
                    int sixtyDaysApart = GroupUtility.verifyDaysApart(i1, i2, 60);
                    if (-1 == sixtyDaysApart) {
                        result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                        result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
                    }
                    else if (1 == sixtyDaysApart)
                        result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                }
            }
            return result;
        }
    }

    public static class MphRuleDiagnosisDate extends MphRule {

        public MphRuleDiagnosisDate(String groupId, String step) {
            super(groupId, step);
            setQuestion("Are there tumors diagnosed more than five (5) years apart?");
            setReason("Tumors diagnosed more than five (5) years apart are multiple primaries.");

        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            int diff = GroupUtility.verifyYearsApart(i1, i2, 5);
            if (-1 == diff) {
                result.setPotentialResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);
                result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". There is no enough diagnosis date information.");
            }
            else if (1 == diff)
                result.setFinalResult(MphUtils.MpResult.MULTIPLE_PRIMARIES);

            return result;
        }
    }

    public static class MphRuleNoCriteriaSatisfied extends MphRule {

        public MphRuleNoCriteriaSatisfied(String groupId, String step) {
            super(groupId, step);
            setQuestion("Does not meet any of the criteria?");
            setReason("Tumors that do not meet any of the criteria are abstracted as a single primary.");
        }

        @Override
        public TempRuleResult apply(MphInput i1, MphInput i2, MphComputeOptions options) {
            TempRuleResult result = new TempRuleResult();
            result.setFinalResult(MphUtils.MpResult.SINGLE_PRIMARY);
            return result;
        }
    }
}
