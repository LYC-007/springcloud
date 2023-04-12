-- 下标从 1 开始 获取key
local key = KEYS[1]
-- 下标从 1 开始 获取参数,  Lua  tonumber这个函数会尝试将它的参数转换为数字
local now = tonumber(ARGV[1]) 			   -- 当前时间戳
local slideExpiredTime = tonumber(ARGV[2]) -- 当前key 滑动窗口之外过期时间
local max = tonumber(ARGV[3])              -- 最大限流次数
local expiredTime = tonumber(ARGV[4])      -- 缓存过期时间

-- 移除滑动窗口之外的数据 Redis ZREMRANGEBYSCORE 命令移除有序集key中，所有score值介于min和max之间(包括等于min或max)的成员。
-- 移除指定分数区间内的所有元素，expired 即已经过期的 score, 根据当前时间毫秒数 - 超时毫秒数，得到过期时间 slideExpiredTime
redis.call('zremrangebyscore', key, 0, slideExpiredTime)

-- 每次访问均重新设置 zset 的过期时间，单位毫秒
redis.call("pexpire", key, expiredTime)

-- 获取 zset 中的当前元素个数 , Redis ZCARD 命令用于返回有序集的成员个数
local current = tonumber(redis.call('zcard', key))
local next = current + 1
-- 判断当前集合内个数+1 后是否超出最大限制次数
if next > max then
  -- 达到限流大小 返回 0
  return 0
-- 否则添加进入zset集合
else
  -- 往 zset 中添加一个 Score、value均为当前时间戳的元素，[score,value]
  redis.call("zadd", key, now, now)

  -- PEXPIRE 命令和 EXPIRE 命令的作用类似，但是它以毫秒为单位设置 key 的生存时间，而不像 EXPIRE 命令那样，以秒为单位
  -- 返回当前次数
  return next
end
