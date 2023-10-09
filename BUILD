"""Helpful commands:

Run with:
    $ bazel run quota_regression
Build with:
    $  bazel build quota_regression_deploy.jar
"""

java_binary(
    name = "quota_regression",
    main_class = "com.google.books.demo.quotaregression.QuotaRegression",
    runtime_deps = ["//java/com/google/books/demo/quotaregression:quota_regression_lib"],
)
