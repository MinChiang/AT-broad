@ECHO off
SETLOCAL ENABLEDELAYEDEXPANSION

SET JAVA_HOME="C:\Program Files\Java\jdk1.6"
ECHO JAVA_HOME = %JAVA_HOME%

for %%i in (../lib/*.jar) do call :addcp %%i
SET CLASSPATH=%CLASSPATH%;%JAVA_HOME%/lib;%JAVA_HOME%/jre/lib;%JAVA_HOME%/jre/lib/rt.jar;./target/classes
ECHO CLASSPATH = %CLASSPATH%

SET DEBUG_PORT=11199
SET JAVA_DEBUG=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=%DEBUG_PORT%,server=y,suspend=n
::SET JAVA_DEBUG=-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=127.0.0.1:%DEBUG_PORT%,server=n,suspend=n

SET VM_OPTION=-Xmx256m -Xms64m -XX:PermSize=64m

SET APP_OPTION=-Duser.dir=../

ECHO STARTUP_CMD=%JAVA_HOME%\java %VM_OPTION% %JAVA_DEBUG% %APP_OPTION% com.app.ServerStartup
%JAVA_HOME%\bin\java %VM_OPTION% %JAVA_DEBUG% %APP_OPTION% com.app.ServerStartup
PAUSE

:addcp
SET CLASSPATH=%CLASSPATH%;/lib/%1
goto :eof