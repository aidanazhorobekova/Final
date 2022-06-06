package br.com.example.iHealth.controller;

import java.security.Principal;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.example.iHealth.model.User;
import br.com.example.iHealth.repository.PostoRepository;
import br.com.example.iHealth.repository.RegiaoRepository;
import br.com.example.iHealth.repository.MedicineRepository;
import br.com.example.iHealth.repository.UserRepository;

@Controller
public class MyErrorController implements ErrorController{
	
	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	MedicineRepository medicineRepository;
	
	@Autowired
	PostoRepository postoRepository;
	
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
