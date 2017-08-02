#JAVA_HOME=/app/java/jdk1.6.0_45
echo JAVA_HOME=${JAVA_HOME}
export JAVA_HOME

DEPLOY_PATH=$(cd "$(dirname "$0")/..";pwd)
echo DEPLOY_PATH=${DEPLOY_PATH}
export DEPLOY_PATH

JAVA_VM="-Xmx256m -Xms64m -XX:PermSize=64m"
export JAVA_VM

CLASSPATH=${JAVA_HOME}/lib:${JAVA_HOME}/jre/lib:${JAVA_HOME}/jre/lib/rt.jar:./target/classes
for file in ${DEPLOY_PATH}/lib/*
do
    CLASSPATH=${CLASSPATH}:${file}
done
echo CLASSPATH=${CLASSPATH}
export CLASSPATH

DEBUG_PORT=55524
export DEBUG_PORT

JAVA_DEBUG="-Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=${DEBUG_PORT},server=y,suspend=n"
echo JAVA_DEBUG=${JAVA_DEBUG}
export JAVA_DEBUG

APP_OPTION=-Duser.dir=../
export APP_OPTION

>serverStartup.log
nohup ${JAVA_HOME}/bin/java ${JAVA_VM} ${JAVA_DEBUG} ${APP_OPTION} com.app.ServerStartup > serverStartup.log &
tail -f serverStartup.log
