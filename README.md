# 计算器解决方案

## 概述
基于编译原理实现的四则运算计算器，支持括号、小数和完整的错误处理。

## 功能特性
- ✅ 四则运算（+、-、*、/）
- ✅ 括号优先级支持
- ✅ 小数运算
- ✅ 完整的错误检测和提示
- ✅ 文件批处理

## 构建和运行

### 前提条件
- Java 8+
- Maven 3.6+

### 构建项目
```bash
mvn clean compile
```

### 运行测试
```bash
mvn test
```

### 运行程序
```bash
mvn compile
java -cp target/classes org.example.calculator.App src/main/resources/input.txt output.txt
```
上述命令使用了程序内置的input测试文件，实际使用中可以替换为实际需要输入的测试文件，输出文件output.txt在当前目录

### 测试数据
项目包含测试文件 src/main/resources/input.txt，包含各种测试用例。

