package com.example.app16;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.app16.ui.main.CacheComponent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class CacheComponentTest {

    Context mockContext= mock(Context.class);

    CacheComponent cacheComponent;

    AssetManager assetManager = mock(AssetManager.class);

    @BeforeEach
    public void setUp(){

        cacheComponent = new CacheComponent(mockContext);
        when(mockContext.getAssets()).thenReturn(assetManager);
    }

    @Test
    public void given_criteria_return_filename_if_exits() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01", "TSLA_2017-01-01_2019-01-01"});
       Boolean isCached = cacheComponent.getFilenameOfStock("TSLA",
                "2015-01-01",
                "2017-01-01");

        Assertions.assertEquals(Boolean.TRUE,isCached);

    }

    @Test
    public void given_fromdate_todate_no_file_exists_return_empty_list() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{});
        Boolean isCached = cacheComponent.getFilenameOfStock("TSLA",
                "2015-01-01",
                "2017-01-01");
        Assertions.assertEquals(Boolean.FALSE,isCached);

    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_equal_return_filename() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01", "TSLA_2017-01-01_2019-01-01"});
        Boolean isCached = cacheComponent.getFilenameOfStock("TSLA",
                "2015-02-01",
                "2017-01-01");
        Assertions.assertEquals(Boolean.TRUE,isCached);

    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_less_than_return_filename() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01", "TSLA_2015-01-01_2017-01-01"});
        Boolean isCached = cacheComponent.getFilenameOfStock("TSLA",
                "2015-02-01",
                "2016-12-01");
        Assertions.assertEquals(Boolean.TRUE,isCached);
    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_more_than_return_empty_list() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01", "TSLA_2015-01-01_2017-01-01"});
        Boolean isCached = cacheComponent.getFilenameOfStock("TSLA",
                "2015-02-01",
                "2017-02-01");
        Assertions.assertEquals(Boolean.FALSE,isCached);
    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_before_than_return_file() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01", "TSLA_2015-01-01_2017-01-01",
                        "AAPL_2015-01-01_2017-01-01", "GOGL_2017-01-01_2017-10-01"});
        Boolean isCached = cacheComponent.getFilenameOfStock("GOGL",
                "2017-02-01",
                "2017-07-01");
        Assertions.assertEquals(Boolean.TRUE,isCached);
    }
}
