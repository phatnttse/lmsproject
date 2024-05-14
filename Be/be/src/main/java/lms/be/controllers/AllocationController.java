package lms.be.controllers;

import com.opencsv.CSVReader;
import lms.be.components.FileFunction;
import lms.be.dtos.AllocationDTO;
import lms.be.dtos.SubjectDTO;
import lms.be.responses.AllocationResponse;
import lms.be.service.AllocationService;
import lms.be.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("allocation")
@RequiredArgsConstructor
public class    AllocationController {
    private final SubjectService subjectService;
    private final AllocationService allocationService;
    private final FileFunction fileFunction;

    @PostMapping
    public ResponseEntity<?> createAllocation(@RequestBody AllocationDTO allocationDTO) {
        try {
            AllocationResponse allocationResponse = allocationService.createAllocation(allocationDTO);
            return ResponseEntity.ok(allocationResponse);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAllocationById(@PathVariable("id") Long id) {
        try {
            AllocationResponse allocationResponse = allocationService.getAllocationById(id);
            return ResponseEntity.ok(allocationResponse);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAllocationFromFile(@ModelAttribute MultipartFile file) {
        try {
            List<MultipartFile> files = new ArrayList<>();
            List<String> required = new ArrayList<>();
            required.add("csv");
            files.add(file);
            String test = fileFunction.checkRequire(files,required, 100L, 1);
            if(!test.equals("accepted")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(test);
            }
            String path = fileFunction.storeFile(file, "allocation");
            CSVReader csvReader = new CSVReader(new FileReader(path));
            String[] nextline;
            List<AllocationResponse> result = new ArrayList<>();
            boolean skipFirstLine = true;
            while ((nextline = csvReader.readNext()) != null) {
                if(skipFirstLine) {
                    skipFirstLine = false;
                    continue;
                }
                ///subjectId -> teacherId -> classId
                AllocationDTO allocationDTO = new AllocationDTO(nextline[0], Integer.parseInt(nextline[1]), nextline[2]);
                result.add(allocationService.createAllocation(allocationDTO));
            }
            csvReader.close();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/subject")
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectDTO createdSubject = subjectService.createSubject(subjectDTO);
            return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/subject/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable("id") String id) {
        try {
            SubjectDTO subjectDTO = subjectService.getSubjectById(id);
            return ResponseEntity.ok(subjectDTO);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/subject")
    public ResponseEntity<?> getAllSubjects() {
        try {
            List<SubjectDTO> subjects = subjectService.getAllSubjects();
            return ResponseEntity.ok(subjects);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/subject/{id}")
    public ResponseEntity<?> updateSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            SubjectDTO updatedSubject = subjectService.updateSubject(subjectDTO);
            return ResponseEntity.ok(updatedSubject);
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/subject/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable("id") String id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
