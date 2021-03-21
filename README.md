# maven
maven 官网学习demo

# pom详解
POM是Maven中的基本工作单元

## 1.pom继承
父子groupid和version一致
```
|-我的模块
| `-pom.xml` 
-pom.xml
```
```
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
     <groupId>com.mycompany.app</groupId>
     <artifactId>my-app</artifactId>
     <version>1</version>
    </parent>
    
    <artifactId>my-module</artifactId>
</project>
```

如果尚未安装父项，并且目录结构如以下示例所示
```
|-- my-module
|   `-- pom.xml
`-- parent
  `-- pom.xml
```
```
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
     <groupId>com.mycompany.app</groupId>
     <artifactId>my-app</artifactId>
     <version>1</version>
     <relativePath>../parent/pom.xml</relativePath>
    </parent>
      
    <artifactId>my-module</artifactId>
</project> 
```

## 2. 项目聚合
```
|-- my-module
|   `-- pom.xml
`-- pom.xml
```
```
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.mycompany.app</groupId>
    <artifactId>my-app</artifactId>
    <version>1</version>
    <packaging>pom</packaging>
    
    <modules>
      <module>my-module</module>
    </modules>
</project>
```
```
|-- my-module
|   `-- pom.xml
`-- parent
   `-- pom.xml
```
```
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>com.mycompany.app</groupId>
    <artifactId>my-app</artifactId>
    <version>1</version>
    <packaging>pom</packaging>
    
    <modules>
      <module>../my-module</module>
    </modules>
</project>
```

## 3.pom变量
- Project Model Variables
```
${project.groupId}，${project.version}，${project.build.sourceDirectory} 等
```

- 特殊变量

变量 | 描述
--- | ---
project.basedir	|当前项目所在的目录。
project.baseUri	|当前项目所在的目录，以URI表示。从Maven 2.1.0开始
maven.build.timestamp|  表示构建开始（UTC）的时间戳。从Maven 2.1.0-M1开始

- properties
```
<properties>
    <mavenVersion>3.0</mavenVersion>
</properties>
```

## 4.配置文件
```
<profiles>
  <profile>
      <id>test</id>
      <build>
          <plugins>
             <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-antrun-plugin</artifactId>
                <version>1.8</version>
                <executions>
                   <execution>
                      <phase>test</phase>
                      <goals>
                         <goal>run</goal>
                      </goals>
                      <configuration>
                      <tasks>
                         <echo>Using env.test.properties</echo>
                         <copy file="src/main/resources/env.test.properties" tofile="${project.build.outputDirectory}/env.properties" overwrite="true"/>
                      </tasks>
                      </configuration>
                   </execution>
                </executions>
             </plugin>
          </plugins>
      </build>
  </profile>
  <profile>
      <id>normal</id>
      <build>
          <plugins>
             <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-antrun-plugin</artifactId>
                <version>1.8</version>
                <executions>
                   <execution>
                      <phase>test</phase>
                      <goals>
                         <goal>run</goal>
                      </goals>
                      <configuration>
                      <tasks>
                         <echo>Using env.properties</echo>
                         <copy file="src/main/resources/env.properties" tofile="${project.build.outputDirectory}/env.properties" overwrite="true"/>
                      </tasks>
                      </configuration>
                   </execution>
                </executions>
             </plugin>
          </plugins>
      </build>
  </profile>
  <profile>
      <id>prod</id>
      <build>
          <plugins>
             <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-antrun-plugin</artifactId>
                <version>1.8</version>
                <executions>
                   <execution>
                      <phase>test</phase>
                      <goals>
                         <goal>run</goal>
                      </goals>
                      <configuration>
                      <tasks>
                         <echo>Using env.prod.properties</echo>
                         <copy file="src/main/resources/env.prod.properties" tofile="${project.build.outputDirectory}/env.properties" overwrite="true"/>
                      </tasks>
                      </configuration>
                   </execution>
                </executions>
             </plugin>
          </plugins>
      </build>
  </profile>
</profiles>
```
- 1.命令控制台
```
mvn test -Ptest
```
- 2.Maven设置激活配置文件
```
<settings xmlns="http://maven.apache.org/POM/4.0.0"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
   http://maven.apache.org/xsd/settings-1.0.0.xsd">
   ...
   <activeProfiles>
      <activeProfile>test</activeProfile>
   </activeProfiles>
</settings>
```
- 3. 环境变量激活配置文件
```
<profiles>
  <profile>
      <id>test</id>
      <activation>
        <property>
           <name>env</name>
           <value>test</value>
        </property>
      </activation> 
  </profile>
</profiles>
```
执行测试
```
mvn test -Denv=test
```
- 4. 操作系统激活配置文件
```
<activation>
  <os>
     <name>Windows XP</name>
     <family>Windows</family>
     <arch>x86</arch>
     <version>5.1.2600</version>
  </os>
</activation>
```
- 5.文件的存在或者缺失激活配置文件
```aidl
<profile>
   <id>test</id>
   <activation>
      <file>
         <missing>target/generated-sources/axistools/wsdl2java/
         com/companyname/group</missing>
      </file>
   </activation>
</profile>
```

## 5. 自定义插件
- demo
测试命令格式
```
mvn groupId:artifactId:version:goal

mvn com.mycompany.app:app01:1.0-SNAPSHOT:sayhi
```
