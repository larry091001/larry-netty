gradlew ==> gradle wrapper (gradle 包装器)

D:\idea-project-work\larry-netty>gradle wrapper 
生成gradlew和gradlew.bat文件
同时也生成gradle/wrapper/gradle-wrapper.jsr
和gradle/wrapper/gradle-wrapper.properties

D:\idea-project-work\larry-netty>gradle wrapper --gradlew-version 3.5 指定版本
或者
build.gradle 添加
task wrapper(type: wrapper){
    gradleVersion = '3.5'
    distributionType = 'all'
}
执行指令：D:\idea-project-work\larry-netty>gradle wrapper

source -/.zshrc 还原为原先的gradle版本（或者重启idea）
查询版本：gradle -version

命令
./gradlew clean build 


