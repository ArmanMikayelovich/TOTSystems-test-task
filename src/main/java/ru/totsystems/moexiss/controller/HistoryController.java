package ru.totsystems.moexiss.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import ru.totsystems.moexiss.model.HistoryDTO;
import ru.totsystems.moexiss.model.HistoryTableDTO;
import ru.totsystems.moexiss.service.HistoryService;
import ru.totsystems.moexiss.util.SortDTO;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/histories")
@AllArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/{id}")
    public HistoryDTO getAllBySecid(@PathVariable(value = "id") Long id) {
        return historyService.getById(id);
    }

    @GetMapping(value = "/table", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HistoryTableDTO>> getTable(
            String sortDirection,
            String sortAttribute,
            String startDate,
            String endDate,
            String emitentTitle) {

        LocalDate startDateLocalDate = null;

        if (startDate != null) {
            startDateLocalDate = LocalDate.parse(startDate);
        }
        LocalDate endDateLocalDate = null;
        if (endDate != null) {
            endDateLocalDate = LocalDate.parse(endDate);
        }
        SortDTO sortDTO = new SortDTO(sortDirection,
                sortAttribute,
                startDateLocalDate,
                endDateLocalDate,
                emitentTitle);
        List<HistoryTableDTO> forTable = historyService.getForTable(sortDTO);
        return ResponseEntity.ok(forTable);
    }

    @PostMapping("")
    public ResponseEntity save(HistoryDTO historyDTO) {
        historyService.save(historyDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "import", consumes = {"multipart/form-data"})
    public String handleFileUpload(@RequestParam("xmlFile") MultipartFile file) throws ParserConfigurationException, SAXException, IOException {
        historyService.saveImported(file);
        return "redirect:/page";
    }

    @PutMapping("")
    public ResponseEntity update(HistoryDTO historyDTO) {
        historyService.update(historyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        historyService.delete(id);
        return ResponseEntity.ok().build();
    }
}
