package com.example.crms_g8.Controller;

import com.example.crms_g8.Service.ServiceImpl.ContractDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    @Autowired
    ContractDetailServiceImpl contractDetailService;

    @RequestMapping(value = "/manager/Future_Schedule", method = RequestMethod.GET)
    public ResponseEntity<?> FutureSchedule(@RequestParam(required = false) String username) {
        return contractDetailService.FutureSchedule(username);
    }


    @RequestMapping(value = "/manager/Current_Schedule", method = RequestMethod.GET)
    public ResponseEntity<?> CurrentSchedule(@RequestParam(required = false) String username) {
        return contractDetailService.CurrentSchedule(username);
    }

    @RequestMapping(value = "/manager/History_schedule", method = RequestMethod.GET)
    public ResponseEntity<?> HistorySchedule(@RequestParam(required = false) String username) {
        return contractDetailService.Historyschedule(username);
    }
//    @RequestMapping(value = "/manager/DetailSchedule", method = RequestMethod.GET)
//    public ResponseEntity<?> DetailSchedule(@RequestParam(required = false) Long ID) {
//        return contractDetailService.Current_Schedule(DriverID);
//    }


//
//    @RequestMapping(value = "/manager/SearchName_Schedule", method = RequestMethod.GET)
//    public ResponseEntity<?> SearchName_Schedule(@RequestParam(value = "p", required = false) Integer p,
//                                                 @RequestParam(value = "name", required = false, defaultValue = "") String name,
//                                                 @RequestParam(value = "date_start1", required = false, defaultValue = "") String date_start1,
//                                                 @RequestParam(value = "date_start2", required = false, defaultValue = "") String date_start2) {
//        return contractDetailService.SearchName_Schedule(name, date_start1, date_start2, p);
//
//    }
//
//        @RequestMapping(value = "/manager/SearchPlateNumber_Schedule", method = RequestMethod.GET)
//        public ResponseEntity<?> Search_PlateNumber_Schedule(@RequestParam(value = "p", required = false) Integer p,
//                @RequestParam(value = "plate_number", required = false, defaultValue = "") String plate_number,
//                @RequestParam(value = "date_start1", required = false, defaultValue = "") String date_start1,
//                @RequestParam(value = "date_start2", required = false, defaultValue = "") String date_start2){
//
//
//            return contractDetailService.Search_PlateNumber_Schedule(plate_number, date_start1, date_start2, p);
//        }
}
