=========================================================
1.安装protobuf插件：
setting--》plugins--》输入protobuf--》安装protobuf support

2.打开cmd命令窗口                                           <br/>
    //生成java存放路径(src/main/java) , 原本文件（src/protobuf/Student.proto）                                  <br/>    
命令为：D:\idea-project-work\larry-netty>
protoc --java_out=src/main/java src/protobuf/Student.proto                 <br/>

===========================================================
实例地址为：https://www.cnblogs.com/liugh/p/7505533.html

一、.proto文件语法高亮显示

需要安装Protobuf Support插件

依次点击Intellij中的“File”-->"Settings"-->"Plugins"-->"Browse repositories"，如下所示：



输入Protobuf，如下所示



安装完后，重启Intellij IDEA，查看.proto文件，会发现已经支持语法高亮显示。

 

二、将.proto文件转成Java类
一般的做法，是执行protoc命令，依次将.proto文件转成Java类:

protoc.exe -I=d:/tmp --java_out=d:/tmp d:/tmp/monitor_data.proto
 

  不过gRPC官方推荐了一种更优雅的使用姿势，可以通过maven轻松搞定

 2.1 pom.xml文件配置

<properties>

<grpc.version>1.6.1</grpc.version>
<protobuf.version>3.3.0</protobuf.version>
</properties>
 

复制代码
  <dependencies>
         <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-netty</artifactId>
            <version>${grpc.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-protobuf</artifactId>
            <version>${grpc.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>io.grpc</groupId>
            <artifactId>grpc-stub</artifactId>
            <version>${grpc.version}</version>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>com.google.protobuf</groupId>
            <artifactId>protobuf-java</artifactId>
            <version>${protobuf.version}</version>
        </dependency>
</dependencies>
复制代码

复制代码
<build>
        <extensions>
            <extension>
                <groupId>kr.motd.maven</groupId>
                <artifactId>os-maven-plugin</artifactId>
                <version>1.5.0.Final</version>
            </extension>
        </extensions>
        <plugins>
            <plugin>
                <groupId>org.xolstice.maven.plugins</groupId>
                <artifactId>protobuf-maven-plugin</artifactId>
                <version>0.5.0</version>
                <configuration>
                    <protocArtifact>com.google.protobuf:protoc:${protobuf.version}:exe:${os.detected.classifier}</protocArtifact>
                    <pluginId>grpc-java</pluginId>
                    <pluginArtifact>io.grpc:protoc-gen-grpc-java:${grpc.version}:exe:${os.detected.classifier}</pluginArtifact>
                </configuration>
                <executions>
                    <execution>
                        <goals>
                            <goal>compile</goal>
                            <goal>compile-custom</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>            
        </plugins>
    </build>
复制代码
 2.2 编译生成Java类

  使用maven的编译命令，即可在target中看到根据.proto文件生成的Java类，如下所示：

三、遇到的坑

1.打开.proto文件后，显示“File not found”提示，如下所示：

 这种情况，一般是未设置.proto文件所在文件夹为源文件，可以进行如下设置：

 在.proto文件所在的文件夹上右键，设置目录为源文件根目录，如下所示：


参考文档：

https://github.com/google/protobuf

https://github.com/protostuff/protobuf-jetbrains-plugin

https://github.com/grpc/grpc-java
