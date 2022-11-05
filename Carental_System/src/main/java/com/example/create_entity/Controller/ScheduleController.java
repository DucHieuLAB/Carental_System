package com.example.create_entity.Controller;

import com.example.create_entity.Service.ContractDetailService;
import com.example.create_entity.Service.ContractDetailServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    @Autowired
    ContractDetailServiceImpl contractDetailService;

    @RequestMapping(value = "/manager/Schedule", method = RequestMethod.GET)
    public ResponseEntity<?> MangerSchedule(@RequestParam(value = "p", required = false) Integer p) {
        return contractDetailService.Schedule(p);
    }

    @RequestMapping(value = "/manager/SearchName_Schedule", method = RequestMethod.GET)
    public ResponseEntity<?> SearchName_Schedule(@RequestParam(value = "p", required = false) Integer p,
                                                 @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                 @RequestParam(value = "date_start1", required = false, defaultValue = "") String date_start1,
                                                 @RequestParam(value = "date_start2", required = false, defaultValue = "") String date_start2) {
        return contractDetailService.SearchName_Schedule(name, date_start1, date_start2, p);

    }

        @RequestMapping(value = "/manager/SearchPlateNumber_Schedule", method = RequestMethod.GET)
        public ResponseEntity<?> Search_PlateNumber_Schedule(@RequestParam(value = "p", required = false) Integer p,
                @RequestParam(value = "plate_number", required = false, defaultValue = "") String plate_number,
                @RequestParam(value = "date_start1", required = false, defaultValue = "") String date_start1,
                @RequestParam(value = "date_start2", required = false, defaultValue = "") String date_start2){


            return contractDetailService.Search_PlateNumber_Schedule(plate_number, date_start1, date_start2, p);
        }
    }
