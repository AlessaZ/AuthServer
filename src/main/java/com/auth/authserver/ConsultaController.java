package com.auth.authserver;

import com.auth.authserver.Repository.IptableRepo;
import com.auth.authserver.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class ConsultaController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/haspermission")
    public ResponseEntity<HashMap<String,Object>> cancelarCompra(
            @RequestParam("userip") String userIp,
            @RequestParam("resip") String resIp,
            @RequestParam("resport") String resPort){
        HashMap<String,Object> responseMap= new HashMap<>();
        responseMap.put("forwarding",userRepo.hasAccessToResource(userIp, resIp, resPort).toString());
        return ResponseEntity.ok(responseMap);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<HashMap<String,Object>> gestionExcepcion(HttpServletRequest request){
        HashMap<String,Object> responseMap = new HashMap<>();
        if(request.getMethod().equals("GET")){
            responseMap.put("estado","error");
            responseMap.put("forwarding","false");
            responseMap.put("msg","No se han enviado los datos de forma correcta");
        }
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }
}
