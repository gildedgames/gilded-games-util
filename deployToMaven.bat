@echo off
title Maven Deployment

call gradlew clean pP publish
pause