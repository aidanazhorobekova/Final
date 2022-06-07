package br.com.example.medicine.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.example.medicine.repository.PostRepository;
import br.com.example.medicine.repository.RegionRepository;
import br.com.example.medicine.repository.MedicineRepository;
import br.com.example.medicine.repository.UserRepository;

@Controller
public class MyErrorController implements ErrorController{
	
	@Autowired
	RegionRepository regionRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

		
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            switch (statusCode) {
            	case 403:
            		return "403";
                
                case 404:
                    return "404";

                case 500:
                    return "500";
                    
            }
        }
        return "error";
    }

}
