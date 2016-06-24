/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.mph.mpgroups;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.imsweb.mph.MphConstants;
import com.imsweb.mph.MphGroup;
import com.imsweb.mph.MphInput;
import com.imsweb.mph.MphRule;
import com.imsweb.mph.MphRuleResult;
import com.imsweb.mph.MphUtils;

public class Mp1998HematopoieticGroup extends MphGroup {

    private static List<String[]> _1998_HEMATOPOIETIC = new ArrayList<>();

    public Mp1998HematopoieticGroup() {
        super(MphConstants.MP_1998_HEMATO_GROUP_ID, MphConstants.MP_1998_HEMATO_GROUP_NAME, "C000-C809", null, "9590-9989", null, "2-3,6", "0000-2000");

        MphRule rule = new MphRule(MphConstants.MP_1998_HEMATO_GROUP_ID, "M1", MphUtils.MPResult.SINGLE_PRIMARY) {
            @Override
            public MphRuleResult apply(MphInput i1, MphInput i2) {
                initializeLookup();
                this.setResult(MphUtils.MPResult.SINGLE_PRIMARY);
                MphRuleResult result = new MphRuleResult();
                result.setResult(MphUtils.RuleResult.TRUE);
                int laterDx = GroupUtility.compareDxDate(i1, i2);
                if (laterDx == -1) {
                    result.setResult(MphUtils.RuleResult.UNKNOWN);
                    result.setMessage("Unable to apply Rule " + this.getStep() + " of " + this.getGroupId() + ". Valid and known diagnosis date should be provided.");
                }
                String firstDx = laterDx == 1 ? i2.getHistology() : i1.getHistology(), secondDx = laterDx == 1 ? i1.getHistology() : i2.getHistology();
                for (String[] row : _1998_HEMATOPOIETIC)
                    if ((firstDx.compareTo(row[0]) >= 0 && firstDx.compareTo(row[1]) <= 0 && secondDx.compareTo(row[2]) >= 0 && secondDx.compareTo(row[3]) <= 0) ||
                            (laterDx == 0 && (secondDx.compareTo(row[0]) >= 0 && secondDx.compareTo(row[1]) <= 0 && firstDx.compareTo(row[2]) >= 0 && firstDx.compareTo(row[3]) <= 0)))
                        return result;

                //if they don't match
                this.setResult(MphUtils.MPResult.MULTIPLE_PRIMARIES);
                return result;
            }
        };
        _rules.add(rule);
    }

    private static synchronized void initializeLookup() {
        if (_1998_HEMATOPOIETIC.isEmpty()) {
            try {
                Reader reader = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("Hematopoietic1998HistologyPairs.csv"), "US-ASCII");
                _1998_HEMATOPOIETIC.addAll(new CSVReader(reader, ',', '\"', 1).readAll());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
