package com.example.crsm_g8.schedular;

import com.example.crsm_g8.Entity.ContractEntity;
import com.example.crsm_g8.Service.IService.ContractService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExpiredSchedular {
    @Autowired
    ContractService contractService;

    @SneakyThrows
    @Scheduled(cron = "0 0 1 * * ?")
    public void checkInvalidContract(){
        List<ContractEntity> contractEntities = contractService.getListInvalidContract();
        if (contractEntities.size() > 0){
            for (ContractEntity contractEntity : contractEntities){
                contractEntity.setStatus(7);
                contractEntity.setNote("Hợp đồng quá hạn lấy xe");
                contractService.save(contractEntity);
            }
        }
    }
}
