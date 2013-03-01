#!/bin/bash

jar="target/suggestion-service-0.1-SNAPSHOT.jar"
main="com.twitter.common.application.AppLauncher"

jvm_options=(
  -Xmx384m
  -Xms256m
  -XX:+UseConcMarkSweepGC
  -XX:+UseParNewGC
  -XX:ParallelGCThreads=2
  -cp $jar
)

app_options=(
  -app_class=com.airbnb.suggest.SuggestionServiceMain
  -http_port=8080
  -use_glog=true
  -use_glog_formatter=true
)

java "${jvm_options[@]}" "$main" "${app_options[@]}"
