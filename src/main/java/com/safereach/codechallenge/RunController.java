package com.safereach.codechallenge;

import com.safereach.codechallenge.donottouch.Data;
import com.safereach.codechallenge.donottouch.DoNotTouchProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RunController {

    private final DoNotTouchProcessor processor;

    @GetMapping("/run")
    public List<Data> run() {
        return processor.run();
    }

}
