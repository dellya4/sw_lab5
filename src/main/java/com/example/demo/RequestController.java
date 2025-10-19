package com.example.demo;

import com.example.demo.entity.ApplicationRequest;
import com.example.demo.entity.Courses;
import com.example.demo.entity.Operators;
import com.example.demo.repository.CoursesRepository;
import com.example.demo.repository.OperatorsRepository;
import com.example.demo.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class RequestController {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private CoursesRepository coursesRepository;
    @Autowired
    private OperatorsRepository operatorsRepository;

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("requests", requestRepository.findAll());
        return "index";
    }

    @GetMapping(value = "/addrequest")
    public String addRequestForm(Model model) {
        model.addAttribute("request", new ApplicationRequest());
        model.addAttribute("courses", coursesRepository.findAll());
        return "addrequest";
    }

    @PostMapping(value="/addrequest")
    public String addRequest(@RequestParam String userName,
                             @RequestParam("courseId") Long courseId,
                             @RequestParam String phone) {
        Courses course = coursesRepository.findById(courseId).orElse(null);

        ApplicationRequest request = new ApplicationRequest();
        request.setUserName(userName);
        request.setCourse(course);
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
            model.addAttribute("courses", coursesRepository.findAll());
            model.addAttribute("operators", operatorsRepository.findAll());
            return "details";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping(value = "/details")
    public String updateRequest(@RequestParam Long requestId,
                              @RequestParam String userName,
                              @RequestParam("courseId") Long courseId,
                              @RequestParam String phone) {
        Optional<ApplicationRequest> request = requestRepository.findById(requestId);
        if (request.isPresent()) {
            Courses course = coursesRepository.findById(courseId).orElse(null);

            ApplicationRequest requestUpdate = request.get();
            requestUpdate.setUserName(userName);
            requestUpdate.setCourse(course);
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
    public String changeHandled(@RequestParam Long requestId, Model model) {
        Optional<ApplicationRequest> request = requestRepository.findById(requestId);
        if (request.isPresent()) {
            ApplicationRequest requestUpdate = request.get();

            if (!requestUpdate.isHandled()) {
                model.addAttribute("request", requestUpdate);
                model.addAttribute("operators", operatorsRepository.findAll());
                return "addoperators";
            }
            requestUpdate.setHandled(false);
            requestRepository.save(requestUpdate);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/addoperators")
    public String addOperators(@RequestParam Long requestId,
                               @RequestParam(value = "operatorIds", required = false) List <Long> operatorsIds) {
        Optional<ApplicationRequest> request = requestRepository.findById(requestId);
        if (request.isPresent()) {
            ApplicationRequest requestUpdate = request.get();
            List<Operators> operators = operatorsRepository.findAllById(operatorsIds);
            requestUpdate.setOperators(operators);
            requestUpdate.setHandled(true);
            requestRepository.save(requestUpdate);
        }
        return "redirect:/";
    }

    @PostMapping(value = "/deleteoperator")
    public String deleteOperators(@RequestParam Long requestId,
                                  @RequestParam Long operatorId) {
        Optional<ApplicationRequest> request = requestRepository.findById(requestId);
        if (request.isPresent()) {
            ApplicationRequest requestUpdate = request.get();
           requestUpdate.getOperators().removeIf(operator -> operator.getId().equals(operatorId));
           requestRepository.save(requestUpdate);
        }
        return "redirect:/details?requestId=" + requestId;
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
