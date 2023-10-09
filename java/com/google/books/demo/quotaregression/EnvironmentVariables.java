package com.google.books.demo.quotaregression;

/** Helper class for fetching the environment variables that have been set. */
final class EnvironmentVariables {

  private EnvironmentVariables() {}

  public enum Key {
    BASELINE_API_NAME,
    PRODUCTION_API_NAME,
    BASELINE_PROJECT_NUMBER,
    PRODUCTION_PROJECT_NUMBER,
    METRIC_NAME;

    public String get() {
      return System.getenv(this.name());
    }
  }
}
