package java8.nginx.assign;

/**
 * String channel：负载渠道
 * final Integer weight：权重，保存配置的权重
 * Integer effectiveWeight：有效权重，轮询的过程权重可能变化
 * Integer currentWeight：当前权重，比对该值大小获取节点
 *   第一次加权轮询时：currentWeight = weight = effectiveWeight
 *   后面每次加权轮询时：currentWeight 的值都会不断变化，其他权重不变
 */
 public class Node implements Comparable<Node>{
    private String channel;
    private final Integer weight;
    private Integer effectiveWeight;
    private Integer currentWeight;

    public Node(String channel,Integer weight){
        this.channel = channel;
        this.weight = weight;
        this.effectiveWeight = weight;
        this.currentWeight = weight;
    }

    public Node(String channel, Integer weight, Integer effectiveWeight, Integer currentWeight) {
        this.channel = channel;
        this.weight = weight;
        this.effectiveWeight = effectiveWeight;
        this.currentWeight = currentWeight;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getEffectiveWeight() {
        return effectiveWeight;
    }

    public void setEffectiveWeight(Integer effectiveWeight) {
        this.effectiveWeight = effectiveWeight;
    }

    public Integer getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }

    @Override
    public int compareTo(Node node) {
        return currentWeight > node.currentWeight ? 1 : (currentWeight.equals(node.currentWeight) ? 0 : -1);
    }

    @Override
    public String toString() {
        return "{channel='" + channel + "', weight=" + weight + ", effectiveWeight=" + effectiveWeight + ", currentWeight=" + currentWeight + "}";
    }
}

