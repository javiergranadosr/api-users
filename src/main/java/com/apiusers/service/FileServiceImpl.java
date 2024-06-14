package com.apiusers.service;

import com.apiusers.entity.FileEntity;
import com.apiusers.entity.UserEntity;
import com.apiusers.exception.ErrorBadRequest;
import com.apiusers.exception.ErrorNotFound;
import com.apiusers.record.response.FileListResponseRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.repository.FileRepository;
import com.apiusers.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@AllArgsConstructor
@Service
public class FileServiceImpl implements IFileService{
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    @Transactional
    @Override
    public ResponseMessageRecord store(MultipartFile file, Long userId) throws IOException {
        log.info("FileServiceImpl call method store()");
        if (file.isEmpty()) {
            throw new ErrorBadRequest("Failed to store empty file");
        }
        UserEntity userEntity = this.userRepository.findById(userId).orElseThrow(() -> new ErrorNotFound("User not found try again"));
        String fileName =  StringUtils.cleanPath(file.getOriginalFilename() != null ? file.getOriginalFilename() : "");
        if (!fileName.endsWith(".jpg") && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")) {
            throw new ErrorBadRequest("Only JPG, JPEG, PNG files are allowed");
        }
        Optional<FileEntity> dataFileEntity = this.fileRepository.findByUserId(userId);
        if (dataFileEntity.isPresent()) {
            dataFileEntity.get().setName(fileName);
            dataFileEntity.get().setType(file.getContentType());
            dataFileEntity.get().setData(file.getBytes());
            dataFileEntity.get().setUserEntity(userEntity);
            this.fileRepository.save(dataFileEntity.get());
        }else {
           FileEntity fileEntity = FileEntity.builder()
                    .name(fileName)
                    .type(file.getContentType())
                    .data(file.getBytes())
                    .userEntity(userEntity)
                    .build();
            this.fileRepository.save(fileEntity);
        }
        return new ResponseMessageRecord(HttpStatus.OK.value(),"File upload successfully");
    }

    @Override
    public Optional<FileEntity> getFile(UUID id) throws FileNotFoundException {
        log.info("FileServiceImpl call method getFile()");
        Optional<FileEntity> file = this.fileRepository.findById(id);
        if (file.isPresent()) {
            return file;
        }
        throw new FileNotFoundException("File not found try again");
    }

    @Override
    public List<FileListResponseRecord> getAllFiles() {
        log.info("FileServiceImpl call method getAllFiles()");
        return this.fileRepository.findAll().
                stream()
                .map(f -> {
                    String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("api/fileManager/files/")
                            .path(f.getId().toString())
                            .toUriString();
                    return new FileListResponseRecord(f.getName(), fileDownloadUrl, f.getType(), f.getData().length);
                }).toList();
    }
}
