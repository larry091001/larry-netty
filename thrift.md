############生成命令##############
=================java=================
1.cmd 命令窗口下，
D:\idea-project-work\larry-netty> thrift --gen java src/thrift/data.thrift

=================python=================
1.cmd 命令窗口下，
D:\idea-project-work\larry-netty> thrift --gen py src/thrift/data.thrift

################## Thrift windows python 安装 #################
记一下 Thrift windows python 安装
现在已经有windwos 预编译的 thrift 了，可以直接下载。先到 https://thrift.apache.org/download 页面把thrift-0.9.2.tar.gz 、Thrift compiler for Windows (thrift-0.9.2.exe) 下载下来。

1.把 thrift-0.9.2.exe 改名为 thrift.exe 并放到 path 里面。
2. 解压 thrift-0.9.2.tar.gz
3.进入thrift-0.9.2\lib\py目录，执行 setup.py install 安装python扩展。
4.现在到 thrift-0.9.2\tutorial 目录，执行 thrift.exe –r –gen py tutorial.thrift 会在 gen-py 目录生成 thrift 代码。
5.现在可以尝试执行 python 例子了。

服务器：
D:\thrift-0.9.2\tutorial>c:\Python27\python.exe py\PythonServer.py
Traceback (most recent call last):
  File "py\PythonServer.py", line 24, in <module>
    sys.path.insert(0, glob.glob('../../lib/py/build/lib.*')[0])
IndexError: list index out of range

#这个错误直接注释掉出错行即可。

D:\thrift-0.9.2\tutorial>c:\Python27\python.exe py\PythonServer.py
Starting the server...
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
D:\thrift-0.9.2\tutorial>c:\Python27\python.exe py\PythonServer.py
Traceback (most recent call last):
  File "py\PythonServer.py", line 24, in <module>
    sys.path.insert(0, glob.glob('../../lib/py/build/lib.*')[0])
IndexError: list index out of range
 
#这个错误直接注释掉出错行即可。
 
D:\thrift-0.9.2\tutorial>c:\Python27\python.exe py\PythonServer.py
Starting the server...
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
ping()
add(1,1)
calculate(1, Work(comment=None, num1=1, num2=0, op=4))
calculate(1, Work(comment=None, num1=15, num2=10, op=2))
getStruct(1)
客户端：
d:\thrift-0.9.2\tutorial>c:\Python27\python.exe py\PythonClient.py
ping()
1+1=2
InvalidOperation: InvalidOperation(what=4, why='Cannot divide by 0')
15-10=5
Check log: 5

d:\thrift-0.9.2\tutorial>
d:\thrift-0.9.2\tutorial>c:\Python27\python.exe py\PythonClient.py
ping()
1+1=2
InvalidOperation: InvalidOperation(what=4, why='Cannot divide by 0')
15-10=5
Check log: 5
 