package com.xufei;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * clear()从这张Map中删除所有的映射。
 * Object clone()返回此 HashMap实例的浅拷贝：键和值本身不被克隆。
 * V compute(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)尝试计算用于指定键和其当前映射的值的映射（或 null如果没有当前映射）。
 * V computeIfAbsent(K key, Function<? super K,? extends V> mappingFunction)如果指定的键尚未与值相关联（或映射到 null ），则尝试使用给定的映射函数计算其值，并将其输入到此映射中，除非 null 。
 * V computeIfPresent(K key, BiFunction<? super K,? super V,? extends V> remappingFunction)如果指定的密钥的值存在且非空，则尝试计算给定密钥及其当前映射值的新映射。
 * boolean containsKey(Object key)如果此映射包含指定键的映射，则返回 true 。
 * boolean containsValue(Object value)如果此Map将一个或多个键映射到指定值，则返回 true 。
 * Set<Map.Entry<K,V>> entrySet()返回此Map中包含的映射的Set视图。
 * void forEach(BiConsumer<? super K,? super V> action)对此映射中的每个条目执行给定的操作，直到所有条目都被处理或操作引发异常。
 * V get(Object key)返回到指定键所映射的值，或 null如果此映射包含该键的映射。
 * V getOrDefault(Object key, V defaultValue)返回到指定键所映射的值，或 defaultValue如果此映射包含该键的映射。
 * boolean isEmpty()如果此Map不包含键值映射，则返回 true 。
 * Set<K> keySet()返回此Map中包含的键的Set视图。
 * V merge(K key, V value, BiFunction<? super V,? super V,? extends V> remappingFunction)如果指定的键尚未与值相关联或与null相关联，则将其与给定的非空值相关联。
 * V put(K key, V value)将指定的值与此映射中的指定键相关联。
 * void putAll(Map<? extends K,? extends V> m)将指定Map的所有映射复制到此Map。
 * V putIfAbsent(K key, V value)如果指定的键尚未与某个值相关联（或映射到 null ），则将其与给定值相关联并返回 null ，否则返回当前值。
 * V remove(Object key)从该Map中删除指定键的映射（如果存在）。
 * boolean remove(Object key, Object value)仅当指定的密钥当前映射到指定的值时删除该条目。
 * V replace(K key, V value)只有当目标映射到某个值时，才能替换指定键的条目。
 * boolean replace(K key, V oldValue, V newValue)仅当当前映射到指定的值时，才能替换指定键的条目。
 * void replaceAll(BiFunction<? super K,? super V,? extends V> function)将每个条目的值替换为对该条目调用给定函数的结果，直到所有条目都被处理或该函数抛出异常。
 * int size()返回此Map中键值映射的数量。
 * Collection<V> values()返回此Map中包含的值的Collection视图。
 */
public class MapTest {

    public void test() {
        Map<String, String> map = new HashMap<>();
        map.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                System.out.println("k:" + s + "<-------->" + "v:" + s2);
            }
        });
        map.forEach((k, v) -> System.out.println("k:" + k + "<-------->" + "v:" + v));
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "<------>" + entry.getValue());
        }
    }
}
