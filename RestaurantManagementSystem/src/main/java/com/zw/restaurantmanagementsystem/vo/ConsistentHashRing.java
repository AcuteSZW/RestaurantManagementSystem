package com.zw.restaurantmanagementsystem.vo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConsistentHashRing<K, V> {
    // 虚拟节点数量，每个物理节点对应的副本数（用于平衡负载）
    private final int replicas;
    // 一致性哈希环，存储哈希值与节点的映射关系（线程安全的有序映射）
    private final ConcurrentSkipListMap<Long, String> ring;
    // 节点与哈希值映射表，记录每个物理节点对应的所有虚拟节点哈希值
    private final Map<String, Set<Long>> nodeToHashes;
    // 数据存储池，保存所有已存储的数据（键为电话号码字符串，线程安全）
    private final Map<String, V> data;
    // 节点数据映射，维护每个物理节点上存储的数据集合（线程安全）
    private final Map<String, List<V>> nodeDataMap;

    /**
     * 构造函数
     * @param nodes
     * @param replicas
     */
    public ConsistentHashRing(Collection<String> nodes, int replicas) {
        this.replicas = replicas;
        this.ring = new ConcurrentSkipListMap<>();
        this.nodeToHashes = new HashMap<>();
        this.data = new ConcurrentHashMap<>();
        this.nodeDataMap = new ConcurrentHashMap<>();
        addNodes(nodes);
    }

    // 哈希计算方法
    private static long hash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(key.getBytes());
            return ((long) (bytes[0] & 0xFF) << 24) |
                    ((long) (bytes[1] & 0xFF) << 16) |
                    ((long) (bytes[2] & 0xFF) << 8) |
                    (bytes[3] & 0xFF);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("不支持 SHA-1", e);
        }
    }

    // 添加节点
    private void addNode(String node) {
        Set<Long> hashes = new HashSet<>();
        for (int i = 0; i < replicas; i++) {
            String key = node + "#" + i;
            long hashVal = hash(key);
            hashes.add(hashVal);
            ring.put(hashVal, node);
        }
        nodeToHashes.put(node, hashes);
    }

    // 删除节点
    private void removeNode(String node) {
        Set<Long> hashes = nodeToHashes.get(node);
        if (hashes != null) {
            for (Long hashVal : hashes) {
                ring.remove(hashVal);
            }
            nodeToHashes.remove(node);
        }
    }

    // 添加节点集合
    public void addNodes(Collection<String> nodes) {
        for (String node : nodes) {
            addNode(node);
        }
    }

    // 删除节点
    public void deleteNode(String node) {
        removeNode(node);
        // 迁移数据到其他节点
        List<V> nodeData = nodeDataMap.remove(node);
        if (nodeData != null) {
            for (V value : nodeData) {
                K key = (K) ((CsvData) value).getTelephone(); // 假设V是CsvData类型
                String newHost = getHostForKey(key);
                nodeDataMap.computeIfAbsent(newHost, k -> new ArrayList<>()).add(value);
            }
        }
    }

    // 存储数据
    public void put(K key, V value) {
        String keyStr = key.toString();
        String targetNode = getHostForKey((K) keyStr);
        data.put(keyStr, value);
        nodeDataMap.computeIfAbsent(targetNode, k -> new ArrayList<>()).add(value);
    }

    // 获取节点数据
    public List<V> getDataForNode(String node) {
        return nodeDataMap.getOrDefault(node, Collections.emptyList());
    }

    // 获取键对应的节点
    public String getHostForKey(K key) {
        String keyStr = key.toString();
        long keyHash = hash(keyStr);
        Map.Entry<Long, String> entry = ring.ceilingEntry(keyHash);
        if (entry == null) {
            entry = ring.firstEntry();
        }
        return entry.getValue();
    }
}