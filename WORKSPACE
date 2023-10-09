workspace(name = "bookservice_workspace")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "5.3"
RULES_JVM_EXTERNAL_SHA = "d31e369b854322ca5098ea12c69d7175ded971435e55c18dd9dd5f29cc5249ac"
GOOGLE_APIS_COMMON_PROTOS_VERSION = "1.50.0"
GOOGLE_PROTOBUF_VERSION = "3.20.1"
RULES_PACKAGE_VERSION = "0.7.0"
GRPC_JAVA_VERSION = "1.45.0"
GRPC_JAVA_SHA = "0a2aebd9b4980c3d555246a27d365349aa9327acf1bc3ed3b545c3cc9594f2e9"
GRPC_ZLIB_VERSION = "1.3"


SERVER_URLS = [
    "https://repo1.maven.org/maven2",
    "http://uk.maven.org/maven2",
    "https://jcenter.bintray.com/",
    "https://mvnrepository.com",
    "https://frontbackend.com/maven",
]

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG)
)

http_archive(
    name="rules_pkg",
    sha256="8a298e832762eda1830597d64fe7db58178aa84cd5926d76d5b744d6558941c2",
    urls=[
        "https://mirror.bazel.build/github.com/bazelbuild/rules_pkg/releases/download/%s/rules_pkg-%s.tar.gz" % (RULES_PACKAGE_VERSION, RULES_PACKAGE_VERSION),
        "https://github.com/bazelbuild/rules_pkg/releases/download/%s/rules_pkg-%s.tar.gz" % (RULES_PACKAGE_VERSION, RULES_PACKAGE_VERSION),
    ],
)

http_archive(
    name = "rules_python",
    sha256 = "5868e73107a8e85d8f323806e60cad7283f34b32163ea6ff1020cf27abef6036",
    strip_prefix = "rules_python-0.25.0",
    url = "https://github.com/bazelbuild/rules_python/releases/download/0.25.0/rules_python-0.25.0.tar.gz",
)
load("@rules_python//python:repositories.bzl", "py_repositories")
py_repositories()

http_archive(
    name="com_google_protobuf",
    sha256="8b28fdd45bab62d15db232ec404248901842e5340299a57765e48abe8a80d930",
    strip_prefix="protobuf-%s" % GOOGLE_PROTOBUF_VERSION,
    urls=["https://github.com/protocolbuffers/protobuf/archive/v%s.tar.gz" % GOOGLE_PROTOBUF_VERSION],
)


http_archive(
  name="com_google_googleapis",
  sha256="e0570e0990ccb30838b942420dcaf326b529c24b2227daac15bfd773770992a6",
  url="https://github.com/googleapis/api-common-protos/archive/%s.zip" % GOOGLE_APIS_COMMON_PROTOS_VERSION,
  strip_prefix="api-common-protos-%s/" % GOOGLE_APIS_COMMON_PROTOS_VERSION,
)

http_archive(
    name = "bazel_common",
    sha256 = "d8c9586b24ce4a5513d972668f94b62eb7d705b92405d4bc102131f294751f1d",
    strip_prefix = "bazel-common-413b433b91f26dbe39cdbc20f742ad6555dd1e27",
    url = "https://github.com/google/bazel-common/archive/413b433b91f26dbe39cdbc20f742ad6555dd1e27.zip",
)

http_archive(
    name = "zlib",
    build_file = "@com_google_protobuf//:third_party/zlib.BUILD",
    sha256 = "ff0ba4c292013dbc27530b3a81e1f9a813cd39de01ca5e0f8bf355702efa593e",
    strip_prefix = "zlib-%s" % GRPC_ZLIB_VERSION,
    urls = ["https://zlib.net/zlib-%s.tar.gz" % GRPC_ZLIB_VERSION ],
)

http_archive(
    name="io_grpc_grpc_java",
    sha256=GRPC_JAVA_SHA,
    strip_prefix="grpc-java-%s" % GRPC_JAVA_VERSION,
    url="https://github.com/grpc/grpc-java/archive/v%s.zip" % GRPC_JAVA_VERSION,
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")
rules_jvm_external_deps()
load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")
rules_jvm_external_setup()
load("@rules_jvm_external//:defs.bzl", "maven_install")
load("@com_google_googleapis//:repository_rules.bzl", "switched_rules_by_language")
switched_rules_by_language(
    name = "com_google_googleapis_imports",
    grpc = True,
    python = False,
    java = True,
)

_IO_GRPC_DEPS = [
  "io.grpc:grpc-api:jar:1.55.1",
  "io.grpc:grpc-netty:jar:1.57.2",
  "io.grpc:grpc-protobuf:jar:1.55.1",
  "io.grpc:grpc-stub:jar:1.55.1",
  "org.apache.tomcat:annotations-api:6.0.53",
  "org.apache.commons:commons-exec:1.3",
  "org.apache.commons:commons-configuration2:2.7",
]


maven_install(
    artifacts=[
        "com.google.api:api-common:2.10.0",
        "com.google.api-client:google-api-client:jar:2.2.0",
        "com.google.api:gapic-generator-java:2.22.0",
        "com.google.api.grpc:proto-google-cloud-service-management-v1:3.17.0",
        "com.google.api.grpc:proto-google-cloud-service-usage-v1beta1:jar:0.31.0",
        "com.google.apis:google-api-services-serviceusage:v1beta1-rev20210806-1.32.1",
        "com.google.auth:google-auth-library-oauth2-http:jar:1.17.0",
        "com.google.auto.value:auto-value:jar:1.10.1",
        "com.google.auto.value:auto-value-annotations:1.9",
        "com.google.code.gson:gson:2.10.1",
        "com.google.cloud.functions:functions-framework-api:1.0.4",
        "com.google.cloud:google-cloud-service-usage:2.24.0",
        "com.google.cloud:google-cloud-service-management:3.17.0",
        "com.google.cloud:google-cloud-storage:2.22.2",
        "com.google.flogger:flogger:0.7.4",
        "com.google.flogger:flogger-system-backend:0.7.4",
        "com.google.guava:guava:jar:31.1-jre",
        "com.google.inject:guice:5.1.0",
        "com.google.http-client:google-http-client:1.25.0",
        "com.google.protobuf:protobuf-java:3.20.1",
        "com.google.protobuf:protobuf-java-util:3.11.1",
        "com.google.protobuf:protoc:21.0-rc-1",
        "javax.inject:javax.inject:jar:1",
    ] + _IO_GRPC_DEPS,
    generate_compat_repositories = True,
    fetch_sources=True,
    repositories=SERVER_URLS,
)


load("@maven//:compat.bzl", "compat_repositories")

compat_repositories()
