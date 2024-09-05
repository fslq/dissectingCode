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
### 1.自定义线程池的核心参数和执行过程
有核心线程数、最大线程数、线程存活时间、等待队列、拒绝策略、线程工厂；流程：调用线程池时首先分配核心线程，如果不够，放入队列，队列满了，增加线程数到最大值，超过值则启用拒绝策略；当队列为空时，及时销毁出核心线程池以外的线程
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
## 面试项目题
### 1.介绍重点项目、使用了什么技术、提升了多大价值
外呼系统：销售用于打电话
背景：使用第三方CRM系统的外呼功能，只提供一家外呼渠道，依赖性太强，活动日存在不稳定因素，比如打电话占用、通话记录延迟、质检困难等
解决：自研一套分布式外呼系统，引入策略模式（避免了使用大量if else实现相同行为，使代码的阅读性和拓展性大大提高）整合了多家外呼平台，增加了分配机制（轮询加权重负载策略：WeightedRoundRobin.java）,引入MQ解决大规模通话录音上传压力（解耦出来结合定时任务数据校验），加入ASR功能提供智能化语音识别质检方案，数据库采用（水平分表：分渠道，垂直分表：热点数据）
### 2.任务流转的具体场景、规则是什么，Redis缓存使用过程、解决了什么问题，工作台大权限怎么查询控制？
场景：一个线索分配给销售，在其转化之前，一般会给销售分配三个工作任务（当日：优质线索（优质外呼线路188XXXXXXXX）、三日、七日和公海库（劣质线索，销售每天自行领取），每天销售会需要完成打电话、聊企微）
规则：每天凌晨4点跑定时任务，执行线索流失程序（当日->三日：打通电话，加上微信，三日->七日：打通电话，微信聊天，七日->公海库：一周后任未转化的会掉公海，当日->公海库：未打通电话）
Redis缓存使用过程:
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
解决了什么问题：
首先，这个缓存维护的是一条线索对应多个销售所在流失阶段任务完成信息，这种信息一些需要扫描表得到（如通话记录表（多个分表）），有些需要逻辑处理得到（如流转状态），这些在查询的过程中完全可以解耦出来，直接从缓存中获取，有效提高查询效率，其次，这个缓存的线索如果在1年时间内没有转化成功（在公海库），我们会将这部分缓存持久化到表中并删除（线索表也进行归档处理），以减少缓存和数据表的大小

工作台大权限怎么查询控制：
首先权限这边我们使用的是SpringSecurity鉴权，然后rpc调用用户中台查询该组织架构下所有用户id,最开始直接in操作（数据少时500），最后使用任务编排技术（id拆分和completablefultrue,无法解决分页问题），（临时表+联表的方式）推荐到公司BI上看