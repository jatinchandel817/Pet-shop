@ECHO OFF
SET "MAVEN_PROJECTBASEDIR=%~dp0"
SET "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"
IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Maven Wrapper JAR is missing. Please run "mvn wrapper:wrapper" with Maven installed.
  EXIT /B 1
)
"%JAVA_HOME%\bin\java.exe" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
