@echo off
title Development Workspace Setup

:: Call Gradle Setup
call gradlew setupDecompWorkspace eclipse
pause