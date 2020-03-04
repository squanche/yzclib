#!/bin/bash
#
# @CreationTime
#   2019/12/19 上午11:34:43
# @ModificationDate
#   2019/12/19 上午11:34:43
# @Function
#  通用脚本、函数
# @Usage
#
#
# @author Siu

. `dirname $0`/.build

### 部分状态码定义###
### 可随意使用的：100-125 131-254
# 0 程序在运行或者服务状态OK
# 1 程序已经死掉，但是 pid文件仍在 /var/run目录下存在
# 2 程序已经死掉，但是lock文件仍在 /var/lock 目录下存在
# 3 程序没有运行
# 4 程序运行状态未知
# 5-99 供LSB扩展的保留段
# 100-149 供特定系统发行版使用的保留段
# 150-199 供特定程序使用的保留段
# 200-254 保留段
#define EX_OK           0       /* successful termination */
#define EX__BASE        64      /* base value for error messages */
#define EX_USAGE        64      /* command line usage error */
#define EX_DATAERR      65      /* data format error */
#define EX_NOINPUT      66      /* cannot open input */
#define EX_NOUSER       67      /* addressee unknown */
#define EX_NOHOST       68      /* host name unknown */
#define EX_UNAVAILABLE  69      /* service unavailable */
#define EX_SOFTWARE     70      /* internal software error */
#define EX_OSERR        71      /* system error (e.g., can't fork) */
#define EX_OSFILE       72      /* critical OS file missing */
#define EX_CANTCREAT    73      /* can't create (user) output file */
#define EX_IOERR        74      /* input/output error */
#define EX_TEMPFAIL     75      /* temp failure; user is invited to retry */
#define EX_PROTOCOL     76      /* remote error in protocol */
#define EX_NOPERM       77      /* permission denied */
#define EX_CONFIG       78      /* configuration error */


# 参数

## 返回码
### 操作用户错误
EX_USER_ERR=100
### 设置环境错误
EX_ENV_ERR=101
### 服务存在错误
EX_SER_EXIST_ERR=102
### 服务主类未找到错误
EX_SER_MAIN_CLASS_NOT_FOUND_ERR=103
### 服务启动超时错误
EX_SER_START_TIMEOUT_ERR=104
### 目录无权限错误
EX_DIR_NO_PER_ERR=105

### 数据库sql运行失败
EX_SQL_EXEC_ERR=106

## 操作用户
DEFINED_EXE_USER="dc_sys"
## 可选的环境配置
ACTIVE_PROFILES_ARR=("dev" "test" "prod")
## 默认的环境配置
ACTIVE_PROFILES="prod"
## 默认的服务PID文件保存位置
SERVER_PID_FILE=../server_pid
## springboot服务启动主类
SPRING_BOOT_MAIN_CLASS=""
## 默认检测服务是否启动的超时时间
CHECK_SERVER_START_TIME_OUT=30
## 默认的JVM参数
JVM_XMS=512m
JVM_XMX=512m
## 程序部署目录
DC_APP_HOME="/app/dc_sys2"
## 前置机脚本目录
DCFE_TASK_SCRIPT_DIR="/cecdat/dc/task_script"
## 前置机数据目录
DCFE_TASK_DATA_DIR="/cecdat/dc/task_data"
## 前置机数据目录
DCFE_DATAX_DIR="/opt/datax"
## 用户环境配置
USER_CONFIG="/home/dc_sys/.dc_sys_default"

## 数据库默认配置
DEFAULT_DB_USER="dc_sys"
DEFAULT_DB_NAME="dc_sys2"
DEFAULT_DB_HOST="127.0.0.1"

## crontab配置文件
CRONTAB_CONFIG="crontab.config"
CRONTAB_BLANK="crontab_blank.config"

#SBA相关环境变量配置
#获取springbootAdmin外网地址,读取到设置变量到SBA_BASE_URL,yml里会使用该地址
HOSTS=/etc/hosts
OPEN_PORT=8890
#模块名称（默认采集服务）
MODULE_NAME=dcfe
# 福州结核病防治院
# 长乐市中医院
# 闽清精神病防治院
# 福清市皮肤病防治院
# 永泰县中医院
# 福州基卫
IP_LIST_8080_PORT="10.121.1.37,10.121.71.51,10.121.1.68,10.121.60.253,10.121.101.209,10.121.71.19,192.168.2.71"

# 公共函数

## 判断目录是否存在并有写权限
check_dir_can_w(){
if [[ ! -w $1 ]];then
   log "ERROR:目录无权限：$1,退出"
   exit ${EX_DIR_NO_PER_ERR}
fi
}

## 判断前置机目录权限
check_dcfe_dir(){
check_dir_can_w ${DCFE_TASK_SCRIPT_DIR}
check_dir_can_w ${DCFE_TASK_DATA_DIR}
check_dir_can_w ${DCFE_DATAX_DIR}
}

## 判断采集中心目录权限
check_dcc_dir(){
check_dir_can_w ${DCFE_TASK_DATA_DIR}
}


## 记录日志
log(){
   date_str=`date "+%Y-%m-%d %H:%M:%S.%3N"`
   echo ${date_str} $1
   echo ${date_str} $1 >> ../server.log
}

# 如果存在用户配置文件，则默认值读取用户配置文件
readDefault(){
    if [[ -r ${USER_CONFIG} ]]; then
          log "INFO:读取用户自定已默认配置"
        . ${USER_CONFIG}
    fi
}

## 限制指定操作用户执行脚本
### 限制指定的用户${DEFINED_EXE_USER}操作,否则退出
check_opt_user(){
if [[ `whoami` != ${DEFINED_EXE_USER} ]];then
        log "WARN:执行失败，请使用"${DEFINED_EXE_USER}"用户执行：su "${DEFINED_EXE_USER}
        log "WARN:中止退出..."
        exit ${EX_USER_ERR}
fi
}


## 设置启动环境
### 设置spring boot 服务的启动环境，默认prod（生产）
set_server_start_env(){
readDefault
log "INFO:传入启动环境： $1 "
if [[ $# -gt 0 ]];then
    if [[ ! "${ACTIVE_PROFILES_ARR[@]}" =~ "$1" ]]; then
         log "WARN:请输入正确的ACTIVE_PROFILES参数，ACTIVE_PROFILES必须为:"${active_arr[@]}
         log "WARN:中止退出..."
         exit ${EX_ENV_ERR}
    fi
    ACTIVE_PROFILES=$1
fi
log "INFO:设置启动服务环境为:"${ACTIVE_PROFILES}
}

## 判断PID文件中的进程是否存在
check_process_exist(){
if [[ -f ${SERVER_PID_FILE} ]];then
   SERVER_PID=`cat ${SERVER_PID_FILE}`
fi
if [[ -n ${SERVER_PID} ]];then
    # 判定SERVER_PID对应的进程是否仍在运行中
    ps -p ${SERVER_PID}
    if [[ $? -eq 0 ]]; then
        log "WARN:启动失败服务已存在，进程号为${SERVER_PID},请确认是否正确停止后才启动"
        exit ${EX_SER_EXIST_ERR}
    else
        rm -f ${SERVER_PID_FILE}
    fi
fi
}


## 设置服务启动主类
set_server_main_class(){
file=`jar -tvf *.jar |grep class| grep -v server | awk '{print $NF}'|sed 's/\//\./g'`
mainClass=${file%.*}
if [[ -z ${mainClass} ]];then
   log "ERROR:找不到主类"
   exit ${EX_SER_MAIN_CLASS_NOT_FOUND_ERR}
else
    SPRING_BOOT_MAIN_CLASS=${mainClass}
fi
}

## 启动服务
start_server(){
# 如果是开发/测试环境使用默认配置
if [[ ${ACTIVE_PROFILES} != "prod" ]];then
log "INFO:非生产环境使用默认JVM参数"
else
log "INFO:生产环境使用自定义JVM参数"
    if [[ $# -gt 0 ]];then
        if  [[ -n "$1" ]] ;then
            JVM_XMS=$1
        fi
        if  [[ -n "$2" ]] ;then
            JVM_XMX=$2
        fi
    fi
fi


log "INFO:-Xms参数：${JVM_XMS}"
log "INFO:-Xmx参数：${JVM_XMX}"

# 服务启动前二次校验进程情况
check_process_exist
tmp_pid=$!
echo ${tmp_pid} > ${SERVER_PID_FILE}

nohup java -cp  ./*.jar:./lib/*:./mapperServer \
 -server -Xms${JVM_XMS} -Xmx${JVM_XMX} -XX:NewRatio=1  \
 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m\
 -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled \
 -XX:MaxTenuringThreshold=15 \
 -Xloggc:/dev/shm/gc-app.log -XX:+PrintGCDateStamps -XX:+PrintGCDetails  -XX:+PrintGCCause -XX:+PrintPromotionFailure\
 ${SPRING_BOOT_MAIN_CLASS} --build.version=${build_version} --spring.profiles.active=${ACTIVE_PROFILES} >/dev/null 2>&1 &

tmp_pid=$!
log "INFO:启动${SPRING_BOOT_MAIN_CLASS} ${ACTIVE_PROFILES} ${tmp_pid}"
echo ${tmp_pid} > ${SERVER_PID_FILE}
}

## 检查进程是否启动
### 当检测到进程已启动则退出为0，如果超时未检测到有进程则返回为1（最长等待30秒）
check_server_start(){
count=0
while [[ ${count} -lt ${CHECK_SERVER_START_TIME_OUT}   ]]
do
   sleep 1
   let count+=1
   ps aux | grep ${SPRING_BOOT_MAIN_CLASS}| grep -v grep
   result=$?
   if [[ ${result} == 0 ]];then
       exit 0
   fi
done

log "WARN:服务启动超时"
exit ${EX_SER_START_TIMEOUT_ERR}
}


## 停止服务
stop_server(){
cd `dirname $0`/..

SERVER_PID_FILE=../server_pid
if [[ -f ${SERVER_PID_FILE} ]];then
   SERVER_PID=`cat ${SERVER_PID_FILE}`
fi
if [[ -n ${SERVER_PID} ]];then
   log "INFO:关闭服务 ${SERVER_PID}"
   kill  -15 ${SERVER_PID}
   rm ${SERVER_PID_FILE}
   sleep 1
else
	log "INFO:服务已停止-${SERVER_PID}"
fi
}

# 查看服务状态
status_server(){
cd `dirname $0`/..

if [[ -f ${SERVER_PID_FILE} ]];then
   SERVER_PID=`cat ${SERVER_PID_FILE}`
fi
#判定SERVER_PID是否存在，0 为存在 非0 为不存在
if [[ -n ${SERVER_PID} ]];then
   ps -up ${SERVER_PID}
else
	log "INFO:服务未启动-${SERVER_PID}"
fi
}

# 设置spring boot admin 地址
set_sba_url(){
if [[ -n $1 ]];then
	MODULE_NAME=$1
fi

if [[ -r ${HOSTS} ]];then
	PUBLIC_IP=`cat /etc/hosts | grep PUBLIC_IP|awk '{print$1}'| tr -d " "`
	#修改端口
	if [[ -n PUBLIC_IP ]]; then
		chang_port=`echo ${IP_LIST_8080_PORT} | grep ${PUBLIC_IP} | wc -l`
		if [ $chang_port -gt 0 ];then
		OPEN_PORT=8080
		fi
		export SBA_BASE_URL=http://${PUBLIC_IP}:${OPEN_PORT}/${MODULE_NAME}
	fi
else
    log "文件${HOSTS}未分配可读权限，无法获取外网地址"
fi
}


#执行sql脚本
exec_sql(){
   sql_file=`basename ${1}`
   log_file=/tmp/exec_sql_${sql_file}.log
   #运行数据库sql，如果运行返回值非0， 则sql执行失败
   psql -h ${DEFAULT_DB_HOST}  -U ${DEFAULT_DB_USER} ${DEFAULT_DB_NAME} < $1  > ${log_file} 2>&1
   if [[ $? -ne 0 ]]; then
        log "sql $1 运行失败"
        exit ${EX_SQL_EXEC_ERR}
    fi

    # sql运行成功后，还需要排查是否有部分语句异常
    cat ${log_file} | grep "ERROR:" > /dev/null
    if [[ $? -eq 0 ]]; then
        log "sql $1 运行失败"
        exit ${EX_SQL_EXEC_ERR}
    fi
}

# 添加cron服务
add_crontab(){
    systemctl status crond > /dev/null
    if [[ $? -ne 0 ]]; then
        log "WARN:crontab 服务未启动 "
        return
    fi

    crontab -l | grep startup.sh
    if [[ $? -eq 0 ]]; then
        log "crontab已添加"
        return
    fi
    crontab bin/${CRONTAB_CONFIG}
     if [[ $? -eq 0 ]]; then
        log "crontab添加成功"
    else
        log "crontab添加失败"
    fi
}


# 清楚cron服务
clear_crontab(){
    systemctl status crond > /dev/null
    if [[ $? -ne 0 ]]; then
        log "WARN:crontab 服务未启动 "
        return
    fi

    crontab bin/${CRONTAB_BLANK}
    if [[ $? -eq 0 ]]; then
        log "INFO:crontab清除成功"
    else
        log "INFO:crontab清除失败"
    fi

}

cd `dirname $0`/..
# source 环境
source /etc/profile
# 设置语言
export LANG=en_US.utf-8
# 检查操作用户
check_opt_user
