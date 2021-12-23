package com.qgstudio.anyworkc.records;


import com.qgstudio.anyworkc.records.bean.RecordsData;

import retrofit2.http.GET;
import rx.Observable;

public interface RecordService {

    @GET("test/record")
    Observable<RecordsData> getNotice();
}
