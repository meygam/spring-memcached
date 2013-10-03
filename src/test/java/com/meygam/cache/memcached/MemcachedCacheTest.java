package com.meygam.cache.memcached;

import com.meygam.cache.spring.memcached.MemcachedCache;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.internal.OperationFuture;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: iiczy
 * Date: 10/2/13
 * Time: 8:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class MemcachedCacheTest {
    private MemcachedCache memcachedCache;
    private Mockery mockery;
    private MemcachedClient memcachedClient;

    @Before
    public void setUp() {
        setMockery(new JUnit4Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        });
        setMemcachedClient(getMockery().mock(MemcachedClient.class));
        setMemcachedCache(new MemcachedCache(getMemcachedClient(),30,"testCache"));
    }

    @After
    public void tearDown() throws Exception {
        getMockery().assertIsSatisfied();
    }

    @Test
    public void testClear() throws Exception {
        getMockery().checking(new Expectations() {
            {
                one(getMemcachedClient()).flush();
                will(returnValue(null));
            }
        });
        getMemcachedCache().clear();
    }

    @Test
    public void testEvict() throws Exception {
        getMockery().checking(new Expectations() {
            {
                one(getMemcachedClient()).delete(with(any(String.class)));
                will(returnValue(null));
            }
        });
        getMemcachedCache().evict("testKey");

    }

    public MemcachedCache getMemcachedCache() {
        return memcachedCache;
    }

    public void setMemcachedCache(MemcachedCache memcachedCache) {
        this.memcachedCache = memcachedCache;
    }

    public Mockery getMockery() {
        return mockery;
    }

    public void setMockery(Mockery mockery) {
        this.mockery = mockery;
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }
}
