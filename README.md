# rpc-learning
alibaba midware race

一个简单的RPC框架
RPC（Remote Procedure Call ）——远程过程调用，它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的协议。RPC协议假定某些传输协议的存在，如TCP或UDP，为通信程序之间携带信息数据。在OSI网络通信模型中，RPC跨越了传输层和应用层。RPC使得开发包括网络分布式多程序在内的应用程序更加容易。
框架——让编程人员便捷地使用框架所提供的功能，由于RPC的特性，聚焦于应用的分布式服务化开发，所以成为一个对开发人员无感知的接口代理，显然是RPC框架优秀的设计。
题目要求
1.要成为框架：对于框架的使用者，隐藏RPC实现。
2.网络模块可以自己编写，如果要使用IO框架，要求使用netty-4.0.23.Final。
3.支持异步调用，提供future、callback的能力。
4.能够传输基本类型、自定义业务类型、异常类型（要在客户端抛出）。
5.要处理超时场景，服务端处理时间较长时，客户端在指定时间内跳出本次调用。
6.提供RPC上下文，客户端可以透传数据给服务端。
7.提供Hook，让开发人员进行RPC层面的AOP。
注：为了降低第一题的难度，RPC框架不需要注册中心，客户端识别-DSIP的JVM参数来获取服务端IP。

我要做什么？
题干主要提供了两样东西:rpc-api, test-demo
rpc-api是一个空实现例子,参赛选手需要从Maven中心仓库将这个空实现的源代码下载下来,然后建立一个新项目,将rpc-api中题干中要求实现的类（com.alibaba.middleware.race.rpc.api.impl.RpcConsumerImpl，com.alibaba.middleware.race.rpc.api.impl.RpcProviderImpl）进行实现。rpc-api中提供的代码仅仅作为参考，不过其中的Api都需要在自己的工程对应的类里实现。
组委会测试的方式是通过选手提供的build.sh脚本,将选手实现的rpc-api打包成一个jar然后得到选手实现的类进行测试。


test-demo是做什么的？
test-demo是测试用例,下载下来时test-demo默认依赖的是Maven中心仓库中的rpc-api.
因为rpc-api是空实现,因此默认test-demo是跑不通的.
选手下载了rpc-api的源码后,自己建立了工程并且实现了需要实现的方法后,通过"一定"的方法使得test-demo依赖自己实现的rpc-api从而可以测试自己的代码。
原则上来说test-demo与题干无关。


那么我到底要做什么?
通过Maven把rpc-api的源码下载下来,使用"一定方法"导入到IDE内,然后把代码中题干要求完成的函数实现。
然后从SVN上把test-demo的代码下载下来,使用"一定方法"把test-demo的依赖改成自己改写过的rpc-api,然后进行测试。
测试通过后,编写build.sh脚本用来打包自己的rpc-api实现。（原则上如果你不需要测试的话,你完全可以不需要test-demo就可以完成rpc这道题目）
