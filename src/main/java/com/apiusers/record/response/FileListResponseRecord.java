package com.apiusers.record.response;

public record FileListResponseRecord(
        String name,
        String url,
        String type,
        long size
){
}
