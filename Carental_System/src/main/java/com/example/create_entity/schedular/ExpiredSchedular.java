package com.example.create_entity.schedular;

import com.example.create_entity.Entity.ContractEntity;
import com.example.create_entity.Service.ContractService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimeZone;

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
