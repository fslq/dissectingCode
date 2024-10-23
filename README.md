# dissectingCode
dissecting source code
## the goal
i want get a job

## 面试代码题
### 1.手动实现消息队列，生产者和消费者
 使用阻塞队列存储消息，两个线程：生产者线程和消费者线程，见：MqQueueTest类
### 2.多线程交替打印ABC
声明三个线程，循环计数9次，每次对lock对象加synchronized，用取余数：0，1，2代表三个线程，打印之后唤醒其它线程，否则使用wait方法阻塞,见：ThreadOrderPrint类
## 面试技术题
### 1.自定义线程池的核心参数和执行过程，拒绝策略有哪些？
有核心线程数、最大线程数、线程存活时间、等待队列、拒绝策略、线程工厂；流程：调用线程池时首先分配核心线程，如果不够，放入队列，队列满了，增加线程数到最大值，超过值则启用拒绝策略；当队列为空时，及时销毁出核心线程池以外的线程
##### 拒绝策略有:
1. AbortPolicy：中止策略，线程池会抛出异常并中止执行此任务；
2. CallerRunsPolicy：把任务交给添加此任务的（main）线程来执行；
3. DiscardPolicy：忽略此任务，忽略最新的一个任务；
4. DiscardOldestPolicy：忽略最早的任务，最先加入队列的任务。

### 2.RabbitMQ如何保证消息不丢失
消息到 MQ 的过程中搞丢：事务机制和 Confirm 机制，注意：事务机制和 Confirm 机制是互斥的，两者不能共存，会导致 RabbitMQ 报错。
MQ 自己搞丢：持久化、集群、普通模式、镜像模式。
MQ 到消费过程中搞丢：basicAck 机制、死信队列、消息补偿机制。
### 3.Nacos为什么默认AP
AP可用性优先，nacos更加注重高可用和可靠性，而采取某些机制保证数据最终一致性
### 4.社交APP中为什么使用Netty,Netty为什么快
场景描述：在社交APP中，用户之间的实时消息传递是核心功能之一。这包括文字聊天、语音通话、视频通话等:
高性能：Netty的非阻塞I/O模型（多路复用IO）使得它能够同时处理大量的并发连接，保证消息的低延迟传递。
低延迟：通过零拷贝、内存池等优化手段，Netty减少了数据传输过程中的延迟，提升了用户体验。
协议支持：Netty支持WebSocket等协议，这些协议非常适合于实时通信场景，能够确保消息的实时性和可靠性。
### 5.新建一个对象，经历了哪些过程
类加载检查、分配内存（指针碰撞和空闲列表）、初始化零值（成员变量设为null或0）、设置对象头（类元信息、GC分代年龄、hashCode和锁信息）、执行init方法
### 6.java1.8的特性，以及lamda表达式和集合使用
Lambda 表达式允许把函数作为方法的参数，或者把代码本身当作数据处理，是一种匿名函数。它的语法由参数列表、箭头符号 “->” 和函数体组成。例如，(int x, int y) -> x + y 表示接收两个整数参数并返回它们的和；
### 7.二级索引执行的流程是？
如 select * from 表名 where age = 某个值，首先会在二级索引的 B + 树中进行查找。从二级索引树的根节点开始，一层一层往下找，一直找到叶节点的数据页，定位到 age 字段值对应的主键值。但此时仅能找到对应主键值，而找不到这行数据的所有字段。所以还需回表，根据主键值再到主键索引（聚集索引）中找主键对应的记录，获取其叶子结点存储的记录内容，相当于要搜索两张索引表。
### 8.Java中线程安全的集合有哪些？底层如何实现的？
1. Vector
底层通过数组实现，方法大多使用synchronized关键字实现同步，保证同一时间只有一个线程能执行方法。
2. Hashtable
基于哈希表实现，使用Entry[]数组存储键值对，所有方法通过synchronized关键字同步，处理哈希冲突时形成链表结构。
3. ConcurrentHashMap
Java 8 及以后基于数组、链表和红黑树。采用分段锁（Java 7 及以前）和CAS操作及volatile关键字实现线程安全。Java 8 取消分段锁结构，put操作通过计算哈希值确定位置，空位置用CAS插入，非空位置用synchronized锁处理冲突。
4. CopyOnWriteArrayList
底层是数组，写操作（如add、remove等）会复制整个数组，读操作无需加锁，通过ReentrantLock保证写操作的原子性。
5. CopyOnWriteArraySet
基于 CopyOnWriteArrayList 实现，利用其线程安全特性保证元素唯一性和线程安全，add方法先检查元素是否存在再添加。
### 9.HashMap底层如何实现的？
HashMap 在 JDK 1.7 时，是通过数组 + 链表实现的，而在 JDK 1.8 时，HashMap 是通过数组 + 链表或红黑树实现的。在 JDK 1.8 之后，如果链表的数量大于阈值（默认为 8），并且数组长度大于 64 时（否则，先扩容），为了查询效率会将链表升级为红黑树，但当红黑树的节点小于等于 6 时，为了节省内存空间会将红黑树退化为链表。
### 10.简要说说什么是ioc和aop，以及底层原理
##### IOC（控制反转）
将对象创建和依赖关系管理从代码转至容器。传统由程序员控制，IOC 模式下容器负责。
##### 底层原理
Spring 通过读取配置（XML 或注解），反射创建对象，按配置管理依赖关系，如构造函数或 setter 注入。
##### AOP（面向切面）
分离横切关注点（如日志,事务等），形成独立切面并织入业务逻辑，使业务更清晰易维护。
##### 底层原理
Spring 基于动态代理，目标对象有接口用 JDK 动态代理，无接口用 CGLIB 动态代理，在方法调用前后插入切面逻辑。
### 11.Spring中如何实现事务？事务的传播机制有哪些？什么情况下事务会失效？
##### Spring中如何实现事务？
只需要在类上或方法上添加 @Transactional 关键字，就可以实现事务的自动开启、提交或回滚
##### 事务的传播机制有哪些？
1. REQUIRED（默认传播行为）：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
2. SUPPORTS：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务方式执行。
3. MANDATORY：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
4. REQUIRESNEW：创建一个新的事务，如果当前存在事务，则挂起该事务。
5. NOTSUPPORTED：以非事务方式执行操作，如果当前存在事务，则挂起该事务。
6. NEVER：以非事务方式执行操作，如果当前存在事务，则抛出异常。
7. NESTED：如果当前存在事务，则在嵌套事务中执行；如果当前没有事务，则创建一个新的事务
##### 什么情况下事务会失效？
1. 非public修饰的方法
2. timeout超时（当在 @Transactional 上，设置了一个较小的超时时间时）
3. 代码中有try/catch
4. 调用类内部@Transactional方法
5. 数据库不支持事务
### 12.说一说MVCC机制的执行原理
在常规读（没有当前g读sql）的情况下，同一事务分别在 RC（读已提交）和 RR（可重复读）两个级别中的表现如下：
##### RC（读已提交）级别
在这个级别下，事务每次读取的数据都是其他事务已经提交的最新数据。例如，事务 A 在执行过程中，如果有事务 B 对某数据进行了更新并提交，那么事务 A 再次读取该数据时，会得到事务 B 更新后的新数据。这种特性可能导致同一个事务在多次读取同一数据时得到不同的结果，也就是可能出现不可重复读的现象。
##### RR（可重复读）级别
在 RR 级别中，事务在第一次读取数据时确定一个版本，之后在该事务执行期间，无论其他事务是否对该数据进行了更新并提交，该事务每次读取该数据时看到的都是第一次读取时确定的那个版本。例如，事务 A 第一次读取某数据为值 10，在事务 A 执行期间，即使事务 B 对该数据进行了更新并提交，事务 A 再次读取该数据时仍然看到值 10。这样就保证了在事务执行过程中对同一数据的读取是可重复的，避免了不可重复读的问题。
##### 原理
1. RR（可重复读）
基于 MVCC，事务启动创建视图，看到启动前已提交及自身更改的数据版本（小于等于自身事务 ID），执行中不受其他事务更新影响，除非自身更新。
2. RC（读已提交）
每次读取获取最新已提交数据快照，基于当前最新已提交情况读数据，可能出现不可重复读。
### 13.什么情况下会索引失效
1. 未遵循最左匹配原则
2. 使用列运算
3. 使用函数方法
4. 类型转换
5. 使用 is not null
6. 错误的模糊匹配
### 14.MySQL怎么优化
##### 单表优化
1. 建立并使用索引
2. 优化查询语句：避免 SELECT *，只查询需要的字段。使用小表驱动大表，比如，当 B 表的数据小于 A 表时，先查 B 表，再查 A 表，查询语句：select * from A where id in (select id from B)。
3. 优化表结构和数据类型：单表不要有太多字段，建议在 20 个字段以内，使用可以存下数据最小的数据类型，尽可能使用 not null 定义字段，因为 null 占用 4 字节空间。
##### 多表优化
1. 表拆分
2. 读写分离
### 15.大表下载怎么优化（OOM）
##### 原则：
全量加载不可行，那我们的目标就是如何实现数据的分批加载了。实事上，Mysql本身支持Stream查询，我们可以通过Stream流获取数据，然后将数据逐条刷入到文件中，每次刷入文件后再从内存中移除这条数据，从而避免OOM。
由于采用了数据逐条刷入文件，而且数据量达到百万级，所以文件格式就不要采用excel了，excel2007最大才支持104万行的数据。这里推荐：
以csv代替excel。
##### 技术：
MyBatis实现逐条获取数据，必须要自定义ResultHandler，然后在mapper.xml文件中，对应的select语句中添加fetchSize="-2147483648"。
最后将自定义的ResultHandler传给SqlSession来执行查询，并将返回的结果进行处理。
##### 测试：
先启动项目，然后打开jdk bin目录下的 jconsole.exe
### 16.Redis集群用过吗？Redission出现问题(主从切换)该怎么解决
redis的多节点分为三种：主从模式、哨兵模式和集群模式（主从+crc16(key,keylen) & 16383）
##### Redission出现问题(主从切换)该怎么解决
主从一致性问题：当主节点还没来得及将锁信息同步到从节点时，此时主节点宕机了。然后，从节点被设为主节点，这个从节点中无锁信息，因此产生了锁失效问题。

Redisson 提供了 multiLock 方案解决主从一致性问题，其思路如下：

设置多个并列的 Rediss 节点，对锁信息，将其写入每个 Redis 节点中。

获取锁时，向多个 Redis 节点中获取锁，只有每个节点都获取锁成功，才算获取锁成功。实现了，只要有一个节点存活，其他线程就不能获取锁，锁不会失效。

当其中有节点宕机时，其他节点仍然含有锁信息，其他节点仍然有效


### 17.分布式锁了解哪些？
基于数据库实现分布式锁:成功插入则获取锁，执行完成后删除对应的行数据释放锁：
基于缓存（Redis等）实现分布式锁:SETNX、expire、delete，第四个问题，释放锁的操作必须使用Lua脚本来实现。释放锁其实包含三步操作：获取、判断和删除，用Lua脚本来实现能保证这三步的原子性。
基于Zookeeper实现分布式锁：
　　比如，创建一个用于发号的节点“/test/lock”，然后以他为父亲节点，可以在这个父节点下面创建相同前缀的子节点，假定相同的前缀为“/test/lock/seq-”，在创建子节点时，同时指明是有序类型。如果是第一个创建的子节点，那么生成的子节点为/test/lock/seq-0000000000，下一个节点则为/test/lock/seq-0000000001，依次类推，等等。

### 18.分布式锁用到的场景有哪些？
##### 1. 线索转合同（生成订单）：
将线索id作为key
##### 2. 三方access_token刷新
压测时避免产生不同的token
##### 3. MQ去重（支付回调）
将订单id作为key,订单如果已处理，丢掉信息
### 19.用过哪些设计模式？简单说说如何实现的（比如工厂、责任链、策略）？
##### 1.工厂模式
目的：定义一个创建对象的接口，但让子类决定实例化哪一个类。工厂模式使一个类的实例化延迟到其子类。
实现：可以创建一个工厂类，该类包含一个方法，根据传入的参数决定创建并返回哪个类的实例。例如，线程工厂
##### 2.单例模式
目的：确保一个类只有一个实例，并提供一个全局访问点。
实现：在类内部创建一个私有静态实例，并提供一个公有静态方法来获取这个实例。例如，自定义线程池、配置管理器或日志记录器通常使用单例模式。
##### 3.策略模式
目的：定义一系列算法，将每个算法封装起来，并使它们可以互换使用。
实现：创建一个策略接口，不同的策略实现这个接口。上下文类使用这个策略接口作为属性，并在运行时可以更改策略。例如，外呼系统（外呼、挂断），排序算法可以作为策略实现，用户可以根据需要选择不同的排序策略。
##### 4.责任链模式
目的：使多个对象都有机会处理请求，从而避免请求的发送者和接收者之间的耦合关系。
实现：创建一个处理请求的接口，多个处理类实现这个接口。每个处理器内部持有下一个处理器的引用。当一个处理器不能处理请求时，它会将请求传递给下一个处理器。例如，分配系统（项目、部门、销售组、销售），错误处理系统可以使用责任链模式来处理不同类型的错误。
## 面试项目题
### 1.介绍重点项目、使用了什么技术、提升了多大价值
外呼系统：销售用于打电话
1. 背景：使用第三方CRM系统的外呼功能，只提供一家外呼渠道，依赖性太强，活动日存在不稳定因素，比如打电话占用、通话记录延迟、质检困难等
2. 解决：自研一套分布式外呼系统，引入策略模式（避免了使用大量if else实现相同行为，使代码的阅读性和拓展性大大提高）整合了多家外呼平台，增加了分配机制（轮询加权重负载策略：WeightedRoundRobin.java）,引入MQ解决大规模通话录音上传压力（解耦出来结合定时任务数据校验），加入ASR功能提供智能化语音识别质检方案，数据库采用（水平分表：分渠道，垂直分表：热点数据）
### 2.任务流转的具体场景、规则是什么，Redis缓存使用过程、解决了什么问题，工作台大权限怎么查询控制？
1. 场景：一个线索分配给销售，在其转化之前，一般会给销售分配三个工作任务（当日：优质线索（优质外呼线路188XXXXXXXX）、三日、七日和公海库（劣质线索，销售每天自行领取），每天销售会需要完成打电话、聊企微）
2. 规则：每天凌晨4点跑定时任务，执行线索流失程序（当日->三日：打通电话，加上微信，三日->七日：打通电话，微信聊天，七日->公海库：一周后任未转化的会掉公海，当日->公海库：未打通电话）
3. Redis缓存使用过程:
存储结构：key是leads-loss-key,
 value是[
            leadsId1,[
            {userId1,lossState1,addWechatState1,callNum1,callDuration1},
            {userId2,lossState2,addWechatState2,callNum2,callDuration2}
            ],
            leadsId2,[
            {userId3,lossState3,addWechatState3,callNum3,callDuration3},
            {userId4,lossState4,addWechatState4,callNum4,callDuration4}
            ],
        ]

##### 定义Java类
```
public class LeadDetail {  
    private String userId;  
    private String lossState;  
    private String addWechatState;  
    private int callNum;  
    private int callDuration;  
  
    // 构造方法、getter和setter省略  
}  
  
public class LeadInfo {  
    private String leadsId;  
    private List<LeadDetail> details;  
  
    // 构造方法、getter和setter省略  
}
```
##### 使用RedisTemplate存取（String）
```
@Autowired  
private RedisTemplate<String, Object> redisTemplate;  
  
public void storeLeadInfo(String key, List<LeadInfo> leadInfos) {  
    // 序列化操作
    String json = new ObjectMapper().writeValueAsString(leadInfos);  
    redisTemplate.opsForValue().set(key, json);  
}  
  
public List<LeadInfo> retrieveLeadInfo(String key) {  
    String json = (String) redisTemplate.opsForValue().get(key);  
    return new ObjectMapper().readValue(json, new TypeReference<List<LeadInfo>>() {});  
}
```
##### 使用RedisTemplate存取（哈希表）
```
// hashKey的设计应该根据你的应用程序的需求和数据的组织方式来决定。它可以是任何字符串，但通常应该具有一定的意义，以便于理解和维护。
public void storeLeadInfoInHash(String hashKey, String key, LeadInfo leadInfo) {  
    ObjectMapper mapper = new ObjectMapper();  
    try {  
        String json = mapper.writeValueAsString(leadInfo);  
        redisTemplate.opsForHash().put(hashKey, key, json);  
    } catch (JsonProcessingException e) {  
        e.printStackTrace();  
    }  
}  
  
public LeadInfo retrieveLeadInfoFromHash(String hashKey, String key) {  
    String json = (String) redisTemplate.opsForHash().get(hashKey, key);  
    if (json != null) {  
        try {  
            return mapper.readValue(json, LeadInfo.class);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
    return null;  
}
```
4. 解决了什么问题：
首先，这个缓存维护的是一条线索对应多个销售所在流失阶段任务完成信息，这种信息一些需要扫描表得到（如通话记录表（多个分表）），有些需要逻辑处理得到（如流转状态），这些在查询的过程中完全可以解耦出来，直接从缓存中获取，有效提高查询效率，其次，这个缓存的线索如果在1年时间内没有转化成功（在公海库），我们会将这部分缓存持久化到表中并删除（线索表也进行归档处理），以减少缓存和数据表的大小

5. 工作台大权限怎么查询控制：
首先权限这边我们使用的是SpringSecurity鉴权，然后rpc调用用户中台查询该组织架构下所有用户id,最开始直接in操作（数据少时500），最后使用任务编排技术（id拆分和completablefultrue,无法解决分页问题），（临时表+联表的方式）推荐到公司BI上看

## 面试模拟
### 1. 你好，请先简单介绍一下自己。
面试官您好，我叫孙赵龙，安徽淮南人，于2020年毕业于安徽工程大学软件工程专业，拥有 4 年的 Java 开发工作经验，对各种 Java 技术和框架具有较深的理解和实践经验，曾在昆山中创软件和北京赛优职教育担任Java开发一职。
### 2. 能详细说一下你在工作中参与过的主要项目吗？从项目的需求背景、技术选型以及你在其中承担的核心职责等方面展开。
好的。在 21年 - 24 年我在北京赛优职教育科技有限公司担任 Java 开发工程师期间，参与过多个关键项目。
以 CRM 系统为例，这个项目的需求背景是公司业务发展需要一套高效、稳定、可靠的客户关系管理系统，同时实现跨与第三方系统数据同步。在技术选型上，我们采用的是 SpringBoot 作为基础框架，Dubbo 用来实现分布式服务，Nacos 作为配置中心和注册中心。我在这个项目中的核心职责包括参与系统的 0 - 1 开发与设计，整合 Nacos 配置服务，确保各个服务能够正确获取配置信息并且稳定运行。
###### 具体地说，在 CRM 工作台的开发业务功能中，需求是为销售人员提供一个高效的工作平台。通过结合 Xxl - Job、IM 和 Redis 缓存等技术,实现销售工作台和线索流转规则核心功能。在接口防抖方面，通过分析业务场景和用户操作习惯，采用合适的技术手段，在不影响用户体验的前提下，有效避免接口的重复调用。在线索流转规则方面，通过了解的公司业务逻辑，根据不同的线索状态和时间节点，设计了合理的流转规则，并通过 Redis 缓存提高了数据的读取效率，确保线索能够按照设定的规则（如打电话、加企微、推班、回访等指标数据，以及当、三、七及公海流失规则）进行定时流转。
在电销外呼系统项目中，我担任组长一职，由于电销是公司 CRM 系统的核心业务需求，所以该系统需要具备稳定、高效、可靠和高拓展性，以满足公司各业务产品的调用需求。这里参考了优秀开源框架 Jeepay 的设计思路，采用策略模式提高拓展性，数据库使用 MyBatisPlus 框架，解决了渠道快速迭代问题。对于录音文件存档缓慢的问题，我选择 RabbitMQ 作为消息中间件，通过合理设计消息队列的结构和处理流程，实现了录音文件的高效存档，以保证系统的稳定性和数据的完整性。同时，在话单存储方面，结合 MySQL 分表技术，有效提高了话单存储和查询的效率,其次，为了解决质检困难和话术优化问题，我们又引入了ASR技术，通过语音识别和大模型学习的方式解决这些问题。
###### 在 20年 - 21 年在昆山中创软件有限公司担任 Java 开发工程师期间，参与了金融风险管理系统和数据报表的开发项目。风险管理系统需要实时监控和分析风险数据，数据报表项目则需要高效准确地生成各种报表。我们采用 SpringCloud、BI 以及存储过程技术。我负责使用 SpringCloud 和存储过程技术开发风险管理系统，通过深入研究业务流程和风险评估模型，设计了合理的技术方案，确保系统能够实时获取和处理风险数据。在数据报表项目中，通过多线程、并发和 IO 技术解决报表数据批量上传 Oracle 数据库的性能问题。我对多线程和并发技术进行了深入研究，根据数据库的特点和报表数据的上传需求，合理分配线程资源，优化了数据上传的流程，提高了系统的性能。同时，我还负责部分前端 Vue 项目的编写，使用 Maven 仓库管理项目依赖，确保项目的顺利开发和维护。
### 3. 在这些项目中，你遇到过哪些技术挑战，你是如何解决的呢？请详细阐述技术细节和思路。
孙赵龙：在电销外呼系统项目中，遇到了几个关键技术挑战。
首先是渠道快速迭代的问题。随着业务的发展，电销渠道不断增加和变化，原有的系统架构难以快速适应这种变化。为了解决这个问题，我深入研究了设计模式，最终选择了策略模式。通过将不同渠道的业务逻辑封装成不同的策略类，使得系统可以根据渠道的类型动态地加载和执行相应的策略，从而实现了系统的高拓展性。在实现过程中，我仔细分析了每个渠道的业务流程和接口规范，设计了一套通用的策略接口和抽象类，然后针对不同渠道的具体需求，实现了具体的策略子类。这样，当有新的渠道加入时，只需要开发一个新的策略子类，而不需要对整个系统架构进行大规模的修改。
###### 对于录音文件存档缓慢的问题，由于录音文件数量庞大（2w*几十mb），传统的文件存储方式效率低下。我选择 RabbitMQ 作为消息中间件来解决这个问题。首先，我设计了一个合理的消息队列结构，将录音文件的存储任务封装成消息，发送到消息队列中。然后，通过多个消费者从消息队列中获取消息，并执行录音文件的存储任务。这样，通过消息队列的异步处理机制，大大提高了录音文件的存档效率。在实现过程中，我还需要考虑消息的可靠性和顺序性问题。为了确保消息的可靠性，我设置了消息的持久化机制(落表)，使得消息在服务器重启等情况下也不会丢失。对于消息的顺序性，我通过合理设置消息队列的消费者数量和消费顺序，确保录音文件按照正确的顺序进行存档。
在 CRM 系统的开发中，遇到了复杂慢 SQL 查询语句的优化问题。为了解决这个问题，我首先对慢 SQL 语句进行了详细的分析，找出了导致查询缓慢的原因。主要原因包括缺少索引、SQL 逻辑复杂以及代码逻辑不合理等。针对缺少索引的问题，我根据查询条件和表的结构，添加了合适的索引。对于 SQL 逻辑复杂的问题，我对 SQL 语句进行了逻辑拆分，将一个复杂的查询语句分解成多个简单的查询语句，然后通过合理的连接方式将它们组合在一起。在代码逻辑优化方面，我对涉及到 SQL 查询的代码进行了重新梳理，去除了一些不必要的计算和判断，提高了代码的执行效率。 