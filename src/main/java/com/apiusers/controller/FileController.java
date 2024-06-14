package com.apiusers.controller;

import com.apiusers.entity.FileEntity;
import com.apiusers.record.response.FileListResponseRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.service.IFileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/fileManager")
@AllArgsConstructor
public class FileController {
    private final IFileService fileService;

    @PostMapping("/upload/userId/{id}")
    public ResponseEntity<ResponseMessageRecord> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
        return new ResponseEntity<>(this.fileService.store(file, id), HttpStatus.OK);
    }

    @GetMapping("/files/{uuid}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID uuid) throws FileNotFoundException {
        FileEntity file = this.fileService.getFile(uuid).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, file.getType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileListResponseRecord>> getAllFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(this.fileService.getAllFiles());
    }
}
