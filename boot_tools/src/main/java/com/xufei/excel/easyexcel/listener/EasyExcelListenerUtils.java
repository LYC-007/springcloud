package com.xufei.excel.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 监听器优化,上面章节的读取Excel的程序弊端:
 *      1.每次解析不同数据模型都要新增一个监听器, 重复工作量大;
 *      2.即使用了匿名内部类,程序也显得臃肿;
 *      3.数据处理一般都会存在于项目的service中, 监听器难免会依赖dao层, 导致程序耦合度高.
 *
 * 解决方案:
 *      1.通过泛型指定数据模型类型, 针对不同类型的数据模型只需要定义一个监听器即可;
 *      2.使用jdk8新特性中的函数式接口, 将数据处理从监听器中剥离出去, 进行解耦.
 */
public class EasyExcelListenerUtils<T> {

    /**
     * 获取读取Excel的监听器对象
     * 为了解耦及减少每个数据模型bean都要创建一个监听器的臃肿, 使用泛型指定数据模型类型
     * 使用jdk8新特性中的函数式接口 Consumer
     * 可以实现任何数据模型bean的数据解析, 不用重复定义监听器
     *
     * @param consumer  处理解析数据的函数, 一般可以是数据入库逻辑的函数
     * @param threshold 阈值,达到阈值就处理一次存储的数据
     * @param <T>       数据模型泛型
     * @return 返回监听器
     */
    public static <T> AnalysisEventListener<T> getReadListener(Consumer<List<T>> consumer, int threshold) {

        return new AnalysisEventListener<T>() {

            /**
             * 存储解析的数据 T t
             */
            // ArrayList基于数组实现, 查询更快
            // List<T> dataList = new ArrayList<>(threshold);
            // LinkedList基于双向链表实现, 插入和删除更快
            final List<T> dataList = new LinkedList<>();

            /**
             * 每解析一行数据事件调度中心都会通知到这个方法, 订阅者1
             * @param data 解析的每行数据
             * @param context
             */
            @Override
            public void invoke(T data, AnalysisContext context) {
                dataList.add(data);
                // 达到阈值就处理一次存储的数据
                if (dataList.size() >= threshold) {
                    consumer.accept(dataList);
                    dataList.clear();
                }
            }

            /**
             * excel文件解析完成后,事件调度中心会通知到该方法, 订阅者2
             * @param context
             */
            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                // 最后阈值外的数据做处理
                if (dataList.size() > 0) {
                    consumer.accept(dataList);
                }
            }
        };

    }

    /**
     * 获取读取Excel的监听器对象, 不指定阈值, 默认阈值为 2000
     */
    public static <T> AnalysisEventListener<T> getReadListener(Consumer<List<T>> consumer) {
        return getReadListener(consumer, 2000);
    }
}
