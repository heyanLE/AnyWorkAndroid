package com.qgstudio.anyworkc.paper.data;

import com.qgstudio.anyworkc.data.ResponseResult;
import com.qgstudio.anyworkc.workout.data.Testpaper;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Yason on 2017/4/11.
 */

public interface PaperApi {

    @POST("test/practiceList")
    Observable<ResponseResult<List<Testpaper>>> getPracticePaper(@Body Map<String, String> organizationId);

    @POST("test/testList")
    Observable<ResponseResult<List<Testpaper>>> getExaminationPaper(@Body Map<String, String> organizationId);

}
