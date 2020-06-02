package ru.totsystems.moexiss.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;
import ru.totsystems.moexiss.model.SecurityEntity;
import ru.totsystems.moexiss.service.SecurityService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/securities/")
@AllArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @GetMapping(value = "{SECID}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SecurityEntity get(@PathVariable("SECID") String secid) {
        return securityService.get(secid);
    }

    @PostMapping(value = "import", consumes = {"multipart/form-data"})
    public String handleFileUpload(@RequestParam("xmlFile") MultipartFile file) throws ParserConfigurationException, SAXException, IOException {
        System.out.println(file);
        securityService.saveImported(file.getInputStream());
        return "redirect:/page";
    }

    @PostMapping("")
    public ResponseEntity save(SecurityEntity securityEntity) {
        securityService.save(securityEntity);
        return ResponseEntity.ok().build();
    }

    @PutMapping("")
    public ResponseEntity update(SecurityEntity securityEntity) {
        securityService.update(securityEntity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{SECID}")
    public ResponseEntity delete(@PathVariable("SECID") String secid) {
        securityService.remove(secid);
        return ResponseEntity.ok().build();
    }
}
