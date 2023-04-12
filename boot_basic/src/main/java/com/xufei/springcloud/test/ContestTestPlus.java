package com.xufei.springcloud.test;

import java.util.stream.IntStream;

/**
 * @Author: XuFei
 * @Date: 2022/10/10 14:24
 */
public class ContestTestPlus {
    // 上下文类
    public static class Context {
        private String name;
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // 复制上下文到 ThreadLocal 中
    public final static class ActionContext {
        private static final ThreadLocal<Context> threadLocal = new ThreadLocal<Context>(){
            @Override
            protected Context initialValue() {
                return new Context();
            }
        };
        public static ActionContext getActionContext() {
            return ContextHolder.actionContext;
        }
        private ActionContext(){}

        public Context getContext() {
            return threadLocal.get();
        }

        public static class ContextHolder {// 获取 ActionContext 单例
            private final static ActionContext actionContext = new ActionContext();
        }
    }

    // 设置上下文名字
    public static class QueryNameAction {
        public void execute() {
            try {
                Thread.sleep(1000L);
                String name = Thread.currentThread().getName();
                ActionContext.getActionContext().getContext().setName(name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 设置上下文 ID
    public static class QueryIdAction {
        public void execute() {
            try {
                Thread.sleep(1000L);
                long id = Thread.currentThread().getId();
                ActionContext.getActionContext().getContext().setId(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 执行方法
    public static class ExecutionTask implements Runnable {
        private final QueryNameAction queryNameAction = new QueryNameAction();
        private final QueryIdAction queryIdAction = new QueryIdAction();

        @Override
        public void run() {
            queryNameAction.execute();// 设置线程名
            System.out.println("The name query successful");
            queryIdAction.execute();// 设置线程 ID
            System.out.println("The id query successful");
            System.out.println("The Name is " + ActionContext.getActionContext().getContext().name+"and id"+ActionContext.getActionContext().getContext().getId());
        }
    }

    public static void main(String[] args) {
        IntStream.range(1, 5).forEach(i -> new Thread(new ContextTest().new ExecutionTask()).start());
    }
}
