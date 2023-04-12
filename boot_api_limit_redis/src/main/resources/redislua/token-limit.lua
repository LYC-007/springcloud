-- 要进行限流的Key   (返回 0:限流, 1:没有限流)
local key = KEYS[1]
-- 请求消耗的令牌数，每个请求消耗一个
local consume_permits = tonumber(ARGV[1])
-- 当前时间戳 (毫秒)
local curr_time = tonumber(ARGV[2])
-- 获取令牌桶hash数据结构
local limiter_info = redis.pcall("HMGET", key, "last_grant_time", "curr_permits", "bucket_cap", "rate")
-- 返回-1是没有配置限流
if not limiter_info[3] then
    return -1
end
-- 以下和令牌桶相关的参数, 使用项目启动时候初始化或者对应key同步方式初始化缓存,
-- 但是这种方式会导致这个令牌桶的缓存一直存在, 如果限流的颗粒度比较细, 会造成一些没有使用的令牌缓存浪费,
-- 这个时候就可以将桶参数传递方式进来, 先获取, 不存在的情况下, 初始化并添加过期时间
-- 上一次令牌的发放时间
local last_grant_time = tonumber(limiter_info[1]) or 0
-- 当前令牌数量
local curr_permits = tonumber(limiter_info[2]) or 0
-- 令牌桶容量
local bucket_cap = tonumber(limiter_info[3]) or 0
-- 令牌每个时间单位生成多少个,  目前这里是  /s
local rate = tonumber(limiter_info[4]) or 0
-- 发送时间单位  秒    1000毫秒=1秒  到时候也可以配置
local inflow_unit = 1000

-- 是否触发了发放令牌, 默认发放
local is_grant = true
local past_time = curr_time-last_grant_time
-- 大于说明需要发放令牌了否则反之
if past_time < inflow_unit then
    is_grant = false
end
-- 计算最终更新回缓存的当前令牌数
local total_permits = curr_permits
-- 需要发放计算需要发放令牌数
if is_grant then
-- 当前时间戳 - 上一次发放令牌时间戳  *  发放速度率 = 需要发放的令牌数
-- 预计投放数量 = (距上次投放过去的时间差)/投放的时间间隔1000) *每单位时间投放的数量 1
    local new_permits = math.floor((curr_time-last_grant_time)/1000) * rate
    total_permits = new_permits + curr_permits
end

-- 计算的总令牌数  >  桶容量, 则以桶容量发满令牌
if total_permits > bucket_cap then
    total_permits = bucket_cap
end

-- 返回是否限流结果, 默认是没有限流
local result = 1
-- 判断: 总令牌数 > 需要消耗的令牌
if total_permits >= consume_permits then
    -- 进行扣除令牌
    total_permits = total_permits - consume_permits
else
    -- 否则说明没有令牌可扣, 限流, 桶内没有令牌可获取
    result = 0
end
-- 根据结果, 做只更减少令牌数,   还是发放令牌与记录发放时间戳
if is_grant then
    -- 更新缓存数据   HMSET 可以更新多个字段
    redis.pcall("HMSET", key, "curr_permits", total_permits, "last_grant_time", curr_time)
else
    -- 更新缓存数据   HSET 更新单个字段
    redis.pcall("HSET", key, "curr_permits", total_permits)
end
-- 返回
return result
