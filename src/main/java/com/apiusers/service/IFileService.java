package com.apiusers.service;

import com.apiusers.entity.FileEntity;
import com.apiusers.record.response.FileListResponseRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IFileService {

    // Permite cargar archivos en BD
    ResponseMessageRecord store(MultipartFile file, Long userId) throws IOException;
    // Permite la descargar de un archivo
    Optional<FileEntity> getFile(UUID id) throws FileNotFoundException;

    // Permite obtener el listado de archivos
    List<FileListResponseRecord> getAllFiles();
}
