@rem Gradle Wrapper launcher for Windows
@echo off
setlocal

set "APP_HOME=%~dp0"

if defined JAVA_HOME (
    set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAVA_EXE=java.exe"
)

"%JAVA_EXE%" "-Dorg.gradle.appname=gradlew" -jar "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" %*
set EXIT_CODE=%ERRORLEVEL%

endlocal & exit /b %EXIT_CODE%
