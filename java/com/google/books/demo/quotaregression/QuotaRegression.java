package com.google.books.demo.quotaregression;

import com.google.books.demo.quotaregression.EnvironmentVariables.Key;
import com.google.common.collect.ImmutableMap;

/** Main code responsible for running the quota regreswion chek. */
public final class QuotaRegression {

  private QuotaRegression() {}

  public static void main(String[] args) {
    try {
      ImmutableMap<String, String> baselineToProductionApiNameMap =
          ImmutableMap.of(Key.BASELINE_API_NAME.get(), Key.PRODUCTION_API_NAME.get());
      long baselineProjectNumber = Long.valueOf(Key.BASELINE_PROJECT_NUMBER.get());
      long productionProjectNumber = Long.valueOf(Key.PRODUCTION_PROJECT_NUMBER.get());
      String metricName = Key.METRIC_NAME.get();
      QuotaAssertion quotaAssertion =
          QuotaAssertion.create(
              baselineToProductionApiNameMap,
              baselineProjectNumber,
              productionProjectNumber,
              metricName);

      quotaAssertion.assertQuota();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }
}
