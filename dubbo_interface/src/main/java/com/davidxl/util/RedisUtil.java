package com.davidxl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Repository
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

    // ----------------------------------------Object----------------------------------------
    /**
     * 设置对象
     *
     * @param key
     * @param object
     * @param timeout
     * @param clazz
     * @return
     * @throws Exception
     */
    public boolean addObject(final String key, final Object object, final Long timeout, Class<?> clazz) {
        redisTemplate.opsForValue().set(key, clazz.cast(object),timeout, TimeUnit.SECONDS);
        return true;
    }

    /**
     * 设置对象
     *
     * @param key
     * @param object
     * @param timeout
     * @param clazz
     * @return
     * @throws Exception
     */
    public boolean deleteAndAddObject(final String key, final Object object, final Long timeout, Class<?> clazz) {
        this.delete(key);
        redisTemplate.opsForValue().set(key, clazz.cast(object));
        return true;
    }

    /**
     * 获得对象
     *
     * @param key
     * @return
     */
    public Object getObject(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // ---------------------------------------String-----------------------------------------

    /**
     * 新增String ----setNX 不存在则增加 ------------------------------
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param timeout
     *            超时(秒)
     * @return true 操作成功，false 已存在值
     */
    public boolean addString(final String key, final String value, final Long timeout) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result1 = connection.setNX(key.getBytes(), value.getBytes());
            if (result1 == false)
                return result1;
            if (timeout != null && timeout > 0)
                connection.expire(key.getBytes(), timeout);
            return result1;
        });
        return result;
    }

    /**
     * 删除并新增String
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param timeout
     *            超时(秒)
     * @return true 操作成功，false 已存在值
     */
    public boolean deleteAndAddString(final String key, final String value, final Long timeout) {
        this.delete(key);
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result1 = connection.setNX(key.getBytes(), value.getBytes());
            if (result1 == false)
                return result1;
            if (timeout != null && timeout > 0)
                connection.expire(key.getBytes(), timeout);
            return result1;
        });
        return result;
    }

    /**
     * 批量新增String---setNx 不存在则增加
     *
     * @param keyValueList
     *            键值对的map
     * @param timeout
     *            超时处理
     * @return
     */
    public boolean addString(final Map<String, String> keyValueList, final Long timeout) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            for (String key : keyValueList.keySet()) {
                connection.setNX(key.getBytes(), keyValueList.get(key).getBytes());
                if (timeout != null && timeout > 0)
                    connection.expire(key.getBytes(), timeout);
            }
            return true;
        }, false, true);
        return result;
    }

    /**
     * 通过key获取单个
     *
     * @param key
     * @return
     */
    public String getString(final String key) {
        String value = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            byte[] result = connection.get(key.getBytes());
            if(result != null && result.length > 0)
                return new String(result);
            return null;
        });
        return value;
    }

    /**
     * 修改 String
     *
     * @param key
     * @param value
     * @return
     */
	/*
	重新Set等于Update
	public boolean updateString(final String key, final String value) {
		if (getString(key) == null) {
			throw new NullPointerException("数据行不存在, key = " + key);
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key.getBytes(), value.getBytes());
				return true;
			}
		});
		return result;
	}
	/

	// ---------------------------------------List-----------------------------------------
	/**
	 * 新增Hash ----setNX 不存在则增加 ------------------------------
	 *
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param timout
	 *            超时(秒)
	 * @return true 操作成功，false 已存在值
	 */
    public boolean addHash(final String key, final String field, final String value, final Long timeout) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            Boolean result1 = connection.hSetNX(key.getBytes(), field.getBytes(), value.getBytes());
            if (result1 == false)
                return result1;
            if (timeout != null && timeout > 0)
                connection.expire(key.getBytes(), timeout);
            return result1;
        });
        return result;
    }

    /**
     * 批量新增Hash ----setNX 不存在则增加 ------------------------------
     *
     * @param key
     *            键
     * @param fieldValueList
     *            值
     * @param timeout
     *            超时(秒)
     * @return true 操作成功，false 已存在值
     */
    public boolean addHash(final String key, final Map<String, String> fieldValueList, final Long timeout) {
        boolean result = (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            for (String hashKey : fieldValueList.keySet()) {
                connection.hSetNX(key.getBytes(), hashKey.getBytes(), fieldValueList.get(hashKey).getBytes());
                if (timeout != null && timeout > 0)
                    connection.expire(key.getBytes(), timeout);
            }
            return true;
        });
        return result;
    }

    /**
     * 通过key获取单个
     *
     * @param key
     * @return
     */
    public Object getHashField(final String key, final String field) {
        String value = (String) redisTemplate.execute((RedisCallback<String>) connection -> {
            return new String(connection.hGet(key.getBytes(), field.getBytes()));
        });
        return value;
    }

    /**
     * 通过key获取整个Hash
     *
     * @param key
     * @return
     */
    public Map<byte[], byte[]> getHashAll(final String key, final String field) {
        Map<byte[], byte[]> value = (Map<byte[], byte[]>) redisTemplate.execute((RedisCallback<Map<byte[], byte[]>>) connection -> {
            return connection.hGetAll(key.getBytes());
        });
        return value;
    }

    //---------------------------------------------------通用删除-------------------------------------------------
    /**
     * 删除单个
     *
     * @param key
     */
    public void delete(final String key) {
        redisTemplate.execute((RedisCallback<Long>) connection -> {
            return connection.del(key.getBytes());
        });
    }


    //----------------------------------------------------队列操作--------------------------------------------------
    /**
     * 压栈
     *
     * @param key
     * @param value
     * @return
     */
    public Long push(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 出栈
     *
     * @param key
     * @return
     */
    public String pop(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 入队
     *
     * @param key
     * @param value
     * @return
     */
    public Long in(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 出队
     *
     * @param key
     * @return
     */
    public String out(String key) {
        return (String) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 栈/队列长
     *
     * @param key
     * @return
     */
    public Long length(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 范围检索
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 移除
     *
     * @param key
     * @param i
     * @param value
     */
    public void remove(String key, long i, String value) {
        redisTemplate.opsForList().remove(key, i, value);
    }

    /**
     * 检索
     *
     * @param key
     * @param index
     * @return
     */
    public String index(String key, long index) {
        return (String) redisTemplate.opsForList().index(key, index);
    }

    /**
     * 置值
     *
     * @param key
     * @param index
     * @param value
     */
    public void set(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 裁剪
     *
     * @param key
     * @param start
     * @param end
     */
    public void trim(String key, long start, int end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    //---------------------------------------------------SET-----------------------------------------------
    /**
     * 新增Set ----setNX 不存在则增加 ------------------------------
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param timeout
     *            超时(秒)
     * @return true 操作成功，false 已存在值
     */
    public Long addSet(final String key, final String value, final Long timeout) {
        Long result = (Long) redisTemplate.execute((RedisCallback<Long>) connection -> {
            Long result1 = connection.sAdd(key.getBytes(), value.getBytes());
            if (result1 == 0)
                return result1;
            if (timeout != null && timeout > 0)
                connection.expire(key.getBytes(), timeout);
            return result1;
        });
        return result;
    }


    /**
     * 通过key获取单个Set
     *
     * @param key
     * @return
     */
    public Set<byte[]> getSet(final String key) {
        Set<byte[]> value = (Set<byte[]>) redisTemplate.execute((RedisCallback<Set<byte[]>>) connection -> {
            return connection.sMembers(key.getBytes());
        });
        return value;
    }

}
