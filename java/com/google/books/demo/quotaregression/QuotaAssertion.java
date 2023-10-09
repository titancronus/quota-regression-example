package com.google.books.demo.quotaregression;

import static com.google.common.base.Preconditions.checkState;

import com.google.api.serviceusage.v1beta1.ConsumerQuotaMetric;
import com.google.api.serviceusage.v1beta1.ListConsumerQuotaMetricsRequest;
import com.google.api.serviceusage.v1beta1.QuotaBucket;
import com.google.api.serviceusage.v1beta1.ServiceUsageClient;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.flogger.FluentLogger;
import java.io.IOException;
import java.util.Map.Entry;

/**
 * Helper class to perform assertions. Key documentation:
 * https://cloud.google.com/java/docs/reference/google-cloud-service-usage/latest/com.google.api.serviceusage.v1beta1.ServiceUsageClient#com_google_api_serviceusage_v1beta1_ServiceUsageClient_listConsumerQuotaMetrics_com_google_api_serviceusage_v1beta1_ListConsumerQuotaMetricsRequest_
 */
public final class QuotaAssertion {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  ImmutableMap<String, String> baselineToProductionApiNameMap;
  private long baselineProjectNumber;
  private long productionProjectNumber;
  private String metricName;

  QuotaAssertion(
      ImmutableMap<String, String> baselineToProductionApiNameMap,
      long baselineProjectNumber,
      long productionProjectNumber,
      String metricName) {
    this.baselineToProductionApiNameMap = baselineToProductionApiNameMap;
    this.baselineProjectNumber = baselineProjectNumber;
    this.productionProjectNumber = productionProjectNumber;
    this.metricName = metricName;
  }

  public static QuotaAssertion create(
      ImmutableMap<String, String> baselineToProductionApiNameMap,
      long baselineProjectNumber,
      long productionProjectNumber,
      String metricName) {
    return new QuotaAssertion(
        baselineToProductionApiNameMap, baselineProjectNumber, productionProjectNumber, metricName);
  }

  /**
   * Method verifies that the Production Project supplied via the {@code productionProjectNumber}
   * has at least the same quota as the Baseline Project supplied by the {@code
   * baselineProjectNumber}.
   */
  public void assertQuota() throws Exception {
    for (Entry<String, String> entry : baselineToProductionApiNameMap.entrySet()) {
      String baselineApiName = entry.getKey();
      String productionApiName = entry.getValue();
      ListConsumerQuotaMetricsRequest baselineQuotaMetricsRequest =
          ListConsumerQuotaMetricsRequest.newBuilder()
              .setParent(
                  String.format(
                      "projects/%s/services/%s",
                      String.valueOf(baselineProjectNumber), baselineApiName))
              .build();
      ListConsumerQuotaMetricsRequest productionQuotaMetricsRequest =
          ListConsumerQuotaMetricsRequest.newBuilder()
              .setParent(
                  String.format(
                      "projects/%s/services/%s",
                      String.valueOf(productionProjectNumber), productionApiName))
              .build();

      long baselineEffectiveLimit = getEffectiveLimit(getQuotaMetrics(baselineQuotaMetricsRequest));
      long productionEffectiveLimit =
          getEffectiveLimit(getQuotaMetrics(productionQuotaMetricsRequest));
      checkState(
          baselineEffectiveLimit >= productionEffectiveLimit,
          "Production environment not provisioned with enough quota! "
              + "Baseline is [%s] and production is [%s]",
          baselineEffectiveLimit,
          productionEffectiveLimit);
    }
  }

  private long getEffectiveLimit(ImmutableList<ConsumerQuotaMetric> quotaMetrics) {
    logger.atInfo().log("Extracting effective limit from: %s", quotaMetrics);
    return quotaMetrics.stream()
        .flatMap(consumerQuotaMetric -> consumerQuotaMetric.getConsumerQuotaLimitsList().stream())
        .filter(quotaLimit -> quotaLimit.getMetric().equals(metricName))
        .flatMap(consumerQuotaLimit -> consumerQuotaLimit.getQuotaBucketsList().stream())
        .findFirst()
        .map(QuotaBucket::getEffectiveLimit)
        .orElse(0L);
  }

  /** Return the list of metrics defined based on the given {@code request}. */
  private ImmutableList<ConsumerQuotaMetric> getQuotaMetrics(
      ListConsumerQuotaMetricsRequest request) throws IOException {
    try (ServiceUsageClient serviceUsageClient = ServiceUsageClient.create()) {
      return ImmutableList.copyOf(
          serviceUsageClient.listConsumerQuotaMetrics(request).iterateAll());
    }
  }
}
