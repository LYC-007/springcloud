package com.xufei.test;

/**
 *
 * https://segmentfault.com/a/1190000011282426
 * SnowFlake算法生成id的结果是一个64bit大小的整数:
 * 1).1位，不用。二进制中最高位为1的都是负数，但是我们生成的id一般都使用整数，所以这个最高位固定是0
 * 2).41位，用来记录时间戳（毫秒）。
 *      a.41位可以表示$2^{41}-1$个数字，
 *      b.如果只用来表示正整数（计算机中正数包含0），可以表示的数值范围是：0 至 $2^{41}-1$，减1是因为可表示的数值范围是从0开始算的，而不是1。
 *      c.也就是说41位可以表示$2^{41}-1$个毫秒的值，转化成单位年则是$(2^{41}-1) / (1000 * 60 * 60 * 24 * 365) = 69$年
 * 3).10位，用来记录工作机器id。
 *      a.可以部署在$2^{10} = 1024$个节点，包括5位datacenterId和5位workerId
 *      b.5位（bit）可以表示的最大正整数是$2^{5}-1 = 31$，即可以用0、1、2、3、....31这32个数字，来表示不同的datecenterId或workerId
 * 4).12位，序列号，用来记录同毫秒内产生的不同id。
 *      a.12位（bit）可以表示的最大正整数是$2^{12}-1 = 4095$，即可以用0、1、2、3、....4094这4095个数字，来表示同一机器同一时间截（毫秒)内产生的4095个ID序号
 * 由于在Java中64bit的整数是long类型，所以在Java中SnowFlake算法生成的id就是long来存储的。
 */
public class IdWorker{

    private long workerId;
    private long datacenterId;
    private long sequence;

    public IdWorker(long workerId, long datacenterId, long sequence){
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",maxDatacenterId));
        }
        System.out.printf("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, workerId);

        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    private long twepoch = 1288834974657L; //起始时间戳，用于用当前时间戳减去这个时间戳，算出偏移量

    /**
     * 那既然现在知道算出来long maxWorkerId = -1L ^ (-1L << 5L)中的maxWorkerId = 31，有什么含义？为什么要用左移5来算？如果你看过概述部分，请找到这段内容看看：
     *
     * 5位（bit）可以表示的最大正整数是$2^{5}-1 = 31$，即可以用0、1、2、3、....31这32个数字，来表示不同的datecenterId或workerId
     * -1L ^ (-1L << 5L)结果是31，$2^{5}-1$的结果也是31，所以在代码中，-1L ^ (-1L << 5L)的写法是利用位运算计算出5位能表示的最大正整数是多少
     */
    private long workerIdBits = 5L; //workerId占用的位数：5
    private long datacenterIdBits = 5L; //datacenterId占用的位数：5
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);  // workerId可以使用的最大数值：31
    private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits); // datacenterId可以使用的最大数值：31
    private long sequenceBits = 12L;//序列号占用的位数：12

    private long workerIdShift = sequenceBits; // 12
    private long datacenterIdShift = sequenceBits + workerIdBits; // 12+5 = 17
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; // 12+5+5 = 22
    private long sequenceMask = -1L ^ (-1L << sequenceBits);//4095    所以在代码中，-1L ^ (-1L << sequenceBits)的写法是利用位运算计算出"sequenceBits"bit位能表示的最大正整数是多少

    private long lastTimestamp = -1L;

    public long getWorkerId(){
        return workerId;
    }

    public long getDatacenterId(){
        return datacenterId;
    }

    public long getTimestamp(){
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long timestamp = timeGen();  //timeGen()=System.currentTimeMillis();

        if (timestamp < lastTimestamp) {//private long lastTimestamp = -1L;
            System.err.printf("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp);
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
                    lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;  //这段代码通过位与运算保证计算的结果范围始终是 0-4095 ！
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;// << 左移算法
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
        /**
         *
         * return ((timestamp - 1288834974657) << 22) |
         *         (datacenterId << 17) |
         *         (workerId << 12) |
         *         sequence;
         * 设：timestamp  = 1505914988849，twepoch = 1288834974657
         * 1505914988849 - 1288834974657 = 217080014192 (timestamp相对于起始时间的毫秒偏移量)，其(a)二进制左移22位计算过程如下：
         *                         |<--这里开始向左移22位
         * 00000000 00000000 000000|00 00110010 10001010 11111010 00100101 01110000 // a = 217080014192
         * 00001100 10100010 10111110 10001001 01011100 00|000000 00000000 00000000 // a左移22位后的值(la)
         *                                                |<--这里后面的位补0
         *
         *
         *设：datacenterId  = 17，其（b）二进制左移17位计算过程如下：
         *                    |<--这里开始向左移17位
         * 00000000 00000000 0|0000000 00000000 00000000 00000000 00000000 00010001 // b = 17
         * 00000000 00000000 00000000 00000000 00000000 0010001|0 00000000 00000000 // b左移17位后的值(lb)
         *                                                     |<--这里后面的位补0
         *
         *
         *设：workerId  = 25，其（c）二进制左移12位计算过程如下：
         *              |<--这里开始左移12位
         * 00000000 0000|0000 00000000 00000000 00000000 00000000 00000000 00011001 // c = 25
         * 00000000 00000000 00000000 00000000 00000000 00000001 1001|0000 00000000 // c左移12位后的值(lc)
         *                                                           |<--这里后面的位补0
         *
         * 管道符号|在Java中也是一个位运算符。其含义是：x的第n位和y的第n位 只要有一个是1，则结果的第n位也为1，否则为0，因此，我们对四个数的位或运算如下：
         *  1  |                    41                        |  5  |   5  |     12
         *    0|0001100 10100010 10111110 10001001 01011100 00|00000|0 0000|0000 00000000 //la
         *    0|0000000 00000000 00000000 00000000 00000000 00|10001|0 0000|0000 00000000 //lb
         *    0|0000000 00000000 00000000 00000000 00000000 00|00000|1 1001|0000 00000000 //lc
         * or 0|0000000 00000000 00000000 00000000 00000000 00|00000|0 0000|0000 00000000 //sequence
         * ------------------------------------------------------------------------------------------
         *    0|0001100 10100010 10111110 10001001 01011100 00|10001|1 1001|0000 00000000 //结果：910499571847892992
         *    怎么得到"910499571847892992"的。下面是具体的算法:
         *    0000  1   1   00  1   0  1  000  1   0  1  0  1  1  1  1  1  0 1   000 1 00 1  0 1  0   1  1  1  0000 1   000  1  1  1  00  1   0000 0000 0000
         *         59  58      55     53      49     47    45 44 43 42 41   39      35   32   30     28 27 26      21       17 16 15     12
         *    各个下标作为2的幂数来计算，并相加：2^{59}+2^{58}+2^{55}+2^{53}+2^{49}+2^{47}+2^{45}+2^{44}+2^{43}+2^{42}+2^{41}+2^{39}+2^{35}+2^{32}+2^{30}+2^{28}+2^{27}+2^{26}+2^{21}+2^{17}+2^{16}+2^{15}+2^{2}
         *     2^59}  : 576460752303423488
         *     2^58}  : 288230376151711744
         *     2^55}  :  36028797018963968
         *     2^53}  :   9007199254740992
         *     2^49}  :    562949953421312
         *     2^47}  :    140737488355328
         *     2^45}  :     35184372088832
         *     2^44}  :     17592186044416
         *     2^43}  :      8796093022208
         *     2^42}  :      4398046511104
         *     2^41}  :      2199023255552
         *     2^39}  :       549755813888
         *     2^35}  :        34359738368
         *     2^32}  :         4294967296
         *     2^30}  :         1073741824
         *     2^28}  :          268435456
         *     2^27}  :          134217728
         *     2^26}  :           67108864
         *     2^21}  :            2097152
         *     2^17}  :             131072
         *     2^16}  :              65536
         *     2^15}  :              32768
         * +   2^12}  :               4096
         * ----------------------------------------
         *              910499571847892992
         *
         *
         *
         *
         *
         *
         *上面的64位我按1、41、5、5、12的位数截开了，方便观察:
         *  1  |                    41                        |  5  |   5  |     12
         *
         *    0|0001100 10100010 10111110 10001001 01011100 00|     |      |              //la
         *    0|                                              |10001|      |              //lb
         *    0|                                              |     |1 1001|              //lc
         * or 0|                                              |     |      |0000 00000000 //sequence
         * ------------------------------------------------------------------------------------------
         *    0|0001100 10100010 10111110 10001001 01011100 00|10001|1 1001|0000 00000000 //结果：910499571847892992
         *纵向观察发现:
         *      1.在41位那一段，除了la一行有值，其它行（lb、lc、sequence）都是0
         *      2.在左起第一个5位那一段，除了lb一行有值，其它行都是0
         *      3.在左起第二个5位那一段，除了lc一行有值，其它行都是0
         *      4.按照这规律，如果sequence是0以外的其它值，12位那段也会有值的，其它行都是0
         * 横向观察发现:
         *      1.在la行，由于左移了5+5+12位，5、5、12这三段都补0了，所以la行除了41那段外，其它肯定都是0
         *      2.同理，lb、lc、sequnece行也以此类推
         *      3.正因为左移的操作，使四个不同的值移到了SnowFlake理论上相应的位置，然后四行做位或运算（只要有1结果就是1），就把4段的二进制数合并成一个二进制数。
         *结论:
         * 所以，在这段代码中:
         *      return ((timestamp - 1288834974657) << 22) |
         *         (datacenterId << 17) |
         *         (workerId << 12) |
         *         sequence;
         * 左移运算是为了将数值移动到对应的段(41、5、5，因为12那段因为本来就在最右，因此不用左移)。
         * 然后对每个左移后的值(la、lb、lc、sequence)做位或运算，是为了把各个短的数据合并起来，合并成一个二进制数。
         * 最后转换成10进制，就是最终生成的id
         */
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen(){
        return System.currentTimeMillis();
    }

    //---------------测试---------------
    public static void main(String[] args) {
        IdWorker worker = new IdWorker(1,1,1);
        for (int i = 0; i < 30; i++) {
            System.out.println(worker.nextId());
        }
    }

}