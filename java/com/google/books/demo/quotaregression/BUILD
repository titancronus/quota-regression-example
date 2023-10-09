load("@rules_java//java:defs.bzl", "java_library")
load("@rules_jvm_external//:defs.bzl", "artifact")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "environment_variables",
    srcs = ["EnvironmentVariables.java"],
    deps = [
    ],
)

java_library(
    name = "quota_regression_lib",
    srcs = ["QuotaRegression.java"],
    deps = [
        ":environment_variables",
        ":quota_assertion",
        artifact("com.google.guava:guava"),
    ],
)

java_library(
    name = "quota_assertion",
    srcs = ["QuotaAssertion.java"],
    deps = [
        artifact("com.google.api.grpc:proto-google-cloud-service-usage-v1beta1"),
        artifact("com.google.cloud:google-cloud-service-usage"),
        artifact("com.google.flogger:flogger"),
        artifact("com.google.flogger:flogger-system-backend"),
        artifact("com.google.guava:guava"),
        artifact("com.google.inject:guice"),
        artifact("javax.inject:javax.inject"),
    ],
)
