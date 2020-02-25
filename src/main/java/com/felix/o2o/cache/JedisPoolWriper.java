package com.felix.o2o.cache;


import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * ǿָ��redis��JedisPool�ӿڹ��캯��������������centos�ɹ�����jedispool
 * 
 * @author felix
 *
 */
public class JedisPoolWriper {
    /** Redis���ӳض��� */
    private JedisPool jedisPool;

    public JedisPoolWriper(final JedisPoolConfig poolConfig, final String host,
            final int port) {
        try {
            jedisPool = new JedisPool(poolConfig, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��ȡRedis���ӳض���
     * @return
     */
    public JedisPool getJedisPool() {
        return jedisPool;
    }

    /**
     * ע��Redis���ӳض���
     * @param jedisPool
     */
    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

}
