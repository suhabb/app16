package com.example.app16;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.app16.ui.main.CacheComponent;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CacheComponentTest {

    Context mockContext= mock(Context.class);

    CacheComponent cacheComponent;

    AssetManager assetManager = mock(AssetManager.class);

    @Before
    public void setUp(){

        cacheComponent = new CacheComponent(mockContext);
        when(mockContext.getAssets()).thenReturn(assetManager);
    }

    @Test
    public void given_criteria_return_filename_if_exits() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01.json", "TSLA_2017-01-01_2019-01-01"});
        List<String> listOfFilename = cacheComponent.getFilenameOfStock("TSLA",
                "2015-01-01",
                "2017-01-01");
        System.out.println(listOfFilename);
        Assert.assertEquals("TSLA_2015-01-01_2017-01-01.json",listOfFilename.get(0));

    }

    @Test
    public void given_fromdate_todate_no_file_exists_return_empty_list() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{});
        List<String> listOfFilename = cacheComponent.getFilenameOfStock("TSLA",
                "2015-01-01",
                "2017-01-01");
        Assert.assertEquals(Boolean.TRUE,listOfFilename.isEmpty());

    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_equal_return_filename() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01.json", "TSLA_2017-01-01_2019-01-01"});
        List<String> listOfFilename = cacheComponent.getFilenameOfStock("TSLA",
                "2015-02-01",
                "2017-01-01");
        Assert.assertEquals("TSLA_2015-01-01_2017-01-01.json",listOfFilename.get(0));

    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_less_than_return_filename() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01.json", "TSLA_2015-01-01_2017-01-01.json"});
        List<String> listOfFilename = cacheComponent.getFilenameOfStock("TSLA",
                "2015-02-01",
                "2016-12-01");
        Assert.assertEquals("TSLA_2015-01-01_2017-01-01.json",listOfFilename.get(0));
    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_more_than_return_empty_list() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01.json", "TSLA_2015-01-01_2017-01-01.json"});
        List<String> listOfFilename = cacheComponent.getFilenameOfStock("TSLA",
                "2015-02-01",
                "2017-02-01");
        Assert.assertEquals(Boolean.TRUE,listOfFilename.isEmpty());
    }

    @Test
    public void given_fromdate_isAfter_beforedate_is_before_than_return_file() throws IOException {

        when(assetManager.list(anyString()))
                .thenReturn(new String[]{"TSLA_2015-01-01_2017-01-01.json", "TSLA_2015-01-01_2017-01-01.json",
                        "AAPL_2015-01-01_2017-01-01", "GOGL_2017-01-01_2017-10-01"});
        List<String> listOfFilename = cacheComponent.getFilenameOfStock("GOGL",
                "2017-02-01",
                "2017-07-01");
        Assert.assertEquals("GOGL_2017-01-01_2017-10-01",listOfFilename.get(0));
    }
}
