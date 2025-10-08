package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("requests", requestRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/addrequest")
    public String addRequestForm() {
        return "addrequest";
    }

    @PostMapping(value="/addrequest")
    public String addRequest(@RequestParam String userName,
                             @RequestParam String courseName,
                             @RequestParam String phone) {
        ApplicationRequest request = new ApplicationRequest();
        request.setUserName(userName);
        request.setCourseName(courseName);
        request.setPhone(phone);
        request.setHandled(false);
        requestRepository.save(request);
        return "redirect:/";
    }

    @GetMapping(value = "/details")
    public String details(@RequestParam Long requestId,  Model model) {
        Optional<ApplicationRequest> request = requestRepository.findById(requestId);
        if (request.isPresent()) {
            model.addAttribute("request", request.get());
            return "details";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/details")
    public String updateRequest(@RequestParam Long requestId,
                              @RequestParam String userName,
                              @RequestParam String courseName,
                              @RequestParam String phone) {
        Optional<ApplicationRequest> request = requestRepository.findById(requestId);
        if (request.isPresent()) {
            ApplicationRequest requestUpdate = request.get();
            requestUpdate.setUserName(userName);
            requestUpdate.setCourseName(courseName);
            requestUpdate.setPhone(phone);
            requestRepository.save(requestUpdate);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deleterequest")
    public String deleteRequest(@RequestParam Long requestId) {
        requestRepository.deleteById(requestId);
        return "redirect:/";
    }

    @PostMapping(value = "/changehandled")
    public String changeHandled(@RequestParam Long requestId) {
        Optional<ApplicationRequest> request = requestRepository.findById(requestId);
        if (request.isPresent()) {
            ApplicationRequest requestUpdate = request.get();
            requestUpdate.setHandled(!requestUpdate.isHandled());
            requestRepository.save(requestUpdate);
        }
        return "redirect:/";
    }

    @GetMapping(value = "/unprocessed")
    public String pending(Model model) {
        model.addAttribute("requests", requestRepository.findByHandledFalse());
        return "unprocessed";
    }

    @GetMapping(value = "/processed")
    public String processed(Model model) {
        model.addAttribute("requests", requestRepository.findByHandledTrue());
        return "processed";
    }
}
